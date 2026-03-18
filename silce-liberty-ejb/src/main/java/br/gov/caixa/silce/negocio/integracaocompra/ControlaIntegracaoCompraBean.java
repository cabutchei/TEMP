package br.gov.caixa.silce.negocio.integracaocompra;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.ApostaComprada;
import br.gov.caixa.silce.dominio.entidade.Apostador;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.servico.compra.MsgNuvemIntegracaoCompraConfirmacao;
import br.gov.caixa.silce.dominio.servico.compra.MsgNuvemIntegracaoCompraSolicitacao;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoAposta;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoApostador;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCombo;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCompra;
import br.gov.caixa.silce.negocio.aposta.dao.ApostaCompradaDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.ApostaDAOLocal;
import br.gov.caixa.silce.negocio.apostador.dao.ApostadorDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.ComboApostaDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.CompraDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.CompraFacadeLocal;
import br.gov.caixa.silce.negocio.aposta.dao.ReservaCotaBolaoDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoApostaDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoCompraDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoReservaCotaBolaoDAOLocal;
import br.gov.caixa.silce.negocio.apostador.dao.LotericaDAOLocal;
import br.gov.caixa.silce.negocio.infra.SilceBean;
import br.gov.caixa.silce.negocio.integracaocompra.importers.AbstractIntegracaoSynchronizer;
import br.gov.caixa.silce.negocio.integracaocompra.importers.SyncContext;
import br.gov.caixa.silce.negocio.interfaces.local.MantemDadosApostadorLocal;
import br.gov.caixa.util.Data;

// import com.google.common.collect.BiMap;
// import com.google.common.collect.HashBiMap;

