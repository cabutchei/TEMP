package br.gov.caixa.silce.dominio.entidade;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.silce.dominio.interfaces.Teimosinha;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesSuperSete;
import br.gov.caixa.silce.dominio.util.ApostaUtil;

public class ApostaSuperSeteVO extends AbstractApostaVO<PalpitesSuperSete> implements Teimosinha {

	private static final long serialVersionUID = 1L;

	private final PalpitesSuperSete palpites = new PalpitesSuperSete();

	private static final String PROGNOSTICO_SURPRESINHA = "XX";

	private Integer quantidadeTeimosinhas;

	private ArrayList<String> prognosticosParaTela;

	private ComboApostaVO comboAposta;


	public ApostaSuperSeteVO() {
		super(Modalidade.SUPER_7);
	}

	@Override
	public PalpitesSuperSete getPalpites() {
		return palpites;
	}

	public String getStringExibicao() {
		return palpites.getStringExibicao();
	}

	public List<List<Integer>> getPrognosticos() {
		return palpites.getPrognosticos();
	}

	@Deprecated
	public List<String> getPrognosticosParaTela() {
		if (prognosticosParaTela == null) {
			prognosticosParaTela = new ArrayList<String>();

			if (!isSurpresinha()) {
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

	private String formataPrognosticoTela(Integer prognostico) {
		return ApostaUtil.formataPrognosticoTela(prognostico);
	}

	public void setPrognosticos(List<List<Integer>> prognosticos) {
		// FIXME fazer converter direito
		prognosticosParaTela = null;
		palpites.setPrognosticos(prognosticos);
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
		ApostaSuperSete apostaSuperSete = (ApostaSuperSete) aposta;
		apostaSuperSete.setQuantidadeTeimosinhas(quantidadeTeimosinhas);
		apostaSuperSete.setPrognosticos(getPrognosticos());
		ComboApostaVO comboAposta2 = getComboAposta();
		if (comboAposta2 != null) {
			apostaSuperSete.setComboAposta(comboAposta2.createComboAposta());
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
		if (!(obj instanceof ApostaSuperSeteVO)) {
			return false;
		}
		ApostaSuperSeteVO other = (ApostaSuperSeteVO) obj;
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

	public ComboApostaVO getComboAposta() {
		return comboAposta;
	}

	public void setComboAposta(ComboApostaVO comboAposta) {
		this.comboAposta = comboAposta;
	}

}
