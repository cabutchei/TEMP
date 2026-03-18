package br.gov.caixa.silce.dominio.jogos;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

/**
 * Tipos de Concursos e seus códigos. 
 *
 */
public enum IndicadorSurpresinha implements CaixaEnum<Integer> {

	NAO_SURPRESINHA(1),
	SURPRESINHA(2),
	SURPRESINHA_NUMERICA(3);

	private final Integer codigo;

	private IndicadorSurpresinha(Integer codigo) {
		this.codigo = codigo;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public static IndicadorSurpresinha getByCodigo(Integer codigo) {
		return EnumUtil.recupereByValue(values(), codigo);
	}
	
	public boolean isSurpresinha() {
		return !this.equals(NAO_SURPRESINHA);
	}

	@Override
	public Integer getValue() {
		return getCodigo();
	}
	
}
