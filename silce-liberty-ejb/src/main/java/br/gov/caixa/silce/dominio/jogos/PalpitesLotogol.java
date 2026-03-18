package br.gov.caixa.silce.dominio.jogos;

import java.util.Arrays;
import java.util.List;

import br.gov.caixa.silce.dominio.util.ApostaUtil;
import br.gov.caixa.util.StringUtil;

/**
 * @author c101482
 * 
 */
public final class PalpitesLotogol extends AbstractPalpitesEsportivos {

	private static final long serialVersionUID = 1L;

	private boolean[][] prognosticosLotogol = new boolean[10][5];

	public PalpitesLotogol() {
		// 10 linhas e 5 colunas
	}

	public PalpitesLotogol(String value) {
		List<Integer> intListPrognosticos = ApostaUtil.getIntListPrognosticos(value);
		for (Integer prognostico : intListPrognosticos) {
			marque(prognostico);
		}
	}

	private void marque(Integer prognostico) {
		int coluna = prognostico % 5;
		if (coluna == 0) {
			coluna = 5;
		}
		int linha = ((prognostico - 1) / 5) + 1;
		marque(coluna, linha);
	}

	/**
	 * @param coluna
	 *            de 1 até 5
	 * @param linha
	 *            de 1 até 10
	 * @return
	 */
	public boolean isMarcado(int coluna, int linha) {
		return prognosticosLotogol[linha - 1][coluna - 1];
	}

	/**
	 * @param partida
	 *            número da partida de 1 até 5
	 * @param time
	 *            time 1 ou 2
	 * @param coluna
	 *            de 1 até 5(placar)
	 * @return
	 */
	public boolean isMarcado(int partida, int time, int coluna) {
		int linha = calculeLinha(partida, time);
		return isMarcado(coluna, linha);
	}

	/**
	 * 
	 * @param partida
	 * @param time
	 * @return linha de 1 até 10
	 */
	private int calculeLinha(int partida, int time) {
		return (partida - 1) * 2 + time;
	}

	/**
	 * @param coluna
	 *            de 1 até 5
	 * @param linha
	 *            de 1 até 10
	 */
	public void marque(int coluna, int linha) {
		for (int i = 0; i < prognosticosLotogol[linha - 1].length; i++) {
			prognosticosLotogol[linha - 1][i] = false;
		}
		prognosticosLotogol[linha - 1][coluna - 1] = true;
	}

	/**
	 * @param partida
	 *            número da partida de 1 até 5
	 * @param time
	 *            time 1 ou 2
	 * @param coluna
	 *            de 1 até 5(placar)
	 * @return
	 */
	public void marque(Integer partida, Integer time, Integer coluna) {
		int linha = calculeLinha(partida, time);
		marque(coluna, linha);
	}
	
	public int getQuantidadePalpites() {
		return prognosticosLotogol.length / 2;
	}

	@Override
	public String toString() {
		return ApostaUtil.convertToString(prognosticosLotogol);
	}

	public boolean isPreenchido() {
		for (int i = 0; i < prognosticosLotogol.length; i++) {
			boolean linhaPreenchida = false;
			for (int j = 0; j < prognosticosLotogol[i].length; j++) {
				if (prognosticosLotogol[i][j]) {
					linhaPreenchida = true;
					// sai do primeiro for
					break;
				}
			}
			if (!linhaPreenchida) {
				return false;
			}
		}
		return true;
	}

	public PlacarLotogol recuperePlacar(int partida, int time) {
		int linha = calculeLinha(partida, time);
		return PlacarLotogol.getByValue(getColunaMarcada(linha) - 1);
	}

	/**
	 * @param linha
	 * @return a coluna de 1 a 5
	 */
	private int getColunaMarcada(int linha) {
		for (int i = 0; i < prognosticosLotogol[linha - 1].length; i++) {
			if (prognosticosLotogol[linha - 1][i]) {
				return i + 1;
			}
		}
		return -1;
	}

	@Deprecated
	public void setPrognosticosConvertidos(boolean[][] prognosticos) {
		this.prognosticosLotogol = prognosticos;
	}

	@Deprecated
	public boolean[][] getPrognosticosConvertidos() {
		return prognosticosLotogol;
	}

	public String[][] getRepresentacaoPlacares() {
		String[][] resultado = new String[5][2];

		for (int i = 1; i <= resultado.length; i++) {
			PlacarLotogol placarTime1 = recuperePlacar(i, 1);
			if (placarTime1 != null) {
				resultado[i - 1][0] = placarTime1.getPlacar();
			}
			PlacarLotogol placarTime2 = recuperePlacar(i, 2);
			if (placarTime2 != null) {
				resultado[i - 1][1] = placarTime2.getPlacar();
			}
		}

		return resultado;
	}

	@Override
	public int size() {
		Integer qtdPalpites = 0;
		Boolean placarTime1Preenchido = false;
		Boolean placarTime2Preenchido = false;

		for (int i = 0; i < prognosticosLotogol.length; i = i + 2) {
			for (int j = 0; j < prognosticosLotogol[i].length; j++) {

				/* Valida se o placar do primeiro time foi preenchido */
				if (prognosticosLotogol[i][j]) {
					placarTime1Preenchido = true;
				}

				/* Valida se o placar do segundo time foi preenchido */
				if (prognosticosLotogol[i + 1][j]) {
					placarTime2Preenchido = true;
				}

				/* Se ambos os placares dos dois times foram preenchidos, conta como um palpite */
				if (placarTime1Preenchido && placarTime2Preenchido) {
					qtdPalpites++;
					break;
				}
			}
			placarTime1Preenchido = false;
			placarTime2Preenchido = false;
		}
		return qtdPalpites;
	}

	@Override
	public String getStringExibicao() {
		throw new UnsupportedOperationException("Não implementado");
	}

	/**
	 * Método que contabiliza palpites conforme marca-se o placar dos dois times de cada jogo.
	 * 
	 * @return String com a quantidade de palpites formatado com zero a esquerda.
	 */
	public String getQtPalpitesSelecionados() {
		Integer qtdPalpites = size();
		return StringUtil.completeAEsquerda(qtdPalpites.toString(), QTD_MAX_DECIMAIS_PALP_SELECIONADOS, CARACT_COMP_PALP_SELECIONADOS);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(prognosticosLotogol);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PalpitesLotogol)) {
			return false;
		}
		PalpitesLotogol other = (PalpitesLotogol) obj;
		if (!Arrays.deepEquals(prognosticosLotogol, other.prognosticosLotogol)) {
			return false;
		}
		return true;
	}



}
