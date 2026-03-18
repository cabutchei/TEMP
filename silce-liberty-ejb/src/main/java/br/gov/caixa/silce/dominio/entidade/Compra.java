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
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.ExternalReferenceUtil;
import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.entidade.Operacao.OperacaoEnum;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.silce.dominio.openjpa.HoraValueHandler;
import br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler;
import br.gov.caixa.silce.dominio.openjpa.TimestampValueHandler;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.StringUtil;

/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 * @author c127237 - Rinaldo Pitzer Junior
 */
@Entity
@Table(name = "LCETB013_COMPRA", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = Compra.NQ_SELECT_BY_APOSTADOR_SITUACAO,
			query = "Select compra From Compra compra Where compra.apostador.id=?1 and compra.situacao.id=?2"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_COMPRA_APOSTADOR, 
			query = "Select compra From Compra compra Where compra.id = ?1 and compra.apostador.id=?2"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_SITUACAO, 
			query = "Select compra From Compra compra Where compra.situacao.id=?1"),

		@NamedQuery(name = Compra.NQ_SELECT_IDS_BY_SITUACAO_WITH_UR, 
			query = "Select compra.id From Compra compra Where compra.situacao.id=?1",
			hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }),

		@NamedQuery(name = Compra.NQ_SELECT_BY_NSU_DATA,
			query = "Select compra From Compra compra Where compra.nsu=?1 and compra.dataInicioCompra=?2"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_NSU_ULTIMA_DATA,
			query = "Select compra From Compra compra Where compra.nsu=?1 and compra.dataInicioCompra <= ?2 order by compra.dataInicioCompra desc"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_APOSTA_NSU,
			query = "Select compra From ApostaComprada ac join ac.aposta a join a.compra compra Where ac.nsuTransacao = ?1 AND ac.dataInicioApostaComprada=?2"),

		@NamedQuery(name = Compra.NQ_SELECT_ID_BY_EM_PROCESSAMENTO_DATAULTIMA_ALTERACAO_MAX_WITH_UR, 
			query = "Select compra.id From Compra compra Where compra.situacao.id=?1 and compra.dataUltimaAlteracao <= ?2", hints = {
					@QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }),

		@NamedQuery(name = Compra.NQ_SELECT_BY_APOSTADOR_DATAINICIOCOMPRAMAXMIX,
			query = "Select entidade From Compra entidade Where entidade.apostador.id=?1 and entidade.dataInicioCompra >= ?2 and entidade.dataInicioCompra <= ?3 order by entidade.dataInicioCompra desc, entidade.horaInicioCompra desc"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_APOSTADOR_DATAINICIOCOMPRAMAXMIX_MEIO,
			query = "Select entidade From Compra entidade Where entidade.apostador.id=?1 and entidade.dataInicioCompra >= ?2 and entidade.dataInicioCompra <= ?3 and entidade.meioPagamento.id = ?4 order by entidade.dataInicioCompra desc, entidade.horaInicioCompra desc"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_APOSTADOR_DATAINICIOCOMPRAMAXMIX_SITUACAO,
			query = "Select entidade From Compra entidade Where entidade.apostador.id=?1 and entidade.dataInicioCompra >= ?2 and entidade.dataInicioCompra <= ?3 and entidade.situacao.id = ?4 order by entidade.dataInicioCompra desc, entidade.horaInicioCompra desc"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_APOSTADOR_DATAINICIOCOMPRAMAXMIX_MEIO_SITUACAO,
			query = "Select entidade From Compra entidade Where entidade.apostador.id=?1 and entidade.dataInicioCompra >= ?2 and entidade.dataInicioCompra <= ?3 and entidade.meioPagamento.id = ?4 and entidade.situacao.id = ?5 order by entidade.dataInicioCompra desc, entidade.horaInicioCompra desc"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_DATA_DEBITO_MEIO_PAGAMENTO_SITUACOES,
			query = "Select compra From Compra compra where compra.dataEfetivacaoDebito = ?1 and compra.meioPagamento.id  = ?2 and compra.situacao.id in (?3) order by compra.id asc"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_DATA_FINALIZACAO_MEIO_PAGAMENTO_SITUACOES,
			query = "Select compra From Compra compra where compra.dataFinalizacaoCompra = ?1 and compra.meioPagamento.id  = ?2 and compra.situacao.id in (?3) order by compra.id asc"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_DATAINICIO_DATAFINALIZACAOMIN,
			query = "Select entidade From Compra entidade Where entidade.dataInicioCompra=?1 and (entidade.dataFinalizacaoCompra>?2 or entidade.dataFinalizacaoCompra is null)"
				+ "  and entidade.situacao.id <> ?3"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_COMPRA_COM_LOTERICA_APOSTADOR,
			query = "Select entidade From Compra entidade "
				+ "join fetch entidade.loterica "
				+ "join fetch entidade.apostador "
				+ "left join fetch entidade.meioPagamento "
				+ "where entidade.id = ?1"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_ULTIMA_COMPRA, 
			query = "Select compra From Compra compra join fetch compra.meioPagamento Where compra.apostador.id=?1 and compra.situacao.id in (?2, ?3, ?4) order by compra.id desc"),

		@NamedQuery(name = Compra.NQ_SELECT_COUNT_COMPRAS_EM_PROCESSAMENTO, 
			query = "Select count(compra.id) from Compra compra where compra.apostador.id=?1 and compra.situacao.id in (?2)"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_COMPRAS_ULTIMO_DIA, query = "Select compra From Compra compra Where compra.apostador.id=?1 and compra.dataUltimaAlteracao >= ?2 and compra.situacao.id in (6, 7, 8, 9, 13, 14) order by compra.id desc"),

		@NamedQuery(name = Compra.NQ_SELECT_BY_SITUACAO_COMPRA_PGTO_NAO_IDENTIFICADO_WITH_UR, query = "Select compra From Compra compra join fetch compra.meioPagamento Where compra.situacao.id = ?1", hints = {
				@QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") })
})

