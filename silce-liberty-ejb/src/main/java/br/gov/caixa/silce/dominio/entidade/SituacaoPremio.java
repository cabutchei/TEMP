package br.gov.caixa.silce.dominio.entidade;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.openjpa.persistence.DataCache;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

@Entity
@Table(name = "LCETB014_SITUACAO_PREMIO", schema = DatabaseConfig.SCHEMA)
@DataCache(enabled = true, timeout = -1)
public class SituacaoPremio extends AbstractEntidadeEnum<Long, SituacaoPremio.Situacao> {

	private static final long serialVersionUID = 1L;

	public enum Situacao implements CaixaEnum<Long> {
		INICIADO(1L),
		AUTORIZADO_PENDENTE_CREDITO(2L),
		CREDITADO_PENDENTE_CONFIRMACAO(3L),
		PAGAMENTO_CONCLUIDO(4L),
		NAO_AUTORIZADO(5L),
		CREDITO_REJEITADO_PENDENTE_CANCELAMENTO(6L),
		PAGAMENTO_CANCELADO(7L);
		
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

		public static Situacao[] getSituacoesFinaisPagamentoNaoEfetivado() {
			return new Situacao[] { NAO_AUTORIZADO, PAGAMENTO_CANCELADO };
		}

		public static Situacao[] getSituacoesNaoFinais() {
			return new Situacao[] { INICIADO, AUTORIZADO_PENDENTE_CREDITO, CREDITADO_PENDENTE_CONFIRMACAO, CREDITO_REJEITADO_PENDENTE_CANCELAMENTO };
		}
		
		public static Situacao[] getSituacoesPagamentoEfetivado() {
			return new Situacao[] { CREDITADO_PENDENTE_CONFIRMACAO, PAGAMENTO_CONCLUIDO };
		}

		public boolean isSituacaoPagamentoEfetivado() {
			return Arrays.asList(SituacaoPremio.Situacao.getSituacoesPagamentoEfetivado()).contains(this);
		}
	}

	@Id
	@Column(name = "NU_SITUACAO_PREMIO", insertable=false, updatable=false)
	private Long id;
	
	@Column(name = "DE_SITUACAO_PREMIO", insertable=false, updatable=false)
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
		return "SituacaoPremio [id=" + id + ", descricao=" + descricao + "]";
	}

}
