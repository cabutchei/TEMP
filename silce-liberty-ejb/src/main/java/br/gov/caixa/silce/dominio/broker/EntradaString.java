package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.EntradaMQ;
import br.gov.caixa.dominio.SaidaMQ;

public class EntradaString implements EntradaMQ<SaidaMQ> {

	private static final long serialVersionUID = 1L;

	private String stringEntrada;

	public String getStringEntrada() {
		return stringEntrada;
	}

	public EntradaString(String stringEntrada) {
		this.stringEntrada = stringEntrada;

	}

	public void setStringEntrada(String stringEntrada) {
		this.stringEntrada = stringEntrada;
	}

}
