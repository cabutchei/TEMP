package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.EntradaBroker;
import br.gov.caixa.silce.dominio.Canal;
import br.gov.caixa.silce.dominio.NSB;
import br.gov.caixa.silce.dominio.entidade.Premio;

public class EntradaPagaPremio implements EntradaBroker<RetornoPagaPremio> {

	private static final long serialVersionUID = 1L;
	
	private final NSB nsb;
	private final Canal canal;
	
	private final OperacaoSispl operacao;

	private final Premio premio;

	public EntradaPagaPremio(NSB nsb, Canal canal, OperacaoSispl operacao, Premio premio) {
		this.nsb = nsb;
		this.canal = canal;
		this.operacao = operacao;
		this.premio = premio;
	}
	
	public EntradaPagaPremio(NSB nsb, Canal canal, OperacaoSispl operacao) {
		this(nsb, canal, operacao, null);
	}

	public NSB getNsb() {
		return nsb;
	}

	public Canal getCanal() {
		return canal;
	}

	public OperacaoSispl getOperacao() {
		return operacao;
	}

	public Premio getPremio() {
		return premio;
	}

}
