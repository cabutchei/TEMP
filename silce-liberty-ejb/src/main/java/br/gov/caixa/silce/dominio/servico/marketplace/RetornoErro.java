package br.gov.caixa.silce.dominio.servico.marketplace;

import java.io.Serializable;
import java.util.Map;

public class RetornoErro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigoHttp;
	private String timestamp;
	private String tipo;
	private String titulo;
	private String mensagem;
	private String mensagemUsuario;
	private Map<String, String> objetos;

	public RetornoErro(Integer codigoHttp, String timestamp, String tipo, String titulo, String mensagem, String mensagemUsuario, Map<String, String> objetos) {
		this.codigoHttp = codigoHttp;
		this.timestamp = timestamp;
		this.tipo = tipo;
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.mensagemUsuario = mensagemUsuario;
		this.objetos = objetos;
	}

	public Integer getCodigoHttp() {
		return codigoHttp;
	}

	public void setCodigoHttp(Integer codigoHttp) {
		this.codigoHttp = codigoHttp;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagemUsuario() {
		return mensagemUsuario;
	}

	public void setMensagemUsuario(String mensagemUsuario) {
		this.mensagemUsuario = mensagemUsuario;
	}

	@Override
	public String toString() {
		return "RetornoErro [codigoHttp=" + codigoHttp + ", timestamp=" + timestamp + ", tipo=" + tipo + ", titulo=" + titulo + ", mensagem=" + mensagem + ", mensagemUsuario="
			+ mensagemUsuario + ", objetos=" + objetos + "]";
	}

	public void setObjetos(Map<String, String> objetos) {
		this.objetos = objetos;
	}

	public Map<String, String> getObjetos() {
		return objetos;
	}
}
