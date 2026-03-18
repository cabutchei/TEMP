package br.gov.caixa.silce.dominio.entidade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.openjpa.ModalidadesJsonValueHandler;

@Entity
@Table(name = "LCETB032_ESCUDO_EQUIPE", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = EscudoEquipeEsportiva.NQ_SELECT_BY_EQUIPE, query = "Select escudo From EscudoEquipeEsportiva escudo join fetch escudo.equipe where escudo.equipe.id = ?1"),
		@NamedQuery(name = EscudoEquipeEsportiva.NQ_SELECT_ESCUDO_PADRAO, query = "Select escudo From EscudoEquipeEsportiva escudo where escudo.equipe is null"),
		@NamedQuery(name = EscudoEquipeEsportiva.NQ_SELECT_LISTA_EQUIPE, query = "Select escudo From EscudoEquipeEsportiva escudo join fetch escudo.equipe")
})
public class EscudoEquipeEsportiva extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_EQUIPE = "EscudoEquipeEsportiva.NQ_SELECT_BY_EQUIPE";
	public static final String NQ_SELECT_ESCUDO_PADRAO = "EscudoEquipeEsportiva.NQ_SELECT_ESCUDO_PADRAO";
	public static final String NQ_SELECT_LISTA_EQUIPE = "EscudoEquipeEsportiva.NQ_SELECT_LISTA_EQUIPE";

	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_ESCUDO_EQUIPE")
	private Long id;

	@Column(name = "DE_PARAMETRO_ESCUDO")
	@Strategy(ModalidadesJsonValueHandler.STRATEGY_NAME)
	@Basic(fetch = FetchType.EAGER)
	// Tem que ser um ArrayList mesmo senao o Strategy nao é utilizado
	private ArrayList<Modalidade> modalidades;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_EQUIPE_ESPORTIVA", referencedColumnName = "NU_EQUIPE")
	private EquipeEsportiva equipe;
	
	@Column(name = "IM_ESCUDO_EQUIPE")
	@Lob
	private byte[] imagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the equipe
	 */
	public EquipeEsportiva getEquipe() {
		return equipe;
	}

	public void setEquipe(EquipeEsportiva equipe) {
		this.equipe = equipe;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public List<Modalidade> getModalidades() {
		return modalidades;
	}

	public void setModalidades(List<Modalidade> modalidades) {
		if (modalidades == null) {
			this.modalidades = null;
		} else {
			this.modalidades = new ArrayList<Modalidade>(modalidades);
		}
	}

	public Boolean modalidadeHabilitada(Modalidade modalidade) {
		return modalidades.contains(modalidade);
	}

}
