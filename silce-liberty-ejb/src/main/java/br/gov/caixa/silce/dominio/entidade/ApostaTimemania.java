package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.AbstractParametroJogo;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroTimemania;
import br.gov.caixa.silce.dominio.exception.CodigoErro;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.Validate;

@Entity
@DiscriminatorValue("20")
public class ApostaTimemania extends AbstractApostaNumerica {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "NU_EQUIPE_ESPORTIVA")
	private Integer timeDoCoracao;

	public ApostaTimemania() {
		super(Modalidade.TIMEMANIA);
	}

	public Integer getTimeDoCoracao() {
		return timeDoCoracao;
	}

	public void setTimeDoCoracao(Integer timeDoCoracao) {
		this.timeDoCoracao = timeDoCoracao;
	}
	
	@Override
	public ApostaTimemaniaVO getVO() {
		ApostaTimemaniaVO vo = (ApostaTimemaniaVO) super.getVO();
		vo.setTimeDoCoracao(timeDoCoracao);
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Aposta<?> getTeimosinha(SituacaoAposta situacao, Premio premio, Long nsuTransacao, Data dataInicioApostaComprada, Hora horaInicioApostaComprada) {
		ApostaTimemania teimosinha = super.getTeimosinha(situacao, premio, nsuTransacao, dataInicioApostaComprada, horaInicioApostaComprada);
		teimosinha.setTimeDoCoracao(getTimeDoCoracao());
		return teimosinha;
	}
	
	@Override
	public boolean valida(AbstractParametroJogo parametroJogo) throws NegocioException {
		Validate.notNull(parametroJogo, "parâmetro do jogo");
		Validate.isTrue(ParametroTimemania.class.isInstance(parametroJogo), "Parametro informado não é de Timemania");

		if (timeDoCoracao == null || timeDoCoracao == 0) {
			throw new NegocioException(CodigoErro.ERRO_SISTEMA_APOSTA_INVALIDA, "o Time do Coração não foi preenchido");
		}

		ParametroTimemania parametro = (ParametroTimemania) parametroJogo;
		if (!parametro.isTimeValido(timeDoCoracao)) {
			throw new NegocioException(CodigoErro.ERRO_SISTEMA_APOSTA_INVALIDA, "o Time do Coração não é válido neste momento");
		}

		return super.valida(parametroJogo);
	}

}
