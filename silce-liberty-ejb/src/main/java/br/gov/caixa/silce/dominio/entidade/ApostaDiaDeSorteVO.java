package br.gov.caixa.silce.dominio.entidade;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

public class ApostaDiaDeSorteVO extends ApostaNumericaVO {

	private static final long serialVersionUID = 1L;
	
	private Integer mesDeSorte;

	public ApostaDiaDeSorteVO() {
		super(Modalidade.DIA_DE_SORTE);
	}

	@Override
	protected void populeAposta(Aposta<?> aposta) {
		super.populeAposta(aposta);
		ApostaDiaDeSorte apostaDiaDeSorte = (ApostaDiaDeSorte) aposta;
		apostaDiaDeSorte.setMesDeSorte(mesDeSorte);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mesDeSorte == null) ? 0 : mesDeSorte.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ApostaDiaDeSorteVO)) {
			return false;
		}
		ApostaDiaDeSorteVO other = (ApostaDiaDeSorteVO) obj;
		if (mesDeSorte == null) {
			if (other.mesDeSorte != null) {
				return false;
			}
		} else if (!mesDeSorte.equals(other.mesDeSorte)) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isPrognosticosEquals(AbstractApostaVO<?> vo) {
		return super.isPrognosticosEquals(vo) && mesDeSorte.equals(((ApostaDiaDeSorteVO) vo).getMesDeSorte());
	}

	public Integer getMesDeSorte() {
		return mesDeSorte;
	}

	public void setMesDeSorte(Integer mesDeSorte) {
		this.mesDeSorte = mesDeSorte;
	}

}
