package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;
import br.gov.caixa.util.StringUtil;

enum SituacaoPremioSISPL2 implements CaixaEnum<Integer> {
	SEM_PREMIO(0, "Sem Prêmio"),
	PREMIADA(1, "Premiada"),
	AUTORIZADO(2, "Autorizada"),
	PAGO(3, "Prêmio Pago"),
	PRESCRITO(4, "Prescrita");

	private final Integer codigo;
	private final String descricao;

	private SituacaoPremioSISPL2(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public static SituacaoPremioSISPL2 getByCodigo(Integer codigo) {
		for (SituacaoPremioSISPL2 situacaoPremioSISPL2 : SituacaoPremioSISPL2.values()) {
			if (situacaoPremioSISPL2.getValue().equals(codigo)) {
				return situacaoPremioSISPL2;
			}
		}
		return SEM_PREMIO;
	}

	public static SituacaoPremioSISPL2 getByDescricao(String descricao) {
		String descr = StringUtil.removeAcentosAndToUpper(descricao).trim().replaceAll("-| ", "");
		for (SituacaoPremioSISPL2 situacaoPremioSISPL2 : SituacaoPremioSISPL2.values()) {
			if (StringUtil
				.removeAcentosAndToUpper(situacaoPremioSISPL2.descricao).trim().replaceAll("-| ", "")
				.equals(descr)) {
				return situacaoPremioSISPL2;
			}
		}
		return SEM_PREMIO;
	}

	@Override
	public Integer getValue() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

}

public enum SituacaoPremioSISPL implements CaixaEnum<Integer> {
	
	A_PAGAR(0),
	SEM_PREMIO(1),
	EM_CONFIRMACAO(2),
	PAGO(3),
	PRESCRITO(4);
	
	private final Integer codigo;
	
	private SituacaoPremioSISPL(Integer codigo) {
		this.codigo = codigo;
	}
	
	@Override
	public Integer getValue() {
		return codigo;
	}
	
	public static SituacaoPremioSISPL getByCodigo(Integer codigo) {
		return EnumUtil.recupereByValue(values(), codigo);
	}
	
	public static SituacaoPremioSISPL converteFromSISPL2(Integer codigo) {
		SituacaoPremioSISPL2 by = SituacaoPremioSISPL2.getByCodigo(codigo);
		return convertFromSISPL2(by);
	}

	public static SituacaoPremioSISPL converteFromSISPL2(String descr) {
		SituacaoPremioSISPL2 by = SituacaoPremioSISPL2.getByDescricao(descr);
		return convertFromSISPL2(by);
	}

	private static SituacaoPremioSISPL convertFromSISPL2(SituacaoPremioSISPL2 by) {
		switch (by) {
			case AUTORIZADO:
				return SituacaoPremioSISPL.EM_CONFIRMACAO;
			case PAGO:
				return SituacaoPremioSISPL.PAGO;
			case PREMIADA:
				return SituacaoPremioSISPL.A_PAGAR;
			case PRESCRITO:
				return SituacaoPremioSISPL.PRESCRITO;
			default:
				return SituacaoPremioSISPL.SEM_PREMIO;
		}
	}

}
