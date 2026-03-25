package br.gov.caixa.silce.negocio.integracaocompra;

import javax.ejb.Remote;

import br.gov.caixa.silce.dominio.servico.compra.MsgNuvemIntegracaoCompraSolicitacao;

@Remote
public interface ControlaIntegracaoCompraRemote {

    void trateNotificacaoCompraNuvem(MsgNuvemIntegracaoCompraSolicitacao solicitacao) throws Exception;
}
