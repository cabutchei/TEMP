package br.gov.caixa.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CPFUtil {
	/**
	 * Formato para o CPF
	 */
	private static final Pattern PATTERN = Pattern.compile("|([0-9]{3}[0-9]{3}[0-9]{3}[0-9]{2})");

	private static Set<String> listCPFInvalidos = null;

	private static int[] pesosDigito1 = new int[] { 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static int[] pesosDigito2 = new int[] { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static CPFUtil instancia = new CPFUtil();

	static {
		listCPFInvalidos = new HashSet<String>();
		listCPFInvalidos.add("00000000000");
		listCPFInvalidos.add("11111111111");
		listCPFInvalidos.add("22222222222");
		listCPFInvalidos.add("33333333333");
		listCPFInvalidos.add("44444444444");
		listCPFInvalidos.add("55555555555");
		listCPFInvalidos.add("66666666666");
		listCPFInvalidos.add("77777777777");
		listCPFInvalidos.add("88888888888");
		listCPFInvalidos.add("99999999999");
	}

	private CPFUtil() {

	}

	public static boolean isValido(String cpf) {
		boolean isValido = false;
		String cpfSemMascara = StringUtil.removeNaoDigitos(cpf);
		if (!StringUtil.isEmpty(cpfSemMascara) && cpfSemMascara.length() == 11) {

			if (listCPFInvalidos.contains(cpfSemMascara)) {
				return false;
			}

			Matcher matcher = PATTERN.matcher(cpfSemMascara);
			isValido = matcher.find();

			if (isValido) {
				int[] digitos = obtenhaDigitosVerificadores(cpfSemMascara);
				char digito1 = cpfSemMascara.charAt(cpfSemMascara.length() - 2);
				char digito2 = cpfSemMascara.charAt(cpfSemMascara.length() - 1);
				isValido = Integer.parseInt(String.valueOf(digito1)) == digitos[0] && Integer.parseInt(String.valueOf(
					digito2)) == digitos[1];
			}
		}
		return isValido;
	}

	/**
	 * @return um novo {@link CPF} com o cpf informado, caso cpf não seja null ou vazio; caso contrário, retorna null
	 */
	public static CPF getEmptySafe(String cpf) {
		if (StringUtil.isEmpty(cpf)) {
			return null;
		}
		return new CPF(cpf);
	}

	/**
	 * Realiza calculo dos dígitos verificadores do cpf
	 * 
	 * @param cpf
	 * @return digitos verificadores
	 */
	private static int[] obtenhaDigitosVerificadores(String cpf) {

		String cpfSemDVs = cpf.substring(0, cpf.length() - 2);

		int digito1 = Modulo11.calculeDigito(cpfSemDVs, pesosDigito1);

		// calculo do segundo digito verificador
		int digito2 = Modulo11.calculeDigito(cpfSemDVs + digito1, pesosDigito2);
		return new int[] { digito1, digito2 };
	}
}
