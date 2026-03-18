package br.gov.caixa.silce.dominio.servico.marketplace;

import java.util.List;

public class RetornoConsultaCotasApostador extends AbstractRetornoErro {

	private static final long serialVersionUID = 1L;

	public RetornoConsultaCotasApostador() {
	}

	public RetornoConsultaCotasApostador(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public RetornoConsultaCotasApostador(Integer tempoExpiracao, List<DadosCotaBolao> cotas) {
		this.setTempoExpiracao(tempoExpiracao);
		this.setCotas(cotas);
	}
}
