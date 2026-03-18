package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.EntradaBroker;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.Apostador;
import br.gov.caixa.silce.dominio.entidade.Loterica;

public class EntradaRegistraAposta implements EntradaBroker<RetornoRegistraAposta> {

	private static final long serialVersionUID = 1L;

	private Aposta<?> aposta;
	
	private Apostador apostador;

	private Loterica loterica;

	public EntradaRegistraAposta(Aposta<?> aposta, Apostador apostador, Loterica loterica) {
		this.aposta = aposta;
		this.apostador = apostador;
		this.loterica = loterica;
	}

	public Aposta<?> getAposta() {
		return aposta;
	}

	public void setAposta(Aposta<?> aposta) {
		this.aposta = aposta;
	}

	public Apostador getApostador() {
		return apostador;
	}

	public void setApostador(Apostador apostador) {
		this.apostador = apostador;
	}

	public Loterica getLoterica() {
		return loterica;
	}

	public void setLoterica(Loterica loterica) {
		this.loterica = loterica;
	}
	
}
