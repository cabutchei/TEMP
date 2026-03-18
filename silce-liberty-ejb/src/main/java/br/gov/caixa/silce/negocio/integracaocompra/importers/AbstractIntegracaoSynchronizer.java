package br.gov.caixa.silce.negocio.integracaocompra.importers;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.negocio.infra.dao.AbstractSilceDAO;
import br.gov.caixa.silce.negocio.integracaocompra.mappers.Mapper;
import br.gov.caixa.util.GenericsUtil;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.logging.log4j.Logger;

import java.io.Serializable;


public abstract class AbstractIntegracaoSynchronizer<N, E extends AbstractEntidade<I>, I extends Serializable> {

    protected boolean initialized = false;
    protected final String INITIALIZATION_ERROR_MSG = "failed to initialize {0}";
    protected Logger LOG;

    protected SyncContext context;

    protected AbstractSilceDAO<E, I> dao;

    protected Mapper<N, E> mapper;

    protected N pendingDto;
    protected E pendingAposta;
    protected Mode pendingMode;
    protected boolean prepared;

    protected AbstractIntegracaoSynchronizer(SyncContext context) {
        this.context = context;
    }

    public void preSynchronize(N nuvem, Mode mode) {

        if (nuvem == null) {
            throw new IllegalStateException("DTO da aposta é null");
        }

        E entity;

        switch (mode) {
            case CREATE:
                entity = prepareCreate(nuvem);
                break;
            case UPDATE:
                entity = prepareUpdate(nuvem);
                break;
            default:
                throw new IllegalStateException("modo inválido");
        }

        cache(nuvem, entity);

        this.pendingMode = mode;
        this.pendingDto = nuvem;
        this.pendingAposta = entity;
        this.prepared = true;
    }

    protected abstract void cache(N nuvem, E entity);

    protected SyncContext.EntityCache getCache() {
        return this.context.getCache();
    }

    @SuppressWarnings("unchecked")
    protected E find(Long id) {
        Class<?> type = GenericsUtil.getDeclaredTypeArgument(this.getClass(), 1);
        Class<E> t = (Class<E>) type;
        return this.context.getCache().find(id, t);
    }

    protected E prepareCreate(N nuvem) {
        return mapper.map(nuvem);
    }

    protected abstract E prepareUpdate(N nuvem);

    public E synchronize(N nuvem, Mode mode, boolean flush) throws IllegalStateException {

        if (!initialized) {
            throw new IllegalStateException("one or more DAO services could not be loaded");
        }

        ensurePrepared();

        try {

            resolveRelations(this.pendingDto, this.pendingAposta);

            E persisted;

            switch (mode) {
                case CREATE:
                    persisted = create(pendingDto);
                    break;
                case UPDATE:
                    persisted = update(pendingDto);
                    break;
                default:
                    throw new IllegalStateException("Modo não suportado: " + this.pendingMode);
            }

            if (flush) dao.flush();

            return persisted;

        } finally {
            clearState();
        }
    }

    public E synchronize(N nuvem, Mode mode) throws IllegalStateException {
        return synchronize(nuvem, mode, true);
    }

    protected abstract E create(N nuvem);

    protected abstract E update(N nuvem);

    protected abstract void resolveRelations(N nuvem, E entidade);

    protected void ensurePrepared() {
        if (!prepared || pendingDto == null || pendingAposta == null) {
            throw new IllegalStateException(
                "preSynchronize() deve ser chamado antes de synchronize()");
        }
    }

    private void clearState() {
        this.pendingDto = null;
        this.pendingAposta = null;
        this.pendingMode = null;
        this.prepared = false;
    }

    protected <DAO extends AbstractSilceDAO<?, ?>> DAO getDao(Class<DAO> clazz) throws NamingException {
        String lookupString = "ejblocal:" + clazz.getName();
        Context ctx = new InitialContext();
        Object result = ctx.lookup(lookupString);
        return clazz.cast(result);
    }

    protected void logInitError(Throwable e) {
        LOG.error(INITIALIZATION_ERROR_MSG, getClass().getSimpleName(), e);
    }

    public static enum Mode {
        CREATE(0),
        UPDATE(1);

        int mode;

        private Mode(int mode) {
            this.mode = mode;
        }

        public int getValue() {
            return this.mode;
        }
    }
}
