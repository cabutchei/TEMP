package br.gov.caixa.silce.negocio.aposta.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.silce.dominio.Canal;
import br.gov.caixa.silce.dominio.DadosResumoCotaLoterica;
import br.gov.caixa.silce.dominio.NSB;
import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.entidade.ApostaComprada;
import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta.Situacao;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra;
import br.gov.caixa.silce.dominio.entidade.SituacaoPremio;
import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.negocio.infra.dao.AbstractSilceDAO;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;

/**
 * Implementação da DAO para Apostas de Carrinho.
 */
@Stateless
public class ApostaCompradaDAO extends AbstractSilceDAO<ApostaComprada, Long> implements ApostaCompradaDAOLocal {

	private static final int PAGAMENTO_AUTORIZADO_SISPL2 = 2;
	private static final int PAGAMENTO_PROCESSAMENTO_SISPL1 = 1;
	private static final Integer LIMIAR_TIMEOUT_SEGUNDOS = 180;
	private static final long serialVersionUID = 1L;

	@Override
	public List<Long> findWithUR(Situacao situacao, Data dataCorteAlteracaoSituacao, SituacaoPremio.Situacao... situacoesPremio) {
		if (situacoesPremio.length == 0) {
			return getResultListByNamedQuery(ApostaComprada.NQ_SELECT_IDS_BY_SITUACAO_DATAULTIMASITUACAO_WITH_UR, situacao.getValue(), dataCorteAlteracaoSituacao);
		}

		List<Long> situacoes = new ArrayList<Long>();
		for (SituacaoPremio.Situacao situacaoPremio : situacoesPremio) {
			situacoes.add(situacaoPremio.getValue());
		}
		return getResultListByNamedQuery(ApostaComprada.NQ_SELECT_IDS_BY_SITUACAO_DATAULTIMASITUACAO_PREMIOS_WITH_UR, situacao.getValue(), dataCorteAlteracaoSituacao, situacoes);
	}

	@Override
	public List<Long> findIdComprasWithUR(Situacao situacaoApostas, Data dataCorteAlteracaoSituacao, SituacaoCompra.Situacao situacaoCompra) {
		return getResultListByNamedQuery(ApostaComprada.NQ_SELECT_IDS_COMPRAS_BY_SITUACAO_DATAULTIMASITUACAO_WITH_UR, situacaoApostas.getValue(), dataCorteAlteracaoSituacao,
			situacaoCompra.getValue());
	}

	@Override
	public long findQuantidadeApostas(Long idCompra, Situacao... situacoes) {

		List<Long> idsSituacoes = convertToIdList(situacoes);
		return execCountForNamedQuery(ApostaComprada.NQ_SELECT_BY_SITUACAO_COMPRA, idCompra, idsSituacoes);
	}

	@Override
	public ApostaComprada findByNSB(NSB nsb) {
		return getSingleResultByNamedQuery(ApostaComprada.NQ_SELECT_BY_NSB, nsb);
	}

	@Override
	public ApostaComprada find(Long nsu, Data data) {
		return getSingleResultByNamedQuery(ApostaComprada.NQ_SELECT_BY_NSU_DATA, nsu, data);
	}

	/**
 	 * Criado no contexto da integração nuvem-legado, pela necessidade de buscar uma ApostaComprada que ainda não tenha sido enviada ao SISPL.
 	 * </p>
 	 * Para apostas nascidas na nuvem, a unicidade do NSU sozinho já é garantida, não requerendo data de envio SISPL.
 	*/
	@Override
	public ApostaComprada findNuvem(Long nsu) {
    	if (nsu < 5000000000L) {
        	throw new UnsupportedOperationException("Método findNuvem não pode ser utilizado para NSUs fora da faixa da nuvem");
    	}

    	return getSingleResultByNamedQuery(ApostaComprada.NQ_SELECT_BY_NSU, nsu);
	}

	@Override
	public ApostaComprada findByApostaApostador(Long idAposta, Long idApostador) {
		return getSingleResultByNamedQuery(ApostaComprada.NQ_SELECT_BY_APOSTA_APOSTADOR, idAposta, idApostador);
	}

