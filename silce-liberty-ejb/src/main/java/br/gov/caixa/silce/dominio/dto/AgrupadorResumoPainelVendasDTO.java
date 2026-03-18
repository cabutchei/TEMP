package br.gov.caixa.silce.dominio.dto;

import java.io.Serializable;

import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.util.Decimal;

public class AgrupadorResumoPainelVendasDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Decimal valorTotal = Decimal.ZERO;
	private long qtdTotal = 0L;
	private Subcanal subcanal;

	private AgrupadorResumoPainelVendasDTO(Subcanal subcanal) {
		this.subcanal = subcanal;
	}

	public static AgrupadorResumoPainelVendasDTO of(Subcanal subcanal) {
		return new AgrupadorResumoPainelVendasDTO(subcanal);
	}

	public Decimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Decimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public long getQtdTotal() {
		return qtdTotal;
	}

	public void setQtdTotal(long qtdTotal) {
		this.qtdTotal = qtdTotal;
	}

	public Subcanal getSubcanal() {
		return subcanal;
	}

	public void setSubcanal(Subcanal subcanal) {
		this.subcanal = subcanal;
	}

}
