package br.gov.caixa.silce.dominio.servico.marketplace;

import java.math.BigDecimal;
import java.util.List;

public class RetornoBuscaCotasDisponiveis extends AbstractRetornoErro {

	private static final long serialVersionUID = 1L;

	public RetornoBuscaCotasDisponiveis(Integer totalRegistros, Integer paginaAtual, Integer ultimaPagina, BigDecimal valorMenorCota, List<DadosCotaBolao> cotas) {
		this.totalRegistros = totalRegistros;
		this.paginaAtual = paginaAtual;
		this.ultimaPagina = ultimaPagina;
		this.valorMenorCota = valorMenorCota;
		this.cotas = cotas;
	}

	public RetornoBuscaCotasDisponiveis() {
		this(null, null, null, null, null);
	}

	public RetornoBuscaCotasDisponiveis(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public List<DadosCotaBolao> getCotas() {
		return cotas;
	}

	public Integer getPaginaAtual() {
		return paginaAtual;
	}

	@Override
	public void setCotas(List<DadosCotaBolao> cotas) {
		super.setCotas(cotas);
	}
}
