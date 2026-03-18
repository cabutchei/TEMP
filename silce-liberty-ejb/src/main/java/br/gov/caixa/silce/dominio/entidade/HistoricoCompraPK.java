package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HistoricoCompraPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "NU_COMPRA")
	private Long id;
	
	@Column(name = "NU_MES")
	private Long mes;

	@Column(name = "NU_ANO")
	private Long ano;

	public HistoricoCompraPK() {
		// necessario
	}

	public HistoricoCompraPK(Long id) {
		this.id = id;
	}

	public HistoricoCompraPK(Long id, Long mes, Long ano) {
		this.id = id;
		this.mes = mes;
		this.ano = ano;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMes() {
		return mes;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mes == null) ? 0 : mes.hashCode());
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
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
		HistoricoCompraPK other = (HistoricoCompraPK) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mes == null) {
			if (other.mes != null)
				return false;
		} else if (!mes.equals(other.mes))
			return false;
		if (ano == null) {
			if (other.ano != null)
				return false;
		} else if (!ano.equals(other.ano))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HistoricoCompraPK [id=" + id + ", mes=" + mes + ", ano=" + ano + "]";
	}
}