package br.gov.caixa.dominio;

import br.gov.caixa.util.StringUtil;

/**
 * @author c101482
 *
 */
public class SaidaBroker implements SaidaMQ {

	private static final char CHAR_COMPLETAR_CODIGO_RETORNO = '0';
	private static final int TAMANHO_CODIGO_RETORNO = 5;
	private static final String CODIGO_RETORNO_ZERADO = completeCodigoRetorno("0");
	public static final String CODIGO_RETORNO_ERRO_INFRA = completeCodigoRetorno("99");

	private static final long serialVersionUID = 1L;

	private static final int TAMANHO_INICIAL_TO_STRING = 100;
	
	private String codigoRetorno;
	
	private String codigoRetornoCompleto;
	
	private String mensagemRetorno;
	
	private String origemRetorno;

	protected static String completeCodigoRetorno(String codigoRetorno) {
		return StringUtil.completeAEsquerda(codigoRetorno, TAMANHO_CODIGO_RETORNO, CHAR_COMPLETAR_CODIGO_RETORNO);
	}
	
	public String getCodigoRetorno() {
		return codigoRetorno;
	}

	public void setCodigoRetorno(String codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
		this.codigoRetornoCompleto = completeCodigoRetorno(codigoRetorno);
	}

	public String getMensagemRetorno() {
		return mensagemRetorno;
	}

	public void setMensagemRetorno(String mensagemRetorno) {
		this.mensagemRetorno = mensagemRetorno;
	}

	public String getOrigemRetorno() {
		return origemRetorno;
	}

	public void setOrigemRetorno(String origemRetorno) {
		this.origemRetorno = origemRetorno;
	}
	
	public String getCodigoRetornoCompleto() {
		return codigoRetornoCompleto;
	}

	public Boolean isOperacaoExecutadaComSucesso() {
		return CODIGO_RETORNO_ZERADO.equals(codigoRetornoCompleto);
	}
	
	protected StringBuilder createToStringBuilder() {
		return new StringBuilder(TAMANHO_INICIAL_TO_STRING);
	}

}
