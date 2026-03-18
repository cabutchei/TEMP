package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler;
import br.gov.caixa.util.StringUtil;

@Entity
@Table(name = "LCEVW009_EQPE_ESPA", schema = DatabaseConfig.SCHEMA)
@NamedNativeQueries({
		@NamedNativeQuery(name = EquipeEsportiva.NQ_SELECT_BY_NOME, query = "Select es.* from LCE.LCEVW009_EQPE_ESPA es where UPPER(TRANSLATE(es.NO_EQUIPE, 'aaaaeeiooouc','ãâáàéêíõôóúç')) like ?1", resultClass = EquipeEsportiva.class)
})
public class EquipeEsportiva extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_NOME = "EquipeEsportiva.NQ_SELECT_BY_NOME";

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "NU_EQUIPE", insertable = false, updatable = false)
	private Long id;
	
	@Column(name = "NO_EQUIPE", insertable = false, updatable = false)
	private String nome;

	@Column(name = "NO_EQUIPE_REDUZIDO", insertable = false, updatable = false)
	private String nomeReduzido;

	@Column(name = "SG_UF", insertable = false, updatable = false)
	private String uf;

	@Column(name = "DE_RAZAO_SOCIAL", insertable = false, updatable = false)
	private String razaoSocial;

	@Column(name = "NU_PAIS", insertable = false, updatable = false)
	private Long pais;

	@Column(name = "IC_SELECAO", insertable = false, updatable = false)
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean selecao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return the nome
	 */
	public String getNomeUf() {
		if (StringUtil.isEmpty(uf)) {
			return nome;
		}
		return nome + "/" + uf;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the nomeReduzido
	 */
	public String getNomeReduzido() {
		return nomeReduzido;
	}

	/**
	 * @param nomeReduzido
	 *            the nomeReduzido to set
	 */
	public void setNomeReduzido(String nomeReduzido) {
		this.nomeReduzido = nomeReduzido;
	}

	/**
	 * @return the uf
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * @param uf
	 *            the uf to set
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * @return the pais
	 */
	public Long getPais() {
		return pais;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public void setPais(Long pais) {
		this.pais = pais;
	}

	/**
	 * @return the selecao
	 */
	public Boolean getSelecao() {
		return selecao;
	}

	/**
	 * @param selecao
	 *            the selecao to set
	 */
	public void setSelecao(Boolean selecao) {
		this.selecao = selecao;
	}

	@Override
	public int hashCode() {
		return (id == null) ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof EquipeEsportiva)) {
			return false;
		}
		EquipeEsportiva other = (EquipeEsportiva) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the razaoSocial
	 */
	public String getRazaoSocial() {
		return razaoSocial;
	}

	/**
	 * @param razaoSocial
	 *            the razaoSocial to set
	 */
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public static EquipeEsportiva gereEquipeGenerica(long idEnviado) {

		EquipeEsportiva equipe = new EquipeEsportiva();
		equipe.setId(idEnviado);
		equipe.setNome("Equipe");
		equipe.setNomeReduzido("Equipe");
		equipe.setPais(null);
		equipe.setSelecao(false);
		equipe.setUf("");

		return equipe;
	}

}
