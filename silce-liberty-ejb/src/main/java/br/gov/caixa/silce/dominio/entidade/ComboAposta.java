package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;

@Entity
@Table(name = "LCETB046_COMBO_APOSTA", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(
		name = ComboAposta.NQ_SELECT_BY_ID,
			query = "SELECT comboAposta FROM ComboAposta WHERE comboAposta.id = ?1"),
	@NamedQuery(name = ComboAposta.NQ_DELETE_BY_LIST_ID, 
			query = "delete FROM ComboAposta combo WHERE combo.id in (?1)")
})
@NamedNativeQueries({
	@NamedNativeQuery(name = ComboAposta.NQ_DELETE_BY_APOSTA_COMPRA, 
			query = "DELETE FROM LCE.LCETB046_COMBO_APOSTA combo WHERE combo.NU_COMBO_APOSTA IN "
				+ "(SELECT NU_COMBO_APOSTA FROM LCE.LCETB011_APOSTA aposta where aposta.NU_COMPRA = ?1 GROUP BY NU_COMBO_APOSTA)")
})
public class ComboAposta extends AbstractEntidade<Long> {
	
	public static final String NQ_SELECT_BY_ID = "ComboAposta.NQ_SELECT_BY_ID";
	public static final String NQ_DELETE_BY_APOSTA_COMPRA = "ComboAposta.NQ_DELETE_BY_APOSTA_COMPRA";
	public static final String NQ_DELETE_BY_LIST_ID = "ComboAposta.NQ_DELETE_BY_LIST_ID";
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_COMBO_APOSTA")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "NU_TIPO_COMBO")
	private TipoCombo tipoCombo;

	@Column(name = "NU_MES")
	private Long mes;

	@Column(name = "NU_PARTICAO")
	private Long particao;

	@Transient
	private String dataInclusao;

	public ComboAposta() {
		super();
	}

	public ComboAposta(TipoCombo tipoCombo) {
		this.tipoCombo = tipoCombo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTipoCombo(TipoCombo tipoCombo) {
		this.tipoCombo = tipoCombo;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	public void setParticao(Long particao) {
		this.particao = particao;
	}

	public Long getId() {
		return id;
	}

	public TipoCombo getTipoCombo() {
		return tipoCombo;
	}

	public Long getMes() {
		return mes;
	}

	public Long getParticao() {
		return particao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mes == null) ? 0 : mes.hashCode());
		result = prime * result + ((particao == null) ? 0 : particao.hashCode());
		result = prime * result + ((tipoCombo == null) ? 0 : tipoCombo.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		ComboAposta other = (ComboAposta) obj;
		if (dataInclusao == null) {
			if (other.dataInclusao != null) {
				return false;
			}
		} else if (!dataInclusao.equals(other.dataInclusao)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (mes == null) {
			if (other.mes != null) {
				return false;
			}
		} else if (!mes.equals(other.mes)) {
			return false;
		}
		if (particao == null) {
			if (other.particao != null) {
				return false;
			}
		} else if (!particao.equals(other.particao)) {
			return false;
		}
		if (tipoCombo != other.tipoCombo) {
			return false;
		}
		return true;
	}

	public String getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(String dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
}
