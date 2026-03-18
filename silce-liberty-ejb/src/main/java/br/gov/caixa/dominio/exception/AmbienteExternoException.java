package br.gov.caixa.dominio.exception;



/**
 * Classe utilizada em erro de ambiente de sistemas externos
 * 
 * @author c101482
 *
 */
public final class AmbienteExternoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String codigo;
	private final String sistema;

	public AmbienteExternoException(String sistema, String codigo, String mensagem) {
		super(mensagem);
		this.sistema = sistema;
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getSistema() {
		return sistema;
	}
	
}
