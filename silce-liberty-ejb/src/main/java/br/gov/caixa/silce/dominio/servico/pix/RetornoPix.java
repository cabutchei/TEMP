package br.gov.caixa.silce.dominio.servico.pix;

import java.util.List;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoDevolucaoPagamento;
import br.gov.caixa.util.Decimal;

public class RetornoPix extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private String endToEndId;
	private String txid;
	private Decimal valor;
	private List<RetornoDevolucaoPagamento> devolucoes;

	public RetornoPix(String endToEndId, String txid, Decimal valor, List<RetornoDevolucaoPagamento> devolucoes) {
		super();
		this.endToEndId = endToEndId;
		this.txid = txid;
		this.valor = valor;
		this.devolucoes = devolucoes;
	}

	public RetornoPix() {
	}

	public String getEndToEndId() {
		return endToEndId;
	}

	public void setEndToEndId(String endToEndId) {
		this.endToEndId = endToEndId;
	}

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public Decimal getValor() {
		return valor;
	}

	public void setValor(Decimal valor) {
		this.valor = valor;
	}

	public List<RetornoDevolucaoPagamento> getDevolucoes() {
		return devolucoes;
	}

	public void setDevolucoes(List<RetornoDevolucaoPagamento> devolucoes) {
		this.devolucoes = devolucoes;
	}

	@Override
	public String getNsuTransacaoMp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "RetornoPix [endToEndId=" + endToEndId + ", txid=" + txid + ", valor=" + valor + ", devolucoes=" + devolucoes + "]";
	}

}
