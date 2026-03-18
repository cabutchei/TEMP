package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroJogoNumerico;
import br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler;

@Entity
@NamedQueries({
		@NamedQuery(name = ApostaCarrinhoFavoritoLotomania.NQ_SELECT_ESPELHO_BY_ORIGINAL, query = "Select entidade From ApostaCarrinhoFavoritoLotomania entidade where entidade.apostaVinculoEspelho.id = ?1")
})
@DiscriminatorValue("16")
public class ApostaCarrinhoFavoritoLotomania extends AbstractApostaCarrinhoFavoritoNumerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String NQ_SELECT_ESPELHO_BY_ORIGINAL = "ApostaCarrinhoFavoritoLotomania.NQ_SELECT_ESPELHO_BY_ORIGINAL";

	@Column(name = "IC_APOSTA_ESPELHO")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean espelho = Boolean.FALSE;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_VINCULO_APOSTA_ESPELHO", referencedColumnName = "NU_APSTA_CARRINHO_FVRTO")
	private AbstractApostaCarrinhoFavorito apostaVinculoEspelho;

	@Transient
	private boolean gereEspelho;

	public Boolean getEspelho() {
		return espelho;
	}

	public void setEspelho(Boolean espelho) {
		this.espelho = espelho;
	}

	public AbstractApostaCarrinhoFavorito getApostaVinculoEspelho() {
		return apostaVinculoEspelho;
	}

	public void setApostaVinculoEspelho(AbstractApostaCarrinhoFavorito apostaVinculoEspelho) {
		this.apostaVinculoEspelho = apostaVinculoEspelho;
	}

	public boolean isGereEspelho() {
		return gereEspelho;
	}

	public void setGereEspelho(boolean gereEspelho) {
		this.gereEspelho = gereEspelho;
	}

	@Override
	public AbstractApostaVO<?> toAbstractApostaVO(ParametroJogoNumerico parametro) {
		ApostaLotomaniaVO voLotomania = (ApostaLotomaniaVO) super.toAbstractApostaVO(parametro);
		voLotomania.setEspelho(getEspelho());
		voLotomania.setGeraEspelho(isGereEspelho());
		return voLotomania;
	}

}
