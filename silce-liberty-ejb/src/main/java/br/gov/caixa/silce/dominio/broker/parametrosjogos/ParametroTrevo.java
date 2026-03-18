package br.gov.caixa.silce.dominio.broker.parametrosjogos;


import java.io.Serializable;
import java.util.List;

public class ParametroTrevo implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<?> trevosSelecionados;

	public ParametroTrevo() {
		// construtor padrão para permitir a construção de um parâmetro vazio que será preenchido depois
	}

	public List<?> getTrevosSelecionados() {
		return trevosSelecionados;
	}

	public void setTrevosSelecionados(List<?> trevosSelecionados) {
		this.trevosSelecionados = trevosSelecionados;
	}

}