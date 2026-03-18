package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import br.gov.caixa.util.Data;

public class TipoComboVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String descricao;
	private Integer ordem;
	private Data dataCriacao;
	private Boolean novo;

	public TipoComboVO() {
		// Metodo feito para o Teste Unitario da Classe, feito no DominioTest
	}

	public TipoComboVO(Long id, String nome, String descricao, Integer ordem, Data dataCriacao, Boolean novo) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.ordem = ordem;
		this.dataCriacao = dataCriacao;
		this.novo = novo;
	}
	
	public static TipoComboVO fromEntidade(TipoCombo tipoCombo) {
		return new TipoComboVO(tipoCombo.getId(), tipoCombo.getNome(), tipoCombo.getDescricao(), tipoCombo.getOrdem(), tipoCombo.getDataCriacao(), tipoCombo.getNovo());
	}
	
	public TipoCombo toEntidade() {
		TipoCombo tipoCombo = new TipoCombo();
		tipoCombo.setDataCriacao(this.getDataCriacao());
		tipoCombo.setDescricao(this.getDescricao());
		tipoCombo.setNome(this.getNome());
		tipoCombo.setNovo(this.getNovo());
		tipoCombo.setOrdem(this.getOrdem());
		tipoCombo.setId(this.getId());
		return tipoCombo;
	}

	public Boolean getNovo() {
		return novo;
	}

	public void setNovo(Boolean novo) {
		this.novo = novo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Data getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Data dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((novo == null) ? 0 : novo.hashCode());
		result = prime * result + ((ordem == null) ? 0 : ordem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TipoComboVO other = (TipoComboVO) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		} else if (!dataCriacao.equals(other.dataCriacao)) {
			return false;
		}
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} else if (!descricao.equals(other.descricao)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		if (novo == null) {
			if (other.novo != null) {
				return false;
			}
		} else if (!novo.equals(other.novo)) {
			return false;
		}
		if (ordem == null) {
			if (other.ordem != null) {
				return false;
			}
		} else if (!ordem.equals(other.ordem)) {
			return false;
		}
		return true;
	}

}
