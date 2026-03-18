package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
import br.gov.caixa.silce.dominio.entidade.Operacao.OperacaoEnum;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.silce.dominio.openjpa.HoraValueHandler;
import br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler;
import br.gov.caixa.silce.dominio.openjpa.TimestampValueHandler;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.StringUtil;

/**
 * 
 * @author c101482
 *
 */
@Entity
@Table(name = "LCETB913_HSTRO_COMPRA", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = HistoricoCompra.NQ_SELECT_BY_APOSTADOR_MES_ANO,
			query = "Select compra From HistoricoCompra compra "
				+ "Where compra.apostador.id=?1 and compra.id.mes=?2 and compra.id.ano=?3"),

		@NamedQuery(name = HistoricoCompra.NQ_SELECT_BY_COMPRA_COM_LOTERICA_APOSTADOR,
			query = "Select entidade From HistoricoCompra entidade "
				+ "join fetch entidade.loterica "
				+ "join fetch entidade.apostador "
				+ "join fetch entidade.meioPagamento "
				+ "where entidade.id.id = ?1 "
				+ "and entidade.id.mes = ?2 "
				+ "and entidade.id.ano = ?3"),

		@NamedQuery(name = HistoricoCompra.NQ_SELECT_BY_NSU_ULTIMA_DATA_HIST,
			query = "Select entidade From HistoricoCompra entidade "
				+ "where entidade.nsu=?1 and entidade.dataInicioCompra <= ?2 "
				+ "and entidade.id.mes = ?3 "
				+ "and entidade.id.ano = ?4 "
				+ "order by entidade.dataInicioCompra desc")
})
public class HistoricoCompra extends AbstractEntidade<HistoricoCompraPK> {

	private static final long serialVersionUID = 1L;
	private static final int TAMANHO_ID_FORMATADO = 9;
	public static final String NQ_SELECT_BY_APOSTADOR_MES_ANO = "HistoricoCompra.NQ_SELECT_BY_APOSTADOR_MES_ANO";
	public static final String NQ_SELECT_BY_COMPRA_COM_LOTERICA_APOSTADOR = "HistoricoCompra.NQ_SELECT_BY_COMPRA_COM_LOTERICA_APOSTADOR";

	public static final String NQ_SELECT_BY_NSU_ULTIMA_DATA_HIST = "Compra.NQ_SELECT_BY_NSU_ULTIMA_DATA_HIST";

	@EmbeddedId
	private HistoricoCompraPK id;

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

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "NU_MEIO_PAGAMENTO", referencedColumnName = "NU_MEIO_PAGAMENTO")
	private MeioPagamento meioPagamento;
	
	@Column(name = "DE_ERRO_PAGAMENTO")
	private String codigoMensageErro;
	
	@Column(name = "IC_SUBCANAL_PAGAMENTO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Subcanal subcanalPagamento;

	@Column(name = "IC_SUBCANAL_CARRINHO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Subcanal subcanalCarrinho;

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
		if (!(obj instanceof HistoricoCompra)) {
			return false;
		}
		HistoricoCompra other = (HistoricoCompra) obj;
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

	public Loterica getLoterica() {
		return loterica;
	}

	public void setLoterica(Loterica loterica) {
		this.loterica = loterica;
	}

	public HistoricoCompraPK getId() {
		return id;
	}

	public void setId(HistoricoCompraPK id) {
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
		return StringUtil.completeAEsquerda(getId().getId().toString(), TAMANHO_ID_FORMATADO, '0');
	}
	
	public String getTxIdPix() {
		return StringUtil.completeAEsquerda(this.getExternalIdDebito(), ExternalReferenceUtil.getTamanhoTXID(), '0');
	}

	/**
	 * @return se o valor dessa compra pode ser devolvido, de acordo com sua situacao atual.
	 */
	public boolean podeSerEstornada() {
		return SituacaoCompra.Situacao.DEBITO_INICIADO.getValue().equals(getSituacao().getId());
	}

	public boolean podePagar() {
		return SituacaoCompra.Situacao.DEBITO_INICIADO.getValue().equals(getSituacao().getId());
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

	/**
	 * @param situacao
	 *            the situacao to set
	 */
	public void setSituacao(SituacaoCompra situacao) {
		this.situacao = situacao;
	}

	public Subcanal getSubcanalPagamento() {
		return subcanalPagamento;
	}

	public void setSubcanalPagamento(Subcanal subcanalPagamento) {
		this.subcanalPagamento = subcanalPagamento;
	}

	public Subcanal getSubcanalCarrinho() {
		return subcanalCarrinho;
	}

	public void setSubcanalCarrinho(Subcanal subcanalCarrinho) {
		this.subcanalCarrinho = subcanalCarrinho;
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
		return compraIntegrada;
	}

	public void setCompraIntegrada(Boolean compraIntegrada) {
		this.compraIntegrada = compraIntegrada;
	}
}