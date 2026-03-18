package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.AbstractParametroJogo;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroLoteca;
import br.gov.caixa.silce.dominio.exception.CodigoErro;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesLoteca;
import br.gov.caixa.silce.dominio.openjpa.PalpitesLotecaValueHandler;
import br.gov.caixa.silce.dominio.util.ApostaUtil;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Validate;

@Entity
@DiscriminatorValue("19")
public class ApostaLoteca extends Aposta<PalpitesLoteca> {

	private static final long serialVersionUID = 1L;
	
	//A String é no formato: 001002003004005006... (ou seja, tres digitos para cada prognostico)
	@Column(name = "DE_PROGNOSTICO")
	@Strategy(PalpitesLotecaValueHandler.STRATEGY_NAME)
	private PalpitesLoteca palpites  = new PalpitesLoteca();

	public ApostaLoteca() {
		super(Modalidade.LOTECA);
	}

	@Override
	public PalpitesLoteca getPalpites() {
		return palpites;
	}
	
	public void setPrognosticos(boolean[][] prognosticos) {
		palpites.setPrognosticosConvertidos(prognosticos);
	}
	
	@Override
	public ApostaLotecaVO getVO() {
		ApostaLotecaVO vo = (ApostaLotecaVO) super.getVO();
		vo.setPrognosticos(getPalpites().getPrognosticosConvertidos());
		return vo;
	}

	@Override
	public boolean valida(AbstractParametroJogo parametroJogo) throws NegocioException {
		Validate.notNull(parametroJogo, "parâmetro do jogo");
		Validate.isTrue(ParametroLoteca.class.isInstance(parametroJogo), "Parametro informado não é de Loteca");

		Decimal valor = ApostaUtil.calculeValor((ParametroLoteca) parametroJogo, this);
		if (valor == null || Decimal.ZERO.equals(valor)) {
			throw new NegocioException(CodigoErro.ERRO_SISTEMA_APOSTA_INVALIDA, "problema desconhecido na aposta");
		}

		return true;
	}

}