@NamedNativeQueries({
		@NamedNativeQuery(name = Compra.NQ_UPDATE_PARTICAO,
			query = "update LCE.LCETB013_COMPRA "
				+ "set NU_PARTICAO = MOD(NU_COMPRA, 10)"),

		@NamedNativeQuery(name = Compra.NQ_SELECT_DATA_FINALIZACAO_MIN_BY_SITUACAO,
			query = "SELECT MIN(t0.DT_FINALIZACAO_COMPRA) "
				+ "FROM LCE.LCETB013_COMPRA t0 "
				+ "WHERE t0.NU_SITUACAO_COMPRA IN (6, 8)"),

		@NamedNativeQuery(name = Compra.NQ_UPDATE_MES,
			query = "update LCE.LCETB013_COMPRA set "
				+ "NU_MES = MONTH (DT_INICIO_COMPRA) WHERE DT_INICIO_COMPRA IS NOT NULL"),

		@NamedNativeQuery(name = Compra.NQ_SELECT_SUMARIZACAO_BY_APOSTADOR_DATA,
			query = "select nu_situacao_compra as situacao, count(*) as quantidade "
				+ "from LCE.LCETB013_COMPRA  "
				+ "where nu_apostador = ?1 and dt_inicio_compra >= ?2 group by nu_situacao_compra"),

		@NamedNativeQuery(name = Compra.NQ_SELECT_COUNT_COMPRAS_NEGADAS,
			query = "(SELECT COALESCE(c.IC_SUBCANAL_PAGAMENTO, 1) AS SUBCANAL,"
				+ " COUNT(COALESCE(c.IC_SUBCANAL_PAGAMENTO, 1)) AS VALOR_SUBCANAL"
				+ " FROM LCE.LCETB013_COMPRA c"
				+ " WHERE c.NU_SITUACAO_COMPRA = 3 AND c.TS_ALTERACAO_SITUACAO BETWEEN ?1 AND ?2 GROUP BY c.IC_SUBCANAL_PAGAMENTO) WITH UR"),

		@NamedNativeQuery(name = Compra.NQ_SELECT_COUNT_CARRINHOS_ABANDONADOS,
			query = "SELECT CONSULTA.SUBCANAL, COUNT (CONSULTA.SUBCANAL) AS VALOR_SUBCANAL "
				+ "FROM (SELECT MAX(ap.TS_INCLUSAO_APOSTA_CARRINHO) AS datas, "
				+ "COALESCE(ap.IC_SUBCANAL_APOSTA, 1) AS subcanal FROM LCE.LCETB011_APOSTA "
				+ "ap INNER JOIN LCE.LCETB013_COMPRA c on c.NU_COMPRA = ap.NU_COMPRA "
				+ "WHERE c.NU_SITUACAO_COMPRA = 1 AND "
				+ "ap.TS_INCLUSAO_APOSTA_CARRINHO >= ?1 "
				+ "GROUP BY ap.NU_COMPRA, ap.IC_SUBCANAL_APOSTA) AS CONSULTA "
				+ "WHERE CONSULTA.datas <= ?2 GROUP BY SUBCANAL WITH UR"),

		@NamedNativeQuery(name = Compra.NQ_SELECT_MEDIA_DIARIA_BY_APOSTADOR_DATA,
			query = "select avg(cpd.soma_por_dia) from ( "
				+ "select day(dt_inicio_compra) as dia, month(dt_inicio_compra) as mes, SUM(VR_TOTAL) as soma_por_dia  "
				+ "from LCE.LCETB013_COMPRA where nu_apostador = ?1 and dt_inicio_compra >= ?2 group by month(dt_inicio_compra),day(dt_inicio_compra) "
				+ ") as cpd"),

		@NamedNativeQuery(name = Compra.NQ_SELECT_BY_SITUACAO_DATA_ULTIMA_ALTERACAO_WITH_UR,
			query = "SELECT lc.NU_COMPRA, lc.NU_TRANSACAO_DEBITO, lc.NU_MEIO_PAGAMENTO, lc.NU_SITUACAO_COMPRA "
				+ "FROM LCE.LCETB013_COMPRA lc "
				+ "WHERE lc.NU_SITUACAO_COMPRA = ?1 "
				+ "AND lc.TS_ALTERACAO_SITUACAO <= ?2 WITH UR"),

		@NamedNativeQuery(name = Compra.NQ_SELECT_BY_SITUACOES_AGUARDANDO_PAGAMENTO_PIX_WITH_UR,
			query = "SELECT lc.NU_COMPRA, lc.NU_TRANSACAO_DEBITO, lc.NU_MEIO_PAGAMENTO, lc.NU_SITUACAO_COMPRA "
				+ "FROM LCE.LCETB013_COMPRA lc "
				+ "WHERE lc.NU_SITUACAO_COMPRA = ?1 "
				+ "AND lc.TS_ALTERACAO_SITUACAO <= ?2 "
				+ "AND ((lc.DT_EXPIRACAO_PAGAMENTO IS NULL AND lc.HH_EXPIRACAO_PAGAMENTO IS NULL) "
				+ "OR (TIMESTAMP(CONCAT(CHAR(lc.DT_EXPIRACAO_PAGAMENTO), ' ') CONCAT(REPLACE(CHAR(lc.HH_EXPIRACAO_PAGAMENTO), '.', ':'))) + 120 SECONDS) <= CURRENT_TIMESTAMP) "
				+ "WITH UR")
})
public class Compra extends AbstractEntidade<Long> {

