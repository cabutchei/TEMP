package br.gov.caixa.silce.dominio.servico.mercadopago;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;

public class RetornoCriaCustomer extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private final String customerId;
	
	public RetornoCriaCustomer(RetornoErro retornoErro) {
		super(retornoErro);
		this.customerId = null;
	}

	public RetornoCriaCustomer(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	@Override
	public String getNsuTransacaoMp() {
		return null;
	}

}
