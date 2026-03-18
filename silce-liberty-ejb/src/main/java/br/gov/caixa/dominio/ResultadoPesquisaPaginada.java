package br.gov.caixa.dominio;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ResultadoPesquisaPaginada<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	private long rowCount;

	private List<T> dados;

	public ResultadoPesquisaPaginada(long rowCount) {
		this.rowCount = rowCount;
		this.dados = Collections.emptyList();
	}

	public ResultadoPesquisaPaginada(long rowCount, List<T> dados) {
		this.rowCount = rowCount;
		this.dados = dados;
	}

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public List<T> getDados() {
		return dados;
	}

	public void setDados(List<T> dados) {
		this.dados = dados;
	}
	
	public T getDadoById(Long id) {
		for (T t : dados) {
			if(t instanceof AbstractEntidade<?> && ((AbstractEntidade<?>) t).getId().equals(id)) {
				return t;
			}
		}
		return null;
	}

}
