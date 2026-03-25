package br.gov.caixa.silce.negocio.integracaocompra.importers;


import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.servico.compra.IdLegado;
import br.gov.caixa.silce.dominio.servico.compra.IdNuvem;


public abstract class AbstractIdNuvemLegadoSynchronizer<N extends IdNuvem & IdLegado, E extends AbstractEntidade<Long>> extends AbstractIdNuvemSynchronizer<N, E, Long> {
    
    @Override
    protected E prepareUpdate(N nuvem) {
        E entity = this.dao.findById(nuvem.getIdLegado());
        // TODO: consertar mensagem de erro de forma a mostrar o tipo da entidade (não do sincronizador)
        if (entity == null) throw new IllegalStateException("Não foi encontrada a entidade do tipo " + this.getClass().getSimpleName() + " com id legado " + nuvem.getIdLegado());
        this.mapper.map(nuvem, entity);
        return entity;
    }

    @Override
    protected E fetch(N nuvem) {
        return this.dao.findById(nuvem.getIdLegado());
    }
    
}
