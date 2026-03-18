package br.gov.caixa.silce.negocio.aposta.dao;

import javax.ejb.Local;

import br.gov.caixa.silce.dominio.servico.compra.MsgNuvemIntegracaoCompraConfirmacao;


@Local
public interface CompraFacadeLocal {

    public static final String JNDI_NAME = "CompraFacadeLocal";

    public void enviaConfirmacaoIntegracao(MsgNuvemIntegracaoCompraConfirmacao confirmacao);
    
}
