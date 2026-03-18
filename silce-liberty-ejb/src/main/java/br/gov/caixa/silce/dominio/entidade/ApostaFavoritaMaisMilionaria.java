package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;
import br.gov.caixa.silce.dominio.openjpa.PalpitesValueHandler;

@Entity
@DiscriminatorValue("9")
@NamedQueries({
	@NamedQuery(name = ApostaFavoritaMaisMilionaria.NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE_TREVO,
		query = "Select aposta From ApostaFavoritaMaisMilionaria aposta Where aposta.apostador.id=?1 and aposta.palpites = ?2"
				+ " and aposta.modalidade =?3 and aposta.trevos = ?4")
})
public class ApostaFavoritaMaisMilionaria extends AbstractApostaFavoritaNumerica {

	public static final String NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE_TREVO = "ApostaFavoritaMaisMilionaria.NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE_TREVO";

	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	@Column(name = "DE_PROGNOSTICO_TREVO")
	@Strategy(PalpitesValueHandler.STRATEGY_NAME)
	private PalpitesSequenciais trevos = new PalpitesSequenciais();

	public ApostaFavoritaVO getVO() {
		ApostaFavoritaVO vo = super.getVO();
		vo.setPalpites(getPalpites());
		vo.setTrevos(getTrevos());
		return vo;
	}

	public PalpitesSequenciais getTrevos() {
		return trevos;
	}

	public void setTrevos(PalpitesSequenciais trevos) {
		this.trevos = trevos;
	}

}