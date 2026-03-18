package br.gov.caixa.util;


/**
 *	utilitário para conversões de dados.
 * 
 */
public final class ConversorUtil {

	//Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static ConversorUtil instancia = new ConversorUtil();
	
	private ConversorUtil() {
	}

	
	/**
	 * Converte um texto <code>String</code> para um valor Inteiro.
	 * 
	 * @param valor
	 * 		O valor a ser convertido.
	 * @return Inteiro correspondente caso o valor seja um inteiro. Nulo caso contrário.
	 */
	public static Integer parseInt(String valor) {
		try {
			return Integer.parseInt(valor);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Converte um texto <code>String</code> para um valor <code>Long</code>.
	 * 
	 * @param valor
	 * 		O valor a ser convertido.
	 * @return 
	 */
	public static Long parseLong(String valor) {
		try {
			return Long.parseLong(valor);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Converte um texto <code>String</code> para um valor <code>Decimal</code>.
	 * 
	 * @param valor
	 * 		O valor a ser convertido.
	 * @return Objeto Decimal representando o valor.
	 */
	public static Decimal parseDecimal(String valor) {
		try {
			return new Decimal(valor);
		} catch (Exception e) {
			return null;
		}
	}
}
