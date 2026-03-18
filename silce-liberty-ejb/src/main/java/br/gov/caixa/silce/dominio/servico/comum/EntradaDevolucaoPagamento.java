package br.gov.caixa.silce.dominio.servico.comum;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.entidade.Operacao;
import br.gov.caixa.util.Decimal;

public class EntradaDevolucaoPagamento implements EntradaHttp<RetornoDevolucaoPagamento> {

	private static final long serialVersionUID = 1L;

	private final Compra compra;
	private final Decimal valorParaDevolver;
	private final Operacao.OperacaoEnum operacao;
	private String idPagamento;
	
	// Devolução Apostas Recarga Pay
	public EntradaDevolucaoPagamento(String idPagamento, Decimal valorParaDevolver) {
		super();
		this.idPagamento = idPagamento;
		this.compra = null;
		this.valorParaDevolver = valorParaDevolver;
		this.operacao = Operacao.OperacaoEnum.DEVOLUCAO_APOSTA;
	}

	// Devolução Apostas PIX
	public EntradaDevolucaoPagamento(Decimal valorParaDevolver) {
		super();
		this.compra = null;
		this.valorParaDevolver = valorParaDevolver;
		this.operacao = Operacao.OperacaoEnum.DEVOLUCAO_APOSTA;
	}

	// Devolução Apostas Mercado Pagao
	public EntradaDevolucaoPagamento(Compra compra, Decimal valorParaDevolver) {
		super();
		this.compra = compra;
		this.valorParaDevolver = valorParaDevolver;
		this.operacao = Operacao.OperacaoEnum.DEVOLUCAO_APOSTA;
	}

	// Devolução Compra Mercado Pago e PIX
	public EntradaDevolucaoPagamento(Compra compra) {
		super();
		this.compra = compra;
		this.valorParaDevolver = compra.getValorTotal();
		this.operacao = Operacao.OperacaoEnum.ESTORNO_COMPRA;
	}

	// Devolução Compra Recarga Pay
	public EntradaDevolucaoPagamento(Compra compra, String idPagamento) {
		super();
		this.compra = compra;
		this.idPagamento = idPagamento;
		this.valorParaDevolver = compra.getValorTotal();
		this.operacao = Operacao.OperacaoEnum.ESTORNO_COMPRA;
	}

	public Decimal getValorParaDevolver() {
		return valorParaDevolver;
	}

	public Operacao.OperacaoEnum getOperacao() {
		return operacao;
	}

	public Compra getCompra() {
		return compra;
	}

	public String getIdPagamento() {
		return idPagamento;
	}
	
}
