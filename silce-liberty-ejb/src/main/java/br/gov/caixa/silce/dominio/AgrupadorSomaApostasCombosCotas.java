package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class AgrupadorSomaApostasCombosCotas implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipo;

	private BigDecimal valorTotal;
	
	private String valorTotalString;

	public AgrupadorSomaApostasCombosCotas() {
	}

	public AgrupadorSomaApostasCombosCotas(String tipo, BigDecimal valorTotal) {
		this.tipo = tipo;
		this.valorTotal = valorTotal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public void setValorTotalString(String valorTotalString) {
		this.valorTotalString = valorTotalString;
	}

	public String getValorTotalString() {
		Locale localeBR = new Locale("pt", "BR");
		NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBR);

		return (valorTotal == null) ? "0,0" : dinheiro.format(valorTotal);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valorTotal == null) ? 0 : valorTotal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgrupadorSomaApostasCombosCotas other = (AgrupadorSomaApostasCombosCotas) obj;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (valorTotal == null) {
			if (other.valorTotal != null)
				return false;
		} else if (!valorTotal.equals(other.valorTotal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AgrupadorSomaApostasCombosCotas [tipo=" + tipo + ", valorTotal=" +
			getValorTotalString() + "]";
	}

}

