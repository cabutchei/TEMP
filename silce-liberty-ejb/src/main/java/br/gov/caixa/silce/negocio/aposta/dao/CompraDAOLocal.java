package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.List;

import javax.ejb.Local;

import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra.Situacao;
import br.gov.caixa.silce.negocio.infra.dao.SilceDAO;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

@Local
public interface CompraDAOLocal extends SilceDAO<Compra, Long> {

	public Compra findByApostador(Long idApostador, Situacao situacaoCompra);
	
	public Long findByApostadorAndSituacao(Long idApostador, Situacao... situacoes);

	public Compra findByNsuData(Long nsu, Data dataInicioCompra);
	
	public Compra findByNsuUltimaData(Long nsuSILCE, Data dataInicioCompra);

	public List<Compra> findBySituacao(Situacao situacao);

	List<Long> findBySituacaoWithUR(Situacao situacao);

	public List<Object[]> findBySituacoesDebitoIniciadoWithUR(Data data);
	
	public List<Object[]> findBySituacoesAguardandoPagamentoPixWithUR(Data data);
	
	public List<Object[]> findBySituacaoDataUltimaAlteracaoWithUR(Data data);

	public ResultadoPesquisaPaginada<Compra> findByDataInicioIgualDataFinalizacaoMaior(Data dataInicio, Data dataFim, int first, int pageSize);
	
	public ResultadoPesquisaPaginada<Compra> findByCompraPeriodo(Long idApostador, Data dataInicio, Data dataFim, 
		int first, int pageSize);
	
	public ResultadoPesquisaPaginada<Compra> findByCompraPeriodoMeioPagamento(Long idApostador, Data dataInicio, Data dataFim, Meio meioPagamento,
		int first, int pageSize);

	public ResultadoPesquisaPaginada<Compra> findByCompraPeriodoSituacao(Long idApostador, Data dataInicio, Data dataFim, Situacao situacao,
		int first, int pageSize);

	public ResultadoPesquisaPaginada<Compra> findByCompraPeriodoMeioPagamentoSituacao(Long idApostador, Data dataInicio, Data dataFim, Meio meioPagamento, Situacao situacao,
		int first, int pageSize);

	public ResultadoPesquisaPaginada<Compra> findByApostadorNumeroData(Long idApostador, Long numeroCompra, Long nsuSilce, Data data, Meio meiosDePagamento,
		int first, int pageSize);
	
	public List<Object[]> findCountSumValorGroupedByMeioPagamento(Data data);

	public ResultadoPesquisaPaginada<Compra> findByDataDebitoMeioPagamentoSituacoes(int firstResult, int tamanhoPagina, Data dataDebito, Meio meio, Situacao... situacoes);
	
	public Compra findByCompraApostador(Long idCompra, Long idApostador);

	public ResultadoPesquisaPaginada<Compra> findByDataFinalizacaoMeioPagamentoSituacoes(int firstResult, int tamanhoPagina, Data dataAtualizacao, Meio meio,
		Situacao... situacoes);

	public List<Object[]> findCountSumValorEstornosGroupedByMeioPagamento(Data data);

	public Compra findByIdComLoterica(Long idCompra);

	public List<Object[]> findSumarizacaoPorSituacao(Long idApostador, Data dataCorte);

	public Decimal findMediaComprasPorDia(Long idApostador, Data dataCorte);

	ResultadoPesquisaPaginada<Long> findEmProcessamentoByDataUltimaAlteracaoSituacaoWithUR(Data dataCorte, int maxResults);

	public Compra findByNsuAposta(Long nsu, Data data);

	public Compra findUltimaCompra(Long idApostador);

	List<Object[]> findQuantidadesComprasNegadasPorSubcanal(Data dataInicio, Data dataFim);

	List<Object[]> findQuantidadesCarrinhosAbandonados(Data dataInicio, Data dataFim);

	public Data findDataMinFinalizada();

	public List<Object[]> findComprasSimulandoComissao(Data dataInicio, Data dataFim, Integer qtdMeioPagamento, Integer intervaloMudarMeio);

	public List<Compra> findUltimasComprasDiaAnterior(Long idApostador, Data data24h);

	public List<Compra> findBySituacoesPixCanceladasPagamentoNaoIdentificadoWithUR();
}