	@Override
	public List<Object[]> findCountSumValorGroupedByMeioPagamento(Data dataFinalizacaoProcessamento, Situacao... situacoes) {
		Data dataInicial = new Data(dataFinalizacaoProcessamento).zereHoraMinutoSegundoMilisegundo();
		Data dataFinal = DataUtil.getUltimoTimestampDoDia(dataInicial);
		int nuMes = dataFinalizacaoProcessamento.getMonth();
		StringBuilder query = new StringBuilder(
			"SELECT COUNT(t0.NU_APOSTA_COMPRADA), SUM(t1.VR_APOSTA), t2.NU_MEIO_PAGAMENTO, "
				+ "t1.NU_MODALIDADE_JOGO, t0.NU_CONCURSO_INICIAL, SUM(t0.VR_COMISSAO) "
				+ "FROM LCE.LCETB009_APOSTA_COMPRADA t0 "
				+ "INNER JOIN LCE.LCETB011_APOSTA t1 ON t0.NU_APOSTA = t1.NU_APOSTA "
				+ "INNER JOIN LCE.LCETB013_COMPRA t2 ON t1.NU_COMPRA = t2.NU_COMPRA "
				+ "WHERE (t0.TS_ALTERACAO_SITUACAO >= ?1 AND "
				+ "t0.TS_ALTERACAO_SITUACAO <= ?2 AND "
				+ "t0.NU_MES IN (?3, ?4, ?5, ?6, ?7, ?8) AND "
				+ "t1.NU_MES IN (?3, ?4, ?5, ?6, ?7, ?8) AND "
				+ "t2.NU_MES IN (?3, ?4, ?5, ?6, ?7, ?8) AND "
				+ "t0.IC_BILHETE_TROCA = 'N' AND "
				+ "t1.IC_COTA_BOLAO = 'N' AND "
				+ "t0.NU_SITUACAO_APOSTA IN (");

		Object[] parametros = new Object[situacoes.length + 8];

		List<Integer> meses = DataUtil.gereListaMeses(nuMes, 5);

		int cont = 0;
		parametros[cont] = DataUtil.dataToString(dataInicial, TIMESTAMP_DB2_FORMAT);
		cont++;
		parametros[cont] = DataUtil.dataToString(dataFinal, TIMESTAMP_DB2_FORMAT);

		for (Integer mes : meses) {
			cont++;
			parametros[cont] = mes;
		}

		for (Situacao situacao : situacoes) {
			cont++;
			query.append("?" + (cont + 1) + ",");
			parametros[cont] = situacao.getValue();
		}
		query.delete(query.length() - 1, query.length());

		query.append(")) GROUP BY t2.NU_MEIO_PAGAMENTO, t1.NU_MODALIDADE_JOGO, t0.NU_CONCURSO_INICIAL");

		return getResultListByNativeQuery(query.toString(), parametros);
	}

