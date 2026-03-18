package br.gov.caixa.silce.dominio.servico.recargapay;

import br.gov.caixa.dominio.EntradaHttp;

public class EntradaGeraApostadorToken extends EntradaGeraToken implements EntradaHttp<RetornoGeraApostadorToken> {

	private static final long serialVersionUID = 1L;

	private final static String GRANT_TYPE = "urn:ietf:params:oauth:grant-type:uma-ticket";
	private final static String AUDIENCE = "https://api.recargapay.com/payments";
	private final static String CLAIM_TOKEN_FORMAT = "urn:ietf:params:oauth:token-type:jwt";
	private final static String PERMISSION = "#write:cards";

	private String identificadorApostador;
	private final String claimTokenFormat = CLAIM_TOKEN_FORMAT;
	private final String permission = PERMISSION;

	public EntradaGeraApostadorToken(String clientId, String clientSecret, String identificadorApostador) {
		super(clientId, clientSecret, AUDIENCE, GRANT_TYPE);
		this.identificadorApostador = identificadorApostador;
	}

	public String getIdentificadorApostador() {
		return identificadorApostador;
	}

	public String getClaimTokenFormat() {
		return claimTokenFormat;
	}

	public String getPermission() {
		return permission;
	}
}
