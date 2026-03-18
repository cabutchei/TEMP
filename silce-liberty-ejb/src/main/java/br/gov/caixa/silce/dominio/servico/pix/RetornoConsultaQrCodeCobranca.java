package br.gov.caixa.silce.dominio.servico.pix;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;

public class RetornoConsultaQrCodeCobranca extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private String qrcodeString;
	private String qrcodeArrByte;

	public RetornoConsultaQrCodeCobranca(String qrcodeString, String qrcodeArrByte) {
		super();
		this.qrcodeString = qrcodeString;
		this.qrcodeArrByte = qrcodeArrByte;
	}

	@Override
	public String getNsuTransacaoMp() {
		return null;
	}

	public String getQrcodeString() {
		return qrcodeString;
	}


	public void setQrcodeString(String qrcodeString) {
		this.qrcodeString = qrcodeString;
	}

	public String getQrcodeArrByte() {
		return qrcodeArrByte;
	}

	public void setQrcodeArrByte(String qrcodeArrByte) {
		this.qrcodeArrByte = qrcodeArrByte;
	}

}
