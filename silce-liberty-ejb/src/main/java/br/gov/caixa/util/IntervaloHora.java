package br.gov.caixa.util;

import java.io.Serializable;

/**
 * Classe que representa período de duas datas
 * 
 * @author c101482 - Vinicius Ferraz Campos Florentino
 *
 */
public final class IntervaloHora implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Hora inicio;

	private final Hora fim;

	public IntervaloHora(Hora inicio, Hora fim) {
		this.inicio = inicio;
		this.fim = fim;
	}

	public Hora getInicio() {
		return inicio;
	}

	public Hora getFim() {
		return fim;
	}

	@Override
	public String toString() {
		return "Periodo [inicio=" + inicio + ", fim=" + fim + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fim == null) ? 0 : fim.hashCode());
		result = prime * result + ((inicio == null) ? 0 : inicio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof IntervaloHora)) {
			return false;
		}
		IntervaloHora other = (IntervaloHora) obj;
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
