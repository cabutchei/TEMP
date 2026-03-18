package br.gov.caixa.util;

public final class CharUtil {

	private static final int INICIO_DIGITOS_CHAR = 48;

	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static CharUtil instancia = new CharUtil();

	private CharUtil() {
		// classe utilitária
	}

	public static int toIntValue(final char ch) {
		if (!isNumber(ch)) {
			throw new IllegalArgumentException("O caractere " + ch + " não é numérico");
		}
		return ch - INICIO_DIGITOS_CHAR;
	}

	public static boolean isNumber(final char c) {
		return Character.isDigit(c);
	}

}
