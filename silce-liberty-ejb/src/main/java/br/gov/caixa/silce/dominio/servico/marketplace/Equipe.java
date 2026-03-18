package br.gov.caixa.silce.dominio.servico.marketplace;

import java.io.Serializable;

public class Equipe implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean vitoria = Boolean.FALSE;
	private ParametroEquipeMarketplace parametroEquipe = new ParametroEquipeMarketplace();

	public Equipe() {
	}

	public Equipe(Boolean vitoria) {
		this.vitoria = vitoria;
	}

	public Boolean getVitoria() {
		return vitoria;
	}

	public void setVitoria(Boolean vitoria) {
		this.vitoria = vitoria;
	}

	public ParametroEquipeMarketplace getParametroEquipe() {
		return parametroEquipe;
	}

	public void setParametroEquipe(ParametroEquipeMarketplace parametroEquipe) {
		this.parametroEquipe = parametroEquipe;
	}

	@Override
	public String toString() {
		return "Equipe [vitoria=" + vitoria + ", parametroEquipe=" + parametroEquipe + "]";
	}

}
