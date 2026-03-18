package br.gov.caixa.silce.dominio.servico.comum;

import java.io.Serializable;


public class RetornoErro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String message;
	private final String error;
	private final Integer status;
	private final Integer causeCode;
	private final String causeMessage;
	private final String code;

	public RetornoErro(String message, String error, Integer status, Integer causeCode, String causeMessage) {
		super();
		this.message = message;
		this.error = error;
		this.status = status;
		this.causeCode = causeCode;
		this.causeMessage = causeMessage;
		this.code = null;
	}

	public RetornoErro(String message) {
		super();
		this.message = message;
		this.status = null;
		this.code = null;
		this.error = null;
		this.causeMessage = null;
		this.causeCode = null;
	}

	public RetornoErro(String message, Integer status, String code) {
		super();
		this.message = message;
		this.status = status;
		this.code = code;
		this.error = null;
		this.causeMessage = null;
		this.causeCode = null;
	}

	public String getMessage() {
		return message;
	}

	public String getError() {
		return error;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getCauseCode() {
		return causeCode;
	}

	public String getCauseMessage() {
		return causeMessage;
	}

	@Override
	public String toString() {
		return "RetornoErroMercadoPago [message=" + message + ", error=" + error + ", status=" + status + ", causeCode=" + causeCode
				+ ", causeMessage=" + causeMessage + "]";
	}

	public String getCode() {
		return code;
	}
	
}
