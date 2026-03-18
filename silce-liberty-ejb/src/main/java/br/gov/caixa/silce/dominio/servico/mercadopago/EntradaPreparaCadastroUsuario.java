package br.gov.caixa.silce.dominio.servico.mercadopago;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.entidade.Apostador;
import br.gov.caixa.util.Email;

public class EntradaPreparaCadastroUsuario implements EntradaHttp<RetornoPreparaCadastroUsuario> {

	private static final long serialVersionUID = 1L;

	private final Apostador apostador;
	private final Email email;

	public EntradaPreparaCadastroUsuario(Apostador apostador, Email emailInformado) {
		super();
		this.apostador = apostador;
		this.email = emailInformado;
	}

	public Apostador getApostador() {
		return apostador;
	}
	
	public Email getEmail() {
		return email;
	}

}
