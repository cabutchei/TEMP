package br.gov.caixa.silce.dominio.servico.mercadopago;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;

public class RetornoPreparaCadastroUsuario extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private final String tokenRegistroExterno;

	public RetornoPreparaCadastroUsuario(String tokenRegistroExterno) {
		super();
		this.tokenRegistroExterno = tokenRegistroExterno;
	}

	public RetornoPreparaCadastroUsuario(RetornoErro retornoErro) {
		super(retornoErro);
		this.tokenRegistroExterno = null;
	}

	public String getTokenRegistroExterno() {
		return tokenRegistroExterno;
	}

	@Override
	public String getNsuTransacaoMp() {
		return null;
	}
	
}
