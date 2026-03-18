package br.gov.caixa.silce.dominio.servico.mercadopago;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.entidade.Compra;

public class EntradaCriaPreferenciaPagamento implements EntradaHttp<RetornoPreferenciaPagamento> {

	private static final long serialVersionUID = 1L;

	private final Compra compra;

	public EntradaCriaPreferenciaPagamento(Compra compra) {
		super();
		this.compra = compra;
	}

	public Compra getCompra() {
		return compra;
	}

}
