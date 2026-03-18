package br.gov.caixa.silce.dominio.servico.sispl2;

public class ErrosValidacao {

	private String campo;
	private String valor;
	private String mensagem;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

}
