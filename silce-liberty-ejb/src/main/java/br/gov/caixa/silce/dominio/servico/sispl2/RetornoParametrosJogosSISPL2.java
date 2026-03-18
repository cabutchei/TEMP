package br.gov.caixa.silce.dominio.servico.sispl2;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.silce.dominio.broker.parametrosjogos.AbstractParametroJogo;

public class RetornoParametrosJogosSISPL2 extends SaidaHttpSISPL2 {

	private static final long serialVersionUID = 1L;

	public RetornoParametrosJogosSISPL2(List<AbstractParametroJogo> parametros) {
		this.parametros = parametros;
	}

	private List<AbstractParametroJogo> parametros = new ArrayList<AbstractParametroJogo>();

	public List<AbstractParametroJogo> getParametros() {
		return parametros;
	}
}