	@Override
	public List<Object[]> findCountSumValorByDataEfetivacaoGroupedByMeioPagamento(Data dataEfetivacao, boolean pesquisaComSubcanal, Situacao... situacoes) {
		int nuMes = dataEfetivacao.getMonth();
		StringBuilder query = new StringBuilder(1000);
		query.append("SELECT COUNT(t0.NU_APOSTA_COMPRADA), SUM(t1.VR_APOSTA), "
			+ "t2.NU_MEIO_PAGAMENTO, t1.NU_MODALIDADE_JOGO, t0.NU_CONCURSO_INICIAL, "
			+ "SUM(t0.VR_COMISSAO)");

		if (pesquisaComSubcanal) {
			query.append(", t0.IC_SUBCANAL_PAGAMENTO");
		}

		query.append(" FROM LCE.LCETB009_APOSTA_COMPRADA t0 "
			+ "INNER JOIN LCE.LCETB011_APOSTA t1 ON t0.NU_APOSTA = t1.NU_APOSTA "
			+ "INNER JOIN LCE.LCETB013_COMPRA t2 ON t1.NU_COMPRA = t2.NU_COMPRA "
			+ "WHERE (t0.DT_EFTCO_APOSTA_SISPL = ?1 AND "
			+ "t0.IC_BILHETE_TROCA = 'N' AND "
			+ "t1.IC_COTA_BOLAO = 'N' AND "
			+ "t0.NU_MES IN (?2, ?3) AND "
			+ "t1.NU_MES IN (?2, ?3) AND "
			+ "t2.NU_MES IN (?2, ?3) AND "
			+ "t0.NU_SITUACAO_APOSTA IN (");

		Object[] parametros = new Object[situacoes.length + 3];

		List<Integer> meses = DataUtil.gereListaMeses(nuMes, 1);

		int cont = 0;
		parametros[cont] = DataUtil.dataToString(dataEfetivacao, DATA_DB2_FORMAT);

		for (Integer mes : meses) {
			cont++;
			parametros[cont] = mes;
		}

		for (Situacao situacao : situacoes) {
			cont++;
			query.append("?" + (cont + 1) + ",");
			parametros[cont] = situacao.getValue();
		}
		query.delete(query.length() - 1, query.length());

		query.append(")) GROUP BY t2.NU_MEIO_PAGAMENTO, t1.NU_MODALIDADE_JOGO, t0.NU_CONCURSO_INICIAL");
		if (pesquisaComSubcanal) {
			query.append(", t0.IC_SUBCANAL_PAGAMENTO");
		}

		return getResultListByNativeQuery(query.toString(), parametros);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DadosResumoCotaLoterica> findCountSumCotasByDataEfetivacaoSituacaoLoterica(Data dataEfetivacao, SituacaoReservaCotaBolao.Situacao situacao, Integer codLoterica) {

		int nuMes = dataEfetivacao.getMonth();
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(t0.NU_APOSTA_COMPRADA) AS quantidade, SUM(t3.VR_COTA_RESERVADA) AS valorCota, SUM(t3.VR_TARIFA_SERVICO) AS valorTarifaCota, "
			+ "SUM(t3.VR_CUSTEIO_COTA) AS valorCusteioCota, SUM(t3.VR_TARIFA_CUSTEIO_COTA) AS valorTarifaCusteioCota, "
			+ "t2.NU_MEIO_PAGAMENTO AS meioPagamento, t1.NU_MODALIDADE_JOGO AS modalidade, t0.NU_CONCURSO_INICIAL AS concurso, "
			+ "SUM(t0.VR_COMISSAO) AS valorComissao, t0.IC_SUBCANAL_PAGAMENTO AS subcanalDetalhamento "
			+ "FROM LCE.LCETB009_APOSTA_COMPRADA t0 "
			+ "INNER JOIN LCE.LCETB011_APOSTA t1 ON t0.NU_APOSTA = t1.NU_APOSTA "
			+ "		AND t1.NU_MES <= ?1 "
			+ "INNER JOIN LCE.LCETB013_COMPRA t2 ON t1.NU_COMPRA = t2.NU_COMPRA "
			+ "		AND t2.NU_MES <= ?1 "
			+ "INNER JOIN LCE.LCETB059_RESERVA_COTA_BOLAO t3 ON t3.NU_RESERVA_COTA_BOLAO = t1.NU_RESERVA_COTA_BOLAO "
			+ " 	AND t3.MM_RESERVA_COTA_BOLAO = t1.MM_RESERVA_COTA_BOLAO AND t3.NU_PARTICAO = t1.NU_PARTICAO_COTA_BOLAO "
			+ "		AND t3.MM_RESERVA_COTA_BOLAO <= ?1 AND t3.NU_SITUACAO_RESERVA_COTA_BOLAO = ?2 "
			+ "		AND t3.NU_CANAL_DISTRIBUICAO = ?3 "
			+ "WHERE t0.NU_MES <= ?1 AND t0.DT_FINALIZACAO_PROCESSAMENTO = ?4 "
			+ "GROUP BY t2.NU_MEIO_PAGAMENTO, t1.NU_MODALIDADE_JOGO, t0.NU_CONCURSO_INICIAL, t0.IC_SUBCANAL_PAGAMENTO ");

		Object[] parametros = new Object[4];

		parametros[0] = nuMes;
		parametros[1] = situacao.getValue();
		parametros[2] = codLoterica;
		parametros[3] = DataUtil.dataToString(dataEfetivacao, DATA_DB2_FORMAT);

		return getResultListByNativeQuery(query.toString(), DadosResumoCotaLoterica.class, parametros);
	}

