package br.gov.caixa.silce.dominio.servico.mercadopago;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;

public class RetornoAddCartao extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private final String cardID;
	
	public RetornoAddCartao(RetornoErro retornoErro) {
		super(retornoErro);
		this.cardID = null;
	}

	public RetornoAddCartao(String cardID) {
		this.cardID = cardID;
	}

	@Override
	public String getNsuTransacaoMp() {
		return null;
	}

	/**
	 * @return the cardID
	 */
	public String getCardID() {
		return cardID;
	}

}
