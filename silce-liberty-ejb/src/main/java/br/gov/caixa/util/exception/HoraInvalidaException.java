package br.gov.caixa.util.exception;

public class HoraInvalidaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HoraInvalidaException(Exception e) {
		super(e);
	}
	
	public HoraInvalidaException(String message) {
		super(message);
	}

}
