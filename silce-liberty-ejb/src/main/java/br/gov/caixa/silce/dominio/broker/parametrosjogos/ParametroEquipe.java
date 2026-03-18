package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.io.Serializable;
import java.text.Normalizer;

import br.gov.caixa.util.StringUtil;

/**
 * parâmetro que representa uma Equipe utilizado por algumas modalidades de
 * jogos.
 * 
 */
public class ParametroEquipe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boolean indicadorSelecao;

	private String nome;

	private Integer numero;

	private Integer numeroPais;

	private String descricaoCurta;

	private String descricaoLonga;

	private String pais;

	private String siglaPais;

	private String uf;
	
	private String nomeClass;

	private String toStringCache;

	public Boolean getIndicadorSelecao() {
		return indicadorSelecao;
	}

	public void setIndicadorSelecao(Boolean indicadorSelecao) {
		this.indicadorSelecao = indicadorSelecao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
		toStringCache = null;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getNumeroPais() {
		return numeroPais;
	}

	public void setNumeroPais(Integer numeroPais) {
		this.numeroPais = numeroPais;
	}

	public String getDescricaoCurta() {
		return descricaoCurta;
	}

	public void setDescricaoCurta(String descricaoCurta) {
		this.descricaoCurta = descricaoCurta;
	}

	public String getDescricaoLonga() {
		return descricaoLonga;
	}

	public void setDescricaoLonga(String descricaoLonga) {
		this.descricaoLonga = descricaoLonga;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getSiglaPais() {
		return siglaPais;
	}

	public void setSiglaPais(String siglaPais) {
		this.siglaPais = siglaPais;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
		toStringCache = null;
	}

	@Override
	public String toString() {
		if (StringUtil.isEmpty(toStringCache)) {
			
			/* Concatena o nome do time juntamente com a Uf somente se o indicadorSelecao for false, 
			 * ou seja, se não for time de selecao (pais). Se o time for um pais, apenas retorna o nome.
			 */
			toStringCache = getNome();
			
			if (indicadorSelecao != null && !indicadorSelecao) {
				toStringCache = toStringCache.concat("/" + getUf());
			}
			// Caso nome esteja vazio, estava retornando null
			if (toStringCache == null) {
				toStringCache = "";
			}
		}
		return toStringCache;
	}

	/**
	 * Monta uma string removendo acentos e espacos em branco 
	 * para ser utilizado no styleClass da tag de times.
	 * @return
	 */
	public String getNomeClass() {
		
		if (StringUtil.isEmpty(nomeClass)) {
			
			// substitui a palavra acentuada pela sem acento
			nomeClass = Normalizer.normalize(toString(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			
			// remove espacos
			nomeClass = nomeClass.replaceAll("\\s+", "");
			
			// remove numeros e caracteres diversos
			nomeClass = StringUtil.removeNaoAlfanumerico(nomeClass);  
		}
		
		return nomeClass;
	}
}
