package br.gov.caixa.silce.negocio.interfaces.local;

import javax.ejb.Local;

import br.gov.caixa.silce.dominio.servico.compra.MsgNuvemIntegracaoCompraSolicitacao;

@Local
public interface ControlaIntegracaoCompraLocal {

    void trateNotificacaoCompraNuvem(MsgNuvemIntegracaoCompraSolicitacao solicitacao) throws Exception;

    void trateNotificacaoCompraNuvemJson(String jsonSolicitacao) throws Exception;
}
