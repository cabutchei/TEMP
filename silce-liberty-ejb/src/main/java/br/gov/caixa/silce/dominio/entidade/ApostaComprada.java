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
import javax.persistence.OneToOne;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;
import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
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
import br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler;
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
@Table(name = "LCETB009_APOSTA_COMPRADA", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = ApostaComprada.NQ_SELECT_IDS_BY_SITUACAO_DATAULTIMASITUACAO_PREMIOS_WITH_UR,
			query = "Select aposta.id From ApostaComprada aposta join fetch aposta.premio where aposta.situacao.id = ?1"
				+ " and aposta.dataUltimaSituacao <= ?2 and aposta.premio.situacao.id in (?3)",
			hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }),

		@NamedQuery(name = ApostaComprada.NQ_SELECT_BY_SITUACAO_COMPRA,
			query = "Select entidade From ApostaComprada entidade join fetch entidade.aposta where entidade.aposta.compra.id = ?1 and entidade.situacao.id in (?2) "),

		@NamedQuery(name = ApostaComprada.NQ_SUMARIZA_BY_SITUACAO_COMPRA_FINALIZACAOPROCESSAMENTO,
			query = "Select sum(entidade.aposta.valor) + 0.0, sum(entidade.valorComissao) + 0.0 From ApostaComprada entidade join fetch entidade.aposta where "
			+ " entidade.aposta.compra.id = ?1 and entidade.dataFinalizacaoProcessamento = (?2) and entidade.situacao.id in (?3)"),

		@NamedQuery(name = ApostaComprada.NQ_SELECT_BY_NSB,
			query = "Select entidade From ApostaComprada entidade where entidade.nsb = ?1"),

		@NamedQuery(name = ApostaComprada.NQ_SELECT_IDS_BY_SITUACAO_DATAULTIMASITUACAO_WITH_UR,
			query = "Select aposta.id From ApostaComprada aposta where aposta.situacao.id = ?1 and aposta.dataUltimaSituacao <= ?2 and aposta.indicadorBloqueioDevolucao = false",
			hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }),

		@NamedQuery(name = ApostaComprada.NQ_SELECT_IDS_COMPRAS_BY_SITUACAO_DATAULTIMASITUACAO_WITH_UR,
			query = "Select apostaComprada.aposta.compra.id From ApostaComprada apostaComprada "
				+ "where apostaComprada.situacao.id = ?1 and apostaComprada.dataUltimaSituacao <= ?2 and apostaComprada.aposta.compra.situacao.id = ?3 "
				+ "group by apostaComprada.aposta.compra.id",
			hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }),

		@NamedQuery(name = ApostaComprada.NQ_SELECT_BY_COMPRA_WITH_UR,
			query = "Select apostaComprada From ApostaComprada apostaComprada "
				+ "join fetch apostaComprada.aposta "
				+ "join fetch apostaComprada.aposta.compra "
				+ "join fetch apostaComprada.aposta.compra.meioPagamento "
				+ "where apostaComprada.aposta.compra.id = ?1 "
				+ "and apostaComprada.situacao.id = ?2 "
				+ "and apostaComprada.dataUltimaSituacao <= ?3 "
				+ "and apostaComprada.indicadorBloqueioDevolucao = ?4",
			hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }),

		@NamedQuery(name = ApostaComprada.NQ_SELECT_BY_DATA_ALTERACAO_MEIO_PAGAMENTO_SITUACAO, 
			query = "Select ac From ApostaComprada ac join fetch ac.aposta join fetch ac.aposta.compra where ac.dataUltimaSituacao between ?1 and ?2"
				+ " and ac.situacao.id in (?3) and ac.aposta.compra.meioPagamento.id = ?4 order by ac.id asc"),

		@NamedQuery(name = ApostaComprada.NQ_SELECT_BY_DATA_ALTERACAO_MEIO_PAGAMENTO_SITUACAO_SEM_COTA, query = "Select ac From ApostaComprada ac join fetch ac.aposta join fetch ac.aposta.compra where ac.dataUltimaSituacao between ?1 and ?2"
			+ " and ac.situacao.id in (?3) and ac.aposta.compra.meioPagamento.id = ?4 and ac.aposta.indicadorBolao = false order by ac.id asc"),


		@NamedQuery(name = ApostaComprada.NQ_SELECT_BY_NSU_DATA,
			query = "Select entidade From ApostaComprada entidade where entidade.nsuTransacao = ?1 and entidade.dataEnvioSISPL = ?2"),

		@NamedQuery(name = ApostaComprada.NQ_SELECT_BY_APOSTA_APOSTADOR,
			query = "Select entidade From ApostaComprada entidade join fetch entidade.aposta join fetch entidade.aposta.compra"
				+ " where entidade.aposta.id = ?1 and entidade.aposta.compra.apostador.id = ?2"),
		@NamedQuery(
			name = ApostaComprada.NQ_SELECT_BY_NU_APOSTA_ORIGEM,
			query = "Select entidade from ApostaComprada entidade where entidade.apostaOriginal.id = ?1"),

		@NamedQuery(name = ApostaComprada.NQ_SELECT_COUNT_BY_ID_COMPRA,
			query = "Select count(entidade) From ApostaComprada entidade join fetch entidade.aposta where entidade.aposta.compra.id = ?1 "),

		// @NamedQuery(name = ApostaComprada.NQ_SELECT_BY_CONCURSO_MODALIDADE_SITUACAO_WITH_UR,
		// query = "Select ac From ApostaComprada ac join fetch ac.aposta join fetch ac.aposta.compra join fetch "
		// + "ac.aposta.compra.situacao where ac.concursoInicial = ?1 and ac.aposta.modalidade = ?2 and (ac.situacao.id
		// = 2 or ac.situacao.id = 3)",
		// hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }),

		@NamedQuery(name = ApostaComprada.NQ_SELECT_BY_CONCURSO_MODALIDADE_SITUACAOPREMIO_WITH_UR,
			query = "Select ac From ApostaComprada ac join fetch ac.aposta join fetch ac.aposta.compra"
				+ " join fetch ac.aposta.compra.situacao join fetch ac.premio, AbstractApostaNumerica aan where aan.quantidadeTeimosinhas > 1"
				+ " AND ac.concursoInicial + aan.quantidadeTeimosinhas > ?1 and ac.aposta.modalidade = ?2 and ac.premio.situacao.id in (?3) AND ac.aposta = aan",
			hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }),
		
		// @NamedQuery(name = ApostaComprada.NQ_SELECT_BY_CONCURSO_MODALIDADE_SITUACAO_COMPRA_WITH_UR,
		// query = "Select ac From ApostaComprada ac join fetch ac.aposta join fetch ac.aposta.compra join fetch
		// ac.aposta.compra.situacao"
		// + " where ac.concursoInicial = ?1 and ac.aposta.modalidade = ?2 and ac.aposta.compra.situacao.id = 10",
		// hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") })
})
@NamedNativeQueries({
		@NamedNativeQuery(name = ApostaComprada.NQ_UPDATE_MES_PARTICAO, query = "UPDATE LCE.LCETB009_APOSTA_COMPRADA apostaComprada SET"
			+ " apostaComprada.NU_MES = (select aposta.NU_MES FROM LCE.LCETB011_APOSTA aposta where apostaComprada.NU_APOSTA = aposta.NU_APOSTA),"
			+ " apostaComprada.NU_PARTICAO = (select aposta.NU_PARTICAO FROM LCE.LCETB011_APOSTA aposta where aposta.NU_APOSTA = apostaComprada.NU_APOSTA)")

})
public class ApostaComprada extends AbstractEntidade<Long> {

