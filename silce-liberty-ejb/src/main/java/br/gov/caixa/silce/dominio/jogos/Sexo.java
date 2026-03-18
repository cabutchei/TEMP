package br.gov.caixa.silce.dominio.jogos;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

/**
 * 
 * @author c101482
 *
 */
public enum Sexo implements CaixaEnum<Character> {

	MASCULINO('M'), 
	FEMININO('F');

	private final Character codigo;

	private Sexo(Character codigo) {
		this.codigo = codigo;
	}
	
	public Character getCodigo() {
		return codigo;
	}
	
	public static Sexo getByCodigo(Character codigo) {
		return EnumUtil.recupereByValue(values(), codigo);
	}
	
	@Override
	public Character getValue() {
		return getCodigo();
	}
	
}
