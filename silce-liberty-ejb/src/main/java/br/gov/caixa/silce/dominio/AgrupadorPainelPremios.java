package br.gov.caixa.silce.dominio;

import java.io.Serializable;

import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.Decimal;

public class AgrupadorPainelPremios implements Serializable {

	private static final long serialVersionUID = 1L;

	private Modalidade modalidade;
	private Meio meio;
	private Integer concurso;
	private Long qtdPremio;
	private Subcanal subcanal;
	private Decimal valorTotalPremio;
	private Decimal valorMedioPremio;
	private Canal canalPagamento;
	private Integer cotaReservada;
	private Integer cotaTotalBolao;

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Meio getMeio() {
		return meio;
	}

	public void setMeio(Meio meio) {
		this.meio = meio;
	}

	public Integer getConcurso() {
		return concurso;
	}

	public void setConcurso(Integer concurso) {
		this.concurso = concurso;
	}

	public Subcanal getSubcanal() {
		return subcanal;
	}

	public void setSubcanal(Subcanal subcanal) {
		this.subcanal = subcanal;
	}

	public Long getQtdPremio() {
		return qtdPremio;
	}

	public void setQtdPremio(Long qtdPremio) {
		this.qtdPremio = qtdPremio;
	}

	public Decimal getValorTotalPremio() {
		return valorTotalPremio;
	}

	public void setValorTotalPremio(Decimal valorTotalPremio) {
		this.valorTotalPremio = valorTotalPremio;
	}

	public Decimal getValorMedioPremio() {
		return valorMedioPremio;
	}

	public void setValorMedioPremio(Decimal valorMedioPremio) {
		this.valorMedioPremio = valorMedioPremio;
	}

	public Canal getCanalPagamento() {
		return canalPagamento;
	}

	public void setCanalPagamento(Canal canalPagamento) {
		this.canalPagamento = canalPagamento;
	}

	public Integer getCotaReservada() {
		return cotaReservada;
	}

	public void setCotaReservada(Integer cotaReservada) {
		this.cotaReservada = cotaReservada;
	}

	public Integer getCotaTotalBolao() {
		return cotaTotalBolao;
	}

	public void setCotaTotalBolao(Integer cotaTotalBolao) {
		this.cotaTotalBolao = cotaTotalBolao;
	}

	public String getCota() {
		return (this.getCotaReservada() == null) ? "1/1"
			: this.getCotaReservada().toString() + "/" + this.getCotaTotalBolao().toString();
	}

}
