package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesLotogol;
import br.gov.caixa.silce.dominio.openjpa.PalpitesLotogolValueHandler;

@Entity
@DiscriminatorValue("14")
public class HistoricoApostaLotogol extends HistoricoAposta<PalpitesLotogol> {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "QT_APOSTA")
	private Integer quantidadeApostas = 1;

	//A String é no formato: 001002003004005006... (ou seja, tres digitos para cada prognostico)
	@Column(name = "DE_PROGNOSTICO")
	@Strategy(PalpitesLotogolValueHandler.STRATEGY_NAME)
	private PalpitesLotogol palpites  = new PalpitesLotogol();

	public HistoricoApostaLotogol() {
		super(Modalidade.LOTOGOL);
	}

	public Integer getQuantidadeApostas() {
		return quantidadeApostas;
	}

	public void setQuantidadeApostas(Integer quantidadeApostas) {
		this.quantidadeApostas = quantidadeApostas;
	}

	@Override
	public PalpitesLotogol getPalpites() {
		return palpites;
	}

	public void setPrognosticos(boolean[][] prognosticos) {
		palpites.setPrognosticosConvertidos(prognosticos);
	}
	

	@Override
	public ApostaLotogolVO getVO() {
		ApostaLotogolVO vo = (ApostaLotogolVO) super.getVO();
		vo.setPrognosticos(getPalpites().getPrognosticosConvertidos());
		return vo;
	}

	@Override
	public boolean contemTeimosinha() {
		return false;
	}
}
