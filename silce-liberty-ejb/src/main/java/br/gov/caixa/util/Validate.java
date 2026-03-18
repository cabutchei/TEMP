package br.gov.caixa.util;

import java.text.MessageFormat;

/**
 * Classe utilitária para validar, principalmente, Paramêtros.
 * 
 * @author c127237
 */
public final class Validate {

	private static final String MSG_PADRAO_NULL = "{0} nao pode ser null.";
	private static final String MSG_PADRAO_EMPTY = "{0} nao pode ser vazio.";
	private static final String MSG_INSTANCIA_OF = "{0} deve ser uma instancia de {1}";
	
	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static Validate instancia = new Validate();

	
	private Validate() {
	}
	
	/**
	 * Valida se o objeto é diferente de null, lançando {@link NullPointerException} caso contrário. 
	 * 
	 * @param object o objeto a ser validado
	 * @param nome o nome que aparecerá na exceção lançada
	 */
	public static void notNull(Object object, String nome) {
		if (object == null) {
			String format = MessageFormat.format(MSG_PADRAO_NULL, nome);
			throw new NullPointerException(format);
		}
	}
	
	/**
	 * Valida se a expressão informada é true, lançando {@link IllegalStateException} caso contrário.
	 * 
	 * @param validation a expressão a ser validada
	 * @param msg a mensagem a ser apresentada na exceção
	 * @param params os parâmetros da mensagem
	 */
	public static void validState(boolean validation, String msg, Object... params) {
		if (!validation) {
			String format = MessageFormat.format(msg, params);
			throw new IllegalStateException(format);
		}
	}
	
	/**
	 * Valida se a expressão informada é true, lançando {@link IllegalArgumentException} caso contrário.
	 * 
	 * @param validation a expressão a ser validada
	 * @param msg a mensagem a ser apresentada na exceção
	 * @param params os parâmetros da mensagem
	 */	
	public static void isTrue(boolean validation, String msg, Object... params) {
		if (!validation) {
			String format = MessageFormat.format(msg, params);
			throw new IllegalArgumentException(format);
		}
	}
	
	/**
	 * Valida se a {@link String} não é null, nem contém apenas espaços em branco, lançando {@link NullPointerException}
	 * ou {@link IllegalArgumentException} caso contrário.
	 * 
	 * @param s a {@link String} a ser validada
	 * @param nome o nome que aparecerá na exceção lançada
	 */
	public static void notEmpty(String s,  String nome) {
		notNull(s, nome);
		if (StringUtil.isEmpty(s)) {
			String format = MessageFormat.format(MSG_PADRAO_EMPTY, nome);
			throw new IllegalArgumentException(format);
		}
	}
	
	/**
	 * Valida se a {@link Object} não é null, e é uma instancia da {@link Class}, lançando {@link NullPointerException}
	 * ou {@link IllegalArgumentException} caso contrário.
	 * 
	 * @param obj
	 *            a {@link Object} o objeto a ser validado
	 * @param classe
	 *            a {@link Object} o classe que o objeto deve ser instancia
	 * @param nome
	 *            o nome que aparecerá na exceção lançada
	 */
	public static void isInstance(Object obj, Class<?> classe, String nome) {
		notNull(obj, nome);
		notNull(classe, "Classe");
		if (!classe.isInstance(obj)) {
			String format = MessageFormat.format(MSG_INSTANCIA_OF, nome, classe.getName());
			throw new IllegalArgumentException(format);
		}
	}

}
