package br.gov.caixa.negocio.infra.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.util.CaixaEnum;

/**
 * Interface DAO para operações CRUD.
 * 
 * @author p552259
 * @param <T> Classe da entitidade a ser manipulada pela DAO.
 */
public interface DAO<T extends AbstractEntidade<I>, I extends Serializable> extends Serializable {

	/**
	 * Insere os dados da entity na fonte de dados.
	 * 
	 * @param entity
	 */
	public void insert(T entity);

	/**
	 * Atualiza os dados da entity na fonte de dados.
	 * 
	 * @param entity
	 */
	public T update(T entity);

	/**
	 * Exclui os dados da entity na fonte dados.
	 * 
	 * @param entity
	 */
	public void delete(T entity);

	public int deleteAll();

	/**
	 * Exclui os dados da entity através do sey ID.
	 * 
	 * @param id
	 */
	public void deleteById(I id);

	/**
	 * Remove a entidade do contexto de persistência. Útil para trabalhar com duas entidades que possuem o mesmo ID,
	 * porém estão com dados diferentes (valores novos em uma e valores antigos na outra, por exemplo).
	 */
	public void detach(T entity);

	/**
	 * Recupera uma instância do entity dado o seu objeto Id. Caso não exista entidade com o respectivo ID, retorna null
	 * 
	 * @param id
	 * @return
	 */
	public T findById(I id);

	/**
	 * Recupera uma instância do entity dado o seu objeto Id e força o fetch para eager das classes parametrizadas no
	 * forceFetch. Caso não exista entidade com o respectivo ID, retorna null.
	 * 
	 * @param id, forceFetch
	 * @return
	 */
	public T findById(I id, Map<Class<?>, String> forceFetch);

	/**
	 * Recupera todas as instâncias do entity na fonte dados.
	 * 
	 * @return
	 */
	public List<T> findAll();

	public void flush();

	public void refresh(T t);

	public T findById(CaixaEnum<I> id);

	public Map<String, Serializable> execNativeQueries(List<String> queries);

	public Serializable execNativeQuery(String query);

	public <R> R execSingleSelectByNativeQuery(String query, Object... parameters);

	public <R> List<R> execSelectByNativeQuery(String query, Object... parameters);

	/**
	 * Usa uma query para fazer o find, ao invés de utilizar o findById do EntityManager. Dessa forma, entidades que não
	 * estão realmente no banco não serão retornadas.
	 */
	public T findByIdUsingQuery(I id);

	/**
	 * Recupera uma referência à entidade. Esse método não vai ao banco para recuperar a entidade, e seus atributos não
	 * vem preenchidos. Útil para recuperar uma referência apenas para fazer uma exclusão, por exemplo.
	 */
	public T findReferenceById(I id);

	/**
	 * Recupera uma instância do entity dado o seu objeto Id. Caso não exista entidade com o respectivo ID, retorna
	 * null.
	 * 
	 * @param id
	 *            identificador da entidade
	 * @param writeLock
	 *            se irá ser feito um lock de escrita nessa entidade
	 * @return a entidade buscada
	 */
	T findById(I id, Boolean writeLock);

}
