package br.gov.caixa.silce.negocio.integracaocompra.importers;

import javax.naming.NamingException;

import br.gov.caixa.silce.dominio.entidade.ComboAposta;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCombo;


public class ComboApostaBatchWorker extends BatchWorker<NuvemIntegracaoCombo, ComboAposta, Long> {

    public ComboApostaBatchWorker(SyncContext.EntityCache cache, AbstractIntegracaoSynchronizer.Mode mode) {
        super(cache, mode);
    }
    
    @Override
    protected AbstractIntegracaoSynchronizer<NuvemIntegracaoCombo, ComboAposta, Long> createSynchronizer() throws NamingException {
        return new ComboApostaSynchronizer(cache);
    }
    
}