	@Override
	public ResultadoPesquisaPaginada<ApostaComprada> findByDataSituacoes(int firstResult, int tamanhoPagina, Data dataSituacao, Meio meio, Situacao... situacoes) {
		List<Long> idsSituacoes = convertToIdList(situacoes);
		Data dataInicial = new Data(dataSituacao).zereHoraMinutoSegundoMilisegundo();
		Data dataFinal = DataUtil.getUltimoTimestampDoDia(dataInicial);
		return getResultListByNamedQuery(firstResult, tamanhoPagina, ApostaComprada.NQ_SELECT_BY_DATA_ALTERACAO_MEIO_PAGAMENTO_SITUACAO, dataInicial, dataFinal, idsSituacoes,
			meio.getValue());
	}

	@Override
	public ResultadoPesquisaPaginada<ApostaComprada> findByDataSituacoesSemCota(int firstResult, int tamanhoPagina, Data dataSituacao, Meio meio, Situacao... situacoes) {
		List<Long> idsSituacoes = convertToIdList(situacoes);
		Data dataInicial = new Data(dataSituacao).zereHoraMinutoSegundoMilisegundo();
		Data dataFinal = DataUtil.getUltimoTimestampDoDia(dataInicial);
		return getResultListByNamedQuery(firstResult, tamanhoPagina, ApostaComprada.NQ_SELECT_BY_DATA_ALTERACAO_MEIO_PAGAMENTO_SITUACAO_SEM_COTA, dataInicial, dataFinal,
			idsSituacoes,
			meio.getValue());
	}

	Decimal converteSomaDecimal(Object valor) {
		if (valor == null) {
			return Decimal.ZERO;
		} else if (valor instanceof Integer) {
			return new Decimal((Integer) valor);
		} else if (valor instanceof BigDecimal) {
			return new Decimal((BigDecimal) valor);
		}
		return (Decimal) valor;
	}

	@Override
	public List<Decimal> findByCompraSituacao(Long idCompra, Data dataFinalizacaoProcessamento, Situacao... situacoes) {
		List<Decimal> valores = new ArrayList<Decimal>();
		List<Long> idsSituacoes = convertToIdList(situacoes);
		Object[] valoresnConvertidos = getSingleResultByNamedQuery(ApostaComprada.NQ_SUMARIZA_BY_SITUACAO_COMPRA_FINALIZACAOPROCESSAMENTO, idCompra,
			dataFinalizacaoProcessamento, idsSituacoes);
		
		valores.add(converteSomaDecimal(valoresnConvertidos[0]));
		valores.add(converteSomaDecimal(valoresnConvertidos[1]));

		return valores;
	}

