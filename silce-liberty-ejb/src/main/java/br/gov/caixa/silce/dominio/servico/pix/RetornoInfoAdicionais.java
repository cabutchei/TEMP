package br.gov.caixa.silce.dominio.servico.pix;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;

public class RetornoInfoAdicionais extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String valor;

	public RetornoInfoAdicionais(String nome, String valor) {
		super();
		this.nome = nome;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String getNsuTransacaoMp() {
		// TODO Auto-generated method stub
		return null;
	}

}
