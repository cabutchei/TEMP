package br.gov.caixa.silce.dominio.entidade;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

public class ApostaTimemaniaVO extends ApostaNumericaVO {

	private static final long serialVersionUID = 1L;
	
	private Integer timeDoCoracao;

	public ApostaTimemaniaVO() {
		super(Modalidade.TIMEMANIA);
	}

	public Integer getTimeDoCoracao() {
		return timeDoCoracao;
	}

	public void setTimeDoCoracao(Integer timeDoCoracao) {
		this.timeDoCoracao = timeDoCoracao;
	}

	@Override
	protected void populeAposta(Aposta<?> aposta) {
		super.populeAposta(aposta);
		ApostaTimemania apostaNumerica = (ApostaTimemania) aposta;
		apostaNumerica.setTimeDoCoracao(timeDoCoracao);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((timeDoCoracao == null) ? 0 : timeDoCoracao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ApostaTimemaniaVO)) {
			return false;
		}
		ApostaTimemaniaVO other = (ApostaTimemaniaVO) obj;
		if (timeDoCoracao == null) {
			if (other.timeDoCoracao != null) {
				return false;
			}
		} else if (!timeDoCoracao.equals(other.timeDoCoracao)) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isPrognosticosEquals(AbstractApostaVO<?> vo) {
		return super.isPrognosticosEquals(vo) && timeDoCoracao.equals(((ApostaTimemaniaVO) vo).getTimeDoCoracao());
	}
	
}
