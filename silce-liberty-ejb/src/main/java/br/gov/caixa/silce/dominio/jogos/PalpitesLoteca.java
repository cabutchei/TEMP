package br.gov.caixa.silce.dominio.jogos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.gov.caixa.silce.dominio.util.ApostaUtil;


/**
 * @author c101482
 *
 */
public final class PalpitesLoteca extends AbstractPalpitesEsportivos {

	private static final long serialVersionUID = 1L;
	
	private boolean quantidadesCalculado = false;
	
	private int[] quantidades;
	
	public PalpitesLoteca() {
		//14 linhas e 3 colunas
		setPrognosticosConvertidos(new boolean[14][3]);
		quantidades = new int[3];
	}
	
	public PalpitesLoteca(String value) {
		this();
		List<Integer> intListPrognosticos = ApostaUtil.getIntListPrognosticos(value);
		for (Integer prognostico : intListPrognosticos) {
			marque(prognostico);
		}
	}
	
	private void marque(Integer prognostico) {
		int coluna = prognostico % 3;
		if(coluna == 0) {
			coluna = 3;
		}
		int linha = ((prognostico - 1) / 3) + 1;
		marque(coluna, linha);
	}

	/**
	 * @param coluna de 1 até 3
	 * @param linha de 1 até 14
	 * @return
	 */
	public boolean isMarcado(int coluna, int linha) {
		return getPrognosticos().get(linha - 1)[coluna - 1];
	}
	
	/**
	 * Retorna true se todas as linhas tiverem pelo menos um "prognostico" marcado
	 * 
	 * @return
	 */
	public boolean isSimplesPreenchidos() {
		for (int i = 0; i < getPrognosticos().size(); i++) {
			boolean linhaPreenchida = false;
			for (int j = 0; j < getPrognosticos().get(i).length; j++) {
				if (getPrognosticos().get(i)[j]) {
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

	/**
	 * 
	 * Marca o prognostico independente de ser válido ou não.
	 * 
	 * @param coluna de 1 até 3
	 * @param linha de 1 até 14
	 */
	public void marque(int coluna, int linha) {
		quantidadesCalculado = false;
		getPrognosticos().get(linha - 1)[coluna - 1] = !getPrognosticos().get(linha - 1)[coluna - 1];
	}
	
	public int getQuantidadePartidas() {
		return getPrognosticos().size();
	}

	/**
	 * @return retorna array com 3 elementos. o primeiro é a quantidade de
	 *         simples, o segundo de duplos o terceiro de triplos
	 */
	private int[] getQuantidades() {
		if(quantidadesCalculado) {
			return quantidades;
		}
		quantidades = calculeQuantidades(this.getPrognosticos());
		quantidadesCalculado = true;
		return quantidades;
	}

	private int[] calculeQuantidades(List<boolean[]> prognosticos) {
		int[] resultado = new int[3];
		for (int i = 0; i < prognosticos.size(); i++) {
			int quantidade = 0;
			for (int j = 0; j < prognosticos.get(i).length; j++) {
				if (prognosticos.get(i)[j]) {
					quantidade++;
				}
			}
			if(quantidade != 0) {
				resultado[quantidade-1]++;
			}
		}
		
		return resultado;
	}
	
	public int getQuantidadeDuplos() {
		return getQuantidades()[1];
	}
	
	public int getQuantidadeTriplos() {
		return getQuantidades()[2];
	}
	
	@Override
	public String toString() {
		return ApostaUtil.convertToString(getPrognosticos());
	}

	@Override
	public int size() {
		int[] marcacoes = getQuantidades();
		return marcacoes[0] + marcacoes[1] + marcacoes[2];
	}

	@Deprecated
	public boolean[][] getPrognosticosConvertidos() {
		int tamanhoI = this.getPrognosticos().size();
		boolean[][] prognosticos = new boolean[tamanhoI][];
		for (int i = 0; i < prognosticos.length; i++) {
			prognosticos[i] = this.getPrognosticos().get(i);
		}
		return prognosticos;
	}

	@Deprecated
	public void setPrognosticosConvertidos(boolean[][] prognosticos) {
		quantidadesCalculado = false;
		ArrayList<boolean[]> progs = new ArrayList<boolean[]>();
		for (boolean[] prognostico : prognosticos) {
			progs.add(prognostico);
		}
		setPrognosticos(progs);
	}

	@Override
	public String getStringExibicao() {
		throw new UnsupportedOperationException("Não implementado");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		for (boolean[] prognostico : getPrognosticos()) {
			result = result + Arrays.hashCode(prognostico);
		}
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
		if (!(obj instanceof PalpitesLoteca)) {
			return false;
		}
		PalpitesLoteca other = (PalpitesLoteca) obj;

		// FIXME revisar isso se nao presao de um deep equals
		for (int i = 0; i < getPrognosticos().size(); i++) {
			if (!Arrays.equals(getPrognosticos().get(i), other.getPrognosticos().get(i))) {
				return false;
			}
		}
		return true;
	}
	
}
