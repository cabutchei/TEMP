package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue("20")
@NamedQueries({
		@NamedQuery(name = ApostaFavoritaTimemania.NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE_TIME,
			query = "Select aposta From ApostaFavoritaTimemania aposta Where aposta.apostador.id=?1 and aposta.palpites = ?2"
				+ " and aposta.modalidade =?3 and aposta.timeDoCoracao = ?4")
	})
public class ApostaFavoritaTimemania extends AbstractApostaFavoritaNumerica {

	public static final String NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE_TIME = "ApostaFavoritaTimamania.NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE_TIME";

	private static final long serialVersionUID = 1L;

	@Column(name = "NU_EQUIPE_ESPORTIVA")
	private Integer timeDoCoracao;

	public Integer getTimeDoCoracao() {
		return timeDoCoracao;
	}

	public void setTimeDoCoracao(Integer timeDoCoracao) {
		this.timeDoCoracao = timeDoCoracao;
	}

	@Override
	public ApostaFavoritaVO getVO() {
		ApostaFavoritaVO vo = super.getVO();
		vo.setTimeDoCoracao(timeDoCoracao);
		return vo;
	}
}
