package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;

@Entity
@Table(name = "LCETB062_DETALHE_RETORNO_FCHMO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = DetalhamentoRetornoFechamento.NQ_SELECT_COUNT_RETORNO_FINANCEIRO_BY_FECHAMENTO_AND_SISTEMA_FECHAMENTO,
			query = "Select count(d) from DetalhamentoRetornoFechamento d "
				+ "join fetch d.detalheEnvioFechamento "
				+ "join fetch d.detalheEnvioFechamento.envioFechamento "
				+ "where d.detalheEnvioFechamento.envioFechamento.fechamento.data = ?1 and (d.detalheEnvioFechamento.envioFechamento.sistemaFechamento.id = ?2)"),
		@NamedQuery(name = DetalhamentoRetornoFechamento.NQ_SELECT_ALL, 
			query = "Select d from DetalhamentoRetornoFechamento d "
				+ "join fetch d.detalheEnvioFechamento "
				+ "join fetch d.detalheEnvioFechamento.envioFechamento "
				+ "left join fetch d.detalheEnvioFechamento.loterica "
				+ "order by d.detalheEnvioFechamento.envioFechamento.dataEnvio desc, d.lancamentoFinanceiro asc"),
		@NamedQuery(name = DetalhamentoRetornoFechamento.NQ_SELECT_ALL_DATA, 
			query = "Select d from DetalhamentoRetornoFechamento d "
				+ "join fetch d.detalheEnvioFechamento "
				+ "join fetch d.detalheEnvioFechamento.envioFechamento "
				+ "left join fetch d.detalheEnvioFechamento.loterica "
				+ "where d.detalheEnvioFechamento.envioFechamento.fechamento.data = ?1 "
				+ "order by d.detalheEnvioFechamento.envioFechamento.dataEnvio desc, d.lancamentoFinanceiro asc")
})
public class DetalhamentoRetornoFechamento extends AbstractEntidade<DetalhamentoRetornoFechamentoPK> {

	private static final long serialVersionUID = 1L;
	
	public static final String NQ_SELECT_COUNT_RETORNO_FINANCEIRO_BY_FECHAMENTO_AND_SISTEMA_FECHAMENTO = "DetalhamentoEnvioFechamento.NQ_SELECT_COUNT_RETORNO_FINANCEIRO_BY_FECHAMENTO_AND_SISTEMA_FECHAMENTO";
	public static final String NQ_SELECT_ALL = "DetalhamentoRetornoFechamento.NQ_SELECT_ALL";
	public static final String NQ_SELECT_ALL_DATA = "DetalhamentoRetornoFechamento.NQ_SELECT_ALL_DATA";

	@EmbeddedId
	private DetalhamentoRetornoFechamentoPK id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "NU_DETALHE_ENVIO_FECHAMENTO", referencedColumnName = "NU_DETALHE_ENVIO_FECHAMENTO")
	private DetalhamentoEnvioFechamento detalheEnvioFechamento;

	@Column(name = "AA_MOVIMENTO_ENVIO_FECHAMENTO")
	private Integer anoMovimentoEnvio;

	@Column(name = "MM_MOVIMENTO_ENVIO_FECHAMENTO")
	private Integer mesMovimentoEnvio;

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

	@Override
	public DetalhamentoRetornoFechamentoPK getId() {
		return id;
	}

	@Override
	public void setId(DetalhamentoRetornoFechamentoPK id) {
		this.id = id;
	}

	public DetalhamentoEnvioFechamento getDetalheEnvioFechamento() {
		return detalheEnvioFechamento;
	}

	public void setEnvioFechamento(DetalhamentoEnvioFechamento detalheEnvioFechamento) {
		this.detalheEnvioFechamento = detalheEnvioFechamento;
	}

	public Integer getAnoMovimentoEnvio() {
		return anoMovimentoEnvio;
	}

	public void setAnoMovimentoEnvio(Integer anoMovimentoEnvio) {
		this.anoMovimentoEnvio = anoMovimentoEnvio;
	}

	public Integer getMesMovimentoEnvio() {
		return mesMovimentoEnvio;
	}

	public void setMesMovimentoEnvio(Integer mesMovimentoEnvio) {
		this.mesMovimentoEnvio = mesMovimentoEnvio;
	}

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

}
