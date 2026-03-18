package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.silce.dominio.NSB;

public class RetornoGeraNSBi extends SaidaBroker {

	private static final long serialVersionUID = 1L;

	private NSB nsbi;

	public NSB getNsbi() {
		return nsbi;
	}

	public void setNsbi(NSB nsbi) {
		this.nsbi = nsbi;
	}
	
}
