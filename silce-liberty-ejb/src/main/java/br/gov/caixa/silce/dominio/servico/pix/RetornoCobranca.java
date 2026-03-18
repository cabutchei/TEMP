package br.gov.caixa.silce.dominio.servico.pix;

import java.util.Calendar;

import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;

public class RetornoCobranca extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private String txId;
	private String pixCopiaECola;
	private String qrcodeArrByte;
	private Integer expiracao;
	private String dataHoraCriacao;
	private Compra compra;

	public RetornoCobranca() {
		super();
	}

	public RetornoCobranca(String pixCopiaECola, String txId, Integer expiracao, String dataHoraCriacao) {
		super();
		this.pixCopiaECola = pixCopiaECola;
		this.txId = txId;
		this.expiracao = expiracao;
		this.dataHoraCriacao = dataHoraCriacao;
	}

	public RetornoCobranca(RetornoErro retornoErro) {
		super(retornoErro);
	}

	@Override
	public String getNsuTransacaoMp() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPixCopiaECola() {
		return pixCopiaECola;
	}

	public void setPixCopiaECola(String pixCopiaECola) {
		this.pixCopiaECola = pixCopiaECola;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getQrcodeArrByte() {
		return qrcodeArrByte;
	}

	public void setQrcodeArrByte(String qrcodeArrByte) {
		this.qrcodeArrByte = qrcodeArrByte;
	}

	public Integer getExpiracao() {
		return expiracao;
	}

	public void setExpiracao(Integer expiracao) {
		this.expiracao = expiracao;
	}

	public String getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public void setDataHoraCriacao(String dataHoraCriacao) {
		this.dataHoraCriacao = dataHoraCriacao;
	}

	public Data getDataHoraExpiracao() {
		Data data = DataUtil.stringToData(getDataHoraCriacao(), DataUtil.DIA_HORA_MILISEGUNDOS_EUA);
		data.add(Calendar.SECOND, getExpiracao());

		return data;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

}