	public static final String NQ_UPDATE_MES = "Compra.NQ_UPDATE_MES";
	public static final String NQ_UPDATE_PARTICAO = "Compra.NQ_UPDATE_PARTICAO";
	public static final String NQ_SELECT_BY_DATA_FINALIZACAO_MEIO_PAGAMENTO_SITUACOES = "Compra.NQ_SELECT_BY_DATA_FINALIZACAO_MEIO_PAGAMENTO_SITUACOES";
	public static final String NQ_SELECT_DATA_FINALIZACAO_MIN_BY_SITUACAO = "Compra.NQ_SELECT_DATA_FINALIZACAO_MIN_BY_SITUACAO";
	public static final String NQ_SELECT_BY_DATA_DEBITO_MEIO_PAGAMENTO_SITUACOES = "Compra.NQ_SELECT_BY_DATA_DEBITO_MEIO_PAGAMENTO_SITUACOES";
	public static final String NQ_SELECT_BY_APOSTADOR_SITUACAO = "Compra.consultePorApostador";
	public static final String NQ_SELECT_BY_NSU_DATA = "Compra.consultePorNsuData";
	public static final String NQ_SELECT_BY_NSU_ULTIMA_DATA = "Compra.NQ_SELECT_BY_NSU_ULTIMA_DATA";
	public static final String NQ_SELECT_BY_SITUACAO = "Compra.consultePorSituacao";
	public static final String NQ_SELECT_IDS_BY_SITUACAO_WITH_UR = "Compra.NQ_SELECT_IDS_BY_SITUACAO_WITH_UR";
	public static final String NQ_SELECT_BY_SITUACOES_AGUARDANDO_PAGAMENTO_PIX_WITH_UR = "Compra.NQ_SELECT_BY_SITUACOES_AGUARDANDO_PAGAMENTO_PIX_WITH_UR";
	public static final String NQ_SELECT_BY_SITUACAO_DATA_ULTIMA_ALTERACAO_WITH_UR = "Compra.NQ_SELECT_BY_SITUACAO_DATA_ULTIMA_ALTERACAO_WITH_UR";
	public static final String NQ_SELECT_ID_BY_EM_PROCESSAMENTO_DATAULTIMA_ALTERACAO_MAX_WITH_UR = "Compra.NQ_SELECT_BY_EM_PROCESSAMENTO_DATAULTIMA_ALTERACAO_MAX_WITH_UR";
	public static final String NQ_SELECT_BY_APOSTADOR_DATAINICIOCOMPRAMAXMIX = "Compra.consultePorIdCompraPeriodo";
	public static final String NQ_SELECT_BY_APOSTADOR_DATAINICIOCOMPRAMAXMIX_MEIO = "Compra.consultePorIdCompraPeriodoMeio";
	public static final String NQ_SELECT_BY_APOSTADOR_DATAINICIOCOMPRAMAXMIX_SITUACAO = "Compra.consultePorIdCompraPeriodoSituacao";
	public static final String NQ_SELECT_BY_APOSTADOR_DATAINICIOCOMPRAMAXMIX_MEIO_SITUACAO = "Compra.consultePorIdCompraPeriodoMeioSituacao";
	public static final String NQ_SELECT_BY_DATAINICIO_DATAFINALIZACAOMIN = "Compra.consultePorDataInicioDataFinalizacaoMin";
	public static final String NQ_SELECT_BY_COMPRA_APOSTADOR = "Compra.consultePorIdCompraIdApostador";
	public static final String NQ_SELECT_BY_COMPRA_COM_LOTERICA_APOSTADOR = "Compra.consultePorIdCompraComLotericaEApostador";
	public static final String NQ_SELECT_SUMARIZACAO_BY_APOSTADOR_DATA = "Compra.NQ_SELECT_SUMARIZACAO_BY_APOSTADOR_DATA";
	public static final String NQ_SELECT_MEDIA_DIARIA_BY_APOSTADOR_DATA = "Compra.NQ_SELECT__MEDIA_DIARIA_BY_APOSTADOR_DATA";
	public static final String NQ_SELECT_BY_APOSTA_NSU = "Compra.NQ_SELECT_BY_APOSTA_NSU";
	public static final String NQ_SELECT_BY_ULTIMA_COMPRA = "Compra.NQ_SELECT_BY_ULTIMA_COMPRA";
	public static final String NQ_SELECT_COUNT_COMPRAS_NEGADAS = "Compra.NQ_SELECT_COUNT_COMPRAS_NEGADAS";
	public static final String NQ_SELECT_COUNT_CARRINHOS_ABANDONADOS = "Compra.NQ_SELECT_COUNT_CARRINHOS_ABANDONADOS";
	public static final String NQ_SELECT_COUNT_COMPRAS_EM_PROCESSAMENTO = "Compra.NQ_SELECT_COUNT_COMPRAS_EM_PROCESSAMENTO";
	public static final String NQ_SELECT_BY_COMPRAS_ULTIMO_DIA = "Compra.NQ_SELECT_BY_COMPRAS_ULTIMO_DIA";
	public static final String NQ_SELECT_BY_SITUACAO_COMPRA_PGTO_NAO_IDENTIFICADO_WITH_UR = "Compra.NQ_SELECT_BY_SITUACAO_COMPRA_PGTO_NAO_IDENTIFICADO_WITH_UR";
	private static final long serialVersionUID = 1L;
	private static final int TAMANHO_ID_FORMATADO = 9;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_COMPRA")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_SITUACAO_COMPRA", referencedColumnName = "NU_SITUACAO_COMPRA")
	@EagerFetchMode(FetchMode.PARALLEL)
	private SituacaoCompra situacao;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NU_APOSTADOR", referencedColumnName = "NU_APOSTADOR")
	private Apostador apostador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_CD", referencedColumnName = "NU_CD")
	private Loterica loterica;

	@Column(name = "NU_TRANSACAO_DEBITO")
	private String nsuTransacaoDebito;
	
	@Column(name = "NU_TRANSACAO_ESTORNO")
	private String nsuTransacaoEstorno;

	@Column(name = "NU_TRANSACAO")
	private Long nsu;

	@Column(name = "DT_INICIO_COMPRA")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataInicioCompra;

	@Column(name = "DT_EFETIVACAO_DEBITO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataEfetivacaoDebito;

	@Column(name = "HH_INICIO_COMPRA")
	@Strategy(HoraValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIME)
	private Hora horaInicioCompra;

	@Column(name = "DT_FINALIZACAO_COMPRA")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataFinalizacaoCompra;

	@Column(name = "HH_FINALIZACAO_COMPRA")
	@Strategy(HoraValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIME)
	private Hora horaFinalizacaoCompra;

	@Column(name = "VR_TOTAL")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorTotal;
	
	@Column(name = "VR_COMISSAO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorComissao;

	@Column(name = "TS_ALTERACAO_SITUACAO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataUltimaAlteracao;

	@Column(name = "NU_PARTICAO")
	private Long particao;

	@Column(name = "NU_MES")
	private Long mes;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "NU_MEIO_PAGAMENTO", referencedColumnName = "NU_MEIO_PAGAMENTO")
	private MeioPagamento meioPagamento;
	
	@Column(name = "NU_MEIO_PAGAMENTO", insertable = false, updatable = false)
	private Long nuMeioPagamento;

	@Column(name = "DE_ERRO_PAGAMENTO")
	private String codigoMensageErro;
	
	@Column(name = "IC_SUBCANAL_PAGAMENTO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Subcanal subcanalPagamento;

	@Column(name = "DT_EXPIRACAO_PAGAMENTO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataExpiracaoPagamento;

	@Column(name = "HH_EXPIRACAO_PAGAMENTO")
	@Strategy(HoraValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIME)
	private Hora horaExpiracaoPagamento;

	@Column(name = "IC_COMPRA_INTEGRADA")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean compraIntegrada;

	@Transient
	private String externalIdPagamentoDebito;
	
	@Transient
	private String externalIdPagamentoEstorno;

	public Long getNsu() {
		return nsu;
	}

	public void setNsu(Long nsu) {
		this.nsu = nsu;
	}

	@Override
	public int hashCode() {
		return (id == null) ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Compra)) {
			return false;
		}
		Compra other = (Compra) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public SituacaoCompra getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoCompra situacao) {
		this.situacao = situacao;
		setDataUltimaAlteracao(DataUtil.getTimestampAtual());

		if(situacao != null) {
			switch (situacao.getEnum()) {
				case DEBITO_REALIZADO:
					setDataEfetivacaoDebito(DataUtil.getDataAtual());
					break;
				case DEBITO_NAO_AUTORIZADO:
				case ESTORNADA:
				case CANCELADA:
				case FINALIZADA:
				case FINALIZADA_CONTEM_APOSTAS_NAO_EFETIVADAS:
				case FINALIZADA_TODAS_APOSTAS_EFETIVADAS:
					setDataFinalizacaoCompra(this.dataUltimaAlteracao);
					setHoraFinalizacaoCompra(new Hora(this.dataFinalizacaoCompra));

					break;
			}
		}
	}

	public Loterica getLoterica() {
		return loterica;
	}

	public void setLoterica(Loterica loterica) {
		this.loterica = loterica;
	}

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

	public Decimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Decimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public void addValorTotal(Decimal valor) {
		if (valorTotal == null) {
			valorTotal = Decimal.ZERO;
		}
		valorTotal = valorTotal.add(valor);
	}
	
	public void removaValorTotal(Decimal valor) {
		if (valorTotal == null) {
			valorTotal = Decimal.ZERO;
		}else {
			valorTotal = valorTotal.subtract(valor);
		}
	}

	public Data getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Data dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public Data getDataInicioCompra() {
		return dataInicioCompra;
	}

	public void setDataInicioCompra(Data dataInicioCompra) {
		this.dataInicioCompra = dataInicioCompra;
	}

	public Hora getHoraInicioCompra() {
		return horaInicioCompra;
	}

	public void setHoraInicioCompra(Hora horaInicioCompra) {
		this.horaInicioCompra = horaInicioCompra;
	}

	public Data getDataFinalizacaoCompra() {
		return dataFinalizacaoCompra;
	}

	public void setDataFinalizacaoCompra(Data dataFinalizacaoCompra) {
		this.dataFinalizacaoCompra = dataFinalizacaoCompra;
	}

	public Hora getHoraFinalizacaoCompra() {
		return horaFinalizacaoCompra;
	}

	public void setHoraFinalizacaoCompra(Hora horaFinalizacaoCompra) {
		this.horaFinalizacaoCompra = horaFinalizacaoCompra;
	}

	public Long getParticao() {
		return particao;
	}

	public void setParticao(Long particao) {
		this.particao = particao;
	}

	public String recupereCodigo() {
		return StringUtil.completeAEsquerda(getId().toString(), TAMANHO_ID_FORMATADO, '0');
	}
	
	/**
	 * @return se o valor dessa compra pode ser devolvido, de acordo com sua situacao atual.
	 */
	public boolean podeSerEstornada() {
		return SituacaoCompra.Situacao.DEBITO_REALIZADO.getValue().equals(getSituacao().getId()) ||
			SituacaoCompra.Situacao.CANCELADA_PAGAMENTO_NAO_IDENTIFICADO_TEMPO_LIMITE.getValue().equals(getSituacao().getId()) ||
			(SituacaoCompra.Situacao.CANCELADA.getValue().equals(getSituacao().getId()) && getMeioPagamento().isMesmoEnum(Meio.PIX));
	}

	public boolean podePagar() {
		return SituacaoCompra.Situacao.DEBITO_INICIADO.getValue().equals(getSituacao().getId()) ||
			SituacaoCompra.Situacao.AGUARDANDO_PAGAMENTO_PIX.getValue().equals(getSituacao().getId());
	}

	public String getNsuTransacaoDebito() {
		return nsuTransacaoDebito;
	}

	public void setNsuTransacaoDebito(String nsuTransacaoDebito) {
		this.nsuTransacaoDebito = nsuTransacaoDebito;
	}

	public String getNsuTransacaoEstorno() {
		return nsuTransacaoEstorno;
	}

	public void setNsuTransacaoEstorno(String nsuTransacaoEstorno) {
		this.nsuTransacaoEstorno = nsuTransacaoEstorno;
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(MeioPagamento meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	/**
	 * @return operação de pagamento, data e nsu , separados por ponto e vírgula
	 */
	public String getExternalIdDebito() {
		if (StringUtil.isEmpty(externalIdPagamentoDebito)) {
			externalIdPagamentoDebito = ExternalReferenceUtil.getExternalId(OperacaoEnum.PAGAMENTO_COMPRA, dataInicioCompra, nsu);
		}
		return externalIdPagamentoDebito;
	}
	
	public String getExternalIdEstorno() {
		if (StringUtil.isEmpty(externalIdPagamentoEstorno)) {
			externalIdPagamentoEstorno = ExternalReferenceUtil.getExternalId(OperacaoEnum.ESTORNO_COMPRA, dataInicioCompra, nsu);
		}
		return externalIdPagamentoEstorno;
	}

	public Decimal getValorComissao() {
		return valorComissao;
	}

	public void setValorComissao(Decimal valorComissao) {
		this.valorComissao = valorComissao;
	}

	public String getCodigoMensageErro() {
		return codigoMensageErro;
	}

	public void setCodigoMensageErro(String codigoMensageErro) {
		this.codigoMensageErro = codigoMensageErro;
	}

	public Data getDataEfetivacaoDebito() {
		return dataEfetivacaoDebito;
	}

	public void setDataEfetivacaoDebito(Data dataEfetivacaoDebito) {
		this.dataEfetivacaoDebito = dataEfetivacaoDebito;
	}

	public Long getMes() {
		return mes;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	public Subcanal getSubcanalPagamento() {
		return subcanalPagamento;
	}

	public void setSubcanalPagamento(Subcanal subcanalPagamento) {
		this.subcanalPagamento = subcanalPagamento;
	}

	public Long getNuMeioPagamento() {
		return nuMeioPagamento;
	}

	public void setNuMeioPagamento(Long nuMeioPagamento) {
		this.nuMeioPagamento = nuMeioPagamento;
	}

	public String getTxIdPix() {
		return StringUtil.completeAEsquerda(this.getExternalIdDebito(), ExternalReferenceUtil.getTamanhoTXID(), '0');
	}

	public String getEstornoPix() {
		return StringUtil.completeAEsquerda(ExternalReferenceUtil.getExternalIdEstornoPix(OperacaoEnum.ESTORNO_COMPRA, DataUtil.getTimestampAtual(), nsu),
			ExternalReferenceUtil.getTamanhoTXID(), '0');
	}

	public Data getDataExpiracaoPagamento() {
		return dataExpiracaoPagamento;
	}

	public void setDataExpiracaoPagamento(Data dataExpiracaoPagamento) {
		this.dataExpiracaoPagamento = dataExpiracaoPagamento;
	}

	public Hora getHoraExpiracaoPagamento() {
		return horaExpiracaoPagamento;
	}

	public void setHoraExpiracaoPagamento(Hora horaExpiracaoPagamento) {
		this.horaExpiracaoPagamento = horaExpiracaoPagamento;
	}

	public Boolean getCompraIntegrada() {
		return compraIntegrada != null && compraIntegrada;
	}

	public void setCompraIntegrada(Boolean compraIntegrada) {
		this.compraIntegrada = compraIntegrada != null && compraIntegrada;
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + "]";
	}
}