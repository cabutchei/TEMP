package br.gov.caixa.silce.dominio.entidade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.AbstractParametroJogo;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroJogoNumerico;
import br.gov.caixa.silce.dominio.exception.CodigoErro;
import br.gov.caixa.silce.dominio.interfaces.Teimosinha;
import br.gov.caixa.silce.dominio.jogos.IndicadorSurpresinha;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;
import br.gov.caixa.silce.dominio.openjpa.PalpitesValueHandler;
import br.gov.caixa.silce.dominio.util.ApostaUtil;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.Validate;

/**
 * @author c101482
 *
 */
@Entity
public abstract class AbstractApostaNumerica extends Aposta<PalpitesSequenciais> implements Teimosinha {

	private static final long serialVersionUID = 1L;

	private static final String PROGNOSTICO_SURPRESINHA = "XX";

	private static final Set<SituacaoAposta.Situacao> SITUACOES_EFETIVADA = new HashSet<SituacaoAposta.Situacao>(Arrays.asList(SituacaoAposta.Situacao.getSituacoesEfetivada()));

	//A String é no formato: 001002003004005006... (ou seja, tres digitos para cada prognostico)
	@Column(name = "DE_PROGNOSTICO")
	@Strategy(PalpitesValueHandler.STRATEGY_NAME)
	private PalpitesSequenciais palpites  = new PalpitesSequenciais();
	
	@Column(name = "QT_TEIMOSINHA")
	protected Integer quantidadeTeimosinhas;

	@Transient
	private transient List<String> prognosticosParaTela = null;


	/**
	 * vazio devido ao jpa
	 */
	protected AbstractApostaNumerica() {
		//vazio devido ao jpa
	}
	
	public AbstractApostaNumerica(Modalidade modalidade) {
		super(modalidade);
	}

	@Override
	public PalpitesSequenciais getPalpites() {
		return palpites;
	}

	public String getStringExibicao() {
		return palpites.getStringExibicao();
	}

	@Override
	public ApostaNumericaVO getVO() {
		ApostaNumericaVO vo = (ApostaNumericaVO) super.getVO();

		if (!vo.getIndicadorBolao()) {
			vo.setPrognosticos(getPrognosticos());
			vo.setQuantidadeTeimosinhas(getQuantidadeTeimosinhas());
			ComboAposta comboAposta = getComboAposta();
			if (comboAposta != null) {
				vo.setComboAposta(ComboApostaVO.fromEntidade(comboAposta));
			}
		}

		return vo;
	}

	public List<Integer> getPrognosticos() {
		return palpites.getPrognosticos();
	}
	
	public void setPrognosticos(List<Integer> prognosticos) {
		palpites.setPrognosticos(prognosticos);
		prognosticosParaTela = null;
	}

	public void addPrognostico(int i) {
		prognosticosParaTela = null;
		palpites.addPrognostico(i);
	}

	@Override
	public Integer getQuantidadeTeimosinhas() {
		return quantidadeTeimosinhas;
	}

	@Override
	public void setQuantidadeTeimosinhas(Integer quantidadeTeimosinhas) {
		this.quantidadeTeimosinhas = quantidadeTeimosinhas;
	}
	
	@Override
	public boolean contemTeimosinha() {
		return quantidadeTeimosinhas > 1;
	}

	@Override
	public void setIndicadorSurpresinha(IndicadorSurpresinha indicadorSurpresinha) {
		super.setIndicadorSurpresinha(indicadorSurpresinha);
		prognosticosParaTela = null;
	}

	@Override
	public int getUltimoConcurso() {
		Integer concursoFinal = getConcursoInicial();
		if (contemTeimosinha()) {
			concursoFinal = concursoFinal + quantidadeTeimosinhas - 1;
		}
		return concursoFinal;
	}

	@Override
	public boolean isConcorrendo(Integer concurso) {
		return concurso >= getConcursoInicial() && concurso <= getUltimoConcurso();
	}
	
	@Override
	public void setSituacao(SituacaoAposta situacao) {
		super.setSituacao(situacao);
		prognosticosParaTela = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Aposta<?>> T getTeimosinha(SituacaoAposta situacao, Premio premio, Long nsuTransacao, Data dataInicioApostaComprada, Hora horaInicioApostaComprada) {
		AbstractApostaNumerica teimosinha = super.getTeimosinha(situacao, premio, nsuTransacao, dataInicioApostaComprada, horaInicioApostaComprada);
		teimosinha.setPrognosticos(getPalpites().getPrognosticos());
		Integer qntConcursos = premio.getConcursoInicialTroca() - getConcursoInicial();
		teimosinha.setQuantidadeTeimosinhas(getQuantidadeTeimosinhas() - qntConcursos.intValue());
		return (T) teimosinha;
	}
	
	@Override
	public boolean valida(AbstractParametroJogo parametroJogo) throws NegocioException {
		Validate.notNull(parametroJogo, "parametro do jogo");
		Validate.isTrue(ParametroJogoNumerico.class.isInstance(parametroJogo), "Parametro informado nao e de Jogo Numerico");

		ParametroJogoNumerico parametroJogoNumerico = (ParametroJogoNumerico) parametroJogo;
		if (quantidadeTeimosinhas != null && !parametroJogoNumerico.getTeimosinhas().contains(quantidadeTeimosinhas) && quantidadeTeimosinhas != 0) {
			throw new NegocioException(CodigoErro.ERRO_SISTEMA_APOSTA_INVALIDA, "a quantidade selecionada de Teimosinhas está indisponível");
		}

		Decimal valor = ApostaUtil.calculeValor((ParametroJogoNumerico) parametroJogo, this);
		if (valor == null || Decimal.ZERO.equals(valor)) {
			throw new NegocioException(CodigoErro.ERRO_SISTEMA_APOSTA_INVALIDA, "problema desconhecido na aposta");
		}

		return true;
	}

	//CODIGO DUPLICADO EM APOSTANUMERICAVO
	public List<String> getPrognosticosParaTela() {
		if (prognosticosParaTela == null) {
			prognosticosParaTela = new ArrayList<String>();

			if (getApostaComprada() == null) {
				for (Integer prognostico : getPrognosticos()) {
					if (isSurpresinha()) {
						prognosticosParaTela.add(PROGNOSTICO_SURPRESINHA);
					} else {
						prognosticosParaTela.add(formataPrognosticoTela(prognostico));
					}
				}
				// se esta efetivada exibe prognostico ou nao eh surpresinha
			} else if (!isSurpresinha() || SITUACOES_EFETIVADA.contains(getSituacao().getEnum())) {
				for (Integer prognostico : getPrognosticos()) {
					prognosticosParaTela.add(formataPrognosticoTela(prognostico));
				}
				// se NAO esta efetivada exibe XX
			} else {
				int quantidadePrognosticos = getPrognosticos().size();
				while (prognosticosParaTela.size() < quantidadePrognosticos) {
					prognosticosParaTela.add(PROGNOSTICO_SURPRESINHA);
				}

			}
		}
		return prognosticosParaTela;
	}
	
	private String formataPrognosticoTela(Integer prognostico) {
		return ApostaUtil.formataPrognosticoTela(prognostico);
	}
}
