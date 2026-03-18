package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.EntradaBroker;
import br.gov.caixa.silce.dominio.NSB;

public class EntradaGeraNSBi implements EntradaBroker<RetornoGeraNSBi> {

	private static final long serialVersionUID = 1L;

	private NSB nsbc;
	
	public NSB getNsbc() {
		return nsbc;
	}
	
	public void setNsbc(NSB nsbc) {
		this.nsbc = nsbc;
	}
	
}
