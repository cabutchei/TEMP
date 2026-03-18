package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.EntradaBroker;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.Telefone;

public class EntradaDebitaCarteiraEletronica implements EntradaBroker<RetornoDebitaCarteiraEletronica> {

	private static final long serialVersionUID = 1L;

	private Integer codCanal;
	private Long nsuCanal;
	private Data dataCanal;
	private Hora horaCanal;
	private Telefone celular;
	private Integer otp;
	private Decimal valor;

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

	public Decimal getValor() {
		return valor;
	}

	public void setValor(Decimal valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "EntradaDebitaCarteiraEletronica [codCanal=" + codCanal + ", nsuCanal=" + nsuCanal + ", dataCanal=" + dataCanal
				+ ", horaCanal=" + horaCanal + ", celular=" + celular + ", otp=" + otp + ", valor=" + valor + "]";
	}

}
