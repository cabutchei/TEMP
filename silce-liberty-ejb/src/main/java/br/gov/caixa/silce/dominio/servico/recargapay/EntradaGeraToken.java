package br.gov.caixa.silce.dominio.servico.recargapay;

/**
 * @author c142924
 *
 */
public class EntradaGeraToken {

	private String clientId;
	private String clientSecret;
	private String audience;
	private String grantType;

	public EntradaGeraToken(String clientId, String clientSecret, String audience, String grantType) {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.audience = audience;
		this.grantType = grantType;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public String getAudience() {
		return audience;
	}

	public String getGrantType() {
		return grantType;
	}
}
