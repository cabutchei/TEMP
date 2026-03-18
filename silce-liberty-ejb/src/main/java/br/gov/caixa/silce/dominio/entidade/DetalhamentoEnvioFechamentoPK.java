package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

@Embeddable
public class DetalhamentoEnvioFechamentoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NU_DETALHE_ENVIO_FECHAMENTO")
	@SequenceGenerator(name = "NU_DETALHE_ENVIO_FECHAMENTO", sequenceName = "LCE.LCESQ029_NU_DTLHE_ENVO_FCHMO", allocationSize = 1)
	@Column(name = "NU_DETALHE_ENVIO_FECHAMENTO")
	private Long id;
	
	@Column(name = "AA_MOVIMENTO_ENVIO_FECHAMENTO")
	private Integer anoMovimento;

	@Column(name = "MM_MOVIMENTO_ENVIO_FECHAMENTO")
	private Integer mesMovimento;

	public DetalhamentoEnvioFechamentoPK() {
		// necessario
	}

	public DetalhamentoEnvioFechamentoPK(Long id, Integer anoMovimento, Integer mesMovimento) {
		this.id = id;
		this.anoMovimento = anoMovimento;
		this.mesMovimento = mesMovimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMesMovimento() {
		return mesMovimento;
	}

	public void setMesMovimento(Integer mesMovimento) {
		this.mesMovimento = mesMovimento;
	}

	public Integer getAnoMovimento() {
		return anoMovimento;
	}

	public void setAnoMovimento(Integer anoMovimento) {
		this.anoMovimento = anoMovimento;
	}

	@Override
	public String toString() {
		return "DetalhamentoEnvioFechamentoPK [id=" + id + ", mesMovimento=" + mesMovimento + ", anoMovimento=" + anoMovimento + "]";
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
		DetalhamentoEnvioFechamentoPK other = (DetalhamentoEnvioFechamentoPK) obj;
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
}
