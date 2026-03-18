package br.gov.caixa.silce.dominio.broker;

import java.util.List;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.util.Decimal;

/**
 * @author c101482
 * 
 */
public class RetornoDetalhamentoConcursoPremiado extends SaidaBroker {

	private static final long serialVersionUID = 1L;

	private Integer numeroConcurso;

	private Decimal valorBruto;

	private Decimal valorLiquido;

	private Decimal valorIRRF;
	
	private List<RetornoDetalhamentoFaixaPremiada> faixasPremiadas;

	public Integer getNumeroConcurso() {
		return numeroConcurso;
	}

	public void setNumeroConcurso(Integer numeroConcurso) {
		this.numeroConcurso = numeroConcurso;
	}

	public Decimal getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(Decimal valorBruto) {
		this.valorBruto = valorBruto;
	}

	public Decimal getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Decimal valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Decimal getValorIRRF() {
		return valorIRRF;
	}

	public void setValorIRRF(Decimal valorIRRF) {
		this.valorIRRF = valorIRRF;
	}

	public List<RetornoDetalhamentoFaixaPremiada> getFaixasPremiadas() {
		return faixasPremiadas;
	}

	public void setFaixasPremiadas(List<RetornoDetalhamentoFaixaPremiada> faixasPremiadas) {
		this.faixasPremiadas = faixasPremiadas;
	}
}
