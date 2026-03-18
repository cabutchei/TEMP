package br.gov.caixa.silce.dominio.servico.mercadopago;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.util.Email;

public class EntradaConsultaCustomer implements EntradaHttp<RetornoConsultaCustomer> {

	private static final long serialVersionUID = 1L;

	private final Email email;

	public EntradaConsultaCustomer(Email email) {
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public Email getEmail() {
		return email;
	}
}
