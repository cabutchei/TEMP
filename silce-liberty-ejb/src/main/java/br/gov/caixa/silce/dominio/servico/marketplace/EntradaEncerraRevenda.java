package br.gov.caixa.silce.dominio.servico.marketplace;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.TipoExecucao;
import br.gov.caixa.silce.dominio.jogos.Modalidade;

public class EntradaEncerraRevenda extends SaidaHttpMarketplace implements EntradaHttp<RetornoEncerraRevenda> {

	private static final long serialVersionUID = 1L;

	private TipoExecucao tipoExecucao;

	public EntradaEncerraRevenda(Integer concurso, Modalidade modalidade, TipoExecucao tipoExecucao) {
		this.concurso = concurso;
		this.modalidade = modalidade;
		this.tipoExecucao = tipoExecucao;
	}

	public TipoExecucao getTipoExecucao() {
		return tipoExecucao;
	}

	public void setTipoExecucao(TipoExecucao tipoExecucao) {
		this.tipoExecucao = tipoExecucao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concurso == null) ? 0 : concurso.hashCode());
		result = prime * result + ((modalidade == null) ? 0 : modalidade.hashCode());
		result = prime * result + ((tipoExecucao == null) ? 0 : tipoExecucao.hashCode());
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
		if (tipoExecucao == null) {
			if (other.tipoExecucao != null)
				return false;
		} else if (!tipoExecucao.equals(other.tipoExecucao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntradaEncerraRevenda [modalidade=" + modalidade + ", concurso=" + concurso + ", tipoExecucao=" + tipoExecucao + "]";
	}

}
