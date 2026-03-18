package br.gov.caixa.silce.dominio.servico.mercadopago;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.util.Email;

public class RetornoCustomer extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private final String id;
	private final Email email;


	public RetornoCustomer(String id, Email email) {
		this.id = id;
		this.email = email;
	}


	@Override
	public String getNsuTransacaoMp() {
		return null;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @return the email
	 */
	public Email getEmail() {
		return email;
	}
}
