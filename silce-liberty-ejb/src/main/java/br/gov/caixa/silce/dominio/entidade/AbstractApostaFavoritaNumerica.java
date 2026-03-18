package br.gov.caixa.silce.dominio.entidade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.silce.dominio.interfaces.Favorita;
import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;
import br.gov.caixa.silce.dominio.openjpa.PalpitesValueHandler;
import br.gov.caixa.silce.dominio.util.ApostaUtil;

@Entity
public abstract class AbstractApostaFavoritaNumerica extends AbstractApostaFavorita implements Favorita {

	private static final long serialVersionUID = 1L;

	@Column(name = "DE_PROGNOSTICO")
	@Strategy(PalpitesValueHandler.STRATEGY_NAME)
	private PalpitesSequenciais palpites = new PalpitesSequenciais();

	@Transient
	private transient List<String> prognosticosParaTela = null;

	public PalpitesSequenciais getPalpites() {
		return palpites;
	}

	public void setPalpites(PalpitesSequenciais palpites) {
		this.palpites = palpites;
	}

	@Override
	public List<Integer> getPrognosticos() {
		return palpites.getPrognosticos();
	}

	// CODIGO DUPLICADO EM AbstractApostaNumerica
	public List<String> getPrognosticosParaTela() {
		if (prognosticosParaTela == null) {
			prognosticosParaTela = new ArrayList<String>();

			for (Integer prognostico : getPrognosticos()) {
				prognosticosParaTela.add(ApostaUtil.formataPrognosticoTela(prognostico));
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
