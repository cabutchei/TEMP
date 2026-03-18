package br.gov.caixa.silce.dominio.servico.comum;

import br.gov.caixa.util.Decimal;

public class RetornoStatusPagamento {

	private StatusPagamento statusPagamento;
	private String nsuTransacaoDebito;
	private String statusDetail;
	private Decimal valorPago;

	public RetornoStatusPagamento(StatusPagamento statusPagamento, String nsuTransacaoDebito,
		String statusDetail, Decimal valorPago) {
		super();
		this.nsuTransacaoDebito = nsuTransacaoDebito;
		this.statusDetail = statusDetail;
		this.statusPagamento = statusPagamento;
		this.valorPago = valorPago;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RetornoStatusPagamento [statusPagamento=");
		builder.append(statusPagamento);
		builder.append(", nsuTransacaoDebito=");
		builder.append(this.getNsuTransacaoDebito());
		builder.append(", statusDetail=");
		builder.append(this.getStatusDetail());
		builder.append(']');
		return builder.toString();
	}

	public String getNsuTransacaoDebito() {
		return nsuTransacaoDebito;
	}

	public String getStatusDetail() {
		return statusDetail;
	}

	public StatusPagamento getStatusPagamento() {
		return statusPagamento;
	}

	public Decimal getValorPago() {
		return valorPago;
	}
}