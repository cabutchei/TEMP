package br.gov.caixa.silce.dominio;

import java.io.Serializable;

import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

public class DadosDetalheDiario extends DadosDetalhamentoDiario implements Serializable {

	private static final long serialVersionUID = 1L;

	private Data data;

	public DadosDetalheDiario() {
		super();
	}

	public DadosDetalheDiario(Data data, String meio, String tipoOperacao, Long qtdOperacoes, Decimal valorBruto, Decimal valorComissao, Decimal valorComissaoAjustada,
		Decimal valorCota, Decimal valorTarifa, Decimal valorCotaCusteio, Decimal valorTarifaCusteio) {
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

	public DadosDetalheDiario(DadosDetalhe dadosDetalhamento) {
		super();
		if (dadosDetalhamento != null) {
			this.data = new Data(dadosDetalhamento.getData());
			this.meio = dadosDetalhamento.getMeio();
			this.tipoOperacao = dadosDetalhamento.getTipoOperacao();
			this.qtdOperacoes = dadosDetalhamento.getQtdOperacoes();
			this.valorBruto = new Decimal(dadosDetalhamento.getValorBruto());
			this.valorComissao = new Decimal(dadosDetalhamento.getValorComissao());
			this.valorComissaoAjustada = new Decimal(dadosDetalhamento.getValorComissaoAjustada());
			this.valorCota = new Decimal(dadosDetalhamento.getValorCota());
			this.valorTarifa = new Decimal(dadosDetalhamento.getValorTarifa());
			this.valorCotaCusteio = new Decimal(dadosDetalhamento.getValorCotaCusteio());
			this.valorTarifaCusteio = new Decimal(dadosDetalhamento.getValorTarifaCusteio());
		}
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
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
		DadosDetalheDiario other = (DadosDetalheDiario) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DadosDetalheDiario [data=" + data + "]";
	}

}
