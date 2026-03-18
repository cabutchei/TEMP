package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroJogoNumerico;

@Entity
@DiscriminatorValue("20")
public class ApostaCarrinhoFavoritoTimemania extends AbstractApostaCarrinhoFavoritoNumerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NU_EQUIPE_ESPORTIVA")
	private Integer timeDoCoracao;


	public Integer getTimeDoCoracao() {
		return timeDoCoracao;
	}

	public void setTimeDoCoracao(Integer timeDoCoracao) {
		this.timeDoCoracao = timeDoCoracao;
	}

	@Override
	public AbstractApostaVO<?> toAbstractApostaVO(ParametroJogoNumerico parametro) {
		ApostaTimemaniaVO voTimemania = (ApostaTimemaniaVO) super.toAbstractApostaVO(parametro);
		voTimemania.setTimeDoCoracao(getTimeDoCoracao());
		return voTimemania;
	}
}
