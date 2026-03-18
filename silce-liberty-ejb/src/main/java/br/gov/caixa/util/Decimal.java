package br.gov.caixa.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

/**
 * Classe que deve ser utilizada no lugar de BigDecimal. Continua imutável como a BigDecimal Motivos? ler
 * http://www.ibm.com/developerworks/java/library/j-jtp02183/index.html
 * 
 * @author c101482 - Vinicius Ferraz Campos Florentino
 */
public final class Decimal extends Number implements Serializable, Comparable<Decimal> {

	public static final Integer DEFAULT_SCALE = 2;

	public static final Decimal ZERO = new Decimal("0");

	public static final Decimal UM = new Decimal("1");

	public static final Decimal DEZ = new Decimal("10");

	public static final Decimal CEM = new Decimal("100");

	public static final Decimal MIL = new Decimal("1000");

	private static final long serialVersionUID = 1L;

	private static final RoundingMode ROUNDING_MODE = RoundingMode.DOWN;

	private static final Locale LOCALE_BRASIL = new Locale("pt", "BR");

	private final BigDecimal bigDecimal;

	/**
	 * @param valor
	 * @param bigDecimalFormat se true, quer dizer que o separador é ponto
	 */
	public Decimal(String valor, boolean bigDecimalFormat) {
		this(valor, DEFAULT_SCALE, bigDecimalFormat);
	}

	public Decimal(String valor) {
		this(valor, DEFAULT_SCALE, false);
	}

	public Decimal(String valor, int scale) {
		this(valor, scale, false);
	}

	public Decimal(String valor, int scale, boolean bigDecimalFormat) {
		if (valor.contains(".") && !valor.contains(",") && !bigDecimalFormat) {
			throw new IllegalArgumentException();
		}
		String valorReplaced = valor;
		if (!bigDecimalFormat) {
			valorReplaced = valor.replaceAll("\\.", "").replace(',', '.');
		}
		bigDecimal = new BigDecimal(valorReplaced).setScale(scale, BigDecimal.ROUND_DOWN);
	}

	public Decimal(Decimal decimal) {
		this(decimal.getValor(), decimal.bigDecimal.scale());
	}

	public Decimal(Integer valor) {
		this(valor, DEFAULT_SCALE);
	}

	public Decimal(Integer valor, int scale) {
		this(new BigDecimal(valor), scale);
	}

	public Decimal(Long valor) {
		this(valor, DEFAULT_SCALE);
	}

	public Decimal(Long valor, int scale) {
		this(new BigDecimal(valor), scale);
	}

	public Decimal(BigDecimal bigDecimal) {
		this(bigDecimal, DEFAULT_SCALE);
	}

	public Decimal(BigDecimal bigDecimal, int scale) {
		if (bigDecimal == null) {
			throw new IllegalArgumentException("Valor não pode ser nulo");
		}
		this.bigDecimal = bigDecimal.setScale(scale, ROUNDING_MODE);
	}

	public Decimal add(Decimal valor) {
		return add(valor, DEFAULT_SCALE, ROUNDING_MODE);
	}

	public Decimal add(Decimal valor, int scale, RoundingMode roundingMode) {
		return new Decimal(bigDecimal.add(valor.getValor()).setScale(scale,
				roundingMode));
	}

	public Decimal subtract(Decimal valor) {
		return subtract(valor, DEFAULT_SCALE, ROUNDING_MODE);
	}

	public Decimal subtract(Decimal valor, int scale, RoundingMode roundingMode) {
		return new Decimal(bigDecimal.subtract(valor.getValor()).setScale(
				scale, roundingMode));
	}

	public Decimal multiply(Decimal valor) {
		return multiply(valor, DEFAULT_SCALE, ROUNDING_MODE);
	}

	public Decimal multiply(Decimal valor, int scale, RoundingMode roundingMode) {
		return new Decimal(bigDecimal.multiply(valor.getValor()).setScale(
				scale, roundingMode));
	}

