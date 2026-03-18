package br.gov.caixa.silce.dominio.servico.recargapay;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;
import br.gov.caixa.util.Data;

/**
 * @author c142924
 *
 */
public class RetornoGeraToken extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private String accessToken;
	private Integer expiresIn;
	private String tokenType;
	private Integer notBeforePolicy;

	private Data dataExpiracaoToken;

	public RetornoGeraToken(String accessToken, Integer expiresIn,
		String tokenType, Integer notBeforePolicy) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.tokenType = tokenType;
		this.notBeforePolicy = notBeforePolicy;
	}

	public RetornoGeraToken(String accessToken, Integer expiresIn, String tokenType) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.tokenType = tokenType;
	}

	public RetornoGeraToken(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public String getTokenType() {
		return tokenType;
	}

	public Integer getNotBeforePolicy() {
		return notBeforePolicy;
	}

	public Data getDataExpiracaoToken() {
		return dataExpiracaoToken;
	}

	@Override
	public String getNsuTransacaoMp() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDataExpiracaoToken(Data dataExpiracaoToken) {
		this.dataExpiracaoToken = dataExpiracaoToken;
	}
	
}
