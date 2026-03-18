package br.gov.caixa.silce.dominio.servico.comum;

import java.util.Arrays;
import java.util.List;

import br.gov.caixa.dominio.SaidaHttp;

public abstract class AbstractRetorno implements SaidaHttp {

	private static final long serialVersionUID = 1L;
	private static final List<Integer> successHttpCodes = Arrays.asList(200, 201, 204);

	private Integer statusCodeHttp;

	public AbstractRetorno() {
		// faz nada
	}

	public String getCodigoErro() {
		if (isOperacaoExecutadaComSucesso()) {
			throw new IllegalStateException("Não há erro para ser retornado.");
		}
		return getCodigoErroSafe();
	}

	public abstract String getMessage();

	protected abstract String getCodigoErroSafe();

	public abstract String getNsuTransacaoMp();

	@Override
	public Integer getStatusCodeHttp() {
		return statusCodeHttp;
	}

	@Override
	public void setStatusCodeHttp(Integer statusCodeHttp) {
		this.statusCodeHttp = statusCodeHttp;
	}
	
	@Override
	public final Boolean isOperacaoExecutadaComSucesso() {
		return statusCodeHttp != null && successHttpCodes.contains(statusCodeHttp); 
	}

}
