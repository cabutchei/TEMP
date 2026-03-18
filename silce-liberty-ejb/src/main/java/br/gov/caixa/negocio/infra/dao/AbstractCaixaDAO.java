package br.gov.caixa.negocio.infra.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TemporalType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;
import org.apache.openjpa.persistence.FetchPlan;
import org.apache.openjpa.persistence.OpenJPAQuery;
import org.apache.openjpa.persistence.jdbc.IsolationLevel;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.CollectionUtil;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.GenericsUtil;
import br.gov.caixa.util.LocaleUtil;
import br.gov.caixa.util.StringUtil;

/**
 * <p>
 * DAO que abstrai a utilização do JPA, fornecendo métodos para:
 * </p>
 * <ul>
 * <li>Realizar consultas (queries, namedQueries, nativeQueries) que retornam um único resultado, em qualquer
 * formato;</li>
 * <li>Realizar consultas (queries, namedQueries, nativeQueries) que retornam múltiplos resultados, em qualquer formato;
 * </li>
 * <li>Receber queries de "SELECT xxx" e transformar em "SELECT count(xxx)", para saber quantos resultados a consulta
 * retornaria;</li>
 * <li>Montagem de queries em String;</li>
 * <li>Montar a clásula WHERE das queries a partir de mapa de parâmetros;</li>
 * <li>Converter coleções de ENUM em uma coleção de IDs desses ENUMs;</li>
 * <li>Forçar fetch de relacionamentos de entidades, baseado em mapas de atributos;</li>
 * <li>Entre outros.</li>
 * </ul>
 * <p>
 * Nem todas as combinações possíveis de métodos utilitários foram implementadas, pois não seriam utilizados. Pode
 * existir a necessidade de se implementar uma combinação nova de parâmetros para um método que tenha a mesma função
 * básica. Esses casos devem ser analisados conforme a necessidade.
 * </p>
 * 
 * @author c101482 - Vinicius Ferraz Campos Florentino
 * @author c127237
 * @param <T>
 *            Tipo da Entidade
 * @param <I>
 *            Tipo do ID da Entidade
 */
/**
 * @author c101482
 *
 * @param <T>
 * @param <I>
 */
public abstract class AbstractCaixaDAO<T extends AbstractEntidade<I>, I extends Serializable> implements DAO<T, I> {

	private static final String SELECT_ENTIDADE_FROM = "Select entidade from ";
	private static final String SPACE = " ";

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(AbstractCaixaDAO.class, new MessageFormatMessageFactory());

	// mapa a ser utilizado ao chamar métodos que recebem o mapa, mas não é necessário
	private static final Map<Class<?>, String> EMPTY_FORCE_FETCH = Collections.emptyMap();

	// utilizado em método utilitário para colocar WILDCARD em parâmetros
	private static final char WILDCARD = '%';

	// colocado em queries para não haver necessidade de verificar se existem ou não parâmetros na query
	private static final String WHERE_TRUE_TRUE = " where true=true ";

	// utilizados para encontrar indexes nas strings de query jpql
	private static final String FROM = "from";
	private static final String WHERE = "where";
	private static final String ORDER_BY = "order by";
	private static final String HAVING = "having";
	private static final String GROUP_BY = "group by";

	// pattern que procura o alias da primeira entidade colocada na cláusula FROM
	// ex.: "SELECT compra from Compra compra join compra.apostador" irá encontrar "compra"
	private static final Pattern ALIAS_SEARCH_PATTERN = Pattern.compile("(.*from +[^ ]+ +)([^ ]+)(.*)");

	// pattern que procura os parâmetros declarados na query
	private static final Pattern PARAMETER_POSITION_PATTERN = Pattern.compile("(\\?)(\\d+)");

	// string de queries utilizadas comumente pelas DAOs filhas, portanto já ficam prontas para serem utilizadas
	protected final String selectQuery = SELECT_ENTIDADE_FROM + getEntityTypeClass().getSimpleName() + " entidade ";
	protected final String selectQueryOrderByIdDesc = selectQuery + " order by entidade.id desc ";
	protected final String selectQueryOrderByIdAsc = selectQuery + " order by entidade.id asc ";
	protected final String selectByIdQuery = SELECT_ENTIDADE_FROM + getEntityTypeClass().getSimpleName() + " entidade where entidade.id = ?1";
	protected final String deleteQuery = "delete from " + getEntityTypeClass().getSimpleName() + " entidade";

	protected abstract EntityManager getEntityManager();

	// Métodos sobrescritos da interface DAO

