package br.gov.caixa.silce.dominio.entidade;

import java.util.List;

import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;

public class ApostaMaisMilionariaVO extends ApostaNumericaVO {

	private static final long serialVersionUID = 1L;

	private final PalpitesSequenciais palpitesTrevo = new PalpitesSequenciais();

	public ApostaMaisMilionariaVO() {
		super(Modalidade.MAIS_MILIONARIA);
	}

	@Override
	protected void populeAposta(Aposta<?> aposta) {
		super.populeAposta(aposta);
		ApostaMaisMilionaria apostaMaisMilionaria = (ApostaMaisMilionaria) aposta;
		apostaMaisMilionaria.setPalpitesTrevo(palpitesTrevo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((palpitesTrevo == null) ? 0 : palpitesTrevo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ApostaMaisMilionariaVO)) {
			return false;
		}
		ApostaMaisMilionariaVO other = (ApostaMaisMilionariaVO) obj;
		if (palpitesTrevo == null) {
			if (other.palpitesTrevo != null) {
				return false;
			}
		} else if (!palpitesTrevo.equals(other.palpitesTrevo)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isPrognosticosEquals(AbstractApostaVO<?> vo) {
		return super.isPrognosticosEquals(vo) && palpitesTrevo.equals(((ApostaMaisMilionariaVO) vo).getPalpitesTrevo());
	}

	public void setPrognosticosTrevo(List<Integer> prognosticos) {
		// prognosticosParaTela = null;
		palpitesTrevo.setPrognosticos(prognosticos);
	}

	public PalpitesSequenciais getPalpitesTrevo() {
		return palpitesTrevo;
	}
}
