package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.EntradaMQ;
import br.gov.caixa.dominio.SaidaMQ;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;

public class ProcessaCompra implements EntradaMQ<ProcessaCompra>, SaidaMQ {

	private static final long serialVersionUID = 1L;

	private Long idCompra;
	private Data dataCompra;
	private Hora horaCompra;
	private Decimal valorCompra;
	private Boolean salvarCartao;

	public ProcessaCompra() {
		this(null, null, null, null, null);
	}
	
	public ProcessaCompra(Long idCompra, Data dataCompra, Hora horaCompra, Decimal valorCompra, Boolean salvarCartao) {
		this.idCompra = idCompra;
		this.dataCompra = dataCompra;
		this.horaCompra = horaCompra;
		this.valorCompra = valorCompra;
		this.salvarCartao = Boolean.TRUE.equals(salvarCartao);
	}

	public Long getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(Long idCompra) {
		this.idCompra = idCompra;
	}

	public Data getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Data dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Hora getHoraCompra() {
		return horaCompra;
	}

	public void setHoraCompra(Hora horaCompra) {
		this.horaCompra = horaCompra;
	}

	public Decimal getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(Decimal valorCompra) {
		this.valorCompra = valorCompra;
	}
	
	@Override
	public Boolean isOperacaoExecutadaComSucesso() {
		return true;
	}

	@Override
	public String toString() {
		return "ProcessaCompra [idCompra=" + idCompra + ", dataCompra=" + dataCompra + ", horaCompra=" + horaCompra
				+ ", valorCompra=" + valorCompra + "]";
	}

	/**
	 * @return the salvarCartao
	 */
	public Boolean getSalvarCartao() {
		return salvarCartao;
	}

	/**
	 * @param salvarCartao
	 *            the salvarCartao to set
	 */
	public void setSalvarCartao(Boolean salvarCartao) {
		this.salvarCartao = salvarCartao;
	}
	
}
