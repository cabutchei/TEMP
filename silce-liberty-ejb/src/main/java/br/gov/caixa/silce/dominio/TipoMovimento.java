package br.gov.caixa.silce.dominio;

import br.gov.caixa.util.CaixaEnum;

/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 *
 */
public enum TipoMovimento implements CaixaEnum<Long> {
	REGISTRA_APOSTA(1L);
	
	private final Long value;
	
	private TipoMovimento(Long value) {
		this.value = value;
	}

	public Long getValue() {
		return value;
	}

}
