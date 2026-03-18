package br.gov.caixa.silce.dominio.servico.pix;

import java.io.Serializable;

import br.gov.caixa.silce.dominio.ExternalReferenceUtil;
import br.gov.caixa.silce.dominio.servico.apimanager.SaidaHttpAPIManager;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;

public final class RetornoConfirmaPagamento extends SaidaHttpAPIManager implements Serializable {

	private static final int POSICAO_FIM_DT_COMPRA = 21;

	private static final int POSICAO_INICIO_DT_COMPRA = 13;

	private static final long serialVersionUID = 1L;

	private String prioridadePagamento;
	private String tipoPrioridadePagamento;
	private String idFimAFim;
	private String txid;
	private String valor;
	private String dataHoraRecebimento;
	private String formaIniciacao;
	private String cnpjIniciadorPagamento;
	private String nomeUsuarioPagador;
	private String cpfCnpjUsuarioPagador;
	private String contaUsuarioPagador;
	private String AgenciaUsuarioPagador;
	private String tipoContaUsuarioPagador;
	private String ispbPagador;
	private String ispbRecebedor;
	private String cpfCnpjUsuarioRecebedor;
	private String contaUsuarioRecebedor;
	private String agenciaUsuarioRecebedor;
	private String tipoContaUsuarioRecebedor;
	private String chaveContaRecebedor;
	private String finalidadeTransacao;
	private String entreUsuarios;
	private String dataContabil;
	private String dataHoraLiquidacao;
	private String situacaoTransacao;
	private String erroCodigo;
	private String erroDetalhe;

	public Long getNsu() {
		String nsuString = getTxid().substring(ExternalReferenceUtil.getTamanhoTXID() - ExternalReferenceUtil.getTamanhoNSU());
		return Long.valueOf(nsuString);
	}

	public Data getDataCompra() {
		String dataCompraString = getTxid().substring(POSICAO_INICIO_DT_COMPRA, POSICAO_FIM_DT_COMPRA);
		return DataUtil.stringToData(dataCompraString, DataUtil.DDMMYYYY);
	}

	public String getPrioridadePagamento() {
		return prioridadePagamento;
	}

	public void setPrioridadePagamento(String prioridadePagamento) {
		this.prioridadePagamento = prioridadePagamento;
	}

	public String getTipoPrioridadePagamento() {
		return tipoPrioridadePagamento;
	}

	public void setTipoPrioridadePagamento(String tipoPrioridadePagamento) {
		this.tipoPrioridadePagamento = tipoPrioridadePagamento;
	}

	public String getIdFimAFim() {
		return idFimAFim;
	}

	public void setIdFimAFim(String idFimAFim) {
		this.idFimAFim = idFimAFim;
	}

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDataHoraRecebimento() {
		return dataHoraRecebimento;
	}

	// public Data getDataHoraRecebimentoData() {
	// try {
	// SimpleDateFormat sdf = new SimpleDateFormat(DataUtil.DIA_HORA_EUA);
	// Date dateRecebimento = sdf.parse(getDataHoraRecebimento().substring(0, 10) + " " +
	// getDataHoraRecebimento().substring(11, 19));
	// Data dataRecebimento = new Data(dateRecebimento.getTime());
	// return dataRecebimento;
	// } catch (Exception e) {
	// return null;
	// }
	//
	// }

	public void setDataHoraRecebimento(String dataHoraRecebimento) {
		this.dataHoraRecebimento = dataHoraRecebimento;
	}

	public String getFormaIniciacao() {
		return formaIniciacao;
	}

	public void setFormaIniciacao(String formaIniciacao) {
		this.formaIniciacao = formaIniciacao;
	}

	public String getCnpjIniciadorPagamento() {
		return cnpjIniciadorPagamento;
	}

	public void setCnpjIniciadorPagamento(String cnpjIniciadorPagamento) {
		this.cnpjIniciadorPagamento = cnpjIniciadorPagamento;
	}

	public String getNomeUsuarioPagador() {
		return nomeUsuarioPagador;
	}

	public void setNomeUsuarioPagador(String nomeUsuarioPagador) {
		this.nomeUsuarioPagador = nomeUsuarioPagador;
	}

