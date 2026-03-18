package br.gov.caixa.silce.dominio.broker;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.util.CNPJ;
import br.gov.caixa.util.Conta;

public class RetornoConsultaConta extends SaidaBroker  {

	private static final long serialVersionUID = 1L;

	private CNPJ cnpj;
	
	private List<Conta> contas = new ArrayList<Conta>();

	public void setCnpj(CNPJ cnpj) {
		this.cnpj = cnpj;
	}

	public CNPJ getCnpj() {
		return cnpj;
	}

	public List<Conta> getContas() {
		return contas;
	}
	
	public boolean addConta(Conta conta) {
		return contas.add(conta);
	}

	
}
