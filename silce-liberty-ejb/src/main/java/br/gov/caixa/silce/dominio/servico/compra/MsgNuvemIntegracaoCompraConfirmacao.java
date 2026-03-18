package br.gov.caixa.silce.dominio.servico.compra;


import java.util.HashMap;
import java.util.Map;

import br.gov.caixa.dominio.EntradaBroker;

/**
 * Mensagem de confirmação de integração de compra da nuvem.
 *
 * Enviado para o silce-compra-processamento via fila LQ.LOG.SILCE.INTEGRACAO_NUVEM_CONFIRMACAO
 */
public class MsgNuvemIntegracaoCompraConfirmacao implements EntradaBroker<MsgNuvemIntegracaoCompraSolicitacao> {

    public Long idMensagemSolicitacao;

    public Boolean ignorada;

    public Long idCompraNuvem;

    public Long idCompraLegado;

    public Map<Long, Long> idsApostasNuvemLegado = new HashMap<Long, Long>();

    public Map<Long, Long> idsCombosNuvemLegado = new HashMap<Long, Long>();

    public MsgNuvemIntegracaoCompraConfirmacao() {

    }

    public MsgNuvemIntegracaoCompraConfirmacao(Long idSolicitacao, Long idCompraNuvem, Long idCompraLegado, Map<Long, Long> idsApostasLegadoNuvem, Map<Long, Long> idsCombosNuvemLegado) {
        this.idMensagemSolicitacao = idSolicitacao;
        this.idCompraNuvem = idCompraNuvem;
        this.idCompraLegado = idCompraLegado;
        this.idsApostasNuvemLegado = idsApostasLegadoNuvem;
        this.idsCombosNuvemLegado = idsCombosNuvemLegado;
        this.ignorada = false;
    }

    public MsgNuvemIntegracaoCompraConfirmacao(Long idSolicitacao, Long idCompraNuvem, Long idCompraLegado, Map<Long, Long> idsApostasLegadoNuvem, Map<Long, Long> idsCombosNuvemLegado, Boolean ignorada) {
        this(idSolicitacao, idCompraNuvem, idCompraLegado, idsApostasLegadoNuvem, idsCombosNuvemLegado);

        this.ignorada = ignorada;
    }

    public MsgNuvemIntegracaoCompraConfirmacao(Long idSolicitacao, Long idCompraNuvem, Long idCompraLegado, Map<Long, Long> idsApostasLegadoNuvem) {
        this(idSolicitacao, idCompraNuvem, idCompraLegado, idsApostasLegadoNuvem, null);
    }
}
