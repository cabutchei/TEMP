package br.gov.caixa.silce.dominio.servico.sispl2;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.Canal;
import br.gov.caixa.silce.dominio.NSB;

public class EntradaSISPL2PagaPremio implements EntradaHttp<RetornoSISPL2PagaPremio> {

	private static final long serialVersionUID = 1L;



	private final NSB nsbc;
	private final Canal canal;
	private final Long loterica;

	public EntradaSISPL2PagaPremio(NSB nsbc, Canal canal, Long loterica) {
		this.nsbc = nsbc;
		this.canal = canal;
		this.loterica = loterica;
	}

	public NSB getNsbc() {
		return nsbc;
	}

	public Canal getCanal() {
		return canal;
	}

	public Long getLoterica() {
		return loterica;
	}
}
