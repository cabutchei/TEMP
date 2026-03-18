package br.gov.caixa.silce.dominio;

import java.io.Serializable;

public class AgrupadorTotalizadorRelatorioMkp extends AgrupadorRelatorioMkp implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Subcanal subcanal;

	public Subcanal getSubcanal() {
		return subcanal;
	}

	public void setSubcanal(Subcanal subcanal) {
		this.subcanal = subcanal;
	}
	

}
