package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;
import br.gov.caixa.silce.dominio.openjpa.PalpitesValueHandler;

@Entity
@DiscriminatorValue("9")
public class HistoricoApostaMaisMilionaria extends AbstractHistoricoApostaNumerica {

	private static final long serialVersionUID = 1L;

	@Column(name = "DE_PROGNOSTICO_TREVO")
	@Strategy(PalpitesValueHandler.STRATEGY_NAME)
	private PalpitesSequenciais palpitesTrevo = new PalpitesSequenciais();

	public HistoricoApostaMaisMilionaria() {
		super(Modalidade.MAIS_MILIONARIA);
	}

	public PalpitesSequenciais getPalpitesTrevo() {
		return palpitesTrevo;
	}

	public void setPalpitesTrevo(PalpitesSequenciais palpitesTrevo) {
		this.palpitesTrevo = palpitesTrevo;
	}
}