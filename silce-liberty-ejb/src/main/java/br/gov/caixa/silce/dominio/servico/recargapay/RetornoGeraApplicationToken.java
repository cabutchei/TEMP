package br.gov.caixa.silce.dominio.servico.recargapay;

import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;

public class RetornoGeraApplicationToken extends RetornoGeraToken {

	private static final long serialVersionUID = 1L;

	private Integer statusCodeHttp;
	private String scope;

	public RetornoGeraApplicationToken(String accessToken, String scope, Integer expiresIn, String tokenType) {
		super(accessToken, expiresIn, tokenType);
		this.scope = scope;
	}

	public RetornoGeraApplicationToken(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public Integer getStatusCodeHttp() {
		return statusCodeHttp;
	}

	public void setStatusCodeHttp(Integer statusCodeHttp) {
		this.statusCodeHttp = statusCodeHttp;
	}

	public String getScope() {
		return scope;
	}
}
