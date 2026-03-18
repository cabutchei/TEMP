package br.gov.caixa.silce.dominio;

import java.io.Serializable;

import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

public class ComplementoFinanceiro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Data dataReferente;
	private TipoOperacaoFinanceira tipoOperacao;
	private Long codigoMeioPagamento;
	private Decimal valor;
	private String justificativa;
	private Decimal valorCota;
	private Decimal valorTarifa;
	private Decimal valorCusteioCota;
	private Decimal valorTarifaCusteioCota;
	private Long unidadeLoterica;
	private Integer tipo;


	public ComplementoFinanceiro() {
		// Construtor vazio para criação dinâmica de linhas para a geração do SIDEC manual
	}

	public ComplementoFinanceiro(Data data, TipoOperacaoFinanceira tipo, Decimal valor) {
		this.dataReferente = data;
		this.tipoOperacao = tipo;
		this.valor = valor;
	}

	public ComplementoFinanceiro(Data data, Long codigoMeioPagamento, TipoOperacaoFinanceira tipo, Decimal valor) {
		this.dataReferente = data;
		this.codigoMeioPagamento = codigoMeioPagamento;
		this.tipoOperacao = tipo;
		this.valor = valor;
	}

	public ComplementoFinanceiro(TipoOperacaoFinanceira tipo, Long codigoMeioPagamento, Decimal valor, String justificativa) {
		this.tipoOperacao = tipo;
		this.codigoMeioPagamento = codigoMeioPagamento;
		this.valor = valor;
		this.justificativa = justificativa;
	}

	public Data getDataReferente() {
		return dataReferente;
	}

	public void setDataReferente(Data dataReferente) {
		this.dataReferente = dataReferente;
	}

	public TipoOperacaoFinanceira getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacaoFinanceira tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Decimal getValor() {
		return valor;
	}

	public void setValor(Decimal valor) {
		this.valor = valor;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public Long getCodigoMeioPagamento() {
		return codigoMeioPagamento;
	}

	public void setCodigoMeioPagamento(Long codigoMeioPagamento) {
		this.codigoMeioPagamento = codigoMeioPagamento;
	}

	@Override
	public String toString() {
		return "Campos do Complemento SIDEC: Data Referente = " + dataReferente + ", Operação = " + tipoOperacao + ", Justificativa = " + justificativa + ", Valor = " + valor;
	}

	public Decimal getValorCota() {
		return valorCota;
	}

	public void setValorCota(Decimal valorCota) {
		this.valorCota = valorCota;
	}

	public Decimal getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(Decimal valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public Decimal getValorCusteioCota() {
		return valorCusteioCota;
	}

	public void setValorCusteioCota(Decimal valorCusteioCota) {
		this.valorCusteioCota = valorCusteioCota;
	}

	public Decimal getValorTarifaCusteioCota() {
		return valorTarifaCusteioCota;
	}

	public void setValorTarifaCusteioCota(Decimal valorTarifaCusteioCota) {
		this.valorTarifaCusteioCota = valorTarifaCusteioCota;
	}

	public Long getUnidadeLoterica() {
		return unidadeLoterica;
	}

	public void setUnidadeLoterica(Long unidadeLoterica) {
		this.unidadeLoterica = unidadeLoterica;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

}
