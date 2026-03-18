package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.AbstractParametroJogo;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroDiaDeSorte;
import br.gov.caixa.silce.dominio.exception.CodigoErro;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.Validate;

@Entity
@DiscriminatorValue("11")
public class ApostaDiaDeSorte extends AbstractApostaNumerica {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "MM_SORTE")
	private Integer mesDeSorte;

	public ApostaDiaDeSorte() {
		super(Modalidade.DIA_DE_SORTE);
	}

	@Override
	public ApostaDiaDeSorteVO getVO() {
		ApostaDiaDeSorteVO vo = (ApostaDiaDeSorteVO) super.getVO();
		vo.setMesDeSorte(mesDeSorte);
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Aposta<?> getTeimosinha(SituacaoAposta situacao, Premio premio, Long nsuTransacao, Data dataInicioApostaComprada, Hora horaInicioApostaComprada) {
		ApostaDiaDeSorte teimosinha = super.getTeimosinha(situacao, premio, nsuTransacao, dataInicioApostaComprada, horaInicioApostaComprada);
		teimosinha.setMesDeSorte(getMesDeSorte());
		return teimosinha;
	}
	
	@Override
	public boolean valida(AbstractParametroJogo parametroJogo) throws NegocioException {
		Validate.notNull(parametroJogo, "parâmetro do jogo");
		Validate.isTrue(ParametroDiaDeSorte.class.isInstance(parametroJogo), "Parametro informado não é de Dia de Sorte");

		if (mesDeSorte == null || mesDeSorte == 0) {
			throw new NegocioException(CodigoErro.ERRO_SISTEMA_APOSTA_INVALIDA, "o Mês de Sorte não foi preenchido");
		}

		ParametroDiaDeSorte parametro = (ParametroDiaDeSorte) parametroJogo;
		if (!parametro.isMesValido(mesDeSorte)) {
			throw new NegocioException(CodigoErro.ERRO_SISTEMA_APOSTA_INVALIDA, "o Mês de Sorte não é válido");
		}

		return super.valida(parametroJogo);
	}

	public Integer getMesDeSorte() {
		return mesDeSorte;
	}

	public void setMesDeSorte(Integer mesDeSorte) {
		this.mesDeSorte = mesDeSorte;
	}

}
