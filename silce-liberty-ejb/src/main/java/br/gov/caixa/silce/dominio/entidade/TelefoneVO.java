package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import br.gov.caixa.util.StringUtil;
import br.gov.caixa.util.Telefone;

public class TelefoneVO implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int QNT_DIGITOS_DDD = 3;

	private Integer ddd;

	private Integer numero;

	public TelefoneVO(Telefone telefone) {
		this.ddd = telefone.getDdd();
		this.numero = telefone.getNumero();
	}

	public Telefone toTelefone() {
		return new Telefone(ddd, numero);
	}



	public Integer getDdd() {
		return ddd;
	}

	public Integer getNumero() {
		return numero;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (ddd != null) {
			builder.append(StringUtil.completeAEsquerda(ddd.toString(), QNT_DIGITOS_DDD, '0'));
		}
		if (numero != null) {
			builder.append(numero);
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ddd == null) ? 0 : ddd.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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

		TelefoneVO other = (TelefoneVO) obj;
		if (ddd == null) {
			if (other.ddd != null) {
				return false;
			}
		} else if (!ddd.equals(other.ddd)) {
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}

		return true;
	}


}
