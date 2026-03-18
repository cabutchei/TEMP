package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;

@Embeddable
public class ConcursoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "NU_JOGO", insertable = false, updatable = false)
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Modalidade modalidade;

	@Column(name = "NU_CONCURSO", insertable = false, updatable = false)
	private Integer concurso;

	public ConcursoPK() {
		// necessário
	}

	public ConcursoPK(Modalidade modalidade, Integer concurso) {
		super();
		this.modalidade = modalidade;
		this.concurso = concurso;
	}

	public Integer getConcurso() {
		return concurso;
	}

	public void setConcurso(Integer concurso) {
		this.concurso = concurso;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ConcursoPK other = (ConcursoPK) obj;
		if (concurso == null) {
			if (other.concurso != null) {
				return false;
			}
		} else if (!concurso.equals(other.concurso)) {
			return false;
		}
		if (modalidade != other.modalidade) {
			return false;
		}
		return true;
	}
}
