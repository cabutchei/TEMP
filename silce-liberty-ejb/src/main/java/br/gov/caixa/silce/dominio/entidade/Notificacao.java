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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.TimestampValueHandler;
import br.gov.caixa.util.Data;

// TODO: Mudar as queries para ordenar recuperar por Data de Vigência

@Entity
@Table(name = "LCETB049_NOTIFICACAO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(
		name = Notificacao.NQ_SELECT_BY_ID,
		query = "SELECT notificacao FROM Notificacao notificacao WHERE notificacao.id = ?1"
	),
	@NamedQuery(
		name = Notificacao.NQ_COUNT_ALL_NAOLIDAS_BY_APOSTADOR,
			query = "SELECT COUNT(notificacao) FROM Notificacao notificacao JOIN FETCH notificacao.agendamentoNotificacao WHERE notificacao.apostador.id = ?1 AND notificacao.agendamentoNotificacao.dataInibicao IS NULL AND notificacao.dataVisualizacao IS NULL AND notificacao.dataExclusao IS NULL"
	),
	@NamedQuery(
		name = Notificacao.NQ_SELECT_NAOLIDA_BY_APOSTADOR,
			query = "SELECT notificacao FROM Notificacao notificacao JOIN FETCH notificacao.agendamentoNotificacao JOIN FETCH notificacao.agendamentoNotificacao.modelo"
				+ " WHERE (notificacao.dataVisualizacao IS NULL AND notificacao.apostador.id = ?1 AND notificacao.dataExclusao IS NULL AND notificacao.agendamentoNotificacao.dataInibicao IS NULL AND (notificacao.agendamentoNotificacao.dataFimVigencia >= ?2 OR notificacao.agendamentoNotificacao.dataFimVigencia IS NULL)  AND (notificacao.agendamentoNotificacao.modelo.subcanal = ?3 OR notificacao.agendamentoNotificacao.modelo.subcanal IS NULL)) ORDER BY notificacao.agendamentoNotificacao.dataInicioVigencia DESC"
	),
	@NamedQuery(
		name = Notificacao.NQ_SELECT_ALL_NAOEXCLUIDAS_BY_APOSTADOR,
			query = "SELECT notificacao FROM Notificacao notificacao"
				+ " JOIN FETCH notificacao.agendamentoNotificacao"
				+ " JOIN FETCH notificacao.agendamentoNotificacao.modelo"
				+ " WHERE notificacao.apostador.id = ?1 AND (notificacao.agendamentoNotificacao.dataInibicao IS NULL"
				+ " AND (notificacao.agendamentoNotificacao.dataFimVigencia >= ?2 OR notificacao.agendamentoNotificacao.dataFimVigencia IS NULL)"
				+ " AND notificacao.dataExclusao IS NULL)"
				+ " AND (notificacao.agendamentoNotificacao.modelo.subcanal = ?3 OR notificacao.agendamentoNotificacao.modelo.subcanal IS NULL)"
				+ " ORDER BY notificacao.agendamentoNotificacao.dataInicioVigencia DESC"
	),
	@NamedQuery(
			name = Notificacao.NQ_SELECT_ALL_NAOVISUALIZADAS_BY_APOSTADOR, query = "SELECT notificacao FROM Notificacao notificacao"
				+ " JOIN FETCH notificacao.agendamentoNotificacao"
				+ " JOIN FETCH notificacao.agendamentoNotificacao.modelo"
				+ " WHERE notificacao.apostador.id = ?1 AND (notificacao.agendamentoNotificacao.dataInibicao IS NULL"
				+ " AND (notificacao.agendamentoNotificacao.dataFimVigencia >= ?2 OR notificacao.agendamentoNotificacao.dataFimVigencia IS NULL)"
				+ " AND notificacao.dataExclusao IS NULL AND notificacao.dataVisualizacao IS NULL)"
				+ " AND (notificacao.agendamentoNotificacao.modelo.subcanal = ?3 OR notificacao.agendamentoNotificacao.modelo.subcanal IS NULL)"
				+ " ORDER BY notificacao.agendamentoNotificacao.dataInicioVigencia DESC"),
		@NamedQuery(
			name = Notificacao.NQ_SELECT_ALLIDS_BY_APOSTADOR, query = "SELECT notificacao.agendamentoNotificacao.id FROM Notificacao notificacao JOIN FETCH notificacao.agendamentoNotificacao"
				+ " WHERE notificacao.apostador.id = ?1"),
		@NamedQuery(
		name = Notificacao.NQ_UPDATE_INIBICAO_ALL_NOTIFICACAO,
			query = "UPDATE Notificacao notificacao SET notificacao.dataExclusao = ?1"
		+ " WHERE notificacao.agendamentoNotificacao.id = ?2"
	),
	@NamedQuery(
		name = Notificacao.NQ_UPDATE_ALL_NAOEXCLUIDAS_BY_APOSTADOR,
			query = "UPDATE Notificacao notificacao SET notificacao.dataExclusao = ?1"
				+ " WHERE notificacao.apostador.id = ?2 AND notificacao.dataExclusao IS NULL"
				+ " AND (notificacao.agendamentoNotificacao.modelo.subcanal = ?3 OR notificacao.agendamentoNotificacao.modelo.subcanal IS NULL)"
	)
})
@NamedNativeQueries({

})
public class Notificacao extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_ID = "Notificacao.NQ_SELECT_BY_ID";
	public static final String NQ_COUNT_ALL_NAOLIDAS_BY_APOSTADOR = "Notificacao.NQ_COUNT_ALL_NAOLIDAS_BY_APOSTADOR";
	public static final String NQ_SELECT_NAOLIDA_BY_APOSTADOR = "Notificacao.NQ_SELECT_NAOLIDA_BY_APOSTADOR";
	public static final String NQ_SELECT_ALL_NAOEXCLUIDAS_BY_APOSTADOR = "Notificacao.NQ_SELECT_ALL_NAOEXCLUIDAS_BY_APOSTADOR";
	public static final String NQ_SELECT_ALL_NAOVISUALIZADAS_BY_APOSTADOR = "Notificacao.NQ_SELECT_ALL_NAOVISUALIZADAS_BY_APOSTADOR";
	public static final String NQ_SELECT_ALLIDS_BY_APOSTADOR = "Notificacao.NQ_SELECT_ALLIDS_BY_APOSTADOR";
	public static final String NQ_UPDATE_INIBICAO_ALL_NOTIFICACAO = "Notificacao.NQ_UPDATE_INIBICAO_NOTIFICACAO";
	public static final String NQ_UPDATE_ALL_NAOEXCLUIDAS_BY_APOSTADOR = "Notificacao.NQ_UPDATE_ALL_NAOEXCLUIDAS_BY_APOSTADOR";
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_NOTIFICACAO")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "NU_AGENDAMENTO_NOTIFICACAO", referencedColumnName = "NU_AGENDAMENTO_NOTIFICACAO")
	private AgendamentoNotificacao agendamentoNotificacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_APOSTADOR", referencedColumnName = "NU_APOSTADOR")
	private Apostador apostador;

	@Column(name = "TS_VISUALIZACAO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataVisualizacao;

	@Column(name = "TS_EXCLUSAO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataExclusao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AgendamentoNotificacao getAgendamentoNotificacao() {
		return agendamentoNotificacao;
	}

	public void setAgendamentoNotificacao(AgendamentoNotificacao agendamentoNotificacao) {
		this.agendamentoNotificacao = agendamentoNotificacao;
	}

	public Apostador getApostador() {
		return apostador;
	}

	public void setApostador(Apostador apostador) {
		this.apostador = apostador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Data getDataVisualizacao() {
		return dataVisualizacao;
	}

	public void setDataVisualizacao(Data dataVisualizacao) {
		this.dataVisualizacao = dataVisualizacao;
	}

	public Data getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Data dataExclusao) {
		this.dataExclusao = dataExclusao;
	}
}
