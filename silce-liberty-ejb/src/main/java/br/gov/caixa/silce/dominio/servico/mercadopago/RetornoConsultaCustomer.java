package br.gov.caixa.silce.dominio.servico.mercadopago;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;

public class RetornoConsultaCustomer extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private List<RetornoCustomer> customers = new ArrayList<RetornoCustomer>();

	public RetornoConsultaCustomer(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public RetornoConsultaCustomer() {
	}
	
	@Override
	public String getNsuTransacaoMp() {
		return null;
	}

	public boolean addCustomer(RetornoCustomer rc) {
		return this.customers.add(rc);
	}

	/**
	 * @return the customers
	 */
	public List<RetornoCustomer> getCustomers() {
		return customers;
	}

	/**
	 * @param customers
	 *            the customers to set
	 */
	public void setCustomers(List<RetornoCustomer> customers) {
		this.customers = customers;
	}

}
