package br.gov.caixa.silce.dominio.servico.marketplace;

import java.util.List;

import br.gov.caixa.dominio.EntradaHttp;

public final class EntradaRealizaReserva extends SaidaHttpMarketplace implements EntradaHttp<RetornoRealizaReserva> {

	private static final long serialVersionUID = 1L;

	public EntradaRealizaReserva(String codBolao, String cpf, List<Long> nsus) {
		this.codigoBolao = codBolao;
		this.cpf = cpf;
		this.nsus = nsus;
	}

	@Override
	public String toString() {
		return "EntradaRealizaReserva [codigoBolao=" + codigoBolao + ", cpf=" + cpf + ", nsus=" + nsus + "]";
	}
}
