package br.gov.caixa.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

public class GeraSurpresinha {

	private SecureRandom random;

	private int menorPrognostico;

	private int maiorPrognostico;

	private static final Integer QTD_COLUNAS_SUPER_7 = 7;

	private static final Integer LIMITE_ESPACO_AMOSTRAL = 6;

	private static final Logger LOG = LogManager.getLogger(GeraSurpresinha.class, new MessageFormatMessageFactory());

	public GeraSurpresinha(int menorPrognostico, int maiorPrognostico) {
		random = new SecureRandom();
		this.menorPrognostico = menorPrognostico;
		this.maiorPrognostico = maiorPrognostico;
	}

	/**
	 * É o mesmo que chamar o outro método sem numeros pre-digitados.
	 * 
	 * @param qtdPrognosticos
	 * @return
	 */
	public int[] gere(int qtdPrognosticos) {
		return this.gere(qtdPrognosticos, new int[] {});
	}

	public List<Integer> gereLista(int qtdPrognosticos) {
		int[] prognosticos = this.gere(qtdPrognosticos, new int[] {});
		List<Integer> retorno = new ArrayList<Integer>();
		for (int i = 0; i < prognosticos.length; i++) {
			retorno.add(prognosticos[i]);
		}
		return retorno;
	}

	public int[] gere(int qtdPrognosticos, int[] numerosDigitados) {
		int range = (maiorPrognostico - menorPrognostico) + 1;
		int[] prognosticos = new int[range];
		int[] prognosticosSorteados = new int[qtdPrognosticos];
		for (int prognosticsIndex = 0; prognosticsIndex < range; prognosticsIndex++) {
			prognosticos[prognosticsIndex] = prognosticsIndex + menorPrognostico;
		}

		// "Sorteia" os numeros já digitados
		int drawnPrognosticsIndex = 0;
		for (; drawnPrognosticsIndex < numerosDigitados.length; drawnPrognosticsIndex++) {
			int drawnPosition = (numerosDigitados[drawnPrognosticsIndex] - menorPrognostico) - drawnPrognosticsIndex;
			int drawnPrognostic = prognosticos[drawnPosition];
			prognosticosSorteados[drawnPrognosticsIndex] = drawnPrognostic;
			// Desloca todos os prognosticos a partir da posicao sorteada, apagagando a posicao sorteada.
			System.arraycopy(prognosticos, drawnPosition + 1, prognosticos, drawnPosition, (range - drawnPrognosticsIndex)
					- (drawnPosition + 1));
		}

		// Sorteia o restante dos números
		for (; drawnPrognosticsIndex < qtdPrognosticos; drawnPrognosticsIndex++) {
			int drawnPosition = random.nextInt(range - drawnPrognosticsIndex);
			int drawnPrognostic = prognosticos[drawnPosition];
			prognosticosSorteados[drawnPrognosticsIndex] = drawnPrognostic;
			// Desloca todos os prognosticos a partir da posicao sorteada, apagagando a posicao sorteada.
			System.arraycopy(prognosticos, drawnPosition + 1, prognosticos, drawnPosition, (range - drawnPrognosticsIndex)
					- (drawnPosition + 1));
		}
		Arrays.sort(prognosticosSorteados);
		return prognosticosSorteados;
	}

