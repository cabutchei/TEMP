package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

@Entity
@DiscriminatorValue("20")
public class HistoricoApostaTimemania extends AbstractHistoricoApostaNumerica {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "NU_EQUIPE_ESPORTIVA")
	private Integer timeDoCoracao;

	public HistoricoApostaTimemania() {
		super(Modalidade.TIMEMANIA);
	}

	public Integer getTimeDoCoracao() {
		return timeDoCoracao;
	}

	public void setTimeDoCoracao(Integer timeDoCoracao) {
		this.timeDoCoracao = timeDoCoracao;
	}
	
	@Override
	public ApostaTimemaniaVO getVO() {
		ApostaTimemaniaVO vo = (ApostaTimemaniaVO) super.getVO();
		vo.setTimeDoCoracao(timeDoCoracao);
		return vo;
	}


}
