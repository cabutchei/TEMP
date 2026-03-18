package br.gov.caixa.silce.negocio.infra.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;
import org.apache.openjpa.datacache.CacheStatistics;
import org.apache.openjpa.kernel.PreparedQueryCache;
import org.apache.openjpa.kernel.QueryStatistics;
import org.apache.openjpa.persistence.FetchPlan;
import org.apache.openjpa.persistence.OpenJPAEntityManagerFactory;
import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.apache.openjpa.persistence.OpenJPAQuery;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.dominio.exception.AmbienteException;
import br.gov.caixa.dominio.exception.Recurso;
import br.gov.caixa.negocio.infra.dao.AbstractCaixaDAO;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;

/**
 * 
 * Para cada Entidade no banco, existe uma DAO especÃ­fica
 * 
 * @author c101482 - Vinicius Ferraz Campos Florentino
 * 
 * @param <T>
 *            Tipo da Entidade
 * @param <I>
 *            Tipo do ID da Entidade
 */
public abstract class AbstractSilceDAO<T extends AbstractEntidade<I>, I extends Serializable>
		extends AbstractCaixaDAO<T, I> implements SilceDAO<T, I> {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LogManager.getLogger(AbstractSilceDAO.class, new MessageFormatMessageFactory());
	
	protected static final String DATA_DB2_FORMAT = "yyyy-MM-dd";
	protected static final String TIMESTAMP_DB2_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	
	@PersistenceContext(unitName = DatabaseConfig.PERSISTENCE_UNIT)
	private transient EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@AroundInvoke
	private Object intercept(InvocationContext ctx) throws Exception {
		try {
			return ctx.proceed();
		} catch (Exception e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Erro ao executar DAO. Método: {0}. Parâmetros: {1}.", e, ctx.getMethod(), Arrays.toString(ctx.getParameters()));
			}
			throw logSQLException(e);
		}
	}
	
	private AmbienteException logSQLException(Throwable e) {
		if(e instanceof SQLException) {
			SQLException exception = (SQLException) e;
			LOG.error("SQLException.nextException", exception);
			return new AmbienteException(Recurso.DB, e, Integer.toString(exception.getErrorCode()));
		}else {
			if(e.getCause() != null) {
				return logSQLException(e.getCause());
			}
		}
		return new AmbienteException(Recurso.DB, e, null);
	}
	
	@Override
	protected String unwrap(Query query) {
		return query.unwrap(OpenJPAQuery.class).getQueryString();
	}
	
	@Override
	protected void forceFetch(Query query, Map<Class<?>, String> forceFetch) {
		if (!forceFetch.isEmpty()) {
			// abrir PRM com a IBM. não está funcionando quando vem do cache.
			FetchPlan fetchPlan = query.unwrap(OpenJPAQuery.class).getFetchPlan();
			for (Map.Entry<Class<?>, String> entry : forceFetch.entrySet()) {
				fetchPlan.addField(entry.getKey(), entry.getValue());
			}
		}
	}
	
	@Override
	protected void forceMaxFetch(Query query, Integer maxDepth) {
		FetchPlan fetchPlan = query.unwrap(OpenJPAQuery.class).getFetchPlan();
		fetchPlan.setMaxFetchDepth(maxDepth);
	}

	@Override
	public CacheStatistics recupereCacheStatistics() {
		OpenJPAEntityManagerFactory oemf = OpenJPAPersistence.cast(getEntityManager().getEntityManagerFactory());
		return oemf.getStoreCache().getStatistics();
	}

	@Override
	public QueryStatistics<String> recupereQueryStatistics() {
		try {
			Object delegate = getEntityManager().getDelegate();
			Method configMethod = delegate.getClass().getMethod("getConfiguration");
			Object config = configMethod.invoke(delegate);
			Method queryCacheMethod = config.getClass().getMethod("getQuerySQLCacheInstance");
			PreparedQueryCache querySQLCacheInstance = (PreparedQueryCache) queryCacheMethod.invoke(config);
			return querySQLCacheInstance.getStatistics();
		} catch (Exception e) {
			LOG.warn("Não foi possível recuperar estatísticas de query SQL cache no runtime atual.", e);
			return null;
		}
	}

	public Set<EntityType<?>> recupereEntities() {
		return entityManager.getMetamodel().getEntities();
	}



}
