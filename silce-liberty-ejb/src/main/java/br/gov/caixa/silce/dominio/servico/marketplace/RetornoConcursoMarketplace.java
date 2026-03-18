package br.gov.caixa.silce.dominio.servico.marketplace;

import java.util.ArrayList;
import java.util.List;

public class RetornoConcursoMarketplace extends AbstractRetornoErro {

	private static final long serialVersionUID = 1L;

	private List<ConcursoMarketplace> concursos;

	public RetornoConcursoMarketplace() {
		concursos = new ArrayList<ConcursoMarketplace>();
	}

	public RetornoConcursoMarketplace(List<ConcursoMarketplace> concursos) {
		this.concursos = concursos;
	}

	public RetornoConcursoMarketplace(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public List<ConcursoMarketplace> getConcursos() {
		return concursos;
	}

	public void setConcursos(List<ConcursoMarketplace> concursos) {
		this.concursos = concursos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concursos == null) ? 0 : concursos.hashCode());
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
		RetornoConcursoMarketplace other = (RetornoConcursoMarketplace) obj;
		if (concursos == null) {
			if (other.concursos != null)
				return false;
		} else if (!concursos.equals(other.concursos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RetornoConcursoMarketplace [concursos=" + concursos + "]";
	}

}
