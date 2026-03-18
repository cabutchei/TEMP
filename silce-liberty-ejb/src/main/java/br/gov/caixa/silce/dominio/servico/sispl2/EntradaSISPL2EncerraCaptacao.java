package br.gov.caixa.silce.dominio.servico.sispl2;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.jogos.Concurso;

public class EntradaSISPL2EncerraCaptacao implements EntradaHttp<RetornoSISPL2EncerraCaptacao> {

	private static final long serialVersionUID = 1L;
	private final Concurso concurso;


	public EntradaSISPL2EncerraCaptacao(Concurso concurso) {
		this.concurso = concurso;
	}

	public Concurso getConcurso() {
		return this.concurso;
	}
}
