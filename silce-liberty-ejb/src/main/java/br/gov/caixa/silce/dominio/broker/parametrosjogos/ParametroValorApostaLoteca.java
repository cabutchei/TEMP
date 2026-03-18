package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import br.gov.caixa.util.Decimal;

/**
 * parâmetro que representa um Valor de Aposta Loteca. <br>

 * 
 */
public class ParametroValorApostaLoteca extends ParametroValorAposta {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Integer quantidadeDuplos;

	private Integer quantidadeTriplos;


	public ParametroValorApostaLoteca(Decimal valor, Integer quantidadeDuplos,
			Integer quantidadeTriplos) {
		super(valor);
		this.quantidadeDuplos = quantidadeDuplos;
		this.quantidadeTriplos = quantidadeTriplos;
	}
	
	public Integer getQuantidadeDuplos() {
		return quantidadeDuplos;
	}

	public void setQuantidadeDuplos(Integer quantidadeDuplos) {
		this.quantidadeDuplos = quantidadeDuplos;
	}

	public Integer getQuantidadeTriplos() {
		return quantidadeTriplos;
	}

	public void setQuantidadeTriplos(Integer quantidadeTriplos) {
		this.quantidadeTriplos = quantidadeTriplos;
	}


}
