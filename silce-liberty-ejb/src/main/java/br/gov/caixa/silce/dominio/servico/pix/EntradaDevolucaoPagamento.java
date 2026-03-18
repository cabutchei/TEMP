package br.gov.caixa.silce.dominio.servico.pix;

import br.gov.caixa.dominio.EntradaHttp;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.entidade.Operacao;
import br.gov.caixa.util.Decimal;

public class EntradaDevolucaoPagamento implements EntradaHttp<RetornoPix> {

	private static final long serialVersionUID = 1L;

	private final Aposta<?> aposta;
	private final Compra compra;
	private final Decimal valorParaDevolver;
	private final Operacao.OperacaoEnum operacao;
	private String idPagamento;

	public EntradaDevolucaoPagamento(Aposta<?> aposta) {
		super();
		this.aposta = aposta;
		this.compra = aposta.getCompra();
		this.valorParaDevolver = aposta.getValor();
		this.operacao = Operacao.OperacaoEnum.DEVOLUCAO_APOSTA;
	}
	
	public EntradaDevolucaoPagamento(Aposta<?> aposta, String idPagamento) {
		super();
		this.aposta = aposta;
		this.idPagamento = idPagamento;
		this.compra = aposta.getCompra();
		this.valorParaDevolver = aposta.getValor();
		this.operacao = Operacao.OperacaoEnum.DEVOLUCAO_APOSTA;
	}

	public EntradaDevolucaoPagamento(Compra compra) {
		super();
		this.aposta = null;
		this.compra = compra;
		this.valorParaDevolver = compra.getValorTotal();
		this.operacao = Operacao.OperacaoEnum.ESTORNO_COMPRA;
	}

	public EntradaDevolucaoPagamento(Compra compra, String idPagamento) {
		super();
		this.aposta = null;
		this.compra = compra;
		this.idPagamento = idPagamento;
		this.valorParaDevolver = compra.getValorTotal();
		this.operacao = Operacao.OperacaoEnum.ESTORNO_COMPRA;
	}

	public Aposta<?> getAposta() {
		return aposta;
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
