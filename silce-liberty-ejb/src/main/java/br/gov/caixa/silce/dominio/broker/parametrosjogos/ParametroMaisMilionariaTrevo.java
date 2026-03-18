package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.io.Serializable;

public class ParametroMaisMilionariaTrevo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer prognosticoMaximoTrevo;

	private Integer quantidadeMinimaTrevo;

	private Integer quantidadeMaximaTrevo;

	public ParametroMaisMilionariaTrevo(Integer prognosticoMaximoTrevo, Integer quantidadeMinimaTrevo, Integer quantidadeMaximaTrevo) {
		this.prognosticoMaximoTrevo = prognosticoMaximoTrevo;
		this.quantidadeMinimaTrevo = quantidadeMinimaTrevo;
		this.quantidadeMaximaTrevo = quantidadeMaximaTrevo;
	}

	public Integer getPrognosticoMaximoTrevo() {
		return prognosticoMaximoTrevo;
	}

	public void setPrognosticoMaximoTrevo(Integer prognosticoMaximoTrevo) {
		this.prognosticoMaximoTrevo = prognosticoMaximoTrevo;
	}

	public Integer getQuantidadeMinimaTrevo() {
		return quantidadeMinimaTrevo;
	}

	public void setQuantidadeMinimaTrevo(Integer quantidadeMinimaTrevo) {
		this.quantidadeMinimaTrevo = quantidadeMinimaTrevo;
	}

	public Integer getQuantidadeMaximaTrevo() {
		return quantidadeMaximaTrevo;
	}

	public void setQuantidadeMaximaTrevo(Integer quantidadeMaximaTrevo) {
		this.quantidadeMaximaTrevo = quantidadeMaximaTrevo;
	}

}
