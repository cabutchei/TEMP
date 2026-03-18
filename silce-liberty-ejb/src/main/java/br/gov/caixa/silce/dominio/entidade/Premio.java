package br.gov.caixa.silce.dominio.entidade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.Canal;
import br.gov.caixa.silce.dominio.ExternalReferenceUtil;
import br.gov.caixa.silce.dominio.NSB;
import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.entidade.Operacao.OperacaoEnum;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.silce.dominio.openjpa.HoraValueHandler;
import br.gov.caixa.silce.dominio.openjpa.NSBValueHandler;
import br.gov.caixa.silce.dominio.openjpa.ParametroPagamentoValueHandler;
import br.gov.caixa.silce.dominio.pagamento.ParametroPagamento;
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
@Table(name = "LCETB015_PREMIO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = Premio.NQ_SELECT_BY_SITUACAO, 
			query = "Select aposta From Premio premio where premio.situacao.id = ?1"),

		@NamedQuery(name = Premio.NQ_SELECT_BY_NSU_DATA, 
			query = "Select premio From Premio premio where premio.nsuPagamento = ?1 and premio.dataInicioPagamento = ?2"),

		@NamedQuery(name = Premio.NQ_SELECT_BY_APOSTA_COMPRADA, query = "Select ac.premio From ApostaComprada ac JOIN FETCH ac.premio where ac.id = ?1"),

		@NamedQuery(name = Premio.NQ_SELECT_BY_SITUACAO_DATA_PAGAMENTO_MEIO_PAGAMENTO, 
			query = "Select premio From Premio premio where premio.dataCreditoMeioPagamento = ?1 and premio.meioPagamento.id = ?2 and premio.situacao.id in (?3) order by premio.id asc"),

		@NamedQuery(name = Premio.NQ_SELECT_BY_DATAINICIOPAGAMENTO_DATAPAGAMENTOMAX,
			query = "SELECT comprada.premio, comprada.aposta.compra.nsu, comprada.aposta.compra.loterica FROM ApostaComprada comprada "
				+ "JOIN FETCH comprada.premio JOIN FETCH comprada.aposta JOIN FETCH comprada.aposta.compra LEFT JOIN FETCH comprada.aposta.compra.loterica"
				+ " WHERE comprada.premio.dataInicioPagamento < ?1 AND comprada.premio.dataPagamento = ?2"),

		@NamedQuery(name = Premio.NQ_COUNT_SEMTEIMOSINHAFUTURA_BY_CONCURSO_SITUACOES_MODALIDADE,
			query = "SELECT count(ac.premio) FROM ApostaComprada ac JOIN FETCH ac.premio JOIN FETCH ac.aposta, AbstractApostaNumerica aan "
				+ " WHERE aan.quantidadeTeimosinhas > 1 AND ac.concursoInicial + aan.quantidadeTeimosinhas > ?1"
			+ " AND ac.premio.situacao.id = ?2 AND ac.aposta.modalidade = ?3 AND ac.aposta = aan"),

		@NamedQuery(name = Premio.NQ_COUNT_SEMTEIMOSINHAFUTURA_BY_CONCURSO_SITUACOES_MODALIDADE_WITH_UR,
			query = "SELECT count(ac.premio) FROM ApostaComprada ac JOIN FETCH ac.premio JOIN FETCH ac.aposta, AbstractApostaNumerica aan "
			+ " WHERE aan.quantidadeTeimosinhas > 1 AND ac.concursoInicial + aan.quantidadeTeimosinhas > ?1"
			+ " AND ac.premio.situacao.id = ?2 AND ac.aposta.modalidade = ?3 AND ac.aposta = aan", hints = {
					@QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }),

		@NamedQuery(name = Premio.NQ_SELECT_BY_DATAINICIOPAGAMENTO_DATAPAGAMENTOMIN,
			query = "SELECT comprada.premio, comprada.aposta.compra.nsu, comprada.aposta.compra.loterica FROM ApostaComprada comprada"
				+ " JOIN FETCH comprada.premio JOIN FETCH comprada.aposta JOIN FETCH comprada.aposta.compra LEFT JOIN FETCH comprada.aposta.compra.loterica"
				+ " WHERE comprada.premio.dataInicioPagamento = ?1 AND (comprada.premio.dataPagamento > ?2 OR comprada.premio.dataPagamento is null)") })
