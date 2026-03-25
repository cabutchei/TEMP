package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

@Embeddable
public class ReservaCotaBolaoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NU_RESERVA_COTA_BOLAO")
	@SequenceGenerator(name = "NU_RESERVA_COTA_BOLAO", sequenceName = "LCE.LCESQ059_NU_RESERVA_COTA_BOLAO", allocationSize = 1)
	@Column(name = "NU_RESERVA_COTA_BOLAO")
	private Long id;
	
	@Column(name = "MM_RESERVA_COTA_BOLAO")
	private Integer mes;

	@Column(name = "NU_PARTICAO")
	private Integer particao;

	public ReservaCotaBolaoPK() {
		// necessario
	}

	public ReservaCotaBolaoPK(Long id, Integer mes, Integer particao) {
		this.id = id;
		this.mes = mes;
		this.particao = particao;
	}

	public ReservaCotaBolaoPK(Integer mes, Integer particao) {
		this.mes = mes;
		this.particao = particao;
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

	public Integer getParticao() {
		return particao;
	}

	public void setParticao(Integer particao) {
		this.particao = particao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mes == null) ? 0 : mes.hashCode());
		result = prime * result + ((particao == null) ? 0 : particao.hashCode());
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
		ReservaCotaBolaoPK other = (ReservaCotaBolaoPK) obj;
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
		if (particao == null) {
			if (other.particao != null)
				return false;
		} else if (!particao.equals(other.particao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReservaCotaBolaoPK [id=" + id + ", mes=" + mes + ", particao=" + particao + "]";
	}
}
