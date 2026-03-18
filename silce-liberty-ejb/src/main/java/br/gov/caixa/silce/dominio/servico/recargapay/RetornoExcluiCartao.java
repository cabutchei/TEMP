package br.gov.caixa.silce.dominio.servico.recargapay;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;

public class RetornoExcluiCartao extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	public RetornoExcluiCartao(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public RetornoExcluiCartao() {

	}

	@Override
	public String getNsuTransacaoMp() {
		return null;
	}

}
