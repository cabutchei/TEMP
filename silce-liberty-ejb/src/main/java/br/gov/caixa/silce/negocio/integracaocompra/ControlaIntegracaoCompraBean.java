package br.gov.caixa.silce.negocio.integracaocompra;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.ComboAposta;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.servico.compra.MsgNuvemIntegracaoCompraConfirmacao;
import br.gov.caixa.silce.dominio.servico.compra.MsgNuvemIntegracaoCompraSolicitacao;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoAposta;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCombo;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCompra;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoReserva;
import br.gov.caixa.silce.negocio.apostador.dao.ApostadorDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.CompraDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.CompraFacadeLocal;
import br.gov.caixa.silce.negocio.infra.SilceBean;

import br.gov.caixa.silce.negocio.integracaocompra.importers.SyncContext;
import br.gov.caixa.silce.negocio.integracaocompra.importers.AbstractIntegracaoSynchronizer.Mode;
import br.gov.caixa.silce.negocio.interfaces.local.ControlaIntegracaoCompraLocal;
import br.gov.caixa.silce.servico.conversor.compra.NuvemIntegracaoCompraConversor;


@Stateless
public class ControlaIntegracaoCompraBean implements ControlaIntegracaoCompraLocal, ControlaIntegracaoCompraRemote, SilceBean {

    private static final Logger LOG = LogManager.getLogger(ControlaIntegracaoCompraBean.class, new MessageFormatMessageFactory());

    @EJB
    private CompraFacadeLocal compraFacadeLocal;

    @EJB
    private CompraDAOLocal compraDAO;

    @EJB
    private ApostadorDAOLocal apostadorDAOLocal;

    @EJB
    private ApostaCompradaDAOLocal apostaCompradaDAO;

    @EJB
    private ReservaCotaBolaoDAOLocal reservaCotaBolaoDAO;

    @EJB
    private ComboApostaDAOLocal comboApostaDAO;

    @EJB
    private SituacaoReservaCotaBolaoDAOLocal situacaoReservaCotaBolaoDAO;

    @EJB
    private LotericaDAOLocal lotericaDAO;

    // ========================= ENTRY POINT =========================

    public void trateNotificacaoCompraNuvem(MsgNuvemIntegracaoCompraSolicitacao solicitacao) {

        if (solicitacao.idMensagemSolicitacao == 12321) {
            System.out.println();
        }

        LOG.info("Iniciando tratamento de notificacao de compra da nuvem. IdCompraNuvem: {0}", solicitacao.compra.idNuvem);

        try {
            // na minha opinião, essa informação deveria ser numérica
            if ("CRIACAO".equals(solicitacao.tipoSolicitacao)) {
                tratarCriacao(solicitacao);
            } else if ("ATUALIZACAO".equals(solicitacao.tipoSolicitacao)) {
                tratarAtualizacao(solicitacao);
            } else {
                throw new IllegalArgumentException("Tipo de solicitacao desconhecido: " + solicitacao.tipoSolicitacao);
            }

        } catch (Exception e) {
            confirmaIntegracaoErro(solicitacao.compra.idNuvem, e);
            throw e;
        }
    }

    private void tratarCriacao(MsgNuvemIntegracaoCompraSolicitacao solicitacao) throws Exception {
        // Apostador apostador = recuperarApostador(solicitacao.apostador); TODO: tratar apostador inexistente

        Compra compraExistente = compraDAO.findByNsuData(solicitacao.compra.nsu, IntegracaoUtil.extrairData(solicitacao.compra.dataInicioCompra));

        if (compraExistente != null) {
            LOG.info("Compra {0} (nsu {1}) ja existe no legado com id {2}, ignorando.", solicitacao.compra.idNuvem, solicitacao.compra.nsu, compraExistente.getId());
            confirmaIntegracaoIgnorada(solicitacao.idMensagemSolicitacao, solicitacao.compra.idNuvem, compraExistente.getId(), null, null);
            return;
        }

        SyncContext context = new SyncContext();

        NuvemIntegracaoCompra compraNuvem = solicitacao.compra;

        preparar(solicitacao, Mode.CREATE, context);
        integrar(solicitacao, Mode.CREATE, context);

        Long idCompraLegado = context.getCache().find(compraNuvem.idNuvem, Compra.class).getId();
        Map<Long, Long> idsApostasNuvemLegadoMap = context.getIdMap(Aposta.class);
        Map<Long, Long> idsCombosNuvemLegado = context.getIdMap(ComboAposta.class);

        LOG.info("Compra {0}/{1} e suas {2} apostas foram inseridas.", solicitacao.compra.idNuvem, idCompraLegado, idsApostasNuvemLegadoMap.size());

        confirmaIntegracaoSucesso(solicitacao.idMensagemSolicitacao, solicitacao.compra.idNuvem, idCompraLegado, idsApostasNuvemLegadoMap, idsCombosNuvemLegado);
    }

