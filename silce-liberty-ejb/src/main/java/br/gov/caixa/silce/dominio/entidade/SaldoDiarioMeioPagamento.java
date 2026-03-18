package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.EnumUtil;

@Entity
@Table(name = "LCETB023_SALDO_DIARIO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name=SaldoDiarioMeioPagamento.NQ_SELECT_BY_DATA_MEIO_PAGAMENTO, 
			query = "Select sd from SaldoDiarioMeioPagamento sd join fetch sd.fechamento where sd.fechamento.data=?1 and sd.meioPagamento.id = ?2 and sd.tipo = ?3"),
	@NamedQuery(name=SaldoDiarioMeioPagamento.NQ_SELECT_BY_MEIO_PAGAMENTO, 
			query = "Select sd from SaldoDiarioMeioPagamento sd join fetch sd.fechamento where sd.meioPagamento.id = ?1 and sd.tipo = ?2 order by sd.fechamento.data desc"),
	@NamedQuery(name=SaldoDiarioMeioPagamento.NQ_SELECT_BY_DATA, 
			query = "Select sd from SaldoDiarioMeioPagamento sd join fetch sd.fechamento where sd.fechamento.data=?1"),
	@NamedQuery(name=SaldoDiarioMeioPagamento.NQ_SELECT_BY_DATA_INICIO_MP_TIPO, 
			query = "Select sd from SaldoDiarioMeioPagamento sd join fetch sd.fechamento where sd.fechamento.data >= ?1"
				+ " and sd.meioPagamento.id = ?2 and sd.tipo = ?3 order by sd.fechamento.data asc"),
		@NamedQuery(name = SaldoDiarioMeioPagamento.NQ_DELETE_BY_FECHAMENTO, query = "Delete from SaldoDiarioMeioPagamento saldo where saldo.fechamento.id = ?1")
})
public class SaldoDiarioMeioPagamento extends AbstractEntidade<Long> {
	
	public static final String NQ_SELECT_BY_MEIO_PAGAMENTO = "SaldoDiarioMeioPagamento.NQ_SELECT_BY_MEIO_PAGAMENTO";
	public static final String NQ_SELECT_BY_DATA = "SaldoDiarioMeioPagamento.NQ_SELECT_BY_DATA";
	public static final String NQ_SELECT_BY_DATA_INICIO_MP_TIPO = "SaldoDiarioMeioPagamento.NQ_SELECT_BY_DATA_INICIO_MP_TIPO";
	public static final String NQ_SELECT_BY_DATA_MEIO_PAGAMENTO = "SaldoDiarioMeioPagamento.NQ_SELECT_BY_DATA_MEIO_PAGAMENTO";
	public static final String NQ_DELETE_BY_FECHAMENTO = "SaldoDiarioMeioPagamento.NQ_DELETE_BY_FECHAMENTO";

	private static final long serialVersionUID = 989041624807244370L;
	
	public enum Situacao implements CaixaEnum<Character> {

		PENDENTE('1', "Pendente"),
		RESOLVIDO('2', "Resolvido"),
		RESOLUCAO_MANUAL('3', "Res. Manual");

		private final Character value;
		private final String descricao;

		private Situacao(Character value, String descricao) {
			this.value = value;
			this.descricao = descricao;
		}

		public Character getValue() {
			return value;
		}
		
		public static Situacao getByValue(Character value) {
			return EnumUtil.recupereByValue(values(), value);
		}
		
		public Boolean isPendente() {
			return this.equals(PENDENTE);
		}

		public String getDescricao() {
			return descricao;
		}

	}
	public enum Tipo implements CaixaEnum<Character> {
		/*
		 * 1 = credito, 2 = débito
		 * 
		 * Pagar = Valor que o SILCE tem que Pagar para o meio de pagamento (premios)
		 * 
		 * Receber = O que o SILCE tem que receber do meio de pagamento (compras)
		 */
		
		PAGAR('1', "Pagar"),
		RECEBER('2', "Receber");
		
		private final Character value;
		private final String descricao;
		
		private Tipo(Character value, String descricao) {
			this.value = value;
			this.descricao = descricao;
		}
		
		public Character getValue() {
			return value;
		}
		
		public static Tipo getByValue(Character value) {
			return EnumUtil.recupereByValue(values(), value);
		}
		
		public String getDescricao() {
			return descricao;
		}
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NU_SALDO_DIARIO")
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_MEIO_PAGAMENTO", referencedColumnName = "NU_MEIO_PAGAMENTO")
	private MeioPagamento meioPagamento;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_CONTROLE_FECHAMENTO", referencedColumnName = "NU_CONTROLE_FECHAMENTO")
	private Fechamento fechamento;
	
	@Column(name="VR_ESPERADO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorEsperado;
	
	@Column(name="VR_EFETIVADO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorEfetivado;
	
	@Column(name="VR_SALDO_ACUMULADO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal saldoAcumulado;
	
	@Column(name = "IC_SITUACAO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Situacao situacao;
	
	@Column(name = "DE_JUSTIFICATIVA")
	private String justificativa;
	
	@Column(name = "IC_TIPO_OPERACAO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Tipo tipo;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(MeioPagamento meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}

	public Decimal getSaldoAcumulado() {
		return saldoAcumulado;
	}

	public void setSaldoAcumulado(Decimal saldoAcumulado) {
		this.saldoAcumulado = saldoAcumulado;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Decimal getValorEsperado() {
		return valorEsperado;
	}

	public void setValorEsperado(Decimal valorEsperado) {
		this.valorEsperado = valorEsperado;
	}

	public Decimal getValorEfetivado() {
		return valorEfetivado;
	}

	public void setValorEfetivado(Decimal valorEfetivado) {
		this.valorEfetivado = valorEfetivado;
	}

}
