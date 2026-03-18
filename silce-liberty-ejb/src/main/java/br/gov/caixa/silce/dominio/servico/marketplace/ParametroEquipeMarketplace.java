package br.gov.caixa.silce.dominio.servico.marketplace;

import java.io.Serializable;

public class ParametroEquipeMarketplace implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean indicadorSelecao;
	private String nome;
	private Integer numero;
	private String siglaPais;
	private String pais;
	private String uf;

	public ParametroEquipeMarketplace() {
	}

	public ParametroEquipeMarketplace(Boolean indicadorSelecao, String nome, Integer numero, String siglaPais, String pais, String uf) {
		this.indicadorSelecao = indicadorSelecao;
		this.nome = nome;
		this.numero = numero;
		this.siglaPais = siglaPais;
		this.pais = pais;
		this.uf = uf;
	}

	public Boolean getIndicadorSelecao() {
		return indicadorSelecao;
	}

	public void setIndicadorSelecao(Boolean indicadorSelecao) {
		this.indicadorSelecao = indicadorSelecao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getSiglaPais() {
		return siglaPais;
	}

	public void setSiglaPais(String siglaPais) {
		this.siglaPais = siglaPais;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indicadorSelecao == null) ? 0 : indicadorSelecao.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result + ((siglaPais == null) ? 0 : siglaPais.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
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
		ParametroEquipeMarketplace other = (ParametroEquipeMarketplace) obj;
		if (indicadorSelecao == null) {
			if (other.indicadorSelecao != null)
				return false;
		} else if (!indicadorSelecao.equals(other.indicadorSelecao))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (pais == null) {
			if (other.pais != null)
				return false;
		} else if (!pais.equals(other.pais))
			return false;
		if (siglaPais == null) {
			if (other.siglaPais != null)
				return false;
		} else if (!siglaPais.equals(other.siglaPais))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RetornoEquipe [indicadorSelecao=" + indicadorSelecao + ", nome=" + nome + ", numero=" + numero + ", siglaPais=" + siglaPais + ", pais=" + pais + ", uf=" + uf + "]";
	}

}
