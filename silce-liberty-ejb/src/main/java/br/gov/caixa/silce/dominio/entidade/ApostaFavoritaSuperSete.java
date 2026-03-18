package br.gov.caixa.silce.dominio.entidade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.silce.dominio.interfaces.Favorita;
import br.gov.caixa.silce.dominio.jogos.PalpitesSuperSete;
import br.gov.caixa.silce.dominio.openjpa.PalpitesSuperSeteValueHandler;
import br.gov.caixa.silce.dominio.util.ApostaUtil;

@Entity
@DiscriminatorValue("7")
@NamedQueries({
		@NamedQuery(name = ApostaFavoritaSuperSete.NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE,
			query = "Select aposta From ApostaFavoritaSuperSete aposta Where aposta.apostador.id=?1 and aposta.palpites = ?2"
		+ " and aposta.modalidade =?3")
})
public class ApostaFavoritaSuperSete extends AbstractApostaFavorita implements Favorita {

	public static final String NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE = "ApostaFavoritaSuperSete.NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "DE_PROGNOSTICO")
	@Strategy(PalpitesSuperSeteValueHandler.STRATEGY_NAME)
	private PalpitesSuperSete palpites = new PalpitesSuperSete();

	@Transient
	private transient List<String> prognosticosParaTela = null;

	public PalpitesSuperSete getPalpites() {
		return palpites;
	}

	public void setPalpites(PalpitesSuperSete palpites) {
		this.palpites = palpites;
	}

	@Override
	public List<List<Integer>> getPrognosticos() {
		return palpites.getPrognosticos();
	}

	// CODIGO COPIADO E ADAPTADO DE AbstractApostaNumerica
	public List<String> getPrognosticosParaTela() {
		if (prognosticosParaTela == null) {
			prognosticosParaTela = new ArrayList<String>();

			for (List<Integer> prognostico : getPrognosticos()) {
				for (Integer integer : prognostico) {
					prognosticosParaTela.add(ApostaUtil.formataPrognosticoTela(integer));
				}
			}

		}
		return prognosticosParaTela;
	}

	public ApostaFavoritaVO getVO() {
		ApostaFavoritaVO vo = super.getVO();
		vo.setPalpites(getPalpites());
		return vo;
	}

}
