package br.gov.caixa.silce.dominio.servico.recargapay;

import br.gov.caixa.dominio.EntradaHttp;

public class EntradaConsultaCartoes implements EntradaHttp<RetornoConsultaCartoes> {

	private static final long serialVersionUID = 1L;

	private String clientId;

	public EntradaConsultaCartoes(String clientId) {
		super();
		this.clientId = clientId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
