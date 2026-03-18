package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.Telefone;

public class RetornoCreditaCarteiraEletronica extends SaidaBroker {

	private static final long serialVersionUID = 1L;
	
	private Integer codCanal;
	private Long nsuCanal;
	private Data dataCanal;
	private Hora horaCanal;
	private Telefone celular;
	private Integer otp;
	private Long nsuTransacao;
	private Decimal valor;
	private Data dataTransacao;
	private Hora horaTransacao;
	
	public Integer getCodCanal() {
		return codCanal;
	}
	public void setCodCanal(Integer codCanal) {
		this.codCanal = codCanal;
	}
	public Long getNsuCanal() {
		return nsuCanal;
	}
	public void setNsuCanal(Long nsuCanal) {
		this.nsuCanal = nsuCanal;
	}
	public Data getDataCanal() {
		return dataCanal;
	}
	public void setDataCanal(Data dataCanal) {
		this.dataCanal = dataCanal;
	}
	public Hora getHoraCanal() {
		return horaCanal;
	}
	public void setHoraCanal(Hora horaCanal) {
		this.horaCanal = horaCanal;
	}
	public Telefone getCelular() {
		return celular;
	}
	public void setCelular(Telefone celular) {
		this.celular = celular;
	}
	public Integer getOtp() {
		return otp;
	}
	public void setOtp(Integer otp) {
		this.otp = otp;
	}
	public Long getNsuTransacao() {
		return nsuTransacao;
	}
	public void setNsuTransacao(Long nsuTransacao) {
		this.nsuTransacao = nsuTransacao;
	}
	public Decimal getValor() {
		return valor;
	}
	public void setValor(Decimal valor) {
		this.valor = valor;
	}
	public Data getDataTransacao() {
		return dataTransacao;
	}
	public void setDataTransacao(Data dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	public Hora getHoraTransacao() {
		return horaTransacao;
	}
	public void setHoraTransacao(Hora horaTransacao) {
		this.horaTransacao = horaTransacao;
	}
	
}
