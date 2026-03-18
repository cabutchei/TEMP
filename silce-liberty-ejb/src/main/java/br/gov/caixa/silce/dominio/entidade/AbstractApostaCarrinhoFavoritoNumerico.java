package br.gov.caixa.silce.dominio.entidade;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroJogoNumerico;
import br.gov.caixa.silce.dominio.interfaces.Favorita;
import br.gov.caixa.silce.dominio.interfaces.Teimosinha;
import br.gov.caixa.silce.dominio.jogos.Palpites;
import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;
import br.gov.caixa.silce.dominio.openjpa.PalpitesValueHandler;
import br.gov.caixa.silce.dominio.util.ApostaUtil;
import br.gov.caixa.util.Decimal;

@Entity
public abstract class AbstractApostaCarrinhoFavoritoNumerico extends AbstractApostaCarrinhoFavorito implements Teimosinha, Favorita {

	private static final long serialVersionUID = 1L;

	@Column(name = "DE_PROGNOSTICO")
	@Strategy(PalpitesValueHandler.STRATEGY_NAME)
	private Palpites<?> palpites;

	@Column(name = "QT_TEIMOSINHA")
	private Integer quantidadeTeimosinhas;

	public Palpites<?> getPalpites() {
		return palpites;
	}

	public void setPalpites(Palpites<?> palpites) {
		this.palpites = palpites;
	}

	@Override
	public List<?> getPrognosticos() {
		return palpites.getPrognosticos();
	}

	public Integer getQuantidadeTeimosinhas() {
		return quantidadeTeimosinhas;
	}

	public void setQuantidadeTeimosinhas(Integer quantidadeTeimosinhas) {
		this.quantidadeTeimosinhas = quantidadeTeimosinhas;
	}

	@Override
	public boolean contemTeimosinha() {
		return quantidadeTeimosinhas > 1;
	}

	@Override
	public int getUltimoConcurso() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isConcorrendo(Integer concurso) {
		// TODO Auto-generated method stub
		return false;
	}

	public AbstractApostaVO<?> toAbstractApostaVO(ParametroJogoNumerico parametro) {
		AbstractApostaVO<?> vo = ApostaUtil.gereApostaFavorita(this, parametro, getQuantidadeTeimosinhas());
		vo.setIndicadorSurpresinha(getIndicadorSurpresinha());
		Integer maxTeimosinhas = parametro.getTeimosinhas() != null ? Collections.max(parametro.getTeimosinhas()) : 0;

		((Teimosinha) vo).setQuantidadeTeimosinhas(Math.min(getQuantidadeTeimosinhas(), maxTeimosinhas));

		((ApostaNumericaVO) vo).setPrognosticos(((PalpitesSequenciais) getPalpites()).getPrognosticos());

		vo.setDataInclusao(this.getDataInclusao());
		if (vo.getValor() == null) {
			vo.setValor(Decimal.ZERO);
		}
		return vo;
	}

}
