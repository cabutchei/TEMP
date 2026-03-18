package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
@Table(name = "LCETB909_HSTRO_APOSTA_COMPRADA", schema = DatabaseConfig.SCHEMA)
public class HistoricoApostaComprada extends AbstractEntidade<HistoricoApostaCompradaPK> {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HistoricoApostaCompradaPK id;

	/*
	 * @OneToOne(optional = false)
	 * 
	 * @JoinColumn(name = "NU_PREMIO", referencedColumnName = "NU_PREMIO")
	 */
	@Transient
	private HistoricoPremio premio;

	@Column(name = "NU_PREMIO")
	private Long nuPremio;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_APOSTA_ORIGEM", referencedColumnName = "NU_APOSTA_COMPRADA")
	private HistoricoApostaComprada apostaOriginal;

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
	private HistoricoAposta<?> aposta;
	
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

	@Column(name = "NU_ANO")
	private Long ano;

	@Transient
	private String externalIdDevolucao;
	
	public HistoricoApostaCompradaPK getId() {
		return id;
	}

	public void setId(HistoricoApostaCompradaPK id) {
		this.id = id;
	}

	public HistoricoApostaComprada getApostaOriginal() {
		return apostaOriginal;
	}

	public void setApostaOriginal(HistoricoApostaComprada apostaOriginal) {
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
		this.apostaTroca = apostaTroca;
	}

	public HistoricoPremio getPremio() {
		return premio;
	}

	public void setPremio(HistoricoPremio premio) {
		this.premio = premio;
		/*
		 * if (premio != null) { premio.setMes(getMes()); premio.setParticao(getParticao()); }
		 */
	}

	public SituacaoAposta getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoAposta situacao) {
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

	public HistoricoAposta<?> getAposta() {
		return aposta;
	}

	public void setAposta(HistoricoAposta<?> aposta) {
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

	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

	public Long getNuPremio() {
		return nuPremio;
	}

	public void setNuPremio(Long nuPremio) {
		this.nuPremio = nuPremio;
	}

}