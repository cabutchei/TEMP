package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.CPFValueHandler;
import br.gov.caixa.util.CPF;

@Entity
@Table(name = "LCETB004_CPF_HABILITADO_PORTAL", schema = DatabaseConfig.SCHEMA)
public class CPFHabilitado extends AbstractEntidade<CPF> {

	private static final long serialVersionUID = 989041624807244370L;

	@Id
	@Strategy(CPFValueHandler.STRATEGY_NAME)
	@Column(name = "NU_CPF_HBLTO_PORTAL_APOSTADOR")
	private CPF id;

	public CPF getId() {
		return id;
	}

	public void setId(CPF id) {
		this.id = id;
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
		if (!(obj instanceof CPFHabilitado)) {
			return false;
		}
		CPFHabilitado other = (CPFHabilitado) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
