package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.silce.dominio.ApostaModalidadeConcurso;
import br.gov.caixa.silce.dominio.OrdenacaoAposta;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.ApostaComprada;
import br.gov.caixa.silce.dominio.entidade.ApostaLotomania;
import br.gov.caixa.silce.dominio.entidade.Apostador;
import br.gov.caixa.silce.dominio.entidade.ComboAposta;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.entidade.Premio;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolaoPK;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta.Situacao;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra;
import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao;
import br.gov.caixa.silce.dominio.entidade.TipoCombo;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.negocio.infra.dao.AbstractSilceDAO;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Email;
import br.gov.caixa.util.Hora;

/**
 * Implementacao da DAO para Apostas de Carrinho.
 */
@Stateless
public class ApostaDAO extends AbstractSilceDAO<Aposta<?>, Long> implements
		ApostaDAOLocal {

	private static final Logger LOG = LogManager.getLogger(ApostaDAO.class, new MessageFormatMessageFactory());

	private static final String ATRIBUTO_APOSTA_COMPRA = "compra";
	private static final String ATRIBUTO_COMPRA_LOTERICA = "loterica";

	private static final Integer MAX_DEPTH = 5;

	private static final String FROM_APOSTA_JOIN_COMPRA_APOSTADOR_APOSTA_COMPRADA = " From Aposta entidade "
		+ "join entidade.compra compra join compra.apostador apostador join entidade.apostaComprada apostaComprada left join entidade.apostaComprada.premio premio "
		+ "left join entidade.reservaCotaBolao reservaCotaBolao";
	private static final long serialVersionUID = 1L;

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Aposta<?>> findByCarrinhoApostador(Long apostador) {
		return getResultListByNamedQuery(
				Aposta.NQ_SELECT_BY_APOSTADOR_SITUACAOCOMPRA, apostador,
			SituacaoCompra.Situacao.CARRINHO.getValue(), SituacaoReservaCotaBolao.Situacao.EM_CANCELAMENTO.getValue());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ResultadoPesquisaPaginada<Aposta<?>> findByPendenciasResolvidas(
			Data data, Modalidade modalidade, Integer concurso,
			Long idLoterica, int first, int pageSize) {
		String jpql = selectQuery
				+ " join entidade.compra compra join entidade.apostaComprada comprada";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("and comprada.dataFinalizacaoProcessamento =", data);
		parametros.put("and compra.dataInicioCompra <", data);
		parametros.put("and entidade.modalidade =", modalidade);
		parametros.put("and comprada.concursoInicial =", concurso);
		parametros.put("and compra.loterica.id =", idLoterica);

		Map<Class<?>, String> newForceFetchMap = newForceFetchMap(Aposta.class,
				ATRIBUTO_APOSTA_COMPRA);
		newForceFetchMap.put(Compra.class, ATRIBUTO_COMPRA_LOTERICA);

		return getResultListByQuery(first, pageSize, jpql, newForceFetchMap,
				parametros);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ResultadoPesquisaPaginada<Aposta<?>> findByPendenciasNaoResolvidas(
			Modalidade modalidade, SituacaoAposta.Situacao situacao,
			Integer concurso, Long idLoterica, Data timestampDeCorte,
			int first, int pageSize) {
		StringBuilder sb = new StringBuilder(selectQuery);
		sb.append(" join entidade.compra compra join entidade.apostaComprada comprada where ((compra.dataInicioCompra < ?1) or (compra.dataInicioCompra = ?1 and compra.horaInicioCompra <= ?2)) ");

		Map<String, Object> parameters = newParametersMap();

		parameters.put("?1", timestampDeCorte);
		parameters.put("?2", new Hora(timestampDeCorte));
		parameters.put("and entidade.modalidade =", modalidade);
		parameters.put("and comprada.concursoInicial =", concurso);
		parameters.put("and compra.loterica.id =", idLoterica);
		if (situacao != null) {
			parameters.put("and comprada.situacao.id =", situacao.getValue());
		} else {
			parameters.put("and comprada.situacao.id in",
					convertToIdList(Situacao.getSituacoesPendente()));
		}

		Map<Class<?>, String> newForceFetchMap = newForceFetchMap(Aposta.class,
				ATRIBUTO_APOSTA_COMPRA);
		newForceFetchMap.put(Compra.class, ATRIBUTO_COMPRA_LOTERICA);

		return getResultListByQuery(first, pageSize, sb.toString(), newForceFetchMap, parameters, getHintsMapWithUR());
	}

	@Override
	public ResultadoPesquisaPaginada<Aposta<?>> findByApostaRelizada(
			Long idApostador, Modalidade modalidade, Email emailApostador,
			CPF cpfApostador, Integer codConcurso, Data dataInicio,
		Data dataFim, String numeroAposta, String nsuAposta, String nsuApostaCota, int first, int pageSize, Situacao... situacoes) {
		String jpql = "Select entidade "
				+ FROM_APOSTA_JOIN_COMPRA_APOSTADOR_APOSTA_COMPRADA
				+ " order by entidade.apostaComprada.concursoInicial desc, entidade.id desc";

		Map<String, Object> parametros = initParametros(idApostador,
				modalidade, emailApostador, cpfApostador, codConcurso,
			dataInicio, dataFim, numeroAposta, nsuAposta, nsuApostaCota, situacoes);

		Map<Class<?>, String> newForceFetchMap = newForceFetchMap(Aposta.class,
				ATRIBUTO_APOSTA_COMPRA);
		newForceFetchMap.put(ApostaComprada.class, "premio");
		newForceFetchMap.put(Aposta.class, "reservaCotaBolao");

		return getResultListByQuery(first, pageSize, jpql, newForceFetchMap,
				parametros);
	}

	@Override
	public ResultadoPesquisaPaginada<Aposta<?>> findByApostaRelizada(Long idApostador, Modalidade modalidade, Data dataInicio, Data dataFim, int first, int pageSize,
		List<Long> idsApostas, Situacao... situacoes) {
		Map<Class<?>, String> newForceFetchMap = newForceFetchMap(Aposta.class,
			ATRIBUTO_APOSTA_COMPRA);

		if (modalidade == null) {
			return getResultListByNamedQuery(first, pageSize, newForceFetchMap, Aposta.NQ_FIND_BY_APOSTADOR_IDS, idApostador, dataInicio, dataFim,
				convertToIdList(situacoes), idsApostas);
		} else {
			return getResultListByNamedQuery(first, pageSize, newForceFetchMap, Aposta.NQ_FIND_BY_APOSTADOR_MODALIDADE_IDS, idApostador, modalidade, dataInicio, dataFim,
				convertToIdList(situacoes), idsApostas);
		}
	}

	@Override
	public ResultadoPesquisaPaginada<Aposta<?>> findByApostaRelizadaOrderByEspecial(Long idApostador, Modalidade modalidade, Data dataInicio, Data dataFim, int first, int pageSize,
		List<Long> idsApostas, Situacao... situacoes) {
		Map<Class<?>, String> newForceFetchMap = newForceFetchMap(Aposta.class, ATRIBUTO_APOSTA_COMPRA);

		if (modalidade == null) {
			return getResultListByNamedQuery(first, pageSize, newForceFetchMap, Aposta.NQ_FIND_BY_APOSTADOR_IDS_ORDER_ESPECIAL, idApostador, dataInicio, dataFim,
				convertToIdList(situacoes), idsApostas);
		} else {
			return getResultListByNamedQuery(first, pageSize, newForceFetchMap, Aposta.NQ_FIND_BY_APOSTADOR_MODALIDADE_IDS_ORDER_ESPECIAL, idApostador, modalidade, dataInicio,
				dataFim, convertToIdList(situacoes), idsApostas);
		}
	}

	@Override
	public Long findCount(Long idApostador, Modalidade modalidade,
			Email emailApostador, CPF cpfApostador, Integer codConcurso,
		Data dataInicio, Data dataFim, String numeroAposta, String nsuAposta, String nsuApostaCota,
		SituacaoAposta.Situacao... situacoes) {
		String jpql = "Select count(entidade) "
				+ FROM_APOSTA_JOIN_COMPRA_APOSTADOR_APOSTA_COMPRADA;

		Map<String, Object> parametros = initParametros(idApostador,
				modalidade, emailApostador, cpfApostador, codConcurso,
			dataInicio, dataFim, numeroAposta, nsuAposta, nsuApostaCota, situacoes);

		return getSingleResultByQuery(jpql, parametros);
	}

	@Override
	public Decimal findSum(Long idApostador, Modalidade modalidade,
			Email emailApostador, CPF cpfApostador, Integer codConcurso,
		Data dataInicio, Data dataFim, String numeroAposta, String nsuAposta, String nsuApostaCota, SituacaoAposta.Situacao... situacoes) {
		String jpql = "Select SUM(entidade.valor)+ 0.0 "
				+ FROM_APOSTA_JOIN_COMPRA_APOSTADOR_APOSTA_COMPRADA;

		Map<String, Object> parametros = initParametros(idApostador,
				modalidade, emailApostador, cpfApostador, codConcurso,
			dataInicio, dataFim, numeroAposta, nsuAposta, nsuApostaCota, situacoes);

		Decimal retorno = getSingleResultByQuery(jpql, parametros);
		if (retorno == null) {
			return Decimal.ZERO;
		}
		return retorno;
	}

	@Override
	public Decimal findSumByApostadorDataCompraSituacaoAposta(Long idApostador, Data dataCompra, List<SituacaoAposta.Situacao> situacaoAposta) {
		List<Long> valorSituacao = new ArrayList<Long>();
		for (Situacao situacao : situacaoAposta) {
			valorSituacao.add(situacao.getValue());
		}
		Decimal retorno = getSingleResultByNamedQuery(Aposta.NQ_SUM_BY_APOSTADOR_DATA_SITUACAO, idApostador, dataCompra, valorSituacao);
		if (retorno == null) {
			return Decimal.ZERO;
		}
		return retorno;
	}

	@Override
	public List<Aposta<?>> findEsportivasByApostador(Long apostadorId) {
		List<Modalidade> modalidadesEsportivas = Arrays.asList(
				Modalidade.LOTECA, Modalidade.LOTOGOL);
		Long situacaoId = SituacaoCompra.Situacao.CARRINHO.getValue();

		return getResultListByNamedQuery(
			Aposta.NQ_SELECT_BY_SITUACAOCOMPRA_APOSTADOR_MODALIDADES,
			situacaoId, apostadorId, modalidadesEsportivas);
	}

	@Override
	public List<Aposta<?>> findByCompra(Long compraId) {
		return getResultListByNamedQuery(Aposta.NQ_SELECT_BY_COMPRA, compraId, SituacaoReservaCotaBolao.Situacao.EM_CANCELAMENTO.getValue(),
			SituacaoReservaCotaBolao.Situacao.CANCELADA.getValue());
	}

	@Override
	public List<Aposta<?>> findByCompraOrderByIdAposta(Long compraId) {
		return getResultListByNamedQuery(Aposta.NQ_SELECT_BY_COMPRA_ORDERBY_ID_APOSTA, compraId);
	}

	public Long findCountEmProcessamentoByConcursoModalidade(Long codConcurso, Modalidade modalidade, Boolean leituraSuja) {
		List<Long> situacoes = Arrays.asList(Situacao.EM_PROCESSAMENTO.getValue(), Situacao.EM_PROCESSAMENTO_ENVIADA_SISPL.getValue());
		String namedQuery;

		if (leituraSuja) {
			namedQuery = Aposta.NQ_COUNT_BY_CONCURSOINICIAL_SITUACAO_WITH_UR;
		} else {
			namedQuery = Aposta.NQ_COUNT_BY_CONCURSOINICIAL_SITUACAO;
		}

		Long apostas = 0L;
		for (Long situacao : situacoes) {
			apostas += (Long) getSingleResultByNamedQuery(namedQuery, modalidade, codConcurso, situacao);
		}
		return apostas;
	}

	@Override
	public Long recupereQuantidadeReservaCotasEmProcessamento(Integer codConcurso, Modalidade modalidade, boolean leituraSuja) {
		List<Long> situacoes = Arrays.asList(SituacaoReservaCotaBolao.Situacao.INICIADA.getValue(), SituacaoReservaCotaBolao.Situacao.RESERVADA.getValue(),
			SituacaoReservaCotaBolao.Situacao.EM_PAGAMENTO.getValue(), SituacaoReservaCotaBolao.Situacao.EM_CANCELAMENTO.getValue());
		String namedQuery;

		if (leituraSuja) {
			namedQuery = Aposta.NQ_COUNT_RESERVA_COTAS_PENDENTES_BY_CONCURSOINICIAL_SITUACAO_WITH_UR;
		} else {
			namedQuery = Aposta.NQ_COUNT_RESERVA_COTAS_PENDENTES_BY_CONCURSOINICIAL_SITUACAO;
		}

		Long apostas = 0L;
		for (Long situacao : situacoes) {
			apostas += (Long) getSingleResultByNamedQuery(namedQuery, modalidade, codConcurso, situacao);
		}
		return apostas;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long findCountAguardandoPagamentoPix(Integer codConcurso, Modalidade modalidade, Boolean cotas, Boolean leituraSuja) {
		String namedQuery;

		if (leituraSuja) {
			namedQuery = Aposta.NQ_COUNT_RESERVA_COTAS_COMPRAS_PIX_PENDENTES_BY_CONCURSOINICIAL_SITUACAO_WITH_UR;
		} else {
			namedQuery = Aposta.NQ_COUNT_RESERVA_COTAS_COMPRAS_PIX_PENDENTES_BY_CONCURSOINICIAL_SITUACAO;
		}

		LOG.debug("namedQuery: " + namedQuery);

		String indicadorApostasCota = cotas ? "S" : "N";
		Integer qtdCotas = getSingleResultByNamedQuery(namedQuery, modalidade.getCodigo(), codConcurso, indicadorApostasCota);

		LOG.debug("qtdCotas: " + qtdCotas);

		return qtdCotas.longValue();
	}

	@Override
	public List<Aposta<?>> findByModalidade(Long idCompra, Modalidade modalidade) {
		return getResultListByNamedQuery(Aposta.NQ_SELECT_BY_MODALIDADE,
				idCompra, modalidade);
	}

	@Override
	public Long findCountBySituacaoApostaComprada(Long idCompra, Long idSituacao) {
		return getSingleResultByNamedQuery(Aposta.NQ_COUNT_BY_SITUACAO,
				idCompra, idSituacao);
	}

	@Override
	public Long findCountByComboAposta(Long idCombo) {
		return getSingleResultByNamedQuery(Aposta.NQ_COUNT_BY_COMBO_APOSTA, idCombo);
	}

	@Override
	public Aposta<?> findByIdAndApostador(Long idAposta, Long idApostador) {
		Map<Class<?>, String> forceFetchMap = newForceFetchMap();
		// naldo - o forceFetch só funciona dentro do MaxFetchDepth padrão
		// tem que fazer um método pra passar um fetchDepth específico pra query
		forceFetchMap.put(Aposta.class, "compra");
		forceFetchMap.put(Compra.class, "loterica");
		forceFetchMap.put(Aposta.class, "apostaComprada");
		forceFetchMap.put(ApostaComprada.class, "premio");
		forceFetchMap.put(Premio.class, "loterica");
		return getSingleResultByNamedQuery(
			Aposta.NQ_SELECT_BY_ID_AND_APOSTADOR, forceFetchMap, idAposta,
			idApostador);
	}

	@Override
	public Aposta<?> findByIdAndApostadorSemFetch(Long idAposta, Long idApostador) {
		return getSingleResultByNamedQuery(Aposta.NQ_SELECT_BY_ID_AND_APOSTADOR, idAposta, idApostador);
	}

	private Map<String, Object> initParametros(Long idApostador,
			Modalidade modalidade, Email emailApostador, CPF cpfApostador,
		Integer codConcurso, Data dataInicio, Data dataFim, String numeroAposta,
		String nsuAposta, String nsuApostaCota, Situacao... situacoes) {
		Map<String, Object> parametros = newParametersMap();
		parametros.put("and compra.apostador.id =", idApostador);
		parametros.put("and entidade.modalidade =", modalidade);
		parametros.put("and apostaComprada.situacao.id in", convertToIdList(situacoes));
		parametros.put("and apostador.email =", emailApostador);
		parametros.put("and apostador.cpf =", cpfApostador);
		parametros.put("and apostaComprada.concursoInicial =", codConcurso);
		parametros.put("and compra.dataInicioCompra >=", dataInicio);
		parametros.put("and compra.dataInicioCompra <=", dataFim);

		if (numeroAposta != null && !numeroAposta.isEmpty()) {
			parametros.put("and entidade.id =", Long.valueOf(numeroAposta));
		}

		if (nsuAposta != null && !nsuAposta.isEmpty()) {
			parametros.put("and apostaComprada.nsuTransacao =", Integer.valueOf(nsuAposta));
		}

		if (nsuApostaCota != null && !nsuApostaCota.isEmpty()) {
			parametros.put("and reservaCotaBolao.nsu =", Long.valueOf(nsuApostaCota));
		}

		return parametros;
	}

	@Override
	public void deleteByCarrinho(Long carrinhoId, Long apostadorId) {

		// Verifica se existem apostas de combo
		List<Long> idCombos = getResultListByNamedQuery(Aposta.NQ_SELECT_ID_COMBOS_BY_COMPRA, carrinhoId, apostadorId);
		execUpdateByNamedQuery(Aposta.NQ_DELETE_ESPELHOS_BY_CARRINHO, carrinhoId, apostadorId);

		// Limpa as apostas simples
		execUpdateByNamedQuery(Aposta.NQ_DELETE_BY_CARRINHO, carrinhoId, apostadorId);
		flush();

		if (idCombos != null && !idCombos.isEmpty()) {
			execUpdateByNamedQuery(ComboAposta.NQ_DELETE_BY_LIST_ID, idCombos);
		}
	}

	@Override
	public List<Object[]> findByApostaPainelVenda(Modalidade modalidade, TipoCombo tipoCombo, Integer codConcurso,
		Data dataInicio, Data dataFim, Meio meio, boolean isBolao) {
		
		StringBuilder query = new StringBuilder(
			"SELECT t0.NU_MODALIDADE_JOGO, "
				+ "t2.NU_MEIO_PAGAMENTO, "
				+ "t1.NU_CONCURSO_INICIAL, "
				+ "COUNT(t0.NU_APOSTA), "
				+ "SUM(t0.VR_APOSTA), "
				+ "COUNT(t0.NU_COMBO_APOSTA), "
				+ "SUM(CASE WHEN t0.NU_RESERVA_COTA_BOLAO IS NOT NULL THEN 1 ELSE 0 END), "
				+ "CASE WHEN t2.IC_SUBCANAL_PAGAMENTO IS NULL THEN 1 ELSE t2.IC_SUBCANAL_PAGAMENTO END, "
				+ "SUM(CASE WHEN t0.NU_COMBO_APOSTA IS NOT NULL THEN t0.VR_APOSTA ELSE 0.0 END), "
				+ "SUM(CASE WHEN t0.NU_RESERVA_COTA_BOLAO IS NOT NULL THEN t0.VR_APOSTA ELSE 0.0 END) "
				+ "FROM LCE.LCETB011_APOSTA t0 "
				+ ((tipoCombo == null) ? "" : "INNER JOIN LCE.LCETB046_COMBO_APOSTA ca on ca.NU_COMBO_APOSTA = t0.NU_COMBO_APOSTA ")
				+ "INNER JOIN LCE.LCETB009_APOSTA_COMPRADA t1 ON t0.NU_APOSTA = t1.NU_APOSTA "
				+ "INNER JOIN LCE.LCETB013_COMPRA t2 ON t0.NU_COMPRA = t2.NU_COMPRA "
				+ "WHERE t1.IC_BILHETE_TROCA = 'N' "

				// Tem q ser passado os valores fixos por questoes de desempenho do db2.
				+ "AND t1.NU_SITUACAO_APOSTA IN (4, 7, 9, 10, 11) "
				+ "AND t1.NU_SITUACAO_APOSTA IS NOT NULL "
				+ "AND t1.DT_FINALIZACAO_PROCESSAMENTO < ? "
				+ "AND t1.DT_FINALIZACAO_PROCESSAMENTO >= ? "
				+ ((isBolao) ? "AND t0.IC_COTA_BOLAO = ? " : "")
				+ ((codConcurso == null) ? "" : "AND t1.NU_CONCURSO_INICIAL = ? ")
				+ ((modalidade == null) ? "" : "AND t0.NU_MODALIDADE_JOGO = ? ")
				+ ((tipoCombo == null) ? "" : "AND ca.NU_TIPO_COMBO = ? ")
				+ ((meio == null) ? "" : "AND t2.NU_MEIO_PAGAMENTO = ? ")
				+ "GROUP BY t0.NU_MODALIDADE_JOGO, "
				+ "t2.NU_MEIO_PAGAMENTO, "
				+ "t1.NU_CONCURSO_INICIAL, "
				+ "t2.IC_SUBCANAL_PAGAMENTO "
				+ "ORDER BY t0.NU_MODALIDADE_JOGO ASC, "
				+ "t1.NU_CONCURSO_INICIAL ASC "
				+ "FOR READ ONLY "
				+ "WITH UR");

		Data dataInit = dataInicio.zereHoraMinutoSegundoMilisegundo();
		Data diaSeguinte = new Data(dataFim).add(Calendar.DAY_OF_MONTH, 1).zereHoraMinutoSegundoMilisegundo();

		Object[] parametros = new Object[7];
		int cont = 0;
		parametros[cont++] = DataUtil.dataToString(diaSeguinte, DataUtil.YYYYMMDD);
		parametros[cont++] = DataUtil.dataToString(dataInit, DataUtil.YYYYMMDD);
		if (isBolao) {
			parametros[cont++] = isBolao ? 'S' : 'N';
		}

		if (codConcurso != null) {
			parametros[cont++] = codConcurso;
		}

		if (modalidade != null) {
			parametros[cont++] = modalidade.getCodigo();
		}
		
		if (tipoCombo != null) {
			parametros[cont++] = tipoCombo.getId();
		}

		if (meio != null) {
			parametros[cont++] = meio.getValue();
		}

		return getResultListByNativeQuery(query.toString(), parametros);
	}

	@Override
	public List<Aposta<?>> findOriginaisByCompra(Long idApostador, Long compraId) {
		return getResultListByNamedQuery(Aposta.NQ_SELECT_ORIGINAIS_BY_COMPRA, compraId, idApostador, SituacaoReservaCotaBolao.Situacao.EM_CANCELAMENTO.getValue());
	}

	@Override
	public List<Aposta<?>> findByCompraApostador(Long idCompra, Long idApostador) {
		return getResultListByNamedQuery(Aposta.NQ_SELECT_BY_COMPRA_APOSTADOR, idCompra, idApostador, SituacaoReservaCotaBolao.Situacao.EM_CANCELAMENTO.getValue());
	}

	@Override
	public ApostaLotomania findEspelhoVinculada(Long idAposta) {
		return getSingleResultByNamedQuery(Aposta.NQ_SELECT_ESPELHO_BY_ORIGINAL, idAposta);
	}

	@Override
	public List<Aposta<?>> findOriginaisByCompraComPremio(Long idApostador, Long compraId) {
		return getResultListByNamedQueryWithMaxDepth(Aposta.NQ_SELECT_ORIGINAIS_BY_COMPRA_COM_PREMIO, MAX_DEPTH, compraId, idApostador);
	}

	@Override
	public Long findCountNoCarrinho(Long idApostador) {
		return getSingleResultByNamedQuery(Aposta.NQ_COUNT_CARRINHO, SituacaoCompra.Situacao.CARRINHO.getValue(), idApostador);
	}

	@Override
	public Long findCountCotasNoCarrinho(Long idApostador) {
		return getSingleResultByNamedQuery(Aposta.NQ_COUNT_COTAS_CARRINHO, SituacaoCompra.Situacao.CARRINHO.getValue(), idApostador);
	}

	@Override
	public List<Aposta<?>> findByCombo(Long idApostador, Long idCombo) {
		return getResultListByNamedQuery(Aposta.NQ_FIND_APOSTAS_BY_APOSTADOR_COMBO, idApostador, idCombo);
	}

	@Override
	public ResultadoPesquisaPaginada<Aposta<?>> findByApostaRelizadaOrderByCompra(Long idApostador, Modalidade modalidade, Data dataInicio, Data dataFim, int offset,
		int size, List<Long> idsApostas, Situacao[] situacoes, OrdenacaoAposta opcaoOrdenacao, Integer tipoAposta) {
		Map<Class<?>, String> newForceFetchMap = newForceFetchMap(Aposta.class, ATRIBUTO_APOSTA_COMPRA);

		String complementoQuery = opcaoOrdenacao.getOrdemCrescente() ? "ASC" : "DESC";
		String filtroTipoAposta = null;
		if ((tipoAposta != null) && (tipoAposta > 1)) {
			// 1: Todos - 2:Aposta Individual - 3: Aposta Bolão
			filtroTipoAposta = " AND entidade.indicadorBolao = ?6 ";
		}
		// TODO Verificar por que ordenação não funciona quando desligado microserviço
		String jpql = "Select distinct entidade From Aposta entidade "
			+ "join fetch entidade.compra "
			+ "join fetch entidade.apostaComprada "
			+ "left join fetch entidade.comboAposta "
			+ "left join fetch entidade.reservaCotaBolao "
			+ "left join fetch entidade.reservaCotaBolao.loterica"
			+ " where (entidade.compra.apostador.id = ?1 and entidade.compra.dataInicioCompra >= ?2 and entidade.compra.dataInicioCompra <= ?3)"
			+ " and (entidade.apostaComprada.situacao.id in (?4) or entidade.id in (?5))"
			+ ((filtroTipoAposta != null) ? filtroTipoAposta : "")
			+ " ORDER BY entidade.compra.dataInicioCompra " + complementoQuery + ", entidade.modalidade " + complementoQuery + ", entidade.compra.id "
			+ complementoQuery + ", entidade.id " + complementoQuery;

		Map<String, Object> parametros = newParametersMap();

		parametros.put("?1", idApostador);
		parametros.put("?2", dataInicio);
		parametros.put("?3", dataFim);
		parametros.put("?4", convertToIdList(situacoes));
		parametros.put("?5", idsApostas);
		if (filtroTipoAposta != null) {
			parametros.put("?6", (tipoAposta == 2) ? Boolean.FALSE : Boolean.TRUE);
		}

		if (modalidade != null) {
			parametros.put(" and entidade.modalidade = ", modalidade);
		}

		return getResultListByQuery(offset, size, jpql, newForceFetchMap, parametros, getHintsMapWithUR());
	}

	@Override
	public List<Aposta<?>> findByCompraIdentica(Modalidade modalidade, Integer idConcurso, Long idApostador) {
		return getResultListByNamedQuery(Aposta.NQ_SELECT_BY_APOSTADOR_MODALIDADES, modalidade, idConcurso, idApostador);
	}

	@Override
	public List<Aposta<?>> findApostasCotaBolaoByCompra(Long idCompra) {
		return getResultListByNamedQuery(Aposta.NQ_FIND_APOSTAS_BOLAO_BY_COMPRA, idCompra);
	}

	@Override
	public void deleteByReservaCotaBolao(ReservaCotaBolaoPK idReserva) {
		execUpdateByNamedQuery(Aposta.NQ_DELETE_BY_RESERVA_COTA_BOLAO, idReserva.getId(), idReserva.getMes(), idReserva.getParticao());
	}

	@Override
	public List<ApostaModalidadeConcurso> findApostasBolaoByCompra(Long idCompra) {
		StringBuilder query = new StringBuilder(
			"SELECT t0.NU_MODALIDADE_JOGO AS modalidade, t0.NU_CONCURSO_ALVO AS concurso "
				+ "FROM LCE.LCETB011_APOSTA t0 WHERE t0.NU_COMPRA = ?1 AND t0.IC_COTA_BOLAO = 'S' "
				+ "GROUP BY t0.NU_MODALIDADE_JOGO, t0.NU_CONCURSO_ALVO");

		return getResultListByNativeQuery(query.toString(), ApostaModalidadeConcurso.class, idCompra);
	}

	@Override
	public List<Object[]> findByConcursoModalidadeSituacaoReservaCotaBolaoWithUR(Integer concurso, Integer modalidade) {
		
		Object[] parametros = new Object[2];
		parametros[0] = concurso;
		parametros[1] = Modalidade.getByCodigo(modalidade).getCodigo();

		StringBuilder query = new StringBuilder(
			"SELECT DISTINCT A.NU_APOSTA, AC.NU_APOSTA_COMPRADA, C.NU_COMPRA, "
				+ "AC.NU_TRANSACAO_APOSTA, MP.DE_MEIO_PAGAMENTO_APOSTA, "
				+ "C.NU_TRANSACAO_DEBITO, AC.DT_INICIO_APOSTA_COMPRADA, "
				+ "AC.HH_INICIO_APOSTA_COMPRADA, C.DT_EXPIRACAO_PAGAMENTO, "
				+ "C.HH_EXPIRACAO_PAGAMENTO, C.NU_SITUACAO_COMPRA, SC.DE_SITUACAO_COMPRA, "
				+ "AC.NU_SITUACAO_APOSTA, SA.DE_SITUACAO_APOSTA, AC.TS_ALTERACAO_SITUACAO, "
				+ "RCB.NU_RESERVA_COTA_BOLAO, RCB.MM_RESERVA_COTA_BOLAO, RCB.NU_PARTICAO, "
				+ "L.NU_CD, L.NO_FANTASIA, RCB.CO_BOLAO_RESERVA_COTA, RCB.CO_COTA_BOLAO, "
				+ "RCB.TS_DATA_HORA_RESERVA_COTA, RCB.DT_EXPRO_RESERVA_COTA_BOLAO, RCB.HH_EXPRO_RESERVA_COTA_BOLAO, "
				+ "RCB.NU_COTA_RESERVADA, RCB.NU_TRANSACAO_RESERVA_COTA, RCB.NU_SITUACAO_RESERVA_COTA_BOLAO, "
				+ "SRCB.NO_RESERVA_SITUACAO_COTA_BOLAO, RCB.TS_ALTERACAO_SITUACAO "
				+ "FROM LCE.LCETB011_APOSTA A "
				+ "LEFT JOIN LCE.LCETB009_APOSTA_COMPRADA AC ON AC.NU_APOSTA = A.NU_APOSTA "
				+ "LEFT JOIN LCE.LCETB008_SITUACAO_APOSTA SA ON SA.NU_SITUACAO_APOSTA = AC.NU_SITUACAO_APOSTA "
				+ "INNER JOIN LCE.LCETB013_COMPRA C ON C.NU_COMPRA = A.NU_COMPRA "
				+ "INNER JOIN LCE.LCETB012_SITUACAO_COMPRA SC ON SC.NU_SITUACAO_COMPRA = C.NU_SITUACAO_COMPRA "
				+ "LEFT JOIN LCE.LCETB018_MEIO_PAGAMENTO MP ON MP.NU_MEIO_PAGAMENTO = C.NU_MEIO_PAGAMENTO "
				+ "INNER JOIN LCE.LCETB059_RESERVA_COTA_BOLAO RCB ON RCB.NU_RESERVA_COTA_BOLAO = A.NU_RESERVA_COTA_BOLAO "
				+ " 	AND RCB.MM_RESERVA_COTA_BOLAO = A.MM_RESERVA_COTA_BOLAO AND RCB.NU_PARTICAO = A.NU_PARTICAO_COTA_BOLAO "
				+ "INNER JOIN LCE.LCETB060_STCO_RVRSA_COTA_BOLAO SRCB ON SRCB.NU_SITUACAO_RESERVA_COTA_BOLAO = RCB.NU_SITUACAO_RESERVA_COTA_BOLAO "
				+ "LEFT JOIN LCE.LCEVW004_LOTERICA L ON L.NU_CD = RCB.NU_CANAL_DISTRIBUICAO "
				+ "WHERE A.NU_CONCURSO_ALVO = ?1 "
				+ "AND A.NU_MODALIDADE_JOGO = ?2 "
				
				// Tem q ser passado os valores fixos por questoes de desempenho do db2.
				+ "AND RCB.NU_SITUACAO_RESERVA_COTA_BOLAO IN (1, 2, 4, 6) "
				+ "FETCH FIRST 50 ROWS ONLY "
				+ "WITH UR");

		return getResultListByNativeQuery(query.toString(), parametros);
	}

	@Override
	public Aposta<?> findByCodBolaoCodCota(String idBolao, String idCota) {
		Map<Class<?>, String> forceFetchMap = newForceFetchMap();
		forceFetchMap.put(Aposta.class, "compra");
		forceFetchMap.put(Aposta.class, "reservaCotaBolao");
		forceFetchMap.put(Compra.class, "meioPagamento");
		forceFetchMap.put(Compra.class, "apostador");
		forceFetchMap.put(Apostador.class, "municipio");

		return getSingleResultByNamedQuery(Aposta.NQ_SELECT_BY_COD_BOLAO_COD_COTA, forceFetchMap, idBolao, idCota);
	}

	@Override
	public Aposta<?> findByIdReservaCotaBolao(ReservaCotaBolaoPK idReserva) {
		return getSingleResultByNamedQuery(Aposta.NQ_SELECT_BY_ID_RESERVA_COTA_BOLAO, idReserva.getId(), idReserva.getMes(), idReserva.getParticao());
	}

	@Override
	public Aposta<?> findByAposta(Long idAposta) {
		Map<Class<?>, String> forceFetchMap = newForceFetchMap();
		forceFetchMap.put(Aposta.class, "compra");
		forceFetchMap.put(Aposta.class, "reservaCotaBolao");
		forceFetchMap.put(Compra.class, "meioPagamento");
		forceFetchMap.put(Compra.class, "apostador");
		forceFetchMap.put(Apostador.class, "municipio");

		return getSingleResultByNamedQuery(Aposta.NQ_SELECT_BY_APOSTA, forceFetchMap, idAposta);
	}
}
