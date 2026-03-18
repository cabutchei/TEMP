package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.dominio.SaidaBroker;

public class RetornoParametrosJogos extends SaidaBroker {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<AbstractParametroJogo> parametros = new ArrayList<AbstractParametroJogo>();

	public List<AbstractParametroJogo> getParametros() {
		return parametros;
	}
	
}
