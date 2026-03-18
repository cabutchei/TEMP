package br.gov.caixa.silce.dominio;

public class ApostaModalidadeConcurso {

	private Integer modalidade;
	private Integer concurso;

	public ApostaModalidadeConcurso() {

	}

	public ApostaModalidadeConcurso(Integer modalidade, Integer concurso) {
		super();
		this.modalidade = modalidade;
		this.concurso = concurso;
	}

	public Integer getModalidade() {
		return modalidade;
	}

	public void setModalidade(Integer modalidade) {
		this.modalidade = modalidade;
	}

	public Integer getConcurso() {
		return concurso;
	}

	public void setConcurso(Integer concurso) {
		this.concurso = concurso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concurso == null) ? 0 : concurso.hashCode());
		result = prime * result + ((modalidade == null) ? 0 : modalidade.hashCode());
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
		ApostaModalidadeConcurso other = (ApostaModalidadeConcurso) obj;
		if (concurso == null) {
			if (other.concurso != null)
				return false;
		} else if (!concurso.equals(other.concurso))
			return false;
		if (modalidade == null) {
			if (other.modalidade != null)
				return false;
		} else if (!modalidade.equals(other.modalidade))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ApostaModalidadeConcurso [modalidade=" + modalidade + ", concurso=" + concurso + "]";
	}

}
