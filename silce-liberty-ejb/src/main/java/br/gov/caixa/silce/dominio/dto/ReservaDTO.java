package br.gov.caixa.silce.dominio.dto;

import java.io.Serializable;

public class ReservaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codBolao;
	private Integer numCota;
	private String cpfApostador;

	public ReservaDTO(String codBolao, Integer numCota, String cpfApostador) {
		super();
		this.codBolao = codBolao;
		this.numCota = numCota;
		this.cpfApostador = cpfApostador;
	}

	public String getCodBolao() {
		return codBolao;
	}

	public void setCodBolao(String codBolao) {
		this.codBolao = codBolao;
	}

	public Integer getNumCota() {
		return numCota;
	}

	public void setNumCota(Integer numCota) {
		this.numCota = numCota;
	}

	public String getCpfApostador() {
		return cpfApostador;
	}

	public void setCpfApostador(String cpfApostador) {
		this.cpfApostador = cpfApostador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
