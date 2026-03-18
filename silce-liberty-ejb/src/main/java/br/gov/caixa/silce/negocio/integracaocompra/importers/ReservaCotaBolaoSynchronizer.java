package br.gov.caixa.silce.negocio.integracaocompra.importers;

import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.Loterica;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolao;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolaoPK;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoLoterica;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoReserva;
import br.gov.caixa.silce.negocio.aposta.dao.ApostaDAO;
import br.gov.caixa.silce.negocio.aposta.dao.ReservaCotaBolaoDAO;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoReservaCotaBolaoDAO;
import br.gov.caixa.silce.negocio.apostador.dao.LotericaDAO;
import br.gov.caixa.silce.negocio.integracaocompra.IntegracaoUtil;


public class ReservaCotaBolaoSynchronizer
        extends IntegracaoSynchronizer<NuvemIntegracaoReserva, ReservaCotaBolao, ReservaCotaBolaoPK> {

    private static final Logger LOG = LogManager.getLogger(ReservaCotaBolaoSynchronizer.class, new MessageFormatMessageFactory());

    ReservaCotaBolaoDAO reservaCotaBolaoDAO;
    SituacaoReservaCotaBolaoDAO situacaoReservaCotaBolaoDAO;
    ApostaDAO apostaDAO;
    LotericaDAO lotericaDAO;

    public ReservaCotaBolaoSynchronizer(SyncContext context) {
        super(context);
        try {
            reservaCotaBolaoDAO = getDao(ReservaCotaBolaoDAO.class);
            apostaDAO = getDao(ApostaDAO.class);
            situacaoReservaCotaBolaoDAO = getDao(SituacaoReservaCotaBolaoDAO.class);
            lotericaDAO = getDao(LotericaDAO.class);
        } catch (NamingException e) {
            logInitError(e);
        }
    }

    @Override
    protected ReservaCotaBolao prepareUpdate(NuvemIntegracaoReserva reservaNuvem) {
        // ReservaCotaBolaoPK id = new ReservaCotaBolaoPK(reservaNuvem.)
        Long idLegadoAposta = reservaNuvem.aposta.getIdLegado();
        if (idLegadoAposta == null) throw new IllegalStateException("id legado da aposta é null");
        Aposta<?> aposta = apostaDAO.findById(idLegadoAposta);
        ReservaCotaBolao reserva = aposta.getReservaCotaBolao();
        mapper.map(reservaNuvem, reserva);
        return reserva;
    }

    @Override
    protected ReservaCotaBolao update(NuvemIntegracaoReserva reservaNuvem) {
        ReservaCotaBolao reserva = find(reservaNuvem);
        if (reserva == null) throw new IllegalStateException("num achei a reserva pow");
        resolveRelations(reservaNuvem, reserva);
        ReservaCotaBolao x = reservaCotaBolaoDAO.update(reserva);
        if (x != reserva) throw new IllegalStateException("entidade reserva retornada não é a mesma do cache");
        return reserva;
    }
    
    @Override
    protected ReservaCotaBolao create(NuvemIntegracaoReserva reservaNuvem) {
        ReservaCotaBolao reserva = find(reservaNuvem);
        // resolveRelations(reservaNuvem, reserva);
        ReservaCotaBolaoPK pk = new ReservaCotaBolaoPK( IntegracaoUtil.getMesAtual(), IntegracaoUtil.getParticao(reservaNuvem.nsu));
        // TODO: conferir se essa geração de partição é aceitável
        reserva.setId(pk);
        reserva.setAno(IntegracaoUtil.getAnoAtual());
        reserva.setLoterica(recuperarLoterica(reservaNuvem.loterica));
        reservaCotaBolaoDAO.insert(reserva);
        Aposta<?> aposta = this.context.getCache().find(reservaNuvem.aposta.getIdNuvem(), Aposta.class);
        reserva.setAposta(aposta);
        return reserva;
    }

    @Override
    protected void resolveRelations(NuvemIntegracaoReserva reservaNuvem, ReservaCotaBolao reserva) {
        Aposta<?> aposta = this.context.getCache().find(reservaNuvem.aposta.getIdNuvem(), Aposta.class);
        reserva.setAposta(aposta);
        reserva.setAno(IntegracaoUtil.getAnoAtual());
        reserva.setLoterica(recuperarLoterica(reservaNuvem.loterica));
    }

    private Loterica recuperarLoterica(NuvemIntegracaoLoterica loterica) {

        if (loterica == null)
            return null;

        return lotericaDAO.findByCodigo(loterica.polo, loterica.id, loterica.dv);
    }
}
