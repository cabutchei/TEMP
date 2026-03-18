package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler;
import br.gov.caixa.util.Data;

@Entity
@Table(name = "LCETB028_ENVIO_FECHAMENTO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name=EnvioFechamento.NQ_SELECT_BY_DATA_SISTEMA, 
			query = "Select ef from EnvioFechamento ef join fetch ef.fechamento join fetch ef.sistemaFechamento where ef.fechamento.data=?1 and ef.sistemaFechamento.id = ?2"),
	@NamedQuery(name=EnvioFechamento.NQ_SELECT_BY_DATA, 
		query = "Select ef from EnvioFechamento ef join fetch ef.fechamento join fetch ef.sistemaFechamento where ef.fechamento.data=?1 and ef.sistemaFechamento.id = ?2"),
	@NamedQuery(name=EnvioFechamento.NQ_SELECT_BY_SISTEMA, 
			query = "Select ef from EnvioFechamento ef join fetch ef.fechamento join fetch ef.sistemaFechamento where ef.sistemaFechamento.id = ?1 order by ef.fechamento.data desc, ef.dataEnvio desc")
})
public class EnvioFechamento extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 989041624807244370L;
	
	public static final String NQ_SELECT_BY_DATA_SISTEMA = "EnvioFechamento.NQ_SELECT_BY_DATA_SISTEMA";
	public static final String NQ_SELECT_BY_DATA = "EnvioFechamento.NQ_SELECT_BY_DATA";
	public static final String NQ_SELECT_BY_SISTEMA = "EnvioFechamento.NQ_SELECT_BY_SISTEMA";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NU_ENVIO_FECHAMENTO")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NU_SISTEMA_FECHAMENTO", referencedColumnName = "NU_SISTEMA_FECHAMENTO")
	@EagerFetchMode(FetchMode.PARALLEL)
	private SistemaFechamento sistemaFechamento;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_CONTROLE_FECHAMENTO", referencedColumnName = "NU_CONTROLE_FECHAMENTO")
	private Fechamento fechamento;
	
	@Column(name="NO_ARQUIVO_ENVIO")
	private String nomeArquivo;
	
	@Column(name="DE_CONTEUDO_HASH")
	private String hashConteudo;
	
	@Column(name = "TS_ENVIO_FECHAMENTO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataEnvio;

	@Column(name = "IC_ENVIO_FECHAMENTO_CONCLUIDO")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean envioFechamentoConcluido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SistemaFechamento getSistemaFechamento() {
		return sistemaFechamento;
	}

	public void setSistemaFechamento(SistemaFechamento sistemaFechamento) {
		this.sistemaFechamento = sistemaFechamento;
	}

	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getHashConteudo() {
		return hashConteudo;
	}

	public void setHashConteudo(String hashConteudo) {
		this.hashConteudo = hashConteudo;
	}

	public Data getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Data dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Boolean getEnvioFechamentoConcluido() {
		return envioFechamentoConcluido;
	}

	public void setEnvioFechamentoConcluido(Boolean envioFechamentoConcluido) {
		this.envioFechamentoConcluido = envioFechamentoConcluido;
	}

}
