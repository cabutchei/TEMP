package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.util.Decimal;

/**
 * @author c101482
 * 
 */
public class RetornoDetalhamentoFaixaPremiada extends SaidaBroker {

	private static final long serialVersionUID = 1L;

	private String nome;

	private Integer quantidade;

	private Decimal valor;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Decimal getValor() {
		return valor;
	}

	public void setValor(Decimal valor) {
		this.valor = valor;
	}
}
