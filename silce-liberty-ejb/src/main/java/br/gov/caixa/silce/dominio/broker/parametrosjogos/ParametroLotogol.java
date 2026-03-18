package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.util.List;

public class ParametroLotogol extends AbstractParametroJogoEsportivo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ParametroValorApostaLotogol> valoresAposta;

	public List<ParametroValorApostaLotogol> getValoresAposta() {
		return valoresAposta;
	}

	public void setValoresAposta(List<ParametroValorApostaLotogol> valoresAposta) {
		this.valoresAposta = valoresAposta;
	}



}