	@Override
	public void insert(T entity) {
		getEntityManager().persist(entity);
	}

	@Override
	public T update(T entity) {
		return getEntityManager().merge(entity);
	}

	@Override
	public void delete(T entity) {
		getEntityManager().remove(entity);
	}

	@Override
	public void deleteById(I id) {
		T obj = findReferenceById(id);
		getEntityManager().remove(obj);
	}

	public int deleteAll() {
		Query query = getEntityManager().createQuery(deleteQuery);
		return query.executeUpdate();
	}

	@Override
	public void detach(T entity) {
		getEntityManager().detach(entity);
	}

	@Override
	public void flush() {
		getEntityManager().flush();
	}

	@Override
	public void refresh(T t) {
		getEntityManager().refresh(t);
	}


	@Override
	public T findById(I id) {
		return findById(id, Boolean.FALSE);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(I id, Boolean writeLock) {
		Class<?> entityTypeClass = getEntityTypeClass();
		T entidade = (T) getEntityManager().find(entityTypeClass, id);

		if (writeLock) {
			Table tableAnnotation = entityTypeClass.getAnnotation(Table.class);
			String nomeTabela = tableAnnotation.name();
			String nomeSchema = tableAnnotation.schema();

			Field idField = null;
			try {
				idField = entityTypeClass.getDeclaredField("id");
			} catch (NoSuchFieldException e) {
				throw new RuntimeException("Erro ao pegar field para fazer lock", e);
			} catch (SecurityException e) {
				throw new RuntimeException("Erro ao pegar field para fazer lock", e);
			}

			Column columnAnnotation = idField.getAnnotation(Column.class);
			String colunaId = columnAnnotation.name();

			String sql = "SELECT " + colunaId + " FROM " + nomeSchema + "." + nomeTabela + " WHERE " + colunaId
				+ " = ?1 optimize for 1 row FOR READ ONLY WITH RR USE AND KEEP UPDATE LOCKS";
			execSelectByNativeQuery(sql, id);
			refresh(entidade);
		}

		return entidade;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findReferenceById(I id) {
		return (T) getEntityManager().getReference(getEntityTypeClass(), id);
	}

	@Override
	public T findByIdUsingQuery(I id) {
		return findById(id, Collections.<Class<?>, String> emptyMap());
	}

	@Override
	public T findById(I id, Map<Class<?>, String> forceFetch) {
		Query query = getEntityManager().createQuery(selectByIdQuery);
		initParameters(query, id);
		forceFetch(query, forceFetch);
		return getSingleResult(query);
	}

	@Override
	public T findById(CaixaEnum<I> id) {
		return findById(id.getValue());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return (List<T>) getEntityManager().createQuery(selectQuery).getResultList();
	}

	// -- Métodos de executeUpdate

	protected int execUpdateByNamedQuery(String namedQuery, Object... params) {
		Query query = createNamedQuery(namedQuery, params);
		return query.executeUpdate();
	}

	// -- Métodos para executar COUNT

	/**
	 * Executa o COUNT da query informada, trocando tudo que estiver na clásula select por uma função COUNT(alias),
	 * utilizando o alias da primeira entidade declarada na clásula FROM.
	 * 
	 * @param originalQuery
	 *            A query que será "contada"
	 * @return A quantidade de linhas que seria retornada pela query informada
	 */
	protected Long execCountForQuery(Query originalQuery) {
		String countQuery = replaceSelectStatementByCount(unwrap(originalQuery));
		Query query = getEntityManager().createQuery(countQuery);
		applyHints(getHintsMapWithUR(), query);
		preencheParametrosCount(originalQuery, query);

		return getSingleResult(query);
	}

	/**
	 * Pega os mesmos valores dos parâmetros da query original e aplica na query de count.
	 */
	private void preencheParametrosCount(Query originalQuery, Query countQuery) {
		for (Parameter<?> parameter : originalQuery.getParameters()) {
			Integer position = parameter.getPosition();
			if (position != null) {
				countQuery.setParameter(position, originalQuery.getParameterValue(parameter));
			} else {
				countQuery.setParameter(parameter.getName(), originalQuery.getParameterValue(parameter));
			}
		}
	}

	/**
	 * Executa o count da {@link NamedQuery} informada.
	 * 
	 * @see #execCountForQuery(Query)
	 */
	protected Long execCountForNamedQuery(String namedQuery, Object... parameters) {
		Query query = createNamedQuery(namedQuery, parameters);
		return execCountForQuery(query);
	}

	// -- Métodos para recuperar SingleResult

	/**
	 * <p>
	 * Monta a query baseada na String JPQL informada e nos parâmetros passados. A String PODE possuir clásula WHERE,
	 * porém os parâmetros devem informados utilizando ALIAS, conforme o exemplo abaixo. Exemplo de parâmetro a ser
	 * utilizado como filtro:
	 * </p>
	 * <code>Map<String, Object> parametros = newParametersMap();</code><br>
	 * <code>parameters.put(":dataInicioCompra", dataAtual);</code><br>
	 * <code>parameters.put(":horaInicioCompra", horaAtualMenosDezMinutos);</code><br>
	 * <code>parametros.put("and entidade.cpf LIKE", addWildCards(cpf.getCpfSemMascara()));</code><br>
	 * <code>parametros.put("and entidade.situacao.id =", situacao.getValue());</code><br>
	 * <br>
	 * 
	 * @param queryCommand
	 *            A String para montar a query, sem clásula WHERE; podem ser útil: {@link #selectQuery},
	 *            {@link #selectQueryOrderByIdAsc}, {@link #selectQueryOrderByIdDesc}
	 * @param parameters
	 *            Os parâmetros que servirão de filtro para a query; parâmetros null ou vazios não serão aplicados
	 * @return Um único resultado obtido através da execução da query; null caso não haja resultado
	 */
	protected <A> A getSingleResultByQuery(String queryCommand, Map<String, Object> parameters) {
		Query query = createQuery(queryCommand, parameters);
		return getSingleResult(query);
	}

	/**
	 * Execura a named query sem necessidade de forçar o FETCH de nenhum relacionamento.
	 * 
	 * @see #getSingleResultByNamedQuery(String, Map, Object...)
	 */
	protected <A> A getSingleResultByNamedQuery(String namedQuery, Object... parameters) {
		return getSingleResultByNamedQuery(namedQuery, EMPTY_FORCE_FETCH, parameters);
	}

	/**
	 * Executa a {@link NamedQuery} com os parâmetros informados, forçando o FETCH de relacionamentos informados.
	 * 
	 * @param namedQuery
	 *            A {@link NamedQuery} a ser executada
	 * @param forceFetch
	 *            Os relacionamentos (classe -> atributo) que devem ser retornados pela consulta, mesmo que sejam
	 *            originalmente {@link FetchType#LAZY}
	 * @param parameters
	 *            Os parâmetros que servirão de filtros
	 * @return Um único resultado obtido através da execução da query; null caso não haja resultado
	 */
	protected <A> A getSingleResultByNamedQuery(String namedQuery, Map<Class<?>, String> forceFetch, Object... parameters) {
		Query query = createNamedQuery(namedQuery, parameters);
		forceFetch(query, forceFetch);
		return getSingleResult(query);
	}

	/**
	 * Recupera um único resultado a partir da query nativa informada. Não há tratamento na String de SQL informada.
	 * 
	 * @param nativeQueryCommand
	 *            Uma query em SQL nativo.
	 * @return Um único resultado obtido através da execução da query; null caso não haja resultado
	 */
	protected <A> A getSingleResultByNativeQuery(String nativeQueryCommand) {
		Query query = getEntityManager().createNativeQuery(nativeQueryCommand);
		return getSingleResult(query);
	}

	protected <A> A getSingleResultByNativeQuery(String nativeQueryCommand, Object... parameters) {
		Query query = getEntityManager().createNativeQuery(nativeQueryCommand);
		initParameters(query, parameters);
		return getSingleResult(query);
	}

	protected <A> A getSingleResultByNativeQuery(String nativeQuery, Class<?> clazz, Object... parameters) {
		Query query = getEntityManager().createNativeQuery(nativeQuery, clazz);
		initParameters(query, parameters);
		return getSingleResult(query);
	}

	// -- Métodos para recuperar ResultList

	/**
	 * @return uma lista de resultados obtido através da execução da query
	 * @see #getSingleResultByQuery(String, Map)
	 */
	protected <A> List<A> getResultListByQuery(String queryCommand, Map<String, Object> parameters) {
		return getResultListByQuery(queryCommand, parameters, Collections.<String, String> emptyMap());
	}

	/**
	 * @return uma lista de resultados obtido através da execução da query, aplicando os hints informados
	 * @see #getSingleResultByQuery(String, Map)
	 */
	protected <A> List<A> getResultListByQuery(String queryCommand, Map<String, Object> parameters, Map<String, String> hints) {
		Query query = createQuery(queryCommand, parameters);
		applyHints(hints, query);
		return getResultList(query);
	}

	/**
	 * @return uma lista de resultados obtido através da execução da query, aplicando os hints informados e com Max
	 *         fecth Depth modificado para atender a necessiade de que chama.
	 * @see #getSingleResultByQuery(String, Map)
	 */
	protected <A> List<A> getResultListByQueryWithMaxDepth(String queryCommand, Map<String, Object> parameters, Map<String, String> hints, Integer maxDepth) {
		Query query = createQuery(queryCommand, parameters);
		applyHints(hints, query);
		setMaxFetchDepth(maxDepth, query);
		return getResultList(query);
	}

	/**
	 * Executa uma pesquisa paginada utilizando a query informada. Ver outros métodos para explicação de outros
	 * parâmetros.
	 * 
	 * @param firstResult
	 *            index do primeiro resultado da pesquisa que deverá ser considerado no retorno
	 * @param maxResults
	 *            quantidade página de resultados do retorno
	 * @return o resultado da query, limitado pelo index e maxResults
	 * @see #getSingleResultByQuery(String, Map)
	 * @see #getSingleResultByNamedQuery(String, Map, Object...)
	 */
	protected <A extends Serializable> ResultadoPesquisaPaginada<A> getResultListByQuery(int firstResult, int maxResults, String queryCommand,
		Map<Class<?>, String> forceFetch, Map<String, Object> parameters, Map<String, String> hints) {
		return getResultListByQueyWithMaxDepth(firstResult, maxResults, queryCommand, forceFetch, parameters, hints, null);
	}

	/**
	 * Executa uma pesquisa paginada utilizando a query informada. Ver outros métodos para explicação de outros
	 * parâmetros.
	 * 
	 * @param firstResult
	 *            index do primeiro resultado da pesquisa que deverá ser considerado no retorno
	 * @param maxResults
	 *            quantidade página de resultados do retorno
	 * @param maxDepth
	 *            profundidade maxima de join fecth
	 * @return o resultado da query, limitado pelo index e maxResults
	 * @see #getSingleResultByQuery(String, Map)
	 * @see #getSingleResultByNamedQuery(String, Map, Object...)
	 */
	public <A extends Serializable> ResultadoPesquisaPaginada<A> getResultListByQueyWithMaxDepth(int firstResult, int maxResults, String queryCommand,
		Map<Class<?>, String> forceFetch,
		Map<String, Object> parameters, Map<String, String> hints, Integer maxDepth) {
		Query query = createQuery(queryCommand, parameters);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		applyHints(hints, query);
		if (maxDepth != null) {
			forceMaxFetch(query, maxDepth);
		}
		forceFetch(query, forceFetch);
		List<A> dados = getResultList(query);
		Long rowCount = execCountForQuery(query);
		return new ResultadoPesquisaPaginada<A>(rowCount, dados);
	}


	/**
	 * @see #getResultListByQuery(int, int, String, Map, Map, Map)
	 */
	protected <A extends Serializable> ResultadoPesquisaPaginada<A> getResultListByQuery(int firstResult, int maxResults, String queryCommand,
		Map<Class<?>, String> forceFetch, Map<String, Object> parameters) {
		return getResultListByQuery(firstResult, maxResults, queryCommand, forceFetch, parameters, Collections.<String, String> emptyMap());
	}

	/**
	 * Executa a pesquisa paginada sem necessidade de forçar o FETCH de nenhum relacionamento.
	 * 
	 * @see #getResultListByQuery(int, int, String, Map, Map)
	 */
	protected <A extends Serializable> ResultadoPesquisaPaginada<A> getResultListByQuery(int firstResult, int maxResults, String queryCommand,
		Map<String, Object> parameters) {
		return getResultListByQuery(firstResult, maxResults, queryCommand, EMPTY_FORCE_FETCH, parameters);
	}

	/**
	 * Executa a {@link NamedQuery} com os parâmetros informados e a maxDepth.
	 * 
	 * @see #getResultListByNamedQuery(String, Object...)
	 */
	protected <A> List<A> getResultListByNamedQueryWithMaxDepth(String namedQuery, Integer maxDepth, Object... parameters) {
		Query query = createNamedQuery(namedQuery, parameters);
		setMaxFetchDepth(maxDepth, query);
		return getResultListByNamedQuery(query);
	}

	/**
	 * Executa a {@link NamedQuery} com os parâmetros informados e a maxDepth.
	 * 
	 * @see #getSingleResultByNamedQuery(String, Object...)
	 */
	protected <A> A getSingleResultByNamedQueryWithMaxDepth(String namedQuery, Integer maxDepth, Object... parameters) {
		Query query = createNamedQuery(namedQuery, parameters);
		setMaxFetchDepth(maxDepth, query);
		return getSingleResult(query);
	}

	/**
	 * Executa a {@link NamedQuery} com os parâmetros informados e a maxDepth.
	 * 
	 * @see #getSingleResultByNamedQuery(String, Object...)
	 */
	protected <A> A getSingleResultByNamedQueryWithMaxDepth(String namedQuery, Integer maxDepth, Integer maxResults, Object... parameters) {
		Query query = createNamedQuery(namedQuery, parameters);
		query.setMaxResults(maxResults);
		setMaxFetchDepth(maxDepth, query);
		return getSingleResult(query);
	}

	private void setMaxFetchDepth(Integer maxDepth, Query query) {
		if (maxDepth != null && maxDepth > 0) {
			FetchPlan fetchPlan = query.unwrap(OpenJPAQuery.class).getFetchPlan();
			fetchPlan.setMaxFetchDepth(maxDepth);
		}
	}

	/**
	 * Executa a {@link NamedQuery} com os parâmetros informados.
	 * 
	 * @see #getSingleResultByNamedQuery(String, Object...)
	 */
	protected <A> List<A> getResultListByNamedQuery(String namedQuery, Object... parameters) {
		Query query = createNamedQuery(namedQuery, parameters);
		return getResultListByNamedQuery(query);
	}

	private <A> List<A> getResultListByNamedQuery(Query query) {
		return getResultList(query);
	}

	protected <A> List<A> getResultListByNativeQuery(String nativeQuery, Object... parameters) {
		Query query = getEntityManager().createNativeQuery(nativeQuery);
		initParameters(query, parameters);
		return getResultList(query);
	}

	protected <A> List<A> getResultListByNativeQuery(String nativeQuery, Class<?> clazz, Object... parameters) {
		Query query = getEntityManager().createNativeQuery(nativeQuery, clazz);
		initParameters(query, parameters);
		return getResultList(query);
	}

	/**
	 * Executa a {@link NamedQuery} com os parâmetros informados.
	 * 
	 * @see #getResultListByQuery(int, int, String, Map, Map)
	 */
	@SuppressWarnings("unchecked")
	protected <A extends Serializable> ResultadoPesquisaPaginada<A> getResultListByNamedQuery(int firstResult, int maxResults, String namedQuery,
		Object... parameters) {
		return getResultListByNamedQuery(firstResult, maxResults, true, Collections.EMPTY_MAP, namedQuery, parameters);
	}

	protected <A extends Serializable> ResultadoPesquisaPaginada<A> getResultListByNamedQuery(int firstResult, int maxResults, Map<Class<?>, String> newForceFetchMap,
		String namedQuery,
		Object... parameters) {
		return getResultListByNamedQuery(firstResult, maxResults, true, newForceFetchMap, namedQuery, parameters);
	}

	@SuppressWarnings("unchecked")
	protected <A extends Serializable> ResultadoPesquisaPaginada<A> getResultListByNamedQuery(int firstResult, int maxResults, boolean execCount,
		String namedQuery,
		Object... parameters) {
		return getResultListByNamedQuery(firstResult, maxResults, execCount, Collections.EMPTY_MAP, namedQuery, parameters);
	}

	protected <A extends Serializable> ResultadoPesquisaPaginada<A> getResultListByNamedQuery(int firstResult, int maxResults, boolean execCount, Map<Class<?>, String> forceFetch,
		String namedQuery, Object... parameters) {
		return getResultListByNamedQuery(firstResult, maxResults, execCount, forceFetch, null, namedQuery, parameters);
	}

	protected <A extends Serializable> ResultadoPesquisaPaginada<A> getResultListByNamedQuery(int firstResult, int maxResults, boolean execCount, Map<Class<?>, String> forceFetch,
		Integer maxFetchDepth, String namedQuery, Object... parameters) {
		Query query = createNamedQuery(namedQuery, parameters);
		setMaxFetchDepth(maxFetchDepth, query);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		forceFetch(query, forceFetch);
		List<A> dados = getResultList(query);
		if (execCount) {
			Long rowCount = execCountForQuery(query);
			return new ResultadoPesquisaPaginada<A>(rowCount, dados);
		}
		return new ResultadoPesquisaPaginada<A>(maxResults, dados);
	}

	// -- Métodos "utilitários" para DAOs filhas

	/**
	 * @return uma lista com os IDs (value) de todos os ENUMs informados.
	 * @see #convertToIdList(Collection)
	 */
	protected <X> List<X> convertToIdList(CaixaEnum<X>... enums) {
		if (enums.length == 0) {
			return Collections.emptyList();
		}

		List<X> idsSituacoes = new ArrayList<X>(enums.length);
		for (CaixaEnum<X> caixaEnum : enums) {
			if (caixaEnum != null) {
				idsSituacoes.add(caixaEnum.getValue());
			}
		}
		return idsSituacoes;
	}

	/**
	 * @see #convertToIdList(CaixaEnum...)
	 */
	protected <X> List<X> convertToIdList(Collection<? extends CaixaEnum<X>> situacoes) {
		List<X> idsSituacoes = new ArrayList<X>(situacoes.size());
		for (CaixaEnum<X> situacao : situacoes) {
			idsSituacoes.add(situacao.getValue());
		}
		return idsSituacoes;
	}

	/**
	 * Adiciona {@link #WILDCARD}({@value #WILDCARD}) no início e no final da string informada.
	 * 
	 * @return A string com WildCard; null caso a string informada seja null
	 */
	protected String addWildCards(String param) {
		return addWildCards(param, null);
	}

	protected String addWildCards(String param, Character escapeCharacterToChange) {
		StringBuilder append = new StringBuilder().append(WILDCARD);
		if (escapeCharacterToChange != null) {
			return append.append(changeEscapeCharacter(param, escapeCharacterToChange)).append(WILDCARD).toString();
		}
		return append.append(param).append(WILDCARD).toString();
	}

	protected static String removeAcentosAndUpper(String str) {
		return StringUtil.removeAcentosAndToUpper(str);
	}

	/**
	 * Verifica se o param tem algum caracter especial: % ou _, caso existe, substitui o escape por \
	 * 
	 * @param param
	 * @param escapeCharacterToChange
	 * @return
	 */
	private String changeEscapeCharacter(String param, Character escapeCharacterToChange) {
		StringBuilder sb = new StringBuilder(param);
		for (int i = sb.length() - 1; i >= 0; i--) {
			if (isWildCard(sb.charAt(i))) {
				sb.insert(i, escapeCharacterToChange);
			}
		}
		return sb.toString();
	}

	private boolean isWildCard(char charAt) {
		return charAt == '%' || charAt == '_';
	}

	/**
	 * @return um novo mapa que servirá para fazer forçar o fetch de relacionamentos
	 */
	protected Map<Class<?>, String> newForceFetchMap() {
		return new HashMap<Class<?>, String>();
	}

	/**
	 * faz o put dos parâmetros no mapa
	 * 
	 * @see #newForceFetchMap()
	 */
	protected Map<Class<?>, String> newForceFetchMap(Class<?> entityClass, String attributo) {
		Map<Class<?>, String> newForceFetchMap = newForceFetchMap();
		newForceFetchMap.put(entityClass, attributo);
		return newForceFetchMap;
	}

	/**
	 * @return um novo mapa que servirá para guardar parâmetros a serem utilizados na query
	 */
	protected Map<String, Object> newParametersMap() {
		return new LinkedHashMap<String, Object>();
	}

	/**
	 * @return um novo mapa que servirá para guardar hints a serem utilizados na query, já com o parâmetro de
	 *         READ_UNCOMMITTED adicionado
	 */
	protected HashMap<String, String> getHintsMapWithUR() {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("openjpa.FetchPlan.Isolation", IsolationLevel.READ_UNCOMMITTED.toString());
		return hashMap;
	}

	/**
	 * @return a String da query
	 */
	protected abstract String unwrap(Query query);

	/**
	 * Força o fetch de relacionamentos informados no mapa, para a query informada
	 */
	protected abstract void forceFetch(Query query, Map<Class<?>, String> forceFetch);

	/**
	 * Força o o max depth da query
	 */
	protected abstract void forceMaxFetch(Query query, Integer forceFetch);

	// -- Métodos para facilitar recuperar os resultados sem precisar ficar fazendo cast

	/**
	 * Facilita a recuperação de SingleResult, retornando null caso não haja resultado e fazendo transfornamções
	 * necessárias para classes padrão CAIXA. Remove a necessidade de cast explícito.
	 */
	@SuppressWarnings("unchecked")
	private <A> A getSingleResult(Query q) {
		try {
			long inicio = System.currentTimeMillis();

			q.setMaxResults(1);
			q.setFirstResult(0);

			Object singleResult = q.getSingleResult();
			if (singleResult instanceof BigDecimal) {
				return (A) new Decimal((BigDecimal) singleResult);
			}
			if (singleResult instanceof Date) {
				return (A) new Data((Date) singleResult);
			}

			logDuration(q, inicio, "getSingleResult");

			return (A) singleResult;
		} catch (NoResultException e) {
			LOG.debug("Resultado nao encontrado ao tentar recuperar um resultado unico: {0}", e.getMessage());
			return null;
		}
	}

	/**
	 * Facilita a recuperação de SingleResult, retornando uma lista vazia caso não haja resultado. Remove a necessidade
	 * de cast explícito.
	 */
	@SuppressWarnings("unchecked")
	private <A> List<A> getResultList(Query q) {
		long inicio = System.currentTimeMillis();
		List<A> resultList = (List<A>) q.getResultList();

		logDuration(q, inicio, "getResultList");

		return resultList;
	}

	private void logDuration(Query q, long inicio, String metodo) {
		if (LOG.isInfoEnabled()) {
			long duration = System.currentTimeMillis() - inicio;
			if (duration >= 1000) {
				LOG.info("TEMPOSILCE DB {0} Query {1}: {2}ms", metodo, q.toString(), duration);
			}
		}
	}

	// -- Métodos para criação das Queries

	private void applyHints(Map<String, String> hints, Query query) {
		Set<String> keySet = hints.keySet();
		for (String key : keySet) {
			query.setHint(key, hints.get(key));
		}
	}

	private Query createQuery(String queryCommand, Map<String, Object> parameters) {
		if (!parameters.isEmpty()) {
			Map<String, Object> linkedParameters = getLinkedParameters(parameters);
			StringBuilder finalQuery = new StringBuilder(queryCommand);
			int indexOfParameters = initQueryWhereClause(finalQuery, linkedParameters);
			Query query = getEntityManager().createQuery(finalQuery.toString());
			return initParameters(query, linkedParameters, indexOfParameters);
		} else {
			return getEntityManager().createQuery(queryCommand);
		}
	}

	private Map<String, Object> getLinkedParameters(Map<String, Object> parameters) {
		return parameters instanceof LinkedHashMap ? parameters : new LinkedHashMap<String, Object>(parameters);
	}

	private Query createNamedQuery(String namedQuery, Object... parameters) {
		Query query = getEntityManager().createNamedQuery(namedQuery);
		return initParameters(query, parameters);
	}

	private String replaceSelectStatementByCount(String originalQuery) {
		String jpqlLowercase = originalQuery.toLowerCase(LocaleUtil.PT_BR);
		StringBuilder sb = new StringBuilder(originalQuery);

		// remove order by
		int indexOfOrderBy = jpqlLowercase.indexOf(ORDER_BY);
		if (indexOfOrderBy > 0) {
			sb.delete(indexOfOrderBy, sb.length());
		}

		// replace select with count
		String aliasName = getAliasName(jpqlLowercase);
		int indexOfFrom = jpqlLowercase.indexOf(FROM);
		sb.replace(0, indexOfFrom, "select count(" + aliasName + ") ");

		return sb.toString();
	}

	private String getAliasName(String jpqlLowercase) {
		Matcher matcher = ALIAS_SEARCH_PATTERN.matcher(jpqlLowercase);
		matcher.matches();
		return matcher.group(2);
	}

	private int initQueryWhereClause(StringBuilder queryCommand, Map<String, Object> parameters) {
		int indexOfParameters = getIndexOfParameters(queryCommand);

		int initialParameterPosition = getInitialParameterPosition(queryCommand);
		int i = initialParameterPosition;
		for (Entry<String, Object> mapEntry : parameters.entrySet()) {
			if (!isParameterPosition(mapEntry.getKey()) && isValorPreenchido(mapEntry.getValue())) {
				queryCommand.insert(indexOfParameters, SPACE + mapEntry.getKey() + " ?" + i + SPACE);
				i++;
			}
		}
		return initialParameterPosition;
	}

	private int getInitialParameterPosition(CharSequence queryCommand) {
		int biggest = -1;
		Matcher matcher = PARAMETER_POSITION_PATTERN.matcher(queryCommand);
		while (matcher.find()) {
			int parseInt = Integer.parseInt(matcher.group(2));
			biggest = Math.max(biggest, parseInt);
		}
		return (biggest == -1) ? 1 : (biggest + 1);
	}

	private boolean isParameterPosition(String key) {
		return key.charAt(0) == '?';
	}

	private int getIndexOfParameters(StringBuilder sb) {
		String jpqlLowercase = sb.toString().toLowerCase(LocaleUtil.PT_BR);
		NavigableSet<Integer> indexes = new TreeSet<Integer>();
		indexes.add(jpqlLowercase.indexOf(ORDER_BY));
		indexes.add(jpqlLowercase.indexOf(HAVING));
		indexes.add(jpqlLowercase.indexOf(GROUP_BY));

		Integer indexOfClauseAfterWhere = indexes.ceiling(0);
		int indexOfWhere = sb.indexOf(WHERE);

		if (indexOfClauseAfterWhere == null) {
			if (indexOfWhere < 0) {
				sb.append(WHERE_TRUE_TRUE);
			}
			return sb.length();
		} else if (indexOfWhere < 0) {
			sb.insert(indexOfClauseAfterWhere, WHERE_TRUE_TRUE);
			return indexOfClauseAfterWhere + WHERE_TRUE_TRUE.length();
		} else {
			return indexOfClauseAfterWhere;
		}
	}

	private Query initParameters(Query query, Map<String, Object> parameters, int indexOfParameters) {
		int i = indexOfParameters;
		for (Entry<String, Object> mapEntry : parameters.entrySet()) {
			String key = mapEntry.getKey();
			Object value = mapEntry.getValue();
			if (isParameterPosition(key)) {
				query.setParameter(Integer.parseInt(key.substring(1)), value);
			} else if (isValorPreenchido(value)) {
				query.setParameter(i, value);
				i++;
			}
		}
		return query;
	}

	/**
	 * 
	 * Fiz uma solucao para data e calendar
	 * 
	 * se o parameter for Calendar, será do tipo timestamp, se for do tipo Date, será Date
	 * 
	 * @param query
	 * @param parameters
	 * @return
	 */
	private Query initParameters(Query query, Object... parameters) {
		for (int i = 0; i < parameters.length; i++) {
			Object parameter = parameters[i];
			int indiceParameter = i + 1;
			if(parameter instanceof Calendar) {
				query.setParameter(indiceParameter, (Calendar) parameter, TemporalType.TIMESTAMP);
			} else if (parameter instanceof java.util.Date) {
				long timeInMillis = ((java.util.Date) parameter).getTime();
				query.setParameter(indiceParameter, new Date(timeInMillis), TemporalType.DATE);
			} else {
				query.setParameter(indiceParameter, parameter);
			}
		}
		return query;
	}

	private boolean isValorPreenchido(Object entryValue) {
		if (entryValue != null) {
			if (entryValue instanceof String) {
				return !StringUtil.isEmpty((String) entryValue);
			} else if (entryValue instanceof Collection) {
				return !CollectionUtil.isVazio((Collection<?>) entryValue);
			}
			return true;
		}
		return false;
	}

	private Class<?> getEntityTypeClass() {
		return GenericsUtil.getFirstDeclaredTypeArgument(this.getClass());
	}

	/**
	 * @param queries
	 * @return Mapa chave é a query, valor ou é um Integer que representa a quantidade de linhas afetadas, ou o
	 *         SQLException
	 */
	@Override
	public Map<String, Serializable> execNativeQueries(List<String> queries) {
		Map<String, Serializable> retorno = new LinkedHashMap<String, Serializable>();
		for (String query : queries) {
			Serializable ret = execNativeQuery(query);
			retorno.put(query, ret);
		}
		return retorno;
	}

	/**
	 * @param query
	 * @return valor ou é um Integer que representa a quantidade de linhas afetadas, ou o SQLException
	 */
	@Override
	public Serializable execNativeQuery(String query) {
		try {
			Query nQuery = getEntityManager().createNativeQuery(query);
			Integer qntLinhasAtualizadas = nQuery.executeUpdate();
			return qntLinhasAtualizadas;
		} catch (Exception e) {
			return getSQLErrorCode(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> R execSingleSelectByNativeQuery(String query, Object... parameters) {
		Query nQuery = getEntityManager().createNativeQuery(query);
		initParameters(nQuery, parameters);
		return (R) nQuery.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> List<R> execSelectByNativeQuery(String query, Object... parameters) {
		Query nQuery = getEntityManager().createNativeQuery(query);
		initParameters(nQuery, parameters);
		return nQuery.getResultList();
	}

	private SQLException getSQLErrorCode(Throwable e) {
		if (e instanceof SQLException) {
			SQLException exception = (SQLException) e;
			LOG.error("SQLException.nextException", exception);
			return exception;
		} else {
			if (e.getCause() != null) {
				return getSQLErrorCode(e.getCause());
			}
		}
		return null;
	}

}
