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
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.ExternalReferenceUtil;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.util.Decimal;

@Entity
@Table(name = "LCETB026_DIVERGENCIA_BATIMENTO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name=DivergenciaBatimento.NQ_SELECT_BY_BATIMENTO, 
			query = "Select db from DivergenciaBatimento db where db.batimento.id = ?1"),
	
	@NamedQuery(name=DivergenciaBatimento.NQ_SELECT_BY_COMPRA, 
			query = "Select db from DivergenciaBatimento db where db.referenciaNSU in ?1")
})
public class DivergenciaBatimento extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_BATIMENTO = "DivergenciaBatimento.NQ_SELECT_BY_BATIMENTO";
	
	private static final long serialVersionUID = 989041624807244370L;

	public static final String NQ_SELECT_BY_COMPRA = "DivergenciaBatimento.NQ_SELECT_BY_COMPRA";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NU_DIVERGENCIA_BATIMENTO")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_BATIMENTO_PAGAMENTO", referencedColumnName = "NU_BATIMENTO_PAGAMENTO")
	private Batimento batimento;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_TIPO_OPERACAO", referencedColumnName = "NU_TIPO_OPERACAO")
	@EagerFetchMode(FetchMode.PARALLEL)
	private Operacao operacao;

	@Column(name="NU_SEQUENCIAL_OPERACAO")
	private String referenciaNSU;

	@Column(name="VR_SILCE")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorSilce;
	
	@Column(name = "CO_OPERACAO_PAGAMENTO")
	private String nsuMeioPagamento;
	
	@Column(name="VR_MEIO_PAGAMENTO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorMeioPagamento;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_TIPO_DIVERGENCIA", referencedColumnName = "NU_TIPO_DIVERGENCIA")
	@EagerFetchMode(FetchMode.PARALLEL)
	private TipoDivergenciaBatimento tipo;
	
	@Transient
	private Long nsuSILCE;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
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

	public String getReferenciaNSU() {
		return referenciaNSU;
	}

	public void setReferenciaNSU(String referenciaNSU) {
		this.referenciaNSU = referenciaNSU;
	}

	public String getNsuMeioPagamento() {
		return nsuMeioPagamento;
	}

	public void setNsuMeioPagamento(String nsuMeioPagamento) {
		this.nsuMeioPagamento = nsuMeioPagamento;
	}

	public Decimal getValorSilce() {
		return valorSilce;
	}

	public void setValorSilce(Decimal valorSilce) {
		this.valorSilce = valorSilce;
	}

	public Decimal getValorMeioPagamento() {
		return valorMeioPagamento;
	}

	public void setValorMeioPagamento(Decimal valorMeioPagamento) {
		this.valorMeioPagamento = valorMeioPagamento;
	}

	public String getDescricao() {
		return tipo.getDescricao();
	}

	public Long getNsuSILCE() {
		if(nsuSILCE == null && getReferenciaNSU() != null) {
			//o atributo estava com: "00000008468        " estava com tipo errado, char de 30 e não varchar
			this.nsuSILCE = ExternalReferenceUtil.getNSU(getReferenciaNSU().trim());
		}
		return nsuSILCE;
	}

	public TipoDivergenciaBatimento getTipo() {
		return tipo;
	}

	public void setTipo(TipoDivergenciaBatimento tipo) {
		this.tipo = tipo;
	}
}
