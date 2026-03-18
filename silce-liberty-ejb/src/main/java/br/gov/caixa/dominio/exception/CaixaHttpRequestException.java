package br.gov.caixa.dominio.exception;

public class CaixaHttpRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final String MSG_RESPONSE = "Falha ao receber resposta HTTP. Erro HTTP: %s. Mensagem HTTP: %s.";
	
	private final Integer responseCode;
	private final String responseMessage;
	private final Boolean erroInfra;
	
	public CaixaHttpRequestException(Integer responseCode, String responseMessage, Boolean erroInfra) {
		super(String.format(MSG_RESPONSE, responseCode, responseMessage));
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.erroInfra = erroInfra;
	}
	
	public CaixaHttpRequestException(String message, Throwable cause, Boolean erroInfra, Object... messageParams) {
		super(String.format(message, messageParams), cause);
		this.responseCode = null;
		this.responseMessage = null;
		this.erroInfra = erroInfra;
	}	

	public Integer getResponseCode() {
		return responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public Boolean getErroInfra() {
		return erroInfra;
	}
	
}
