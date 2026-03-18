package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.AbstractParametroJogo;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroLotogol;
import br.gov.caixa.silce.dominio.exception.CodigoErro;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesLotogol;
import br.gov.caixa.silce.dominio.openjpa.PalpitesLotogolValueHandler;
import br.gov.caixa.silce.dominio.util.ApostaUtil;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Validate;

@Entity
@DiscriminatorValue("14")
public class ApostaLotogol extends Aposta<PalpitesLotogol> {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "QT_APOSTA")
	private Integer quantidadeApostas = 1;

	//A String é no formato: 001002003004005006... (ou seja, tres digitos para cada prognostico)
	@Column(name = "DE_PROGNOSTICO")
	@Strategy(PalpitesLotogolValueHandler.STRATEGY_NAME)
	private PalpitesLotogol palpites  = new PalpitesLotogol();

	public ApostaLotogol() {
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
	public ApostaLotogolVO getVO() {
		ApostaLotogolVO vo = (ApostaLotogolVO) super.getVO();
		vo.setPrognosticos(getPalpites().getPrognosticosConvertidos());
		return vo;
	}

	@Override
	public boolean valida(AbstractParametroJogo parametroJogo) throws NegocioException {
		Validate.notNull(parametroJogo, "parâmetro do jogo");
		Validate.isTrue(ParametroLotogol.class.isInstance(parametroJogo), "Parametro informado não é de Lotogol");

		Decimal valor = ApostaUtil.calculeValor((ParametroLotogol) parametroJogo, this);
		if (valor == null || Decimal.ZERO.equals(valor)) {
			throw new NegocioException(CodigoErro.ERRO_SISTEMA_APOSTA_INVALIDA, "problema desconhecido na aposta");
		}

		return true;

	}
}
