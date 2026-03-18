package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import br.gov.caixa.util.Decimal;

/**
 * parâmetro que representa um Valor de Aposta. <br>
 * Podem ser: <br>
 * - Valor por quantidade de dezenas. <br>
 * 
 */
public class ParametroValorApostaLotogol extends ParametroValorAposta {

	private static final long serialVersionUID = 1L;

	private Integer numeroApostas;

	public ParametroValorApostaLotogol(Integer numeroApostas, Decimal valor) {
		super(valor);
		this.numeroApostas = numeroApostas;
	}

	public Integer getNumeroApostas() {
		return numeroApostas;
	}

	public void setNumeroApostas(Integer numeroApostas) {
		this.numeroApostas = numeroApostas;
	}
}
