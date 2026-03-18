package br.gov.caixa.dominio;

import java.io.Serializable;

import br.gov.caixa.util.Data;

/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 *
 * @param <E>
 */
public abstract class AbstractCaixaMovimento<E extends Enum<?>> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String usuario;
	
	private Data data;
	
	private Long nsu;
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Long getNsu() {
		return nsu;
	}

	public void setNsu(Long nsu) {
		this.nsu = nsu;
	}

	public abstract E getTipo();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((nsu == null) ? 0 : nsu.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		if (!(obj instanceof AbstractCaixaMovimento)) {
			return false;
		}
		return compareValoresAtributos((AbstractCaixaMovimento<?>) obj);
	}

	public boolean compareValoresAtributos(AbstractCaixaMovimento<?> other) {
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		if (nsu == null) {
			if (other.nsu != null) {
				return false;
			}
		} else if (!nsu.equals(other.nsu)) {
			return false;
		}
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		} else if (!usuario.equals(other.usuario)) {
			return false;
		}
		return true;
	}
	
}
