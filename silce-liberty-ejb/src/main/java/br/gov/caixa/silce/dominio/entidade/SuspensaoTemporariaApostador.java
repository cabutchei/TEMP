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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler;
import br.gov.caixa.silce.dominio.openjpa.TimestampValueHandler;
import br.gov.caixa.util.Data;

/**
 * @author f656372
 */

@Entity
@Table(name = "LCETB056_SUSPENSAO_APOSTADOR", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = SuspensaoTemporariaApostador.NQ_SELECT_ULTIMA_SUSPENSAO_APOSTADOR, query = "Select suspensao From SuspensaoTemporariaApostador suspensao"
			+ " Where suspensao.apostador.id=?1 order by suspensao.id desc ") })
@NamedNativeQueries({
		@NamedNativeQuery(name = SuspensaoTemporariaApostador.NQ_SELECT_SUSPENSAO_ATIVA_APOSTADOR, query = "Select * from LCE.LCETB056_SUSPENSAO_APOSTADOR lsa WHERE lsa.NU_APOSTADOR = ?1 AND IC_SUSPENSAO_ATIVA = 'S' ORDER BY lsa.TS_INICIO_SUSPENSAO DESC LIMIT 1", resultClass = SuspensaoTemporariaApostador.class),
})
public class SuspensaoTemporariaApostador extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_ULTIMA_SUSPENSAO_APOSTADOR = "SuspensaoTemporariaApostador.NQ_SELECT_ULTIMA_SUSPENSAO_APOSTADOR";
	public static final String NQ_SELECT_SUSPENSAO_ATIVA_APOSTADOR = "SuspensaoTemporariaApostador.NQ_SELECT_SUSPENSAO_APOSTADOR";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NU_SUSPENSAO_APOSTADOR")
	@SequenceGenerator(name = "NU_SUSPENSAO_APOSTADOR", sequenceName = "LCE.LCESQ056_NU_SSPNO_APOSTADOR", allocationSize = 1)
	@Column(name = "NU_SUSPENSAO_APOSTADOR")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NU_APOSTADOR", referencedColumnName = "NU_APOSTADOR")
	private Apostador apostador;

	@Column(name = "TS_INICIO_SUSPENSAO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataSolicitacaoSuspensao;

	@Column(name = "TS_FIM_SUSPENSAO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataFimSuspensao;

	@Column(name = "DE_PRAZO_SUSPENSAO")
	private String prazoSuspensao;

	@Column(name = "IC_SUSPENSAO_ATIVA")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean suspenso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Apostador getApostador() {
		return apostador;
	}

	public void setApostador(Apostador apostador) {
		this.apostador = apostador;
	}

	public Data getDataSolicitacaoSuspensao() {
		return dataSolicitacaoSuspensao;
	}

	public void setDataSolicitacaoSuspensao(Data dataSolicitacaoSuspensao) {
		this.dataSolicitacaoSuspensao = dataSolicitacaoSuspensao;
	}

	public Data getDataFimSuspensao() {
		return dataFimSuspensao;
	}

	public void setDataFimSuspensao(Data dataFimSuspensao) {
		this.dataFimSuspensao = dataFimSuspensao;
	}

	public String getPrazoSuspensao() {
		return prazoSuspensao;
	}

	public void setPrazoSuspensao(String prazoSuspensao) {
		this.prazoSuspensao = prazoSuspensao;
	}

	public Boolean getSuspenso() {
		return suspenso;
	}

	public void setSuspenso(Boolean suspenso) {
		this.suspenso = suspenso;
	}

}