@Stateless
public class ControlaIntegracaoCompraBean implements
        ControlaIntegracaoCompraLocal,
        ControlaIntegracaoCompraRemote,
        SilceBean {

    private static final Logger LOG =
        LogManager.getLogger(ControlaIntegracaoCompraBean.class, new MessageFormatMessageFactory());

    @EJB
    private CompraFacadeLocal compraFacadeLocal;

    @EJB
    private MantemDadosApostadorLocal mantemDadosApostadorLocal;

    @EJB
    private SituacaoCompraDAOLocal situacaoCompraDAO;

    @EJB
    private SituacaoApostaDAOLocal situacaoApostaDAO;

    @EJB
    private CompraDAOLocal compraDAO;

    @EJB
    private ApostaDAOLocal apostaDAO;

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
                // tratarAtualizacao(solicitacao);
            } else {
                throw new IllegalArgumentException("Tipo de solicitacao desconhecido: " + solicitacao.tipoSolicitacao);
            }

        } catch (Exception e) {
            confirmaIntegracaoErro(solicitacao.compra.idNuvem, e);
        }
    }

    private Apostador recuperarApostador(NuvemIntegracaoApostador apostadorNuvem) {
        return apostadorDAOLocal.findByCPF(apostadorNuvem.getParsedCpf());
    }

    private Data extrairData(Date date) {
        return date != null ? new Data(date) : null;
    }

    private void tratarCriacao(MsgNuvemIntegracaoCompraSolicitacao solicitacao) throws Exception {
        // Apostador apostador = recuperarApostador(solicitacao.apostador); TODO: tratar apostador inexistente

        Compra compraExistente = compraDAO.findByNsuData( solicitacao.compra.nsu, extrairData(solicitacao.compra.dataInicioCompra));

        if (compraExistente != null) {

            LOG.info("Compra {0} (nsu {1}) ja existe no legado com id {2}, ignorando.", solicitacao.compra.idNuvem, solicitacao.compra.nsu, compraExistente.getId());

            Map<Long, Long> idsApostasNuvemLegadoMap = new HashMap<>();

            for (NuvemIntegracaoAposta apostaNuvem : solicitacao.apostas) {

                ApostaComprada apostaCompradaLegado = apostaCompradaDAO.findNuvem(apostaNuvem.comprada.nsu);

                if (apostaCompradaLegado == null) {
                    throw new IllegalStateException( "Compra já foi integrada e a mensagem seria ignorada, mas nem todas as suas apostas foram. Nada alterado. idApostaNuvem: " + apostaNuvem.idNuvem);
                }

                Aposta<?> apostaLegado = apostaCompradaLegado.getAposta();

                idsApostasNuvemLegadoMap.put( apostaNuvem.idNuvem, apostaLegado.getId());
            }

            confirmaIntegracaoIgnorada( solicitacao.idMensagemSolicitacao, solicitacao.compra.idNuvem, compraExistente.getId(), idsApostasNuvemLegadoMap, null);

            return;
        }

        SyncContext context = new SyncContext();

        NuvemIntegracaoCompra compraNuvem = solicitacao.compra;
        List<NuvemIntegracaoAposta> apostasNuvem = solicitacao.apostas;
        List<NuvemIntegracaoCombo> combosNuvem = solicitacao.combos;

        // BiMap<Long, Long> idsApostasNuvemLegadoMap = HashBiMap.create();
        // BiMap<Long, Long> idsCombosNuvemLegado = HashBiMap.create();

        Map<Long, Long> idsApostasNuvemLegadoMap = new HashMap<Long, Long>();
        Map<Long, Long> idsCombosNuvemLegado = new HashMap<Long, Long>();

        preparar(solicitacao, AbstractIntegracaoSynchronizer.Mode.CREATE, context);
        integrar(solicitacao, AbstractIntegracaoSynchronizer.Mode.CREATE, context);
        LOG.info("Compra {0}/{1} e suas {2} apostas foram inseridas.", solicitacao.compra.idNuvem, compraNuvem.idLegado, idsApostasNuvemLegadoMap.size());

        confirmaIntegracaoSucesso( solicitacao.idMensagemSolicitacao, solicitacao.compra.idNuvem, compraNuvem.idLegado, idsApostasNuvemLegadoMap, idsCombosNuvemLegado);
    }

    private void preparar( MsgNuvemIntegracaoCompraSolicitacao solicitacao, AbstractIntegracaoSynchronizer.Mode mode, SyncContext context) {
        NuvemIntegracaoCompra compraNuvem = solicitacao.compra;
        List<NuvemIntegracaoAposta> apostasNuvem = solicitacao.apostas;
        List<NuvemIntegracaoCombo> combosNuvem = solicitacao.combos;

        context.getCompraSynchronizer().preSynchronize(compraNuvem, mode);
        // for (NuvemIntegracaoAposta apostaNuvem : apostasNuvem) {
        //     context.getApostaCompradaSynchronizer().preSynchronize(apostaNuvem.comprada, mode);
        //     context.getApostaSynchronizer().preSynchronize(apostaNuvem, mode);
        //     if (apostaNuvem.reservaCotaBolao != null) {
        //         context.getReservaCotaBolaoSynchronizer()
        //             .preSynchronize(apostaNuvem.reservaCotaBolao, mode);
        //     }
        // }

        // for (NuvemIntegracaoCombo comboNuvem : combosNuvem) {
        //     context.getComboSynchronizer().preSynchronize(comboNuvem, mode);
        // }

        // nota: acredito que, em atualizações, nada seria alterado nos combos. verificar isso.
    }

    private void integrar(
            MsgNuvemIntegracaoCompraSolicitacao solicitacao,
            AbstractIntegracaoSynchronizer.Mode mode,
            SyncContext context) {

        NuvemIntegracaoCompra compraNuvem = solicitacao.compra;

        context.getCompraSynchronizer().synchronize(compraNuvem, mode);

        // if (mode == AbstractIntegracaoSynchronizer.Mode.CREATE && combosNuvem != null) {
        //     for (NuvemIntegracaoCombo comboNuvem : combosNuvem) {
        //         context.getComboSynchronizer().synchronize(comboNuvem, mode);
        //     }
        // }

        // for (NuvemIntegracaoAposta apostaNuvem : apostasNuvem) {
        //     NuvemIntegracaoReserva reservaNuvem = apostaNuvem.reservaCotaBolao;
        //     if (reservaNuvem != null) {
        //         context.getReservaCotaBolaoSynchronizer().synchronize(reservaNuvem, mode);
        //     }

        //     NuvemIntegracaoApostaComprada apostaCompradaNuvem = apostaNuvem.comprada;

        //     context.getApostaSynchronizer().synchronize(apostaNuvem, mode);
        //     context.getApostaCompradaSynchronizer().synchronize(apostaCompradaNuvem, mode);
        // }
    }



    // private void tratarAtualizacao(MsgNuvemIntegracaoCompraSolicitacao solicitacao) throws IllegalStateException {
    //     Compra compra = compraDAO.findById(solicitacao.compra.idLegado);
    //
    //     if (compra == null) {
    //         throw new IllegalStateException(
    //             "Compra " + solicitacao.compra.idNuvem + "/" + solicitacao.compra.idLegado +
    //             " NÃO existe no legado"
    //         );
    //     }
    //
    //     atualizarCompraExistente(compra, solicitacao.compra);
    //
    //     Map<NuvemIntegracaoAposta, Aposta<?>> apostasNuvemLegadoMap =
    //         new HashMap<NuvemIntegracaoAposta, Aposta<?>>();
    //
    //     for (NuvemIntegracaoAposta apostaNuvem : solicitacao.apostas) {
    //         Aposta<?> apostaLegado = apostaDAO.findById(apostaNuvem.idLegado);
    //
    //         if (apostaLegado == null) {
    //             throw new IllegalStateException(
    //                 "Aposta " + apostaNuvem.idNuvem +
    //                 " de compra " + solicitacao.compra.idNuvem + "/" + solicitacao.compra.idLegado +
    //                 " NÃO existe no legado"
    //             );
    //         }
    //
    //         atualizarApostaExistente(apostaLegado, apostaNuvem);
    //         apostasNuvemLegadoMap.put(apostaNuvem, apostaLegado);
    //     }
    //
    //     Map<Long, Long> idsApostasNuvemLegadoMap = new HashMap<Long, Long>();
    //
    //     for (Map.Entry<NuvemIntegracaoAposta, Aposta<?>> entry : apostasNuvemLegadoMap.entrySet()) {
    //         idsApostasNuvemLegadoMap.put(
    //             entry.getKey().idNuvem,
    //             entry.getValue().getId()
    //         );
    //     }
    //
    //     confirmaIntegracaoSucesso(
    //         solicitacao.idMensagemSolicitacao,
    //         solicitacao.compra.idNuvem,
    //         compra.getId(),
    //         idsApostasNuvemLegadoMap
    //     );
    // }

    private void confirmaIntegracaoSucesso( Long idSolicitacao, Long idCompraNuvem, Long idCompraLegado, Map<Long, Long> idsApostasNuvemLegado, Map<Long, Long> idsCombosNuvemLegado) {

        MsgNuvemIntegracaoCompraConfirmacao msg = new MsgNuvemIntegracaoCompraConfirmacao( idSolicitacao, idCompraNuvem, idCompraLegado, idsApostasNuvemLegado, idsCombosNuvemLegado);

        compraFacadeLocal.enviaConfirmacaoIntegracao(msg);
    }

    private void confirmaIntegracaoIgnorada( Long idSolicitacao, Long idCompraNuvem, Long idCompraLegado, Map<Long, Long> idsApostasNuvemLegado, Map<Long, Long> idsCombosNuvemLegado) {

        MsgNuvemIntegracaoCompraConfirmacao msg = new MsgNuvemIntegracaoCompraConfirmacao( idSolicitacao, idCompraNuvem, idCompraLegado, idsApostasNuvemLegado, idsCombosNuvemLegado, true);

        compraFacadeLocal.enviaConfirmacaoIntegracao(msg);
    }

    private void confirmaIntegracaoErro(Long idSolicitacao, Exception e) {
        LOG.catching(e);

        // TODO: Implementar lógica de notificação de erro para a nuvem

        LOG.warn("Notificando erro de integracao para a nuvem. IdCompraNuvem: {0}, Erro: {1}",
                idSolicitacao, e.toString());
    }
}
