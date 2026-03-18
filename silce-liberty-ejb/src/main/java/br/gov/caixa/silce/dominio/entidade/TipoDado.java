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
@Table(name = "LCETB006_TIPO_DADO", schema = DatabaseConfig.SCHEMA)
@DataCache(enabled = true, timeout = -1)
public class TipoDado extends AbstractEntidadeEnum<Long, TipoDado.Tipo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Tipo implements CaixaEnum<Long> {
		BOOLEANO(1L),
		DATA(2L),
		DECIMAL(3L),
		INTEIRO(4L),
		MONETARIO(5L),
		STRING(6L),
		TIMESTAMP(7L),
		HORA(8L),
		NAO_SE_APLICA(9L),
		INTERVALO_HORA(10L),
		INTERVALO_DATA(11L),
		MODALIDADE_BLOQUEADA_CANAL(12L)
		;
		
		
		private final Long value;
		
		private Tipo(Long value) {
			this.value = value;
		}
		
		public Long getValue() {
			return value;
		}

	}
	
	@Id
	@Column(name = "NU_TIPO_DADO")
	private Long id;
	
	
	@Column(name = "DE_TIPO_DADO")
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
	protected Tipo[] createValores() {
		return Tipo.values();
	}

	public boolean isMonetario() {
		return Tipo.MONETARIO.getValue().equals(getId());
	}

	public boolean isDecimal() {
		return Tipo.DECIMAL.getValue().equals(getId());
	}
}
