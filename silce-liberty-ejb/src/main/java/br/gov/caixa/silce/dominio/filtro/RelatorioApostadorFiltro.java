package br.gov.caixa.silce.dominio.filtro;

import java.io.Serializable;

import br.gov.caixa.util.Data;

public class RelatorioApostadorFiltro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String cpf;
	private Integer modalidade;
	private Integer tipoConcurso;
	private Integer numeroConcurso;
	private Data dtInicio;
	private Data dtFim;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Integer getModalidade() {
		return modalidade;
	}
	public void setModalidade(Integer modalidade) {
		this.modalidade = modalidade;
	}
	public Integer getTipoConcurso() {
		return tipoConcurso;
	}
	public void setTipoConcurso(Integer tipoConcurso) {
		this.tipoConcurso = tipoConcurso;
	}
	public Integer getNumeroConcurso() {
		return numeroConcurso;
	}
	public void setNumeroConcurso(Integer numeroConcurso) {
		this.numeroConcurso = numeroConcurso;
	}
	public Data getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(Data dtInicio) {
		this.dtInicio = dtInicio;
	}
	public Data getDtFim() {
		return dtFim;
	}
	public void setDtFim(Data dtFim) {
		this.dtFim = dtFim;
	}
}
