package br.gov.caixa.silce.dominio.jogos;

import java.util.List;

public abstract class AbstractPalpitesEsportivos implements Palpites<boolean[]> {
	private static final long serialVersionUID = 1L;

	protected static final char CARACT_COMP_PALP_SELECIONADOS = '0';

	protected static final int QTD_MAX_DECIMAIS_PALP_SELECIONADOS = 2;

	private List<boolean[]> prognosticos;

	@Override
	public void addPrognostico(Integer partida, boolean[] prognostico) {
		prognosticos.set(partida, prognostico);
	}

	@Override
	public List<boolean[]> getPrognosticos() {
		return prognosticos;
	}

	@Override
	public void setPrognosticos(List<boolean[]> prognosticos) {
		this.prognosticos = prognosticos;
	}


}
