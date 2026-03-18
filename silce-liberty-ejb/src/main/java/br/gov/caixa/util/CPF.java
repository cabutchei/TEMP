package br.gov.caixa.util;

import java.io.Serializable;

public final class CPF implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 *
	 * Valor do CPF sem máscara
	 */
	private final String cpfSemMascara;

	public CPF(String cpf) {
		if (!CPFUtil.isValido(cpf)) {
			throw new IllegalArgumentException("CPF informado não é válido " + cpf);
		}
		this.cpfSemMascara = StringUtil.removeNaoDigitos(cpf);
	}

	public String getCpfSemMascara() {
		return cpfSemMascara;
	}

	@Override
	public int hashCode() {
		return cpfSemMascara.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CPF)) {
			return false;
		}
		CPF other = (CPF) obj;
		if (!cpfSemMascara.equals(other.cpfSemMascara)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return cpfSemMascara;
	}

	public String getCpfComMascara() {
		return FormatadorUtil.formatarCpf(cpfSemMascara);
	}

}
