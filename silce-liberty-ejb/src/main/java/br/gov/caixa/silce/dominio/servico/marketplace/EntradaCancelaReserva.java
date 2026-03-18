package br.gov.caixa.silce.dominio.servico.marketplace;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.dominio.EntradaHttp;

public final class EntradaCancelaReserva extends SaidaHttpMarketplace implements EntradaHttp<RetornoCancelaReserva> {

	private static final long serialVersionUID = 1L;

	private List<Integer> numerosCotas;

	public EntradaCancelaReserva(String codBolao, Integer numCota, String cpf) {
		this.codigoBolao = codBolao;

		List<Integer> listNumeroCota = new ArrayList<Integer>();
		listNumeroCota.add(numCota);
		this.numerosCotas = listNumeroCota;

		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "EntradaCancelaReserva [codigoBolao=" + codigoBolao + ", numerosCotas=" + numerosCotas + ", cpf=" + cpf + "]";
	}

	public List<Integer> getNumerosCotas() {
		return numerosCotas;
	}

	public void setNumerosCotas(List<Integer> numerosCotas) {
		this.numerosCotas = numerosCotas;
	}
}
