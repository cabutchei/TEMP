package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MunicipioPK implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final int TOSTRING_BUILDER_SIZE = 52;

	@Column(name="NU_UF_IBGE_L99", insertable = false, updatable = false)
	private Long codigoUF;
	
	@Column(name = "NU_MUNICIPIO_IBGE", insertable = false, updatable = false)
	private Long numero;
	
	@Column(name = "NU_DV_IBGE", insertable = false, updatable = false)
	private Long digitoVerificador;

	public MunicipioPK() {
		// necessário
	}
	
	public MunicipioPK(Long codigoUF, Long numero, Long digitoVerificador) {
		super();
		this.codigoUF = codigoUF;
		this.numero = numero;
		this.digitoVerificador = digitoVerificador;
	}

	public Long getCodigoUF() {
		return codigoUF;
	}

	public void setCodigoUF(Long codigoUF) {
		this.codigoUF = codigoUF;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Long getDigitoVerificador() {
		return digitoVerificador;
	}

	public void setDigitoVerificador(Long digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
			+ ((codigoUF == null) ? 0 : codigoUF.hashCode());
		result = prime * result
			+ ((digitoVerificador == null) ? 0 : digitoVerificador.hashCode());
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
		if (!(obj instanceof MunicipioPK)) {
			return false;
		}
		MunicipioPK other = (MunicipioPK) obj;
		if (codigoUF == null) {
			if (other.codigoUF != null) {
				return false;
			}
		} else if (!codigoUF.equals(other.codigoUF)) {
			return false;
		}
		if (digitoVerificador == null) {
			if (other.digitoVerificador != null) {
				return false;
			}
		} else if (!digitoVerificador.equals(other.digitoVerificador)) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(TOSTRING_BUILDER_SIZE);
		builder.append("MunicipioPK [codigoUF=");
		builder.append(codigoUF);
		builder.append(", numero=");
		builder.append(numero);
		builder.append(", digitoVerificador=");
		builder.append(digitoVerificador);
		builder.append(']');
		return builder.toString();
	}


}
