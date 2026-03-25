package br.gov.caixa.silce.negocio.integracaocompra.importers;

import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.Loterica;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolao;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolaoPK;
import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoLoterica;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoReserva;
import br.gov.caixa.silce.negocio.aposta.dao.ApostaDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.ReservaCotaBolaoDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoReservaCotaBolaoDAOLocal;
import br.gov.caixa.silce.negocio.apostador.dao.LotericaDAOLocal;
import br.gov.caixa.silce.negocio.integracaocompra.IntegracaoUtil;
import br.gov.caixa.silce.negocio.integracaocompra.mappers.ReservaCotaBolaoMapper;


public class ReservaCotaBolaoSynchronizer extends AbstractIdNuvemSynchronizer<NuvemIntegracaoReserva, ReservaCotaBolao, ReservaCotaBolaoPK> {

    private static final Logger LOG = LogManager.getLogger(ReservaCotaBolaoSynchronizer.class, new MessageFormatMessageFactory());

    SituacaoReservaCotaBolaoDAOLocal situacaoReservaCotaBolaoDAO;
    ApostaDAOLocal apostaDAO;
    LotericaDAOLocal lotericaDAO;

    public ReservaCotaBolaoSynchronizer(SyncContext.EntityCache cache) throws NamingException {
        apostaDAO = getDao(ApostaDAOLocal.class);
        situacaoReservaCotaBolaoDAO = getDao(SituacaoReservaCotaBolaoDAOLocal.class);
        lotericaDAO = getDao(LotericaDAOLocal.class);
        this.dao = getDao(ReservaCotaBolaoDAOLocal.class);
        this.cache = cache;
        this.mapper = new ReservaCotaBolaoMapper();
    }

    @Override
    protected ReservaCotaBolao fetch(NuvemIntegracaoReserva nuvem) {
        Long idLegadoAposta = nuvem.aposta.getIdLegado();
        if (idLegadoAposta == null) throw new IllegalStateException("id legado da aposta é null");
        Aposta<?> aposta = apostaDAO.findById(idLegadoAposta);
        ReservaCotaBolao reserva = aposta.getReservaCotaBolao();
        return reserva;
    }

    @Override
    protected ReservaCotaBolao update(NuvemIntegracaoReserva reservaNuvem) {
        ReservaCotaBolao reserva = find(reservaNuvem);
        if (reserva == null) throw new IllegalStateException("num achei a reserva pow");
        resolve(reservaNuvem, reserva);
        ReservaCotaBolao x = dao.update(reserva);
        if (x != reserva) throw new IllegalStateException("entidade reserva retornada não é a mesma do cache");
        return reserva;
    }
    
    @Override
    protected ReservaCotaBolao create(NuvemIntegracaoReserva reservaNuvem) {
        ReservaCotaBolao reserva = find(reservaNuvem);
        resolve(reservaNuvem, reserva);
        ReservaCotaBolaoPK pk = new ReservaCotaBolaoPK(IntegracaoUtil.getMesAtual(), IntegracaoUtil.getParticao(reservaNuvem.nsu));
        reserva.setId(pk);
        dao.insert(reserva);
        return reserva;
    }

    @Override
    protected void resolve(NuvemIntegracaoReserva reservaNuvem, ReservaCotaBolao reserva) {
        reserva.setLoterica(recuperarLoterica(reservaNuvem.loterica));
        reserva.setSituacao(recuperarSituacaoReservaCotaBolao(reservaNuvem.situacao));
    }

    private SituacaoReservaCotaBolao recuperarSituacaoReservaCotaBolao(Long situacao) {
        return situacaoReservaCotaBolaoDAO.findById(situacao);
    }

    private Loterica recuperarLoterica(NuvemIntegracaoLoterica loterica) {

        if (loterica == null)
            return null;

        return lotericaDAO.findByCodigo(loterica.polo, loterica.id, loterica.dv);
    }
}
