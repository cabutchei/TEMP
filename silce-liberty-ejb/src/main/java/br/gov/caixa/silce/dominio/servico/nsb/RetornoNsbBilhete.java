package br.gov.caixa.silce.dominio.servico.nsb;

import br.gov.caixa.dominio.SaidaHttp;

public class RetornoNsbBilhete implements SaidaHttp {

	private static final long serialVersionUID = 1L;
	private Integer statusCodeHttp;

	private String retorno;

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

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}

}
