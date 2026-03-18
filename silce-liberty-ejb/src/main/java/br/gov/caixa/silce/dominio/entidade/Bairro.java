package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.DataCache;
import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.util.StringUtil;

@Entity
@Table(name = "LCEVW003_BAIRRO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = Bairro.NQ_SELECT_BY_UF_MUNICIPIO_DVMUNICIPIO,
			query = "Select distinct b  from Bairro b JOIN FETCH b.municipio where b.municipio.id.codigoUF =?1 and b.municipio.id.numero =?2 and b.municipio.id.digitoVerificador =?3"),
		@NamedQuery(name = Bairro.NQ_SELECT_NOMES_BY_UF_MUNICIPIO_DVMUNICIPIO,
			query = "Select distinct b.nome, max(b.id) from Bairro b JOIN FETCH b.municipio "
				+ "where b.municipio.id.codigoUF =?1 and b.municipio.id.numero =?2 and b.municipio.id.digitoVerificador =?3 group by b.nome"),
		@NamedQuery(name = Bairro.NQ_SELECT_BY_NOME, query = "Select b from Bairro b "
			+ "where UPPER(TRIM(b.nome)) = UPPER(TRIM(?1)) and b.municipio.id.codigoUF =?2 and b.municipio.id.numero =?3 "
			+ "and b.municipio.id.digitoVerificador =?4 order by b.id desc, b.nome")
})
@DataCache(enabled = true, timeout = 3600000)
public class Bairro extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_UF_MUNICIPIO_DVMUNICIPIO = "Bairro.findByMunicipio";
	public static final String NQ_SELECT_NOMES_BY_UF_MUNICIPIO_DVMUNICIPIO = "Bairro.findNomesByMunicipio";
	public static final String NQ_SELECT_BY_NOME = "Bairro.findByNome";

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_CD_C01", insertable = false, updatable = false)
	private Long id;

	@Column(name = "NO_BAIRRO", insertable = false, updatable = false)
	private String nome;

	@ManyToOne(optional = false)
	@JoinColumns({
			@JoinColumn(name = "NU_DV_IBGE", referencedColumnName = "NU_DV_IBGE", insertable = false, updatable = false),
			@JoinColumn(name = "NU_MUNICIPIO_IBGE", referencedColumnName = "NU_MUNICIPIO_IBGE", insertable = false, updatable = false),
			@JoinColumn(name = "NU_UF_IBGE_L99", referencedColumnName = "NU_UF_IBGE_L99", insertable = false, updatable = false)
	})
	@EagerFetchMode(FetchMode.PARALLEL)
	private Municipio municipio;

	@Transient
	private String nomeACSII;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdUF() {
		return getMunicipio().getId().getCodigoUF();
	}

	public Long getIdMunicipio() {
		return getMunicipio().getId().getNumero();
	}

	public Long getDvMunicipio() {
		return getMunicipio().getId().getDigitoVerificador();
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
		if (!(obj instanceof Bairro)) {
			return false;
		}
		Bairro other = (Bairro) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}



	public String getNomeACSII() {
		if (nomeACSII == null) {
			nomeACSII = StringUtil.removeNonASCIICharacters(nome);
		}
		return nomeACSII;
	}

	/**
	 * @return the municipio
	 */
	public Municipio getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio
	 *            the municipio to set
	 */
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bairro [id=" + id + ", nome=" + nome + ", municipio=" + municipio + ", nomeACSII=" + nomeACSII + "]";
	}

}
