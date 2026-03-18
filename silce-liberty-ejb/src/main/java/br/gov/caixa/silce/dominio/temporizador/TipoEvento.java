package br.gov.caixa.silce.dominio.temporizador;

import java.io.Serializable;

/**
 * Domínio para mapear os Tipos e os Critérios para lançamento dos Eventos do Sistema
 * 
 * @author c101486
 */
public class TipoEvento implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer codigoTipoEvento;
	private String nomeTipoEvento;
	private String descricaoTipoEvento;

	public Integer getCodigoTipoEvento() {
		return codigoTipoEvento;
	}

	public void setCodigoTipoEvento(Integer codigoTipoEvento) {
		this.codigoTipoEvento = codigoTipoEvento;
	}

	public String getNomeTipoEvento() {
		return nomeTipoEvento;
	}

	public void setNomeTipoEvento(String nomeTipoEvento) {
		this.nomeTipoEvento = nomeTipoEvento;
	}

	public String getDescricaoTipoEvento() {
		return descricaoTipoEvento;
	}

	public void setDescricaoTipoEvento(String descricaoTipoEvento) {
		this.descricaoTipoEvento = descricaoTipoEvento;
	}


}
