package br.gov.caixa.silce.dominio.servico.mercadopago;

import java.util.List;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;

public class RetornoConsultaPagamento extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;
	
	private List<RetornoPagamento> pagamentos;

	public RetornoConsultaPagamento(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public RetornoConsultaPagamento(List<RetornoPagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public List<RetornoPagamento> getPagamentos() {
		return pagamentos;
	}
	
	@Override
	public String getNsuTransacaoMp() {
		return null;
	}

}
