package br.gov.caixa.silce.dominio.broker.parametrosjogos;

public class ParametroJogoInstantanea extends AbstractParametroJogo {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isConcursoDisponivelSimulacao() {
		return true;
	}

	@Override
	public boolean isDisponivelEfetivacao() {
		return true;
	}

}
