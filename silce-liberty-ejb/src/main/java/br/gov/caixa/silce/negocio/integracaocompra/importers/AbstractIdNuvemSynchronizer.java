package br.gov.caixa.silce.negocio.integracaocompra.importers;

import java.io.Serializable;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.servico.compra.IdNuvem;


public abstract class AbstractIdNuvemSynchronizer<N extends IdNuvem, E extends AbstractEntidade<I>, I extends Serializable> extends AbstractIntegracaoSynchronizer<N, E, I> {

    @Override
    protected void cachePreparedEntity(N nuvem, E entity) {
        getCache().put(nuvem.getIdNuvem(), entity);
    }

    protected E find(N nuvem) {
        Long id = nuvem.getIdNuvem();
        return find(id);
    }
}
