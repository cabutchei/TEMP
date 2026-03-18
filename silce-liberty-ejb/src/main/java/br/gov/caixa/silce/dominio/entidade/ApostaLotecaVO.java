package br.gov.caixa.silce.dominio.entidade;

import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesLoteca;

public class ApostaLotecaVO extends AbstractApostaVO<PalpitesLoteca> {

	private static final long serialVersionUID = 1L;
	
	//A String é no formato: 001002003004005006... (ou seja, tres digitos para cada prognostico)
	private final PalpitesLoteca palpites  = new PalpitesLoteca();

	public ApostaLotecaVO() {
		super(Modalidade.LOTECA);
	}

	@Override
	public PalpitesLoteca getPalpites() {
		return palpites;
	}
	
	public void setPrognosticos(boolean[][] prognosticos) {
		palpites.setPrognosticosConvertidos(prognosticos);
	}

	@Override
	public void populeAposta(Aposta<?> aposta) {
		((ApostaLoteca) aposta).setPrognosticos(getPalpites().getPrognosticosConvertidos());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + palpites.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ApostaLotecaVO)) {
			return false;
		}
		ApostaLotecaVO other = (ApostaLotecaVO) obj;
		if (!palpites.equals(other.palpites)) {
			return false;
		}
		return true;
	}

}
