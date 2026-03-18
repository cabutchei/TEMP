package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParametroLoteca extends AbstractParametroJogoEsportivo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ParametroValorApostaLoteca> valoresAposta;

	//Quantidade máxima de duplos por triplo. a chave é a quantidade de triplo, o valor é a quantidade máxima de duplos
	private Map<Integer, Integer> quantidadesDuplos;
	
	//Quantidade máxima de triplos por duplo. a chave é a quantidade de duplo, o valor é a quantidade máxima de triplos
	private Map<Integer, Integer> quantidadesTriplos;
	
	public List<ParametroValorApostaLoteca> getValoresAposta() {
		return valoresAposta;
	}

	public void setValoresAposta(List<ParametroValorApostaLoteca> valoresAposta) {
		this.valoresAposta = valoresAposta;
		calculeQuantidadesMaximaDuplosETriplos();
	}
	
	private void calculeQuantidadesMaximaDuplosETriplos() {
		quantidadesDuplos = new HashMap<Integer, Integer>();
		quantidadesTriplos = new HashMap<Integer, Integer>();
		if(valoresAposta != null) {
			for (ParametroValorApostaLoteca parametro : valoresAposta) {
				Integer quantidadeMaxDuplos = quantidadesDuplos.get(parametro.getQuantidadeTriplos());
				if(quantidadeMaxDuplos == null) {
					quantidadesDuplos.put(parametro.getQuantidadeTriplos(), parametro.getQuantidadeDuplos());
				}else {
					int max = Math.max(quantidadeMaxDuplos, parametro.getQuantidadeDuplos());
					quantidadesDuplos.put(parametro.getQuantidadeTriplos(), max);
				}

				Integer quantidadeMaxTriplos = quantidadesTriplos.get(parametro.getQuantidadeDuplos());
				if(quantidadeMaxTriplos == null) {
					quantidadesTriplos.put(parametro.getQuantidadeDuplos(), parametro.getQuantidadeTriplos());
				}else {
					int max = Math.max(quantidadeMaxTriplos, parametro.getQuantidadeTriplos());
					quantidadesTriplos.put(parametro.getQuantidadeDuplos(), max);
				}
			}
		}
	}
	
	public Integer getQuantidadeMaximaDuplos(Integer quantidadeTriplos) {
		return quantidadesDuplos.get(quantidadeTriplos);
	}

	public Integer getQuantidadeMaximaTriplos(Integer quantidadeDuplos) {
		return quantidadesTriplos.get(quantidadeDuplos);
	}

}
