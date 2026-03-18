package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.Palpites;
import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;
import br.gov.caixa.silce.dominio.util.ApostaUtil;

public class ApostaFavoritaVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int TAMANHO_TO_STRING = 600;

	private Long id;

	private Long apostador;

	private Modalidade modalidade;

	private Palpites<?> palpites;

	private String nome;

	private Integer timeDoCoracao;

	private Integer mesDeSorte;

	private Palpites<?> trevos;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the apostador
	 */
	public Long getApostador() {
		return apostador;
	}

	/**
	 * @param apostador
	 *            the apostador to set
	 */
	public void setApostador(Long apostador) {
		this.apostador = apostador;
	}

	/**
	 * @return the modalidade
	 */
	public Modalidade getModalidade() {
		return modalidade;
	}

	/**
	 * @param modalidade
	 *            the modalidade to set
	 */
	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	/**
	 * @return the palpites
	 */
	public Palpites<?> getPalpites() {
		return palpites;
	}

	/**
	 * @param palpites
	 *            the palpites to set
	 */
	public void setPalpites(Palpites<?> palpites) {
		this.palpites = palpites;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the timeDoCoracao
	 */
	public Integer getTimeDoCoracao() {
		return timeDoCoracao;
	}

	/**
	 * @param timeDoCoracao
	 *            the timeDoCoracao to set
	 */
	public void setTimeDoCoracao(Integer timeDoCoracao) {
		this.timeDoCoracao = timeDoCoracao;
	}

	/**
	 * @return the tamanhoToString
	 */
	public static int getTamanhoToString() {
		return TAMANHO_TO_STRING;
	}

	public Integer getMesDeSorte() {
		return mesDeSorte;
	}

	public void setMesDeSorte(Integer mesDeSorte) {
		this.mesDeSorte = mesDeSorte;
	}

	public Palpites<?> getTrevos() {
		return trevos;
	}

	public void setTrevos(Palpites<?> trevos) {
		this.trevos = trevos;
	}

	public final AbstractApostaFavorita createApostaFavorita(Apostador apostador) {
		AbstractApostaFavorita apostaFavorita = ApostaUtil.gereApostaFavorita(modalidade);
		apostaFavorita.setApostador(apostador);
		apostaFavorita.setNome(nome);
		apostaFavorita.setModalidade(modalidade);
		apostaFavorita.setId(id);
		initPalpites(apostaFavorita);
		if(modalidade.isTimemania()){
			((ApostaFavoritaTimemania) apostaFavorita).setTimeDoCoracao(timeDoCoracao);
		}else if(modalidade.isDiaDeSorte()){
			((ApostaFavoritaDiaDeSorte) apostaFavorita).setMesDeSorte(mesDeSorte);
		} else if (modalidade.isMaisMilionaria()) {
			((ApostaFavoritaMaisMilionaria) apostaFavorita).setTrevos((PalpitesSequenciais) trevos);
		}
		return apostaFavorita;
	}

	private void initPalpites(AbstractApostaFavorita apostaF) {
		if (apostaF instanceof AbstractApostaFavoritaNumerica) {
			((AbstractApostaFavoritaNumerica) apostaF).setPalpites((PalpitesSequenciais) palpites);
		} else {
			throw new IllegalArgumentException("Tipo não esperado para favoritas");
		}
	}

}
