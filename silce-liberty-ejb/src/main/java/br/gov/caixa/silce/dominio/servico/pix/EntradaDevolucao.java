package br.gov.caixa.silce.dominio.servico.pix;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.servico.comum.RetornoDevolucaoPagamento;
import br.gov.caixa.util.Decimal;

public class EntradaDevolucao implements EntradaHttp<RetornoDevolucaoPagamento> {

	private static final long serialVersionUID = 1L;

	private Decimal valor;
	private String descricao;

	public EntradaDevolucao() {
	}

	public EntradaDevolucao(Decimal valor, String descricao) {
		super();
		this.valor = valor;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setValor(Decimal valor) {
		this.valor = valor;
	}

	public Decimal getValor() {
		return valor;
	}

}
