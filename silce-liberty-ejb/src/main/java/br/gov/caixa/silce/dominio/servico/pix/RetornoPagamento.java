package br.gov.caixa.silce.dominio.servico.pix;

import java.util.List;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.StatusPagamento;

public class RetornoPagamento extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private String txid;
	private String status;
	private List<RetornoInfoAdicionais> infoAdicionais;
	private StatusPagamento statusPagamento;
	private List<RetornoPix> pix;
	private String retornoJson;

	public RetornoPagamento(String txid, String status, List<RetornoInfoAdicionais> infoAdicionais, StatusPagamento statusPagamento, List<RetornoPix> pix) {
		super();
		this.txid = txid;
		this.status = status;
		this.infoAdicionais = infoAdicionais;
		this.statusPagamento = statusPagamento;
		this.pix = pix;
	}

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public String getStatusCobranca() {
		return status;
	}

	public void setStatusCobranca(String status) {
		this.status = status;
	}

	public List<RetornoInfoAdicionais> getInfoAdicionais() {
		return infoAdicionais;
	}

	public void setInfoAdicionais(List<RetornoInfoAdicionais> infoAdicionais) {
		this.infoAdicionais = infoAdicionais;
	}

	public List<RetornoPix> getPix() {
		return pix;
	}

	public void setPix(List<RetornoPix> pix) {
		this.pix = pix;
	}

	public StatusPagamento getStatusPagamento() {
		return statusPagamento;
	}

	public void setStatusPagamento(StatusPagamento statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	@Override
	public String getNsuTransacaoMp() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRetornoJson() {
		return retornoJson;
	}

	public void setRetornoJson(String retornoJson) {
		this.retornoJson = retornoJson;
	}
}
