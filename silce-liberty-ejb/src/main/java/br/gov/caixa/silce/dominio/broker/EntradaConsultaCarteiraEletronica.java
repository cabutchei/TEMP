package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.EntradaBroker;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.Telefone;

public class EntradaConsultaCarteiraEletronica implements EntradaBroker<RetornoConsultaCarteiraEletronica> {

	private static final long serialVersionUID = 1L;

	private Integer codCanal;
	private Long nsuCanal;
	private Data dataCanal;
	private Hora horaCanal;
	private Telefone celular;
	private Integer otp;
	
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

}
