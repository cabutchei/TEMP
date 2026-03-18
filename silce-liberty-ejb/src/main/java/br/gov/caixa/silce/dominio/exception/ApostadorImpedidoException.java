package br.gov.caixa.silce.dominio.exception;

import java.io.Serializable;

import javax.ejb.ApplicationException;

import br.gov.caixa.dominio.exception.ICodigoErro;
import br.gov.caixa.dominio.exception.NegocioException;

@ApplicationException(rollback = false)
public class ApostadorImpedidoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public ApostadorImpedidoException(Exception e) {
		super(null, e);
	}

	public ApostadorImpedidoException(ICodigoErro codigoErro, Serializable[] arguments) {
		super(codigoErro, arguments);
	}

	public ApostadorImpedidoException(ICodigoErro codigoErro) {
		super(codigoErro);
	}

	public ApostadorImpedidoException(ICodigoErro codigoErro, Throwable t, Serializable... arguments) {
		super(codigoErro, t, arguments);
	}
}
