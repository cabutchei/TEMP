package br.gov.caixa.silce.dominio.servico.marketplace;

public abstract class AbstractRetornoErro extends SaidaHttpMarketplace {

	private static final long serialVersionUID = 1L;
	
	private final RetornoErro retornoErro;
	
	protected AbstractRetornoErro() {
		this(null);
	}
	
	public AbstractRetornoErro(RetornoErro retornoErro) {
		this.retornoErro = retornoErro;
	}

	public RetornoErro getRetornoErro() {
		return retornoErro;
	}

	@Override
	public String toString() {
		return "RetornoErro [retornoErro=" + retornoErro + "]";
	}
	
}
