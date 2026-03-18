package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

@Entity
@Table(name = "LCEMTV45_ULTMO_RSLTO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(
			name = ResultadoConcurso.NQ_SELECT_BY_MODALIDADE_NUCONCURSO,
			query = "SELECT res FROM ResultadoConcurso res WHERE res.id.modalidade = ?1 AND res.id.concurso = ?2"),
		@NamedQuery(
			name = ResultadoConcurso.NQ_SELECT_BY_MODALIDADE,
			query = "SELECT res FROM ResultadoConcurso res WHERE res.id.modalidade = ?1 ORDER BY res.id.concurso DESC")
})
public class ResultadoConcurso extends AbstractEntidade<ConcursoPK> {
	private static final long serialVersionUID = 1L;
	public static final String NQ_SELECT_BY_MODALIDADE_NUCONCURSO = "ResultadoConcurso.NQ_SELECT_BY_MODALIDADE_NUCONCURSO";
	public static final String NQ_SELECT_BY_MODALIDADE = "ResultadoConcurso.NQ_SELECT_BY_MODALIDADE";

	@EmbeddedId
	private ConcursoPK id;

	@Column(name = "NU_SQNCL_SRTO")
	private Integer seqSorteio;

	@Column(name = "DT_APRCO_CCRSO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataApuracao;

	@Column(name = "NO_MNCPO_SRTO_CCRSO")
	private String municipioSorteio;

	@Column(name = "IC_CCRSO_ESPCL")
	private String icConcurso;

	@Column(name = "NU_DZNA_SORTEADA")
	private String numerosSorteadosSorteio;

	@Column(name = "NO_TIME_CORACAO_MES_SORTE")
	private String timeCoracaoOuMesSorte;

	@Column(name = "NU_FXA_QNTDE_VLR")
	private String faixas;

	@Column(name = "NO_MNCPO_UF_GNHDR")
	private String municipiosSorteados;

	@Column(name = "VR_ARCDO_CCRSO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal arrecadacaoTotal;

	@Column(name = "VR_ESTMO_PRXMO_CCRSO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal acumuladoParaProximoConcurso;

	@Column(name = "VR_ACMDO_PRXMO_CCRSO_ESPCL")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal acumuladoParaConcursoEspecial;

	@Column(name = "VR_ACMDO_CCRSO_FNL_0_5")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal acumuladoParaProximoFinalZeroOuCinco;

	@Column(name = "SG_UF_TIME_CORACAO")
	private String ufTimeCoracao;

	public Modalidade getModalidade() {
		if (this.id == null) {
			this.id = new ConcursoPK();
		}
		return this.id.getModalidade();
	}

	public void setModalidade(Modalidade modalidade) {
		if (this.id == null) {
			this.id = new ConcursoPK();
		}
		this.id.setModalidade(modalidade);
	}
	public Integer getConcurso() {
		if (this.id == null) {
			this.id = new ConcursoPK();
		}
		return this.id.getConcurso();
	}
	public void setConcurso(Integer concurso) {
		if (this.id == null) {
			this.id = new ConcursoPK();
		}
		this.id.setConcurso(concurso);
	}

	public Integer getSeqSorteio() {
		return seqSorteio;
	}

	public void setSeqSorteio(Integer seqSorteio) {
		this.seqSorteio = seqSorteio;
	}

	public Data getDataApuracao() {
		return dataApuracao;
	}

	public void setDataApuracao(Data dataApuracao) {
		this.dataApuracao = dataApuracao;
	}

	public String getMunicipioSorteio() {
		return municipioSorteio;
	}

	public void setMunicipioSorteio(String municipioSorteio) {
		this.municipioSorteio = municipioSorteio;
	}

	public String getIcConcurso() {
		return icConcurso;
	}

	public void setIcConcurso(String icConcurso) {
		this.icConcurso = icConcurso;
	}

	public Decimal getArrecadacaoTotal() {
		return arrecadacaoTotal;
	}

	public void setArrecadacaoTotal(Decimal arrecadacaoTotal) {
		this.arrecadacaoTotal = arrecadacaoTotal;
	}

	public Decimal getAcumuladoParaProximoConcurso() {
		return acumuladoParaProximoConcurso;
	}

	public void setAcumuladoParaProximoConcurso(Decimal acumuladoParaProximoConcurso) {
		this.acumuladoParaProximoConcurso = acumuladoParaProximoConcurso;
	}

	public Decimal getAcumuladoParaProximoFinalZeroOuCinco() {
		return acumuladoParaProximoFinalZeroOuCinco;
	}

	public void setAcumuladoParaProximoFinalZeroOuCinco(Decimal acumuladoParaProximoFinalZero_Cinco) {
		this.acumuladoParaProximoFinalZeroOuCinco = acumuladoParaProximoFinalZero_Cinco;
	}

	public Decimal getAcumuladoParaConcursoEspecial() {
		return acumuladoParaConcursoEspecial;
	}

	public void setAcumuladoParaConcursoEspecial(Decimal acumuladoParaConcursoEspecial) {
		this.acumuladoParaConcursoEspecial = acumuladoParaConcursoEspecial;
	}

	public String getTimeCoracaoOuMesSorte() {
		return timeCoracaoOuMesSorte;
	}

	public void setTimeCoracaoOuMesSorte(String tmCoracao_DiSorte) {
		this.timeCoracaoOuMesSorte = tmCoracao_DiSorte;
	}

	public String getNumerosSorteadosSorteio() {
		return numerosSorteadosSorteio;
	}

	public void setNumerosSorteadosSorteio(String numerosSorteadosSorteio) {
		this.numerosSorteadosSorteio = numerosSorteadosSorteio;
	}

	public String getFaixas() {
		return faixas;
	}

	public void setFaixas(String faixas) {
		this.faixas = faixas;
	}

	public String getMunicipiosSorteados() {
		return municipiosSorteados;
	}

	public void setMunicipiosSorteados(String municipiosSorteados) {
		this.municipiosSorteados = municipiosSorteados;
	}

	@Override
	public ConcursoPK getId() {
		return this.id;
	}

	@Override
	public void setId(ConcursoPK id) {
		this.id = id;
	}

	public String getUfTimeCoracao() {
		return ufTimeCoracao;
	}

	public void setUfTimeCoracao(String ufTimeCoracao) {
		this.ufTimeCoracao = ufTimeCoracao;
	}

}
