package br.gov.caixa.silce.dominio.servico.marketplace;

import br.gov.caixa.dominio.EntradaHttp;

public final class EntradaDetalhamentoBolao implements EntradaHttp<RetornoDetalhamentoBolao> {

	private static final long serialVersionUID = 1L;

	private String idBolao;

	public EntradaDetalhamentoBolao(String idBolao) {
		this.idBolao = idBolao;
	}

	@Override
	public String toString() {
		return "EntradaDetalhamentoBolao [idBolao=" + idBolao + "]";
	}

	public String getIdBolao() {
		return idBolao;
	}

	public void setIdBolao(String idBolao) {
		this.idBolao = idBolao;
	}

}
