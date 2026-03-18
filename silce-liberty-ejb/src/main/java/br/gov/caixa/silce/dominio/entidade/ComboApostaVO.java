package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

public class ComboApostaVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private TipoComboVO tipoCombo;
	private String dataInclusao;

	public ComboApostaVO() {
		// Metodo feito para o Teste Unitario da Classe, feito no DominioTest
	}

	public static ComboApostaVO fromEntidade(ComboAposta comboAposta) {
		TipoComboVO tipoComboVO = TipoComboVO.fromEntidade(comboAposta.getTipoCombo());
		return new ComboApostaVO(comboAposta.getId(), tipoComboVO, comboAposta.getDataInclusao());
	}


	public ComboAposta createComboAposta() {
		ComboAposta comboAposta = new ComboAposta(this.tipoCombo.toEntidade());
		comboAposta.setId(this.id);
		comboAposta.setTipoCombo(this.tipoCombo.toEntidade());
		return comboAposta;
	}

	public ComboApostaVO(Long id, TipoComboVO tipoCombo, String dataInclusao) {
		this.id = id;
		this.tipoCombo = tipoCombo;
		this.dataInclusao = dataInclusao;
	}

	public Long getId() {
		return id;
	}

	public TipoComboVO getTipoCombo() {
		return tipoCombo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTipoCombo(TipoComboVO tipoCombo) {
		this.tipoCombo = tipoCombo;
	}

	public String getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(String dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipoCombo == null) ? 0 : tipoCombo.hashCode());
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
		ComboApostaVO other = (ComboApostaVO) obj;
		if (dataInclusao == null) {
			if (other.dataInclusao != null) {
				return false;
			}
		} else if (!dataInclusao.equals(other.dataInclusao)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (tipoCombo == null) {
			if (other.tipoCombo != null) {
				return false;
			}
		} else if (!tipoCombo.equals(other.tipoCombo)) {
			return false;
		}
		return true;
	}
}
