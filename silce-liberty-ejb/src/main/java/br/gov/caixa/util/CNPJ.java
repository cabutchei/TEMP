package br.gov.caixa.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public final class CNPJ implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Set<String> listCNPJSInvalidos = null;

	private static final int[] PESOS_CNPJ = { 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5, 6 };

	static {
		listCNPJSInvalidos = new HashSet<String>();
		listCNPJSInvalidos.add("00000000000000");
		listCNPJSInvalidos.add("11111111111111");
		listCNPJSInvalidos.add("22222222222222");
		listCNPJSInvalidos.add("33333333333333");
		listCNPJSInvalidos.add("44444444444444");
		listCNPJSInvalidos.add("55555555555555");
		listCNPJSInvalidos.add("66666666666666");
		listCNPJSInvalidos.add("77777777777777");
		listCNPJSInvalidos.add("88888888888888");
		listCNPJSInvalidos.add("99999999999999");
	}

	/**
	 * 
	 * Valor do CPF sem máscara
	 */
	private final String cnpjSemMascara;

	public CNPJ(String cnpj) {
		this.cnpjSemMascara = StringUtil.removeNaoDigitos(cnpj);
		if (!isValido(cnpjSemMascara)) {
			throw new IllegalArgumentException("CPF informado não é válido "
					+ cnpj);
		}
	}

	public static boolean isValido(String cnpj) {
		if (cnpj == null || cnpj.length() != 14 || listCNPJSInvalidos.contains(cnpj)) {
			return false;
		}

		Integer digito1 = calcularDigito(cnpj.substring(0, 12));
		Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString()
				+ digito2.toString());
	}

	private static int calcularDigito(String str) {
		StringBuilder sb = new StringBuilder(str).reverse();
		return Modulo11.calculeDigito(sb.toString(), PESOS_CNPJ);
	}


	public String getCnpjSemMascara() {
		return cnpjSemMascara;
	}

	@Override
	public int hashCode() {
		return cnpjSemMascara.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CNPJ)) {
			return false;
		}
		CNPJ other = (CNPJ) obj;
		if (!cnpjSemMascara.equals(other.cnpjSemMascara)) {
			return false;
		}
		return true;
	}

	public String getCnpjComMascara() {
		return FormatadorUtil.formatarCnpj(cnpjSemMascara);
	}
	
	@Override
	public String toString() {
		return cnpjSemMascara;
	}

}
