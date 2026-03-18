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
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.TipoOperacaoFinanceira;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

@Entity
@Table(name = "LCETB029_DETALHE_ENVIO_FECHAMENTO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = DetalhamentoEnvioFechamento.NQ_SELECT_BY_ID_DATA_VALOR,
			query = "Select d from DetalhamentoEnvioFechamento d join fetch d.envioFechamento where d.id.id = ?1 and d.envioFechamento.dataEnvio between ?2 and ?3 and d.valor = ?4"),

		@NamedQuery(name=DetalhamentoEnvioFechamento.NQ_SELECT_BY_ENVIO_FECHAMENTO, 
			query="Select d from DetalhamentoEnvioFechamento d where d.envioFechamento.id = ?1"),

		@NamedQuery(name = DetalhamentoEnvioFechamento.NQ_SELECT_BY_TIPO_OPERACAO,
			query = "Select d from DetalhamentoEnvioFechamento d where d.tipo = ?1 order by d.envioFechamento.dataEnvio desc"),

		@NamedQuery(name = DetalhamentoEnvioFechamento.NQ_SELECT_BY_MEIO_PAGAMENTO,
			query = "Select d from DetalhamentoEnvioFechamento d where d.meioPagamento.id = ?1 order by d.envioFechamento.dataEnvio desc"),

		@NamedQuery(name = DetalhamentoEnvioFechamento.NQ_SELECT_BY_TS_ENVIO_MENSAGEM,
			query = "Select d from DetalhamentoEnvioFechamento d join fetch d.envioFechamento where d.meioPagamento is not null and d.envioFechamento.fechamento.data = ?1"
				+ " order by d.envioFechamento.dataEnvio desc"),

		@NamedQuery(name = DetalhamentoEnvioFechamento.NQ_SELECT_ALL, 
			query = "Select d from DetalhamentoEnvioFechamento d join fetch d.envioFechamento order by d.envioFechamento.dataEnvio desc"),

		@NamedQuery(name = DetalhamentoEnvioFechamento.NQ_SELECT_ALL_MENSAGEM,
			query = "Select d from DetalhamentoEnvioFechamento d join fetch d.envioFechamento where d.meioPagamento is not null"
				+ " order by d.envioFechamento.dataEnvio desc"),

		@NamedQuery(name = DetalhamentoEnvioFechamento.NQ_SELECT_COUNT_FINANCEIRO,
			query = "Select count(d) from DetalhamentoEnvioFechamento d join fetch d.envioFechamento "
				+ "where d.envioFechamento.fechamento.data = ?1 and (d.envioFechamento.sistemaFechamento.id = 1 or d.envioFechamento.sistemaFechamento.id = 3)"),

		@NamedQuery(name = DetalhamentoEnvioFechamento.NQ_SELECT_COUNT_FINANCEIRO_BY_SISTEMA_FECHAMENTO, query = "Select count(d) from DetalhamentoEnvioFechamento d join fetch d.envioFechamento "
			+ "where d.envioFechamento.fechamento.data = ?1 and (d.envioFechamento.sistemaFechamento.id = ?2) and d.envioFechamento.envioFechamentoConcluido = true")
})
public class DetalhamentoEnvioFechamento extends AbstractEntidade<DetalhamentoEnvioFechamentoPK> {

	public static final String NQ_SELECT_BY_ENVIO_FECHAMENTO = "DetalhamentoEnvioFechamento.NQ_SELECT_BY_ENVIO_FECHAMENTO";
	public static final String NQ_SELECT_BY_TIPO_OPERACAO = "DetalhamentoEnvioFechamento.NQ_SELECT_BY_TIPO_OPERACAO";
	public static final String NQ_SELECT_BY_MEIO_PAGAMENTO = "DetalhamentoEnvioFechamento.NQ_SELECT_BY_MEIO_PAGAMENTO";
	public static final String NQ_SELECT_BY_TS_ENVIO_MENSAGEM = "DetalhamentoEnvioFechamento.NQ_SELECT_BY_TS_ENVIO";
	public static final String NQ_SELECT_ALL = "DetalhamentoEnvioFechamento.NQ_SELECT_ALL";
	public static final String NQ_SELECT_ALL_MENSAGEM = "DetalhamentoEnvioFechamento.NQ_SELECT_ALL_MENSAGEM";
	public static final String NQ_SELECT_COUNT_FINANCEIRO = "DetalhamentoEnvioFechamento.NQ_SELECT_COUNT_FINANCEIRO";
	public static final String NQ_SELECT_COUNT_FINANCEIRO_BY_SISTEMA_FECHAMENTO = "DetalhamentoEnvioFechamento.NQ_SELECT_COUNT_FINANCEIRO_BY_SISTEMA_FECHAMENTO";
	public static final String NQ_SELECT_BY_ID_DATA_VALOR = "DetalhamentoEnvioFechamento.NQ_SELECT_BY_ID_DATA_VALOR";
	
