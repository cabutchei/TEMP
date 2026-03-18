package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesLoteca;
import br.gov.caixa.silce.dominio.openjpa.PalpitesLotecaValueHandler;

@Entity
@DiscriminatorValue("19")
public class HistoricoApostaLoteca extends HistoricoAposta<PalpitesLoteca> {

	private static final long serialVersionUID = 1L;
	
	//A String é no formato: 001002003004005006... (ou seja, tres digitos para cada prognostico)
	@Column(name = "DE_PROGNOSTICO")
	@Strategy(PalpitesLotecaValueHandler.STRATEGY_NAME)
	private PalpitesLoteca palpites  = new PalpitesLoteca();

	public HistoricoApostaLoteca() {
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
	public ApostaLotecaVO getVO() {
		ApostaLotecaVO vo = (ApostaLotecaVO) super.getVO();
		vo.setPrognosticos(getPalpites().getPrognosticosConvertidos());
		return vo;
	}

	@Override
	public boolean contemTeimosinha() {
		return false;
	}

}