	@Override
	public List<Object[]> findCountSumValorByDataEfetivacaoGroupedByMeioPagamentoModalidade(Data dataEfetivacao, Modalidade modalidade) {
		StringBuilder query = new StringBuilder(1000);
		query.append("SELECT COUNT(DISTINCT t0.NU_APOSTA_COMPRADA), SUM(t1.VR_APOSTA), "
				+ "t2.NU_MEIO_PAGAMENTO, t1.NU_MODALIDADE_JOGO, t0.NU_CONCURSO_INICIAL, "
				+ "SUM(t0.VR_COMISSAO) FROM LCE.LCETB009_APOSTA_COMPRADA t0 "
				+ "INNER JOIN LCE.LCETB011_APOSTA t1 ON t0.NU_APOSTA = t1.NU_APOSTA "
				+ "INNER JOIN LCE.LCETB013_COMPRA t2 ON t1.NU_COMPRA = t2.NU_COMPRA "
			+ "WHERE (t0.DT_EFTCO_APOSTA_SISPL = ?1 AND t0.IC_BILHETE_TROCA = 'N' AND t1.NU_MODALIDADE_JOGO = " + modalidade.getCodigo()
			+ ") GROUP BY t1.NU_MODALIDADE_JOGO, t2.NU_MEIO_PAGAMENTO, t0.NU_CONCURSO_INICIAL");
		Object[] parametros = new Object[2];

		int cont = 0;
		parametros[cont] = DataUtil.dataToString(dataEfetivacao, DATA_DB2_FORMAT);
		return getResultListByNativeQuery(query.toString(), parametros);
	}

	@Override
	public long countTeimosinha(Long id) {
		return execCountForNamedQuery(ApostaComprada.NQ_SELECT_BY_NU_APOSTA_ORIGEM, id);
	}

	@Override
	public long countApostasByCompra(Long idCompra) {
		return execCountForNamedQuery(ApostaComprada.NQ_SELECT_COUNT_BY_ID_COMPRA, idCompra);
	}

