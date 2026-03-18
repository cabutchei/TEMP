package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler;

@Entity
@DiscriminatorValue("16")
public class HistoricoApostaLotomania extends AbstractHistoricoApostaNumerica {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "IC_ESPELHO")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean espelho = Boolean.FALSE;

	public HistoricoApostaLotomania() {
		super(Modalidade.LOTOMANIA);
	}

	public Boolean getEspelho() {
		return isEspelho();
	}

	public Boolean isEspelho() {
		return espelho;
	}

	public void setEspelho(Boolean espelho) {
		this.espelho = espelho;
	}
	
	@Override
	public ApostaLotomaniaVO getVO() {
		ApostaLotomaniaVO vo = (ApostaLotomaniaVO) super.getVO();
		vo.setEspelho(espelho);
		return vo;
	}

}
