package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;
import java.util.Map;

import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

public class RepassometroVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Decimal arrecadacaoAcumulada;
	private Decimal arrecadaoUltimaSemana;
	private Data dataBase;
	private Map<String, Decimal> percentuais;


	public RepassometroVO(Decimal arrecadacao, Decimal ultimaSemana, Data base, Map<String, Decimal> percentuais) {
		this.arrecadacaoAcumulada = arrecadacao;
		this.arrecadaoUltimaSemana = ultimaSemana;
		this.dataBase = base;
		this.percentuais = percentuais;
	}

	public Decimal getArrecadacaoAcumulada() {
		return arrecadacaoAcumulada;
	}

	public void setArrecadacaoAcumulada(Decimal arrecadacaoAcumulada) {
		this.arrecadacaoAcumulada = arrecadacaoAcumulada;
	}

	public Data getDataBase() {
		return dataBase;
	}

	public void setDataBase(Data dataBase) {
		this.dataBase = dataBase;
	}

	public Map<String, Decimal> getPercentuais() {
		return percentuais;
	}

	public void setPercentuais(Map<String, Decimal> percentuais) {
		this.percentuais = percentuais;
	}

	public Decimal getArrecadaoUltimaSemana() {
		return arrecadaoUltimaSemana;
	}

	public void setArrecadaoUltimaSemana(Decimal arrecadaoUltimaSemana) {
		this.arrecadaoUltimaSemana = arrecadaoUltimaSemana;
	}
}
