package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;
import java.util.List;

import br.gov.caixa.silce.dominio.jogos.Combo;
import br.gov.caixa.silce.dominio.jogos.ModalidadeCombo;
import br.gov.caixa.util.Decimal;

public class CombosVO implements Serializable, Comparable<CombosVO> {

	private static final long serialVersionUID = 1L;

	private final TipoCombo tipoCombo;
	private final List<ModalidadeCombo> modalidadesCombo;
	private final Decimal valor;

	public static CombosVO fromEntidade(Combo combo){
		return new CombosVO(combo.getTipoCombo(), combo.getModalidades(), combo.getValor());
	}

	public CombosVO(TipoCombo tipoCombo, List<ModalidadeCombo> modalidadesCombo, Decimal valor) {
		this.tipoCombo = tipoCombo;
		this.modalidadesCombo = modalidadesCombo;
		this.valor = valor;
	}

	public List<ModalidadeCombo> getModalidadesCombo() {
		return modalidadesCombo;
	}

	public TipoCombo getTipoCombo() {
		return tipoCombo;
	}

	public Decimal getValor() {
		return valor;
	}

	@Override
	public int compareTo(CombosVO o) {
		return tipoCombo.getOrdem().compareTo(o.getTipoCombo().getOrdem());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modalidadesCombo == null) ? 0 : modalidadesCombo.hashCode());
		result = prime * result + ((tipoCombo == null) ? 0 : tipoCombo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		CombosVO other = (CombosVO) obj;
		if (modalidadesCombo == null) {
			if (other.modalidadesCombo != null) {
				return false;
			}
		} else if (!modalidadesCombo.equals(other.modalidadesCombo)) {
			return false;
		}
		if (tipoCombo != other.tipoCombo) {
			return false;
		}
		if (valor == null) {
			if (other.valor != null) {
				return false;
			}
		} else if (!valor.equals(other.valor)) {
			return false;
		}
		return true;
	}
}
