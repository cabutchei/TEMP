package br.gov.caixa.dominio;

import java.io.Serializable;

/**
 * @author c101482
 */
public interface Auditavel extends Serializable {

	public Long getIdentificador();

	public String getDescricao();

}
