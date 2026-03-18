package br.gov.caixa.silce.dominio;

import br.gov.caixa.util.CaixaEnum;

public enum TipoOperacaoFinanceira implements CaixaEnum<Character> {

	CREDITO('1', "Crédito"), DEBITO('2', "Débito");

	private final Character value;
	private final String descricao;

	private TipoOperacaoFinanceira(Character value, String descricao) {
		this.value = value;
		this.descricao = descricao;

	}

	@Override
	public Character getValue() {
		return value;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoOperacaoFinanceira getByValue(Character value) {
		for (TipoOperacaoFinanceira tipo : TipoOperacaoFinanceira.values()) {
			if (tipo.value.equals(value)) {
				return tipo;
			}
		}
		return null;
	}
}
