package br.gov.caixa.util.exception;

public class DataInvalidaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataInvalidaException(Exception e) {
		super(e);
	}
	
	public DataInvalidaException(String message) {
		super(message);
	}

}
