package br.gov.caixa.silce.negocio.integracaocompra;

import javax.ejb.Local;

import br.gov.caixa.silce.dominio.servico.compra.MsgNuvemIntegracaoCompraSolicitacao;

@Local
public interface ControlaIntegracaoCompraLocal {

    void trateNotificacaoCompraNuvem(MsgNuvemIntegracaoCompraSolicitacao solicitacao);
}