	@Override
	public List<Object[]> recupereApostasPremiosNaoAutorizados(Modalidade modalidade, CPF cpfApostador, Data dataInicio, Data dataFim, Long numeroCompra) {

		Object[] parametros = new Object[10];
		StringBuilder query = new StringBuilder();
		query.append(
			"SELECT DISTINCT lc.DT_FINALIZACAO_COMPRA, lc.HH_FINALIZACAO_COMPRA , la.NU_MODALIDADE_JOGO, la.VR_APOSTA, AC.NU_TRANSACAO_APOSTA, AC.NU_APOSTA, AC.NU_SITUACAO_APOSTA, AC.NU_APOSTA_COMPRADA, P.NU_PREMIO, P.NU_SITUACAO_PREMIO, lc.NU_COMPRA, lc.NU_SITUACAO_COMPRA  ");
		query.append("FROM LCE.LCETB009_APOSTA_COMPRADA AC ");
		query.append("INNER JOIN LCE.LCETB015_PREMIO P ON P.NU_PREMIO = AC.NU_PREMIO ");
		query.append("	AND P.NU_SITUACAO_PREMIO = ?1 ");
		query.append(" AND P.IC_SUBCANAL_RESGATE IN (" + Subcanal.SILCE.getCodigo() + ", " + Subcanal.SIMLO_ANDROID.getCodigo() + ", " + Subcanal.SIMLO_IOS.getCodigo() + ")");
		query.append("INNER JOIN LCE. LCEVW014_APOSTA_PREMIADA SPL1 ON AC.NU_TRANSACAO_APOSTA = SPL1.NU_NSU_SILCE ");
		query.append("INNER JOIN LCE.LCETB011_APOSTA la ON AC.NU_APOSTA = la.NU_APOSTA ");
		query.append("INNER JOIN LCE.LCETB013_COMPRA lc ON la.NU_COMPRA = lc.NU_COMPRA ");
		query.append("INNER JOIN LCE.LCETB002_APOSTADOR lar ON lc.NU_APOSTADOR = lar.NU_APOSTADOR ");
		query.append("	AND SPL1.NU_ORIGEM = ?2 AND SPL1.NU_SITUACAO = ?3 ");
		query.append("WHERE AC.NU_SITUACAO_APOSTA = ?4 ");
		query.append("	AND lc.DT_FINALIZACAO_COMPRA BETWEEN ?5 AND ?6 ");
		if (cpfApostador != null) {
			query.append("AND lar.NU_CPF = ?7 ");
		}

		if (modalidade != null) {
			query.append("AND la.NU_MODALIDADE_JOGO = ?8");
		}

		if (numeroCompra != null) {
			query.append(" AND lc.NU_COMPRA = ?10");
		}

		query.append("UNION ALL ");
		query.append(
			"SELECT DISTINCT lc.DT_FINALIZACAO_COMPRA, lc.HH_FINALIZACAO_COMPRA , la.NU_MODALIDADE_JOGO, la.VR_APOSTA, AC.NU_TRANSACAO_APOSTA, AC.NU_APOSTA, AC.NU_SITUACAO_APOSTA, AC.NU_APOSTA_COMPRADA, P.NU_PREMIO, P.NU_SITUACAO_PREMIO, lc.NU_COMPRA, lc.NU_SITUACAO_COMPRA  ");
		query.append("FROM LCE.LCETB009_APOSTA_COMPRADA AC ");
		query.append("INNER JOIN LCE.LCETB015_PREMIO P ON P.NU_PREMIO = AC.NU_PREMIO ");
		query.append("	AND P.NU_SITUACAO_PREMIO = ?1 ");
		query.append(" AND P.IC_SUBCANAL_RESGATE IN (" + Subcanal.SILCE.getCodigo() + ", " + Subcanal.SIMLO_IOS.getCodigo() + ", " + Subcanal.SIMLO_ANDROID.getCodigo() + ")");
		query.append("INNER JOIN LCE.LCEVW706_COMPROVANTE_APOSTA T706 ON AC.NU_SERIE_BILHETE = T706.NU_NSB_CANAL_APOSTA ");
		query.append("INNER JOIN LCE.LCEVW714_APOSTA_PREMIADA T714 ON T706.NU_NSU_COMPROVANTE = T714.NU_NSU_COMPROVANTE ");
		query.append("	AND T706.NU_CDC_COMPROVANTE = T714.NU_CDC_COMPROVANTE ");
		query.append("	AND T714.NU_SITUACAO_APOSTA_PREMIADA = ?9 ");
		query.append("INNER JOIN LCE.LCETB011_APOSTA la ON AC.NU_APOSTA = la.NU_APOSTA ");
		query.append("INNER JOIN LCE.LCETB013_COMPRA lc ON la.NU_COMPRA = lc.NU_COMPRA ");
		query.append("INNER JOIN LCE.LCETB002_APOSTADOR lar ON lc.NU_APOSTADOR = lar.NU_APOSTADOR ");
		query.append("WHERE AC.NU_SITUACAO_APOSTA = ?4 ");
		query.append("	AND lc.DT_FINALIZACAO_COMPRA BETWEEN ?5 AND ?6 ");
		if (cpfApostador != null) {
			query.append("AND lar.NU_CPF = ?7 ");
		}

		if (modalidade != null) {
			query.append("AND la.NU_MODALIDADE_JOGO = ?8");
		}

		if (numeroCompra != null) {
			query.append(" AND lc.NU_COMPRA = ?10");
		}


		String dataInicial = DataUtil.dataToString(dataInicio, DATA_DB2_FORMAT);
		String dataFinal = DataUtil.dataToString(dataFim, DATA_DB2_FORMAT);

		parametros[0] = SituacaoPremio.Situacao.NAO_AUTORIZADO.getValue();
		parametros[1] = Canal.SILCE.getValue();
		parametros[2] = PAGAMENTO_PROCESSAMENTO_SISPL1;
		parametros[3] = SituacaoAposta.Situacao.EFETIVADA.getValue();
		parametros[4] = dataInicial;
		parametros[5] = dataFinal;

		if (cpfApostador != null) {
			parametros[6] = cpfApostador.getCpfSemMascara();
		}

		if (modalidade != null) {
			parametros[7] = modalidade.getCodigo();
		}

		parametros[8] = PAGAMENTO_AUTORIZADO_SISPL2;

		if (numeroCompra != null) {
			parametros[9] = numeroCompra;
		}

		return getResultListByNativeQuery(query.toString(), parametros);
	}

