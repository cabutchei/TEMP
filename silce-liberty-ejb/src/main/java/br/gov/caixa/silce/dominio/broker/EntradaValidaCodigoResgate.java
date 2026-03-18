package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.EntradaBroker;
import br.gov.caixa.silce.dominio.NSB;
import br.gov.caixa.util.CPF;

public class EntradaValidaCodigoResgate implements EntradaBroker<RetornoValidaCodigoResgate> {

	private static final long serialVersionUID = 1L;

	private NSB nsbc;
	private CPF cpf;
	private String codigoResgate;
	private Integer canalEletronico;
	
	public NSB getNsbc() {
		return nsbc;
	}

	public void setNsbc(NSB nsbc) {
		this.nsbc = nsbc;
	}

	public CPF getCpf() {
		return cpf;
	}

	public void setCpf(CPF cpf) {
		this.cpf = cpf;
	}

	public String getCodigoResgate() {
		return codigoResgate;
	}

	public void setCodigoResgate(String codigoResgate) {
		this.codigoResgate = codigoResgate;
	}

	/**
	 * @return the canalEletronico
	 */
	public Integer getCanalEletronico() {
		return canalEletronico;
	}

	/**
	 * @param canalEletronico
	 *            the canalEletronico to set
	 */
	public void setCanalEletronico(Integer canalEletronico) {
		this.canalEletronico = canalEletronico;
	}

}
