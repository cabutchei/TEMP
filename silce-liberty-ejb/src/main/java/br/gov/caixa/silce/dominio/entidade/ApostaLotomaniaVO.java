package br.gov.caixa.silce.dominio.entidade;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

public class ApostaLotomaniaVO extends ApostaNumericaVO {

	private static final long serialVersionUID = 1L;
	
	private Boolean espelho = Boolean.FALSE;
	private Boolean geraEspelho;

	public ApostaLotomaniaVO() {
		super(Modalidade.LOTOMANIA);
	}

	public Boolean getEspelho() {
		return espelho;
	}

	public void setEspelho(Boolean espelho) {
		this.espelho = espelho;
	}
	
	public boolean isEspelho() {
		return getEspelho() != null && getEspelho();
	}
	
	@Override
	protected void populeAposta(Aposta<?> aposta) {
		super.populeAposta(aposta);
		ApostaLotomania apostaNumerica = (ApostaLotomania) aposta;
		apostaNumerica.setEspelho(espelho);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((espelho == null) ? 0 : espelho.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ApostaLotomaniaVO)) {
			return false;
		}
		ApostaLotomaniaVO other = (ApostaLotomaniaVO) obj;
		if (espelho == null) {
			if (other.espelho != null) {
				return false;
			}
		} else if (!espelho.equals(other.espelho)) {
			return false;
		}
		return true;
	}

	public void setGeraEspelho(Boolean geraEspelho) {
		this.geraEspelho = geraEspelho;
	}

	@Deprecated
	// Usar isGeraEspelho
	public Boolean getGeraEspelho() {
		return this.geraEspelho;
	}

	public Boolean isGeraEspelho() {
		return Boolean.TRUE.equals(this.geraEspelho);
	}
}
