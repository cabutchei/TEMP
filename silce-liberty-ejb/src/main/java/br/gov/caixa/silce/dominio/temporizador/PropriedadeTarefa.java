package br.gov.caixa.silce.dominio.temporizador;

import java.io.Serializable;

/**
 * Domínio para mapear os valores das propriedades dos eventos associados as Tarefas.
 * 
 * @author c101486
 */
public class PropriedadeTarefa implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer codigoPropriedadeTarefa;
	private Tarefa tarefa;
	private PropriedadeEvento propriedadeEvento;
	private String valorPropriedadeTarefa;

	public Integer getCodigoPropriedadeTarefa() {
		return codigoPropriedadeTarefa;
	}

	public void setCodigoPropriedadeTarefa(Integer codigoPropriedadeTarefa) {
		this.codigoPropriedadeTarefa = codigoPropriedadeTarefa;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public PropriedadeEvento getPropriedadeEvento() {
		return propriedadeEvento;
	}

	public void setPropriedadeEvento(PropriedadeEvento propriedadeEvento) {
		this.propriedadeEvento = propriedadeEvento;
	}

	public String getValorPropriedadeTarefa() {
		return valorPropriedadeTarefa;
	}

	public void setValorPropriedadeTarefa(String valorPropriedadeTarefa) {
		this.valorPropriedadeTarefa = valorPropriedadeTarefa;
	}

}
