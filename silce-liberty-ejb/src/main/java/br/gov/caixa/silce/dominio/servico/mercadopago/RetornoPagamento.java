package br.gov.caixa.silce.dominio.servico.mercadopago;

import java.util.List;

import br.gov.caixa.silce.dominio.entidade.Operacao;
import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoDevolucaoPagamento;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;
import br.gov.caixa.silce.dominio.servico.comum.StatusPagamento;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Email;

public class RetornoPagamento extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private Long idPagamentoMp;
	private Data dataCriacao;
	private Data dataAprovacao;
	private Data dataUltimaAtualizacao;
	private StatusPagamento statusPagamento;
	private String statusDetail;
	private CPF cpfApostador;
	private Email emailApostador;
	private Long nsuEntidade;
	private Data dataEntidade;
	private Operacao.OperacaoEnum operacaoEnum;
	private Decimal valor;
	private Decimal valorDevolvido;
	private List<RetornoDevolucaoPagamento> devolucoes;

	private CPF cardholderCPF;

	public RetornoPagamento(StatusPagamento statusPagamento) {
		super();
		this.statusPagamento = statusPagamento;
	}

	public RetornoPagamento(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public RetornoPagamento(Long idPagamentoMp, Data dataCriacao, Data dataAprovacao, Data dataUltimaAtualizacao,
		StatusPagamento statusPagamento, String statusDetail, CPF cpfApostador, Email emailApostador, Decimal valor,
			Decimal valorDevolvido, Long nsuEntidade, Data dataEntidade, Operacao.OperacaoEnum operacaoEnum,
		List<RetornoDevolucaoPagamento> devolucoes, CPF cardholderCPF) {
		super();
		this.idPagamentoMp = idPagamentoMp;
		this.dataCriacao = dataCriacao;
		this.dataAprovacao = dataAprovacao;
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
		this.statusPagamento = statusPagamento;
		this.statusDetail = statusDetail;
		this.cpfApostador = cpfApostador;
		this.emailApostador = emailApostador;
		this.valor = valor;
		this.valorDevolvido = valorDevolvido;
		this.devolucoes = devolucoes;
		this.nsuEntidade = nsuEntidade;
		this.dataEntidade = dataEntidade;
		this.operacaoEnum = operacaoEnum;
		this.cardholderCPF = cardholderCPF;
	}

	public Long getIdPagamentoMp() {
		return idPagamentoMp;
	}

	public Data getDataCriacao() {
		return dataCriacao;
	}

	public Data getDataAprovacao() {
		return dataAprovacao;
	}

	public Data getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public StatusPagamento getStatusPagamento() {
		return statusPagamento;
	}

	public CPF getCpfApostador() {
		return cpfApostador;
	}

	public Email getEmailApostador() {
		return emailApostador;
	}

	public Decimal getValor() {
		return valor;
	}

	public Decimal getValorDevolvido() {
		return valorDevolvido;
	}

	public List<RetornoDevolucaoPagamento> getDevolucoes() {
		return devolucoes;
	}

	public String getStatusDetail() {
		return statusDetail;
	}

	public Long getNsuEntidade() {
		return nsuEntidade;
	}

	public Data getDataEntidade() {
		return dataEntidade;
	}

	public Operacao.OperacaoEnum getOperacaoEnum() {
		return operacaoEnum;
	}

	@Override
	public String getNsuTransacaoMp() {
		return idPagamentoMp == null ? null : idPagamentoMp.toString();
	}

	/**
	 * @return the cardholderCPF
	 */
	public CPF getCardholderCPF() {
		return cardholderCPF;
	}



}
