package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

public class BairroVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String siglaUF;
	private Long codigoUF;
	private Long numeroMunicipio;
	private Long dvMunicipio;
	private String nomeBairro;
	
	public BairroVO() {
		this(null, null, null, null, null);
	}
	
	public BairroVO(String siglaUF, Long codigoUF, Long numeroMunicipio, Long dvMunicipio, String nomeBairro) {
		this.siglaUF = siglaUF;
		this.codigoUF = codigoUF;
		this.numeroMunicipio = numeroMunicipio;
		this.dvMunicipio = dvMunicipio;
		this.nomeBairro = nomeBairro;
	}

	public String getSiglaUF() {
		return siglaUF;
	}

	public void setSiglaUF(String siglaUF) {
		this.siglaUF = siglaUF;
	}

	public Long getCodigoUF() {
		return codigoUF;
	}

	public void setCodigoUF(Long codigoUF) {
		this.codigoUF = codigoUF;
	}

	public Long getNumeroMunicipio() {
		return numeroMunicipio;
	}

	public void setNumeroMunicipio(Long numeroMunicipio) {
		this.numeroMunicipio = numeroMunicipio;
	}

	public Long getDvMunicipio() {
		return dvMunicipio;
	}

	public void setDvMunicipio(Long dvMunicipio) {
		this.dvMunicipio = dvMunicipio;
	}

	public String getNomeBairro() {
		return nomeBairro;
	}

	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
	}

}
