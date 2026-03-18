package br.gov.caixa.silce.dominio;

import java.io.Serializable;

public class ResumoParticao implements Serializable {


	private static final long serialVersionUID = 1L;

	private Integer mes;

	private String nomeTabela;

	private Long quantidadeRegistros;

	public ResumoParticao() {
		super();
	}
	public ResumoParticao(Integer mes, String nomeTabela, Long quantidadeRegistros) {
		this.mes = mes;
		this.nomeTabela = nomeTabela;
		this.quantidadeRegistros = quantidadeRegistros;
	}

	public Integer getMes() {
		return mes;
	}


	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public String getNomeTabela() {
		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}

	public Long getQuantidadeRegistros() {
		return quantidadeRegistros;
	}

	public void setQuantidadeRegistros(Long quantidadeRegistros) {
		this.quantidadeRegistros = quantidadeRegistros;
	}

	@Override
	public String toString() {
		return "ResumoParticao [mes=" + mes + ", nomeTabela=" + nomeTabela + ", quantidadeRegistros=" + quantidadeRegistros + "]";
	}
}
