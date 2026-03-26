package br.gov.caixa.silce.negocio.integracaocompra.importers;

import javax.naming.NamingException;

import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoAposta;

public class ApostaBatchWorker extends BatchWorker<NuvemIntegracaoAposta, Aposta<?>, Long> {

    public ApostaBatchWorker(SyncContext.EntityCache cache, AbstractIntegracaoSynchronizer.Mode mode) {
        super(cache, mode);
    }

    @Override
    protected AbstractIntegracaoSynchronizer<NuvemIntegracaoAposta, Aposta<?>, Long> createSynchronizer() throws NamingException {
        return new ApostaSynchronizer(cache);
    }
}
