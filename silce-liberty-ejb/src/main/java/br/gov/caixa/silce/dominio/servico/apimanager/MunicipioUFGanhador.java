package br.gov.caixa.silce.dominio.servico.apimanager;

import java.io.Serializable;

public class MunicipioUFGanhador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer posicao;
	private Integer ganhadores;
	private String municipio;
	private String UF;
	private String nomeFatansiaUL;
	private String serie;

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public Integer getGanhadores() {
		return ganhadores;
	}

	public void setGanhadores(Integer ganhadores) {
		this.ganhadores = ganhadores;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUF() {
		return UF;
	}

	public void setUF(String uF) {
		UF = uF;
	}

	public String getNomeFatansiaUL() {
		return nomeFatansiaUL;
	}

	public void setNomeFatansiaUL(String nomeFatansiaUL) {
		this.nomeFatansiaUL = nomeFatansiaUL;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

}
