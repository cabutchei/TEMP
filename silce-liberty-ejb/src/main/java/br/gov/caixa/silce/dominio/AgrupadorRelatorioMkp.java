package br.gov.caixa.silce.dominio;

import java.io.Serializable;

import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;

public class AgrupadorRelatorioMkp implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer loterico;
	private Modalidade modalidade;
	private Integer concurso;
	private String data;
	private Meio meio;
	private Decimal valorCota;
	private Decimal valorTarifaCota;
	private Decimal valorCusteioCota;
	private Decimal valorTarifaCusteioCota;
	private Integer agencia;
	private Integer qtdCotas;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String getDataFormatada() {
		if (data == null)
			return "";
		Data dataBase = DataUtil.stringToData(data, "yyyy-MM-dd");
		return DataUtil.dataToString(dataBase, "dd/MM/yyyy");
		
	}

	public Integer getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}
	
	public Integer getLoterico() {
		return loterico;
	}

	public void setLoterico(Integer loterico) {
		this.loterico = loterico;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}
	public Integer getConcurso() {
		return concurso;
	}
	public void setConcurso(Integer concurso) {
		this.concurso = concurso;
	}
	
	public Decimal getValorCota() {
		return valorCota;
	}

	public void setValorCota(Decimal valorCota) {
		this.valorCota = valorCota;
	}

	public Decimal getValorTarifaCota() {
		return valorTarifaCota;
	}

	public void setValorTarifaCota(Decimal valorTarifaCota) {
		this.valorTarifaCota = valorTarifaCota;
	}

	public Decimal getValorCusteioCota() {
		return valorCusteioCota;
	}

	public void setValorCusteioCota(Decimal valorCusteioCota) {
		this.valorCusteioCota = valorCusteioCota;
	}

	public Decimal getValorTarifaCusteioCota() {
		return valorTarifaCusteioCota;
	}

	public void setValorTarifaCusteioCota(Decimal valorTarifaCusteioCota) {
		this.valorTarifaCusteioCota = valorTarifaCusteioCota;
	}

	public Meio getMeio() {
		return meio;
	}

	public void setMeio(Meio meio) {
		this.meio = meio;
	}

	public Integer getQtdCotas() {
		return qtdCotas;
	}

	public void setQtdCotas(Integer qtdCotas) {
		this.qtdCotas = qtdCotas;
	}

	public Decimal getValorTotal() {
		return this.valorCota.add(this.valorTarifaCota);
	}

}
