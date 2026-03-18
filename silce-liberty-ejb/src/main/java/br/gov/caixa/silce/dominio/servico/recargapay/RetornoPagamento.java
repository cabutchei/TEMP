package br.gov.caixa.silce.dominio.servico.recargapay;

import java.math.BigDecimal;
import java.util.List;

import br.gov.caixa.silce.dominio.entidade.Operacao;
import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;
import br.gov.caixa.silce.dominio.servico.comum.StatusPagamento;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;

public class RetornoPagamento extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private String idTransacao;
	private String idPagamento;
	private BigDecimal valor;
	private String nome;
	private String email;
	private List<String> listaTelefone;
	private CPF cpfApostador;
	private Data dataAniversario;
	private String genero;
	private BigDecimal valorCancelado;
	private StatusPagamento statusPagamento;
	private RetornoCartao dadosCartao;
	private Long nsuCompra;
	private Data dataCompra;
	private Operacao.OperacaoEnum operacaoCompra;
	private String statusDetail;

	public RetornoPagamento() {
		super();
	}

	public RetornoPagamento(String idTransacao, StatusPagamento statusPagamento) {
		super();
		this.idTransacao = idTransacao;
		this.statusPagamento = statusPagamento;
	}

	public RetornoPagamento(String idTransacao, StatusPagamento statusPagamento, CPF cpfApostador) {
		super();
		this.idTransacao = idTransacao;
		this.statusPagamento = statusPagamento;
		this.cpfApostador = cpfApostador;
	}

	public RetornoPagamento(String idTransacao, String idPagamento, BigDecimal valor, String nome, String email, List<String> listaTelefone, CPF cpfApostador,
		Data dataAniversario, BigDecimal valorCancelado, StatusPagamento statusPagamento, RetornoCartao dadosCartao,
		Long nsuCompra, Data dataCompra, Operacao.OperacaoEnum operacaoCompra) {
		super();
		this.idTransacao = idTransacao;
		this.idPagamento = idPagamento;
		this.valor = valor;
		this.nome = nome;
		this.email = email;
		this.listaTelefone = listaTelefone;
		this.cpfApostador = cpfApostador;
		this.dataAniversario = dataAniversario;
		this.valorCancelado = valorCancelado;
		this.statusPagamento = statusPagamento;
		this.dadosCartao = dadosCartao;
		this.nsuCompra = nsuCompra;
		this.dataCompra = dataCompra;
		this.operacaoCompra = operacaoCompra;
	}

	public RetornoPagamento(RetornoPagamento retornoPagamento) {
		super();
		this.idTransacao = retornoPagamento.getIdTransacao();
		this.idPagamento = retornoPagamento.getIdPagamento();
		this.valor = retornoPagamento.getValor();
		this.nome = retornoPagamento.getNome();
		this.email = retornoPagamento.getEmail();
		this.listaTelefone = retornoPagamento.getListaTelefone();
		this.cpfApostador = retornoPagamento.getCpfApostador();
		this.dataAniversario = retornoPagamento.getDataAniversario();
		this.valorCancelado = retornoPagamento.getValorCancelado();
		this.statusPagamento = retornoPagamento.getStatusPagamento();
		this.dadosCartao = retornoPagamento.getDadosCartao();
		this.nsuCompra = retornoPagamento.getNsuCompra();
		this.dataCompra = retornoPagamento.getDataCompra();
		this.operacaoCompra = retornoPagamento.getOperacaoCompra();
	}

	public RetornoPagamento(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public String getIdTransacao() {
		return idTransacao;
	}

	public void setIdTransacao(String idTransacao) {
		this.idTransacao = idTransacao;
	}

	public StatusPagamento getStatusPagamento() {
		return statusPagamento;
	}

	public void setStatusPagamento(StatusPagamento statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	public CPF getCpfApostador() {
		return cpfApostador;
	}

	public void setCpfApostador(CPF cpfApostador) {
		this.cpfApostador = cpfApostador;
	}

	@Override
	public String getNsuTransacaoMp() {
		return idTransacao == null ? null : idTransacao.toString();
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getListaTelefone() {
		return listaTelefone;
	}

	public void setListaTelefone(List<String> listaTelefone) {
		this.listaTelefone = listaTelefone;
	}

	public Data getDataAniversario() {
		return dataAniversario;
	}

	public void setDataAniversario(Data dataAniversario) {
		this.dataAniversario = dataAniversario;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public BigDecimal getValorCancelado() {
		return valorCancelado;
	}

	public void setValorCancelado(BigDecimal valorCancelado) {
		this.valorCancelado = valorCancelado;
	}

	public RetornoCartao getDadosCartao() {
		return dadosCartao;
	}

	public void setDadosCartao(RetornoCartao dadosCartao) {
		this.dadosCartao = dadosCartao;
	}

	public Long getNsuCompra() {
		return nsuCompra;
	}

	public void setNsuCompra(Long nsuCompra) {
		this.nsuCompra = nsuCompra;
	}

	public Data getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Data dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Operacao.OperacaoEnum getOperacaoCompra() {
		return operacaoCompra;
	}

	public void setOperacaoCompra(Operacao.OperacaoEnum operacaoCompra) {
		this.operacaoCompra = operacaoCompra;
	}

	public String getStatusDetail() {
		return statusDetail;
	}

	public void setStatusDetail(String statusDetail) {
		this.statusDetail = statusDetail;
	}

	public String getIdPagamento() {
		return idPagamento;
	}
}
