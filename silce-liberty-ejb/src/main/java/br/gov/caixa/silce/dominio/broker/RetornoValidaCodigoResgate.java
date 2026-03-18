package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.SaidaBroker;

public class RetornoValidaCodigoResgate extends SaidaBroker {

	private static final long serialVersionUID = 1L;
	
	private Integer quantidadeTentativasRestantes;

	public Integer getQuantidadeTentativasRestantes() {
		return quantidadeTentativasRestantes;
	}

	public void setQuantidadeTentativasRestantes(Integer quantidadeTentativasRestantes) {
		this.quantidadeTentativasRestantes = quantidadeTentativasRestantes;
	}
	
}