@NamedNativeQueries({
		@NamedNativeQuery(name = Premio.NQ_UPDATE_MES_PARTICAO, query = "UPDATE LCE.LCETB015_PREMIO premio "
			+ "SET premio.NU_MES = COALESCE((select aposta.NU_MES FROM LCE.LCETB009_APOSTA_COMPRADA aposta where premio.NU_PREMIO = aposta.NU_PREMIO), premio.NU_MES), "
			+ "premio.NU_PARTICAO = COALESCE((select aposta.NU_PARTICAO FROM LCE.LCETB009_APOSTA_COMPRADA aposta where aposta.NU_PREMIO = premio.NU_PREMIO), premio.NU_PARTICAO)"),
})
public class Premio extends AbstractEntidade<Long> {

	public static final String NQ_UPDATE_MES_PARTICAO = "Premio.NQ_UPDATE_MES_PARTICAO";
	public static final String NQ_SELECT_BY_SITUACAO_DATA_PAGAMENTO_MEIO_PAGAMENTO = "Premio.NQ_SELECT_BY_SITUACAO_DATA_PAGAMENTO_MEIO_PAGAMENTO";
	public static final String NQ_SELECT_BY_SITUACAO = "Premio.findBySituacao";
	public static final String NQ_SELECT_BY_NSU_DATA = "Premio.findByNSUData";
	public static final String NQ_SELECT_BY_DATAINICIOPAGAMENTO_DATAPAGAMENTOMAX = "Premio.findByDataInicioPagamentoDataPagamentoMax";
	public static final String NQ_SELECT_BY_DATAINICIOPAGAMENTO_DATAPAGAMENTOMIN = "Premio.findByDataInicioPagamentoDataPagamentoMin";
	public static final String NQ_COUNT_SEMTEIMOSINHAFUTURA_BY_CONCURSO_SITUACOES_MODALIDADE = "Premio.countSemTeimosinhaByConcursoSituacoesModalidade";
	public static final String NQ_COUNT_SEMTEIMOSINHAFUTURA_BY_CONCURSO_SITUACOES_MODALIDADE_WITH_UR = "Premio.countSemTeimosinhaByConcursoSituacoesModalidadeWithUr";
	public static final String NQ_SELECT_BY_APOSTA_COMPRADA = "Premio.NQ_SELECT_BY_APOSTA_COMPRADA";
	
	private static final long serialVersionUID = 1L;

