package br.gov.caixa.silce.dominio.entidade;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.openjpa.persistence.DataCache;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

@Entity
@Table(name = "LCETB008_SITUACAO_APOSTA", schema = DatabaseConfig.SCHEMA)
@NamedQuery(name = SituacaoAposta.NQ_FIND_BY_IDS,
			query = "Select entidade from SituacaoAposta entidade Where entidade.id in (?1)")
@DataCache(enabled = true, timeout = -1)
public class SituacaoAposta extends AbstractEntidadeEnum<Long, SituacaoAposta.Situacao> {

	public static final String NQ_FIND_BY_IDS = "SituacaoAposta.consulteByIds";
	
	private static final long serialVersionUID = 1L;

	public enum Situacao implements CaixaEnum<Long> {
		NAO_REGISTRADA(1L),
		EM_PROCESSAMENTO(2L),
		EM_PROCESSAMENTO_ENVIADA_SISPL(3L),
		EFETIVADA(4L),
		NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO(5L), 
		NAO_EFETIVADA_DINHEIRO_DEVOLVIDO(6L),
		PAGAMENTO_EM_PROCESSAMENTO(7L),
		CANCELADA(8L),
		NAO_PREMIADA(9L),
		PRESCRITA(10L), 
		PREMIO_PAGO(11L),
		NAO_EFETIVADA_AGUARDANDO_DEVOLUCAO_PIX(12L);

		private final Long value;

		private Situacao(Long value) {
			this.value = value;
		}

		public Long getValue() {
			return value;
		}
		
		/**
		 * @return os dois estados em que a aposta está em processamento
		 */
		public static Situacao[] getSituacoesPendenteParaFecharConcurso() {
			return new Situacao[] { NAO_REGISTRADA, EM_PROCESSAMENTO, EM_PROCESSAMENTO_ENVIADA_SISPL };
		}

		/**
		 * @return os dois estados em que a aposta está em processamento
		 */
		public static Situacao[] getSituacoesEmProcessamento() {
			return new Situacao[] { EM_PROCESSAMENTO, EM_PROCESSAMENTO_ENVIADA_SISPL };
		}
		
		/**
		 * @return todas as situações em que a aposta foi efetivada
		 */
		public static Situacao[] getSituacoesEfetivada() {
			return new Situacao[]{EFETIVADA, NAO_PREMIADA, PRESCRITA, PREMIO_PAGO, PAGAMENTO_EM_PROCESSAMENTO};
		}
		
		/**
		 * @return todas as situações em que a aposta foi conferida
		 */
		public static Situacao[] getSituacoesConferida() {
			return new Situacao[] {Situacao.PAGAMENTO_EM_PROCESSAMENTO, Situacao.NAO_PREMIADA, Situacao.PRESCRITA, Situacao.PREMIO_PAGO};
		}		

		/**
		 * @return todas as situações em que a aposta foi conferida
		 */
		public static Situacao[] getSituacoesConcursoPrescrito() {
			return new Situacao[] { Situacao.NAO_REGISTRADA,
					Situacao.EM_PROCESSAMENTO,
					Situacao.EM_PROCESSAMENTO_ENVIADA_SISPL,
					Situacao.EFETIVADA,
					Situacao.NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO,
					Situacao.NAO_EFETIVADA_DINHEIRO_DEVOLVIDO,
					Situacao.PAGAMENTO_EM_PROCESSAMENTO,
					Situacao.CANCELADA,
					Situacao.NAO_PREMIADA,
					Situacao.PRESCRITA };
		}
		
		/**
		 * @return todas as situações em que a aposta está pendente
		 */
		public static Situacao[] getSituacoesPendente() {
			return new Situacao[]{NAO_REGISTRADA, EM_PROCESSAMENTO, EM_PROCESSAMENTO_ENVIADA_SISPL, NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO};
		}
		
		public static Situacao getByValue(Long value) {
			return EnumUtil.recupereByValue(values(), value);
		}
		
		public static Situacao getBySituacaoAposta(SituacaoAposta situacaoAposta) {
			if (situacaoAposta == null) {
				return null;
			}
			return EnumUtil.recupereByValue(values(), situacaoAposta.getId());
		}		
	}

	@Id
	@Column(name = "NU_SITUACAO_APOSTA", insertable = false, updatable = false)
	private Long id;

	@Column(name = "DE_SITUACAO_APOSTA", insertable = false, updatable = false)
	private String descricao;

	private static final Set<Long> idsEmProcessamento = new HashSet<Long>();
	static {
		Situacao[] situacoesEmProcessamento = Situacao.getSituacoesEmProcessamento();
		for (Situacao situacao : situacoesEmProcessamento) {
			idsEmProcessamento.add(situacao.getValue());
		}
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public boolean isEmProcessamento() {
		return idsEmProcessamento.contains(id);
	}

	@Override
	protected Situacao[] createValores() {
		return Situacao.values();
	}

	@Override
	public String toString() {
		return "SituacaoAposta [id=" + id + ", descricao=" + descricao + "]";
	}

}
