package br.gov.caixa.silce.dominio.servico.recargapay;

import br.gov.caixa.dominio.EntradaHttp;

/**
 * @author c142924
 *
 */
public class EntradaGeraApplicationToken extends EntradaGeraToken implements EntradaHttp<RetornoGeraApplicationToken> {

	private static final long serialVersionUID = 1L;

	private static final String GRANT_TYPE = "client_credentials";

	public EntradaGeraApplicationToken(String clientId, String clientSecret, String audience) {
		super(clientId, clientSecret, audience, GRANT_TYPE);
	}
}
