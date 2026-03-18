package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.util.Data;

@Entity
@Table(name = "LCETB038_CARRINHO_FAVORITO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = CarrinhoFavorito.NQ_SELECT_BY_APOSTADOR_NOMECARRINHO, query = "Select af From CarrinhoFavorito af Where af.apostador.id=?1 and af.nome=?2"),
		@NamedQuery(name = CarrinhoFavorito.NQ_SELECT_BY_APOSTADOR, query = "Select af From CarrinhoFavorito af Where af.apostador.id=?1 order by af.nome, af.id"),
		@NamedQuery(name = CarrinhoFavorito.NQ_DELETE_BY_APOSTADOR_ID, query = "Delete From CarrinhoFavorito af Where af.apostador.id=?1 and af.id = ?2")
})
public class CarrinhoFavorito extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_APOSTADOR_NOMECARRINHO = "CarrinhoFavorito.NQ_SELECT_BY_APOSTADOR_NOMECARRINHO";
	public static final String NQ_SELECT_BY_APOSTADOR = "CarrinhoFavorito.NQ_SELECT_BY_APOSTADOR";
	public static final String NQ_DELETE_BY_APOSTADOR_ID = "CarrinhoFavorito.NQ_DELETE_BY_APOSTADOR_ID";

	private static final long serialVersionUID = 1L;


	@Id
	@Column(name = "NU_CARRINHO_FVRTO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NU_APOSTADOR", referencedColumnName = "NU_APOSTADOR")
	private Apostador apostador;

	@Column(name = "NO_CARRINHO_FVRTO")
	private String nome;

	@Column(name = "TS_INCSO_CARRINHO_FVRTO_CRNHO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data timestampAdicicaoCarrinho;
	
	@Transient
	private boolean adicionadoRecentemente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Apostador getApostador() {
		return apostador;
	}

	public void setApostador(Apostador apostador) {
		this.apostador = apostador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Data getTimestampAdicicaoCarrinho() {
		return timestampAdicicaoCarrinho;
	}

	public void setTimestampAdicicaoCarrinho(Data timestampAdicicaoCarrinho) {
		this.timestampAdicicaoCarrinho = timestampAdicicaoCarrinho;
	}

	public boolean isAdicionadoRecentemente() {
		return adicionadoRecentemente;
	}

	public void setAdicionadoRecentemente(boolean adicionadoRecentemente) {
		this.adicionadoRecentemente = adicionadoRecentemente;
	}

}
