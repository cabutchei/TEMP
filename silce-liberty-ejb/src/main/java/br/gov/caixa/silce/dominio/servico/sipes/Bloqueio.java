package br.gov.caixa.silce.dominio.servico.sipes;

import java.io.Serializable;

public class Bloqueio implements Serializable {

	private static final long serialVersionUID = 1L;

	private Classificacao classificacao;
	private String dataInicio;
	private String mensagem;

	public Bloqueio(Classificacao classificacao, String dataInicio, String mensagem) {
		this.classificacao = classificacao;
		this.dataInicio = dataInicio;
		this.mensagem = mensagem;
	}

	public Classificacao getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Classificacao classificacao) {
		this.classificacao = classificacao;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classificacao == null) ? 0 : classificacao.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((mensagem == null) ? 0 : mensagem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bloqueio other = (Bloqueio) obj;
		if (classificacao == null) {
			if (other.classificacao != null)
				return false;
		} else if (!classificacao.equals(other.classificacao))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (mensagem == null) {
			if (other.mensagem != null)
				return false;
		} else if (!mensagem.equals(other.mensagem))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bloqueio [classificacao=" + classificacao + ", dataInicio=" + dataInicio + ", mensagem=" + mensagem + "]";
	}

}
