package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra.Situacao;
import br.gov.caixa.silce.negocio.infra.dao.AbstractSilceDAO;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;

@Stateless
public class CompraDAO extends AbstractSilceDAO<Compra, Long> implements CompraDAOLocal {

	private static final long serialVersionUID = 1L;

	@Override
	public Compra findByApostador(Long idApostador, Situacao situacaoCompra) {
		return getSingleResultByNamedQuery(Compra.NQ_SELECT_BY_APOSTADOR_SITUACAO, idApostador, situacaoCompra.getValue());
	}

	@Override
	public Long findByApostadorAndSituacao(Long idApostador, Situacao... situacoes) {
		List<Long> ids = convertToIdList(situacoes);
		return getSingleResultByNamedQuery(Compra.NQ_SELECT_COUNT_COMPRAS_EM_PROCESSAMENTO, idApostador, ids);
	}

	@Override
	public ResultadoPesquisaPaginada<Compra> findByCompraPeriodo(Long idApostador, Data dataInicio, Data dataFim, int first, int pageSize) {
		return getResultListByNamedQuery(first, pageSize, Compra.NQ_SELECT_BY_APOSTADOR_DATAINICIOCOMPRAMAXMIX, idApostador, dataInicio, dataFim);
	}

	@Override
	public ResultadoPesquisaPaginada<Compra> findByCompraPeriodoMeioPagamento(Long idApostador, Data dataInicio, Data dataFim, Meio meioPagamento, int first, int pageSize) {
		return getResultListByNamedQuery(first, pageSize, Compra.NQ_SELECT_BY_APOSTADOR_DATAINICIOCOMPRAMAXMIX_MEIO, idApostador, dataInicio, dataFim, meioPagamento.getValue());
	}

	@Override
	public ResultadoPesquisaPaginada<Compra> findByCompraPeriodoSituacao(Long idApostador, Data dataInicio, Data dataFim, Situacao situacao, int first, int pageSize) {
		return getResultListByNamedQuery(first, pageSize, Compra.NQ_SELECT_BY_APOSTADOR_DATAINICIOCOMPRAMAXMIX_SITUACAO, idApostador, dataInicio, dataFim, situacao.getValue());
	}

	@Override
	public ResultadoPesquisaPaginada<Compra> findByCompraPeriodoMeioPagamentoSituacao(Long idApostador, Data dataInicio, Data dataFim, Meio meioPagamento, Situacao situacao,
		int first, int pageSize) {
		return getResultListByNamedQuery(first, pageSize, Compra.NQ_SELECT_BY_APOSTADOR_DATAINICIOCOMPRAMAXMIX_MEIO_SITUACAO, idApostador, dataInicio, dataFim,
			meioPagamento.getValue(), situacao.getValue());
	}

	@Override
	public List<Compra> findBySituacao(Situacao situacao) {
		return getResultListByNamedQuery(Compra.NQ_SELECT_BY_SITUACAO, situacao.getValue());
	}

	@Override
	public List<Long> findBySituacaoWithUR(Situacao situacao) {
		return getResultListByNamedQuery(Compra.NQ_SELECT_IDS_BY_SITUACAO_WITH_UR, situacao.getValue());
	}

	@Override
	public List<Object[]> findBySituacoesDebitoIniciadoWithUR(Data data) {
		String stringDataFormatada = DataUtil.dataToString(data, TIMESTAMP_DB2_FORMAT);
		return getResultListByNamedQuery(Compra.NQ_SELECT_BY_SITUACAO_DATA_ULTIMA_ALTERACAO_WITH_UR,
			SituacaoCompra.Situacao.DEBITO_INICIADO.getValue(), stringDataFormatada);
	}

	@Override
	public List<Object[]> findBySituacoesAguardandoPagamentoPixWithUR(Data data) {
		String stringDataFormatada = DataUtil.dataToString(data, TIMESTAMP_DB2_FORMAT);
		return getResultListByNamedQuery(Compra.NQ_SELECT_BY_SITUACOES_AGUARDANDO_PAGAMENTO_PIX_WITH_UR,
			SituacaoCompra.Situacao.AGUARDANDO_PAGAMENTO_PIX.getValue(), stringDataFormatada);
	}

