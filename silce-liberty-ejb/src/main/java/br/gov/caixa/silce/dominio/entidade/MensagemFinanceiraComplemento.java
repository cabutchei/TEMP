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
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.TipoOperacaoFinanceira;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

@Entity
@Table(name = "LCETB050_MENSAGEM_FNNCA_CMPMO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = MensagemFinanceiraComplemento.NQ_SELECT_BY_ID_DATA_VALOR, query = "Select d from MensagemFinanceiraComplemento d where d.id = ?1 and d.tsEnvio between ?2 and ?3 and d.valor = ?4"),
		@NamedQuery(name = MensagemFinanceiraComplemento.NQ_SELECT_BY_MEIO_PAGAMENTO,
			query = "Select d from MensagemFinanceiraComplemento d where d.meioPagamento.id = ?1 order by d.tsEnvio desc"),
		@NamedQuery(name = MensagemFinanceiraComplemento.NQ_SELECT_BY_TIPO_OPERACAO,
			query = "Select d from MensagemFinanceiraComplemento d where d.tipo = ?1 order by d.tsEnvio desc"),
		@NamedQuery(name = MensagemFinanceiraComplemento.NQ_SELECT_BY_TS_ENVIO_NSGD,
			query = "Select d from MensagemFinanceiraComplemento d join fetch d.meioPagamento where d.tsEnvio between ?1 and ?2 and d.valor <> ?3"),
		@NamedQuery(name = MensagemFinanceiraComplemento.NQ_SELECT_BY_TS_ENVIO_MARKETPLACE,
			query = "Select d from MensagemFinanceiraComplemento d join fetch d.meioPagamento left join fetch d.loterica where d.tsEnvio between ?1 and ?2 and d.valor = ?3"),
		@NamedQuery(name = MensagemFinanceiraComplemento.NQ_SELECT_BY_NSGD,
			query = "Select d from MensagemFinanceiraComplemento d join fetch d.meioPagamento where d.valor <> ?1 order by d.tsEnvio desc"),
		@NamedQuery(name = MensagemFinanceiraComplemento.NQ_SELECT_BY_MARKETPLACE,
			query = "Select d from MensagemFinanceiraComplemento d join fetch d.meioPagamento left join fetch d.loterica where d.valor = ?1 order by d.tsEnvio desc")
})

public class MensagemFinanceiraComplemento extends AbstractEntidade<Long> {
	
	public static final String NQ_SELECT_BY_ID_DATA_VALOR = "MensagemFinanceiraComplemento.NQ_SELECT_BY_ID_DATA_VALOR";
	public static final String NQ_SELECT_BY_ENVIO_FECHAMENTO = "MensagemFinanceiraComplemento.NQ_SELECT_BY_ENVIO_FECHAMENTO";
	public static final String NQ_SELECT_BY_TIPO_OPERACAO = "MensagemFinanceiraComplemento.NQ_SELECT_BY_TIPO_OPERACAO";
	public static final String NQ_SELECT_BY_MEIO_PAGAMENTO = "MensagemFinanceiraComplemento.NQ_SELECT_BY_MEIO_PAGAMENTO";
	public static final String NQ_SELECT_BY_TS_ENVIO_NSGD = "MensagemFinanceiraComplemento.NQ_SELECT_BY_TS_ENVIO_NSGD";
	public static final String NQ_SELECT_BY_TS_ENVIO_MARKETPLACE = "MensagemFinanceiraComplemento.NQ_SELECT_BY_TS_ENVIO_MARKETPLACE";
	public static final String NQ_SELECT_BY_NSGD = "MensagemFinanceiraComplemento.NQ_SELECT_BY_NSGD";
	public static final String NQ_SELECT_BY_MARKETPLACE = "MensagemFinanceiraComplemento.NQ_SELECT_BY_MARKETPLACE";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_MENSAGEM_FNNCA_CMPLR")
	private Long id;

	@Column(name = "VR_BRUTO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_MEIO_PAGAMENTO", referencedColumnName = "NU_MEIO_PAGAMENTO")
	private MeioPagamento meioPagamento;

	@Column(name = "DE_CONTA_MEIO_PAGAMENTO")
	private String conta;

	@Column(name = "TS_MENSAGEM_FNNCA_CMPLR")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data tsEnvio;

