package br.gov.caixa.silce.dominio.servico.marketplace;

import java.util.List;

public class Bolao extends AbstractRetornoErro {

	List<ApostaBolao> apostasBolao;

	private static final long serialVersionUID = 1L;

	public Bolao() {
		new Bolao(null);
	}

	public Bolao(List<ApostaBolao> apostasBolao) {
		this.apostasBolao = apostasBolao;
	}

	public List<ApostaBolao> getListApostasBolao() {
		return apostasBolao;
	}

}
