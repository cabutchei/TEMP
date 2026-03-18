package br.gov.caixa.silce.dominio.temporizador;

import java.io.Serializable;

/**
 * Domínio para mapear as Propriedades dos Eventos.
 * 
 * @author c101486
 */
public class PropriedadeEvento implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer codigoPropriedadeEvento;
	private TipoEvento tipoEvento;
	private String valorPropriedadeEvento;

	public Integer getCodigoPropriedadeEvento() {
		return codigoPropriedadeEvento;
	}

	public void setCodigoPropriedadeEvento(Integer codigoPropriedadeEvento) {
		this.codigoPropriedadeEvento = codigoPropriedadeEvento;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getValorPropriedadeEvento() {
		return valorPropriedadeEvento;
	}

	public void setValorPropriedadeEvento(String valorPropriedadeEvento) {
		this.valorPropriedadeEvento = valorPropriedadeEvento;
	}

}
