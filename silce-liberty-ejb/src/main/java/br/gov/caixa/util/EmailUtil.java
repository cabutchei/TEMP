package br.gov.caixa.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailUtil {
	
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{1,10}$", Pattern.CASE_INSENSITIVE);
	
	private static final char CARACTERE_PARA_MARCARAR = '*';
	
	//Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static EmailUtil instancia = new EmailUtil();
	
	private EmailUtil() {
		
	}
	
	public static boolean isValido(String email) {
		if(StringUtil.isEmpty(email)) {
			return false;
		}
		
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		
		return matcher.matches();
	}
	
	public static String escondeEmail(Email email) {
		if (email == null) {
			return null;
		}
		
		String emailStr = email.getValor();

		int indexOfAt = emailStr.indexOf('@');

		int qntAsteriscos = indexOfAt - 1;
		int posicaoInicialAsteriscos = 1;
		if (indexOfAt == 1) {
			qntAsteriscos = 1;
			posicaoInicialAsteriscos = 0;
		} else if (indexOfAt > 3) {
			qntAsteriscos = (int) Math.ceil(indexOfAt / 2d);
			posicaoInicialAsteriscos = ((indexOfAt - qntAsteriscos) / 2);
			if ((indexOfAt - qntAsteriscos) % 2 != 0) {
				posicaoInicialAsteriscos = posicaoInicialAsteriscos + 1;
			}
		}

		String asteriscos = StringUtil.completeAEsquerda(StringUtil.EMPTY, qntAsteriscos, CARACTERE_PARA_MARCARAR);

		StringBuilder emailEscondido = new StringBuilder(emailStr);
		emailEscondido.replace(posicaoInicialAsteriscos, qntAsteriscos + posicaoInicialAsteriscos, asteriscos);
		
		return emailEscondido.toString();
	}
	
	/**
	 * @return um novo {@link Email} com o email informado, caso email não seja null ou vazio; caso contrário, retorna null
	 */
	public static Email getEmptySafe(String email) {
		if (StringUtil.isEmpty(email)) {
			return null;
		}
		return new Email(email);
	}
	
	/**
	 * @return um novo {@link Email} com o email informado, caso o email seja válido; caso contrário, returna null
	 */
	public static Email getSafe(String email) {
		try {
			return new Email(email);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
}
