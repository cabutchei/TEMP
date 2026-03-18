package br.gov.caixa.silce.dominio.temporizador;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.util.Data;

/**
 * Um Evento é uma Entidade que é lançada de acordo com Critérios definidos em seu Tipo de Evento e que podem ser
 * tratados, cada um, por diversas Tarefas que podem estar em diferentes Sistemas.
 * 
 * @author c101486
 */
public class Evento extends SaidaBroker {

	private static final long serialVersionUID = 1L;
	
	private Integer codigoEvento;
	private Data dataLancamentoEvento;
	private TipoEvento tipoEvento;

	public Integer getCodigoEvento() {
		return codigoEvento;
	}

	public void setCodigoEvento(Integer codigoEvento) {
		this.codigoEvento = codigoEvento;
	}

	public Data getDataLancamentoEvento() {
		return dataLancamentoEvento;
	}

	public void setDataLancamentoEvento(Data dataLancamentoEvento) {
		this.dataLancamentoEvento = dataLancamentoEvento;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

}
