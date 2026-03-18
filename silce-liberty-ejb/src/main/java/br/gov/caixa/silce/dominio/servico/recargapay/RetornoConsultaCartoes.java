package br.gov.caixa.silce.dominio.servico.recargapay;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;

public class RetornoConsultaCartoes extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private List<RetornoCartao> cartoes = new ArrayList<RetornoCartao>();

	public RetornoConsultaCartoes(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public RetornoConsultaCartoes() {
		this(null);
	}

	public boolean addCartao(String idCartao, Long mesValidade, Long anoValidade, String tipoCartao, String bandeiraCartao, Long primeirosDigitos, Long ultimosDigitos) {
		return addCartao(
			new RetornoCartao(idCartao, mesValidade, anoValidade, tipoCartao, bandeiraCartao, primeirosDigitos, ultimosDigitos));
	}

	public boolean addCartao(RetornoCartao cartao) {
		return this.cartoes.add(cartao);
	}

	@Override
	public String getNsuTransacaoMp() {
		return null;
	}

	public List<RetornoCartao> getCartoes() {
		return cartoes;
	}

	public void setCartoes(List<RetornoCartao> cartoes) {
		this.cartoes = cartoes;
	}
}
