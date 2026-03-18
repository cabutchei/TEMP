package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.util.Decimal;

public class SubcanalValor implements Serializable {

	private static final long serialVersionUID = 1L;
	private Subcanal subcanal;
	private Decimal valorSubcanal;

	public SubcanalValor(Subcanal subcanal, Decimal valorSubcanal) {
		this.subcanal = subcanal;
		this.valorSubcanal = valorSubcanal != null ? valorSubcanal : Decimal.ZERO;
	}

	public static SubcanalValor of(Subcanal subcanal, Decimal valorSubcanal) {
		return new SubcanalValor(subcanal, valorSubcanal);
	}

	public static List<SubcanalValor> getListaCompletaZerada() {
		List<SubcanalValor> valoresZerados = new ArrayList<SubcanalValor>();
		for (Subcanal subcanal : Subcanal.getAllSubcanais()) {
			valoresZerados.add(SubcanalValor.of(subcanal, Decimal.ZERO));
		}
		valoresZerados.add(SubcanalValor.of(null, Decimal.ZERO));
		return valoresZerados;
	}

	public Subcanal getSubcanal() {
		return subcanal;
	}

	public void setSubcanal(Subcanal subcanal) {
		this.subcanal = subcanal;
	}

	public Decimal getValorSubcanal() {
		return valorSubcanal;
	}

	public void setValorSubcanal(Decimal valorSubcanal) {
		this.valorSubcanal = valorSubcanal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subcanal == null) ? 0 : subcanal.hashCode());
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
		SubcanalValor other = (SubcanalValor) obj;
		if (subcanal != other.subcanal) {
			return false;
		}
		return true;
	}
}