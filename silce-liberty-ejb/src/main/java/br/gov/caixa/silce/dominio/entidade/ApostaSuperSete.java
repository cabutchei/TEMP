package br.gov.caixa.silce.dominio.entidade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
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
import br.gov.caixa.silce.dominio.jogos.PalpitesSuperSete;
import br.gov.caixa.silce.dominio.openjpa.PalpitesSuperSeteValueHandler;
import br.gov.caixa.silce.dominio.util.ApostaUtil;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.Validate;

@Entity
@DiscriminatorValue("7")
public class ApostaSuperSete extends Aposta<PalpitesSuperSete> implements Teimosinha {
	
	private static final long serialVersionUID = 1L;

	private static final Set<SituacaoAposta.Situacao> SITUACOES_EFETIVADA = new HashSet<SituacaoAposta.Situacao>(Arrays.asList(SituacaoAposta.Situacao.getSituacoesEfetivada()));

	@Column(name = "DE_PROGNOSTICO")
	@Strategy(PalpitesSuperSeteValueHandler.STRATEGY_NAME)
	private PalpitesSuperSete palpites = new PalpitesSuperSete();

	@Column(name = "QT_TEIMOSINHA")
	private Integer quantidadeTeimosinhas;
	
	@Transient
	private transient List<String> prognosticosParaTela = null;

	private static final String PROGNOSTICO_SURPRESINHA = "XX";

	public ApostaSuperSete() {
		super(Modalidade.SUPER_7);
	}

	@Override
	public ApostaSuperSeteVO getVO() {
		ApostaSuperSeteVO vo = (ApostaSuperSeteVO) super.getVO();
		vo.setPrognosticos(getPrognosticos());
		vo.setQuantidadeTeimosinhas(getQuantidadeTeimosinhas());
		ComboAposta comboAposta = getComboAposta();
		if (comboAposta != null) {
			vo.setComboAposta(ComboApostaVO.fromEntidade(comboAposta));
		}
		return vo;
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

	@Override
	public boolean contemTeimosinha() {
		return quantidadeTeimosinhas > 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Aposta<?>> T getTeimosinha(SituacaoAposta situacao, Premio premio, Long nsuTransacao, Data dataInicioApostaComprada, Hora horaInicioApostaComprada) {
		ApostaSuperSete teimosinha = super.getTeimosinha(situacao, premio, nsuTransacao, dataInicioApostaComprada, horaInicioApostaComprada);
		teimosinha.setPrognosticos(getPalpites().getPrognosticos());
		Integer qntConcursos = premio.getConcursoInicialTroca() - getConcursoInicial();
		teimosinha.setQuantidadeTeimosinhas(getQuantidadeTeimosinhas() - qntConcursos.intValue());
		return (T) teimosinha;
	}

	@Override
	public PalpitesSuperSete getPalpites() {
		return palpites;
	}
	
	public List<List<Integer>> getPrognosticos() {
		return palpites.getPrognosticos();
	}
	
	public void setPrognosticos(List<List<Integer>> prognosticos) {
		palpites.setPrognosticos(prognosticos);
		prognosticosParaTela = null;
	}

	@Override
	public Integer getQuantidadeTeimosinhas() {
		return quantidadeTeimosinhas;
	}

	@Override
	public void setQuantidadeTeimosinhas(Integer quantidadeTeimosinhas) {
		this.quantidadeTeimosinhas = quantidadeTeimosinhas;
	}

	public String getStringExibicao() {
		return palpites.getStringExibicao();
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

	@Deprecated
	public List<String> getPrognosticosParaTela() {
		if (prognosticosParaTela == null) {
			prognosticosParaTela = new ArrayList<String>();

			if (!isSurpresinha() || SITUACOES_EFETIVADA.contains(getSituacao().getEnum())) {
				List<List<Integer>> prognosticos = getPrognosticos();
				for (List<Integer> linha : prognosticos) {
					for (Integer prognostico : linha) {
						prognosticosParaTela.add(formataPrognosticoTela(prognostico));
					}
					prognosticosParaTela.add(" | ");
				}
			} else {
				int quantidadePrognosticos = palpites.size();
				while (prognosticosParaTela.size() < quantidadePrognosticos) {
					prognosticosParaTela.add(PROGNOSTICO_SURPRESINHA);
				}

			}
		}
		return prognosticosParaTela;
	}

	@Override
	public void setIndicadorSurpresinha(IndicadorSurpresinha indicadorSurpresinha) {
		super.setIndicadorSurpresinha(indicadorSurpresinha);
		prognosticosParaTela = null;
	}

	private String formataPrognosticoTela(Integer prognostico) {
		return ApostaUtil.formataPrognosticoTela(prognostico);
	}

}
