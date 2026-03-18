package br.gov.caixa.silce.dominio;

import br.gov.caixa.util.CaixaEnum;

public enum TipoExecucao implements CaixaEnum<String> {
	CONSULTA("CONSULTA"), VALIDA("VALIDA"), FECHA("FECHA");

	private final String execucao;

	private TipoExecucao(String execucao) {
		this.execucao = execucao;
	}

	@Override
	public String getValue() {

		return execucao;
	}

}
