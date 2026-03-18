package br.gov.caixa.silce.dominio.temporizador;

import br.gov.caixa.dominio.EntradaBroker;
import br.gov.caixa.util.Data;

/**
 * Domínio para mapear as Tarefas que foram executadas em reação ao lançamento de determinados Eventos.
 * 
 * @author c101486
 */
public class Tarefa implements EntradaBroker<Evento> {

	private static final long serialVersionUID = 1L;
	
	private Evento evento;
	private String nomeSistema;
	private String nomePrograma;
	private Data dataInicioTarefa;
	private Data dataFinalizacaoTarefa;
	private String codigoRetornoTarefa;
	private String descricaoRetornoTarefa;

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public String getNomeSistema() {
		return nomeSistema;
	}

	public void setNomeSistema(String nomeSistema) {
		this.nomeSistema = nomeSistema;
	}

	public String getNomePrograma() {
		return nomePrograma;
	}

	public void setNomePrograma(String nomePrograma) {
		this.nomePrograma = nomePrograma;
	}

	public Data getDataInicioTarefa() {
		return dataInicioTarefa;
	}

	public void setDataInicioTarefa(Data dataInicioTarefa) {
		this.dataInicioTarefa = dataInicioTarefa;
	}

	public Data getDataFinalizacaoTarefa() {
		return dataFinalizacaoTarefa;
	}

	public void setDataFinalizacaoTarefa(Data dataFinalizacaoTarefa) {
		this.dataFinalizacaoTarefa = dataFinalizacaoTarefa;
	}

	public String getCodigoRetornoTarefa() {
		return codigoRetornoTarefa;
	}

	public void setCodigoRetornoTarefa(String codigoRetornoTarefa) {
		this.codigoRetornoTarefa = codigoRetornoTarefa;
	}

	public String getDescricaoRetornoTarefa() {
		return descricaoRetornoTarefa;
	}

	public void setDescricaoRetornoTarefa(String descricaoRetornoTarefa) {
		this.descricaoRetornoTarefa = descricaoRetornoTarefa;
	}

}
