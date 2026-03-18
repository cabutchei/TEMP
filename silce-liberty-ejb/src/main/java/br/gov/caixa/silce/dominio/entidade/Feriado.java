package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.util.Data;

/**
 * Entidade que representa os feriados nacionais
 * @author c101482
 *
 */
@Entity
@Table(name = "LCEVW006_FERIADO", schema = DatabaseConfig.SCHEMA)
public class Feriado extends AbstractEntidade<Data> {

	public static final String NQ_SELECT_BY_DATA = "Feriado.NQ_SELECT_BY_DATA";
	
	private static final long serialVersionUID = 1L;

	@Id
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FERIADO", insertable = false, updatable = false)
	private Data id;

	@Column(name = "NO_FERIADO", insertable = false, updatable = false)
	private String nome;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public Data getId() {
		return id;
	}

	@Override
	public void setId(Data id) {
		this.id = id;
		
	}

}
