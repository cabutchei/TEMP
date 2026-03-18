package br.gov.caixa.util;

import java.io.Serializable;

public abstract class AbstractCacheKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException("hashCode não sobrescrito");
	}
	
	@Override
	public boolean equals(Object obj) {
		throw new UnsupportedOperationException("equals não sobrescrito");
	}
	
	
}
