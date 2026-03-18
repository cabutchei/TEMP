package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.List;

import javax.ejb.Local;

import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolao;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolaoPK;
import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao.Situacao;
import br.gov.caixa.silce.negocio.infra.dao.SilceDAO;
import br.gov.caixa.util.Data;

@Local
public interface ReservaCotaBolaoDAOLocal extends SilceDAO<ReservaCotaBolao, ReservaCotaBolaoPK> {

	public List<Object[]> findBySituacaoDataInclusao(Situacao situacao, Data dataCorte);

	public List<Object[]> findEmCancelamento();

	public List<Object[]> findExpiradas();

	public void deleteByIds(List<ReservaCotaBolaoPK> idsReservas);

	public void atualizarSituacaoReserva(ReservaCotaBolaoPK idReserva, Situacao situacao);

	public List<Object[]> findEmPagamento(Data dataCorte);

	public List<Integer> findLotericasByDtFinalizacaoProcessamentoSituacaoReserva(Data dataFechamento, Situacao situacao);

	void retornarSituacaoCotasContabilizadasParaConfirmadasByMesDtFinalizacaoProcessamento(Data dataFechamento);

	void atualizarCotasConfirmadasParaContabilizadasByLotericaDataFechamento(Integer codLoterica, Data dataFechamento);

	List<ReservaCotaBolao> findByCodBolaoCodCotaCpf(String codBolao, String codCota, String cpf, Data dataReserva);

	ReservaCotaBolao findByIdAposta(Long idAposta);

	public List<Object[]> findBySituacaoNaoContabilizada(Data data);
}

