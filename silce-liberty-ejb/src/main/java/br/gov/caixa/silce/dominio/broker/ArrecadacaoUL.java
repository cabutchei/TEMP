package br.gov.caixa.silce.dominio.broker;

import java.io.Serializable;

import br.gov.caixa.silce.dominio.entidade.Loterica;
import br.gov.caixa.silce.dominio.jogos.Modalidade;

public class ArrecadacaoUL implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Loterica loterica;
	
	private Double valor;
	
	private Modalidade modalidade;
	
	private Long quantidade;

	public Loterica getLoterica() {
		return loterica;
	}

	public void setLoterica(Loterica loterica) {
		this.loterica = loterica;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	
}
