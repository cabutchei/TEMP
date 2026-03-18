package br.gov.caixa.util;

import java.io.Serializable;

/**
 * 
 * Interface que deve ser utilizada por enuns internos a classes que refletem tabelas
 * de domínio.
 * 
 * E por enuns que representam colunas de tabelas
 * 
 * 
 * @author c101482 - Vinicius Ferraz Campos Florentino
 *
 * @param <X>
 */
public interface CaixaEnum<X> extends Serializable {

	/**
	 * Valor que identifica unicamente o Enum, ex: PK da entidade
	 * @return
	 */
	public X getValue();
	
	
}
