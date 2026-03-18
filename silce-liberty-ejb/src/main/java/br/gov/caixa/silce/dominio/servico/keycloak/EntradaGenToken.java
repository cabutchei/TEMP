package br.gov.caixa.silce.dominio.servico.keycloak;

import br.gov.caixa.dominio.EntradaHttp;

/**
 * Os parametros sao so pela URL
 * @author c101482
 *
 */
public class EntradaGenToken implements EntradaHttp<RetornoGenToken> {

	private static final long serialVersionUID = 1L;

	private String grantType;

	private String clientId;

	private String clientSecret;

	private String accessToken;

	private String refreshToken;

	private String expiresIn;

	public EntradaGenToken(String grantType, String clientID, String clientSecret) {
		this.grantType = grantType;
		this.clientId = clientID.trim();
		this.clientSecret = clientSecret.trim();
	}

	public EntradaGenToken(String grantType, String clientID, String clientSecret, String accessToken, String refreshToken, String expiresIn) {
		this(grantType, clientID, clientSecret);
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
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

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
}
