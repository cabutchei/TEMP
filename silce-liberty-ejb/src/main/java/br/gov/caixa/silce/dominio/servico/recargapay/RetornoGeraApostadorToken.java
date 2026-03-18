package br.gov.caixa.silce.dominio.servico.recargapay;

import br.gov.caixa.silce.dominio.servico.comum.RetornoErro;

/**
 * @author c142924
 *
 */
public class RetornoGeraApostadorToken extends RetornoGeraToken {

	private Boolean upgraded;

	public RetornoGeraApostadorToken(Boolean upgraded, String accessToken, Integer expiresIn,
		String tokenType, Integer notBeforePolicy) {
		super(accessToken, expiresIn, tokenType, notBeforePolicy);
		this.upgraded = upgraded;
	}

	public RetornoGeraApostadorToken(RetornoErro retornoErro) {
		super(retornoErro);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getNsuTransacaoMp() {
		return null;
	}

	public Boolean getUpgraded() {
		return upgraded;
	}
}
