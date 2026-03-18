package br.gov.caixa.silce.dominio.servico.mercadopago;

import br.gov.caixa.dominio.EntradaHttp;

public class EntradaAddCartao implements EntradaHttp<RetornoAddCartao> {

	private static final long serialVersionUID = 1L;

	private final String token;

	public EntradaAddCartao(String token) {
		this.token = token;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
}
