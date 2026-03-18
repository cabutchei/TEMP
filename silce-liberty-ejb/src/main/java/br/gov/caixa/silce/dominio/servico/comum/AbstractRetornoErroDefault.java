package br.gov.caixa.silce.dominio.servico.comum;

import br.gov.caixa.util.StringUtil;

public abstract class AbstractRetornoErroDefault extends AbstractRetorno {

	private static final long serialVersionUID = 1L;
	
	private final RetornoErro retornoErro;
	
	protected AbstractRetornoErroDefault() {
		this(null);
	}
	
	protected AbstractRetornoErroDefault(RetornoErro retornoErro) {
		super();
		this.retornoErro = retornoErro;
	}

	@Override
	protected String getCodigoErroSafe() {
		if (retornoErro != null) {
			if (retornoErro.getCauseCode() != null) {
				return retornoErro.getCauseCode().toString();
			} else if (retornoErro.getCode() != null) {
				return retornoErro.getCode().toString();
			}
		}
		return StringUtil.EMPTY;
	}

	public String getMessage() {
		return retornoErro.getMessage();
	}

	public String getError() {
		return retornoErro.getError();
	}

	public Integer getStatus() {
		return retornoErro.getStatus();
	}

	public Integer getCauseCode() {
		return retornoErro.getCauseCode();
	}

	public String getCauseMessage() {
		return retornoErro.getCauseMessage();
	}

	public RetornoErro getRetornoErro() {
		return retornoErro;
	}

	@Override
	public String toString() {
		return "RetornoErroDefault [retornoErro=" + retornoErro + "]";
	}
	
}
