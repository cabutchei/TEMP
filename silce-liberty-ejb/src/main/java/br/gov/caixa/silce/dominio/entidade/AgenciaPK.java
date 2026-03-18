package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AgenciaPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="NU_UNIDADE")
	private Long numero;
	
	@Column(name = "NU_NATURAL")
	private Long numeroNatural;

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Long getNumeroNatural() {
		return numeroNatural;
	}

	public void setNumeroNatural(Long numeroNatural) {
		this.numeroNatural = numeroNatural;
	}

	@Override
	public String toString() {
		return "AgenciaPK [numero=" + numero + ", numeroNatural=" + numeroNatural + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((numeroNatural == null) ? 0 : numeroNatural.hashCode());
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
		if (!(obj instanceof AgenciaPK)) {
			return false;
		}
		AgenciaPK other = (AgenciaPK) obj;
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}
		if (numeroNatural == null) {
			if (other.numeroNatural != null) {
				return false;
			}
		} else if (!numeroNatural.equals(other.numeroNatural)) {
			return false;
		}
		return true;
	}
	

}
