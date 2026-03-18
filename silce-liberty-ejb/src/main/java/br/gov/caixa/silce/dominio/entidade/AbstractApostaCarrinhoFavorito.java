package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroJogoNumerico;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.jogos.IndicadorSurpresinha;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.Palpites;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.util.Data;

/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 */
@Entity
@Table(name = "LCETB039_APOSTA_CARRINHO_FVRTO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = AbstractApostaCarrinhoFavorito.NQ_SELECT_BY_APOSTADOR_CARRINHO, query = "Select acf From AbstractApostaCarrinhoFavorito acf Where acf.carrinhoFavorito.apostador.id=?1 and acf.carrinhoFavorito.id=?2 ORDER BY acf.dataInclusao DESC"),
		@NamedQuery(name = AbstractApostaCarrinhoFavorito.NQ_DELETE_BY_APOSTADOR_CARRINHO_APOSTA, query = "Delete from AbstractApostaCarrinhoFavorito aposta where aposta.carrinhoFavorito.apostador.id = ?1 and aposta.carrinhoFavorito.id = ?2 and aposta.id = ?3"),
		@NamedQuery(name = AbstractApostaCarrinhoFavorito.NQ_DELETE_BY_APOSTADOR_CARRINHO, query = "Delete from AbstractApostaCarrinhoFavorito aposta where aposta.carrinhoFavorito.apostador.id = ?1 and aposta.carrinhoFavorito.id = ?2"),
})
@DiscriminatorColumn(name = "NU_MODALIDADE_JOGO", discriminatorType = DiscriminatorType.INTEGER)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractApostaCarrinhoFavorito extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_APOSTADOR_CARRINHO = "AbstractApostaCarrinhoFavorito.NQ_SELECT_BY_APOSTADOR_CARRINHO";
	public static final String NQ_DELETE_BY_APOSTADOR_CARRINHO_APOSTA = "AbstractApostaCarrinhoFavorito.NQ_DELETE_BY_APOSTADOR_CARRINHO_APOSTA";
	public static final String NQ_DELETE_BY_APOSTADOR_CARRINHO = "AbstractApostaCarrinhoFavorito.NQ_DELETE_BY_APOSTADOR_CARRINHO";


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_APSTA_CARRINHO_FVRTO")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "NU_CARRINHO_FVRTO")
	private CarrinhoFavorito carrinhoFavorito;

	@Column(name = "NU_MODALIDADE_JOGO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Modalidade modalidade;

	@Column(name = "IC_SURPRESINHA")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private IndicadorSurpresinha indicadorSurpresinha = IndicadorSurpresinha.NAO_SURPRESINHA;

	@Column(name = "TS_INCLUSAO_APOSTA_CARRINHO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataInclusao;

	/**
	 * Se true, gera a espelho ao salvar no carrinho
	 */


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CarrinhoFavorito getCarrinhoFavorito() {
		return carrinhoFavorito;
	}

	public void setCarrinhoFavorito(CarrinhoFavorito carrinhoFavorito) {
		this.carrinhoFavorito = carrinhoFavorito;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public IndicadorSurpresinha getIndicadorSurpresinha() {
		return indicadorSurpresinha;
	}

	public void setIndicadorSurpresinha(IndicadorSurpresinha indicadorSurpresinha) {
		this.indicadorSurpresinha = indicadorSurpresinha;
	}

	public boolean isSurpresinha() {
		return indicadorSurpresinha.isSurpresinha();
	}

	public Data getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Data dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public AbstractApostaVO<?> toAbstractApostaVO(ParametroJogoNumerico parametro) {
		throw new RuntimeException("Método não implementado");
	}

	public abstract Palpites<?> getPalpites();

}
