package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.openjpa.persistence.DataCache;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.util.CaixaEnum;

@Entity
@Table(name = "LCETB005_TIPO_PARAMETRO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name = TipoParametro.NQ_SELECT_BY_IDS,
			query = "Select tipo From TipoParametro tipo Where tipo.id in (?1)")
})
@DataCache(enabled = true, timeout = -1)
public class TipoParametro extends AbstractEntidadeEnum<Long, TipoParametro.Tipo> {

	public static final String NQ_SELECT_BY_IDS = "nq.tipoParametro.byIds";
	
	private static final long serialVersionUID = 1L;
	
	public enum Tipo implements CaixaEnum<Long> {
		
		PARAMETRO_DE_COMERCIALIZACAO(1L),
		PARAMETRO_DE_SEGURANCA(2L),
		PARAMETRO_DE_SISTEMA(3L),
		PARAMETRO_DE_APP(4L),
		PARAMETRO_DE_MODALIDADE(5L),
		PARAMETRO_DE_TIMEOUT(6L);

		private final Long value;
		
		private Tipo(Long value){
			this.value = value;
		}
		
		public Long getValue(){
			return value;
		}
	}

	@Column(name="DE_TIPO_PARAMETRO")
	private String descricao;
	
	@Id
	@Column(name = "NU_TIPO_PARAMETRO")
	private Long id;
	
	public Long getId() {
		return id;
	}

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

}
