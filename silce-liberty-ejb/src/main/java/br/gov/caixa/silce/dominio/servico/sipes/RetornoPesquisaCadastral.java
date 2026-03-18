package br.gov.caixa.silce.dominio.servico.sipes;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;

public class RetornoPesquisaCadastral extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private List<Bloqueio> bloqueios;

	public RetornoPesquisaCadastral() {
		super();
		this.bloqueios = new ArrayList<Bloqueio>();
	}

	public RetornoPesquisaCadastral(List<Bloqueio> bloqueios) {
		super();
		this.bloqueios = bloqueios;
	}

	public RetornoPesquisaCadastral(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public List<Bloqueio> getBloqueios() {
		return bloqueios;
	}

	public void setBloqueios(List<Bloqueio> bloqueios) {
		this.bloqueios = bloqueios;
	}

	@Override
	public String getNsuTransacaoMp() {
		return null;
	}

}
