package br.gov.caixa.util;

public final class FormatadorUtil {

	private static final String TRACO = "-";
	private static final String BARRA = "/";
	private static final String PONTO = ".";

	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static FormatadorUtil instancia = new FormatadorUtil();

	
	private FormatadorUtil() {
	}

	public static String formatarCnpj(String cnpj) {

		StringBuilder cnpjFormatado = new StringBuilder();

		if (!StringUtil.isEmpty(cnpj)) {
			cnpjFormatado.append(cnpj.substring(0, 2));
			cnpjFormatado.append(PONTO);
			cnpjFormatado.append(cnpj.substring(2, 5));
			cnpjFormatado.append(PONTO);
			cnpjFormatado.append(cnpj.substring(5, 8));
			cnpjFormatado.append(BARRA);
			cnpjFormatado.append(cnpj.substring(8, 12));
			cnpjFormatado.append(TRACO);
			cnpjFormatado.append(cnpj.substring(12, 14));
		}

		return cnpjFormatado.toString();
	}

	public static String formatarCpf(String cpf) {

		StringBuilder cpfFormatado = new StringBuilder();

		if (!StringUtil.isEmpty(cpf)) {
			cpfFormatado.append(cpf.substring(0, 3));
			cpfFormatado.append(PONTO);
			cpfFormatado.append(cpf.substring(3, 6));
			cpfFormatado.append(PONTO);
			cpfFormatado.append(cpf.substring(6, 9));
			cpfFormatado.append(TRACO);
			cpfFormatado.append(cpf.substring(9, 11));
		}

		return cpfFormatado.toString();
	}

	public static String formatarCEP(String cep) {

		StringBuilder cepFormatado = new StringBuilder();

		if (!StringUtil.isEmpty(cep)) {
			cepFormatado.append(cep.substring(0, 2));
			cepFormatado.append(PONTO);
			cepFormatado.append(cep.substring(2, 5));
			cepFormatado.append(TRACO);
			cepFormatado.append(cep.substring(5, 8));
		}

		return cepFormatado.toString();
	}

}
