package br.gov.caixa.silce.dominio;

import java.io.Serializable;

import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.Decimal;

public class AgrupadorPainelVendas implements Serializable {

	private static final long serialVersionUID = 1L;

	private Modalidade modalidade;
	private Meio meio;
	private Integer concurso;
	private Long qtdAposta;
	private Decimal valorTotalAposta;
	private Long qtdCombo;
	private Subcanal subcanal;
	private Decimal valorTotalCompraCombo;
	private Long qtdCota;
	private Decimal valorTotalCota;
	private Decimal valorTotalApostaIndividual;
	private Long qtdApostaIndividual;

	public Decimal getValorTotalModalidadeSubcanal() {
		return valorTotalAposta.add(valorTotalCompraCombo).add(valorTotalCota);
	}

	public long getQtdTotalModalidadeSubcanal() {
		return qtdAposta + qtdCombo + qtdCota;
	}

	public Long getQtdApostaEfetivada() {
		return qtdAposta;
	}

	public void setQtdApostaEfetivada(Long qtdApostaEfetivada) {
		this.qtdAposta = qtdApostaEfetivada;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Integer getConcurso() {
		return concurso;
	}

	public void setConcurso(Integer concurso) {
		this.concurso = concurso;
	}

	public Long getQtdAposta() {
		return qtdAposta;
	}

	public void setQtdAposta(Long qtdAposta) {
		this.qtdAposta = qtdAposta;
	}

	public void setValorTotalAposta(Decimal decimal) {
		this.valorTotalAposta = decimal;
	}

	public Decimal getValorTotalAposta() {
		return valorTotalAposta;
	}

	public Long getQtdCombo() {
		return qtdCombo;
	}

	public void setQtdCombo(Long qtdCombo) {
		this.qtdCombo = qtdCombo;
	}

	public Subcanal getSubcanal() {
		return subcanal;
	}

	public void setSubcanal(Subcanal subcanal) {
		this.subcanal = subcanal;
	}

	public Meio getMeio() {
		return meio;
	}

	public void setMeio(Meio meio) {
		this.meio = meio;
	}

	public void setvalorTotalCompraCombo(Decimal decimal) {
		this.valorTotalCompraCombo = decimal;
	}

	public Decimal getvalorTotalCompraCombo() {
		return valorTotalCompraCombo;
	}

	public Long getQtdCota() {
		return qtdCota;
	}

	public void setQtdCota(Long qtdCota) {
		this.qtdCota = qtdCota;
	}

	public Decimal getValorTotalCota() {
		return valorTotalCota;
	}

	public void setValorTotalCota(Decimal valorTotalCota) {
		this.valorTotalCota = valorTotalCota;
	}

	public Decimal getValorTotalApostaIndividual() {
		return valorTotalApostaIndividual;
	}

	public void setValorTotalApostaIndividual(Decimal valorTotalApostaIndividual) {
		this.valorTotalApostaIndividual = valorTotalApostaIndividual;
	}

	public Long getQtdApostaIndividual() {
		return qtdApostaIndividual;
	}

	public void setQtdApostaIndividual(Long qtdApostaIndividual) {
		this.qtdApostaIndividual = qtdApostaIndividual;
	}

}
