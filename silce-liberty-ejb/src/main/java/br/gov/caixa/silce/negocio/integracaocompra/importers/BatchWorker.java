package br.gov.caixa.silce.negocio.integracaocompra.importers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import br.gov.caixa.dominio.AbstractEntidade;

public abstract class BatchWorker<N, E extends AbstractEntidade<I>, I extends Serializable> {

    protected final List<AbstractIntegracaoSynchronizer<N, E, I>> synchronizers = new ArrayList<AbstractIntegracaoSynchronizer<N, E, I>>();
    protected SyncContext.EntityCache cache;
    protected AbstractIntegracaoSynchronizer.Mode mode;

    public BatchWorker(SyncContext.EntityCache cache, AbstractIntegracaoSynchronizer.Mode mode) {
        this.cache = cache;
        this.mode = mode;
    }

    public void prepare(N nuvem) throws NamingException {
        AbstractIntegracaoSynchronizer<N, E, I> sync = createSynchronizer();
        sync.prepare(nuvem, mode);
        this.synchronizers.add(sync);
    }

    public void prepare(List<N> nuvem) throws NamingException {
        for (N n : nuvem) prepare(n);
    }

    public List<E> commit() {
        List<E> l = new ArrayList<E>();
        for (AbstractIntegracaoSynchronizer<N, E, I> s : synchronizers) l.add(s.commit());
        return l;
    }

    public List<E> commit(boolean flush) {
        List<E> l = new ArrayList<E>();
        AbstractIntegracaoSynchronizer<N, E, I> s;
        for (int i = 0; i < synchronizers.size(); i++) {
            s = synchronizers.get(i);
            if (i < synchronizers.size() - 1) l.add(s.commit());
            else l.add(s.commit(true));
        }
        return l;
    }

    protected abstract AbstractIntegracaoSynchronizer<N, E, I> createSynchronizer() throws NamingException;

}
