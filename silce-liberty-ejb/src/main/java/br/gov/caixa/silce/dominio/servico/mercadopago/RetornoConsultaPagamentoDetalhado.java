package br.gov.caixa.silce.dominio.servico.mercadopago;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;

public class RetornoConsultaPagamentoDetalhado extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;
	
	private String detalhamento;

	public RetornoConsultaPagamentoDetalhado(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public RetornoConsultaPagamentoDetalhado(String detalhamento) {
		this.detalhamento = detalhamento;
	}

	@Override
	public String getNsuTransacaoMp() {
		return null;
	}

	public String getDetalhamento() {
		return detalhamento;
	}

}
