package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.EntradaBroker;
import br.gov.caixa.silce.dominio.broker.parametroscanal.ParametroCanal;

public class EntradaParametroCanal implements EntradaBroker<ParametroCanal> {

	private static final long serialVersionUID = 1L;
	private final int codigoCanal;

	public EntradaParametroCanal(int codigoCanal) {
		this.codigoCanal = codigoCanal;
	}

	public int getCodigoCanal() {
		return codigoCanal;
	}

}