	public int[] gere(Long idRapidao, int qtdPrognosticos, int[] numerosDigitados, int[] numerosProibidos, boolean trevo) {
		if ((numerosProibidos != null) && (numerosProibidos.length > 5)) {
			throw new IllegalArgumentException("ME036-Quantidade máxima de números proibidos: 5 ");
		}
		Arrays.sort(numerosProibidos);
		int range = (maiorPrognostico - menorPrognostico) + 1;
		int[] prognosticos = new int[range];
		int[] prognosticosSorteados = new int[qtdPrognosticos];
		for (int prognosticsIndex = 0; prognosticsIndex < range; prognosticsIndex++) {
			prognosticos[prognosticsIndex] = prognosticsIndex + menorPrognostico;
		}

		// "Sorteia" os numeros já digitados
		int drawnPrognosticsIndex = 0;
		for (; drawnPrognosticsIndex < numerosDigitados.length; drawnPrognosticsIndex++) {
			int drawnPosition = (numerosDigitados[drawnPrognosticsIndex] - menorPrognostico) - drawnPrognosticsIndex;
			int drawnPrognostic = prognosticos[drawnPosition];
			prognosticosSorteados[drawnPrognosticsIndex] = drawnPrognostic;
			// Desloca todos os prognosticos a partir da posicao sorteada, apagagando a posicao sorteada.
			System.arraycopy(prognosticos, drawnPosition + 1, prognosticos, drawnPosition, (range - drawnPrognosticsIndex)
				- (drawnPosition + 1));
		}

		int qtdeLoop = 0;
		// Sorteia o restante dos números
		for (; drawnPrognosticsIndex < qtdPrognosticos; drawnPrognosticsIndex++) {
			qtdeLoop++;
			if (qtdeLoop > 100) {
				LOG.error("Passou no loop Rapidão 100 vezes. Abortando loop para evitar statckOverFlow. Parâmetros: idRapidao:" + idRapidao + " - qtdPrognosticos:"
					+ qtdPrognosticos + " - numerosDigitados:" + ((numerosDigitados == null) ? "null" : Arrays.toString(numerosDigitados))
					+ " - numerosProibidos:" + ((numerosProibidos == null) ? "null" : Arrays.toString(numerosProibidos)) + " - trevo:" + trevo);
				throw new IllegalArgumentException("ME036-Quantidade máxima de números proibidos: 5 ");
			}
			int drawnPosition = random.nextInt(range - drawnPrognosticsIndex);
			int drawnPrognostic = prognosticos[drawnPosition];
			int binarySearch = Arrays.binarySearch(numerosProibidos, drawnPrognostic);
			if ((binarySearch >= 0) && (!trevo)) {
				drawnPrognosticsIndex--;
				continue;
			}
			prognosticosSorteados[drawnPrognosticsIndex] = drawnPrognostic;
			// Desloca todos os prognosticos a partir da posicao sorteada, apagagando a posicao sorteada.
			System.arraycopy(prognosticos, drawnPosition + 1, prognosticos, drawnPosition, (range - drawnPrognosticsIndex)
				- (drawnPosition + 1));
		}
		Arrays.sort(prognosticosSorteados);
		return prognosticosSorteados;
	}

	private List<List<Integer>> gereSuperSete(List<Integer> quantidadeSorteioCadaColuna) {
		List<List<Integer>> sorteados = new ArrayList<List<Integer>>();
		for (int i = 0; i < QTD_COLUNAS_SUPER_7; i++) {
			int jFinal = quantidadeSorteioCadaColuna.get(i);
			sorteados.add(new ArrayList<Integer>());
			for (int j = 0; j < jFinal; j++) {
				random = new SecureRandom();
				sorteados.get(i).add(gere(1)[0]);
			}
		}
		return sorteados;
	}

	public List<List<Integer>> gereSuperSete(Integer quantidadePrognosticos, List<List<Integer>> marcados) {
		List<List<Integer>> gereSuperSeteFinal = inicializaListaSurpresaSuperSete();
		setPrognosticosMarcados(marcados, gereSuperSeteFinal);
		List<Integer> quantidadeSorteioCadaColuna = inicializaQuantidadeSorteioCadaColuna(quantidadePrognosticos, gereSuperSeteFinal);
		List<List<Integer>> gereSuperSete = gereSuperSete(quantidadeSorteioCadaColuna);
		setListaPrognosticosFinais(gereSuperSeteFinal, gereSuperSete);
		return gereSuperSeteFinal;
	}

	private void setListaPrognosticosFinais(List<List<Integer>> gereSuperSeteFinal, List<List<Integer>> gereSuperSete) {
		for (int i = 0; i < gereSuperSete.size(); i++) {
			List<Integer> list = gereSuperSete.get(i);
			for (int j = 0; j < list.size(); j++) {
				Integer valorAdicionar = list.get(j);
				while (gereSuperSeteFinal.get(i).contains(valorAdicionar)) {
					valorAdicionar = gere(1)[0];
				}
				gereSuperSeteFinal.get(i).add(valorAdicionar);
			}
			Collections.sort(gereSuperSeteFinal.get(i));
		}
	}

