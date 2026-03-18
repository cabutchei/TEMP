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

import br.gov.caixa.silce.dominio.jogos.IndicadorSurpresinha;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;
import br.gov.caixa.silce.dominio.openjpa.PalpitesValueHandler;
import br.gov.caixa.silce.dominio.util.ApostaUtil;

/**
 * @author c101482
 *
 */
@Entity
public abstract class AbstractHistoricoApostaNumerica extends HistoricoAposta<PalpitesSequenciais> {

	private static final long serialVersionUID = 1L;

	private static final String PROGNOSTICO_SURPRESINHA = "XX";

	private static final Set<SituacaoAposta.Situacao> SITUACOES_EFETIVADA = new HashSet<SituacaoAposta.Situacao>(Arrays.asList(SituacaoAposta.Situacao.getSituacoesEfetivada()));

	//A String é no formato: 001002003004005006... (ou seja, tres digitos para cada prognostico)
	@Column(name = "DE_PROGNOSTICO")
	@Strategy(PalpitesValueHandler.STRATEGY_NAME)
	private PalpitesSequenciais palpites  = new PalpitesSequenciais();
	
	@Column(name = "QT_TEIMOSINHA")
	private Integer quantidadeTeimosinhas;

	@Transient
	private transient List<String> prognosticosParaTela = null;
	

	/**
	 * vazio devido ao jpa
	 */
	protected AbstractHistoricoApostaNumerica() {
		//vazio devido ao jpa
	}
	
	public AbstractHistoricoApostaNumerica(Modalidade modalidade) {
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
		vo.setPrognosticos(getPrognosticos());
		vo.setQuantidadeTeimosinhas(getQuantidadeTeimosinhas());
		return vo;
	}

	public List<Integer> getPrognosticos() {
		return palpites.getPrognosticos();
	}
	
	public void setPrognosticos(List<Integer> prognosticos) {
		palpites.setPrognosticos(prognosticos);
		prognosticosParaTela = null;
	}

	@Override
	public void setIndicadorSurpresinha(IndicadorSurpresinha indicadorSurpresinha) {
		super.setIndicadorSurpresinha(indicadorSurpresinha);
		prognosticosParaTela = null;
	}

	public void addPrognostico(int i) {
		prognosticosParaTela = null;
		palpites.addPrognostico(i);
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

	public int getUltimoConcurso() {
		Integer concursoFinal = getConcursoInicial();
		if (contemTeimosinha()) {
			concursoFinal = concursoFinal + quantidadeTeimosinhas - 1;
		}
		return concursoFinal;
	}

	public boolean isConcorrendo(Integer concurso) {
		return concurso >= getConcursoInicial() && concurso <= getUltimoConcurso();
	}
	
	@Override
	public void setSituacao(SituacaoAposta situacao) {
		super.setSituacao(situacao);
		prognosticosParaTela = null;
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
