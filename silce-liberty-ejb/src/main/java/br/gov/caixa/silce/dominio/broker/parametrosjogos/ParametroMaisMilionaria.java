package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.gov.caixa.util.Decimal;

/**
 * Resposta para a Solicitação de parâmetros da Modalidade de Jogo: MAIS MILIONARIA.
 * 
 */
public class ParametroMaisMilionaria extends ParametroJogoNumerico {

	private static final long serialVersionUID = 1L;

	private ParametroMaisMilionariaTrevo trevos;

	private List<ParametroValorApostaMilionaria> valoresApostasMultiplas;

	private HashMap<List<Integer>, Decimal> mapaQuantidadePrognosticosValorMaisMilionaria;

	public List<ParametroValorApostaMilionaria> getValoresApostasMultiplas() {
		return valoresApostasMultiplas;
	}

	public void setValoresApostasMultiplas(List<ParametroValorApostaMilionaria> valoresApostasMultiplas) {
		this.valoresApostasMultiplas = valoresApostasMultiplas;
		mapaQuantidadePrognosticosValorMaisMilionaria = new HashMap<List<Integer>, Decimal>();
		if (valoresApostasMultiplas != null) {
			for (ParametroValorApostaMilionaria parametro : valoresApostasMultiplas) {
				List<Integer> progJogo = new ArrayList<Integer>();
				progJogo.add(parametro.getNumeroPrognosticos());
				progJogo.add(parametro.getNumeroTrevos());
				mapaQuantidadePrognosticosValorMaisMilionaria.put(progJogo, parametro.getValor());
			}
		}
	}

	public Decimal getValorMaisMilionaria(List<Integer> quantidadePrognosticos) {
		return mapaQuantidadePrognosticosValorMaisMilionaria.get(quantidadePrognosticos);
	}

	public ParametroMaisMilionariaTrevo getTrevos() {
		return trevos;
	}

	public void setTrevos(ParametroMaisMilionariaTrevo trevos) {
		this.trevos = trevos;
	}

}
