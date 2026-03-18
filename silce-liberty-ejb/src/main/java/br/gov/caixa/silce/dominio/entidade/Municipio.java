package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;

import org.apache.openjpa.persistence.DataCache;
import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;

@Entity
@Table(name = "LCEVW002_MUNICIPIO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name=Municipio.NQ_SELECT_BY_SIGLAUF_NOME, 
			query = "Select m from Municipio m join fetch m.uf where m.uf.sigla=?1 order by m.nome")
	, @NamedQuery(name=Municipio.NQ_SELECT_BY_UF_NOME, 
			query = "Select m from Municipio m where m.uf.id=?1 order by m.nome"),
		@NamedQuery(name = Municipio.NQ_SELECT_BY_UF_MUNICIPIO, query = "Select m from Municipio m where m.id.numero=?1 and m.uf.id=?2")
})
@DataCache(enabled = true, timeout = 3600000)
public class Municipio extends AbstractEntidade<MunicipioPK> {
	
	public static final String NQ_SELECT_BY_SIGLAUF_NOME = "Municipio.findByUF";
	public static final String NQ_SELECT_BY_UF_NOME = "Municipio.findByIdUF";
	public static final String NQ_SELECT_BY_UF_MUNICIPIO = "Municipio.findByMunicipioUf";
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private MunicipioPK id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="NU_UF_IBGE_L99", insertable = false, updatable = false)
	@EagerFetchMode(FetchMode.PARALLEL)
	private UnidadeFederacao uf;
	
	@Column(name = "NO_MUNICIPIO", insertable = false, updatable = false)
	private String nome;

	public MunicipioPK getId() {
		return id;
	}

	public void setId(MunicipioPK id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public UnidadeFederacao getUf() {
		return uf;
	}

	public void setUf(UnidadeFederacao uf) {
		this.uf = uf;
	}
	
	@PostLoad
	protected void repairNome() {
		nome = nome.trim();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Municipio)) {
			return false;
		}
		Municipio other = (Municipio) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = createToStringBuilder();
		builder.append("Municipio [id=");
		builder.append(id);
		builder.append(", uf=");
		builder.append(uf);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(']');
		return builder.toString();
	}

}