	public Decimal divide(Decimal valor) {
		return divide(valor, DEFAULT_SCALE, ROUNDING_MODE);
	}

	public Decimal divide(Decimal valor, int scale, RoundingMode roundingMode) {
		return new Decimal(
				bigDecimal.divide(valor.getValor(), scale, roundingMode));
	}

	public Decimal setScale(int newScale) {
		return new Decimal(getValor(), newScale);
	}

	public BigDecimal getValor() {
		return bigDecimal;
	}

	@Override
	public int compareTo(Decimal d) {
		return bigDecimal.compareTo(d.bigDecimal);
	}

	public boolean isMaiorQue(Decimal d) {
		return this.compareTo(d) > 0;
	}

	public boolean isMaiorOuIgualQue(Decimal d) {
		return this.compareTo(d) >= 0;
	}

	public boolean isMenorQue(Decimal d) {
		return this.compareTo(d) < 0;
	}

	public boolean isMenorOuIgualQue(Decimal d) {
		return this.compareTo(d) <= 0;
	}

	@Override
	public int hashCode() {
		return bigDecimal.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Decimal)) {
			return false;
		}
		Decimal other = (Decimal) obj;
		if (bigDecimal == null) {
			if (other.bigDecimal != null) {
				return false;
			}
		} else if (!bigDecimal.equals(other.bigDecimal)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return valorFormatado(LOCALE_BRASIL);
	}

	public String valorFormatado() {
		return valorFormatado(LOCALE_BRASIL);
	}

	public String valorFormatado(Locale locale) {
		return createDecimalFormatador("###,###.#", locale).format(getValor());
	}

	public String getValorSemSeparador() {
		return getValorFormatado().replaceAll("\\,", "").replaceAll("\\.", "");
	}

	public String getValorFormatado() {
		return valorFormatado(LOCALE_BRASIL);
	}

	public String getValorFormatado(Locale locale) {
		return valorFormatado(locale);
	}

	private DecimalFormat createDecimalFormatador(String pattern, Locale locale) {
		DecimalFormat formatador = new DecimalFormat(pattern, new DecimalFormatSymbols(locale));
		formatador.setMaximumFractionDigits(DEFAULT_SCALE);
		formatador.setMinimumFractionDigits(DEFAULT_SCALE);
		formatador.setMinimumIntegerDigits(1);
		return formatador;
	}

	public long longValue() {
		return bigDecimal.longValue();
	}

	public Decimal abs() {
		return new Decimal(bigDecimal.abs());
	}

	@Override
	public int intValue() {
		return bigDecimal.intValue();
	}

	@Override
	public float floatValue() {
		return bigDecimal.floatValue();
	}

	@Override
	public double doubleValue() {
		return bigDecimal.doubleValue();
	}

	public static final Decimal min(Decimal d1, Decimal d2) {
		return d1.isMenorQue(d2) ? d1 : d2;
	}

	public static final Decimal max(Decimal d1, Decimal d2) {
		return d1.isMaiorQue(d2) ? d1 : d2;
	}

	public static Decimal random(Decimal min, Decimal max) {
		if(min != null && min.equals(max)){
			return min;
		}

		if (min != null && max != null) {
			BigDecimal minToUse = min.getValor();
			BigDecimal maxToUse = max.getValor();

			BigDecimal minShifted = minToUse.movePointRight(DEFAULT_SCALE);
			BigDecimal maxShifted = maxToUse.movePointRight(DEFAULT_SCALE);
			BigInteger range = maxShifted.toBigInteger().subtract(minShifted.toBigInteger());

			Random random = new Random();

			BigInteger generated;
			do {
				generated = new BigInteger(range.bitLength(), random);
			} while (generated.compareTo(range) >= 0);

			BigDecimal gerado = minShifted.add(new BigDecimal(generated)).movePointLeft(DEFAULT_SCALE);

			return new Decimal(gerado);
		}

		return null;
	}
}
