package br.gov.caixa.silce.dominio;

import java.io.Serializable;

import br.gov.caixa.util.Decimal;

public class RecalculoCompra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Decimal valorNaoEfetivadasDinheiroDevolvido;
	private Decimal valorComissaoDevolvido;

	public Decimal getValorNaoEfetivadasDinheiroDevolvido() {
		return valorNaoEfetivadasDinheiroDevolvido;
	}

	public void setValorNaoEfetivadasDinheiroDevolvido(Decimal valorNaoEfetivadasDinheiroDevolvido) {
		this.valorNaoEfetivadasDinheiroDevolvido = valorNaoEfetivadasDinheiroDevolvido;
	}

	public Decimal getValorComissaoDevolvido() {
		return valorComissaoDevolvido;
	}

	public void setValorComissaoDevolvido(Decimal valorComissaoDevolvido) {
		this.valorComissaoDevolvido = valorComissaoDevolvido;
	}
}
