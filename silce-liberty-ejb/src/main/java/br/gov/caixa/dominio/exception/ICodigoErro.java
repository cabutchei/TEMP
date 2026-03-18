package br.gov.caixa.dominio.exception;

import java.io.Serializable;

/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 *
 */
public interface ICodigoErro extends Serializable {

	public ICodigoMensagem getCodigoMensagem();
	
	public String getCodigoErro();

}
