package br.gov.caixa.silce.dominio.servico.marketplace;

import java.util.List;

public class RetornoEncerraRevenda extends AbstractRetornoErro {

	private static final long serialVersionUID = 1L;

	private List<Cota> cotas;

	public RetornoEncerraRevenda() {
	}

	public RetornoEncerraRevenda(List<Cota> cotas) {
		this.cotas = cotas;
	}

	public RetornoEncerraRevenda(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public List<Cota> getCotasEncerramento() {
		return cotas;
	}

	public void setCotasEncerramento(List<Cota> cotas) {
		this.cotas = cotas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cotas == null) ? 0 : cotas.hashCode());
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
		RetornoEncerraRevenda other = (RetornoEncerraRevenda) obj;
		if (cotas == null) {
			if (other.cotas != null)
				return false;
		} else if (!cotas.equals(other.cotas))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RetornoEncerraRevenda [cotas=" + cotas + "]";
	}

}
