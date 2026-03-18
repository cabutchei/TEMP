package br.gov.caixa.silce.dominio.entidade;

import java.text.MessageFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.util.StringUtil;

/**
 * Entidade que representa uma Unidade lotérica.
 */
@Entity
@Table(name = "LCEVW004_LOTERICA", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = Loterica.NQ_SELECT_BY_POLO_ID_DV, query = "Select l from Loterica l where l.polo=?1 and l.id=?2  and l.dv=?3")
})
public class Loterica extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_POLO_ID_DV = "Loterica.findByCodigo";

	public static final int TAMANHO_CODIGO_COMPLETO_SEM_MASCARA = 9;
	public static final String EXEMPLO_CODIGO_COMPLETO = "99.999999-9"; 

	private static final int INICIO_POLO = 0;
	private static final int FIM_POLO = 2;
	private static final int INICIO_ID = 2;
	private static final int FIM_ID = 8;
	private static final int INICIO_DV = 8;
	private static final int FIM_DV = 9;

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_CD", insertable = false, updatable = false)
	private Long id;

	@Column(name = "NO_FANTASIA", insertable = false, updatable = false)
	private String nomeFantasia;

	@Column(name = "NO_RAZAO_SOCIAL", insertable = false, updatable = false)
	private String nome;

	@Column(name = "NO_LOGRADOURO", insertable = false, updatable = false)
	private String logradouro;

	@Column(name = "NU_UF_IBGE_L98", insertable = false, updatable = false)
	private Long idUF;

	@Column(name = "NU_MNCPO_IBGE_L98", insertable = false, updatable = false)
	private Long idMunicipio;

	@Column(name = "NU_DV_IBGE_L98", insertable = false, updatable = false)
	private Long dvMunicipio;

	@Column(name = "NO_BAIRRO", insertable = false, updatable = false)
	private String bairro;

	@Column(name = "NO_MUNICIPIO", insertable = false, updatable = false)
	private String nomeMunicipio;

	@Column(name = "NO_UF", insertable = false, updatable = false)
	private String nomeUF;

	@Column(name = "NU_POLO_CD", insertable = false, updatable = false)
	private Long polo;

	@Column(name = "NU_DV_CD", insertable = false, updatable = false)
	private Long dv;

	@Column(name = "NU_CEP", insertable = false, updatable = false)
	private String cep;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_CATEGORIA_005")
	@EagerFetchMode(FetchMode.PARALLEL)
	private CategoriaLoterica categoria;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_SITUACAO_C04", insertable = false, updatable = false)
	@EagerFetchMode(FetchMode.PARALLEL)
	private SituacaoLoterica situacao;

	@Transient
	private String numeroFormatado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String noFantasia) {
		this.nomeFantasia = noFantasia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public SituacaoLoterica getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoLoterica situacao) {
		this.situacao = situacao;
	}

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Long getIdUF() {
		return idUF;
	}

	public void setIdUF(Long idUF) {
		this.idUF = idUF;
	}

	public Long getDvMunicipio() {
		return dvMunicipio;
	}

	public void setDvMunicipio(Long dvMunicipio) {
		this.dvMunicipio = dvMunicipio;
	}

	public String getNomeUF() {
		return nomeUF;
	}

	public void setNomeUF(String nomeUF) {
		this.nomeUF = nomeUF;
	}

	public String getNomeMunicipio() {
		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio) {
		this.nomeMunicipio = nomeMunicipio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Loterica)) {
			return false;
		}
		Loterica other = (Loterica) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Long getPolo() {
		return polo;
	}

	public void setPolo(Long polo) {
		this.polo = polo;
	}

	public Long getDv() {
		return dv;
	}

	public void setDv(Long dv) {
		this.dv = dv;
	}

	@PostLoad
	protected void repairNomeFantasia() {
		nomeFantasia = nomeFantasia.trim();
	}

	public String getNumeroFormatado() {
		if (numeroFormatado == null) {
			// subtrai o início e o fim de cada campo para calcular o tamanho total
			numeroFormatado = StringUtil.completeAEsquerda(getPolo().toString(), FIM_POLO - INICIO_POLO, '0') + "."
					+ StringUtil.completeAEsquerda(getId().toString(), FIM_ID - INICIO_ID, '0') + "-"
					+ StringUtil.completeAEsquerda(getDv().toString(), FIM_DV - INICIO_DV, '0');
		}
		return numeroFormatado;
	}

	/**
	 * @param codigoCompleto código completo da Lotérica, composto de Polo, ID e DV, no formato
	 *        {@value #EXEMPLO_CODIGO_COMPLETO}, com ou sem máscara.
	 * @return o split do código completo da lotérica, na ordem: polo, id e dv
	 * @throws IllegalArgumentException caso o código não tenha a quantidade de dígitos necessários
	 */
	public static Long[] splitCodigoCompleto(String codigoCompleto) {
		try {
			String codigoSemMascara = StringUtil.removeNaoDigitos(codigoCompleto);
			Long polo = Long.valueOf(codigoSemMascara.substring(INICIO_POLO, FIM_POLO));
			Long id = Long.valueOf(codigoSemMascara.substring(INICIO_ID, FIM_ID));
			Long dv = Long.valueOf(codigoSemMascara.substring(INICIO_DV, FIM_DV));
			return new Long[] {polo, id, dv};
		} catch (IndexOutOfBoundsException e) {
			String msg = "Parâmetro codigoCompleto em formato incorreto. Deve seguir o padrão: '{0}' com ou sem máscara. Erro ocorrido: {1}";
			String msgFormatada = MessageFormat.format(msg, EXEMPLO_CODIGO_COMPLETO, e.getMessage());
			throw new IllegalArgumentException(msgFormatada, e);
		}
	}
	
	/**
	 * @param codigoCompleto código completo da Lotérica, composto de Polo, ID e DV, no formato
	 *        {@value #EXEMPLO_CODIGO_COMPLETO}, com ou sem máscara.
	 * @return false caso o código não possua somente números ou tiver um tamanho inválido, true caso contrário
	 */
	public static boolean valideCodigoCompleto(String codigoCompleto) {
		String codigoAlfanumerico = StringUtil.removeNaoAlfanumerico(codigoCompleto);
		return StringUtil.hasSomenteNumeros(codigoAlfanumerico) &&  StringUtil.hasTamanhoValido(codigoAlfanumerico, TAMANHO_CODIGO_COMPLETO_SEM_MASCARA);
	}


	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep
	 *            the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the categoria
	 */
	public CategoriaLoterica getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria
	 *            the categoria to set
	 */
	public void setCategoria(CategoriaLoterica categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		StringBuilder builder = createToStringBuilder();
		builder.append("Loterica [id=").append(id).append(", nomeFantasia=").append(nomeFantasia).append(", nome=").append(nome)
			.append(", logradouro=").append(logradouro).append(", idUF=").append(idUF).append(", idMunicipio=").append(idMunicipio)
			.append(", dvMunicipio=").append(dvMunicipio).append(", bairro=").append(bairro).append(", nomeMunicipio=")
			.append(nomeMunicipio).append(", nomeUF=").append(nomeUF).append(", situacao=").append(situacao).append(", polo=")
			.append(polo).append(", dv=").append(dv).append(", numeroFormatado=").append(numeroFormatado).append(']');
		return builder.toString();
	}

}
