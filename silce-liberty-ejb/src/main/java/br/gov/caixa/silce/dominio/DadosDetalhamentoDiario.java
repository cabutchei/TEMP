package br.gov.caixa.silce.dominio;

import java.io.Serializable;

import br.gov.caixa.silce.dominio.entidade.Operacao.OperacaoEnum;
import br.gov.caixa.util.Decimal;

public class DadosDetalhamentoDiario implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String meio;
	protected Long codigoOperacao;
	protected String tipoOperacao;
	protected Long qtdOperacoes;
	protected Decimal valorBruto;
	protected Decimal valorComissao;
	protected Decimal valorComissaoAjustada;
	protected Decimal valorCota;
	protected Decimal valorTarifa;
	protected Decimal valorCotaCusteio;
	protected Decimal valorTarifaCusteio;

	public DadosDetalhamentoDiario() {
		super();
	}

	public DadosDetalhamentoDiario(String meio, Long codigoOperacao, String tipoOperacao, Long qtdOperacoes, Decimal valorBruto, Decimal valorComissao,
		Decimal valorComissaoAjustada, Decimal valorCota, Decimal valorTarifa, Decimal valorCotaCusteio, Decimal valorTarifaCusteio) {
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

	public DadosDetalhamentoDiario(DadosDetalhamento dadosDetalhamento) {
		super();
		if (dadosDetalhamento != null) {
			this.meio = dadosDetalhamento.getMeio();
			this.codigoOperacao = dadosDetalhamento.getCodigoOperacao();
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

	public Decimal getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(Decimal valorBruto) {
		this.valorBruto = valorBruto;
	}

	public Decimal getValorComissao() {
		return valorComissao;
	}

	public void setValorComissao(Decimal valorComissao) {
		this.valorComissao = valorComissao;
	}

	public Decimal getValorComissaoAjustada() {
		return valorComissaoAjustada;
	}

	public void setValorComissaoAjustada(Decimal valorComissaoAjustada) {
		this.valorComissaoAjustada = valorComissaoAjustada;
	}

	public Decimal getValorCota() {
		return valorCota;
	}

	public void setValorCota(Decimal valorCota) {
		this.valorCota = valorCota;
	}

	public Decimal getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(Decimal valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public Decimal getValorCotaCusteio() {
		return valorCotaCusteio;
	}

	public void setValorCotaCusteio(Decimal valorCotaCusteio) {
		this.valorCotaCusteio = valorCotaCusteio;
	}

	public Decimal getValorTarifaCusteio() {
		return valorTarifaCusteio;
	}

	public void setValorTarifaCusteio(Decimal valorTarifaCusteio) {
		this.valorTarifaCusteio = valorTarifaCusteio;
	}

	public DadosDetalhamentoDiario getObjetoZerado(OperacaoEnum operacaoEnum, Long meio) {
		DadosDetalhamentoDiario dadosDetalhamentoDiario = new DadosDetalhamentoDiario();

		dadosDetalhamentoDiario.setMeio(getDescricaoMeio(meio));
		dadosDetalhamentoDiario.setCodigoOperacao(operacaoEnum.getValue());
		dadosDetalhamentoDiario.setTipoOperacao(getDescricaoOperacao(operacaoEnum));
		dadosDetalhamentoDiario.setQtdOperacoes(0L);
		dadosDetalhamentoDiario.setValorBruto(Decimal.ZERO);
		dadosDetalhamentoDiario.setValorComissao(Decimal.ZERO);
		dadosDetalhamentoDiario.setValorComissaoAjustada(Decimal.ZERO);
		dadosDetalhamentoDiario.setValorCota(Decimal.ZERO);
		dadosDetalhamentoDiario.setValorTarifa(Decimal.ZERO);
		dadosDetalhamentoDiario.setValorCotaCusteio(Decimal.ZERO);
		dadosDetalhamentoDiario.setValorTarifaCusteio(Decimal.ZERO);

		return dadosDetalhamentoDiario;
	}

	// FIXME Ajustar para melhor forma de se recuperar essas informações
	private String getDescricaoMeio(Long meio) {
		switch (meio.intValue()) {
			case 1:
				return "Mercado Pago";
			case 5:
				return "Recarga Pay";
			case 6:
				return "PIX";
			default:
				return "Meio de pagamento Invalido";
		}
	}

	private String getDescricaoOperacao(OperacaoEnum operacaoEnum) {
		switch (operacaoEnum) {
			case ESTORNO_COMPRA:
				return "Estorno de Compra";
			case DEVOLUCAO_APOSTA:
				return "Devolução de Aposta";
			case PAGAMENTO_COMPRA:
				return "Pagamento de Compra";
			case PAGAMENTO_APOSTA:
				return "Pagamento de Aposta";
			case PAGAMENTO_COTA_CONTABILIZADA:
			case PAGAMENTO_COTA_ENVIADA_SIGEL:
				return "Pagamento de Cota";
			default:
				return "Operação Invalida";
		}
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
		DadosDetalhamentoDiario other = (DadosDetalhamentoDiario) obj;
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
		return "DadosDetalhamentoDiario [meio=" + meio + ", codigoOperacao=" + codigoOperacao + ", tipoOperacao=" + tipoOperacao + ", qtdOperacoes=" + qtdOperacoes
			+ ", valorBruto=" + valorBruto + ", valorComissao=" + valorComissao + ", valorComissaoAjustada=" + valorComissaoAjustada + ", valorCota=" + valorCota + ", valorTarifa="
			+ valorTarifa + ", valorCotaCusteio=" + valorCotaCusteio + ", valorTarifaCusteio=" + valorTarifaCusteio + "]";
	}

}
