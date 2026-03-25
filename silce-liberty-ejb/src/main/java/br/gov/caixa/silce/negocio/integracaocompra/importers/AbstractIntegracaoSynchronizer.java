package br.gov.caixa.silce.negocio.integracaocompra.importers;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.negocio.infra.dao.DAO;
import br.gov.caixa.silce.negocio.integracaocompra.mappers.Mapper;
import br.gov.caixa.util.GenericsUtil;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representação abstrata de um sincronizador stateful. Cada sincronizador é como estação que trabalha com apenas um dto por vez. Os sincronizadores concretos
 * implementam sua própria estratégia de sincronização. A sincronização está pensada em duas etapas: 
 * <ul>
 *  <li>{@link #prepare(N, Mode) prepare}
 *  <p>a entidade será materializada e preenchida com os campos escalares do dto nuvem usando o mapper.
 *  <li>{@link #commit() commit}
 *  <p>as dependências de outras entidades serão resolvidas e o sql será agendado usando o DAO apropriado
 * 
 */
public abstract class AbstractIntegracaoSynchronizer<N, E extends AbstractEntidade<I>, I extends Serializable> {

    protected static final String INITIALIZATION_ERROR_MSG = "failed to initialize {0}";

    protected SyncContext.EntityCache cache;

    protected Logger LOG;
    protected DAO<E, I> dao;
    protected Mapper<N, E> mapper;

    private WorkerState workerState = WorkerState.IDLE;

    protected N preparedDto;
    protected E preparedEntity;
    protected Mode preparedMode;


    /**
     * Materializa ou recupera a entidade, pré-popula e coloca no cache compartilhado
     */
    public final void prepare(N nuvem, Mode mode) {
        assertNonNull(nuvem, mode);

        requireState(WorkerState.IDLE);

        E entity;

        switch (mode) {
            case CREATE:
                entity = prepareCreate(nuvem);
                break;
            case UPDATE:
                entity = prepareUpdate(nuvem);
                break;
            default:
                throw new IllegalStateException("Modo inválido: " + mode);
        }

        cachePreparedEntity(nuvem, entity);

        this.preparedDto = nuvem;
        this.preparedEntity = entity;
        this.preparedMode = mode;
        this.workerState = WorkerState.PREPARED;
    }

    /**
     * Segunda tarefa do sincronizador:
     * resolve relações e persiste/atualiza a entidade preparada.
     */
    public final E commit() {
        return commit(false);
    }

    /**
     * Segunda tarefa do sincronizador:
     * resolve relações e persiste/atualiza a entidade preparada.
     * @param flush
     * força o JPA a enviar os comandos sql imediatamente. Default: false 
     * @return
     */
    public final E commit(boolean flush) {
        assertNonNull(preparedDto, preparedMode);

        requireState(WorkerState.PREPARED);

        try {

            E persisted;

            switch (preparedMode) {
                case CREATE:
                    persisted = create(preparedDto);
                    break;
                case UPDATE:
                    persisted = update(preparedDto);
                    break;
                default:
                    throw new IllegalStateException("Modo não suportado: " + preparedMode);
            }

            if (flush) {
                dao.flush();
            }

            return persisted;

        } finally {
            clearWorkbench();
        }
    }

    public final boolean isIdle() {
        return workerState == WorkerState.IDLE;
    }

    protected final N getPreparedDto() {
        ensurePrepared();
        return preparedDto;
    }

    protected final E getPreparedEntity() {
        ensurePrepared();
        return preparedEntity;
    }

    protected final Mode getPreparedMode() {
        ensurePrepared();
        return preparedMode;
    }

    protected abstract void cachePreparedEntity(N nuvem, E entity);

    protected SyncContext.EntityCache getCache() {
        return cache;
    }

    @SuppressWarnings("unchecked")
    protected E find(Long id) {
        Class<?> type = GenericsUtil.getDeclaredTypeArgument(this.getClass(), 1);
        Class<E> t = (Class<E>) type;
        return cache.find(id, t);
    }

    /**
     * Busca a entidade. Implementações estão livres para decidir como fazer a busca.
     * @param nuvem
     * @return
     */
    protected abstract E fetch(N nuvem);

    /**
     * Preparação para criação da entidade
     * @param nuvem
     * @return
     */
    protected E prepareCreate(N nuvem) {
        E entity = mapper.map(nuvem);
        // cachePreparedEntity(nuvem, entity);
        return entity;
    }

    /**
     * Preparação para atualização de entidade
     * @param nuvem
     * @return
     */
    protected E prepareUpdate(N nuvem) {
        E entity = fetch(nuvem);
        mapper.map(nuvem, entity);
        return entity;
    }
    
    /**
     * Estratégia de criação da entidade. Ao final do método, a entidade deve estar persistida, no mínimo, localmente, ou seja, é garantido que estará no contexto
     * de persistência da transação.
     * @param nuvem
     * @return
     */
    protected abstract E create(N nuvem);

    /**
     * Estratégia de atualização da entidade.
     * @param nuvem
     * @return
     */
    protected abstract E update(N nuvem);

    /**
     * Resolve as relações da entidade em questão com as outras entidades do grafo
     * @param nuvem
     * @param entidade
     */
    protected abstract void resolve(N nuvem, E entidade);

    protected <T> T getDao(Class<T> localInterface) throws NamingException {
        Context ctx = new InitialContext();
        NamingException lastException = null;

        for (String lookupString : buildLookupNames(localInterface)) {
            try {
                Object result = ctx.lookup(lookupString);
                return localInterface.cast(result);
            } catch (NamingException e) {
                lastException = e;
            }
        }

        throw lastException != null
            ? lastException
            : new NamingException("Nenhum lookup JNDI encontrado para " + localInterface.getName());
    }

    private <T> List<String> buildLookupNames(Class<T> localInterface) {
        List<String> lookupNames = new ArrayList<String>();
        String beanName = localInterface.getSimpleName().replaceFirst("Local$", "");
        String interfaceName = localInterface.getName();
        String beanClassName = interfaceName.replaceFirst("Local$", "");

        lookupNames.add("java:module/" + beanName + "!" + interfaceName);
        lookupNames.add("java:app/silce-liberty-ejb-1.0-SNAPSHOT/" + beanName + "!" + interfaceName);
        lookupNames.add("java:global/silce-liberty-ear-1.0-SNAPSHOT/silce-liberty-ejb-1.0-SNAPSHOT/" + beanName + "!" + interfaceName);
        lookupNames.add("ejblocal:" + beanClassName);
        return lookupNames;
    }

    protected void logInitError(Throwable e) {
        LOG.error(INITIALIZATION_ERROR_MSG, getClass().getSimpleName(), e);
    }

    protected final void ensurePrepared() {
        if (workerState != WorkerState.PREPARED
                || preparedDto == null
                || preparedEntity == null
                || preparedMode == null) {
            throw new IllegalStateException(
                    "O sincronizador não possui item preparado na bancada.");
        }
    }

    private void requireState(WorkerState expected) {
        if (workerState != expected) {
            throw new IllegalStateException(
                    "Estado inválido do sincronizador. Esperado: " + expected + ", atual: " + workerState);
        }
    }

    private void assertNonNull(N nuvem, Mode mode) {
        Objects.requireNonNull(nuvem);
        Objects.requireNonNull(mode);
    }

    // private void assertNonNull(N nuvem, Mode mode) {
    //     Objects.requireNonNull(nuvem, "")
    // }

    // private Supplier<String> msg(String m) {
    //     return new Supplier<String>() {
    //         @Override
    //         public String get() {
    //             return m;
    //         }
    //     };
    // }

    private void clearWorkbench() {
        this.preparedDto = null;
        this.preparedEntity = null;
        this.preparedMode = null;
        this.workerState = WorkerState.IDLE;
    }

    protected enum WorkerState {
        IDLE,
        PREPARED
    }

    public static enum Mode {
        CREATE(0),
        UPDATE(1);

        private final int mode;

        private Mode(int mode) {
            this.mode = mode;
        }

        public int getValue() {
            return this.mode;
        }
    }
}
