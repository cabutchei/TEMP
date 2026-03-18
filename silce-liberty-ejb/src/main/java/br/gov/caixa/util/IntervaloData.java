package br.gov.caixa.util;

import java.io.Serializable;

/**
 * Classe que representa período de duas datas
 * 
 * @author c101482 - Vinicius Ferraz Campos Florentino
 *
 */
public final class IntervaloData implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Data inicio;

	private final Data fim;

	public IntervaloData(Data inicio, Data fim) {
		if (inicio != null) {
			this.inicio = new Data(inicio);
		} else {
			this.inicio = null;
		}
		if (fim != null) {
			this.fim = new Data(fim);
		} else {
			this.fim = null;
		}
	}

	public boolean isBetween(Data data) {
		boolean inicio1 = getInicio() == null ? false : data.equalsOrAfter(getInicio());
		boolean fim1 = getFim() == null ? false : data.equalsOrBefore(getFim());
		return inicio1 && fim1;
	}

	public boolean hojeIsBetween() {
		Data hoje = DataUtil.getDataAtual();
		return isBetween(hoje);
	}

	public boolean inicioEqualsOrBeforeHoje() {
		Data hoje = DataUtil.getDataAtual();
		return inicio == null ? false : hoje.equalsOrAfter(inicio);
	}

	public Data getInicio() {
		return inicio;
	}

	public Data getFim() {
		return fim;
	}

	@Override
	public String toString() {
		return "Periodo [inicio=" + inicio + ", fim=" + fim + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fim == null) ? 0 : fim.hashCode());
		result = prime * result + ((inicio == null) ? 0 : inicio.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof IntervaloData)) {
			return false;
		}
		IntervaloData other = (IntervaloData) obj;
		if (fim == null) {
			if (other.fim != null) {
				return false;
			}
		} else if (!fim.equals(other.fim)) {
			return false;
		}
		if (inicio == null) {
			if (other.inicio != null) {
				return false;
			}
		} else if (!inicio.equals(other.inicio)) {
			return false;
		}
		return true;
	}

}
