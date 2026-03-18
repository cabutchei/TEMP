package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

@Entity
@Table(name = "LCETB042_TIPO_LANCAMENTO", schema = DatabaseConfig.SCHEMA)
public class TipoLancamento extends AbstractEntidadeEnum<Long, TipoLancamento.Tipo> {

	private static final long serialVersionUID = 1L;

	public enum Tipo implements CaixaEnum<Long> {
		DEBITO(1L),
		CREDITO(2L); 

		private final Long value;

		private Tipo(Long value) {
			this.value = value;
		}

		public Long getValue() {
			return value;
		}
		
		public static Tipo getByValue(Long value) {
			return EnumUtil.recupereByValue(values(), value);
		}
		
	}

	@Id
	@Column(name = "NU_TIPO_LANCAMENTO", insertable = false, updatable = false)
	private Long id;

	@Column(name = "DE_TIPO_LANCAMENTO", insertable = false, updatable = false)
	private String descricao;

	@Column(name = "IC_TIPO_LANCAMENTO", insertable = false, updatable = false)
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Tipo tipo;

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
	protected Tipo[] createValores() {
		return Tipo.values();
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}
