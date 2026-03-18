package br.gov.caixa.silce.dominio.broker;

import java.util.List;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.silce.dominio.Canal;
import br.gov.caixa.silce.dominio.NSB;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;


/**
 * @author c101482
 *
 */
public class RetornoDetalhamentoPremio extends SaidaBroker {

	private static final long serialVersionUID = 1L;
	
	//Colocado integer para não precisar alterar o enum sempre que o sispl crie um novo canal
	private Integer numeroCanal;
	
	private String descricaoLocalPagamento;
	
	private Long nsuTransacaoPagamento;
	
	private Long concursoInicial;
	
	private Data dataPagamentoPremio;
	
	private Hora horaPagamentoPremio;
	
	private Decimal valorPremio;
	private Decimal valorBruto;
	private Decimal valorLiquido;
	
	private Decimal valorIRRF;
	
	private NSB nsbTroca;
	
	private NSB nsb;
	
	private List<RetornoDetalhamentoConcursoPremiado> concursosPremiados;
	
	
	public boolean isPagoSILCE() {
		return numeroCanal.equals(Canal.SILCE.getValue());
	}

	public Integer getNumeroCanal() {
		return numeroCanal;
	}

	public void setNumeroCanal(Integer numeroCanal) {
		this.numeroCanal = numeroCanal;
	}

	public String getDescricaoLocalPagamento() {
		return descricaoLocalPagamento;
	}

	public void setDescricaoLocalPagamento(String descricaoLocalPagamento) {
		this.descricaoLocalPagamento = descricaoLocalPagamento;
	}

	public Long getNsuTransacaoPagamento() {
		return nsuTransacaoPagamento;
	}

	public void setNsuTransacaoPagamento(Long nsuTransacaoPagamento) {
		this.nsuTransacaoPagamento = nsuTransacaoPagamento;
	}

	public Long getConcursoInicial() {
		return concursoInicial;
	}

	public void setConcursoInicial(Long concursoInicial) {
		this.concursoInicial = concursoInicial;
	}

	public Data getDataPagamentoPremio() {
		return dataPagamentoPremio;
	}

	public void setDataPagamentoPremio(Data dataPagamentoPremio) {
		this.dataPagamentoPremio = dataPagamentoPremio;
	}

	public Hora getHoraPagamentoPremio() {
		return horaPagamentoPremio;
	}

	public void setHoraPagamentoPremio(Hora horaPagamentoPremio) {
		this.horaPagamentoPremio = horaPagamentoPremio;
	}

	public Decimal getValorPremio() {
		return valorPremio;
	}

	public void setValorPremio(Decimal valorPremio) {
		this.valorPremio = valorPremio;
	}

	public Decimal getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(Decimal valorBruto) {
		this.valorBruto = valorBruto;
	}

	public Decimal getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Decimal valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Decimal getValorIRRF() {
		return valorIRRF;
	}

	public void setValorIRRF(Decimal valorIRRF) {
		this.valorIRRF = valorIRRF;
	}

	public NSB getNsbTroca() {
		return nsbTroca;
	}

	public void setNsbTroca(NSB nsbTroca) {
		this.nsbTroca = nsbTroca;
	}

	public NSB getNsb() {
		return nsb;
	}

	public void setNsb(NSB nsb) {
		this.nsb = nsb;
	}

	public List<RetornoDetalhamentoConcursoPremiado> getConcursosPremiados() {
		return concursosPremiados;
	}

	public void setConcursosPremiados(List<RetornoDetalhamentoConcursoPremiado> concursosPremiados) {
		this.concursosPremiados = concursosPremiados;
	}


}
