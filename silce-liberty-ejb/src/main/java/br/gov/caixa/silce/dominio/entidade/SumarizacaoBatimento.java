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

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.util.Decimal;

@Entity
@Table(name = "LCETB037_SUMARIZACAO_BTMNO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = SumarizacaoBatimento.NQ_SELECT_BY_BATIMENTO, query = "Select sb from SumarizacaoBatimento sb where sb.batimento.id = ?1")
})
public class SumarizacaoBatimento extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_SELECT_BY_BATIMENTO = "SumarizacaoBatimento.NQ_SELECT_BY_BATIMENTO";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_SMRZO_BTMNO")
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_BTMNO_PGMNO", referencedColumnName = "NU_BATIMENTO_PAGAMENTO")
	private Batimento batimento;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_TIPO_OPERACAO", referencedColumnName = "NU_TIPO_OPERACAO")
	@EagerFetchMode(FetchMode.PARALLEL)
	private Operacao operacao;
	
	@Column(name = "QT_OPERACAO")
	private Long quantidadeOperacoes;
	
	@Column(name = "VR_BRUTO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorBruto;
	
	@Column(name = "VR_COMISSAO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorComissao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Batimento getBatimento() {
		return batimento;
	}

	public void setBatimento(Batimento batimento) {
		this.batimento = batimento;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Long getQuantidadeOperacoes() {
		return quantidadeOperacoes;
	}

	public void setQuantidadeOperacoes(Long quantidadeOperacoes) {
		this.quantidadeOperacoes = quantidadeOperacoes;
	}

	public Decimal getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(Decimal valorBruto) {
		this.valorBruto = valorBruto;
	}

	public Decimal getValorComissao() {
		return valorComissao;
	}

	public void setValorComissao(Decimal valorComissao) {
		this.valorComissao = valorComissao;
	}

}
