package br.gov.caixa.silce.dominio.autoavaliacao;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

/**
 * 
 * @author f656372
 *
 */
public enum IndicadorResposta implements CaixaEnum<Character> {

	VERDADEIRO('V'), FALSO('F');

	private final Character codigo;

	private IndicadorResposta(Character codigo) {
		this.codigo = codigo;
	}
	
	public Character getCodigo() {
		return codigo;
	}

	public static IndicadorResposta getByCodigo(Character codigo) {
		return EnumUtil.recupereByValue(values(), codigo);
	}
	
	@Override
	public Character getValue() {
		return getCodigo();
	}
	
}
