package br.gov.caixa.dominio;

public interface SaidaHttp extends SaidaServico {
	Integer getStatusCodeHttp();
	
	void setStatusCodeHttp(Integer statusCodeHttp);

}
