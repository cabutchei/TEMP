package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.Apostador;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.CollectionUtil;
import br.gov.caixa.util.Decimal;

public class AgrupadorAposta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<Modalidade, ResultadoPesquisaPaginada<Aposta<?>>> mapa;

	private Long qtdApostaEfetivada;
	private Long qtdApostaNaoEfetivada;
	private Decimal valorTotalApostaRealizada;
	private Apostador apostador;

	public AgrupadorAposta() {
		mapa = new HashMap<Modalidade, ResultadoPesquisaPaginada<Aposta<?>>>();
		for (Modalidade modalidade : Modalidade.values()) {
			mapa.put(modalidade, new ResultadoPesquisaPaginada<Aposta<?>>(0));
		}
	}

	public boolean contemAposta(Modalidade modalidade) {
		return contemAposta(mapa.get(modalidade));
	}

	public int getQtdModalidadesComAposta() {
		int i = 0;
		for (Modalidade modalidade : Modalidade.values()) {
			if (contemAposta(modalidade)) {
				i++;
			}
		}
		return i;
	}
	
	private boolean contemAposta(ResultadoPesquisaPaginada<Aposta<?>> resultadoPesquisaPaginada) {
		return resultadoPesquisaPaginada != null && !CollectionUtil.isVazio(resultadoPesquisaPaginada.getDados());
	}

	public boolean contemApostas() {
		for (Modalidade modalidade : Modalidade.values()) {
			if (contemAposta(modalidade)) {
				return true;
			}
		}
		return false;
	}
	
	public ResultadoPesquisaPaginada<Aposta<?>> getLista(Modalidade modalidade) {
		return mapa.get(modalidade);
	}
	
	public void setLista(Modalidade modalidade, ResultadoPesquisaPaginada<Aposta<?>> lista) {
		mapa.replace(modalidade, lista);
	}

	public Long getQtdApostaEfetivada() {
		return qtdApostaEfetivada;
	}

	public void setQtdApostaEfetivada(Long qtdApostaEfetivada) {
		this.qtdApostaEfetivada = qtdApostaEfetivada;
	}

	public Long getQtdApostaNaoEfetivada() {
		return qtdApostaNaoEfetivada;
	}

	public void setQtdApostaNaoEfetivada(Long qtdApostaNaoEfetivada) {
		this.qtdApostaNaoEfetivada = qtdApostaNaoEfetivada;
	}

	public Decimal getValorTotalApostaRealizada() {
		return valorTotalApostaRealizada;
	}

	public void setValorTotalApostaRealizada(Decimal valorTotalApostaRealizada) {
		this.valorTotalApostaRealizada = valorTotalApostaRealizada;
	}

	public Apostador getApostador() {
		return apostador;
	}

	public void setApostador(Apostador apostador) {
		this.apostador = apostador;
	}

}
