package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.math.BigDecimal;

/**
 * Tipo que define os parâmetros data de encerramento dos jogos marketplace.
 */
public class ParametroJogoMarketplace extends AbstractParametroJogo {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal valorMenorCota;

	public ParametroJogoMarketplace(boolean marketplace) {
		this.marketplace = marketplace;
	}

	public ParametroJogoMarketplace(boolean marketplace, BigDecimal valorMenorCota) {
		this.marketplace = marketplace;
		this.valorMenorCota = valorMenorCota;
	}

	public BigDecimal getValorMenorCota() {
		return valorMenorCota;
	}

	public void setValorMenorCota(BigDecimal valorMenorCota) {
		this.valorMenorCota = valorMenorCota;
	}

}
