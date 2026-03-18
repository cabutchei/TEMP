package br.gov.caixa.silce.dominio;

import java.math.BigDecimal;

/*
 * Classe criada para ser o retorno de uma native query
 */
public class DadosDetalhamento {

	protected String meio;
	protected Long codigoOperacao;
	protected String tipoOperacao;
	protected Long qtdOperacoes;
	protected BigDecimal valorBruto;
	protected BigDecimal valorComissao;
	protected BigDecimal valorComissaoAjustada;
	protected BigDecimal valorCota;
	protected BigDecimal valorTarifa;
	protected BigDecimal valorCotaCusteio;
	protected BigDecimal valorTarifaCusteio;

	public DadosDetalhamento() {
		super();
	}

	public DadosDetalhamento(String meio, Long codigoOperacao, String tipoOperacao, Long qtdOperacoes, BigDecimal valorBruto, BigDecimal valorComissao,
		BigDecimal valorComissaoAjustada,
		BigDecimal valorCota, BigDecimal valorTarifa, BigDecimal valorCotaCusteio, BigDecimal valorTarifaCusteio) {
		super();
		this.meio = meio;
		this.codigoOperacao = codigoOperacao;
		this.tipoOperacao = tipoOperacao;
		this.qtdOperacoes = qtdOperacoes;
		this.valorBruto = valorBruto;
		this.valorComissao = valorComissao;
		this.valorComissaoAjustada = valorComissaoAjustada;
		this.valorCota = valorCota;
		this.valorTarifa = valorTarifa;
		this.valorCotaCusteio = valorCotaCusteio;
		this.valorTarifaCusteio = valorTarifaCusteio;
	}

	public String getMeio() {
		return meio;
	}

	public void setMeio(String meio) {
		this.meio = meio;
	}

	public Long getCodigoOperacao() {
		return codigoOperacao;
	}

	public void setCodigoOperacao(Long codigoOperacao) {
		this.codigoOperacao = codigoOperacao;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Long getQtdOperacoes() {
		return qtdOperacoes;
	}

	public void setQtdOperacoes(Long qtdOperacoes) {
		this.qtdOperacoes = qtdOperacoes;
	}

	public BigDecimal getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(BigDecimal valorBruto) {
		this.valorBruto = valorBruto;
	}

	public BigDecimal getValorComissao() {
		return valorComissao;
	}

	public void setValorComissao(BigDecimal valorComissao) {
		this.valorComissao = valorComissao;
	}

	public BigDecimal getValorComissaoAjustada() {
		return valorComissaoAjustada;
	}

	public void setValorComissaoAjustada(BigDecimal valorComissaoAjustada) {
		this.valorComissaoAjustada = valorComissaoAjustada;
	}

	public BigDecimal getValorCota() {
		return valorCota;
	}

	public void setValorCota(BigDecimal valorCota) {
		this.valorCota = valorCota;
	}

	public BigDecimal getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(BigDecimal valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public BigDecimal getValorCotaCusteio() {
		return valorCotaCusteio;
	}

	public void setValorCotaCusteio(BigDecimal valorCotaCusteio) {
		this.valorCotaCusteio = valorCotaCusteio;
	}

	public BigDecimal getValorTarifaCusteio() {
		return valorTarifaCusteio;
	}

	public void setValorTarifaCusteio(BigDecimal valorTarifaCusteio) {
		this.valorTarifaCusteio = valorTarifaCusteio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoOperacao == null) ? 0 : codigoOperacao.hashCode());
		result = prime * result + ((meio == null) ? 0 : meio.hashCode());
		result = prime * result + ((qtdOperacoes == null) ? 0 : qtdOperacoes.hashCode());
		result = prime * result + ((tipoOperacao == null) ? 0 : tipoOperacao.hashCode());
		result = prime * result + ((valorBruto == null) ? 0 : valorBruto.hashCode());
		result = prime * result + ((valorComissao == null) ? 0 : valorComissao.hashCode());
		result = prime * result + ((valorComissaoAjustada == null) ? 0 : valorComissaoAjustada.hashCode());
		result = prime * result + ((valorCota == null) ? 0 : valorCota.hashCode());
		result = prime * result + ((valorCotaCusteio == null) ? 0 : valorCotaCusteio.hashCode());
		result = prime * result + ((valorTarifa == null) ? 0 : valorTarifa.hashCode());
		result = prime * result + ((valorTarifaCusteio == null) ? 0 : valorTarifaCusteio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DadosDetalhamento other = (DadosDetalhamento) obj;
		if (codigoOperacao == null) {
			if (other.codigoOperacao != null)
				return false;
		} else if (!codigoOperacao.equals(other.codigoOperacao))
			return false;
		if (meio == null) {
			if (other.meio != null)
				return false;
		} else if (!meio.equals(other.meio))
			return false;
		if (qtdOperacoes == null) {
			if (other.qtdOperacoes != null)
				return false;
		} else if (!qtdOperacoes.equals(other.qtdOperacoes))
			return false;
		if (tipoOperacao == null) {
			if (other.tipoOperacao != null)
				return false;
		} else if (!tipoOperacao.equals(other.tipoOperacao))
			return false;
		if (valorBruto == null) {
			if (other.valorBruto != null)
				return false;
		} else if (!valorBruto.equals(other.valorBruto))
			return false;
		if (valorComissao == null) {
			if (other.valorComissao != null)
				return false;
		} else if (!valorComissao.equals(other.valorComissao))
			return false;
		if (valorComissaoAjustada == null) {
			if (other.valorComissaoAjustada != null)
				return false;
		} else if (!valorComissaoAjustada.equals(other.valorComissaoAjustada))
			return false;
		if (valorCota == null) {
			if (other.valorCota != null)
				return false;
		} else if (!valorCota.equals(other.valorCota))
			return false;
		if (valorCotaCusteio == null) {
			if (other.valorCotaCusteio != null)
				return false;
		} else if (!valorCotaCusteio.equals(other.valorCotaCusteio))
			return false;
		if (valorTarifa == null) {
			if (other.valorTarifa != null)
				return false;
		} else if (!valorTarifa.equals(other.valorTarifa))
			return false;
		if (valorTarifaCusteio == null) {
			if (other.valorTarifaCusteio != null)
				return false;
		} else if (!valorTarifaCusteio.equals(other.valorTarifaCusteio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DadosDetalhamento [meio=" + meio + ", codigoOperacao=" + codigoOperacao + ", tipoOperacao=" + tipoOperacao + ", qtdOperacoes=" + qtdOperacoes + ", valorBruto="
			+ valorBruto + ", valorComissao=" + valorComissao + ", valorComissaoAjustada=" + valorComissaoAjustada + ", valorCota=" + valorCota + ", valorTarifa=" + valorTarifa
			+ ", valorCotaCusteio=" + valorCotaCusteio + ", valorTarifaCusteio=" + valorTarifaCusteio + "]";
	}

}
