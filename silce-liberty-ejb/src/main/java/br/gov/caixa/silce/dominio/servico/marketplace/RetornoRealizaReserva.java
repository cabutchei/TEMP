package br.gov.caixa.silce.dominio.servico.marketplace;

import java.util.List;

public class RetornoRealizaReserva extends AbstractRetornoErro {

	private static final long serialVersionUID = 1L;

	public RetornoRealizaReserva(Integer tempoExpiracao, List<DadosCotaBolao> cotas) {
		this.setTempoExpiracao(tempoExpiracao);
		this.setCotas(cotas);
	}

	public RetornoRealizaReserva(RetornoErro retornoErro) {
		super(retornoErro);
	}
}
