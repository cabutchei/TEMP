package br.gov.caixa.silce.dominio.dto;

import br.gov.caixa.silce.dominio.TipoOperacaoFinanceira;
import br.gov.caixa.silce.dominio.entidade.DetalhamentoEnvioFechamento;
import br.gov.caixa.silce.dominio.entidade.Loterica;
import br.gov.caixa.silce.dominio.entidade.MeioPagamento;
import br.gov.caixa.silce.dominio.entidade.MensagemFinanceiraComplemento;
import br.gov.caixa.util.Conta;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

public class LinhaNSGDDTO {
	private final MeioPagamento meioPagamento;
	private final TipoOperacaoFinanceira tipo;
	private final Decimal somaTotal;
	private final Conta conta;
	private final Long operacao;
	private final Data dataEnvio;
	private final String idMensagemFinanceira;
	private final Data dataPrevistaPagamento;
	private final Decimal valorCota;
	private final Decimal valorTarifaCota;
	private final Decimal valorCusteioCota;
	private final Decimal valorTarifaCusteioCota;
	private final Loterica loterica;
	private final String descricao;

	private LinhaNSGDDTO(MeioPagamento meioPagamento, TipoOperacaoFinanceira tipo, Decimal somaTotal, Conta conta, Long operacao, Data dataEnvio,
		String idMensagemFinanceira, Data dataPrevistaPagamento, Decimal valorCota, Decimal valorTarifaCota, Decimal valorCusteioCota, Decimal valorTarifaCusteioCota,
		Loterica loterica, String descricao) {
		this.meioPagamento = meioPagamento;
		this.tipo = tipo;
		this.somaTotal = somaTotal;
		this.conta = conta;
		this.operacao = operacao;
		this.dataEnvio = dataEnvio;
		this.idMensagemFinanceira = idMensagemFinanceira;
		this.dataPrevistaPagamento = dataPrevistaPagamento;
		this.valorCota = valorCota;
		this.valorTarifaCota = valorTarifaCota;
		this.valorCusteioCota = valorCusteioCota;
		this.valorTarifaCusteioCota = valorTarifaCusteioCota;
		this.loterica = loterica;
		this.descricao = descricao;

	}

	// FIXME: Criar um builder, mas como isso só é usado aqui talvez não tenha necessidade
	public static LinhaNSGDDTO buildFrom(DetalhamentoEnvioFechamento msgFinanceira) {
		MeioPagamento meioPagamento = msgFinanceira.getMeioPagamento();
		TipoOperacaoFinanceira tipo = msgFinanceira.getTipo();
		Decimal somaTotal = msgFinanceira.getValor() != null ? msgFinanceira.getValor() : new Decimal(0);
		Decimal valorCota = msgFinanceira.getValorCota() != null ? msgFinanceira.getValorCota() : new Decimal(0);
		Decimal valorTarifaCota = msgFinanceira.getValorTarifaCota() != null ? msgFinanceira.getValorTarifaCota() : new Decimal(0);
		Decimal valorCusteioCota = msgFinanceira.getValorCusteioCota() != null ? msgFinanceira.getValorCusteioCota() : new Decimal(0);
		Decimal valorTarifaCusteioCota = msgFinanceira.getValorTarifaCusteioCota() != null ? msgFinanceira.getValorTarifaCusteioCota() : new Decimal(0);
		Conta conta = null;
		Long operacao = 0L;
		if (meioPagamento != null) {
			if (meioPagamento.getConta() != null) {
				conta = meioPagamento.getConta();
				operacao = Long.valueOf(conta.getOperacao());
			}
		}
		Data dataEnvio = msgFinanceira.getEnvioFechamento().getDataEnvio();
		String idMensagemFinanceira = msgFinanceira.getId().getId().toString();
		Data dataPrevistaPagamento = msgFinanceira.getDataPrevistaPagamento();
		Loterica loterica = msgFinanceira.getLoterica();
		return new LinhaNSGDDTO(meioPagamento, tipo, somaTotal, conta, operacao, dataEnvio, idMensagemFinanceira, dataPrevistaPagamento, valorCota, valorTarifaCota,
			valorCusteioCota, valorTarifaCusteioCota, loterica, "");
	}

	public static LinhaNSGDDTO buildFrom(MensagemFinanceiraComplemento msgFinanceira) {
		MeioPagamento meioPagamento = msgFinanceira.getMeioPagamento();
		TipoOperacaoFinanceira tipo = msgFinanceira.getTipoOperacao();
		Decimal somaTotal = msgFinanceira.getValor() != null ? msgFinanceira.getValor() : new Decimal(0);
		Conta conta = null;
		Long operacao = 0L;
		if (meioPagamento != null) {
			if (meioPagamento.getConta() != null) {
				conta = meioPagamento.getConta();
				operacao = Long.valueOf(conta.getOperacao());
			}
		}
		Data dataEnvio = msgFinanceira.getTsEnvio();
		String idMsgFinanceira = "idDefault";

		Data dataPrevistaPagamento = dataEnvio;
		Loterica loterica = msgFinanceira.getLoterica();
		Decimal valorCota = msgFinanceira.getValorCota() != null ? msgFinanceira.getValorCota() : new Decimal(0);
		Decimal valorTarifaCota = msgFinanceira.getValorTarifaCota() != null ? msgFinanceira.getValorTarifaCota() : new Decimal(0);
		Decimal valorCusteioCota = msgFinanceira.getValorCusteioCota() != null ? msgFinanceira.getValorCusteioCota() : new Decimal(0);
		Decimal valorTarifaCusteioCota = msgFinanceira.getValorTarifaCusteioCota() != null ? msgFinanceira.getValorTarifaCusteioCota() : new Decimal(0);

		if (!msgFinanceira.isPrevia()) {
			idMsgFinanceira = msgFinanceira.getId().toString();
		}

		return new LinhaNSGDDTO(meioPagamento, tipo, somaTotal, conta, operacao, dataEnvio, idMsgFinanceira, dataPrevistaPagamento, valorCota, valorTarifaCota,
			valorCusteioCota, valorTarifaCusteioCota, loterica, msgFinanceira.getJustificativa());
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public TipoOperacaoFinanceira getTipo() {
		return tipo;
	}

	public Decimal getSomaTotal() {
		return somaTotal;
	}

	public Conta getConta() {
		return conta;
	}

	public Long getOperacao() {
		return operacao;
	}

	public Data getDataEnvio() {
		return dataEnvio;
	}

	public String getIdMensagemFinanceira() {
		return idMensagemFinanceira;
	}

	public Data getDataPrevistaPagamento() {
		return dataPrevistaPagamento;
	}

	public Decimal getValorCota() {
		return valorCota;
	}

	public Decimal getValorTarifaCota() {
		return valorTarifaCota;
	}

	public Decimal getValorCusteioCota() {
		return valorCusteioCota;
	}

	public Decimal getValorTarifaCusteioCota() {
		return valorTarifaCusteioCota;
	}

	public Loterica getLoterica() {
		return loterica;
	}

	public String getDescricao() {
		return descricao;
	}
}