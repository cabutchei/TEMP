package br.gov.caixa.silce.dominio.pagamento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;

public enum ParametroPagamento {

	OTP(1L, Meio.CARTEIRA_ELETRONICA), 
	TOKEN(2L, Meio.MERCADO_PAGO), 
	PAYMENT_METHOD_ID(3L, Meio.MERCADO_PAGO),
	EMAIL(4L, Meio.MERCADO_PAGO),
	SAVE_CARD(5L, Meio.MERCADO_PAGO),
	/**
	 * Indica se o cartão utilizado está salvo no Mercado Pago
	 */
	SAVED_CARD(6L, Meio.MERCADO_PAGO)
	
	;
	
	private final Long codigo;
	private final List<Meio> meiosPagamento;

	private ParametroPagamento(Long codigo, Meio... meiosPagamento) {
		this.codigo = codigo;
		this.meiosPagamento = Arrays.asList(meiosPagamento);
	}
	
	public static List<ParametroPagamento> getParametros(Meio meioPagamento) {
		List<ParametroPagamento> parametrosPagamento = new ArrayList<ParametroPagamento>();
		for (ParametroPagamento pp : values()) {
			if (pp.getMeiosPagamento().contains(meioPagamento)) {
				parametrosPagamento.add(pp);
			}
		}
		return parametrosPagamento;
	}
	
	public List<Meio> getMeiosPagamento() {
		return meiosPagamento;
	}

	public Long getCodigo() {
		return codigo;
	}

	public static ParametroPagamento getByCodigo(Long codigo) {
		for (ParametroPagamento pp : values()) {
			if (pp.getCodigo().equals(codigo)) {
				return pp;
			}
		}
		return null;
	}
	
}
