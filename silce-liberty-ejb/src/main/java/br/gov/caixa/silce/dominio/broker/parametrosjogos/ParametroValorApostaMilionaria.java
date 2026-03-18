package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import br.gov.caixa.util.Decimal;

public class ParametroValorApostaMilionaria extends ParametroValorAposta {

	private static final long serialVersionUID = 1L;

	private Integer numeroPrognosticos;

	private Integer numeroTrevos;

	public ParametroValorApostaMilionaria(Integer numeroPrognosticos, Integer numeroTrevos, Decimal valor) {
		super(valor);
		this.numeroPrognosticos = numeroPrognosticos;
		this.numeroTrevos = numeroTrevos;
	}

	public Integer getNumeroPrognosticos() {
		return numeroPrognosticos;
	}

	public void setNumeroPrognosticos(Integer numeroPrognosticos) {
		this.numeroPrognosticos = numeroPrognosticos;
	}

	public Integer getNumeroTrevos() {
		return numeroTrevos;
	}

	public void setNumeroTrevos(Integer numeroTrevos) {
		this.numeroTrevos = numeroTrevos;
	}

}
