package br.gov.caixa.silce.dominio.exception;

import java.io.Serializable;

import br.gov.caixa.dominio.exception.ICodigoErro;
import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.entidade.Operacao.OperacaoEnum;

public class MeioPagamentoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	private final String nsuTransacao;
	private final TipoExcecao tipoExcecao;

	public enum TipoExcecao {
		DEBITO_REJEITADO, ERRO_OPERACAO, TIMEOUT, CREDITO_REJEITADO, ESTORNO_NECESSARIO, DEBITO_CANCELADO, PAGAMENTO_NAO_LOCALIZADO, COBRANCA_NAO_LOCALIZADA;
	}

	public MeioPagamentoException(TipoExcecao tipoExcecao, OperacaoEnum operacaoEnum, Meio meio, String codigoErroMp, Throwable cause, String nsuTransacao,
		Serializable... arguments) {
		this(tipoExcecao, new CodigoErroMeioPagamento(meio, operacaoEnum, codigoErroMp), cause, nsuTransacao, arguments);
	}
	
	public MeioPagamentoException(TipoExcecao tipoExcecao, ICodigoErro codigoErro, Throwable t, String nsuTransacao, Serializable... arguments) {
		super(codigoErro, t, arguments);
		this.tipoExcecao = tipoExcecao;
		this.nsuTransacao = nsuTransacao;
	}

	public MeioPagamentoException(TipoExcecao tipoExcecao, OperacaoEnum operacaoEnum, Meio meio, String codigoErroMp, String nsuTransacao, Serializable... arguments) {
		this(tipoExcecao, operacaoEnum, meio, codigoErroMp, null, nsuTransacao, arguments);
	}

	public MeioPagamentoException(TipoExcecao tipoExcecao, OperacaoEnum operacaoEnum, Meio meio, String codigoErroMp, Serializable... arguments) {
		this(tipoExcecao, operacaoEnum, meio, codigoErroMp, null, null, arguments);
	}

	public MeioPagamentoException(TipoExcecao tipoExcecao, ICodigoErro codigoErro, String nsuTransacao, Serializable... arguments) {
		this(tipoExcecao, codigoErro, null, nsuTransacao, arguments);
	}
	
	public MeioPagamentoException(TipoExcecao tipoExcecao, ICodigoErro codigoErro, Serializable... arguments) {
		this(tipoExcecao, codigoErro, null, null, arguments);
	}

	public String getNsuTransacao() {
		return nsuTransacao;
	}

	public Boolean isDebitoRejeitado() {
		return TipoExcecao.DEBITO_REJEITADO.equals(this.getTipoExcecao());
	}

	public Boolean isCreditoRejeitado() {
		return TipoExcecao.CREDITO_REJEITADO.equals(this.getTipoExcecao());
	}

	public Boolean isErroOperacao() {
		return TipoExcecao.ERRO_OPERACAO.equals(this.getTipoExcecao());
	}

	public Boolean isEstornoNecessario() {
		return TipoExcecao.ESTORNO_NECESSARIO.equals(this.getTipoExcecao());
	}

	public Boolean isDebitoCancelado() {
		return TipoExcecao.DEBITO_CANCELADO.equals(this.getTipoExcecao());
	}

	public Boolean isTimeout() {
		return TipoExcecao.TIMEOUT.equals(this.getTipoExcecao());
	}

	public TipoExcecao getTipoExcecao() {
		return tipoExcecao;
	}

	public CodigoErroMeioPagamento getCodigoErroMp() {
		ICodigoErro codigoErro = getCodigoErro();
		return CodigoErroMeioPagamento.class.isInstance(codigoErro) ? (CodigoErroMeioPagamento) codigoErro : null;
	}

	public String getCodigoErroMpStr() {
		CodigoErroMeioPagamento codigoErroMp = getCodigoErroMp();
		return codigoErroMp == null ? null : codigoErroMp.getCodigoErro();
	}

}
