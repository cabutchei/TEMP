package br.gov.caixa.dominio.exception;



/**
 * Classe utilizada em erro durante acesso a recursos. Como banco de dados e MQ.
 * 
 * Lançado em momentos como: Timeout na MQ, erro de primary key duplicada...
 * @author c101482
 *
 */
public final class AmbienteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Recurso recurso;
	private final String causeCode;
	private final Throwable cause;
	private final String codigoMensagem;

	/**
	 * @param recurso Tipo do recurso
	 * @param cause Cause principal do erro
	 * @param causeCode código referente ao recurso
	 * @param codigoMensagem código da mensagem que será exibido ao cliente final. Este é o único 
	 * atributo alterável.
	 */
	public AmbienteException(Recurso recurso, Throwable cause, String causeCode, String codigoMensagem) {
		if(cause == null) {
			throw new IllegalArgumentException("cause não pode ser null");
		}
		this.recurso = recurso;
		this.cause = cause;
		this.causeCode = causeCode;
		this.codigoMensagem = codigoMensagem;
	}
	
	/**
	 * @see AmbienteException
	 */
	public AmbienteException(Recurso recurso, Throwable cause, String causeCode) {
		this(recurso, cause, causeCode, recurso.getCodigo());
	}

	public String getCauseCode() {
		return causeCode;
	}

	public Throwable getCause() {
		return cause;
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public String getCodigoMensagem() {
		return codigoMensagem;
	}

	
}
