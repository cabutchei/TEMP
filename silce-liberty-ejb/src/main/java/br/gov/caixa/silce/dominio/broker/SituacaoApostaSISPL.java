package br.gov.caixa.silce.dominio.broker;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

/**
 * Situações que o SISPL retorna ao consultar se a aposta está premiada.
 */
public enum SituacaoApostaSISPL implements CaixaEnum<Integer> {
	PREMIADA(0),
	PREMIOS_PRESCRITOS(1),
	PRESCRITA(2),
	NAO_SORTEADA(3),
	CANCELADA(4),
	NAO_PREMIADA(5),
	PREMIO_PAGO(6),
	PREMIADA_CANAL_NAO_PAGA(13);
	
	private final Integer codigo;
	
	private SituacaoApostaSISPL(Integer codigo) {
		this.codigo = codigo;
	}
	
	@Override
	public Integer getValue() {
		return codigo;
	}
	
	public static SituacaoApostaSISPL getByCodigo(Integer codigo) {
		return EnumUtil.recupereByValue(values(), codigo);
	}
	
	public static List<SituacaoApostaSISPL> getSituacoesPremiadas() {
		List<SituacaoApostaSISPL> situacoes = new ArrayList<SituacaoApostaSISPL>();
		situacoes.add(PREMIADA);
		situacoes.add(PREMIADA_CANAL_NAO_PAGA);
		return situacoes;
	}
	
	public static List<SituacaoApostaSISPL> getSituacoesPrescritas() {
		List<SituacaoApostaSISPL> situacoes = new ArrayList<SituacaoApostaSISPL>();
		situacoes.add(PREMIOS_PRESCRITOS);
		situacoes.add(PRESCRITA);
		return situacoes;
	}
	
}

