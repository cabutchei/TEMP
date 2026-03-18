package br.gov.caixa.silce.dominio;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.caixa.dominio.Auditavel;

/**
 * Transações do sistema. Cada instância é uma transação que pode ser realizada no SILCE, com um código que a identifica
 * unicamente.
 * 
 * @author c127237
 */
public enum Transacao implements Auditavel {

	GESTOR_ALTERA_PARAMETRO_SISTEMA(1L, "Alteração de Parâmetros do Sistema"), 
	GESTOR_ALTERA_DADOS_APOSTADOR(2L, "Alteração de dados do Apostador"),
	GESTOR_CONTROLA_MEIO_PAGAMENTO(3L, "Controla Meio de Pagamento"),
	GESTOR_GERENCIA_FECHAMENTO(4L, "Gerenciamento do Fechamento"),
	GESTOR_EMITE_COMPLEMENTO_SIDEC(5L, "Emite Complemento SIDEC"),
	GESTOR_EMITE_COMPLEMENTO_FINANCEIRO(6L, "Emite Complemento Financeiro"),
	GESTOR_AJUSTE_COMISSAO_MENSAL(7L, "Ajuste da comissão mensal"),
	GESTOR_CANCELA_RESERVA_COTA_MKP(8L, "Cancelamento de Reserva de Cota do Marketplace"),
	GESTOR_CONFIRMA_RESERVA_COTA_MKP(9L, "Confirmação de Reserva de Cota do Marketplace"),
	GESTOR_BLOQUEIA_DESBLOQUEIA_DEVOLUCAO(10L, "Alteração do bloqueio/desbloqueio Devolução de Apostas"),
	GESTOR_CONFIRMA_DEVOLUCAO(11L, "Confirmação Devolução de Apostas");

	private static final Map<Long, String> DESCRICOES = new HashMap<Long, String>(); 
	static {
		for (Transacao transacao : values()) {
			DESCRICOES.put(transacao.getValue(), transacao.getDescricao());
		}
	}
	
	private final Long value;
	
	private final String descricao;

	private Transacao(Long value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public static String getDescricao(Long value) {
		return DESCRICOES.get(value);
	}
	
	public static List<Transacao> getTransacoes() {
		return Arrays.asList(values());
	}
	
	public Long getValue() {
		return value;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return "Transacao SILCE [" + name() + ",value=" + value + "]";
	}

	@Override
	public Long getIdentificador() {
		return getValue();
	}
	
}
