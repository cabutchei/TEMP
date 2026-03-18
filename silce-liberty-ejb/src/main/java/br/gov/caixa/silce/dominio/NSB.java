package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.util.regex.Pattern;

import br.gov.caixa.util.StringUtil;

public final class NSB implements Serializable {

	public static final String FIELD_NSBC = "NSBC";
	public static final String FIELD_NSB = "NSB";

	public enum Tipo {

		// NSB
		IMPRESSAO_AO_PORTADOR(Pattern.compile("(\\d|[A-F]){4}[\\dA-F]{17}(\\d|[A-F]){2}")),

		// NSBi
		IMPRESSAO_IDENTIFICADA(Pattern.compile("(\\d|[A-F]){4}(\\d|[A-F]){32}")),

		// NSBC
		PERSISTENCIA(Pattern.compile("(\\d|[A-F]){4}(\\d|[A-F]){32}"));

		private final Pattern pattern;

		private Tipo(Pattern pattern) {
			this.pattern = pattern;
		}

		public Pattern getPattern() {
			return pattern;
		}

		public Boolean matches(String valor) {
			return getPattern().matcher(valor).matches();
		}
	}

	private static final long serialVersionUID = 1L;
	private final String valor;
	private final Tipo tipo;

	public NSB(String valor) {
		this(valor, Tipo.PERSISTENCIA);
	}

	public NSB(String valor, Tipo tipo) {
		valideValores(valor, tipo);
		this.valor = valor;
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public Tipo getTipo() {
		return tipo;
	}

	@Override
	public int hashCode() {
		return tipo.hashCode() + valor.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NSB)) {
			return false;
		}
		NSB other = (NSB) obj;
		if (tipo != other.tipo) {
			return false;
		}
		if (!valor.equals(other.valor)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return valor;
	}

	public static NSB crieNSBTipoCorreto(String valor) {
		for (Tipo tipo : Tipo.values()) {
			if (tipo.matches(valor)) {
				return new NSB(valor, tipo);
			}
		}
		// se não for nenhum dos tipos acima, deve ser leitura de código de barra de um bilhete que são somente números,
		// ex: 756712619302034863648397012948
		return barcodeToNSB(valor);
	}

	private static NSB barcodeToNSB(String barcode) {
		int num1 = Integer.parseInt(barcode.substring(4, 9));
		int num2 = Integer.parseInt(barcode.substring(9, 14));
		int num3 = Integer.parseInt(barcode.substring(14, 19));
		int num4 = Integer.parseInt(barcode.substring(19, 24));
		int num5 = Integer.parseInt(barcode.substring(24, 29));

		String nsb = barcode.substring(0, 4) + hexString(num1, 4) + hexString(num2, 4) + hexString(num3, 4) + hexString(num4, 4)
			+ hexString(num5, 3);
		return new NSB(nsb, Tipo.IMPRESSAO_AO_PORTADOR);
	}

	/**
	 * @return String Retorna a string hexadecimal
	 */
	private static String hexString(int value, int size) {
		String out = Integer.toHexString(value).toUpperCase();
		StringBuilder sb = new StringBuilder(out);
		while (sb.length() < size) {
			sb.insert(0, '0');
		}
		return sb.toString();
	}

	private void valideValores(String valor, Tipo tipo) {
		if (StringUtil.isEmpty(valor)) {
			throw new IllegalArgumentException("Valor precisa ser informado");
		}
		if (!tipo.matches(valor)) {
			throw new IllegalArgumentException("Valor informado: " + valor + " não é valido para o tipo: " + tipo);

		}
	}
}