	private void setPrognosticosMarcados(List<List<Integer>> marcados, List<List<Integer>> gereSuperSeteFinal) {
		if (marcados != null) {
			for (int i = 0; i < marcados.size(); i++) {
				List<Integer> colunaMarcada = marcados.get(i);
				for (int j = 0; j < colunaMarcada.size(); j++) {
					Integer valorMarcado = colunaMarcada.get(j);
					gereSuperSeteFinal.get(i).add(valorMarcado);
				}
			}
		}
	}

	private List<Integer> inicializaQuantidadeSorteioCadaColuna(Integer quantidadePrognosticos, List<List<Integer>> prognosticosFinais) {
		List<Integer> quantidadeSorteioCadaColuna = new ArrayList<Integer>();
		Integer totalEmCadaColuna = quantidadePrognosticos / QTD_COLUNAS_SUPER_7;
		Integer quantidadeDeColunasComExtra = quantidadePrognosticos % QTD_COLUNAS_SUPER_7;
		for (int i = 0; i < QTD_COLUNAS_SUPER_7; i++) {
			quantidadeSorteioCadaColuna.add(totalEmCadaColuna);
		}
		for (int i = 0; i < prognosticosFinais.size(); i++) {
			Integer valorAnterior = quantidadeSorteioCadaColuna.get(i);
			quantidadeSorteioCadaColuna.set(i, valorAnterior - prognosticosFinais.get(i).size());
		}
		for (Integer integer : quantidadeSorteioCadaColuna) {
			if (integer < 0) {
				quantidadeDeColunasComExtra--;
			}
		}
		List<Integer> colunasBloqueadas = new ArrayList<Integer>();
		for (int i = 0; i < quantidadeDeColunasComExtra; i++) {
			Integer valorAtualDaColuna = null;
			Integer colunaComMais = null;
			Boolean done = false;
			do {
				random = new SecureRandom();
				colunaComMais = random.nextInt(QTD_COLUNAS_SUPER_7);
				valorAtualDaColuna = quantidadeSorteioCadaColuna.get(colunaComMais);
				if (valorAtualDaColuna > -1 && valorAtualDaColuna < (totalEmCadaColuna + 1) && !colunasBloqueadas.contains(colunaComMais)) {
					marcaColunaBloqueada(colunasBloqueadas, valorAtualDaColuna, colunaComMais);
					quantidadeSorteioCadaColuna.set(colunaComMais, valorAtualDaColuna + 1);
					done = true;
				}
			} while (!done);
		}
		return quantidadeSorteioCadaColuna;
	}

	private void marcaColunaBloqueada(List<Integer> colunasBloqueadas, Integer valorAtualDaColuna, Integer colunaComMais) {
		if (valorAtualDaColuna == 0) {
			colunasBloqueadas.add(colunaComMais);
		}
	}

	private List<List<Integer>> inicializaListaSurpresaSuperSete() {
		List<List<Integer>> gereSuperSeteFinal = new ArrayList<List<Integer>>();
		for (int i = 0; i < QTD_COLUNAS_SUPER_7; i++) {
			gereSuperSeteFinal.add(new ArrayList<Integer>());
		}
		return gereSuperSeteFinal;
	}

	public List<List<Integer>> gereSuperSete(Integer quantidadePrognosticos) {
		return gereSuperSete(quantidadePrognosticos, null);
	}

	public int[][] gere(int qtdSorteios, int qtdPrognosticos) {
		int[][] sorteios = new int[qtdSorteios][qtdPrognosticos];
		for (int sorteioIndex = 0; sorteioIndex < qtdSorteios; sorteioIndex++) {
			random = new SecureRandom();
			boolean sorteioValido;
			do {
				sorteioValido = true;
				sorteios[sorteioIndex] = gere(qtdPrognosticos);
				for (int sorteioAComparar = 0; sorteioAComparar < sorteioIndex && sorteioValido; sorteioAComparar++) {
					if (Arrays.equals(sorteios[sorteioIndex], sorteios[sorteioAComparar])) {
						sorteioValido = false;

						// Foi feito esse tratamento por conta dos trevos da mais milionaira que tem um espaço amostral
						// de possibilidades baixo com isso aumenta incidencia de um loop infinito ocorrer, contudo no
						// futuro caso haja novas modalidades rever essa regra se necessario.
						if (this.maiorPrognostico <= LIMITE_ESPACO_AMOSTRAL) {
							sorteioValido = true;
						}
					}
				}
			} while (!sorteioValido);
		}
		return sorteios;
	}
}
