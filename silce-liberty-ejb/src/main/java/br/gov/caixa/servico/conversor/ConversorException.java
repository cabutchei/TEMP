package br.gov.caixa.servico.conversor;

import java.text.MessageFormat;

public final class ConversorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConversorException(Throwable cause) {
		this("Erro de conversão.", cause);
	}

	public ConversorException(String message, Throwable cause) {
		this(message, cause, (Object[]) null);
	}

	public ConversorException(String message, Throwable cause, Object... params) {
		super(MessageFormat.format(message, params), cause);
		if (cause == null) {
			throw new NullPointerException("cause não pode ser null");
		}
	}
	
}
