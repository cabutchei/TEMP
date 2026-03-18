package br.gov.caixa.dominio;

import java.io.Serializable;

import br.gov.caixa.dominio.exception.Recurso;

public class InformacaoRecursoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Recurso recurso;
	
	private final String label;
	
	private Exception exception;
	
	private final String value;

	public InformacaoRecursoVO(Recurso recurso, String label) {
		this(recurso, label, null);
	}
	
	public InformacaoRecursoVO(Recurso recurso, String label, String value) {
		this.recurso = recurso;
		this.label = label;
		this.value = value;
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public String getLabel() {
		return label;
	}

	public Exception getException() {
		return exception;
	}

	public boolean isAcessivel() {
		return exception == null;
	}
	
	public void setException(Exception exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "InformacaoRecursoVO [recurso=" + recurso + ", label=" + label + ", exception=" + exception + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((recurso == null) ? 0 : recurso.hashCode());
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
		if (!(obj instanceof InformacaoRecursoVO)) {
			return false;
		}
		InformacaoRecursoVO other = (InformacaoRecursoVO) obj;
		if (!label.equals(other.label)) {
			return false;
		}
		if (recurso != other.recurso) {
			return false;
		}
		return true;
	}

	public String getValue() {
		return value;
	}
	
	
}
