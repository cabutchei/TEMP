package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;

@Entity
@Table(name = "LCETB063_MENSAGEM_CMPMO_RTRNO", schema = DatabaseConfig.SCHEMA)
public class MensagemFinanceiraComplementoRetorno extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_MENSAGEM_CMPMO_RETORNO")
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "NU_MENSAGEM_FNNCA_CMPLR", referencedColumnName = "NU_MENSAGEM_FNNCA_CMPLR")
	private MensagemFinanceiraComplemento mensagemFinanceiraComplemento;

	@Column(name = "NU_AGENCIA_LOTERICO")
	private Integer numAgencia;
	
	@Column(name = "NU_CONTA_CORRENTE_LOTERICO")
	private Long numContaCorrente;

	@Column(name = "NU_OPERACAO_CONTA_LOTERICO")
	private String numOperacao;

	@Column(name = "NO_TIPO_CONTA_LOTERICO")
	private String tipoConta;

	@Column(name = "NU_LANCAMENTO_FINANCEIRO")
	private String lancamentoFinanceiro;


	public Integer getNumAgencia() {
		return numAgencia;
	}

	public void setNumAgencia(Integer numAgencia) {
		this.numAgencia = numAgencia;
	}

	public Long getNumContaCorrente() {
		return numContaCorrente;
	}

	public void setNumContaCorrente(Long numContaCorrente) {
		this.numContaCorrente = numContaCorrente;
	}

	public String getNumOperacao() {
		return numOperacao;
	}

	public void setNumOperacao(String numOperacao) {
		this.numOperacao = numOperacao;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getLancamentoFinanceiro() {
		return lancamentoFinanceiro;
	}

	public void setLancamentoFinanceiro(String lancamentoFinanceiro) {
		this.lancamentoFinanceiro = lancamentoFinanceiro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MensagemFinanceiraComplemento getMensagemFinanceiraComplemento() {
		return mensagemFinanceiraComplemento;
	}

	public void setMensagemFinanceiraComplemento(MensagemFinanceiraComplemento mensagemFinanceiraComplemento) {
		this.mensagemFinanceiraComplemento = mensagemFinanceiraComplemento;
	}

}
