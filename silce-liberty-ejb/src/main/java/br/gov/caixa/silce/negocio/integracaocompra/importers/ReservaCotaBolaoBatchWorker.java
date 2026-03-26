package br.gov.caixa.silce.negocio.integracaocompra.importers;

import javax.naming.NamingException;

import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolao;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolaoPK;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoReserva;

public class ReservaCotaBolaoBatchWorker extends BatchWorker<NuvemIntegracaoReserva, ReservaCotaBolao, ReservaCotaBolaoPK>{

    public ReservaCotaBolaoBatchWorker(SyncContext.EntityCache cache, AbstractIntegracaoSynchronizer.Mode mode) {
        super(cache, mode);
    }

    @Override
    protected ReservaCotaBolaoSynchronizer createSynchronizer() throws NamingException {
        return new ReservaCotaBolaoSynchronizer(cache);
    }
    
}
