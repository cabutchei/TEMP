package br.gov.caixa.silce.dominio.servico.keycloak;

import br.gov.caixa.dominio.SaidaHttp;

public class RetornoIntrospectToken implements SaidaHttp {

	private static final long serialVersionUID = 1L;
	private Integer statusCodeHttp;

	private Boolean active;

	@Override
	public Boolean isOperacaoExecutadaComSucesso() {
		return 200 == statusCodeHttp;
	}

	@Override
	public Integer getStatusCodeHttp() {
		return statusCodeHttp;
	}

	@Override
	public void setStatusCodeHttp(Integer statusCodeHttp) {
		this.statusCodeHttp = statusCodeHttp;

	}

	public Boolean getActive() {
		return active;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
