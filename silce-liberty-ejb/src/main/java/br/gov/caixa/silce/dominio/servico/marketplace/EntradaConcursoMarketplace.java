package br.gov.caixa.silce.dominio.servico.marketplace;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.jogos.Modalidade;

public class EntradaConcursoMarketplace extends SaidaHttpMarketplace implements EntradaHttp<RetornoConcursoMarketplace> {

	private static final long serialVersionUID = 1L;

	public EntradaConcursoMarketplace() {

	}

	public EntradaConcursoMarketplace(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public EntradaConcursoMarketplace(Integer concurso, Modalidade modalidade) {
		this.concurso = concurso;
		this.modalidade = modalidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concurso == null) ? 0 : concurso.hashCode());
		result = prime * result + ((modalidade == null) ? 0 : modalidade.hashCode());
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
		EntradaEncerraRevenda other = (EntradaEncerraRevenda) obj;
		if (concurso == null) {
			if (other.concurso != null)
				return false;
		} else if (!concurso.equals(other.concurso))
			return false;
		if (modalidade == null) {
			if (other.modalidade != null)
				return false;
		} else if (!modalidade.equals(other.modalidade))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntradaConcursoMarketplace [modalidade=" + modalidade + ", concurso=" + concurso + "]";
	}
}
