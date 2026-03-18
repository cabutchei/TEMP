package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.io.Serializable;

import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.StringUtil;

/**
 * parâmetro que representa um Mês de Sorte utilizado pelo Dia de Sorte.
 * 
 */
public class ParametroMesDeSorte implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer numero;

	private String nome;

	private String abreviacao;

	private String toStringCache;

	public ParametroMesDeSorte() {
		// construtor padrão para permitir a construção de um parâmetro vazio que será preenchido depois
	}

	public ParametroMesDeSorte(Integer numero) {
		setNumero(numero);
	}

	public String toString() {
		if (StringUtil.isEmpty(toStringCache)) {
			toStringCache = getNumero() + " - " + getNome();
		}
		return toStringCache;
	}

	public Integer getNumero() {
		return numero;
	}

	public final void setNumero(Integer numero) {
		this.toStringCache = null;
		this.nome = null;
		this.abreviacao = null;
		this.numero = numero;
		if (numero != null && !numero.equals(0)) {
			this.nome = DataUtil.getNomeMes(numero);
			this.abreviacao = DataUtil.getAbreviacaoMes(numero);
		}
	}

	public String getNome() {
		return nome;
	}

	public String getAbreviacao() {
		return abreviacao;
	}

	public static ParametroMesDeSorte getByNomeMes(String nomeMes) {
		Integer numeroMes = DataUtil.getNumeroMesPorNome(nomeMes.trim());
		return new ParametroMesDeSorte(numeroMes);
	}

}
