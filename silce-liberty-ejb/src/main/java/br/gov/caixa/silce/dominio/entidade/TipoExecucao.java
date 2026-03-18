package br.gov.caixa.silce.dominio.entidade;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

public enum TipoExecucao implements CaixaEnum<Character> {

	AUTOMATICO('A'),
	MANUAL('M');

	private final Character value;

	private TipoExecucao(Character value) {
		this.value = value;
	}

	public Character getValue() {
		return value;
	}

	public static TipoExecucao getByValue(Character value) {
		return EnumUtil.recupereByValue(values(), value);
	}

}
