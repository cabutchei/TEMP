package br.gov.caixa.dominio.exception;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * 
 * @author f516198 - Sergio Henrique Weber Queiroz
 * 
 */
public class NegocioPixException extends Exception {

	private static final long serialVersionUID = 1L;

	private final ICodigoErro codigoErro;

	private final Serializable[] arguments;

	private final String mensagem;
	
	public NegocioPixException(ICodigoErro codigoErro, Serializable... arguments) {
		this(codigoErro, null, arguments);
	}
	
	/**
	 * @param codigoErro
	 * @param t Causa do erro, pode ser null
	 * @param arguments Argumentos utilizados como parâmetros para a mensagem. Ex: campos que são obrigatórios
	 */
	public NegocioPixException(ICodigoErro codigoErro, Throwable t, Serializable... arguments) {
		super(t);
		this.codigoErro = codigoErro;
		this.arguments = arguments.clone();
		this.mensagem = MessageFormat.format("Exceção de Negócio. Código de erro: {0}. Parâmetros: {1}", codigoErro,
				Arrays.toString(arguments));
	}

	public ICodigoErro getCodigoErro() {
		return codigoErro;
	}

	public Object[] getArguments() {
		return arguments.clone();
	}

	public String getCodigoMensagem() {
		return getCodigoErro().getCodigoMensagem().getCodigo();
	}

	@Override
	public String getMessage() {
		return mensagem;
	}

}