	@Override
	public List<Object[]> findBySituacaoDataUltimaAlteracaoWithUR(Data data) {
		String stringDataFormatada = DataUtil.dataToString(data, TIMESTAMP_DB2_FORMAT);
		return getResultListByNamedQuery(Compra.NQ_SELECT_BY_SITUACAO_DATA_ULTIMA_ALTERACAO_WITH_UR, SituacaoCompra.Situacao.DEBITO_REALIZADO.getValue(), stringDataFormatada);
	}

	@Override
	public ResultadoPesquisaPaginada<Compra> findByApostadorNumeroData(Long idApostador, Long numeroCompra, Long nsuSilce, Data data, Meio meiosDePagamento,
		int first, int pageSize) {
		StringBuilder jpql = new StringBuilder(selectQuery);
		jpql.append(
			" join fetch entidade.meioPagamento where entidade.dataInicioCompra is not null order by entidade.dataInicioCompra desc, entidade.horaInicioCompra desc");

		Map<String, Object> parametros = new LinkedHashMap<String, Object>();
		parametros.put("and entidade.apostador.id =", idApostador);
		parametros.put("and entidade.id =", numeroCompra);
		parametros.put("and entidade.nsu =", nsuSilce);
		parametros.put("and entidade.dataInicioCompra =", data);

		if (meiosDePagamento != null) {
			parametros.put("and entidade.meioPagamento.id =", meiosDePagamento.getValue());
		}

		return getResultListByQuery(first, pageSize, jpql.toString(), newForceFetchMap(Compra.class, "apostador"), parametros, getHintsMapWithUR());
	}

	@Override
	public ResultadoPesquisaPaginada<Compra> findByDataInicioIgualDataFinalizacaoMaior(Data dataInicio, Data dataFim, int first, int pageSize) {
		return getResultListByNamedQuery(first, pageSize, Compra.NQ_SELECT_BY_DATAINICIO_DATAFINALIZACAOMIN, dataInicio, dataFim,
				Situacao.CARRINHO.getValue());
	}

	@Override
	public Compra findByNsuData(Long nsu, Data dataInicioCompra) {
		return getSingleResultByNamedQuery(Compra.NQ_SELECT_BY_NSU_DATA, nsu, dataInicioCompra);
	}

	@Override
	public Compra findByNsuUltimaData(Long nsu, Data dataInicioCompra) {
		return getSingleResultByNamedQuery(Compra.NQ_SELECT_BY_NSU_ULTIMA_DATA, nsu, dataInicioCompra);
	}

	/**
	 * para fazer em uma query a soma das compras efetivadas (que tem por referencia a data de efetivacao) e as
	 * estornadas (que tem por referencia a data de finalizacao), precisei do método específico.
	 */
	@Override
	public List<Object[]> findCountSumValorGroupedByMeioPagamento(Data data) {
		Data dataInicial = new Data(data).zereHoraMinutoSegundoMilisegundo();
		int nuMes = data.getMonth();
		StringBuilder query = new StringBuilder(
			"SELECT COUNT(t0.NU_COMPRA), SUM(t0.VR_TOTAL), t0.NU_MEIO_PAGAMENTO, SUM(t0.VR_COMISSAO) FROM LCE.LCETB013_COMPRA t0 "
				+ "WHERE t0.DT_EFETIVACAO_DEBITO = ?1 AND "
				+ "t0.NU_MES IN (?2, ?3) AND "
				+ "t0.NU_MEIO_PAGAMENTO IS NOT NULL AND "
				+ "t0.NU_SITUACAO_COMPRA IN (?4, ?5, ?6, ?7, ?8, ?9) GROUP BY t0.NU_MEIO_PAGAMENTO");

		Object[] parametros = new Object[Situacao.getSituacoesEfetivadaParaFechamento().length + 3];
		List<Integer> meses = DataUtil.gereListaMeses(nuMes, 1);
		int cont = 0;
		String dataToString = DataUtil.dataToString(dataInicial, DATA_DB2_FORMAT);

		parametros[cont] = dataToString;
		cont++;

		for (Integer mes : meses) {
			parametros[cont] = mes;
			cont++;
		}

		for (Situacao situacao : Situacao.getSituacoesEfetivadaParaFechamento()) {
			parametros[cont] = situacao.getValue();
			cont++;
		}

		return getResultListByNativeQuery(query.toString(), parametros);
	}

