package br.gov.caixa.silce.dominio.servico.apimanager;

import java.io.Serializable;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

public class RetornoConsultaApostaLog extends SaidaHttpAPIManager implements Serializable {

	private static final long serialVersionUID = 1L;

	Modalidade modalidadeJogo;
	Integer concursoInicialAposta;
	Integer concursoFinal;

	public Modalidade getModalidadeJogo() {
		return modalidadeJogo;
	}

	public void setModalidadeJogo(Modalidade modalidadeJogo) {
		this.modalidadeJogo = modalidadeJogo;
	}

	public Integer getConcursoInicialAposta() {
		return concursoInicialAposta;
	}

	public void setConcursoInicialAposta(Integer concursoInicialAposta) {
		this.concursoInicialAposta = concursoInicialAposta;
	}

	public Integer getConcursoFinal() {
		return concursoFinal;
	}

	public void setConcursoFinal(Integer concursoFinal) {
		this.concursoFinal = concursoFinal;
	}

}
