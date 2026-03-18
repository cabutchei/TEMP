package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.util.List;

/**
 * Resposta para a Solicitação de parâmetros da Modalidade de Jogo: TIMEMANIA.
 * 
 */
public class ParametroTimemania extends ParametroJogoNumerico {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ParametroEquipe> equipes;

	public List<ParametroEquipe> getEquipes() {
		return equipes;
	}

	public void setEquipes(List<ParametroEquipe> equipes) {
		this.equipes = equipes;
	}

	public ParametroEquipe getByNumero(Integer numero) {
		if (equipes != null) {
			for (ParametroEquipe parametroEquipe : equipes) {
				if(parametroEquipe.getNumero().equals(numero)) {
					return parametroEquipe;
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean isConcursoDisponivelSimulacao() {
		return super.isConcursoDisponivelSimulacao() && equipes != null && !equipes.isEmpty();
	}
	
	public boolean isTimeValido(Integer numero) {
		return getByNumero(numero) != null;
	}

}
