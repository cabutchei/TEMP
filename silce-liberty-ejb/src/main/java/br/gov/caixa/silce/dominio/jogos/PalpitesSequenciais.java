package br.gov.caixa.silce.dominio.jogos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import br.gov.caixa.silce.dominio.util.ApostaUtil;

/**
 * @author c101482
 *
 */
public final class PalpitesSequenciais implements Palpites<Integer> {

	private static final long serialVersionUID = 1L;

	private List<Integer> prognosticos = new ArrayList<Integer>();

	private boolean estaOrdenado = true;

	public PalpitesSequenciais() {
		// palpites com lista de prognosticos vazia
	}

	public PalpitesSequenciais(String value) {
		setPrognosticos(ApostaUtil.getIntListPrognosticos(value));
	}

	public PalpitesSequenciais(List<Integer> prognosticos) {
		setPrognosticos(prognosticos);
	}

	public void addPrognostico(Integer prognostico) {
		addPrognostico(null, prognostico);
	}

	@Override
	public void addPrognostico(Integer eixo, Integer prognostico) {
		if (eixo != null) {
			throw new IllegalArgumentException("Palpites Sequenciais possuem somente um eixo.");
		}
		if (contemPrognostico(prognostico)) {
			throw new IllegalArgumentException("Palpites Sequenciais não devem possiui numeros repetidos.");
		}
		estaOrdenado = false;
		prognosticos.add(prognostico);
	}

	@Override
	public String toString() {
		return ApostaUtil.convertToString(getPrognosticos());
	}

	public List<Integer> getPrognosticos() {
		if (!estaOrdenado) {
			Collections.sort(prognosticos);
			estaOrdenado = true;
		}
		return Collections.unmodifiableList(prognosticos);
	}

	@Override
	public void setPrognosticos(List<Integer> prognosticos) {
		this.prognosticos.clear();
		if (prognosticos != null) {
			estaOrdenado = false;
			this.prognosticos.addAll(new LinkedHashSet<Integer>(prognosticos));
		}
	}

	private boolean contemPrognostico(Integer prognostico) {
		return prognosticos.contains(prognostico);
	}

	@Override
	public String getStringExibicao() {
		StringBuilder sb = new StringBuilder();
		for (Integer prognostico : getPrognosticos()) {
			sb.append(ApostaUtil.formataPrognosticoTela(prognostico)).append(' ');
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	@Override
	public int size() {
		return prognosticos.size();
	}

	@Override
	public int hashCode() {
		return getPrognosticos().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PalpitesSequenciais)) {
			return false;
		}
		PalpitesSequenciais other = (PalpitesSequenciais) obj;
		if (prognosticos == null) {
			if (other.prognosticos != null) {
				return false;
			}
		} else if (!getPrognosticos().equals(other.getPrognosticos())) {
			return false;
		}
		return true;
	}

}
