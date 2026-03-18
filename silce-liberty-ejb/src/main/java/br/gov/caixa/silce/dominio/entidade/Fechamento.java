package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.util.Data;

@Entity
@Table(name = "LCETB021_CONTROLE_FECHAMENTO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name=Fechamento.NQ_SELECT_BY_DATA, 
		query="Select f from Fechamento f where f.data=?1"),

	@NamedQuery(name=Fechamento.NQ_SELECT_ORDENADO_DATA, 
			query = "Select f from Fechamento f order by f.data desc"),

		@NamedQuery(name = Fechamento.NQ_SELECT_FECHAMENTO_ANTERIOR_NAO_FINALIZADO_BY_DATA_FECHAMENTO_ATUAL,
			query = "Select f from Fechamento f where f.data < ?1 and f.etapa.id <> ?2 order by f.data desc")
})
@NamedNativeQueries({
		@NamedNativeQuery(name = Fechamento.NQ_SELECT_DATA_MAX,
			query = "Select max(f.DT_CONTROLE_FECHAMENTO) from LCE.LCETB021_CONTROLE_FECHAMENTO f"),

		@NamedNativeQuery(name = Fechamento.NQ_SELECT_DATA_MIN,
			query = "Select min(f.DT_CONTROLE_FECHAMENTO) from LCE.LCETB021_CONTROLE_FECHAMENTO f"),

		@NamedNativeQuery(name = Fechamento.NQ_SELECT_FECHAMENTO_SEM_BATIMENTO, 
			resultClass = Fechamento.class, 
			query = "Select f.* from LCE.LCETB021_CONTROLE_FECHAMENTO f where "
				+ "(select count(*) from LCE.LCETB025_BATIMENTO_PAGAMENTO b where "
				+ "b.NU_MEIO_PAGAMENTO = ?1 and "
				+ "b.NU_CONTROLE_FECHAMENTO = f.NU_CONTROLE_FECHAMENTO and b.IC_TIPO_BATIMENTO = 'S') = 0 and "
				+ "(select count(*) from LCE.LCETB023_SALDO_DIARIO sd where "
				+ "sd.NU_MEIO_PAGAMENTO = ?1 and "
				+ "sd.NU_CONTROLE_FECHAMENTO = f.NU_CONTROLE_FECHAMENTO) > 0 "
				+ "order by f.NU_CONTROLE_FECHAMENTO asc"),

		@NamedNativeQuery(name = Fechamento.NQ_SELECT_BY_MES_ANO_ORDENADO_DATA,
			resultClass = Fechamento.class,
			query = "Select * from LCE.LCETB021_CONTROLE_FECHAMENTO f where month(f.DT_CONTROLE_FECHAMENTO) = ?1 "
				+ "and year(f.DT_CONTROLE_FECHAMENTO) = ?2 order by f.DT_CONTROLE_FECHAMENTO desc")
})
public class Fechamento extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_FECHAMENTO_SEM_BATIMENTO = "Fechamento.NQ_SELECT_FECHAMENTO_SEM_BATIMENTO";
	public static final String NQ_SELECT_BY_DATA = "Fechamento.NQ_SELECT_BY_DATA";
	public static final String NQ_SELECT_ORDENADO_DATA = "Fechamento.NQ_SELECT_ORDENADO_DATA";
	public static final String NQ_SELECT_BY_MES_ANO_ORDENADO_DATA = "Fechamento.NQ_SELECT_BY_MES_ANO_ORDENADO_DATA";
	public static final String NQ_SELECT_DATA_MAX = "Fechamento.NQ_SELECT_DATA_MAX";
	public static final String NQ_SELECT_DATA_MIN = "Fechamento.NQ_SELECT_DATA_MIN";
	public static final String NQ_SELECT_FECHAMENTO_ANTERIOR_NAO_FINALIZADO_BY_DATA_FECHAMENTO_ATUAL = "Fechamento.NQ_SELECT_FECHAMENTO_ANTERIOR_NAO_FINALIZADO_BY_DATA_FECHAMENTO_ATUAL";
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NU_CONTROLE_FECHAMENTO")
	private Long id;

	@Column(name = "DT_CONTROLE_FECHAMENTO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data data;

	@Column(name="IC_ORIGEM_FECHAMENTO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	@EagerFetchMode(FetchMode.PARALLEL)
	private TipoExecucao tipo;
	
	@Column(name = "TS_INICIO_PROCESSAMENTO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataInicioGeracao;
	
	@Column(name = "TS_FIM_PROCESSAMENTO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataFimGeracao;

	@ManyToOne
	@JoinColumn(name = "NU_ETAPA_FECHAMENTO", referencedColumnName = "NU_ETAPA_FECHAMENTO")
	@EagerFetchMode(FetchMode.PARALLEL)
	private EtapaFechamento etapa;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getDataInicioGeracao() {
		return dataInicioGeracao;
	}

	public void setDataInicioGeracao(Data dataInicioGeracao) {
		this.dataInicioGeracao = dataInicioGeracao;
	}

	public Data getDataFimGeracao() {
		return dataFimGeracao;
	}

	public void setDataFimGeracao(Data dataFimGeracao) {
		this.dataFimGeracao = dataFimGeracao;
	}

	public TipoExecucao getTipo() {
		return tipo;
	}

	public void setTipo(TipoExecucao tipo) {
		this.tipo = tipo;
	}

	public EtapaFechamento getEtapa() {
		return etapa;
	}

	public void setEtapa(EtapaFechamento etapa) {
		this.etapa = etapa;
	}

}
