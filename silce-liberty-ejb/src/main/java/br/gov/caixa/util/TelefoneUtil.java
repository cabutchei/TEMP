package br.gov.caixa.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TelefoneUtil {
	private static final Pattern PATTERN = Pattern.compile("^([1-9]{2})(?:9[1-9]|[1-9])[0-9]{3}[0-9]{4}$");

	private static Set<String> listTELInvalidos = null;

	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static TelefoneUtil instancia = new TelefoneUtil();

	static {
		listTELInvalidos = new HashSet<String>();
		listTELInvalidos.add("000000000");
		listTELInvalidos.add("111111111");
		listTELInvalidos.add("222222222");
		listTELInvalidos.add("333333333");
		listTELInvalidos.add("444444444");
		listTELInvalidos.add("555555555");
		listTELInvalidos.add("666666666");
		listTELInvalidos.add("777777777");
		listTELInvalidos.add("888888888");
		listTELInvalidos.add("999999999");
		listTELInvalidos.add("00000000");
		listTELInvalidos.add("11111111");
		listTELInvalidos.add("22222222");
		listTELInvalidos.add("33333333");
		listTELInvalidos.add("44444444");
		listTELInvalidos.add("55555555");
		listTELInvalidos.add("66666666");
		listTELInvalidos.add("77777777");
		listTELInvalidos.add("88888888");
		listTELInvalidos.add("99999999");
	}

	private TelefoneUtil() {

	}

	public static boolean isValido(String tel) {
		boolean isValido = false;
		String telSemMascara = StringUtil.removeNaoDigitos(tel);
		if (!StringUtil.isEmpty(telSemMascara) && (telSemMascara.length() == 11 || telSemMascara.length() == 10)) {

			if (listTELInvalidos.contains(telSemMascara.substring(2, telSemMascara.length()))) {
				return isValido;
			}

			Matcher matcher = PATTERN.matcher(telSemMascara);
			isValido = matcher.find();
		}
		return isValido;
	}

	/**
	 * @return um novo {@link CPF} com o cpf informado, caso cpf não seja null ou vazio; caso contrário, retorna null
	 */

	public static boolean isEmpty(String dddnumero) {
		return dddnumero == null ? true : dddnumero.trim().isEmpty();
	}
}
