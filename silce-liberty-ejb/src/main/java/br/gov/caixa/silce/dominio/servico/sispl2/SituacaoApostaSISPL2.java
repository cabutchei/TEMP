package br.gov.caixa.silce.dominio.servico.sispl2;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

/**
 * Situações que o SISPL retorna ao consultar se a aposta está premiada.
 */
public enum SituacaoApostaSISPL2 implements CaixaEnum<Long> {
	
	NAO_PREMIADO(1L, "Não premiado"), 
    PREMIADO(2L, "Premiado"),
    PAGO(3L, "Pago"), 
    PREMIADO_CANAL_NAO_PAGA(4L, "Premiado canal não paga"),
    PRESCRITO(5L, "Concurso prescrito"),
	NAO_AUTORIZADO(6L, "Não Autorizado"), 
    EM_PAGAMENTO(7L, "Em pagamento"), 
    AGUARDANDO_SORTEIO(9L, "Aguardando sorteio");

	
	private final Long codigo;
	private final String descricao;
	
	private SituacaoApostaSISPL2(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	@Override
	public 	Long getValue() {
		return codigo;
	}
	
	public static SituacaoApostaSISPL2 getByEnumName(String name) {
		return EnumUtil.valueOf(SituacaoApostaSISPL2.class, name);
	}
	
	public static List<SituacaoApostaSISPL2> getSituacoesPremiadas() {
		List<SituacaoApostaSISPL2> situacoes = new ArrayList<SituacaoApostaSISPL2>();
		situacoes.add(PREMIADO);
		situacoes.add(PREMIADO_CANAL_NAO_PAGA);
		return situacoes;
	}
	
	public static List<SituacaoApostaSISPL2> getSituacoesPrescritas() {
		List<SituacaoApostaSISPL2> situacoes = new ArrayList<SituacaoApostaSISPL2>();
		situacoes.add(PRESCRITO);
		return situacoes;
	}

	public String getDescricao() {
		return descricao;
	}
	
}

