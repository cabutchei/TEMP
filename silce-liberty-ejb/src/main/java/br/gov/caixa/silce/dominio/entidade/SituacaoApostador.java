package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.openjpa.persistence.DataCache;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.util.CaixaEnum;

@Entity
@Table(name = "LCETB001_SITUACAO_APOSTADOR", schema = DatabaseConfig.SCHEMA)
@DataCache(enabled = true, timeout = -1)
public class SituacaoApostador extends AbstractEntidadeEnum<Long, SituacaoApostador.Situacao> {

	private static final long serialVersionUID = 1L;

	public enum Situacao implements CaixaEnum<Long> {
		ATIVO(1L),
		BLOQUEADO(2L),
		BLOQUEADO_TOTAL(3L), 
		BLOQUEADO_PARCIAL(4L);
		
		private final Long value;
		
		private Situacao(Long value) {
			this.value = value;
		}
		
		public Long getValue() {
			return value;
		}

		public boolean isBloqueado() {
			return BLOQUEADO.equals(this);
		}

		public boolean isAtivo() {
			return ATIVO.equals(this);
		}
		
		/**
		 * @return todas as situações em que o apostador tenha sido bloqueado pelo sicow
		 */
		public static Situacao[] getSituacoesBloqueioSicow() {
			return new Situacao[]{BLOQUEADO_TOTAL, BLOQUEADO_PARCIAL};
		}

	}
	
	@Id
	@Column(name = "NU_SITUACAO_APOSTADOR", insertable=false, updatable=false)
	private Long id;
	
	@Column(name = "DE_SITUACAO_APOSTADOR", insertable=false, updatable=false)
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
		return "SituacaoApostador [id=" + id + ", descricao=" + descricao + "]";
	}
	
}
