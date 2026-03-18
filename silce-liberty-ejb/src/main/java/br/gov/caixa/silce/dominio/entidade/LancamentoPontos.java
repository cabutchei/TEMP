package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.TimestampValueHandler;
import br.gov.caixa.util.Data;

@Entity
@Table(name = "LCETB041_LNCMO_PONTO_APSTR", schema = DatabaseConfig.SCHEMA)
public class LancamentoPontos extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_LANCAMENTO")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NU_PONTO_APSTR", referencedColumnName = "NU_PONTO_APSTR")
	private ContaPontos conta;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NU_TIPO_LANCAMENTO", referencedColumnName = "NU_TIPO_LANCAMENTO")
	private TipoLancamento tipo;

	@Column(name = "TS_ATULIZACAO_LNCMO")
	@Strategy(TimestampValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataUltimaAlteracao;

	@Column(name = "QT_PONTOS_LNCMO")
	private Long pontos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ContaPontos getConta() {
		return conta;
	}

	public void setConta(ContaPontos conta) {
		this.conta = conta;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	public Data getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Data dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public Long getPontos() {
		return pontos;
	}

	public void setPontos(Long pontos) {
		this.pontos = pontos;
	}
}
