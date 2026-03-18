package br.gov.caixa.silce.dominio.autoavaliacao;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

/**
 * 
 * @author f656372
 *
 */
public enum IndicadorAtivo implements CaixaEnum<Character> {

	ATIVO('A', true), INATIVO('I', false);

	private final Character codigo;
	private final Boolean ativo;

	private IndicadorAtivo(Character codigo, Boolean ativo) {
		this.codigo = codigo;
		this.ativo = ativo;
	}
	
	public Character getCodigo() {
		return codigo;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}

	public static IndicadorAtivo getByCodigo(Character codigo) {
		return EnumUtil.recupereByValue(values(), codigo);
	}
	
	@Override
	public Character getValue() {
		return getCodigo();
	}
	
}
