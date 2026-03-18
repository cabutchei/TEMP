package br.gov.caixa.silce.dominio.servico.comum;

import br.gov.caixa.silce.dominio.entidade.Operacao;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

public class RetornoDevolucaoPagamento extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private String idDevolucaoMp;
	private Long idPagamentoMp;
	private Decimal valorDevolvido;
	private Operacao.OperacaoEnum operacao;
	private Long nsuCompra;
	private Long nsuAposta;
	private Data dataCompra;
	private Data dataAposta;
	private Data dataCriacaoDevolucao;
	private StatusPagamento statusPagamento;
	private String statusDetail;

	public RetornoDevolucaoPagamento() {

	}

	public RetornoDevolucaoPagamento(String idDevolucaoMp) {
		super();
		this.idDevolucaoMp = idDevolucaoMp;
	}

	public RetornoDevolucaoPagamento(RetornoErro retornoErro) {
		super(retornoErro);
	}
	
	public RetornoDevolucaoPagamento(String idDevolucaoMp, StatusPagamento statusPagamento) {
		this.idDevolucaoMp = idDevolucaoMp;
		this.statusPagamento = statusPagamento;
	}

	public RetornoDevolucaoPagamento(String idDevolucaoMp, StatusPagamento statusPagamento, Decimal valorDevolvido, String motivo) {
		this.idDevolucaoMp = idDevolucaoMp;
		this.statusPagamento = statusPagamento;
		this.valorDevolvido = valorDevolvido;
		this.statusDetail = motivo;
	}
	public RetornoDevolucaoPagamento(String idDevolucaoMp, Long idPagamentoMp, Decimal valorDevolvido, Operacao.OperacaoEnum operacaoEnum,
			Long nsuCompra, Long nsuAposta, Data dataCompra, Data dataAposta, Data dataCriacaoDevolucao) {
		super();
		this.idDevolucaoMp = idDevolucaoMp;
		this.idPagamentoMp = idPagamentoMp;
		this.valorDevolvido = valorDevolvido;
		this.operacao = operacaoEnum;
		this.nsuCompra = nsuCompra;
		this.nsuAposta = nsuAposta;
		this.dataCompra = dataCompra;
		this.dataAposta = dataAposta;
		this.dataCriacaoDevolucao = dataCriacaoDevolucao;
	}

	public String getIdDevolucaoMp() {
		return idDevolucaoMp;
	}

	public Long getIdPagamentoMp() {
		return idPagamentoMp;
	}

	public Decimal getValorDevolvido() {
		return valorDevolvido;
	}

	public Operacao.OperacaoEnum getOperacao() {
		return operacao;
	}

	public Long getNsuCompra() {
		return nsuCompra;
	}

	public Long getNsuAposta() {
		return nsuAposta;
	}

	public Data getDataCriacaoDevolucao() {
		return dataCriacaoDevolucao;
	}

	public Data getDataCompra() {
		return dataCompra;
	}

	public Data getDataAposta() {
		return dataAposta;
	}

	public StatusPagamento getStatusPagamento() {
		return statusPagamento;
	}

	public String getStatusDetail() {
		return statusDetail;
	}

	@Override
	public String getNsuTransacaoMp() {
		return idDevolucaoMp == null ? null : idDevolucaoMp.toString();
	}

}
