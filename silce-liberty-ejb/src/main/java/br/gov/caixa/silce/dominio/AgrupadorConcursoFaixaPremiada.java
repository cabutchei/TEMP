package br.gov.caixa.silce.dominio;

import java.io.Serializable;

import br.gov.caixa.util.Decimal;

public class AgrupadorConcursoFaixaPremiada implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer nuConcurso;
	private String nomeFaixa;
	private Integer nuSorteio;
	private Decimal valorLiquido;

	public Integer getNuConcurso() {
		return nuConcurso;
	}

	public void setNuConcurso(Integer nuConcurso) {
		this.nuConcurso = nuConcurso;
	}

	public String getNomeFaixa() {
		return nomeFaixa;
	}

	public void setNomeFaixa(String nomeFaixa) {
		this.nomeFaixa = nomeFaixa;
	}

	public Integer getNuSorteio() {
		return nuSorteio;
	}

	public void setNuSorteio(Integer nuSorteio) {
		this.nuSorteio = nuSorteio;
	}

	public Decimal getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Decimal valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

}
