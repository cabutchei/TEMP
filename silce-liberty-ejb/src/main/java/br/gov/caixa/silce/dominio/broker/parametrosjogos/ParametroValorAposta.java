package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.io.Serializable;

import br.gov.caixa.util.Decimal;

/**
 * parâmetro que representa um Valor de Aposta. <br>
 * Podem ser: <br>
 * - Valor por quantidade de dezenas. <br>
 * - Valor de um Duplo. <br>
 * - Valor de um Triplo. <br>
 * 
 */
public class ParametroValorAposta implements Serializable, Comparable<ParametroValorAposta> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Decimal valor;

	public ParametroValorAposta(Decimal valor) {
		this.valor = valor;
	}

	public Decimal getValor() {
		return valor;
	}

	@Override
	public int compareTo(ParametroValorAposta o) {
		return this.valor.compareTo(o.valor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ParametroValorAposta other = (ParametroValorAposta) obj;
		if (valor == null) {
			if (other.valor != null) {
				return false;
			}
		} else if (!valor.equals(other.valor)) {
			return false;
		}
		return true;
	}
}
