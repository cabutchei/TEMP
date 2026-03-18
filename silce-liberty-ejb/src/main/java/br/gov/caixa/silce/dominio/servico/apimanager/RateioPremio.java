package br.gov.caixa.silce.dominio.servico.apimanager;

import br.gov.caixa.util.Decimal;

public class RateioPremio {

	private Integer faixa;
	private Integer numeroGanhadores;
	private Decimal valorPremio;
	private String descricaoFaixa;

	public Integer getFaixa() {
		return faixa;
	}

	public void setFaixa(Integer faixa) {
		this.faixa = faixa;
	}

	public Integer getNumeroGanhadores() {
		return numeroGanhadores;
	}

	public void setNumeroGanhadores(Integer numeroGanhadores) {
		this.numeroGanhadores = numeroGanhadores;
	}

	public Decimal getValorPremio() {
		return valorPremio;
	}

	public void setValorPremio(Decimal valorPremio) {
		this.valorPremio = valorPremio;
	}

	public String getDescricaoFaixa() {
		return descricaoFaixa;
	}

	public void setDescricaoFaixa(String descricaoFaixa) {
		this.descricaoFaixa = descricaoFaixa;
	}

}

