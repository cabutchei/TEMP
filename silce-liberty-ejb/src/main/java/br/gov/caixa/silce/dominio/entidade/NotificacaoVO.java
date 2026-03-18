package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import br.gov.caixa.util.Data;

public class NotificacaoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private AgendamentoNotificacao agendamentoNotificacao;
	private Apostador apostador;
	private Data dataVisualizacao;
	private Data dataExclusao;

	public static NotificacaoVO fromEntidade(Notificacao notificacao) {
		return new NotificacaoVO(notificacao.getAgendamentoNotificacao(), notificacao.getApostador(), notificacao.getDataVisualizacao(), notificacao.getDataExclusao());
	}

	public NotificacaoVO(AgendamentoNotificacao agendamentoNotificacao, Apostador apostador, Data dataVisualizacao, Data dataExclusao) {
		this.agendamentoNotificacao = agendamentoNotificacao;
		this.apostador = apostador;
		this.dataVisualizacao = dataVisualizacao;
		this.dataExclusao = dataExclusao;
	}

	public AgendamentoNotificacao getAgendamentoNotificacao() {
		return agendamentoNotificacao;
	}

	public void setAgendamentoNotificacao(AgendamentoNotificacao agendamentoNotificacao) {
		this.agendamentoNotificacao = agendamentoNotificacao;
	}

	public Apostador getApostador() {
		return apostador;
	}

	public void setApostador(Apostador apostador) {
		this.apostador = apostador;
	}

	public Data getDataVisualizacao() {
		return dataVisualizacao;
	}

	public void setDataVisualizacao(Data dataVisualizacao) {
		this.dataVisualizacao = dataVisualizacao;
	}

	public Data getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Data dataExclusao) {
		this.dataExclusao = dataExclusao;
	}
}
