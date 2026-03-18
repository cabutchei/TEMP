package br.gov.caixa.silce.dominio.servico.mercadopago;

import java.util.List;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.Compra;

public class EntradaCriaPagamento implements EntradaHttp<RetornoPagamento> {

	private static final long serialVersionUID = 1L;

	private final String token;
	private final String payMethodId;
	private final Compra compra;
	private final List<Aposta<?>> apostas;
	private final Boolean savedCard;

	public EntradaCriaPagamento(String token, String payMethodId, Compra compra, List<Aposta<?>> apostas, Boolean savedCard) {
		super();
		this.token = token;
		this.payMethodId = payMethodId;
		this.compra = compra;
		this.apostas = apostas;
		this.savedCard = savedCard;
	}

	public String getToken() {
		return token;
	}

	public String getPayMethodId() {
		return payMethodId;
	}

	public Compra getCompra() {
		return compra;
	}

	public Boolean getSavedCard() {
		return savedCard;
	}

	public List<Aposta<?>> getApostas() {
		return apostas;
	}

}
