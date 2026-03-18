package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroJogoNumerico;
import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;
import br.gov.caixa.silce.dominio.openjpa.PalpitesValueHandler;

@Entity
@DiscriminatorValue("9")
public class ApostaCarrinhoFavoritoMaisMilionaria extends AbstractApostaCarrinhoFavoritoNumerico {

	private static final long serialVersionUID = 1L;

	@Column(name = "DE_PROGNOSTICO_TREVO")
	@Strategy(PalpitesValueHandler.STRATEGY_NAME)
	private PalpitesSequenciais trevos;

	public PalpitesSequenciais getTrevos() {
		return trevos;
	}

	public void setTrevos(PalpitesSequenciais trevos) {
		this.trevos = trevos;
	}

	public AbstractApostaVO<?> toAbstractApostaVO(ParametroJogoNumerico parametro) {
		ApostaMaisMilionariaVO voMaisMilionaria = (ApostaMaisMilionariaVO) super.toAbstractApostaVO(parametro);
		voMaisMilionaria.setPrognosticosTrevo(getTrevos().getPrognosticos());
		return voMaisMilionaria;
	}
}
