package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.TimestampValueHandler;
import br.gov.caixa.util.Data;

@Entity
@Table(name = "LCETB048_AGENDAMENTO_NOTIFICACAO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(
		name = AgendamentoNotificacao.NQ_SELECT_BY_ID,
		query = "SELECT agendamento from AgendamentoNotificacao agendamento WHERE agendamento.id = ?1"
	),
	@NamedQuery(
		name = AgendamentoNotificacao.NQ_SELECT_ORDEM_DESC,
		query = "SELECT agendamento from AgendamentoNotificacao agendamento ORDER BY agendamento.id DESC"
	),
	@NamedQuery(
		name = AgendamentoNotificacao.NQ_SELECT_BY_APOSTADOR_NAO_CADASTRADAS,
		query = "SELECT agendamento FROM AgendamentoNotificacao agendamento"
		+ " WHERE agendamento.dataInibicao IS NULL"
		+ " AND ?1 >= agendamento.dataInicioVigencia AND (?1 <= agendamento.dataFimVigencia OR agendamento.dataFimVigencia IS NULL)"
		+ " AND agendamento.id NOT IN"
		+ "(?2) ORDER BY agendamento.dataInicioVigencia DESC"
	)
})
@NamedNativeQueries({

})
public class AgendamentoNotificacao extends AbstractEntidade<Long> {
	private static final long serialVersionUID = 1L;

	public static final String NQ_SELECT_BY_ID = "AgendamentoNotificacao.NQ_SELECT_BY_ID";
	public static final String NQ_SELECT_ORDEM_DESC = "AgendamentoNotificacao.NQ_SELECT_ORDEM_DESC";
	public static final String NQ_SELECT_BY_APOSTADOR_NAO_CADASTRADAS = "AgendamentoNotificacao.NQ_SELECT_BY_APOSTADOR_NAO_CADASTRADAS";


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_AGENDAMENTO_NOTIFICACAO")
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "NU_MODELO_NOTIFICACAO", referencedColumnName = "NU_MODELO_NOTIFICACAO")
	private ModeloNotificacao modelo;

	@Column(name = "TS_INICIO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataInicioVigencia;

	@Column(name = "TS_FIM")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataFimVigencia;

	@Column(name = "TS_INIBICAO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataInibicao;

	@Column(name = "TS_CRIACAO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataCriacao;

	@Column(name = "TS_ALTERACAO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataAlteracao;

	public ModeloNotificacao getModeloNotificacao() {
		return modelo;
	}

	public void setModeloNotificacao(ModeloNotificacao modelo) {
		this.modelo = modelo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Data getDataInibicao() {
		return dataInibicao;
	}

	public void setDataInibicao(Data dataInibicao) {
		this.dataInibicao = dataInibicao;
	}

	public Data getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Data dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Data getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Data dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Data getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(Data dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public Data getDataFimVigencia() {
		return dataFimVigencia;
	}

	public void setDataFimVigencia(Data dataFimVigencia) {
		this.dataFimVigencia = dataFimVigencia;
	}
	
}