	@Override
	public List<Object[]> findByConcursoModalidadeSituacaoCompraWithUR(Integer concurso, Integer modalidade, Boolean comprasPix) {

		Object[] parametros = new Object[2];
		parametros[0] = concurso;
		parametros[1] = Modalidade.getByCodigo(modalidade).getCodigo();

		StringBuilder query = new StringBuilder(
			"SELECT t0.NU_APOSTA_COMPRADA, t1.NU_APOSTA, t3.NU_COMPRA, "
				+ "t0.NU_TRANSACAO_APOSTA, t4.DE_MEIO_PAGAMENTO_APOSTA, "
				+ "t3.NU_TRANSACAO_DEBITO, t0.DT_INICIO_APOSTA_COMPRADA, "
				+ "t0.HH_INICIO_APOSTA_COMPRADA, t3.DT_EXPIRACAO_PAGAMENTO, "
				+ "t3.HH_EXPIRACAO_PAGAMENTO, t0.DT_ENVIO_APOSTA_SISPL, "
				+ "t0.HH_ENVIO_APOSTA_SISPL, t0.DT_EFTCO_APOSTA_SISPL, "
				+ "t0.HH_EFTCO_APOSTA_SISPL, t3.NU_SITUACAO_COMPRA, t5.DE_SITUACAO_COMPRA, "
				+ "t0.NU_SITUACAO_APOSTA, t6.DE_SITUACAO_APOSTA, t0.TS_ALTERACAO_SITUACAO "
				+ "FROM LCE.LCETB009_APOSTA_COMPRADA t0 "
				+ "INNER JOIN LCE.LCETB011_APOSTA t1 ON t0.NU_APOSTA = t1.NU_APOSTA "
				+ "LEFT OUTER JOIN LCE.LCETB009_APOSTA_COMPRADA t2 ON t1.NU_APOSTA = t2.NU_APOSTA "
				+ "INNER JOIN LCE.LCETB013_COMPRA t3 ON t1.NU_COMPRA = t3.NU_COMPRA "
				+ "LEFT OUTER JOIN LCE.LCETB018_MEIO_PAGAMENTO t4 ON t3.NU_MEIO_PAGAMENTO = t4.NU_MEIO_PAGAMENTO "
				+ "INNER JOIN LCE.LCETB012_SITUACAO_COMPRA t5 ON t5.NU_SITUACAO_COMPRA = t3.NU_SITUACAO_COMPRA "
				+ "INNER JOIN LCE.LCETB008_SITUACAO_APOSTA t6 ON t6.NU_SITUACAO_APOSTA = t0.NU_SITUACAO_APOSTA "
				+ "WHERE t0.NU_CONCURSO_INICIAL = ?1 "
				+ "AND t1.NU_MODALIDADE_JOGO = ?2 ");

		// Tem q ser passado os valores fixos por questoes de desempenho do db2.
		if (comprasPix) {
			query.append("AND t3.NU_SITUACAO_COMPRA = 10 "
				+ "AND t0.NU_SITUACAO_APOSTA IN (1, 2, 3) ");
		} else {
			query.append("AND t3.NU_SITUACAO_COMPRA <> 10 "
				+ "AND t0.NU_SITUACAO_APOSTA IN (2, 3) ");
		}

		query.append("FETCH FIRST 50 ROWS ONLY WITH UR ");

		return getResultListByNativeQuery(query.toString(), parametros);
	}

