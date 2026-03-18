package br.gov.caixa.silce.dominio.servico.recargapay;

import java.util.List;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.Compra;

public class EntradaCriaPagamento implements EntradaHttp<RetornoPagamento> {

	private static final long serialVersionUID = 1L;

	private final String cardToken;
	private final Compra compra;
	private final List<Aposta<?>> apostas;

	public EntradaCriaPagamento(String cardToken, Compra compra, List<Aposta<?>> apostas) {
		super();
		this.cardToken = cardToken;
		this.compra = compra;
		this.apostas = apostas;
	}

	public String getCardToken() {
		return cardToken;
	}

	public Compra getCompra() {
		return compra;
	}

	public List<Aposta<?>> getApostas() {
		return apostas;
	}

}
