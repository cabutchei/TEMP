package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

@Entity
@Table(name = "LCEMTV44_ARRECADACAO", schema = DatabaseConfig.SCHEMA)

@NamedQueries({
		@NamedQuery(name = Arrecadacao.NQ_SELECT_BY_PERIOD, query = "SELECT SUM(ar.arrecadacaoAcumulada) + 0.0" +
			" FROM Arrecadacao ar" +
			" WHERE ar.dataBase >= ?1 AND ar.dataBase <= ?2")
})
public class Arrecadacao extends AbstractEntidade<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NQ_SELECT_BY_PERIOD = "Arrecadacao.NQ_SELECT_BY_PERIOD";

	private Long id;
	@Column(name = "VR_ARRECADACAO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal arrecadacaoAcumulada;

	@Column(name = "DT_ENCERRAMENTO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataBase;

	public Decimal getArrecadacaoAcumulada() {
		return arrecadacaoAcumulada;
	}

	public void setArrecadacaoAcumulada(Decimal arrecadacaoAcumulada) {
		this.arrecadacaoAcumulada = arrecadacaoAcumulada;
	}

	public Data getDataBase() {
		return dataBase;
	}

	public void setDataBase(Data dataBase) {
		this.dataBase = dataBase;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
