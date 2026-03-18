package br.gov.caixa.silce.dominio;

import java.io.Serializable;

import br.gov.caixa.util.Decimal;

public class AgrupadorSimulaComissao implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer meioPagamento;
	private Decimal valorComissao;

	public Integer getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(Integer meioPagamento) {
		this.meioPagamento = meioPagamento;
	}
	public Decimal getValorComissao() {
		return valorComissao;
	}
	public void setValorComissao(Decimal valorComissao) {
		this.valorComissao = valorComissao;
	}
}
