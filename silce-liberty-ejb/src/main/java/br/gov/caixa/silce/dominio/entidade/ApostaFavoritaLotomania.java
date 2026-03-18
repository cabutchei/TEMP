package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue("16")
@NamedQueries({
		@NamedQuery(name = ApostaFavoritaLotomania.NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE, query = "Select aposta From ApostaFavoritaLotomania aposta Where aposta.apostador.id=?1 and aposta.palpites = ?2"
			+ " and aposta.modalidade =?3")
})
public class ApostaFavoritaLotomania extends AbstractApostaFavoritaNumerica {

	public static final String NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE = "ApostaFavoritaLotomania.NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
