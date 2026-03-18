package br.gov.caixa.silce.dominio.servico.sipes;

import java.io.Serializable;

public class Classificacao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer sequencia;

	public Classificacao() {
		super();
	}

	public Classificacao(Integer sequencia) {
		super();
		this.sequencia = sequencia;
	}

	public Integer getSequencia() {
		return sequencia;
	}

	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sequencia == null) ? 0 : sequencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Classificacao other = (Classificacao) obj;
		if (sequencia == null) {
			if (other.sequencia != null)
				return false;
		} else if (!sequencia.equals(other.sequencia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Classificacao [sequencia=" + sequencia + "]";
	}

}