    private void tratarAtualizacao(MsgNuvemIntegracaoCompraSolicitacao solicitacao) throws Exception {
        Compra compra = compraDAO.findById(solicitacao.compra.idLegado);
    
        if (compra == null) {
            throw new IllegalStateException("Compra " + solicitacao.compra.idNuvem + "/" + solicitacao.compra.idLegado + " NÃO existe no legado");
        }

        SyncContext context = new SyncContext();

        preparar(solicitacao, Mode.UPDATE, context);
        integrar(solicitacao, Mode.UPDATE, context);

        Long idCompraLegado = compra.getId();
        Map<Long, Long> idsApostasNuvemLegadoMap = context.getIdMap(Aposta.class);
        Map<Long, Long> idsCombosNuvemLegado = context.getIdMap(ComboAposta.class);

        confirmaIntegracaoSucesso(solicitacao.idMensagemSolicitacao, solicitacao.compra.idNuvem, idCompraLegado, idsApostasNuvemLegadoMap, idsCombosNuvemLegado);
    }

    private void preparar(MsgNuvemIntegracaoCompraSolicitacao solicitacao, Mode mode, SyncContext context) {
        NuvemIntegracaoCompra compraNuvem = solicitacao.compra;
        List<NuvemIntegracaoAposta> apostasNuvem = solicitacao.apostas;
        List<NuvemIntegracaoCombo> combosNuvem = solicitacao.combos;

        context.getCompraSynchronizer().prepare(compraNuvem, mode);
        for (NuvemIntegracaoAposta apostaNuvem : apostasNuvem) {
            context.getApostaCompradaSynchronizer().prepare(apostaNuvem.comprada, mode);
            context.getApostaSynchronizer().prepare(apostaNuvem, mode);
            if (apostaNuvem.reservaCotaBolao != null) {
                context.getReservaCotaBolaoSynchronizer()
                    .prepare(apostaNuvem.reservaCotaBolao, mode);
            }
        }

        for (NuvemIntegracaoCombo comboNuvem : combosNuvem) {
            context.getComboSynchronizer().prepare(comboNuvem, mode);
        }

        // nota: acredito que, em atualizações, nada seria alterado nos combos. verificar isso.
    }

    private void integrar(MsgNuvemIntegracaoCompraSolicitacao solicitacao, Mode mode, SyncContext context) {
        List<NuvemIntegracaoAposta> apostasNuvem = solicitacao.apostas;
        List<NuvemIntegracaoCombo> combosNuvem = solicitacao.combos;

        context.getCompraSynchronizer().commit();

        if (mode == Mode.CREATE && combosNuvem != null) {
            for (NuvemIntegracaoCombo comboNuvem : combosNuvem) {
                context.getComboSynchronizer().commit();
            }
        }

        for (NuvemIntegracaoAposta apostaNuvem : apostasNuvem) {
            NuvemIntegracaoReserva reservaNuvem = apostaNuvem.reservaCotaBolao;
            if (reservaNuvem != null) {
                // forçando o flush cedo para contornar o cascade errado do OpenJPA
                context.getReservaCotaBolaoSynchronizer().commit(true);
            }

            context.getApostaSynchronizer().commit();
            context.getApostaCompradaSynchronizer().commit();
        }

        compraDAO.flush();
    }

    private void confirmaIntegracaoSucesso(Long idSolicitacao, Long idCompraNuvem, Long idCompraLegado, Map<Long, Long> idsApostasNuvemLegado, Map<Long, Long> idsCombosNuvemLegado) {
        MsgNuvemIntegracaoCompraConfirmacao msg = new MsgNuvemIntegracaoCompraConfirmacao(idSolicitacao, idCompraNuvem, idCompraLegado, idsApostasNuvemLegado, idsCombosNuvemLegado);
        compraFacadeLocal.enviaConfirmacaoIntegracao(msg);
    }

    private void confirmaIntegracaoIgnorada(Long idSolicitacao, Long idCompraNuvem, Long idCompraLegado, Map<Long, Long> idsApostasNuvemLegado, Map<Long, Long> idsCombosNuvemLegado) {
        MsgNuvemIntegracaoCompraConfirmacao msg = new MsgNuvemIntegracaoCompraConfirmacao(idSolicitacao, idCompraNuvem, idCompraLegado, idsApostasNuvemLegado, idsCombosNuvemLegado, true);
        compraFacadeLocal.enviaConfirmacaoIntegracao(msg);
    }

    private void confirmaIntegracaoErro(Long idSolicitacao, Exception e) {
        LOG.catching(e);

        // TODO: Implementar lógica de notificação de erro para a nuvem

        LOG.warn("Notificando erro de integracao para a nuvem. IdCompraNuvem: {0}, Erro: {1}", idSolicitacao, e.toString());
    }
}
