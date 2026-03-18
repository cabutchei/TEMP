package br.gov.caixa.silce.dominio.servico.marketplace;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolao;

public final class EntradaConfirmaReserva extends SaidaHttpMarketplace implements EntradaHttp<RetornoConfirmaReserva> {

	private static final long serialVersionUID = 1L;

	public EntradaConfirmaReserva(ReservaCotaBolao reserva) {
		this.reservaCotaBolao = reserva;
	}

	@Override
	public String toString() {
		return "EntradaRealizaReserva [id reserva=" + reservaCotaBolao.getId() + "]";
	}
}
