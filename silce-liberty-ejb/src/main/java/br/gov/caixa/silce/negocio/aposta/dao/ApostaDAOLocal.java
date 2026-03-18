package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.List;

import javax.ejb.Local;

import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.silce.dominio.ApostaModalidadeConcurso;
import br.gov.caixa.silce.dominio.OrdenacaoAposta;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.ApostaLotomania;
import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolaoPK;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta.Situacao;
import br.gov.caixa.silce.dominio.entidade.TipoCombo;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.negocio.infra.dao.SilceDAO;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Email;

@Local
public interface ApostaDAOLocal extends SilceDAO<Aposta<?>, Long> {

	/**
	 * Consulta as apostas por carrinho do apostador.
	 * 
	 * @param apostador O apostador.
	 * @return A coleÃ§Ã£o de apostas do carrinho.
	 */
	public List<Aposta<?>> findByCarrinhoApostador(Long apostador);

	/**
	 * Consulta a aposta por id e por id do Apostador.
	 * 
	 * @param idAposta
	 * @param idApostador
	 * @return Aposta.
	 */
	public Aposta<?> findByIdAndApostador(Long idAposta, Long idApostador);

	/**
	 * Consulta a aposta por id e por id do Apostador.
	 * 
	 * @param idAposta
	 * @param idApostador
	 * @return Aposta.
	 */
	public Aposta<?> findByIdAndApostadorSemFetch(Long idAposta, Long idApostador);

	/**
	 * Retorna conjunto de apostas que possuam propriedades restringidas pelos parÃ¢metros passados.
	 * 
	 * @param idApostador
	 * @param modalidade
	 * @param periodo
	 * @param situacao
	 * @param emailApostador
	 * @param cpfApostador
	 * @param tipoConcurso
	 * @param codConcurso
	 * @param dataInicio
	 * @param dataFim
	 * @param first
	 * @param pageSize
	 * @return
	 */
	public ResultadoPesquisaPaginada<Aposta<?>> findByApostaRelizada(
			Long idApostador, Modalidade modalidade, Email emailApostador,
			CPF cpfApostador, Integer codConcurso, Data dataInicio,
		Data dataFim, String numeroAposta, String nsuAposta, String nsuApostaCota, int first, int pageSize, Situacao... situacoes);

	public ResultadoPesquisaPaginada<Aposta<?>> findByApostaRelizada(
		Long idApostador, Modalidade modalidade, Data dataInicio, Data dataFim, int first, int pageSize, List<Long> idApostas, Situacao... situacoes);

	/**
	 * Deleta as apostas esportivas do apostador.
	 * 
	 * @return Lista das apostas.
	 */
	public List<Aposta<?>> findEsportivasByApostador(Long id);

	/**
	 * @return todas as apostas da compra informada.
	 */
	public List<Aposta<?>> findByCompra(Long compraId);

	/**
	 * @return todas as apostas da compra informada, ordenadas pelo Id.
	 */
	public List<Aposta<?>> findByCompraOrderByIdAposta(Long compraId);

	public ResultadoPesquisaPaginada<Aposta<?>> findByPendenciasResolvidas(
			Data data, Modalidade modalidade, Integer concurso,
			Long idLoterica, int first, int pageSize);

	public ResultadoPesquisaPaginada<Aposta<?>> findByPendenciasNaoResolvidas(
			Modalidade modalidade, SituacaoAposta.Situacao situacao,
			Integer concurso, Long idLoterica, Data timestampDeCorte,
			int first, int pageSize);

	public Long findCountEmProcessamentoByConcursoModalidade(Long codConcurso, Modalidade modalidade, Boolean leituraSuja);

	public List<Aposta<?>> findByModalidade(Long idCompra, Modalidade modalidade);

	public Long findCountBySituacaoApostaComprada(Long idCompra, Long idSituacao);

	public Long findCount(Long idApostador, Modalidade modalidade,
			Email emailApostador, CPF cpfApostador, Integer codConcurso,
		Data dataInicio, Data dataFim, String numeroAposta, String nsuAposta, String nsuApostaCota, SituacaoAposta.Situacao... situacoes);

	public Decimal findSum(Long idApostador, Modalidade modalidade,
			Email emailApostador, CPF cpfApostador, Integer codConcurso,
		Data dataInicio, Data dataFim, String numeroAposta, String nsuAposta, String nsuApostaCota, SituacaoAposta.Situacao... situacoes);

	public Decimal findSumByApostadorDataCompraSituacaoAposta(Long idApostador, Data dataCompra, List<SituacaoAposta.Situacao> situacaoAposta);

	public void deleteByCarrinho(Long carrinhoId, Long apostadorId);

	public List<Object[]> findByApostaPainelVenda(Modalidade modalidade, TipoCombo tipoCombo, Integer codConcurso, Data dataInicio, Data dataFim, Meio meio, boolean isBolao);

	public List<Aposta<?>> findOriginaisByCompra(Long idApostador, Long compraId);

	public List<Aposta<?>> findByCompraApostador(Long idCompra, Long idApostador);

	public ResultadoPesquisaPaginada<Aposta<?>> findByApostaRelizadaOrderByEspecial(Long idApostador, Modalidade modalidade, Data dataInicio, Data dataFim, int first, int pageSize,
		List<Long> idsApostas, Situacao... situacoes);

	public ApostaLotomania findEspelhoVinculada(Long idAposta);

	public List<Aposta<?>> findOriginaisByCompraComPremio(Long idApostador, Long idCompra);

	public Long findCountNoCarrinho(Long idApostador);

	public Long findCountCotasNoCarrinho(Long idApostador);

	public Long findCountByComboAposta(Long idCombo);

	public List<Aposta<?>> findByCombo(Long idApostador, Long idCombo);

	public ResultadoPesquisaPaginada<Aposta<?>> findByApostaRelizadaOrderByCompra(Long idApostador, Modalidade modalidade, Data dataInicio, Data dataFim, int offset,
		int size, List<Long> idsApostas, Situacao[] situacoes, OrdenacaoAposta opcaoOrdenacao, Integer tipoAposta);

	public List<Aposta<?>> findByCompraIdentica(Modalidade modalidade, Integer idConcurso, Long idApostador);

	public List<Aposta<?>> findApostasCotaBolaoByCompra(Long idCompra);

	public List<ApostaModalidadeConcurso> findApostasBolaoByCompra(Long idCompra);

	public Long recupereQuantidadeReservaCotasEmProcessamento(Integer codConcurso, Modalidade modalidade, boolean leituraSuja);

	public void deleteByReservaCotaBolao(ReservaCotaBolaoPK idReserva);

	public List<Object[]> findByConcursoModalidadeSituacaoReservaCotaBolaoWithUR(Integer concurso, Integer modalidade);

	public Aposta<?> findByCodBolaoCodCota(String idBolao, String idCota);

	public Aposta<?> findByIdReservaCotaBolao(ReservaCotaBolaoPK idReserva);

	public Aposta<?> findByAposta(Long idAposta);

	public Long findCountAguardandoPagamentoPix(Integer codConcurso, Modalidade modalidade, Boolean cotas, Boolean leituraSuja);
}