	private static final String RESGATE_STR_COMPROVANTE = "Resgate ";
	private static final String UNIDADE_LOTERICA_STR_COMPROVANTE = "Unidade Lotérica ";
	private static final String AGENCIA_STR_COMPROVANTE = "Agência ";
	private static final String SEPARADOR_STR_COMPROVANTE = " - ";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_PREMIO")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_SITUACAO_PREMIO", referencedColumnName = "NU_SITUACAO_PREMIO")
	@EagerFetchMode(FetchMode.PARALLEL)
	private SituacaoPremio situacao;

	@Column(name = "NU_CANAL_PAGAMENTO")
	private Integer canalPagamento;

	@Column(name = "NU_TRANSACAO_PAGAMENTO")
	private Long nsuPagamento;

	@Column(name = "NU_TRANSACAO_MEIO_PAGAMENTO")
	private String nsuTransacaoMeioPagamento;

	@Column(name = "DT_PAGAMENTO_PREMIO_SISPL")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataPagamento;

	@Column(name = "HH_PAGAMENTO_PREMIO_SISPL")
	@Strategy(HoraValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIME)
	private Hora horaPagamento;

	@Column(name = "DT_INICIO_PAGAMENTO_SILCE")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataInicioPagamento;

	@Column(name = "DT_EFTCO_CRDTO_MEIO_PAGAMENTO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataCreditoMeioPagamento;

	@Column(name = "HH_INICIO_PAGAMENTO_SILCE")
	@Strategy(HoraValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIME)
	private Hora horaInicioPagamento;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="NU_UNIDADE", referencedColumnName="NU_UNIDADE"),
		@JoinColumn(name="NU_NATURAL", referencedColumnName="NU_NATURAL")
	})
	private Agencia agencia;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "NU_CD")
	private Loterica loterica;
	
	@Column(name = "NU_CONCURSO_INICIAL_TROCA")
	private Integer concursoInicialTroca;

	@Column(name = "VR_PREMIO_BRUTO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorBruto;

	@Column(name = "VR_PREMIO_LIQUIDO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorLiquido;
	
	@ManyToOne
	@JoinColumn(name = "NU_MEIO_PAGAMENTO", referencedColumnName = "NU_MEIO_PAGAMENTO")
	private MeioPagamento meioPagamento;
	
	@Column(name = "CO_NSBC_TROCA")
	@Strategy(NSBValueHandler.STRATEGY_NAME)
	private NSB nsbTroca;
	
	@Column(name = "DE_PARAMETRO_PAGAMENTO")
	@Strategy(ParametroPagamentoValueHandler.STRATEGY_NAME)
	// Tem que ser um HashMap mesmo se nao o Strategy nao é utilizado, openjpa safado)
	private HashMap<ParametroPagamento, String> informacoesPagamento;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "premio", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private ApostaComprada apostaComprada;

	@Column(name = "VR_PREMIO_IRRF")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorIRRF;

	@Column(name = "TS_ALTERACAO_SITUACAO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataUltimaAlteracaoSituacao;

	@Column(name = "NU_PARTICAO")
	private Long particao;

	@Column(name = "NU_MES")
	private Long mes;

	@Column(name = "IC_SUBCANAL_RESGATE")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Subcanal subcanalResgate;

	//utilizado na consulta do premio
	@Transient
	private List<ConcursoPremiado> concursosPremiados;
	
	@Transient
	private Long nsuCompra;
	
	@Transient
	private String localPagamentoCache;
	
	public SituacaoPremio getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoPremio situacao) {
		this.situacao = situacao;
		this.dataUltimaAlteracaoSituacao = DataUtil.getTimestampAtual();
	}

	public Integer getConcursoInicialTroca() {
		return concursoInicialTroca;
	}

	public void setConcursoInicialTroca(Integer concursoInicialTroca) {
		this.concursoInicialTroca = concursoInicialTroca;
	}

	public Data getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Data dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Hora getHoraPagamento() {
		return horaPagamento;
	}

	public void setHoraPagamento(Hora horaPagamento) {
		this.horaPagamento = horaPagamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Decimal getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(Decimal valorPremioBruto) {
		this.valorBruto = valorPremioBruto;
	}

	public Decimal getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Decimal valorPremioLiquido) {
		this.valorLiquido = valorPremioLiquido;
	}

	public Decimal getValorIRRF() {
		return valorIRRF;
	}

	public void setValorIRRF(Decimal valorIRRF) {
		this.valorIRRF = valorIRRF;
	}

	public Data getDataInicioPagamento() {
		return dataInicioPagamento;
	}

	public void setDataInicioPagamento(Data dataInicioPagamento) {
		this.dataInicioPagamento = dataInicioPagamento;
	}

	public Hora getHoraInicioPagamento() {
		return horaInicioPagamento;
	}

	public void setHoraInicioPagamento(Hora horaInicioPagamento) {
		this.horaInicioPagamento = horaInicioPagamento;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
		localPagamentoCache = null;
	}

	public Loterica getLoterica() {
		return loterica;
	}

	public void setLoterica(Loterica loterica) {
		this.loterica = loterica;
		localPagamentoCache = null;
	}

	public Integer getCanalPagamento() {
		return canalPagamento;
	}

	public void setCanalPagamento(Integer canalPagamento) {
		this.canalPagamento = canalPagamento;
		localPagamentoCache = null;
	}

	public String getNumeroLoterica() {
		return loterica.getNumeroFormatado();
	}

	public List<ConcursoPremiado> getConcursosPremiados() {
		if(concursosPremiados == null) {
			concursosPremiados = new ArrayList<ConcursoPremiado>();
		}
		return concursosPremiados;
	}

	public Long getNsuCompra() {
		return nsuCompra;
	}

	public void setNsuCompra(Long nsuCompra) {
		this.nsuCompra = nsuCompra;
	}

	public boolean isPagoSILCE() {
		return Canal.SILCE.getValue().equals(getCanalPagamento());
	}

	public boolean isPagoSIGEL() {
		return Canal.SIGEL.getValue().equals(getCanalPagamento());
	}

	public boolean isPagoSISPL() {
		return Canal.SISPL.getValue().equals(getCanalPagamento());
	}

	public Data getDataUltimaAlteracaoSituacao() {
		return dataUltimaAlteracaoSituacao;
	}

	public void setDataUltimaAlteracaoSituacao(Data dataUltimaAlteracaoSituacao) {
		this.dataUltimaAlteracaoSituacao = dataUltimaAlteracaoSituacao;
	}

	public Long getNsuPagamento() {
		return nsuPagamento;
	}

	public void setNsuPagamento(Long nsuPagamento) {
		this.nsuPagamento = nsuPagamento;
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(MeioPagamento meioPagamento) {
		this.meioPagamento = meioPagamento;
		localPagamentoCache = null;
	}
	
	public NSB getNsbTroca() {
		return nsbTroca;
	}

	public void setNsbTroca(NSB nsbTroca) {
		this.nsbTroca = nsbTroca;
	}

	public String getLocalPagamento() {
		if (StringUtil.isEmpty(localPagamentoCache)) {
			if(isPagoSILCE()) {
				if (getMeioPagamento() != null) {
					localPagamentoCache = RESGATE_STR_COMPROVANTE + getMeioPagamento().getNomeNoPagamentoPremio();
				} else {
					return "Indisponível.";
				}
			} else {
				Loterica loterica2 = getLoterica();
				if (loterica2 != null && isPagoSISPL()) {
					localPagamentoCache = UNIDADE_LOTERICA_STR_COMPROVANTE + loterica2.getNumeroFormatado() + SEPARADOR_STR_COMPROVANTE + loterica2.getNomeFantasia();
				} else if (getAgencia() != null && isPagoSIGEL()) {
					int qtdeMaximaDigitos = getAgencia().getId().getNumeroNatural().toString().length() <= 4 ? 4 : 5;
					localPagamentoCache = AGENCIA_STR_COMPROVANTE + StringUtil.completeAEsquerda(getAgencia().getId().getNumeroNatural().toString(), qtdeMaximaDigitos, '0')
						+ SEPARADOR_STR_COMPROVANTE + getAgencia().getNome();
				} else {
					return "Indisponível";
				}
			}
		}
		return localPagamentoCache;
	}

	public String getNsuTransacaoMeioPagamento() {
		return nsuTransacaoMeioPagamento;
	}

	public void setNsuTransacaoMeioPagamento(String nsuTransacaoMeioPagamento) {
		this.nsuTransacaoMeioPagamento = nsuTransacaoMeioPagamento;
	}

	public Map<ParametroPagamento, String> getInformacoesPagamento() {
		return informacoesPagamento;
	}

	public void setInformacoesPagamento(HashMap<ParametroPagamento, String> informacoesPagamento) {
		this.informacoesPagamento = informacoesPagamento;
	}

	/**
	 * @return operação de pagamento, data e nsu , separados por ponto e vírgula
	 */
	public String getExternalIdPagamento() {
		return ExternalReferenceUtil.getExternalId(OperacaoEnum.PAGAMENTO_PREMIO, dataPagamento, nsuPagamento);
	}

	public Data getDataCreditoMeioPagamento() {
		return dataCreditoMeioPagamento;
	}

	public void setDataCreditoMeioPagamento(Data dataCreditoMeioPagamento) {
		this.dataCreditoMeioPagamento = dataCreditoMeioPagamento;
	}

	public Long getMes() {
		return mes;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	public Long getParticao() {
		return particao;
	}

	public void setParticao(Long particao) {
		this.particao = particao;
	}

	public ApostaComprada getApostaComprada() {
		return apostaComprada;
	}

	public void setApostaComprada(ApostaComprada apostaComprada) {
		this.apostaComprada = apostaComprada;
	}

	public Subcanal getSubcanalResgate() {
		return subcanalResgate;
	}

	public void setSubcanalResgate(Subcanal subcanalResgate) {
		this.subcanalResgate = subcanalResgate;
	}
}
