package br.gov.caixa.silce.dominio.servico.recargapay;

import java.util.Calendar;

import br.gov.caixa.silce.dominio.servico.comum.AbstractRetornoErroDefault;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;

public class RetornoCartao extends AbstractRetornoErroDefault {

	private static final long serialVersionUID = 1L;

	private String tokenCartao;
	private Long mesValidade;
	private Long anoValidade;
	private String idMetodoPagamento;
	private String nomeMetodoPagamento;
	private Long primeirosDigitos;
	private Long ultimosDigitos;

	public RetornoCartao(String tokenCartao, Long mesValidade, Long anoValidade, String idMetodoPagamento, String nomeMetodoPagamento, Long primeirosDigitos, Long ultimosDigitos) {
		this.tokenCartao = tokenCartao;
		this.mesValidade = mesValidade;
		this.anoValidade = anoValidade;
		this.idMetodoPagamento = idMetodoPagamento;
		this.nomeMetodoPagamento = nomeMetodoPagamento;
		this.primeirosDigitos = primeirosDigitos;
		this.ultimosDigitos = ultimosDigitos;
	}

	@Override
	public String getNsuTransacaoMp() {
		return null;
	}

	public String getTokenCartao() {
		return tokenCartao;
	}

	public Long getMesValidade() {
		return mesValidade;
	}

	public Long getAnoValidade() {
		return anoValidade;
	}

	public String getIdMetodoPagamento() {
		return idMetodoPagamento;
	}

	public String getNomeMetodoPagamento() {
		return nomeMetodoPagamento;
	}

	public Long getPrimeirosDigitos() {
		return primeirosDigitos;
	}

	public Long getUltimosDigitos() {
		return ultimosDigitos;
	}

	public boolean isValido() {
		return tokenCartao != null && primeirosDigitos != null
			&& ultimosDigitos != null && idMetodoPagamento != null && nomeMetodoPagamento != null
			&& !isExpirado();
	}

	public boolean isExpirado() {
		Data dataAtual = DataUtil.getDataAtual();
		Data dataExpiracao = new Data(anoValidade.intValue(), mesValidade.intValue() - 1, dataAtual.get(Calendar.DAY_OF_MONTH));
		dataExpiracao = new Data(anoValidade.intValue(), dataExpiracao.getMonth() - 1, dataExpiracao.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH));
		return mesValidade != null && anoValidade != null
			&& dataExpiracao.before(dataAtual);
	}
}
