package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

@Embeddable
public class DetalhamentoRetornoFechamentoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NU_DETALHE_RETORNO_FECHAMENTO")
	@SequenceGenerator(name = "NU_DETALHE_RETORNO_FECHAMENTO", sequenceName = "LCE.LCESQ062_NU_DTLHE_RTRNO_FCHMO", allocationSize = 1)
	@Column(name = "NU_DETALHE_RETORNO_FECHAMENTO")
	private Integer id;

	@Column(name = "AA_MOVIMENTO_RETORNO_FCHMO")
	private Integer anoMovimento;
	
	@Column(name = "MM_MOVIMENTO_RETORNO_FCHMO")
	private Integer mesMovimento;

	public DetalhamentoRetornoFechamentoPK() {
		// necessario
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAnoMovimento() {
		return anoMovimento;
	}

	public void setAnoMovimento(Integer anoMovimento) {
		this.anoMovimento = anoMovimento;
	}

	public Integer getMesMovimento() {
		return mesMovimento;
	}

	public void setMesMovimento(Integer mesMovimento) {
		this.mesMovimento = mesMovimento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anoMovimento == null) ? 0 : anoMovimento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mesMovimento == null) ? 0 : mesMovimento.hashCode());
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
		DetalhamentoRetornoFechamentoPK other = (DetalhamentoRetornoFechamentoPK) obj;
		if (anoMovimento == null) {
			if (other.anoMovimento != null)
				return false;
		} else if (!anoMovimento.equals(other.anoMovimento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mesMovimento == null) {
			if (other.mesMovimento != null)
				return false;
		} else if (!mesMovimento.equals(other.mesMovimento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DetalhamentoRetornoFechamentoPK [id=" + id + ", anoMovimento=" + anoMovimento + ", mesMovimento=" + mesMovimento + "]";
	}
}
