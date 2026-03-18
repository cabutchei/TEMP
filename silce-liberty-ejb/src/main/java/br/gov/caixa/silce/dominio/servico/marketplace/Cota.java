package br.gov.caixa.silce.dominio.servico.marketplace;

import java.io.Serializable;

public class Cota implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer numeroCota;
	private Integer anoBolao;
	private Integer mesBolao;
	private Integer numeroBolao;
	private Integer canalRevenda;
	private String cpf;
	private String dataReserva;
	private String idCota;
	private String idBolao;

	public Integer getNumeroCota() {
		return numeroCota;
	}

	public void setNumeroCota(Integer numeroCota) {
		this.numeroCota = numeroCota;
	}

	public Integer getAnoBolao() {
		return anoBolao;
	}

	public void setAnoBolao(Integer anoBolao) {
		this.anoBolao = anoBolao;
	}

	public Integer getMesBolao() {
		return mesBolao;
	}

	public void setMesBolao(Integer mesBolao) {
		this.mesBolao = mesBolao;
	}

	public Integer getNumeroBolao() {
		return numeroBolao;
	}

	public void setNumeroBolao(Integer numeroBolao) {
		this.numeroBolao = numeroBolao;
	}

	public Integer getCanalRevenda() {
		return canalRevenda;
	}

	public void setCanalRevenda(Integer canalRevenda) {
		this.canalRevenda = canalRevenda;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDataReserva() {
		return dataReserva;
	}

	public void setDataReserva(String dataReserva) {
		this.dataReserva = dataReserva;
	}

	public String getIdCota() {
		return idCota;
	}

	public void setIdCota(String idCota) {
		this.idCota = idCota;
	}

	public String getIdBolao() {
		return idBolao;
	}

	public void setIdBolao(String idBolao) {
		this.idBolao = idBolao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anoBolao == null) ? 0 : anoBolao.hashCode());
		result = prime * result + ((canalRevenda == null) ? 0 : canalRevenda.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataReserva == null) ? 0 : dataReserva.hashCode());
		result = prime * result + ((idBolao == null) ? 0 : idBolao.hashCode());
		result = prime * result + ((idCota == null) ? 0 : idCota.hashCode());
		result = prime * result + ((mesBolao == null) ? 0 : mesBolao.hashCode());
		result = prime * result + ((numeroBolao == null) ? 0 : numeroBolao.hashCode());
		result = prime * result + ((numeroCota == null) ? 0 : numeroCota.hashCode());
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
		Cota other = (Cota) obj;
		if (anoBolao == null) {
			if (other.anoBolao != null)
				return false;
		} else if (!anoBolao.equals(other.anoBolao))
			return false;
		if (canalRevenda == null) {
			if (other.canalRevenda != null)
				return false;
		} else if (!canalRevenda.equals(other.canalRevenda))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataReserva == null) {
			if (other.dataReserva != null)
				return false;
		} else if (!dataReserva.equals(other.dataReserva))
			return false;
		if (idBolao == null) {
			if (other.idBolao != null)
				return false;
		} else if (!idBolao.equals(other.idBolao))
			return false;
		if (idCota == null) {
			if (other.idCota != null)
				return false;
		} else if (!idCota.equals(other.idCota))
			return false;
		if (mesBolao == null) {
			if (other.mesBolao != null)
				return false;
		} else if (!mesBolao.equals(other.mesBolao))
			return false;
		if (numeroBolao == null) {
			if (other.numeroBolao != null)
				return false;
		} else if (!numeroBolao.equals(other.numeroBolao))
			return false;
		if (numeroCota == null) {
			if (other.numeroCota != null)
				return false;
		} else if (!numeroCota.equals(other.numeroCota))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cota [numeroCota=" + numeroCota + ", anoBolao=" + anoBolao + ", mesBolao=" + mesBolao + ", numeroBolao=" + numeroBolao + ", canalRevenda=" + canalRevenda + ", cpf="
			+ cpf + ", dataReserva=" + dataReserva + ", idCota=" + idCota + ", idBolao=" + idBolao + "]";
	}

}
