package br.gov.caixa.silce.dominio.servico.compra;

import java.util.List;

import br.gov.caixa.dominio.SaidaBroker;

/**
 * Mensagem de solicitação de integração de compra da nuvem.
 * 
 * Recebido do silce-compra-processamento via fila LQ.LOG.SILCE.INTEGRACAO_NUVEM_COMPRA_LEGADO
 */

public class MsgNuvemIntegracaoCompraSolicitacao extends SaidaBroker {

    public Long idMensagemSolicitacao;

    public String tipoSolicitacao;

    public NuvemIntegracaoCompra compra;

    public List<NuvemIntegracaoAposta> apostas;

    public List<NuvemIntegracaoCombo> combos;

    public MsgNuvemIntegracaoCompraSolicitacao() {

    }

    public MsgNuvemIntegracaoCompraSolicitacao( NuvemIntegracaoApostador apostador, NuvemIntegracaoCompra compra, List<NuvemIntegracaoAposta> apostas) {
        this.compra = compra;
        this.apostas = apostas;
    }
}