	/**
	 *
	 */
	@Override
	public List<Object[]> findCountSumValorEstornosGroupedByMeioPagamento(Data data) {
		Data dataInicial = new Data(data).zereHoraMinutoSegundoMilisegundo();
		int nuMes = data.getMonth();

		StringBuilder query = new StringBuilder(
			"SELECT COUNT(t0.NU_COMPRA), SUM(t0.VR_TOTAL), t0.NU_MEIO_PAGAMENTO, SUM(t0.VR_COMISSAO) FROM LCE.LCETB013_COMPRA t0 "
				+ "WHERE t0.DT_FINALIZACAO_COMPRA = ?1 AND t0.NU_MES IN (?2, ?3, ?4, ?5, ?6, ?7) AND t0.NU_SITUACAO_COMPRA = ?8 GROUP BY t0.NU_MEIO_PAGAMENTO");

		Object[] parametros = new Object[8];

		int cont = 0;

		String dataToString = DataUtil.dataToString(dataInicial, DATA_DB2_FORMAT);

		List<Integer> meses = DataUtil.gereListaMeses(nuMes, 5);

		parametros[cont] = dataToString;
		cont++;

		for (Integer mes : meses) {
			parametros[cont] = mes;
			cont++;
		}

		parametros[cont] = Situacao.ESTORNADA.getValue();
		cont++;

		return getResultListByNativeQuery(query.toString(), parametros);
	}

	@Override
	public ResultadoPesquisaPaginada<Compra> findByDataDebitoMeioPagamentoSituacoes(int firstResult, int tamanhoPagina, Data dataDebito, Meio meio, Situacao... situacoes) {
		List<Long> ids = convertToIdList(situacoes);
		return getResultListByNamedQuery(firstResult, tamanhoPagina, Compra.NQ_SELECT_BY_DATA_DEBITO_MEIO_PAGAMENTO_SITUACOES, dataDebito, meio.getValue(), ids);
	}

	@Override
	public ResultadoPesquisaPaginada<Compra> findByDataFinalizacaoMeioPagamentoSituacoes(int firstResult, int tamanhoPagina, Data dataAtualizacao, Meio meio,
		Situacao... situacoes) {
		List<Long> ids = convertToIdList(situacoes);
		return getResultListByNamedQuery(firstResult, tamanhoPagina, Compra.NQ_SELECT_BY_DATA_FINALIZACAO_MEIO_PAGAMENTO_SITUACOES, dataAtualizacao, meio.getValue(), ids);
	}

	@Override
	public Data findDataMinFinalizada() {
		return getSingleResultByNamedQuery(Compra.NQ_SELECT_DATA_FINALIZACAO_MIN_BY_SITUACAO);
	}

	@Override
	public Compra findByCompraApostador(Long idCompra, Long idApostador) {
		return getSingleResultByNamedQuery(Compra.NQ_SELECT_BY_COMPRA_APOSTADOR, idCompra, idApostador);
	}

	@Override
	public Compra findByIdComLoterica(Long idCompra) {
		return getSingleResultByNamedQuery(Compra.NQ_SELECT_BY_COMPRA_COM_LOTERICA_APOSTADOR, idCompra);
	}

	@Override
	public ResultadoPesquisaPaginada<Long> findEmProcessamentoByDataUltimaAlteracaoSituacaoWithUR(Data dataCorte, int maxResults) {
		return getResultListByNamedQuery(0, maxResults, false, Compra.NQ_SELECT_ID_BY_EM_PROCESSAMENTO_DATAULTIMA_ALTERACAO_MAX_WITH_UR, Situacao.EM_PROCESSAMENTO.getValue(),
			dataCorte);
	}

	@Override
	public List<Object[]> findSumarizacaoPorSituacao(Long idApostador, Data dataCorte) {
		return getResultListByNamedQuery(Compra.NQ_SELECT_SUMARIZACAO_BY_APOSTADOR_DATA, idApostador, dataCorte.getTime());
	}

	@Override
	public Decimal findMediaComprasPorDia(Long idApostador, Data dataCorte) {
		Decimal retorno = getSingleResultByNamedQuery(Compra.NQ_SELECT_MEDIA_DIARIA_BY_APOSTADOR_DATA, idApostador, dataCorte.getTime());
		if (retorno == null) {
			return Decimal.ZERO;
		}
		return retorno;
	}

	@Override
	public Compra findByNsuAposta(Long nsu, Data data) {
		return getSingleResultByNamedQuery(Compra.NQ_SELECT_BY_APOSTA_NSU, nsu, data);
	}