	@Column(name = "IC_TIPO_OPERACAO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private TipoOperacaoFinanceira tipoOperacao;

	@Column(name = "CO_RESPOSTA_FINANCEIRA")
	private Long codigoResposta;

	@Column(name = "DE_RESPOSTA_FINANCEIRA")
	private String descricaoResposta;

	@Column(name = "DE_JUSTIFICATIVA")
	private String justificativa;

	@Transient
	private boolean previa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_CANAL_DISTRIBUICAO", referencedColumnName = "NU_CD")
	private Loterica loterica;

	@Column(name = "VR_COTA_ADQUIRIDA_COMPLEMENTO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorCota;

	@Column(name = "VR_TRFA_COTA_ADQRA_COMPLEMENTO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorTarifaCota;

	@Column(name = "VR_CUSTEIO_COTA_COMPLEMENTO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorCusteioCota;

	@Column(name = "VR_TRFA_CSTEO_COTA_COMPLEMENTO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorTarifaCusteioCota;

	public MensagemFinanceiraComplemento() {
		// Construtor vazio para criação dinâmica de linhas para a geração do Complemento manual
	}

	public MensagemFinanceiraComplemento(TipoOperacaoFinanceira tipo, MeioPagamento meioPagamento, Decimal valor, String justificativa, boolean isPrevia) {
		this.tipoOperacao = tipo;
		this.meioPagamento = meioPagamento;
		this.valor = valor;
		this.justificativa = justificativa;
		this.conta = meioPagamento.getConta().toString();
		this.previa = isPrevia;
	}

	public MensagemFinanceiraComplemento(TipoOperacaoFinanceira tipo, MeioPagamento meioPagamento, String justificativa, Loterica loterica, Decimal valorCota,
		Decimal valorTarifaCota, Decimal valorCusteioCota,
		Decimal valorTarifaCusteioCota, boolean isPrevia) {
		super();
		this.valor = Decimal.ZERO;
		this.meioPagamento = meioPagamento;
		this.conta = meioPagamento.getConta().toString();

		// Não pode ter esse campo null
		this.tipoOperacao = tipo;

		this.justificativa = justificativa;
		this.loterica = loterica;
		this.valorCota = valorCota;
		this.valorTarifaCota = valorTarifaCota;
		this.valorCusteioCota = valorCusteioCota;
		this.valorTarifaCusteioCota = valorTarifaCusteioCota;
		this.previa = isPrevia;
	}

	public TipoOperacaoFinanceira getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacaoFinanceira tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Decimal getValor() {
		return valor;
	}

	public void setValor(Decimal valor) {
		this.valor = valor;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}


	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(MeioPagamento meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	public Data getTsEnvio() {
		return tsEnvio;
	}

	public void setTsEnvio(Data tsEnvio) {
		this.tsEnvio = tsEnvio;
	}

	public Long getCodigoResposta() {
		return codigoResposta;
	}

	public void setCodigoResposta(Long codigoResposta) {
		this.codigoResposta = codigoResposta;
	}

	public String getDescricaoResposta() {
		return descricaoResposta;
	}

	public void setDescricaoResposta(String descricaoResposta) {
		this.descricaoResposta = descricaoResposta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		String nomeMeioPagamento = null;
		if (meioPagamento != null) {
			nomeMeioPagamento = meioPagamento.getNome();
		}
		return "Campos da Mensagem Financeira: Operação = " + tipoOperacao + ", Valor = " + valor + ", Justificativa = " + justificativa + ", Meio de Pagamento = "
			+ nomeMeioPagamento + ".";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getConta() {
		return conta;
	}

	public boolean isPrevia() {
		return previa;
	}

	public void setPrevia(boolean previa) {
		this.previa = previa;
	}

	public Loterica getLoterica() {
		return loterica;
	}

	public void setLoterica(Loterica loterica) {
		this.loterica = loterica;
	}

	public Decimal getValorCota() {
		return valorCota;
	}

	public void setValorCota(Decimal valorCota) {
		this.valorCota = valorCota;
	}

	public Decimal getValorTarifaCota() {
		return valorTarifaCota;
	}

	public void setValorTarifaCota(Decimal valorTarifaCota) {
		this.valorTarifaCota = valorTarifaCota;
	}

	public Decimal getValorCusteioCota() {
		return valorCusteioCota;
	}

	public void setValorCusteioCota(Decimal valorCusteioCota) {
		this.valorCusteioCota = valorCusteioCota;
	}

	public Decimal getValorTarifaCusteioCota() {
		return valorTarifaCusteioCota;
	}

	public void setValorTarifaCusteioCota(Decimal valorTarifaCusteioCota) {
		this.valorTarifaCusteioCota = valorTarifaCusteioCota;
	}

}
