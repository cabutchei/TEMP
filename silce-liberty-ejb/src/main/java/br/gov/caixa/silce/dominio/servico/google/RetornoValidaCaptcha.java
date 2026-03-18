package br.gov.caixa.silce.dominio.servico.google;

import br.gov.caixa.dominio.SaidaHttp;
import br.gov.caixa.util.Data;

public class RetornoValidaCaptcha implements SaidaHttp {

	private static final long serialVersionUID = 1L;
	private Integer statusCodeHttp;

	private Boolean sucesso;

	private Data timestampChallenge;

	private String hostname;

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

	public Boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}

	public Data getTimestampChallenge() {
		return timestampChallenge;
	}

	public void setTimestampChallenge(Data timestampChallenge) {
		this.timestampChallenge = timestampChallenge;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
}