	public static final String NQ_UPDATE_MES_PARTICAO = "ApostaComprada.NQ_UPDATE_MES_PARTICAO";
	public static final String NQ_SELECT_BY_DATA_ALTERACAO_MEIO_PAGAMENTO_SITUACAO = "ApostaComprada.NQ_SELECT_BY_DATA_ALTERACAO_MEIO_PAGAMENTO_SITUACAO";
	public static final String NQ_SELECT_BY_DATA_ALTERACAO_MEIO_PAGAMENTO_SITUACAO_SEM_COTA = "ApostaComprada.NQ_SELECT_BY_DATA_ALTERACAO_MEIO_PAGAMENTO_SITUACAO_SEM_COTA";
	public static final String NQ_SELECT_BY_SITUACAO_DATAULTIMASITUACAO_PREMIOS = "ApostaComprada.findBySituacaoDataEPremio";
	public static final String NQ_SELECT_IDS_BY_SITUACAO_DATAULTIMASITUACAO_PREMIOS_WITH_UR = "ApostaComprada.NQ_SELECT_IDS_BY_SITUACAO_DATAULTIMASITUACAO_PREMIOS_WITH_UR";
	public static final String NQ_SELECT_BY_SITUACAO_DATAULTIMASITUACAO = "ApostaComprada.findBySituacaoEData";
	public static final String NQ_SELECT_IDS_BY_SITUACAO_DATAULTIMASITUACAO_WITH_UR = "ApostaComprada.NQ_SELECT_BY_SITUACAO_DATAULTIMASITUACAO_WITH_UR";
	public static final String NQ_SELECT_IDS_COMPRAS_BY_SITUACAO_DATAULTIMASITUACAO_WITH_UR = "ApostaComprada.NQ_SELECT_IDS_COMPRAS_BY_SITUACAO_DATAULTIMASITUACAO_WITH_UR";
	public static final String NQ_SELECT_BY_COMPRA_WITH_UR = "ApostaComprada.NQ_SELECT_BY_COMPRA_WITH_UR";
	public static final String NQ_SELECT_BY_SITUACAO_COMPRA = "ApostaComprada.findBySituacaoECompra";
	public static final String NQ_SUMARIZA_BY_SITUACAO_COMPRA_FINALIZACAOPROCESSAMENTO = "ApostaComprada.NQ_SELECT_BY_SITUACAO_COMPRA_FINALIZACAOPROCESSAMENTO";
	public static final String NQ_SELECT_BY_NSB = "ApostaComprada.findByNSB";
	public static final String NQ_SELECT_BY_NSU_DATA = "ApostaComprada.findByNSUData";
	public static final String NQ_SELECT_BY_NSU = "ApostaComprada.findByNSU";
	public static final String NQ_SELECT_BY_APOSTA_APOSTADOR = "ApostaComprada.findByAposta";
	public static final String NQ_SELECT_BY_NU_APOSTA_ORIGEM = "ApostaComprada.NQ_SELECT_BY_NU_APOSTA_ORIGEM";
	public static final String NQ_SELECT_COUNT_BY_ID_COMPRA = "ApostaComprada.countByCompra";
	// public static final String NQ_SELECT_BY_CONCURSO_MODALIDADE_SITUACAO_WITH_UR =
	// "ApostaComprada.NQ_SELECT_BY_CONCURSO_MODALIDADE_SITUACAO_WITH_UR";
	public static final String NQ_SELECT_BY_CONCURSO_MODALIDADE_WITH_UR = "ApostaComprada.NQ_SELECT_BY_CONCURSO_MODALIDADE_WITH_UR";
	public static final String NQ_SELECT_BY_CONCURSO_MODALIDADE_SITUACAOPREMIO_WITH_UR = "ApostaComprada.NQ_SELECT_BY_CONCURSO_MODALIDADE_SITUACAOPREMIO_WITH_UR";
	// public static final String NQ_SELECT_BY_CONCURSO_MODALIDADE_SITUACAO_COMPRA_WITH_UR =
	// "ApostaComprada.NQ_SELECT_BY_CONCURSO_MODALIDADE_SITUACAO_COMPRA_WITH_UR";

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(ApostaComprada.class, new MessageFormatMessageFactory());

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_APOSTA_COMPRADA")
	private Long id;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="NU_PREMIO", referencedColumnName = "NU_PREMIO")
	private Premio premio;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "NU_APOSTA_ORIGEM", referencedColumnName = "NU_APOSTA_COMPRADA")
	private ApostaComprada apostaOriginal;

	@Column(name = "NU_TRANSACAO_APOSTA")
	private Long nsuTransacao;

	@Column(name = "NU_TRANSACAO_DEVOLUCAO")
	private String nsuTransacaoDevolucao;
	
	@Column(name = "NU_CONCURSO_INICIAL")
	private Integer concursoInicial;

	@Column(name = "NU_SERIE_BILHETE")
	@Strategy(NSBValueHandler.STRATEGY_NAME)
	private NSB nsb;

	@Column(name = "IC_BILHETE_TROCA")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean apostaTroca;

	@Column(name = "DT_INICIO_APOSTA_COMPRADA")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataInicioApostaComprada;

	@Column(name = "HH_INICIO_APOSTA_COMPRADA")
	@Strategy(HoraValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIME)
	private Hora horaInicioApostaComprada;

	@Column(name = "DT_ENVIO_APOSTA_SISPL")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataEnvioSISPL;
	
	@Column(name = "HH_ENVIO_APOSTA_SISPL")
	@Strategy(HoraValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIME)
	private Hora horaEnvioSISPL;

	@Column(name = "DT_EFTCO_APOSTA_SISPL")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataEfetivacaoSISPL;

	@Column(name = "HH_EFTCO_APOSTA_SISPL")
	@Strategy(HoraValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIME)
	private Hora horaEfetivacaoSISPL;

	@Column(name = "DT_FINALIZACAO_PROCESSAMENTO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataFinalizacaoProcessamento;
	
	@Column(name = "HH_FINALIZACAO_PROCESSAMENTO")
	@Strategy(HoraValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIME)
	private Hora horaFinalizacaoProcessamento;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_SITUACAO_APOSTA")
	@EagerFetchMode(FetchMode.PARALLEL)
	private SituacaoAposta situacao;
	
	@Column(name = "TS_ALTERACAO_SITUACAO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataUltimaSituacao;
	
	@Column(name = "DE_ERRO_RESGATE")
	private String codigoMensageErro;
	
	@Column(name = "VR_COMISSAO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorComissao;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "NU_APOSTA", referencedColumnName="NU_APOSTA")
	private Aposta<?> aposta;
	
	@Column(name = "IC_SUBCANAL_PAGAMENTO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Subcanal subcanalPagamento;

	@Column(name = "IC_BLOQUEIO_DEVOLUCAO")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean indicadorBloqueioDevolucao;

	@Column(name = "DE_MENSAGEM_ERRO_APOSTA")
	private String mensageErroAposta;

	@Column(name = "NU_PARTICAO")
	private Long particao;

	@Column(name = "NU_MES")
	private Long mes;

	@Transient
	private String externalIdDevolucao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ApostaComprada getApostaOriginal() {
		return apostaOriginal;
	}

	public void setApostaOriginal(ApostaComprada apostaOriginal) {
		this.apostaOriginal = apostaOriginal;
	}

	public Long getNsuTransacao() {
		return nsuTransacao;
	}

	public void setNsuTransacao(Long nsuTransacao) {
		this.nsuTransacao = nsuTransacao;
	}

	public Integer getConcursoInicial() {
		return concursoInicial;
	}

	public void setConcursoInicial(Integer concursoInicial) {
		this.concursoInicial = concursoInicial;
	}

	public Boolean getApostaTroca() {
		return apostaTroca;
	}

	public void setApostaTroca(Boolean apostaTroca) {
		Boolean apostaTrocaInicial = this.getApostaTroca();
		if (apostaTrocaInicial != null && apostaTroca != null && !apostaTrocaInicial.equals(apostaTroca)) {
			LOG.debug("Aposta comprada com ID {0} trocará o parametro ApostaTroca de {1} para {2} ", this.id.toString(), apostaTrocaInicial.toString(), apostaTroca.toString());
		}
		this.apostaTroca = apostaTroca;
	}

	public Premio getPremio() {
		return premio;
	}

	public void setPremio(Premio premio) {
		this.premio = premio;
		if (premio != null) {
			premio.setMes(getMes());
			premio.setParticao(getParticao());
		}
	}

	public SituacaoAposta getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoAposta situacao) {
		SituacaoAposta situacaoInicial = this.getSituacao();
		if (situacao != null && situacaoInicial != null && !situacaoInicial.equals(situacao)) {
			LOG.debug("A situação da Aposta comprada com ID {0} trocará de {1} para {2} ", this.id != null ? this.id.toString() : "",
				situacaoInicial.getDescricao(), situacao.getDescricao());
		}

		this.situacao = situacao;
		this.dataUltimaSituacao = DataUtil.getTimestampAtual();
	}

	public Data getDataUltimaSituacao() {
		return dataUltimaSituacao;
	}

	public void setDataUltimaSituacao(Data dataUltimaSituacao) {
		this.dataUltimaSituacao = dataUltimaSituacao;
	}

	public NSB getNsb() {
		return nsb;
	}

	public void setNsb(NSB nsb) {
		this.nsb = nsb;
	}

	public Aposta<?> getAposta() {
		return aposta;
	}

	public void setAposta(Aposta<?> aposta) {
		this.aposta = aposta;
		if (aposta == null) {
			setParticao(null);
		} else {
			setParticao(aposta.getParticao());
			setMes(aposta.getMes());
		}
	}

	public Data getDataEnvioSISPL() {
		return dataEnvioSISPL;
	}

	public void setDataEnvioSISPL(Data dataEnvioSISPL) {
		this.dataEnvioSISPL = dataEnvioSISPL;
	}

	public Hora getHoraEnvioSISPL() {
		return horaEnvioSISPL;
	}

	public void setHoraEnvioSISPL(Hora horaEnvioSISPL) {
		this.horaEnvioSISPL = horaEnvioSISPL;
	}

	public Data getDataFinalizacaoProcessamento() {
		return dataFinalizacaoProcessamento;
	}

	public void setDataFinalizacaoProcessamento(Data dataFinalizacaoProcessamento) {
		this.dataFinalizacaoProcessamento = dataFinalizacaoProcessamento;
	}

	public Hora getHoraFinalizacaoProcessamento() {
		return horaFinalizacaoProcessamento;
	}

	public void setHoraFinalizacaoProcessamento(Hora horaFinalizacaoProcessamento) {
		this.horaFinalizacaoProcessamento = horaFinalizacaoProcessamento;
	}

	public Data getDataEfetivacaoSISPL() {
		return dataEfetivacaoSISPL;
	}

	public void setDataEfetivacaoSISPL(Data dataEfetivacaoSISPL) {
		Data dataEfetivacaoSISPLInicial = this.getDataEfetivacaoSISPL();
		if (dataEfetivacaoSISPLInicial != null && dataEfetivacaoSISPL != null && !dataEfetivacaoSISPLInicial.equals(dataEfetivacaoSISPL)) {
			LOG.debug("A data de Efetivação do SISPL da Aposta comprada com ID {0} foi alterada de {1} para {2} ", this.id.toString(), dataEfetivacaoSISPLInicial.toString(),
				dataEfetivacaoSISPL.toString());
		}

		if (dataEfetivacaoSISPL != null && !DataUtil.getDataAtual().equals(dataEfetivacaoSISPL)) {
			LOG.debug("A data de Efetivação do SISPL da Aposta comprada com ID {0} será armazenada como {1} que é diferente da data atual.",
				this.id != null ? this.id.toString() : "",
				dataEfetivacaoSISPL.toString());
		}
		this.dataEfetivacaoSISPL = dataEfetivacaoSISPL;
	}

	public Hora getHoraEfetivacaoSISPL() {
		return horaEfetivacaoSISPL;
	}

	public void setHoraEfetivacaoSISPL(Hora horaEfetivacaoSISPL) {
		this.horaEfetivacaoSISPL = horaEfetivacaoSISPL;
	}

	public String getNsuTransacaoDevolucao() {
		return nsuTransacaoDevolucao;
	}

	public void setNsuTransacaoDevolucao(String nsuTransacaoDevolucao) {
		this.nsuTransacaoDevolucao = nsuTransacaoDevolucao;
	}
	
	/**
	 * @return se o valor de premio dessa aposta pode ser pago, de acordo com sua situacao atual.
	 */
	public boolean podePagarPremio() {
		return SituacaoAposta.Situacao.PAGAMENTO_EM_PROCESSAMENTO.getValue().equals(getSituacao().getId());
	}


	public Data getDataInicioApostaComprada() {
		return dataInicioApostaComprada;
	}

	public void setDataInicioApostaComprada(Data dataInicioApostaComprada) {
		this.dataInicioApostaComprada = dataInicioApostaComprada;
	}

	public Hora getHoraInicioApostaComprada() {
		return horaInicioApostaComprada;
	}

	public void setHoraInicioApostaComprada(Hora horaInicioApostaComprada) {
		this.horaInicioApostaComprada = horaInicioApostaComprada;
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

	public String getExternalIdDevolucao() {
		if (StringUtil.isEmpty(externalIdDevolucao)) {
			externalIdDevolucao = ExternalReferenceUtil.getExternalId(OperacaoEnum.DEVOLUCAO_APOSTA, getDataInicioApostaComprada(), getNsuTransacao());
		}
		return externalIdDevolucao;
	}

	public String getTxIdDevolucaoPix() {
		return StringUtil.completeAEsquerda(this.getExternalIdDevolucao(), ExternalReferenceUtil.getTamanhoTXID(), '0');
	}

	public String getDevolucaoApostaPix() {
		return StringUtil.completeAEsquerda(ExternalReferenceUtil.getExternalIdEstornoPix(OperacaoEnum.DEVOLUCAO_APOSTA, DataUtil.getTimestampAtual(), getNsuTransacao()),
			ExternalReferenceUtil.getTamanhoTXID(), '0');
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

	public Subcanal getSubcanalPagamento() {
		return subcanalPagamento;
	}

	public void setSubcanalPagamento(Subcanal subcanalPagamento) {
		this.subcanalPagamento = subcanalPagamento;
	}

	public Boolean getIndicadorBloqueioDevolucao() {
		return indicadorBloqueioDevolucao;
	}

	public void setIndicadorBloqueioDevolucao(Boolean indicadorBloqueioDevolucao) {
		this.indicadorBloqueioDevolucao = indicadorBloqueioDevolucao;
	}

	public String getMensageErroAposta() {
		return mensageErroAposta;
	}

	public void setMensageErroAposta(String mensageErroAposta) {
		this.mensageErroAposta = mensageErroAposta;
	}

}
