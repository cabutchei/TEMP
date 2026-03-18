package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.EntradaBroker;
import br.gov.caixa.util.CEP;

public class EntradaConsultaLocalidadePorCEP implements EntradaBroker<RetornoConsultaLocalidadePorCEP> {

	private static final long serialVersionUID = 1L;

	private CEP cep;

	/**
	 * @return the cep
	 */
	public CEP getCep() {
		return cep;
	}

	/**
	 * @param cep
	 *            the cep to set
	 */
	public void setCep(CEP cep) {
		this.cep = cep;
	}

	
}
