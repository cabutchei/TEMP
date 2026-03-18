package br.gov.caixa.silce.dominio.entidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.silce.dominio.openjpa.ModalidadesJsonValueHandler;
import br.gov.caixa.silce.dominio.openjpa.PalpitesValueHandler;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Validate;

@NamedQueries({
		@NamedQuery(name = Rapidao.NQ_SELECT_BY_APOSTADOR, query = "Select rapidao from Apostador a join a.rapidao rapidao where a.id = ?1")
})
@Entity
@Table(name = "LCETB030_CONFIGURACAO_RAPIDAO", schema = DatabaseConfig.SCHEMA)
public class Rapidao extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_APOSTADOR = "Rapidao.findByApostador";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_CONFIGURACAO_RAPIDAO")
	private Long id;

	@Column(name = "VR_MINIMO_GRUPO_APOSTA")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorMinimo;

	@Column(name = "VR_MAXIMO_GRUPO_APOSTA")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorMaximo;

	@Column(name = "VR_MINIMO_PREMIO_PRINCIPAL")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorMinimoPremioPrincipal;

	@Column(name = "DE_PROGNOSTICO_OBRIGATORIO")
	@Strategy(PalpitesValueHandler.STRATEGY_NAME)
	private PalpitesSequenciais prognosticosObrigatorios = new PalpitesSequenciais();

	@Column(name = "DE_PROGNOSTICO_PROIBIDO")
	@Strategy(PalpitesValueHandler.STRATEGY_NAME)
	private PalpitesSequenciais prognosticosProibidos = new PalpitesSequenciais();

	@Column(name = "DE_MODALIDADE_JOGO")
	@Strategy(ModalidadesJsonValueHandler.STRATEGY_NAME)
	@Basic(fetch = FetchType.EAGER)
	// Tem que ser um ArrayList mesmo senao o Strategy nao é utilizado
	private ArrayList<Modalidade> modalidades;
	
	@Transient
	private List<AbstractApostaFavorita> apostasFavoritas;

	@Transient
	private Boolean usarMetaValorMinimo = false;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public Decimal getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(Decimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public Decimal getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(Decimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public Decimal getValorMinimoPremioPrincipal() {
		return valorMinimoPremioPrincipal;
	}

	public void setValorMinimoPremioPrincipal(Decimal valorMinimoPremioPrincipal) {
		this.valorMinimoPremioPrincipal = valorMinimoPremioPrincipal;
	}

	public PalpitesSequenciais getPrognosticosObrigatorios() {
		return prognosticosObrigatorios;
	}

	public void setPrognosticosObrigatorios(PalpitesSequenciais prognosticosObrigatorios) {
		this.prognosticosObrigatorios = prognosticosObrigatorios;
	}

	public PalpitesSequenciais getPrognosticosProibidos() {
		return prognosticosProibidos;
	}

	public void setPrognosticosProibidos(PalpitesSequenciais prognosticosProibidos) {
		this.prognosticosProibidos = prognosticosProibidos;
	}

	public ArrayList<Modalidade> getModalidades() {
		return modalidades;
	}

	public void setModalidades(ArrayList<Modalidade> modalidades) {
		this.modalidades = modalidades;
	}

	public List<AbstractApostaFavorita> getApostasFavoritas() {
		return apostasFavoritas;
	}

	public void setApostasFavoritas(List<AbstractApostaFavorita> apostasFavoritas) {
		this.apostasFavoritas = apostasFavoritas;
	}

	public Boolean getUsarMetaValorMinimo() {
		return usarMetaValorMinimo;
	}

	public void setUsarMetaValorMinimo(Boolean usarMetaValorMinimo) {
		this.usarMetaValorMinimo = usarMetaValorMinimo;
	}

	public static final Rapidao getComValoresDefault(Decimal valorMaximoDefault, Decimal valorMinimoDefault) {
		Rapidao rapidao = new Rapidao();
		
		rapidao.setUsarMetaValorMinimo(Boolean.TRUE);

		rapidao.setApostasFavoritas(Collections.<AbstractApostaFavorita> emptyList());
		rapidao.setPrognosticosObrigatorios(new PalpitesSequenciais(Collections.<Integer> emptyList()));
		rapidao.setPrognosticosProibidos(new PalpitesSequenciais(Collections.<Integer> emptyList()));
		rapidao.setValorMaximo(valorMaximoDefault);
		rapidao.setValorMinimo(valorMinimoDefault);
		rapidao.setValorMinimoPremioPrincipal(Decimal.ZERO);
		
		List<Modalidade> modalidadesNaoEsportivas = Modalidade.getModalidadesNaoEsportivas();
		ArrayList<Modalidade> modalidades2 = new ArrayList<Modalidade>(modalidadesNaoEsportivas);
		rapidao.setModalidades(modalidades2);

		return rapidao;
	}

	public static final Rapidao completeComValoresDefault(Rapidao rapidao, Decimal valorMaximoDefault, Decimal valorMinimoDefault) {
		Validate.notNull(rapidao, "rapidao");

		rapidao.setUsarMetaValorMinimo(Boolean.FALSE);

		if (rapidao.getApostasFavoritas() == null) {
			rapidao.setApostasFavoritas(Collections.<AbstractApostaFavorita> emptyList());
		}
		if (rapidao.getPrognosticosProibidos() == null) {
			rapidao.setPrognosticosProibidos(new PalpitesSequenciais(Collections.<Integer> emptyList()));
		}
		if (rapidao.getPrognosticosObrigatorios() == null) {
			rapidao.setPrognosticosObrigatorios(new PalpitesSequenciais(Collections.<Integer> emptyList()));
		}
		if (rapidao.getValorMaximo() == null) {
			rapidao.setUsarMetaValorMinimo(Boolean.TRUE);
			rapidao.setValorMaximo(valorMaximoDefault);
		}
		if (rapidao.getValorMinimo() == null) {
			rapidao.setValorMinimo(valorMinimoDefault);
		}
		if (rapidao.getValorMinimoPremioPrincipal() == null) {
			rapidao.setValorMinimoPremioPrincipal(Decimal.ZERO);
		}
		if (rapidao.getModalidades() == null) {
			List<Modalidade> modalidadesNaoEsportivas = Modalidade.getModalidadesNaoEsportivas();
			ArrayList<Modalidade> modalidades2 = new ArrayList<Modalidade>(modalidadesNaoEsportivas);
			rapidao.setModalidades(modalidades2);
		}

		return rapidao;
	}

}
