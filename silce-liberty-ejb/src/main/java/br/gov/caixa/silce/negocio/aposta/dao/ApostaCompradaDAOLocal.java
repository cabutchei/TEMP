package br.gov.caixa.silce.negocio.aposta.dao;

import java.util.List;

import javax.ejb.Local;

import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.silce.dominio.DadosResumoCotaLoterica;
import br.gov.caixa.silce.dominio.NSB;
import br.gov.caixa.silce.dominio.entidade.ApostaComprada;
import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta.Situacao;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra;
import br.gov.caixa.silce.dominio.entidade.SituacaoPremio;
import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.negocio.infra.dao.SilceDAO;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

@Local
public interface ApostaCompradaDAOLocal extends SilceDAO<ApostaComprada, Long> {
	
	List<Long> findWithUR(Situacao situacao, Data dataCorteAlteracaoSituacao, SituacaoPremio.Situacao... situacoesPremio);

	List<Long> findIdComprasWithUR(Situacao situacaoApostas, Data dataCorteAlteracaoSituacao, SituacaoCompra.Situacao situacaoCompra);

	public long findQuantidadeApostas(Long idCompra, Situacao... situacoes);

	public ApostaComprada findByNSB(NSB nsb);
	
	public ApostaComprada find(Long nsu, Data data);

	public ApostaComprada findNuvem(Long nsu);

	public List<Object[]> findCountSumValorGroupedByMeioPagamento(Data dataFinalizacaoProcessamento, Situacao... situacoes);

	public ResultadoPesquisaPaginada<ApostaComprada> findByDataSituacoes(int firstResult, int tamanhoPagina, Data dataSituacao, Meio meio, Situacao... situacoes);

	public ResultadoPesquisaPaginada<ApostaComprada> findByDataSituacoesSemCota(int firstResult, int tamanhoPagina, Data dataSituacao, Meio meio, Situacao... situacoes);

	ApostaComprada findByApostaApostador(Long idAposta, Long idApostador);

	List<Object[]> findCountSumValorByDataEfetivacaoGroupedByMeioPagamento(Data dataEfetivacao, boolean pesquisaComSubcanal, Situacao... situacoes);
	
	List<DadosResumoCotaLoterica> findCountSumCotasByDataEfetivacaoSituacaoLoterica(Data dataEfetivacao, SituacaoReservaCotaBolao.Situacao situacao, Integer codLoterica);

	public List<Decimal> findByCompraSituacao(Long idCompra, Data dataFinalizacaoProcessamento, Situacao... situacoes);

	List<Object[]> findCountSumValorByDataEfetivacaoGroupedByMeioPagamentoModalidade(Data dataEfetivacao, Modalidade modalidade);

	public long countTeimosinha(Long id);

	public long countApostasByCompra(Long idCompra);

	public List<Object[]> recupereApostasPremiosNaoAutorizados(Modalidade modalidade, CPF cpfApostador, Data dataInicio, Data dataFim, Long numeroCompra);

	public List<Object[]> findByConcursoModalidadeSituacaoCompraWithUR(Integer concurso, Integer modalidade, Boolean comprasPix);

	public List<Object[]> findByConcursoModalidadePremiosPendentesWithUR(Integer concurso, Integer modalidade);

	public List<ApostaComprada> findByCompraWithUR(Long numeroCompra);

}
