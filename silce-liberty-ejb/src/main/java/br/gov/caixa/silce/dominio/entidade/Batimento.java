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

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.EnumUtil;

@Entity
@Table(name = "LCETB025_BATIMENTO_PAGAMENTO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = Batimento.NQ_SELECT_BY_DATA_FECHAMENTO_MEIO_PAGAMENTO_TIPO,
			query = "Select b from Batimento b join fetch b.fechamento where b.fechamento.data = ?1 and b.meioPagamento.id = ?2 and b.tipo = ?3"),
	@NamedQuery(name=Batimento.NQ_SELECT_ALL_ORDER_BY_DATA_FECHAMENTO, 
			query = "Select b from Batimento b join fetch b.fechamento order by b.fechamento.data desc")
})
@NamedNativeQueries({
		@NamedNativeQuery(name = Batimento.NQ_SELECT_DATA_MAX_BY_MEIO_PAGAMENTO_TIPO, query = "Select max(f.DT_CONTROLE_FECHAMENTO) from LCE.LCETB025_BATIMENTO_PAGAMENTO b LEFT OUTER JOIN LCE.LCETB021_CONTROLE_FECHAMENTO f ON b.NU_CONTROLE_FECHAMENTO = f.NU_CONTROLE_FECHAMENTO WHERE b.NU_MEIO_PAGAMENTO = ?1 and b.IC_TIPO_BATIMENTO = ?2")
})
public class Batimento extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_DATA_FECHAMENTO_MEIO_PAGAMENTO_TIPO = "Batimento.NQ_SELECT_BY_DATA_FECHAMENTO_MEIO_PAGAMENTO_TIPO";
	public static final String NQ_SELECT_ALL_ORDER_BY_DATA_FECHAMENTO = "Batimento.NQ_SELECT_ALL_ORDER_BY_DATA_FECHAMENTO";
	public static final String NQ_SELECT_DATA_MAX_BY_MEIO_PAGAMENTO_TIPO = "Batimento.NQ_SELECT_DATA_MAX_BY_MEIO_PAGAMENTO_TIPO";
	
	private static final long serialVersionUID = 1L;
	
	public enum Situacao implements CaixaEnum<Character> {

		CORRETO('1'),
		DIVERGENTE('2'),
		ERRO('3');

		private final Character value;

		private Situacao(Character value) {
			this.value = value;
		}

		public Character getValue() {
			return value;
		}
		
		public static Situacao getByValue(Character value) {
			return EnumUtil.recupereByValue(values(), value);
		}

	}
	
	public enum Tipo implements CaixaEnum<Character> {
		
		ENTRADA('E'),
		SAIDA('S');
		
		private final Character value;
		
		private Tipo(Character value) {
			this.value = value;
		}
		
		public Character getValue() {
			return value;
		}
		
		public static Tipo getByValue(Character value) {
			return EnumUtil.recupereByValue(values(), value);
		}
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NU_BATIMENTO_PAGAMENTO")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_CONTROLE_FECHAMENTO", referencedColumnName = "NU_CONTROLE_FECHAMENTO")
	private Fechamento fechamento;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_MEIO_PAGAMENTO", referencedColumnName = "NU_MEIO_PAGAMENTO")
	private MeioPagamento meioPagamento;

	@Column(name="IC_TIPO_BATIMENTO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Tipo tipo;
	
	@Column(name="DE_BATIMENTO_PAGAMENTO")
	private String descricao;
	
	@Column(name="DE_CONTEUDO_HASH")
	private String hashConteudo;
	
	@Column(name = "TS_INICIO_PROCESSAMENTO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data inicioProcessamento;
	
	@Column(name = "TS_FIM_PROCESSAMENTO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data fimProcessamento;

	@Column(name="IC_SITUACAO_BATIMENTO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Situacao situacao;
	
	@Column(name="QT_OPERACAO")
	private Long quantidadeOperacoes;
	
	@Column(name="DE_OBSERVACAO_BATIMENTO")
	private String descricaoDetalhadaErro;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(MeioPagamento meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getHashConteudo() {
		return hashConteudo;
	}

	public void setHashConteudo(String hashConteudo) {
		this.hashConteudo = hashConteudo;
	}

	public Data getInicioProcessamento() {
		return inicioProcessamento;
	}

	public void setInicioProcessamento(Data inicioProcessamento) {
		this.inicioProcessamento = inicioProcessamento;
	}

	public Data getFimProcessamento() {
		return fimProcessamento;
	}

	public void setFimProcessamento(Data fimProcessamento) {
		this.fimProcessamento = fimProcessamento;
	}


	public Long getQuantidadeOperacoes() {
		return quantidadeOperacoes;
	}

	public void setQuantidadeOperacoes(Long quantidadeOperacoes) {
		this.quantidadeOperacoes = quantidadeOperacoes;
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

	public String getDescricaoDetalhadaErro() {
		return descricaoDetalhadaErro;
	}

	public void setDescricaoDetalhadaErro(String descricaoDetalhadaErro) {
		this.descricaoDetalhadaErro = descricaoDetalhadaErro;
	}
}
