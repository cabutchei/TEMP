package br.gov.caixa.silce.dominio;

import java.io.Serializable;

import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;

public class AgrupadorCotasNaoContabilizadas implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer loterico;
	private Decimal valorCota;
	private Decimal valorTarifaCota;
	private Decimal valorCusteioCota;
	private Decimal valorTarifaCusteioCota;
	private Data dataHoraReserva;
	private Data dataFinalizacao;
	private Hora horaFinalizacao;
	private Long meioPagamento;
	private boolean enviado;

	public Integer getLoterico() {
		return loterico;
	}

	public void setLoterico(Integer loterico) {
		this.loterico = loterico;
	}

	public Decimal getValorCota() {
		return valorCota;
	}

	public void setValorCota(Decimal valorCota) {
		this.valorCota = valorCota;
	}

	public Decimal getValorTarifaCota() {
		return valorTarifaCota;
	}

	public void setValorTarifaCota(Decimal valorTarifaCota) {
		this.valorTarifaCota = valorTarifaCota;
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

	public Data getDataHoraReserva() {
		return dataHoraReserva;
	}

	public void setDataHoraReserva(Data dataHoraReserva) {
		this.dataHoraReserva = dataHoraReserva;
	}

	public Data getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Data dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public Hora getHoraFinalizacao() {
		return horaFinalizacao;
	}

	public void setHoraFinalizacao(Hora horaFinalizacao) {
		this.horaFinalizacao = horaFinalizacao;
	}

	public String getDataFormatada() {
		return getDataHoraReserva().toString();
	}

	public Long getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(Long meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	public boolean getEnviado() {
		return enviado;
	}

	public void setEnviado(boolean isEnviado) {
		this.enviado = isEnviado;
	}

	public String getEnviadoFormatado() {
		return this.enviado ? "SIM" : "NÃO";
	}

}
