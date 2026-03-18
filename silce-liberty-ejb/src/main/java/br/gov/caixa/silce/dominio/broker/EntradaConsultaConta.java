package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.EntradaBroker;
import br.gov.caixa.util.CNPJ;

public class EntradaConsultaConta  implements EntradaBroker<RetornoConsultaConta> {

	private static final long serialVersionUID = 1L;

	private CNPJ cnpj;

	public CNPJ getCnpj() {
		return cnpj;
	}

	public void setCnpj(CNPJ cnpj) {
		this.cnpj = cnpj;
	}


	
}
