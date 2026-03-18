package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolao;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolaoPK;
import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao;
import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao.Situacao;
import br.gov.caixa.silce.negocio.infra.dao.AbstractSilceDAO;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;

@Stateless
public class ReservaCotaBolaoDAO extends AbstractSilceDAO<ReservaCotaBolao, ReservaCotaBolaoPK> implements ReservaCotaBolaoDAOLocal {

	private static final long serialVersionUID = 1L;

	private static final Integer MAX_DEPTH = 5;

	@Override
	public List<Object[]> findBySituacaoDataInclusao(Situacao situacao, Data dataCorte) {
		String stringDataFormatada = DataUtil.dataToString(dataCorte, TIMESTAMP_DB2_FORMAT);
		return getResultListByNamedQuery(ReservaCotaBolao.NQ_SELECT_BY_SITUACAO_DATA_INCLUSAO, situacao.getValue(), stringDataFormatada);
	}

	@Override
	public List<Object[]> findEmCancelamento() {
		return getResultListByNamedQuery(ReservaCotaBolao.NQ_SELECT_EM_CANCELAMENTO, SituacaoReservaCotaBolao.Situacao.EM_CANCELAMENTO.getValue());
	}

	@Override
	public List<Object[]> findExpiradas() {
		return getResultListByNamedQuery(ReservaCotaBolao.NQ_SELECT_EXPIRADAS_BY_SITUACAO, SituacaoReservaCotaBolao.Situacao.RESERVADA.getValue());
	}

	@Override
	public void deleteByIds(List<ReservaCotaBolaoPK> idsReservas) {
		for (ReservaCotaBolaoPK pk : idsReservas) {
			execUpdateByNamedQuery(ReservaCotaBolao.NQ_DELETE_BY_ID, pk.getId(), pk.getMes(), pk.getParticao());
		}
	}

	@Override
	public void atualizarSituacaoReserva(ReservaCotaBolaoPK idReserva, Situacao situacao) {
		execUpdateByNamedQuery(ReservaCotaBolao.NQ_UPDATE_SITUACAO_BY_ID, situacao.getValue(), idReserva.getId(), idReserva.getMes(), idReserva.getParticao());
	}

