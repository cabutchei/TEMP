package br.gov.caixa.silce.dominio.entidade;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "LCETB016_CONCURSO_PREMIADO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name = ConcursoPremiado.NQ_SELECT_BY_APOSTADOR_PREMIO, 
			query = "Select cp from ConcursoPremiado cp join fetch cp.premio where cp.premio.id = ?1"
				+ " and (Select ac.aposta.compra.apostador.id from ApostaComprada ac where ac.premio.id = cp.premio.id) = ?2"),
	@NamedQuery(name = ConcursoPremiado.NQ_SELECT_BY_PREMIO, 
			query = "Select cp from ConcursoPremiado cp where cp.premio.id = ?1")
	})
	
@NamedNativeQueries({
		@NamedNativeQuery(name = ConcursoPremiado.NQ_UPDATE_MES_PARTICAO, query = "UPDATE LCE.LCETB016_CONCURSO_PREMIADO concurso SET"
			+ " concurso.NU_MES = (select premio.NU_MES FROM LCE.LCETB015_PREMIO premio where premio.NU_PREMIO = concurso.NU_PREMIO),"
			+ " concurso.NU_PARTICAO = (select premio.NU_PARTICAO FROM LCE.LCETB015_PREMIO premio where premio.NU_PREMIO = concurso.NU_PREMIO)"),
})
public class ConcursoPremiado extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_UPDATE_MES_PARTICAO = "ConcursoPremiado.NQ_UPDATE_MES_PARTICAO";
	public static final String NQ_SELECT_BY_APOSTADOR_PREMIO = "ConcursoPremiado.NQ_SELECT_BY_APOSTADOR_PREMIO";
	public static final String NQ_SELECT_BY_PREMIO = "ConcursoPremiado.NQ_SELECT_BY_PREMIO";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_CONCURSO_PREMIADO")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_PREMIO", referencedColumnName = "NU_PREMIO")
	private Premio premio;

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

	@Transient
	private SituacaoPremioSISPL situacao;

	@Transient
	private List<FaixaPremiada> faixasPremiadas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Premio getPremio() {
		return premio;
	}

	public void setPremio(Premio premio) {
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

	public List<FaixaPremiada> getFaixasPremiadas() {
		if (faixasPremiadas == null) {
			faixasPremiadas = new ArrayList<FaixaPremiada>();
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

}