	public Compra findUltimaCompra(Long idApostador) {
		return getSingleResultByNamedQuery(Compra.NQ_SELECT_BY_ULTIMA_COMPRA, idApostador, SituacaoCompra.Situacao.FINALIZADA.getValue(),
			SituacaoCompra.Situacao.FINALIZADA_CONTEM_APOSTAS_NAO_EFETIVADAS.getValue(), SituacaoCompra.Situacao.FINALIZADA_TODAS_APOSTAS_EFETIVADAS.getValue());
	}

	public List<Compra> findUltimasComprasDiaAnterior(Long idApostador, Data data24h) {
		return getResultListByNamedQuery(Compra.NQ_SELECT_BY_COMPRAS_ULTIMO_DIA, idApostador, data24h);
	}

	@Override
	public List<Object[]> findQuantidadesComprasNegadasPorSubcanal(Data dataInicioEnviada, Data dataFimEnviada) {
		String stringDataInicio = DataUtil.dataToString(dataInicioEnviada, TIMESTAMP_DB2_FORMAT);
		String stringDataFim = DataUtil.dataToString(dataFimEnviada, TIMESTAMP_DB2_FORMAT);
		return getResultListByNamedQuery(Compra.NQ_SELECT_COUNT_COMPRAS_NEGADAS, stringDataInicio, stringDataFim);
	}

	@Override
	// Número de Carrinhos que estão abandonados no dia da consulta, cuja ultima alteração ocorreu no periodo pedido.
	public List<Object[]> findQuantidadesCarrinhosAbandonados(Data dataInicioEnviada, Data dataFimEnviada) {
		Data dataFim = new Data(dataFimEnviada);
		Data dataFimMinima = DataUtil.getDataAtual().subtract(Calendar.DAY_OF_YEAR, 5);

		if (dataFimMinima.before(dataFimEnviada)) {
			dataFim = dataFimMinima;
		}
		if (dataFim.equalsOrBefore(dataInicioEnviada)) {
			return new ArrayList<Object[]>();
		}
		String stringDataInicio = DataUtil.dataToString(dataInicioEnviada, TIMESTAMP_DB2_FORMAT);
		String stringDataFim = DataUtil.dataToString(dataFim, TIMESTAMP_DB2_FORMAT);
		return getResultListByNamedQuery(Compra.NQ_SELECT_COUNT_CARRINHOS_ABANDONADOS, stringDataInicio, stringDataFim);
	}

	@Override
	public List<Object[]> findComprasSimulandoComissao(Data dataInicio, Data dataFim, Integer qtdMeioPagamento, Integer intervaloMudarMeio) {

		String dataInicioToString = DataUtil.dataToString(dataInicio, DATA_DB2_FORMAT);
		String dataFimToString = DataUtil.dataToString(dataFim, DATA_DB2_FORMAT);

		StringBuilder query = new StringBuilder(
			"SELECT MOD((((HOUR(HH_INICIO_COMPRA) * 60) + MINUTE(HH_INICIO_COMPRA)) / " + intervaloMudarMeio + "), " + qtdMeioPagamento + ") + 1, "
			+ "sum(VR_COMISSAO) "
			+ "FROM lce.LCETB013_COMPRA t0 "
			+ "WHERE t0.DT_INICIO_COMPRA BETWEEN ?1 AND ?2 "
				+ "AND t0.NU_SITUACAO_COMPRA in (8, 13, 14) "
				+ "GROUP BY MOD((((HOUR(HH_INICIO_COMPRA) * 60) + MINUTE(HH_INICIO_COMPRA)) / " + intervaloMudarMeio + "), " + qtdMeioPagamento + ")");
		
		Object[] parametros = new Object[2];

		parametros[0] = dataInicioToString;
		parametros[1] = dataFimToString;

		return getResultListByNativeQuery(query.toString(), parametros);

	}

	@Override
	public List<Compra> findBySituacoesPixCanceladasPagamentoNaoIdentificadoWithUR() {
		return getResultListByNamedQuery(Compra.NQ_SELECT_BY_SITUACAO_COMPRA_PGTO_NAO_IDENTIFICADO_WITH_UR,
			SituacaoCompra.Situacao.CANCELADA_PAGAMENTO_NAO_IDENTIFICADO_TEMPO_LIMITE.getValue());
	}
}
