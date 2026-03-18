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
@Table(name = "LCETB024_TIPO_DIVERGENCIA", schema = DatabaseConfig.SCHEMA)
@DataCache(enabled = true, timeout = -1)
public class TipoDivergenciaBatimento extends AbstractEntidadeEnum<Long, TipoDivergenciaBatimento.Tipo> {

	private static final long serialVersionUID = 1L;

	public enum Tipo implements CaixaEnum<Long> {
		VALOR_MAIOR(1L),
		VALOR_MENOR(2L),
		NAO_ENCONTRADA(3L),
		COMISSAO_MAIOR(4L),
		COMISSAO_MENOR(5L),
		SITUACAO_INVALIDA(6L);

		private final Long value;

		private Tipo(Long value) {
			this.value = value;
		}

		public Long getValue() {
			return value;
		}
		
	}

	@Id
	@Column(name = "NU_TIPO_DIVERGENCIA", insertable = false, updatable = false)
	private Long id;

	@Column(name = "DE_TIPO_DIVERGENCIA", insertable = false, updatable = false)
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
	public String toString() {
		return "TipoDivergenciaBatimento [id=" + id + ", descricao=" + descricao + "]";
	}

	@Override
	protected Tipo[] createValores() {
		return Tipo.values();
	}

}
