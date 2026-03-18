package br.gov.caixa.silce.negocio.apostador.dao;

import java.util.List;

import javax.ejb.Local;

import br.gov.caixa.silce.dominio.entidade.BairroVO;
import br.gov.caixa.silce.dominio.entidade.Loterica;
import br.gov.caixa.silce.dominio.entidade.MunicipioPK;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.negocio.infra.dao.SilceDAO;
import br.gov.caixa.util.CEP;
import br.gov.caixa.util.Data;

/**
 * Interface da DAO de Unidade lotérica.
 */
@Local
public interface LotericaDAOLocal extends SilceDAO<Loterica, Long> {

	/**
	 * Busca todas as lotéricas que pertencem ao Bairro passado por parâmetro. Faz distinct por bairro para contornar o
	 * problema de vários bairros com o mesmo nome.
	 * 
	 * @return Coleção de lotéricas de um determinado Bairro.
	 */
	public List<Loterica> findByDistinctNomeBairroOrderRandom(BairroVO bairroVO, Long[] situacoes, Long[] categorias);

	/**
	 * 
	 * @param bairroVO
	 * @return
	 */
	public List<Loterica> findByCEPOrderRandom(CEP cep, Long[] situacoes, Long[] categorias);

	/**
	 * Busca uma única lotérica que apresentem um determinado código lotérico.
	 * 
	 * @param coUnddeLoterica
	 *            código da Unidade lotérica a ser pesquisada.
	 * @return Uma Unidade lotérica que apresente o código parametrizado.
	 */
	public Loterica findByCodigo(Long polo, Long id, Long dv);

	/**
	 * Busca todas as lotéricas que apresentem um determinado Nome Fantasia.
	 * 
	 * @param nomeFantasia Nome Fantasia das Unidades lotéricas a serem pesquisadas.
	 * @return Coleção com as Unidades lotéricas que apresentem o Nome Fantasia informado.
	 */
	public List<Loterica> findByNomeFantasiaOrderRandom(String nomeFantasia, Long[] situacoes, Long[] categorias);

	public List<Object[]> findByCodUlDataArrecadacao(int codigoUL, Data inicio, Data fim);
	
	public List<Object[]> findByModalidadeDataArrecadacao(Modalidade modalidade, Data inicio, Data fim);

	/**
	 * Busca todas lotéricas de um município
	 * 
	 * @param idMunicipio
	 * @return
	 */
	public List<Loterica> findByMunicipioOrderRandom(MunicipioPK idMunicipio, Long[] situacoes, Long[] categorias);

	/**
	 * Busca todas lotéricas de um estado
	 * 
	 * @param idUf
	 * @return
	 */
	public List<Loterica> findByUfOrderRandom(Long idUf, Long[] situacoes, Long[] categorias);

	public List<Loterica> findByNomeCodigo(String nome, Integer codigo);

}
