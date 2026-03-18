package br.gov.caixa.util;

import java.io.Serializable;

public final class CEP implements Serializable {

	private static final int TAMANHO_CEP = 8;

	private static final long serialVersionUID = 1L;

	/**
	 * Valor do CEP sem máscara
	 */
	private final String cepSemMascara;
	

	public CEP(String cep) {
		String cepSemMascara = StringUtil.removeNaoDigitos(cep);
		if(cepSemMascara == null || cepSemMascara.length() != TAMANHO_CEP) {
			throw new IllegalArgumentException("CEP informado não é válido " + cepSemMascara);
		}
		this.cepSemMascara = cepSemMascara;
	}
	
	public String getCepSemMascara() {
		return cepSemMascara;
	}
	
	public String getNumero() {
		return cepSemMascara.substring(0, 5);
	}

	public String getComplemento() {
		return cepSemMascara.substring(5);
	}

	public String getCepComMascara() {
		return FormatadorUtil.formatarCEP(cepSemMascara);
	}

	@Override
	public int hashCode() {
		return cepSemMascara.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CEP)) {
			return false;
		}
		CEP other = (CEP) obj;
		if (!cepSemMascara.equals(other.cepSemMascara)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return cepSemMascara;  
	}

}
