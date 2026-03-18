package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.AbstractParametroJogo;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroJogoNumerico;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroMaisMilionaria;
import br.gov.caixa.silce.dominio.exception.CodigoErro;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;
import br.gov.caixa.silce.dominio.openjpa.PalpitesValueHandler;
import br.gov.caixa.silce.dominio.util.ApostaUtil;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.Validate;

@Entity
@DiscriminatorValue("9")
public class ApostaMaisMilionaria extends AbstractApostaNumerica {
	
	private static final long serialVersionUID = 1L;
	
	// A String é no formato: 001002003004005006... (ou seja, tres digitos para cada prognostico)
	@Column(name = "DE_PROGNOSTICO_TREVO")
	@Strategy(PalpitesValueHandler.STRATEGY_NAME)
	private PalpitesSequenciais palpitesTrevo = new PalpitesSequenciais();

	public ApostaMaisMilionaria() {
		super(Modalidade.MAIS_MILIONARIA);
	}

	@Override
	public ApostaMaisMilionariaVO getVO() {
		ApostaMaisMilionariaVO vo = (ApostaMaisMilionariaVO) super.getVO();
		vo.setPrognosticosTrevo(palpitesTrevo.getPrognosticos());
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Aposta<?> getTeimosinha(SituacaoAposta situacao, Premio premio, Long nsuTransacao, Data dataInicioApostaComprada, Hora horaInicioApostaComprada) {
		ApostaMaisMilionaria teimosinha = super.getTeimosinha(situacao, premio, nsuTransacao, dataInicioApostaComprada, horaInicioApostaComprada);
		teimosinha.setPalpitesTrevo(getPalpitesTrevo());
		return teimosinha;
	}
	
	@Override
	public boolean valida(AbstractParametroJogo parametroJogo) throws NegocioException {
		Validate.notNull(parametroJogo, "parâmetro do jogo");
		Validate.isTrue(ParametroMaisMilionaria.class.isInstance(parametroJogo), "Parametro informado não é de +Milionária");

		if (palpitesTrevo == null || palpitesTrevo.size() == 0) {
			throw new NegocioException(CodigoErro.ERRO_SISTEMA_APOSTA_INVALIDA, "Os trevos não foram preenchidos");
		}

		ParametroJogoNumerico parametroJogoNumerico = (ParametroJogoNumerico) parametroJogo;
		if (quantidadeTeimosinhas != null && !parametroJogoNumerico.getTeimosinhas().contains(quantidadeTeimosinhas) && quantidadeTeimosinhas != 0) {
			throw new NegocioException(CodigoErro.ERRO_SISTEMA_APOSTA_INVALIDA, "a quantidade selecionada de Teimosinhas está indisponível");
		}

		Decimal valor = ApostaUtil.calculeValor((ParametroMaisMilionaria) parametroJogo, this);
		if (valor == null || Decimal.ZERO.equals(valor)) {
			throw new NegocioException(CodigoErro.ERRO_SISTEMA_APOSTA_INVALIDA, "problema desconhecido na aposta");
		}

		return true;
	}

	public void addTrevos(int i) {
		palpitesTrevo.addPrognostico(i);
	}

	public PalpitesSequenciais getPalpitesTrevo() {
		return palpitesTrevo;
	}

	public void setPalpitesTrevo(PalpitesSequenciais palpitesTrevo) {
		this.palpitesTrevo = palpitesTrevo;
	}

}
