package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.util.Decimal;

/**
 * @author c127237 - Rinaldo Pitzer Junior
 */
@Entity
@Table(name = "LCETB917_HSTRO_FAIXA_PREMIADA", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = HistoricoFaixaPremiada.NQ_SELECT_BY_APOSTADOR_CONCURSO_PREMIADO,
			query = "Select fp from HistoricoFaixaPremiada fp join fetch fp.concursoPremiado "
				+ "where fp.concursoPremiado.id = ?1 "
				+ "and ("
				+ "Select ac.aposta.compra.apostador.id from HistoricoApostaComprada ac "
				+ "where ac.premio.id = fp.concursoPremiado.premio.id"
				+ ") = ?2"),

		@NamedQuery(name = HistoricoFaixaPremiada.NQ_SELECT_BY_PREMIO,
			query = "select concursoPremiado.concurso, faixasPremiadas.nome, faixasPremiadas.sorteio, faixasPremiadas.valorLiquido "
				+ "From HistoricoFaixaPremiada faixasPremiadas "
				+ "join faixasPremiadas.concursoPremiado concursoPremiado "
				+ "join concursoPremiado.premio premio "
				+ "where premio.id.id = ?1 "
				+ "and premio.id.mes = ?2 "
				+ "and premio.id.ano = ?3 "
				+ "order by concursoPremiado.concurso asc ",
			hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") })
})
public class HistoricoFaixaPremiada extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_SELECT_BY_APOSTADOR_CONCURSO_PREMIADO = "HistoricoFaixaPremiada.NQ_SELECT_BY_APOSTADOR_CONCURSO_PREMIADO";
	public static final String NQ_SELECT_BY_PREMIO = "Premio.NQ_SELECT_BY_PREMIO";

	@Id
	@Column(name = "NU_FAIXA_PREMIADA")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_CONCURSO_PREMIADO", referencedColumnName = "NU_CONCURSO_PREMIADO")
	private HistoricoConcursoPremiado concursoPremiado;

	@Column(name = "NU_FAIXA")
	private Integer numero;

	@Column(name = "NU_SORTEIO")
	private Integer sorteio;
	
	@Column(name = "NO_FAIXA")
	private String nome;

	@Column(name = "VR_LIQUIDO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorLiquido;

	@Column(name = "VR_LIQUIDO_COTA")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorLiquidoCota;

	@Column(name = "NU_PARTICAO")
	private Long particao;

	@Column(name = "NU_MES")
	private Long mes;

	@Column(name = "NU_ANO")
	private Long ano;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HistoricoConcursoPremiado getConcursoPremiado() {
		return concursoPremiado;
	}

	public void setConcursoPremiado(HistoricoConcursoPremiado concursoPremiado) {
		this.concursoPremiado = concursoPremiado;
		if (concursoPremiado != null) {
			setMes(concursoPremiado.getMes());
			setParticao(concursoPremiado.getParticao());
		}
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Decimal getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Decimal valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Integer getSorteio() {
		return sorteio;
	}

	public void setSorteio(Integer sorteio) {
		this.sorteio = sorteio;
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

	public Decimal getValorLiquidoCota() {
		return valorLiquidoCota;
	}

	public void setValorLiquidoCota(Decimal valorLiquidoCota) {
		this.valorLiquidoCota = valorLiquidoCota;
	}

}
