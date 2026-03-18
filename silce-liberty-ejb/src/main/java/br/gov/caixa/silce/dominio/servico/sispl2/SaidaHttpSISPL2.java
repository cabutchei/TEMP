package br.gov.caixa.silce.dominio.servico.sispl2;

import java.util.List;

import br.gov.caixa.dominio.SaidaHttp;

public class SaidaHttpSISPL2 implements SaidaHttp {

	private static final long serialVersionUID = 1L;
	private Integer statusCodeHttp;
	private String codigo;
	private String mensagem;
	private TipoMensagemSISPL2 tipo;
	private String redirect;
	private List<ErrosValidacao> errosValidacao;


	public SaidaHttpSISPL2() {
		// Construtor Padrão
	}

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

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getRedirect() {
		return this.redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public List<ErrosValidacao> getErrosValidacao() {
		return this.errosValidacao;
	}

	public void setErrosValidacao(List<ErrosValidacao> errosValidacao) {
		this.errosValidacao = errosValidacao;
	}

	public TipoMensagemSISPL2 getTipoMensagem() {
		return this.tipo;
	}

	public void setTipoMensagem(TipoMensagemSISPL2 tipoMensagem) {
		this.tipo = tipoMensagem;
	}
}
