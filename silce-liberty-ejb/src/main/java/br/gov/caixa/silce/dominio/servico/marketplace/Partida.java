package br.gov.caixa.silce.dominio.servico.marketplace;

import java.io.Serializable;

public class Partida implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer ordemVolante;
	private String dataHoraJogo;
	private ParametroEquipeMarketplace equipe1;
	private ParametroEquipeMarketplace equipe2;

	public Partida() {
	}

	public Partida(Integer ordemVolante, String dataHoraJogo, ParametroEquipeMarketplace equipe1, ParametroEquipeMarketplace equipe2) {
		super();
		this.ordemVolante = ordemVolante;
		this.dataHoraJogo = dataHoraJogo;
		this.equipe1 = equipe1;
		this.equipe2 = equipe2;
	}

	public Integer getOrdemVolante() {
		return ordemVolante;
	}

	public void setOrdemVolante(Integer ordemVolante) {
		this.ordemVolante = ordemVolante;
	}

	public String getDataHoraJogo() {
		return dataHoraJogo;
	}

	public void setDataHoraJogo(String dataHoraJogo) {
		this.dataHoraJogo = dataHoraJogo;
	}

	public ParametroEquipeMarketplace getEquipe1() {
		return equipe1;
	}

	public void setEquipe1(ParametroEquipeMarketplace equipe1) {
		this.equipe1 = equipe1;
	}

	public ParametroEquipeMarketplace getEquipe2() {
		return equipe2;
	}

	public void setEquipe2(ParametroEquipeMarketplace equipe2) {
		this.equipe2 = equipe2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataHoraJogo == null) ? 0 : dataHoraJogo.hashCode());
		result = prime * result + ((equipe1 == null) ? 0 : equipe1.hashCode());
		result = prime * result + ((equipe2 == null) ? 0 : equipe2.hashCode());
		result = prime * result + ((ordemVolante == null) ? 0 : ordemVolante.hashCode());
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
		Partida other = (Partida) obj;
		if (dataHoraJogo == null) {
			if (other.dataHoraJogo != null)
				return false;
		} else if (!dataHoraJogo.equals(other.dataHoraJogo))
			return false;
		if (equipe1 == null) {
			if (other.equipe1 != null)
				return false;
		} else if (!equipe1.equals(other.equipe1))
			return false;
		if (equipe2 == null) {
			if (other.equipe2 != null)
				return false;
		} else if (!equipe2.equals(other.equipe2))
			return false;
		if (ordemVolante == null) {
			if (other.ordemVolante != null)
				return false;
		} else if (!ordemVolante.equals(other.ordemVolante))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Partida [ordemVolante=" + ordemVolante + ", dataHoraJogo=" + dataHoraJogo + ", equipe1=" + equipe1 + ", equipe2=" + equipe2 + "]";
	}

}
