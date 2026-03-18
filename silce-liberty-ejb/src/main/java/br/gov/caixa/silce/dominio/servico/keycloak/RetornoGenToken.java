package br.gov.caixa.silce.dominio.servico.keycloak;

import br.gov.caixa.dominio.SaidaHttp;
import br.gov.caixa.util.Data;

public class RetornoGenToken implements SaidaHttp {

	private static final long serialVersionUID = 1L;
	private Integer statusCodeHttp;
	private String accessToken;
	private Integer expiresIn;
	private String refreshToken;
	private Integer refreshExpiresIn;
	private Data dataExpiracaoToken;
	private Data dataExpiracaoRefreshToken;

	public RetornoGenToken(String accessToken, Integer expiresIn, String refreshToken, Integer refreshExpiresIn) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.refreshToken = refreshToken;
		this.refreshExpiresIn = refreshExpiresIn;
	}

	@Override
	public Boolean isOperacaoExecutadaComSucesso() {
		return 200 == statusCodeHttp;
	}

	@Override
	public Integer getStatusCodeHttp() {
		return statusCodeHttp;
	}

	@Override
	public void setStatusCodeHttp(Integer statusCodeHttp) {
		this.statusCodeHttp = statusCodeHttp;

	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Integer getRefreshExpiresIn() {
		return refreshExpiresIn;
	}

	public void setRefreshExpiresIn(Integer refreshExpiresIn) {
		this.refreshExpiresIn = refreshExpiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Data getDataExpiracaoToken() {
		return dataExpiracaoToken;
	}

	public void setDataExpiracaoToken(Data dataExpiracaoToken) {
		this.dataExpiracaoToken = dataExpiracaoToken;
	}

	public Data getDataExpiracaoRefreshToken() {
		return dataExpiracaoRefreshToken;
	}

	public void setDataExpiracaoRefreshToken(Data dataExpiracaoRefreshToken) {
		this.dataExpiracaoRefreshToken = dataExpiracaoRefreshToken;
	}

}
