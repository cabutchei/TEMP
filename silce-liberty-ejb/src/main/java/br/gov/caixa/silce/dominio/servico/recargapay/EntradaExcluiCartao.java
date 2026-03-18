package br.gov.caixa.silce.dominio.servico.recargapay;

import br.gov.caixa.dominio.EntradaHttp;

public class EntradaExcluiCartao implements EntradaHttp<RetornoExcluiCartao> {

	private static final long serialVersionUID = 1L;

	private String tokenCartao;

	public EntradaExcluiCartao(String tokenCartao) {
		super();
		this.tokenCartao = tokenCartao;
	}

	public String getTokenCartao() {
		return tokenCartao;
	}

	public void setTokenCartao(String tokenCartao) {
		this.tokenCartao = tokenCartao;
	}
}
