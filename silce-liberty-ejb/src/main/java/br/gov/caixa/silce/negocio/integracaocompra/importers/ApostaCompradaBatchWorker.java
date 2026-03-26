package br.gov.caixa.silce.negocio.integracaocompra.importers;

import javax.naming.NamingException;

import br.gov.caixa.silce.dominio.entidade.ApostaComprada;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoApostaComprada;

public class ApostaCompradaBatchWorker extends BatchWorker<NuvemIntegracaoApostaComprada, ApostaComprada, Long> {

    public ApostaCompradaBatchWorker(SyncContext.EntityCache cache, AbstractIntegracaoSynchronizer.Mode mode) {
        super(cache, mode);
    }

    @Override
    protected AbstractIntegracaoSynchronizer<NuvemIntegracaoApostaComprada, ApostaComprada, Long> createSynchronizer() throws NamingException {
        return new ApostaCompradaSynchronizer(cache);
    }
    
}
