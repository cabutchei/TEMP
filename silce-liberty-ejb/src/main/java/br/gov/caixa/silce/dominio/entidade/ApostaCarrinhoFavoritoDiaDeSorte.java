package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroJogoNumerico;

@Entity
@DiscriminatorValue("11")
public class ApostaCarrinhoFavoritoDiaDeSorte extends AbstractApostaCarrinhoFavoritoNumerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "MM_SORTE")
	private Integer mesDeSorte;

	public Integer getMesDeSorte() {
		return mesDeSorte;
	}

	public void setMesDeSorte(Integer mesDeSorte) {
		this.mesDeSorte = mesDeSorte;
	}

	@Override
	public AbstractApostaVO<?> toAbstractApostaVO(ParametroJogoNumerico parametro) {
		ApostaDiaDeSorteVO voDiaDeSorte = (ApostaDiaDeSorteVO) super.toAbstractApostaVO(parametro);
		voDiaDeSorte.setMesDeSorte(getMesDeSorte());
		return voDiaDeSorte;
	}

}
