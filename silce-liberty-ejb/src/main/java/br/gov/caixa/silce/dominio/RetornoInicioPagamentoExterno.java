package br.gov.caixa.silce.dominio;

import java.io.Serializable;

public class RetornoInicioPagamentoExterno implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCompra;

	private String initPoint;

	public RetornoInicioPagamentoExterno(Long idCompra, String initPoint) {
		this.idCompra = idCompra;
		this.initPoint = initPoint;
	}

	public String getInitPoint() {
		return initPoint;
	}

	public void setInitPoint(String initPoint) {
		this.initPoint = initPoint;
	}

	public Long getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(Long idCompra) {
		this.idCompra = idCompra;
	}

}
