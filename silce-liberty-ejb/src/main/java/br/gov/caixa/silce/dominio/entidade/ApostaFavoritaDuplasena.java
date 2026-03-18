package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue("18")
@NamedQueries({
		@NamedQuery(name = ApostaFavoritaDuplasena.NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE, query = "Select aposta From ApostaFavoritaDuplasena aposta Where aposta.apostador.id=?1 and aposta.palpites = ?2"
			+ " and aposta.modalidade =?3")
})
public class ApostaFavoritaDuplasena extends AbstractApostaFavoritaNumerica {

	public static final String NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE = "ApostaFavoritaDuplasena.NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
