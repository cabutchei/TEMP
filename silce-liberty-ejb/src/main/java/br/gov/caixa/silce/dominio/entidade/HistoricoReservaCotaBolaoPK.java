package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HistoricoReservaCotaBolaoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "NU_RESERVA_COTA_BOLAO")
	private Long id;

	@Column(name = "MM_RESERVA_COTA_BOLAO")
	private Integer mes;

	@Column(name = "AA_RESERVA_COTA_BOLAO")
	private Integer ano;

	public HistoricoReservaCotaBolaoPK() {
		// necessario
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
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
		HistoricoReservaCotaBolaoPK other = (HistoricoReservaCotaBolaoPK) obj;
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
		return "HistoricoReservaCotaBolaoPK [id=" + id + ", mes=" + mes + ", ano=" + ano + "]";
	}
}
