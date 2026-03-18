package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

public enum OperacaoSidmo implements CaixaEnum<String> {

	CONSULTA_CARTEIRA("900001"), 
	CONSULTA_SALDO("900003"), 
	DEBITA_CARTEIRA("100001"), 
	ESTORNA("200005"), 
	CREDITA_PAGAMENTO_PREMIO("200003"), 
	CREDITA_DEVOLUCAO_APOSTA_NAO_REALIZADA("200004");

	private final String operacao;

	private OperacaoSidmo(String operacao) {
		this.operacao = operacao;
	}

	public String getOperacao() {
		return operacao;
	}

	public static OperacaoSidmo getByValue(String value) {
		return EnumUtil.recupereByValue(values(), value);
	}

	@Override
	public String getValue() {
		return getOperacao();
	}
}
