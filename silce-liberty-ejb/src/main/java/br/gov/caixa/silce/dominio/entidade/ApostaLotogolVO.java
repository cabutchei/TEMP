package br.gov.caixa.silce.dominio.entidade;

import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesLotogol;

public class ApostaLotogolVO extends AbstractApostaVO<PalpitesLotogol> {
	
	private static final long serialVersionUID = 1L;
	
	private Integer quantidadeApostas = 1;

	//A String é no formato: 001002003004005006... (ou seja, tres digitos para cada prognostico)
	private final PalpitesLotogol palpites  = new PalpitesLotogol();

	public ApostaLotogolVO() {
		super(Modalidade.LOTOGOL);
	}

	public Integer getQuantidadeApostas() {
		return quantidadeApostas;
	}

	public void setQuantidadeApostas(Integer quantidadeApostas) {
		this.quantidadeApostas = quantidadeApostas;
	}

	@Override
	public PalpitesLotogol getPalpites() {
		return palpites;
	}

	public void setPrognosticos(boolean[][] prognosticos) {
		palpites.setPrognosticosConvertidos(prognosticos);
	}
	
	@Override
	public void populeAposta(Aposta<?> aposta) {
		ApostaLotogol apostaLotogol = (ApostaLotogol) aposta;
		apostaLotogol.setPrognosticos(getPalpites().getPrognosticosConvertidos());
		apostaLotogol.setQuantidadeApostas(getQuantidadeApostas());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + palpites.hashCode();
		result = prime * result + ((quantidadeApostas == null) ? 0 : quantidadeApostas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ApostaLotogolVO)) {
			return false;
		}
		ApostaLotogolVO other = (ApostaLotogolVO) obj;
		if (!palpites.equals(other.palpites)) {
			return false;
		}
		if (quantidadeApostas == null) {
			if (other.quantidadeApostas != null) {
				return false;
			}
		} else if (!quantidadeApostas.equals(other.quantidadeApostas)) {
			return false;
		}
		return true;
	}
}
