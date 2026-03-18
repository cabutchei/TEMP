package br.gov.caixa.silce.dominio.servico.recargapay;

import java.util.List;

public class RetornoConsultaPagamento extends RetornoPagamento {

	private static final long serialVersionUID = 1L;

	private List<RetornoPagamento> pagamentos;
	private String retornoJson;

	public RetornoConsultaPagamento(List<RetornoPagamento> pagamentos, String retornoJson) {
		super();
		this.pagamentos = pagamentos;
		this.retornoJson = retornoJson;
	}

	public List<RetornoPagamento> getPagamentos() {
		return pagamentos;
	}

	public String getRetornoJson() {
		return retornoJson;
	}
}
