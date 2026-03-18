package br.gov.caixa.silce.dominio.servico.apimanager;

import br.gov.caixa.dominio.SaidaHttp;

public class SaidaHttpAPIManager implements SaidaHttp {

	private static final long serialVersionUID = 1L;
	private Integer statusCodeHttp;
	private String tipoMensagem;
	private String mensagem;
	private String codigoErro;

	@Override
	public Boolean isOperacaoExecutadaComSucesso() {
		return 200 == statusCodeHttp;
	}

	@Override
	public Integer getStatusCodeHttp() {
		return statusCodeHttp;
	}

	@Override
	public void setStatusCodeHttp(Integer statusCodeHttp) {
		this.statusCodeHttp = statusCodeHttp;
	}

	public String getTipoMensagem() {
		return tipoMensagem;
	}

	public void setTipoMensagem(String tipoMensagem) {
		this.tipoMensagem = tipoMensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getCodigoErro() {
		return codigoErro;
	}

	public void setCodigoErro(String codigoErro) {
		this.codigoErro = codigoErro;
	}

}
