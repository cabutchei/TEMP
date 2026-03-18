package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import br.gov.caixa.util.Decimal;

/**
 * parâmetro que representa um Valor de Aposta. <br>
 * Podem ser: <br>
 * - Valor por quantidade de dezenas. <br>
 * 
 */
public class ParametroValorApostaJogoNumerico extends ParametroValorAposta {

	private static final long serialVersionUID = 1L;

	private Integer numeroPrognosticos;

	public ParametroValorApostaJogoNumerico(Integer numeroPrognosticos, Decimal valor) {
		super(valor);
		this.numeroPrognosticos = numeroPrognosticos;
	}

	public Integer getNumeroPrognosticos() {
		return numeroPrognosticos;
	}

	public void setNumeroPrognosticos(Integer numeroPrognosticos) {
		this.numeroPrognosticos = numeroPrognosticos;
	}

}
