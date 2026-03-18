package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.util.Data;

@Entity
@Table(name = "LCETB003_TERMO_ADESAO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = TermoDeUso.NQ_SELECT_ULTIMA_VERSAO, query = "Select max(tdu.id) from TermoDeUso tdu"),
		@NamedQuery(name = TermoDeUso.NQ_SELECT_ORDEM_DESC, query = "Select tdu from TermoDeUso tdu order by tdu.id desc"),
		@NamedQuery(name = TermoDeUso.NQ_SELECT_BY_DATA_MAXIMA, query = "Select tdu from TermoDeUso tdu where tdu.dataInicioVigencia <= ?1 order by tdu.dataInicioVigencia desc"),
})
public class TermoDeUso extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_SELECT_ULTIMA_VERSAO = "TermoDeUso.findUltimaVersao";
	public static final String NQ_SELECT_ORDEM_DESC = "TermoDeUso.findOrdemDesc";
	public static final String NQ_SELECT_BY_DATA_MAXIMA = "TermoDeUso.NQ_SELECT_BY_DATA_MAXIMA";

	@Id
	@Column(name = "NU_VERSAO_TERMO_ADESAO")
	private Long id;

	@Column(name = "DE_CONTEUDO_TERMO_ADESAO")
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private String conteudo;

	@Column(name = "TS_INICIO_VIGENCIA_TERMO")
	@Temporal(TemporalType.TIMESTAMP)
	@Strategy(DataValueHandler.STRATEGY_NAME)
	private Data dataInicioVigencia;

	@Column(name = "TS_CRIACAO_TERMO_ADESAO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataCriacao;

	@Column(name = "TS_ALTERACAO_TERMO_ADESAO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataUltimaAlteracao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Data getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(Data dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public Data getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Data dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Data getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Data dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

}
