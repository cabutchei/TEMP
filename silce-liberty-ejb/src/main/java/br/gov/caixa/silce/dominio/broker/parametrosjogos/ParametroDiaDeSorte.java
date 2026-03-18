package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.util.List;

/**
 * Resposta para a Solicitação de parâmetros da Modalidade de Jogo: DIA DE SORTE.
 * 
 */
public class ParametroDiaDeSorte extends ParametroJogoNumerico {
	
	private static final long serialVersionUID = 1L;

	private List<ParametroMesDeSorte> meses;

	public ParametroMesDeSorte getByNumero(Integer numero) {
		if (getMeses() != null) {
			for (ParametroMesDeSorte parametroMesDeSorte : getMeses()) {
				if (parametroMesDeSorte.getNumero().equals(numero)) {
					return parametroMesDeSorte;
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean isConcursoDisponivelSimulacao() {
		return super.isConcursoDisponivelSimulacao() && meses != null && !meses.isEmpty();
	}
	
	public boolean isMesValido(Integer numero) {
		return getByNumero(numero) != null;
	}

	public List<ParametroMesDeSorte> getMeses() {
		return meses;
	}

	public void setMeses(List<ParametroMesDeSorte> meses) {
		this.meses = meses;
	}

}
