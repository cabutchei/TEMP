package br.gov.caixa.silce.dominio.entidade;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.silce.dominio.interfaces.Teimosinha;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;
import br.gov.caixa.silce.dominio.util.ApostaUtil;

/**
 * @author c101482
 *
 */
public class ApostaNumericaVO extends AbstractApostaVO<PalpitesSequenciais> implements Teimosinha{

	private static final long serialVersionUID = 1L;

	private final PalpitesSequenciais palpites  = new PalpitesSequenciais();
	
	private static final String PROGNOSTICO_SURPRESINHA = "XX";

	private Integer quantidadeTeimosinhas;

	private ArrayList<String> prognosticosParaTela;
	
	private ComboApostaVO comboAposta;

	protected ApostaNumericaVO() {
		//vazio devido ao jpa
	}
	
	public ApostaNumericaVO(Modalidade modalidade) {
		super(modalidade);
	}

	@Override
	public PalpitesSequenciais getPalpites() {
		return palpites;
	}

	public String getStringExibicao() {
		return palpites.getStringExibicao();
	}


	public List<Integer> getPrognosticos() {
		return palpites.getPrognosticos();
	}

	//CODIGO DUPLICADO EM AbstractApostaNumerica
	public List<String> getPrognosticosParaTela() {
		if (prognosticosParaTela == null) {
			prognosticosParaTela = new ArrayList<String>();

			if (!isSurpresinha()) {
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

	public void setPrognosticos(List<Integer> prognosticos) {
		prognosticosParaTela = null;
		palpites.setPrognosticos(prognosticos);
	}

	public ComboApostaVO getComboAposta() {
		return comboAposta;
	}

	public void setComboAposta(ComboApostaVO comboAposta) {
		this.comboAposta = comboAposta;
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
	public int getUltimoConcurso() {
		Integer concursoFinal = getConcursoAlvo();
		if (contemTeimosinha()) {
			concursoFinal = concursoFinal + quantidadeTeimosinhas - 1;
		}
		return concursoFinal;
	}

	@Override
	public boolean isConcorrendo(Integer concurso) {
		return concurso >= getConcursoAlvo() && concurso <= getUltimoConcurso();
	}

	@Override
	protected void populeAposta(Aposta<?> aposta) {
		AbstractApostaNumerica apostaNumerica = (AbstractApostaNumerica) aposta;
		apostaNumerica.setQuantidadeTeimosinhas(apostaNumerica.getIndicadorBolao() != null && apostaNumerica.getIndicadorBolao() ? 0 : quantidadeTeimosinhas);
		apostaNumerica.setPrognosticos(getPrognosticos());
		ComboApostaVO comboAposta2 = getComboAposta();
		if (comboAposta2 != null) {
			apostaNumerica.setComboAposta(comboAposta2.createComboAposta());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + palpites.hashCode();
		result = prime * result + ((quantidadeTeimosinhas == null) ? 0 : quantidadeTeimosinhas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ApostaNumericaVO)) {
			return false;
		}
		ApostaNumericaVO other = (ApostaNumericaVO) obj;
		if (!palpites.equals(other.palpites)) {
			return false;
		}
		if (quantidadeTeimosinhas == null) {
			if (other.quantidadeTeimosinhas != null) {
				return false;
			}
		} else if (!quantidadeTeimosinhas.equals(other.quantidadeTeimosinhas)) {
			return false;
		}
		return true;
	}

}
