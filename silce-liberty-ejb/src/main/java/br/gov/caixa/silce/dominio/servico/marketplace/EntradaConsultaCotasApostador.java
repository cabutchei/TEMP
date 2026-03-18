package br.gov.caixa.silce.dominio.servico.marketplace;

import br.gov.caixa.dominio.EntradaHttp;

public final class EntradaConsultaCotasApostador extends SaidaHttpMarketplace implements EntradaHttp<RetornoConsultaCotasApostador> {

	private static final long serialVersionUID = 1L;

	public EntradaConsultaCotasApostador(String idBolao, String cpf, Long nsu) {
	}
}
