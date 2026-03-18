package br.gov.caixa.silce.dominio.exception;

import java.io.Serializable;

import br.gov.caixa.dominio.exception.ICodigoErro;
import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.servico.marketplace.RetornoErro;

public class MarketplaceException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public enum TipoExcecao {
		ERRO_OPERACAO, TIMEOUT;
	}

	public MarketplaceException(TipoExcecao tipoExcecao, ICodigoErro codigoErro, RetornoErro retornoErro, Serializable... arguments) {
		super(codigoErro, arguments, retornoErro.toString(), "Tipo de excecao: " + tipoExcecao);
	}

	public MarketplaceException(TipoExcecao tipoExcecao, ICodigoErro codigoErro, Serializable... arguments) {
		this(tipoExcecao, codigoErro, null, arguments);
	}
}
