package br.gov.caixa.silce.dominio;

import java.math.BigDecimal;
import java.sql.Date;

/*
 * Classe criada para ser o retorno de uma native query
 */
public class DadosDetalhe extends DadosDetalhamento {

	private Date data;

	public DadosDetalhe() {
		super();
	}

	public DadosDetalhe(Date data, String meio, String tipoOperacao, Long qtdOperacoes, BigDecimal valorBruto, BigDecimal valorComissao, BigDecimal valorComissaoAjustada,
		BigDecimal valorCota, BigDecimal valorTarifa, BigDecimal valorCotaCusteio, BigDecimal valorTarifaCusteio) {
		super();
		this.data = data;
		this.meio = meio;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DadosDetalhe other = (DadosDetalhe) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DadosDetalhamentoData [data=" + data + "]";
	}

}
