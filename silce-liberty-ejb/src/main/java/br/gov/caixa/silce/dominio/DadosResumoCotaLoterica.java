package br.gov.caixa.silce.dominio;

import java.math.BigDecimal;

/*
 * Classe criada para ser o retorno de uma native query
 */
public class DadosResumoCotaLoterica {

	private Long quantidade;
	private BigDecimal valorCota;
	private BigDecimal valorTarifaCota;
	private BigDecimal valorCusteioCota;
	private BigDecimal valorTarifaCusteioCota;
	private Long meioPagamento;
	private Integer modalidade;
	private Integer concurso;
	private BigDecimal valorComissao;
	private Integer subcanalDetalhamento;

	public DadosResumoCotaLoterica() {
		super();
	}

	public DadosResumoCotaLoterica(Long quantidade, BigDecimal valorCota, BigDecimal valorTarifaCota, BigDecimal valorCusteioCota, BigDecimal valorTarifaCusteioCota,
		Long meioPagamento, Integer modalidade, Integer concurso, BigDecimal valorComissao, Integer subcanalDetalhamento) {
		super();
		this.quantidade = quantidade;
		this.valorCota = valorCota;
		this.valorTarifaCota = valorTarifaCota;
		this.valorCusteioCota = valorCusteioCota;
		this.valorTarifaCusteioCota = valorTarifaCusteioCota;
		this.meioPagamento = meioPagamento;
		this.modalidade = modalidade;
		this.concurso = concurso;
		this.valorComissao = valorComissao;
		this.subcanalDetalhamento = subcanalDetalhamento;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorCota() {
		return valorCota;
	}

	public void setValorCota(BigDecimal valorCota) {
		this.valorCota = valorCota;
	}

	public BigDecimal getValorTarifaCota() {
		return valorTarifaCota;
	}

	public void setValorTarifaCota(BigDecimal valorTarifaCota) {
		this.valorTarifaCota = valorTarifaCota;
	}

	public Long getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(Long meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	public Integer getModalidade() {
		return modalidade;
	}

	public void setModalidade(Integer modalidade) {
		this.modalidade = modalidade;
	}

	public Integer getConcurso() {
		return concurso;
	}

	public void setConcurso(Integer concurso) {
		this.concurso = concurso;
	}

	public BigDecimal getValorComissao() {
		return valorComissao;
	}

	public void setValorComissao(BigDecimal valorComissao) {
		this.valorComissao = valorComissao;
	}

	public Integer getSubcanalDetalhamento() {
		return subcanalDetalhamento;
	}

	public void setSubcanalDetalhamento(Integer subcanalDetalhamento) {
		this.subcanalDetalhamento = subcanalDetalhamento;
	}

	public BigDecimal getValorCusteioCota() {
		return valorCusteioCota;
	}

	public void setValorCusteioCota(BigDecimal valorCusteioCota) {
		this.valorCusteioCota = valorCusteioCota;
	}

	public BigDecimal getValorTarifaCusteioCota() {
		return valorTarifaCusteioCota;
	}

	public void setValorTarifaCusteioCota(BigDecimal valorTarifaCusteioCota) {
		this.valorTarifaCusteioCota = valorTarifaCusteioCota;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concurso == null) ? 0 : concurso.hashCode());
		result = prime * result + ((meioPagamento == null) ? 0 : meioPagamento.hashCode());
		result = prime * result + ((modalidade == null) ? 0 : modalidade.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((subcanalDetalhamento == null) ? 0 : subcanalDetalhamento.hashCode());
		result = prime * result + ((valorComissao == null) ? 0 : valorComissao.hashCode());
		result = prime * result + ((valorCota == null) ? 0 : valorCota.hashCode());
		result = prime * result + ((valorCusteioCota == null) ? 0 : valorCusteioCota.hashCode());
		result = prime * result + ((valorTarifaCota == null) ? 0 : valorTarifaCota.hashCode());
		result = prime * result + ((valorTarifaCusteioCota == null) ? 0 : valorTarifaCusteioCota.hashCode());
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
		DadosResumoCotaLoterica other = (DadosResumoCotaLoterica) obj;
		if (concurso == null) {
			if (other.concurso != null)
				return false;
		} else if (!concurso.equals(other.concurso))
			return false;
		if (meioPagamento == null) {
			if (other.meioPagamento != null)
				return false;
		} else if (!meioPagamento.equals(other.meioPagamento))
			return false;
		if (modalidade != other.modalidade)
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		if (subcanalDetalhamento != other.subcanalDetalhamento)
			return false;
		if (valorComissao == null) {
			if (other.valorComissao != null)
				return false;
		} else if (!valorComissao.equals(other.valorComissao))
			return false;
		if (valorCota == null) {
			if (other.valorCota != null)
				return false;
		} else if (!valorCota.equals(other.valorCota))
			return false;
		if (valorCusteioCota == null) {
			if (other.valorCusteioCota != null)
				return false;
		} else if (!valorCusteioCota.equals(other.valorCusteioCota))
			return false;
		if (valorTarifaCota == null) {
			if (other.valorTarifaCota != null)
				return false;
		} else if (!valorTarifaCota.equals(other.valorTarifaCota))
			return false;
		if (valorTarifaCusteioCota == null) {
			if (other.valorTarifaCusteioCota != null)
				return false;
		} else if (!valorTarifaCusteioCota.equals(other.valorTarifaCusteioCota))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DetalheFechamentoCotas [quantidade=" + quantidade + ", valorCota=" + valorCota + ", valorTarifaCota=" + valorTarifaCota + ", valorCotaCusteio=" + valorCusteioCota
			+ ", valorTarifaCotaCusteio=" + valorTarifaCusteioCota + ", meioPagamento=" + meioPagamento + ", modalidade=" + modalidade + ", concurso=" + concurso
			+ ", valorComissao=" + valorComissao + ", subcanalDetalhamento=" + subcanalDetalhamento + "]";
	}

}
