package br.gov.caixa.dominio;

import java.io.Serializable;

/**
 * Todas as entidades JPA devem ser subclasses desta.
 * 
 * @author c101482 - Vinicius Ferraz Campos Florentino
 *
 * @param <T>
 */
public abstract class AbstractEntidade<T extends Serializable> implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract T getId();

	public abstract void setId(T id);
	
	protected StringBuilder createToStringBuilder() {
		return new StringBuilder(TAMANHO_INICIAL_TO_STRING);
	}

	private static final int TAMANHO_INICIAL_TO_STRING = 100;
}
