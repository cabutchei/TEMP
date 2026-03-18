package br.gov.caixa.silce.dominio.servico.keycloak;

import br.gov.caixa.dominio.EntradaHttp;

/**
 * Os parametros sao so pela URL
 * @author c101482
 *
 */
public class EntradaIntrospectToken implements EntradaHttp<RetornoIntrospectToken> {

	private static final long serialVersionUID = 1L;

	private String token;

	private String clientId;

	private String clientSecret;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
}
