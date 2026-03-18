package br.gov.caixa.silce.dominio.entidade;

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
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.util.Decimal;

/**
 * @author c127237 - Rinaldo Pitzer Junior
 */
@Entity
@Table(name = "LCETB017_FAIXA_PREMIADA", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = FaixaPremiada.NQ_SELECT_BY_APOSTADOR_CONCURSO_PREMIADO, query = "Select fp from FaixaPremiada fp join fetch fp.concursoPremiado where fp.concursoPremiado.id = ?1"
				+ " and (Select ac.aposta.compra.apostador.id from ApostaComprada ac where ac.premio.id = fp.concursoPremiado.premio.id) = ?2")
})
@NamedNativeQueries({
		@NamedNativeQuery(name = FaixaPremiada.NQ_UPDATE_MES_PARTICAO, query = "UPDATE LCE.LCETB017_FAIXA_PREMIADA faixa SET faixa.NU_MES = (select concurso.NU_MES FROM LCE.LCETB016_CONCURSO_PREMIADO concurso"
				+ " where faixa.NU_CONCURSO_PREMIADO = concurso.NU_CONCURSO_PREMIADO),"
				+ " faixa.NU_PARTICAO = (select concurso.NU_PARTICAO FROM LCE.LCETB016_CONCURSO_PREMIADO concurso"
				+ " where faixa.NU_CONCURSO_PREMIADO = concurso.NU_CONCURSO_PREMIADO)"),
})
public class FaixaPremiada extends AbstractEntidade<Long> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_UPDATE_MES_PARTICAO = "FaixaPremiada.NQ_UPDATE_MES_PARTICAO";
	public static final String NQ_SELECT_BY_APOSTADOR_CONCURSO_PREMIADO = "FaixaPremiada.NQ_SELECT_BY_APOSTADOR_CONCURSO_PREMIADO";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_FAIXA_PREMIADA")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_CONCURSO_PREMIADO", referencedColumnName = "NU_CONCURSO_PREMIADO")
	private ConcursoPremiado concursoPremiado;

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

	@Transient
	private Integer quantidadeAcertos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ConcursoPremiado getConcursoPremiado() {
		return concursoPremiado;
	}

	public void setConcursoPremiado(ConcursoPremiado concursoPremiado) {
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

	public Decimal getValorLiquidoCota() {
		return valorLiquidoCota;
	}

	public void setValorLiquidoCota(Decimal valorLiquidoCota) {
		this.valorLiquidoCota = valorLiquidoCota;
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

	public Integer getQuantidadeAcertos() {
		return quantidadeAcertos;
	}

	public void setQuantidadeAcertos(Integer quantidadeAcertos) {
		this.quantidadeAcertos = quantidadeAcertos;
	}
}
