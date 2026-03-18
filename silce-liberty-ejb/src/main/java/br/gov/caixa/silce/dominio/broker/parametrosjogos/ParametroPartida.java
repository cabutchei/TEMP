package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.io.Serializable;

public class ParametroPartida implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer numero;
	
	private ParametroEquipe equipe1;
	
	private ParametroEquipe equipe2;

	private String descricaoLegenda;

	private String letraLegenda;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public ParametroEquipe getEquipe1() {
		return equipe1;
	}

	public void setEquipe1(ParametroEquipe equipe1) {
		this.equipe1 = equipe1;
	}

	public ParametroEquipe getEquipe2() {
		return equipe2;
	}

	public void setEquipe2(ParametroEquipe equipe2) {
		this.equipe2 = equipe2;
	}

	public String getDescricaoLegenda() {
		return descricaoLegenda;
	}

	public void setDescricaoLegenda(String descricaoLegenda) {
		this.descricaoLegenda = descricaoLegenda;
	}

	public String getLetraLegenda() {
		return letraLegenda;
	}

	public void setLetraLegenda(String letraLegenda) {
		this.letraLegenda = letraLegenda;
	}

}
