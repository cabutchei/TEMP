package br.gov.caixa.silce.dominio.exception;

import java.io.Serializable;

import javax.ejb.ApplicationException;

import br.gov.caixa.dominio.exception.ICodigoErro;
import br.gov.caixa.dominio.exception.NegocioException;

@ApplicationException(rollback = false)
public class ReservaCotaBolaoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public ReservaCotaBolaoException(Exception e) {
		super(null, e);
	}

	public ReservaCotaBolaoException(ICodigoErro codigoErro, Serializable[] arguments) {
		super(codigoErro, arguments);
	}

	public ReservaCotaBolaoException(ICodigoErro codigoErro) {
		super(codigoErro);
	}

	public ReservaCotaBolaoException(ICodigoErro codigoErro, Throwable t, Serializable... arguments) {
		super(codigoErro, t, arguments);
	}
}
