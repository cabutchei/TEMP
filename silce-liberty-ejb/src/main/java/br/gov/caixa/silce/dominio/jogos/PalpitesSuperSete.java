package br.gov.caixa.silce.dominio.jogos;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.silce.dominio.util.ApostaUtil;

public final class PalpitesSuperSete implements Palpites<List<Integer>> {

	private static final int NUMERO_COLUNAS_SUPER7 = 7;

	private static final long serialVersionUID = 1L;

	private List<List<Integer>> prognosticosSuperSete = new ArrayList<List<Integer>>(NUMERO_COLUNAS_SUPER7);


	public PalpitesSuperSete() {
		// palpites com lista de prognosticos vazia
	}

	public PalpitesSuperSete(String value) {
		prognosticosSuperSete = converteFromBanco(value);
	}

	public PalpitesSuperSete(List<List<Integer>> prognosticos) {
		this.prognosticosSuperSete = prognosticos;
	}

	private List<List<Integer>> converteFromBanco(String value) {
		List<List<Integer>> prognosticos = inicializaListaSuperSete();
		List<Integer> intListPrognosticos = ApostaUtil.getIntListPrognosticos(value);
		for (Integer prog : intListPrognosticos) {
			Integer linha = (prog - 1) / 7;
			Integer coluna = (prog - 1) % 7;
			prognosticos.get(coluna).add(linha);
		}
		return prognosticos;
	}

	private List<List<Integer>> inicializaListaSuperSete() {
		List<List<Integer>> prognosticos = new ArrayList<List<Integer>>(7);
		for (int i = 0; i < NUMERO_COLUNAS_SUPER7; i++) {
			prognosticos.add(new ArrayList<Integer>());
		}
		return prognosticos;
	}

	private String converteToBanco(List<List<Integer>> value) {
		StringBuilder sb = new StringBuilder();
		for (int coluna = 0; coluna < value.size(); coluna++) {
			List<Integer> list = value.get(coluna);
			for (Integer prog : list) {
				sb.append(ApostaUtil.formataPrognostico(1 + (coluna + prog * 7)));
			}
		}
		return sb.toString();
	}

	@Override
	public void addPrognostico(Integer eixo, List<Integer> prognostico) {
		prognosticosSuperSete.set(eixo, prognostico);
	}

	@Override
	public int size() {
		int total = 0;
		for (List<Integer> list : prognosticosSuperSete) {
			total += list.size();
		}
		return total;
	}

	/**
	 * 
	 * @return String das dezenas para serem exibidas na tela, ex: 01 02 10 00
	 */
	public String getStringExibicao() {
		StringBuilder sb = new StringBuilder();
		for (List<Integer> list : prognosticosSuperSete) {
			for (Integer integer : list) {
				sb.append(integer).append(' ');
			}
			sb.append("| ");
		}
		sb.deleteCharAt(sb.length() - 2);
		return sb.toString();
	}

	/**
	 * converte para string que é salva no banco, 001002003004....
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return converteToBanco(getPrognosticos());
	}

	@Override
	public List<List<Integer>> getPrognosticos() {
		return prognosticosSuperSete;
	}

	@Override
	public void setPrognosticos(List<List<Integer>> prognosticos) {
		this.prognosticosSuperSete = prognosticos;

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
		if (!(obj instanceof PalpitesSuperSete)) {
			return false;
		}
		PalpitesSuperSete other = (PalpitesSuperSete) obj;
		if (prognosticosSuperSete == null) {
			if (other.prognosticosSuperSete != null) {
				return false;
			}
		} else if (!getPrognosticos().equals(other.getPrognosticos())) {
			return false;
		}
		return true;
	}

}
