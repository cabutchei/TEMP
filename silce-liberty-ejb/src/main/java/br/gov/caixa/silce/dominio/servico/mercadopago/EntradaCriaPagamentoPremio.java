package br.gov.caixa.silce.dominio.servico.mercadopago;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.util.Email;

public class EntradaCriaPagamentoPremio implements EntradaHttp<RetornoPagamento> {

	private static final long serialVersionUID = 1L;

	private final Aposta<?> aposta;
	private final Email email;

	public EntradaCriaPagamentoPremio(Aposta<?> aposta, Email email) {
		super();
		this.aposta = aposta;
		this.email = email;
	}

	public Aposta<?> getAposta() {
		return aposta;
	}

	public Email getEmail() {
		return email;
	}
	
}