	public String getCpfCnpjUsuarioPagador() {
		return cpfCnpjUsuarioPagador;
	}

	public void setCpfCnpjUsuarioPagador(String cpfCnpjUsuarioPagador) {
		this.cpfCnpjUsuarioPagador = cpfCnpjUsuarioPagador;
	}

	public String getContaUsuarioPagador() {
		return contaUsuarioPagador;
	}

	public void setContaUsuarioPagador(String contaUsuarioPagador) {
		this.contaUsuarioPagador = contaUsuarioPagador;
	}

	public String getAgenciaUsuarioPagador() {
		return AgenciaUsuarioPagador;
	}

	public void setAgenciaUsuarioPagador(String agenciaUsuarioPagador) {
		AgenciaUsuarioPagador = agenciaUsuarioPagador;
	}

	public String getTipoContaUsuarioPagador() {
		return tipoContaUsuarioPagador;
	}

	public void setTipoContaUsuarioPagador(String tipoContaUsuarioPagador) {
		this.tipoContaUsuarioPagador = tipoContaUsuarioPagador;
	}

	public String getIspbPagador() {
		return ispbPagador;
	}

	public void setIspbPagador(String ispbPagador) {
		this.ispbPagador = ispbPagador;
	}

	public String getIspbRecebedor() {
		return ispbRecebedor;
	}

	public void setIspbRecebedor(String ispbRecebedor) {
		this.ispbRecebedor = ispbRecebedor;
	}

	public String getCpfCnpjUsuarioRecebedor() {
		return cpfCnpjUsuarioRecebedor;
	}

	public void setCpfCnpjUsuarioRecebedor(String cpfCnpjUsuarioRecebedor) {
		this.cpfCnpjUsuarioRecebedor = cpfCnpjUsuarioRecebedor;
	}

	public String getContaUsuarioRecebedor() {
		return contaUsuarioRecebedor;
	}

	public void setContaUsuarioRecebedor(String contaUsuarioRecebedor) {
		this.contaUsuarioRecebedor = contaUsuarioRecebedor;
	}

	public String getAgenciaUsuarioRecebedor() {
		return agenciaUsuarioRecebedor;
	}

	public void setAgenciaUsuarioRecebedor(String agenciaUsuarioRecebedor) {
		this.agenciaUsuarioRecebedor = agenciaUsuarioRecebedor;
	}

	public String getTipoContaUsuarioRecebedor() {
		return tipoContaUsuarioRecebedor;
	}

	public void setTipoContaUsuarioRecebedor(String tipoContaUsuarioRecebedor) {
		this.tipoContaUsuarioRecebedor = tipoContaUsuarioRecebedor;
	}

	public String getChaveContaRecebedor() {
		return chaveContaRecebedor;
	}

	public void setChaveContaRecebedor(String chaveContaRecebedor) {
		this.chaveContaRecebedor = chaveContaRecebedor;
	}

	public String getFinalidadeTransacao() {
		return finalidadeTransacao;
	}

	public void setFinalidadeTransacao(String finalidadeTransacao) {
		this.finalidadeTransacao = finalidadeTransacao;
	}

	public String getEntreUsuarios() {
		return entreUsuarios;
	}

	public void setEntreUsuarios(String entreUsuarios) {
		this.entreUsuarios = entreUsuarios;
	}

	public String getDataContabil() {
		return dataContabil;
	}

	public void setDataContabil(String dataContabil) {
		this.dataContabil = dataContabil;
	}

	public String getDataHoraLiquidacao() {
		return dataHoraLiquidacao;
	}

	public void setDataHoraLiquidacao(String dataHoraLiquidacao) {
		this.dataHoraLiquidacao = dataHoraLiquidacao;
	}

	public String getSituacaoTransacao() {
		return situacaoTransacao;
	}

	public void setSituacaoTransacao(String situacaoTransacao) {
		this.situacaoTransacao = situacaoTransacao;
	}

	public String getErroCodigo() {
		return erroCodigo;
	}

	public void setErroCodigo(String erroCodigo) {
		this.erroCodigo = erroCodigo;
	}

	public String getErroDetalhe() {
		return erroDetalhe;
	}

	public void setErroDetalhe(String erroDetalhe) {
		this.erroDetalhe = erroDetalhe;
	}
}
