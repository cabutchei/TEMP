package br.gov.caixa.silce.dominio.entidade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.broker.SituacaoPremioSISPL;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.util.Decimal;

/**
 * @author c127237 - Rinaldo Pitzer Junior
 */
@Entity
@Table(name = "LCETB916_HSTRO_CCRSO_PREMIADO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name = HistoricoConcursoPremiado.NQ_SELECT_BY_APOSTADOR_PREMIO, 
			query = "Select cp from HistoricoConcursoPremiado cp join fetch cp.premio where cp.premio.id = ?1 and (Select ac.aposta.compra.apostador.id from HistoricoApostaComprada ac where ac.premio.id = cp.premio.id) = ?2")
	})
public class HistoricoConcursoPremiado extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_SELECT_BY_APOSTADOR_PREMIO = "HistoricoConcursoPremiado.NQ_SELECT_BY_APOSTADOR_PREMIO";

	@Id
	@Column(name = "NU_CONCURSO_PREMIADO")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_PREMIO", referencedColumnName = "NU_PREMIO")
	private HistoricoPremio premio;

	@Column(name = "NU_CONCURSO")
	private Integer concurso;

	@Column(name = "VR_PREMIO_BRUTO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorPremioBruto;

	@Column(name = "VR_PREMIO_LIQUIDO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorPremioLiquido;

	@Column(name = "VR_IRRF")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorIRRF;

	@Column(name = "NU_PARTICAO")
	private Long particao;

	@Column(name = "NU_MES")
	private Long mes;

	@Column(name = "NU_ANO")
	private Long ano;

	@Transient
	private SituacaoPremioSISPL situacao;

	@Transient
	private List<HistoricoFaixaPremiada> faixasPremiadas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HistoricoPremio getPremio() {
		return premio;
	}

	public void setPremio(HistoricoPremio premio) {
		this.premio = premio;
		if (premio != null) {
			setMes(premio.getMes());
			setParticao(premio.getParticao());
		}
	}

	public Integer getConcurso() {
		return concurso;
	}

	public void setConcurso(Integer concurso) {
		this.concurso = concurso;
	}

	public Decimal getValorPremioBruto() {
		return valorPremioBruto;
	}

	public void setValorPremioBruto(Decimal valorPremioBruto) {
		this.valorPremioBruto = valorPremioBruto;
	}

	public Decimal getValorPremioLiquido() {
		return valorPremioLiquido;
	}

	public void setValorPremioLiquido(Decimal valorPremioLiquido) {
		this.valorPremioLiquido = valorPremioLiquido;
	}

	public Decimal getValorIRRF() {
		return valorIRRF;
	}

	public void setValorIRRF(Decimal valorIRRF) {
		this.valorIRRF = valorIRRF;
	}

	public List<HistoricoFaixaPremiada> getFaixasPremiadas() {
		if (faixasPremiadas == null) {
			faixasPremiadas = new ArrayList<HistoricoFaixaPremiada>();
		}
		return faixasPremiadas;
	}

	public SituacaoPremioSISPL getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoPremioSISPL situacao) {
		this.situacao = situacao;
	}

	public boolean isAPagar() {
		return SituacaoPremioSISPL.A_PAGAR.equals(getSituacao());
	}

	public Long getMes() {
		return mes;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	public Long getParticao() {
		return particao;
	}

	public void setParticao(Long particao) {
		this.particao = particao;
	}

	/**
	 * @return the ano
	 */
	public Long getAno() {
		return ano;
	}

	/**
	 * @param ano
	 *            the ano to set
	 */
	public void setAno(Long ano) {
		this.ano = ano;
	}

}
