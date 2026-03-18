package br.gov.caixa.silce.dominio.servico.mercadopago;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;

public class RetornoPreferenciaPagamento extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private String initPoint;

	public RetornoPreferenciaPagamento(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public RetornoPreferenciaPagamento(String initPoint) {
		super();
		this.initPoint = initPoint;
	}

	public String getInitPoint() {
		return initPoint;
	}

	public void setInitPoint(String initPoint) {
		this.initPoint = initPoint;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getNsuTransacaoMp() {
		return initPoint == null ? null : initPoint;
	}

}
