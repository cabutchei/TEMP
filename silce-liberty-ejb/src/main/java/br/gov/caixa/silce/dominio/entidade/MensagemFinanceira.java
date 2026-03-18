package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.TipoOperacaoFinanceira;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.silce.dominio.openjpa.TimestampValueHandler;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

@Entity
@Table(name = "LCETB050_MENSAGEM_MVMTO_FNNCO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = MensagemFinanceira.NQ_SELECT_BY_DATA_FECHAMENTO, query = "Select mf from MensagemFinanceira mf where mf.fechamento.data = ?1 "),
		@NamedQuery(name = MensagemFinanceira.NQ_SELECT_BY_DATA_ENVIO, query = "Select mf from MensagemFinanceira mf where mf.dataEnvio = ?1 "),
		@NamedQuery(name = MensagemFinanceira.NQ_SELECT_BY_MEIO_PAGAMENTO, query = "Select mf from MensagemFinanceira mf "
			+ "where mf.meio.id = ?1 order by ef.fechamento.data desc, ef.dataEnvio desc"),
		@NamedQuery(name = MensagemFinanceira.NQ_SELECT_BY_TIPO_OPERACAO, query = "Select mf from MensagemFinanceira mf "
			+ "where mf.tipo = ?1 order by ef.fechamento.data desc, ef.dataEnvio desc"),
		@NamedQuery(name = MensagemFinanceira.NQ_SELECT_ALL, query = "Select mf from MensagemFinanceira mf order by mf.fechamento.data desc, mf.dataEnvio desc")
})
@NamedNativeQueries({

})
public class MensagemFinanceira extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_DATA_FECHAMENTO = "MensagemFinanceira.NQ_SELECT_BY_DATA_FECHAMENTO";
	public static final String NQ_SELECT_BY_DATA_ENVIO = "MensagemFinanceira.NQ_SELECT_BY_DATA_ENVIO";
	public static final String NQ_SELECT_BY_TIPO_OPERACAO = "MensagemFinanceira.NQ_SELECT_BY_TIPO_OPERACAO";
	public static final String NQ_SELECT_BY_MEIO_PAGAMENTO = "MensagemFinanceira.NQ_SELECT_BY_MEIO_PAGAMENTO";
	public static final String NQ_SELECT_ALL = "MensagemFinanceira.NQ_SELECT_ALL";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_MENSAGEM_MVMTO_FINANCEIRO")
	private Long id;

	@Column(name = "VR_BRUTO_OPERACAO_FINANCEIRA")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valor;

	@Column(name = "TS_ENVIO_MENSAGEM_OPRCO_FNNCO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataEnvio;

	@Column(name = "IC_TIPO_OPERACAO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private TipoOperacaoFinanceira tipo;

	@Column(name = "DE_CONTA_MEIO_PAGAMENTO")
	private String conta;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_MEIO_PAGAMENTO", referencedColumnName = "NU_MEIO_PAGAMENTO")
	private MeioPagamento meioPagamento;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_CONTROLE_FECHAMENTO_OPRCO", referencedColumnName = "NU_CONTROLE_FECHAMENTO")
	private Fechamento fechamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Decimal getValor() {
		return valor;
	}

	public void setValor(Decimal valor) {
		this.valor = valor;
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(MeioPagamento meio) {
		this.meioPagamento = meio;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}

	public Data getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Data dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public TipoOperacaoFinanceira getTipo() {
		return tipo;
	}

	public void setTipo(TipoOperacaoFinanceira tipo) {
		this.tipo = tipo;
	}

}
