package br.gov.caixa.silce.negocio.compra;

import javax.ejb.Stateless;

import br.gov.caixa.silce.dominio.servico.compra.MsgNuvemIntegracaoCompraConfirmacao;
import br.gov.caixa.silce.negocio.aposta.dao.CompraFacadeLocal;

@Stateless
public class CompraFacadeBean implements CompraFacadeLocal {

    @Override
    public void enviaConfirmacaoIntegracao(MsgNuvemIntegracaoCompraConfirmacao confirmacao) {}
    
}
