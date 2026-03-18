package br.gov.caixa.silce.dominio.broker.finalizacaptacaoconcurso;

import br.gov.caixa.dominio.EntradaBroker;
import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.silce.dominio.jogos.Concurso;

public class EntradaFinalizaCaptacaoConcurso implements EntradaBroker<SaidaBroker> {

	private static final long serialVersionUID = 1L;
	
	private Concurso concurso;

	public void setConcurso(Concurso concurso) {
		this.concurso = concurso;
	}

	public Concurso getConcurso() {
		return concurso;
	}
	
}