	@Override
	public List<Object[]> findEmPagamento(Data dataCorte) {
		String stringDataFormatada = DataUtil.dataToString(dataCorte, TIMESTAMP_DB2_FORMAT);
		return getResultListByNamedQuery(ReservaCotaBolao.NQ_SELECT_EM_PAGAMENTO_BY_DATA_CORTE_ALTERACAO_SITUACAO_WITH_UR,
			SituacaoReservaCotaBolao.Situacao.EM_PAGAMENTO.getValue(), stringDataFormatada);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Integer> findLotericasByDtFinalizacaoProcessamentoSituacaoReserva(Data dataFechamento, Situacao situacao) {
		String dataMovimento = DataUtil.dataToString(dataFechamento, DATA_DB2_FORMAT);
		return getResultListByNamedQuery(ReservaCotaBolao.NQ_SELECT_BY_DT_FINALIZACAO_PROCESSAMENTO_SITUACAO_RESERVA, dataFechamento.getMonth(), dataMovimento,
			situacao.getValue());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void retornarSituacaoCotasContabilizadasParaConfirmadasByMesDtFinalizacaoProcessamento(Data dataFechamento) {
		String dataMovimento = DataUtil.dataToString(dataFechamento, DATA_DB2_FORMAT);
		Integer mes = dataFechamento.getMonth();
		execUpdateByNamedQuery(ReservaCotaBolao.NQ_RETORNAR_COTAS_CONTABILIZADAS_PARA_CONFIRMADAS_BY_MES_DT_FINALIZACAO_PROCESSAMENTO, mes,
			dataMovimento, Situacao.CONTABILIZADA.getValue(), Situacao.CONFIRMADA.getValue());
	}

	@Override
	public void atualizarCotasConfirmadasParaContabilizadasByLotericaDataFechamento(Integer codLoterica, Data dataFechamento) {
		Integer mes = dataFechamento.getMonth();
		String dataMovimento = DataUtil.dataToString(dataFechamento, DATA_DB2_FORMAT);
		execUpdateByNamedQuery(ReservaCotaBolao.NQ_ATUALIZAR_COTAS_CONFIRMADAS_PARA_CONTABILIZADAS_BY_LOTERICA_MES_SITUACAO,
			mes, dataMovimento, Situacao.CONFIRMADA.getValue(), codLoterica, Situacao.CONTABILIZADA.getValue());
	}

	@Override
	public List<ReservaCotaBolao> findByCodBolaoCodCotaCpf(String codBolao, String codCota, String cpf, Data dataReserva) {
		String data = DataUtil.dataToString(dataReserva, TIMESTAMP_DB2_FORMAT);
		Data dataDB2 = new Data(data, TIMESTAMP_DB2_FORMAT);
		return getResultListByNamedQueryWithMaxDepth(ReservaCotaBolao.NQ_SELECT_BY_COD_BOLAO_COD_COTA_CPF, MAX_DEPTH, codBolao, codCota, new CPF(cpf), dataDB2);
	}

	@Override
	public ReservaCotaBolao findById(ReservaCotaBolaoPK id, Boolean writeLock) {
		ReservaCotaBolao reserva = getEntityManager().find(ReservaCotaBolao.class, id);

		String sql = "SELECT NU_RESERVA_COTA_BOLAO FROM LCE.LCETB059_RESERVA_COTA_BOLAO WHERE NU_RESERVA_COTA_BOLAO = ?1"
			+ " AND MM_RESERVA_COTA_BOLAO = ?2 AND NU_PARTICAO = ?3 ";

		if (writeLock) {
			sql += "optimize for 1 row FOR READ ONLY WITH RR USE AND KEEP UPDATE LOCKS";
			execSelectByNativeQuery(sql, id.getId(), id.getMes(), id.getParticao());
			refresh(reserva);
		} else {
			execSelectByNativeQuery(sql, id.getId(), id.getMes(), id.getParticao());
		}

		return reserva;
	}

	@Override
	public ReservaCotaBolao findByIdAposta(Long idAposta) {
		return getSingleResultByNamedQuery(ReservaCotaBolao.NQ_SELECT_BY_ID_APOSTA, idAposta);
	}

	@Override
	public List<Object[]> findBySituacaoNaoContabilizada(Data data) {
		String dataMovimento = DataUtil.dataToString(DataUtil.getDataAtual(), DATA_DB2_FORMAT);
		String sqlAux = "AND AC.DT_FINALIZACAO_PROCESSAMENTO < ?1 ";
		if (data != null) {
			dataMovimento = DataUtil.dataToString(data, DATA_DB2_FORMAT);
			sqlAux = "AND AC.DT_FINALIZACAO_PROCESSAMENTO = ?1 ";
		}

		String sql = "SELECT RC.TS_DATA_HORA_RESERVA_COTA, RC.VR_COTA_RESERVADA, RC.VR_TARIFA_SERVICO, RC.VR_CUSTEIO_COTA, RC.VR_TARIFA_CUSTEIO_COTA, "
			+ "RC.NU_CANAL_DISTRIBUICAO, AC.DT_FINALIZACAO_PROCESSAMENTO, AC.HH_FINALIZACAO_PROCESSAMENTO, C.NU_MEIO_PAGAMENTO "
			+ "FROM LCE.LCETB059_RESERVA_COTA_BOLAO RC INNER JOIN LCE.LCETB011_APOSTA A ON A.NU_RESERVA_COTA_BOLAO = RC.NU_RESERVA_COTA_BOLAO "
			+ "AND A.MM_RESERVA_COTA_BOLAO = RC.MM_RESERVA_COTA_BOLAO AND A.NU_PARTICAO_COTA_BOLAO = RC.NU_PARTICAO "
			+ "INNER JOIN LCE.LCETB009_APOSTA_COMPRADA AC ON AC.NU_APOSTA = A.NU_APOSTA "
			+ "INNER JOIN LCE.LCETB013_COMPRA C ON C.NU_COMPRA = A.NU_COMPRA "
			+ "WHERE RC.NU_SITUACAO_RESERVA_COTA_BOLAO = 5 " + sqlAux + " ORDER BY RC.NU_RESERVA_COTA_BOLAO, RC.TS_DATA_HORA_RESERVA_COTA WITH UR";

		return getResultListByNativeQuery(sql, dataMovimento);
	}
}
