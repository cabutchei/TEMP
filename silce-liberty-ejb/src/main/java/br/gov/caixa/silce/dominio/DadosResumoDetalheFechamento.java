package br.gov.caixa.silce.dominio;

import java.math.BigDecimal;

/*
 * Classe criada para ser o retorno de uma native query
 */
public class DadosResumoDetalheFechamento {

	private BigDecimal valorCotaAgrupado;
	private BigDecimal valorTarifaCotaAgrupado;
	private BigDecimal valorCusteioCotaAgrupado;
	private BigDecimal valorTarifaCusteioCotaAgrupado;
	private Long loterica;
	private Integer tipoOperacao;

	public DadosResumoDetalheFechamento() {
		super();
	}

	public DadosResumoDetalheFechamento(BigDecimal valorCotaAgrupado, BigDecimal valorTarifaCotaAgrupado, BigDecimal valorCusteioCotaAgrupado,
		BigDecimal valorTarifaCusteioCotaAgrupado, Long loterica, Integer tipoOperacao) {
		super();
		this.valorCotaAgrupado = valorCotaAgrupado;
		this.valorTarifaCotaAgrupado = valorTarifaCotaAgrupado;
		this.valorCusteioCotaAgrupado = valorCusteioCotaAgrupado;
		this.valorTarifaCusteioCotaAgrupado = valorTarifaCusteioCotaAgrupado;
		this.loterica = loterica;
		this.tipoOperacao = tipoOperacao;
	}

	public BigDecimal getValorCotaAgrupado() {
		return valorCotaAgrupado;
	}

	public void setValorCotaAgrupado(BigDecimal valorCotaAgrupado) {
		this.valorCotaAgrupado = valorCotaAgrupado;
	}

	public BigDecimal getValorTarifaCotaAgrupado() {
		return valorTarifaCotaAgrupado;
	}

	public void setValorTarifaCotaAgrupado(BigDecimal valorTarifaCotaAgrupado) {
		this.valorTarifaCotaAgrupado = valorTarifaCotaAgrupado;
	}

	public BigDecimal getValorCusteioCotaAgrupado() {
		return valorCusteioCotaAgrupado;
	}

	public void setValorCusteioCotaAgrupado(BigDecimal valorCusteioCotaAgrupado) {
		this.valorCusteioCotaAgrupado = valorCusteioCotaAgrupado;
	}

	public BigDecimal getValorTarifaCusteioCotaAgrupado() {
		return valorTarifaCusteioCotaAgrupado;
	}

	public void setValorTarifaCusteioCotaAgrupado(BigDecimal valorTarifaCusteioCotaAgrupado) {
		this.valorTarifaCusteioCotaAgrupado = valorTarifaCusteioCotaAgrupado;
	}

	public Long getLoterica() {
		return loterica;
	}

	public void setLoterica(Long loterica) {
		this.loterica = loterica;
	}

	public Integer getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(Integer tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valorCotaAgrupado == null) ? 0 : valorCotaAgrupado.hashCode());
		result = prime * result + ((valorTarifaCotaAgrupado == null) ? 0 : valorTarifaCotaAgrupado.hashCode());
		result = prime * result + ((valorCusteioCotaAgrupado == null) ? 0 : valorCusteioCotaAgrupado.hashCode());
		result = prime * result + ((valorTarifaCusteioCotaAgrupado == null) ? 0 : valorTarifaCusteioCotaAgrupado.hashCode());
		result = prime * result + ((loterica == null) ? 0 : loterica.hashCode());
		result = prime * result + ((tipoOperacao == null) ? 0 : tipoOperacao.hashCode());
		return result;
	}

}
