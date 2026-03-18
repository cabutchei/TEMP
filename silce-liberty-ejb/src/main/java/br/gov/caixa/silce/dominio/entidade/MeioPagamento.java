package br.gov.caixa.silce.dominio.entidade;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.openjpa.CNPJValueHandler;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.pagamento.ParametroPagamento;
import br.gov.caixa.util.CNPJ;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.Conta;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.EnumUtil;

@Entity
@Table(name = "LCETB018_MEIO_PAGAMENTO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name = MeioPagamento.NQ_SELECT_BY_SITUACAO, query = "Select meioPagamento From MeioPagamento meioPagamento Where meioPagamento.situacao=?1"),
	@NamedQuery(name = MeioPagamento.NQ_SELECT_BY_CNPJ, query = "Select meioPagamento From MeioPagamento meioPagamento Where meioPagamento.cnpj=?1"),
	@NamedQuery(name = MeioPagamento.NQ_SELECT_BY_DATA_ATIVACAO, query = "Select meioPagamento From MeioPagamento meioPagamento Where meioPagamento.dataAtivacao=?1"),
	@NamedQuery(name = MeioPagamento.NQ_SELECT_BY_CONTA, query = "Select meioPagamento From MeioPagamento meioPagamento Where meioPagamento.conta.agencia =?1 and  meioPagamento.conta.operacao =?2 and  meioPagamento.conta.numero =?3 and  meioPagamento.conta.dv =?4")
})
public class MeioPagamento extends AbstractEntidadeEnum<Long, Meio> {

	private static final long serialVersionUID = 1L;
	
	public static final String NQ_SELECT_BY_SITUACAO = "MeioPagamento.NQ_SELECT_BY_SITUACAO";
	public static final String NQ_SELECT_BY_CNPJ = "MeioPagamento.NQ_SELECT_BY_CNPJ";
	public static final String NQ_SELECT_BY_DATA_ATIVACAO = "MeioPagamento.NQ_SELECT_BY_DATA_ATIVACAO";
	public static final String NQ_SELECT_BY_CONTA = "MeioPagamento.NQ_SELECT_BY_CONTA";

	public enum Meio implements CaixaEnum<Long> {

		MERCADO_PAGO(1L, "MPG"), CARTEIRA_ELETRONICA(2L, "DMO"), PONTOS_SILCE(3L, "PLE"), MERCADO_PAGO_BASICO(4L, "MPB"), RECARGA_PAY(5L, "RPY"), PIX(6L, "PIX");

		private Meio(Long codigo, String sigla) {
			this.codigo = codigo;
			this.sigla = sigla;
		}

		private final Long codigo;
		private final String sigla;

		public final List<ParametroPagamento> getParametros() {
			return ParametroPagamento.getParametros(this);
		}

		public static Meio getByMeioPagamento(MeioPagamento mp) {
			if (mp == null) {
				return null;
			}
			return EnumUtil.recupereByValue(values(), mp.getId());
		}
		
		public static Meio getByValue(Long value) {
			return EnumUtil.recupereByValue(values(), value);
		}

		@Override
		public Long getValue() {
			return this.codigo;
		}

		public String getSigla() {
			return sigla;
		}

	}
	
	public enum Situacao implements CaixaEnum<Long> {

		ATIVO(1L, "Ativo"),
		BLOQUEADO(2L, "Bloqueado");

		private final Long value;
		private final String descricao;

		private Situacao(Long value, String descricao) {
			this.value = value;
			this.descricao = descricao;
		}

		public Long getValue() {
			return value;
		}
		
		public boolean isAtivo() {
			return this.equals(ATIVO); 
		}

		public static Situacao getByValue(Long value) {
			return EnumUtil.recupereByValue(values(), value);
		}

		public String getDescricao() {
			return descricao;
		}

	}

	public enum TipoMeioPagamento implements CaixaEnum<Character> {

		INTERNO('1'), EXTERNO('2');

		private final Character value;

		private TipoMeioPagamento(Character value) {
			this.value = value;
		}

		public Character getValue() {
			return value;
		}

		public static TipoMeioPagamento getByValue(Character value) {
			return EnumUtil.recupereByValue(values(), value);
		}

	}

	@Id
	@Column(name = "NU_MEIO_PAGAMENTO")
	private Long id;

	@Column(name = "DE_MEIO_PAGAMENTO_APOSTA")
	private String nome;
	
	@Column(name = "DE_MEIO_PAGAMENTO_PREMIO")
	private String nomeNoPagamentoPremio;

	@Column(name = "IC_SITUACAO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Situacao situacao;

	@Column(name = "NU_CNPJ")
	@Strategy(CNPJValueHandler.STRATEGY_NAME)
	private CNPJ cnpj;
	
	@Column(name = "DT_ATIVACAO_PAGAMENTO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataAtivacao;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="agencia", column=@Column(name="NU_AGENCIA")),
		@AttributeOverride(name="operacao", column=@Column(name="NU_OPERACAO")),
		@AttributeOverride(name="numero", column=@Column(name="NU_CONTA_CORRENTE")),
		@AttributeOverride(name="dv", column=@Column(name="NU_DV"))
	})
	private Conta conta;

	@Column(name = "IC_TIPO_MEIO_PAGAMENTO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private TipoMeioPagamento tipo;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public CNPJ getCnpj() {
		return cnpj;
	}

	public void setCnpj(CNPJ cnpj) {
		this.cnpj = cnpj;
	}

	public Boolean getAtivo() {
		return isAtivo();
	}

	public Boolean isAtivo() {
		return getSituacao().isAtivo();
	}

	public final List<ParametroPagamento> getParametros() {
		return ParametroPagamento.getParametros(Meio.getByMeioPagamento(this));
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Data getDataAtivacao() {
		return dataAtivacao;
	}

	public void setDataAtivacao(Data dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}

	@Override
	protected Meio[] createValores() {
		return Meio.values();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof MeioPagamento)) {
			return false;
		}
		MeioPagamento other = (MeioPagamento) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public String getNomeNoPagamentoPremio() {
		return nomeNoPagamentoPremio;
	}

	public void setNomeNoPagamentoPremio(String nomeNoPagamentoPremio) {
		this.nomeNoPagamentoPremio = nomeNoPagamentoPremio;
	}
	
	public boolean isContaVinculada() {
		return conta != null && conta.getAgencia()!= null && conta.getNumero() != null && conta.getOperacao() != null && conta.getDv() != null;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public String toString() {
		return "MeioPagamento [id=" + id + ", nome=" + nome + ", nomeNoPagamentoPremio=" + nomeNoPagamentoPremio + "]";
	}

	public String meioPagamentoConverter() {
		// TODO Auto-generated method stub
		return null;
	}

}
