package br.gov.caixa.util;

import java.io.Serializable;

public final class Email implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Valor do email em lowercase
	 */
	private String emailValue;
	
	public Email(String email) {
		if(!EmailUtil.isValido(email)) {
			throw new IllegalArgumentException("Email informado não é válido " + email);
		}
		this.emailValue = StringUtil.toLowerCase(email);
	}
	
	public String getValor() {
		return emailValue;
	}

	public String getValorMascarado() {
		return EmailUtil.escondeEmail(this);
	}

	@Override
	public int hashCode() {
		return emailValue.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Email)) {
			return false;
		}
		Email other = (Email) obj;
		if (!emailValue.equals(other.emailValue)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return emailValue;  
	}

}