	@Override
	public List<Object[]> findByConcursoModalidadePremiosPendentesWithUR(Integer concurso, Integer modalidade) {

		Object[] parametros = new Object[2];
		parametros[0] = Modalidade.getByCodigo(modalidade).getCodigo();
		parametros[1] = concurso;

		StringBuilder query = new StringBuilder(
			"SELECT t0.NU_APOSTA_COMPRADA, t1.NU_APOSTA, t7.NU_PREMIO, t3.NU_COMPRA, "
				+ "t4.DE_MEIO_PAGAMENTO_APOSTA, t7.NU_CANAL_PAGAMENTO, t7.NU_MEIO_PAGAMENTO, "
				+ "t3.NU_TRANSACAO_DEBITO, t7.NU_TRANSACAO_PAGAMENTO, t7.DE_PARAMETRO_PAGAMENTO, "
				+ "t0.DT_INICIO_APOSTA_COMPRADA, t0.HH_INICIO_APOSTA_COMPRADA, t0.DT_ENVIO_APOSTA_SISPL, "
				+ "t0.HH_ENVIO_APOSTA_SISPL, t0.DT_EFTCO_APOSTA_SISPL, t0.HH_EFTCO_APOSTA_SISPL, "
				+ "t7.DT_INICIO_PAGAMENTO_SILCE, t7.HH_INICIO_PAGAMENTO_SILCE, t7.DT_PAGAMENTO_PREMIO_SISPL, "
				+ "t7.HH_PAGAMENTO_PREMIO_SISPL, t7.DT_EFTCO_CRDTO_MEIO_PAGAMENTO, "
				+ "t3.NU_SITUACAO_COMPRA, t5.DE_SITUACAO_COMPRA, t0.NU_SITUACAO_APOSTA, t6.DE_SITUACAO_APOSTA, "
				+ "t0.TS_ALTERACAO_SITUACAO, t7.NU_SITUACAO_PREMIO, t8.DE_SITUACAO_PREMIO, t7.TS_ALTERACAO_SITUACAO "
				+ "FROM LCE.LCETB009_APOSTA_COMPRADA t0 "
				+ "INNER JOIN LCE.LCETB011_APOSTA t1 ON t0.NU_APOSTA = t1.NU_APOSTA "
				+ "LEFT OUTER JOIN LCE.LCETB009_APOSTA_COMPRADA t2 ON t1.NU_APOSTA = t2.NU_APOSTA "
				+ "INNER JOIN LCE.LCETB013_COMPRA t3 ON t1.NU_COMPRA = t3.NU_COMPRA "
				+ "LEFT OUTER JOIN LCE.LCETB018_MEIO_PAGAMENTO t4 ON t3.NU_MEIO_PAGAMENTO = t4.NU_MEIO_PAGAMENTO "
				+ "INNER JOIN LCE.LCETB012_SITUACAO_COMPRA t5 ON t5.NU_SITUACAO_COMPRA = t3.NU_SITUACAO_COMPRA "
				+ "INNER JOIN LCE.LCETB008_SITUACAO_APOSTA t6 ON t6.NU_SITUACAO_APOSTA = t0.NU_SITUACAO_APOSTA "
				+ "INNER JOIN LCE.LCETB015_PREMIO t7 ON t7.NU_PREMIO = t0.NU_PREMIO "
				+ "INNER JOIN LCE.LCETB014_SITUACAO_PREMIO t8 ON t8.NU_SITUACAO_PREMIO = t7.NU_SITUACAO_PREMIO "
				+ "WHERE t1.QT_TEIMOSINHA > 1 "
				+ "AND t1.NU_MODALIDADE_JOGO = ?1 "
				+ "AND (t0.NU_CONCURSO_INICIAL + t1.QT_TEIMOSINHA > ?2) "

				// Tem q ser passado os valores fixos por questoes de desempenho do db2.
				+ "AND t7.NU_SITUACAO_PREMIO IN (1, 2, 3, 6) "
				+ "FETCH FIRST 50 ROWS ONLY "
				+ "WITH UR;");

		return getResultListByNativeQuery(query.toString(), parametros);
	}

	@Override
	public List<ApostaComprada> findByCompraWithUR(Long numeroCompra) {
		Data dataCorteAlteracaoSituacao = getDataCorteConsultas();
		return getResultListByNamedQuery(ApostaComprada.NQ_SELECT_BY_COMPRA_WITH_UR, numeroCompra, Situacao.NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO.getValue(),
			dataCorteAlteracaoSituacao, Boolean.FALSE);
	}

	private Data getDataCorteConsultas() {
		Data dataCorte = DataUtil.getTimestampAtual();
		dataCorte.subtract(Calendar.SECOND, LIMIAR_TIMEOUT_SEGUNDOS);
		return dataCorte;
	}
}
