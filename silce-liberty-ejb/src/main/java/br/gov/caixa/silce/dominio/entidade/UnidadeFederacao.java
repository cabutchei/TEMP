package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.openjpa.persistence.DataCache;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;

@Entity
@Table(name = "LCEVW001_UF", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name=UnidadeFederacao.NQ_SELECT_BY_SIGLA, 
		query="Select u from UnidadeFederacao u where u.sigla=?1"),
	@NamedQuery(name=UnidadeFederacao.NQ_SELECT_ALL_ORDER_BY_SIGLA, 
		query="Select u from UnidadeFederacao u order by u.sigla")
		
})
@DataCache(enabled = true, timeout = -1)
public class UnidadeFederacao extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_SIGLA = "UnidadeFederacao.findByUF";
	public static final String NQ_SELECT_ALL_ORDER_BY_SIGLA = "UnidadeFederacao.findAllOrdenadoBySIgla";
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "SG_UF", insertable = false, updatable = false)
	private String sigla;

	@Id
	@Column(name = "NU_UF_IBGE", insertable = false, updatable = false)
	private Long id;
	
	@Column(name = "NO_UF", insertable = false, updatable = false)
	private String nome;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		return (id == null) ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UnidadeFederacao)) {
			return false;
		}
		UnidadeFederacao other = (UnidadeFederacao) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (sigla == null) {
			if (other.sigla != null) {
				return false;
			}
		} else if (!sigla.equals(other.sigla)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = createToStringBuilder();
		builder.append("UnidadeFederacao [sigla=").append(sigla).append(", id=").append(id).append(", nome=").append(nome).append(']');
		return builder.toString();
	}

	

}