	private static final long serialVersionUID = 989041624807244370L;
	
	@EmbeddedId
	private DetalhamentoEnvioFechamentoPK id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NU_ENVIO_FECHAMENTO", referencedColumnName = "NU_ENVIO_FECHAMENTO")
	private EnvioFechamento envioFechamento;
	
	@ManyToOne
	@JoinColumn(name = "NU_MEIO_PAGAMENTO", referencedColumnName = "NU_MEIO_PAGAMENTO")
	private MeioPagamento meioPagamento;

	@Column(name="NU_LINHA_ARQUIVO")
	private Integer linhaArquivo;
	
	@Column(name="IC_TIPO_OPERACAO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private TipoOperacaoFinanceira tipo;
	
	@Column(name="DE_OBSERVACAO")
	private String observacao;

	@Column(name = "VR_BRUTO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valor;

	@Column(name = "DE_CONTA_MEIO_PAGAMENTO")
	private String conta;

	@Column(name = "CO_RESPOSTA_FINANCEIRA")
	private Integer codigoResposta;

	@Column(name = "DE_RESPOSTA_FINANCEIRA")
	private String descricaoResposta;

	@Column(name = "IC_DETALHE_ENVIO_FECHAMENTO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	@EagerFetchMode(FetchMode.PARALLEL)
	private TipoExecucao execucao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_CANAL_DISTRIBUICAO", referencedColumnName = "NU_CD")
	private Loterica loterica;

	@Column(name = "VR_COTA_ADQUIRIDA_ENVIO_FCHMO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorCota;

	@Column(name = "VR_TRFA_COTA_ADQRA_ENVO_FCHMO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorTarifaCota;

	@Column(name = "VR_CUSTEIO_COTA_ENVIO_FCHMO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorCusteioCota;

	@Column(name = "VR_TRFA_CSTEO_COTA_ENVIO_FCHMO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorTarifaCusteioCota;

	@Transient
	private Data dataPrevistaPagamento;

	public DetalhamentoEnvioFechamentoPK getId() {
		return id;
	}

	public void setId(DetalhamentoEnvioFechamentoPK id) {
		this.id = id;
	}

	public EnvioFechamento getEnvioFechamento() {
		return envioFechamento;
	}

	public void setEnvioFechamento(EnvioFechamento envioFechamento) {
		this.envioFechamento = envioFechamento;
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(MeioPagamento meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	public Integer getLinhaArquivo() {
		return linhaArquivo;
	}

	public void setLinhaArquivo(Integer linhaArquivo) {
		this.linhaArquivo = linhaArquivo;
	}

	public TipoOperacaoFinanceira getTipo() {
		return tipo;
	}

	public void setTipo(TipoOperacaoFinanceira tipo) {
		this.tipo = tipo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Decimal getValor() {
		return valor;
	}

	public void setValor(Decimal valor) {
		this.valor = valor;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public Integer getCodigoResposta() {
		return codigoResposta;
	}

	public void setCodigoResposta(Integer codigoResposta) {
		this.codigoResposta = codigoResposta;
	}

	public String getDescricaoResposta() {
		return descricaoResposta;
	}

	public void setDescricaoResposta(String descricaoResposta) {
		this.descricaoResposta = descricaoResposta;
	}

	public TipoExecucao getExecucao() {
		return execucao;
	}

	public void setExecucao(TipoExecucao execucao) {
		this.execucao = execucao;
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

	public Data getDataPrevistaPagamento() {
		return dataPrevistaPagamento;
	}

	public void setDataPrevistaPagamento(Data dataPrevistaPagamento) {
		this.dataPrevistaPagamento = dataPrevistaPagamento;
	}
}
