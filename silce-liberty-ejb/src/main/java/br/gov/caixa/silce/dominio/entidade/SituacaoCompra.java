package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.openjpa.persistence.DataCache;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra.Situacao;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

@Entity
@Table(name = "LCETB012_SITUACAO_COMPRA", schema = DatabaseConfig.SCHEMA)
@NamedQuery(name = SituacaoCompra.NQ_FIND_BY_IDS,
	query = "Select entidade from SituacaoCompra entidade Where entidade.id in (?1)")
@DataCache(enabled = true, timeout = -1)
public class SituacaoCompra extends AbstractEntidadeEnum<Long, Situacao> {

	private static final long serialVersionUID = 1L;
	public static final String NQ_FIND_BY_IDS = "SituacaoCompra.consulteByIds";

	public enum Situacao implements CaixaEnum<Long> {
		CARRINHO(1L), 
		DEBITO_INICIADO(2L), 
		DEBITO_NAO_AUTORIZADO(3L),
		ESTORNADA(4L),
		CANCELADA(5L), 
		DEBITO_REALIZADO(6L), 
		EM_PROCESSAMENTO(7L), 
		FINALIZADA(8L),
		EM_PROCESSAMENTO_REPRESADA(9L),
		AGUARDANDO_PAGAMENTO_PIX(10L),
		AGUARDANDO_DEVOLUCAO_PIX(11L),
		CANCELADA_PAGAMENTO_NAO_IDENTIFICADO_TEMPO_LIMITE(12L),
		FINALIZADA_TODAS_APOSTAS_EFETIVADAS(13L),
		FINALIZADA_CONTEM_APOSTAS_NAO_EFETIVADAS(14L);
		
		private final Long value;

		private Situacao(Long value) {
			this.value = value;
		}

		public Long getValue() {
			return value;
		}
		
		public static Situacao getByValue(Long value) {
			return EnumUtil.recupereByValue(values(), value);
		}

		public static Object getBySituacaoCompra(SituacaoCompra situacao) {
			if (situacao == null) {
				return null;
			}
			return EnumUtil.recupereByValue(values(), situacao.getId());
		}
		
		public static Situacao[] getSituacoesEfetivada() {
			return new Situacao[] { ESTORNADA, DEBITO_REALIZADO, EM_PROCESSAMENTO, FINALIZADA, FINALIZADA_CONTEM_APOSTAS_NAO_EFETIVADAS, FINALIZADA_TODAS_APOSTAS_EFETIVADAS };
		}

		public static Situacao[] getSituacoesEmProcessamento() {
			return new Situacao[] { EM_PROCESSAMENTO, EM_PROCESSAMENTO_REPRESADA };
		}

		public static Situacao[] getSituacoesEfetivadaParaFechamento() {
			return new Situacao[] { DEBITO_REALIZADO, FINALIZADA, EM_PROCESSAMENTO, EM_PROCESSAMENTO_REPRESADA, FINALIZADA_CONTEM_APOSTAS_NAO_EFETIVADAS,
					FINALIZADA_TODAS_APOSTAS_EFETIVADAS };
		}

		public static Situacao[] getSituacoesEfetivadaParaConfirmacaoCota() {
			return getSituacoesEfetivadaParaFechamento();
		}

		public static Situacao[] getSituacoesSucessoCompra() {
			return new Situacao[] { EM_PROCESSAMENTO, EM_PROCESSAMENTO_REPRESADA, FINALIZADA, FINALIZADA_CONTEM_APOSTAS_NAO_EFETIVADAS, FINALIZADA_TODAS_APOSTAS_EFETIVADAS };
		}

		public static Situacao[] getSituacoesFalhaCompra() {
			return new Situacao[] { DEBITO_NAO_AUTORIZADO, CANCELADA, CANCELADA_PAGAMENTO_NAO_IDENTIFICADO_TEMPO_LIMITE, ESTORNADA, AGUARDANDO_DEVOLUCAO_PIX };
		}

		public static Situacao[] getSituacoesFalhaCompraCota() {
			return new Situacao[] { DEBITO_INICIADO, DEBITO_NAO_AUTORIZADO, CANCELADA, CANCELADA_PAGAMENTO_NAO_IDENTIFICADO_TEMPO_LIMITE, ESTORNADA, AGUARDANDO_DEVOLUCAO_PIX };
		}

		public static Situacao[] getListaSitucaoCompraTodosMeioPagamento() {
			return new Situacao[] { DEBITO_NAO_AUTORIZADO, ESTORNADA, CANCELADA, DEBITO_REALIZADO, EM_PROCESSAMENTO, FINALIZADA, EM_PROCESSAMENTO_REPRESADA,
					AGUARDANDO_PAGAMENTO_PIX, AGUARDANDO_DEVOLUCAO_PIX, CANCELADA_PAGAMENTO_NAO_IDENTIFICADO_TEMPO_LIMITE, FINALIZADA_TODAS_APOSTAS_EFETIVADAS,
					FINALIZADA_CONTEM_APOSTAS_NAO_EFETIVADAS };
		}

		public static Situacao[] getListaSitucaoCompraMercadoPagoRecargaPay() {
			return new Situacao[] { DEBITO_NAO_AUTORIZADO, ESTORNADA, CANCELADA, DEBITO_REALIZADO, EM_PROCESSAMENTO, FINALIZADA, EM_PROCESSAMENTO_REPRESADA,
					FINALIZADA_TODAS_APOSTAS_EFETIVADAS, FINALIZADA_CONTEM_APOSTAS_NAO_EFETIVADAS };
		}

		public static Situacao[] getListaSitucaoCompraPix() {
			return new Situacao[] { ESTORNADA, CANCELADA, DEBITO_REALIZADO, EM_PROCESSAMENTO, FINALIZADA, EM_PROCESSAMENTO_REPRESADA,
					AGUARDANDO_PAGAMENTO_PIX, AGUARDANDO_DEVOLUCAO_PIX, CANCELADA_PAGAMENTO_NAO_IDENTIFICADO_TEMPO_LIMITE, FINALIZADA_TODAS_APOSTAS_EFETIVADAS,
					FINALIZADA_CONTEM_APOSTAS_NAO_EFETIVADAS };
		}

	}

	@Id
	@Column(name = "NU_SITUACAO_COMPRA", insertable = false, updatable = false)
	private Long id;

	@Column(name = "DE_SITUACAO_COMPRA", insertable = false, updatable = false)
	private String descricao;

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

	@Override
	protected Situacao[] createValores() {
		return Situacao.values();
	}

	@Override
	public String toString() {
		StringBuilder builder = createToStringBuilder();
		builder.append("SituacaoCarrinho [id=");
		builder.append(id);
		builder.append(", descricao=");
		builder.append(descricao);
		builder.append(']');
		return builder.toString();
	}


}
