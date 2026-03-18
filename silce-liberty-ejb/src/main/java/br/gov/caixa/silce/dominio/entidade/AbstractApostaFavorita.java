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

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;

/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 */
@Entity
@Table(name = "LCETB010_APOSTA_FAVORITA", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name = AbstractApostaFavorita.NQ_RECUPERE_BY_RAPIDAO,
			query = "SELECT af FROM AbstractApostaFavorita af WHERE af.rapidao.id = ?1"),
	@NamedQuery(name = AbstractApostaFavorita.NQ_UPDATE_ATRIBUI_RAPIDAO,
			query = "UPDATE AbstractApostaFavorita af SET af.rapidao = ?1 WHERE af.id in (?2) and af.apostador.id = ?3"),
	@NamedQuery(name = AbstractApostaFavorita.NQ_UPDATE_REMOVE_RAPIDAO,
			query = "UPDATE AbstractApostaFavorita af SET af.rapidao = null WHERE af.rapidao is not null and af.apostador.id = ?1"),
	@NamedQuery(name = AbstractApostaFavorita.NQ_RECUPERE_APOSTA_FAVORITA,
			query = "Select entidade From AbstractApostaFavorita entidade Where entidade.apostador.id=?1 and entidade.modalidade =?2"),
	@NamedQuery(name = AbstractApostaFavorita.NQ_RECUPERE_APOSTA_FAVORITA_BY_APOSTADOR_ORDER_BY_NOME,
			query = "Select entidade From AbstractApostaFavorita entidade Where entidade.apostador.id=?1 order by entidade.nome"),
	@NamedQuery(name = AbstractApostaFavorita.NQ_RECUPERE_APOSTA_NOME_E_MODALIDADE,
			query = "Select aposta From AbstractApostaFavorita aposta Where aposta.apostador.id=?1 and aposta.nome = ?2 and aposta.modalidade =?3"),
	@NamedQuery(name = AbstractApostaFavorita.NQ_RECUPERE_BY_ID_AND_APOSTADOR,
			query = "Select aposta From AbstractApostaFavorita aposta Where aposta.id = ?1 and aposta.apostador.id=?2") })

@DiscriminatorColumn(name = "NU_MODALIDADE_JOGO", discriminatorType = DiscriminatorType.INTEGER)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractApostaFavorita extends AbstractEntidade<Long> {

	public static final String NQ_RECUPERE_APOSTA_FAVORITA_BY_APOSTADOR_ORDER_BY_NOME = "AbstractApostaFavorita.NQ_RECUPERE_APOSTA_FAVORITA_BY_APOSTADOR_ORDER_BY_NOME";

	public static final String NQ_RECUPERE_BY_RAPIDAO = "AbstractApostaFavorita.recupereByRapidao";

	public static final String NQ_UPDATE_ATRIBUI_RAPIDAO = "AbstractApostaFavorita.updateAtribuiRapidao";
	
	public static final String NQ_UPDATE_REMOVE_RAPIDAO = "AbstractApostaFavorita.updateRemoveRapidao";
	
	public static final String NQ_RECUPERE_APOSTA_FAVORITA = "AbstractApostaFavorita.recupereApostaFavorita";

	public static final String NQ_RECUPERE_APOSTA_NOME_E_MODALIDADE = "AbstractApostaFavorita.recupereApostaNomeEModalidade";

	public static final String NQ_RECUPERE_BY_ID_AND_APOSTADOR = "AbstractApostaFavorita.NQ_RECUPERE_BY_ID_AND_APOSTADOR";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_APOSTA_FAVORITA")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NU_APOSTADOR")
	private Apostador apostador;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "NU_CONFIGURACAO_RAPIDAO")
	private Rapidao rapidao;

	@Column(name = "NU_MODALIDADE_JOGO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Modalidade modalidade;

	@Column(name = "NO_APOSTA_FAVORITA")
	private String nome;

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

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ApostaFavoritaVO getVO() {
		ApostaFavoritaVO apostaFavoritaVO = new ApostaFavoritaVO();
		apostaFavoritaVO.setId(id);

		Apostador apostadorReferente = getApostador();
		if (apostadorReferente != null) {
			apostaFavoritaVO.setApostador(apostadorReferente.getId());
		}
		apostaFavoritaVO.setModalidade(modalidade);
		apostaFavoritaVO.setNome(nome);
		return apostaFavoritaVO;
	}

	public Rapidao getRapidao() {
		return rapidao;
	}

	public void setRapidao(Rapidao rapidao) {
		this.rapidao = rapidao;
	}
}
