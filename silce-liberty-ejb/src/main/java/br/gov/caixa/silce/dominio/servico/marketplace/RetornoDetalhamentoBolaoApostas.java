package br.gov.caixa.silce.dominio.servico.marketplace;

import java.io.Serializable;
import java.util.List;

import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroEquipe;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroMesDeSorte;

public class RetornoDetalhamentoBolaoApostas implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean indicadorSurpresinha;
	private List<?> dezenas;
	private List<Integer> trevos;
	private Integer timeCoracaoMesSorte;
	private ParametroEquipe timeDoCoracao;
	private ParametroMesDeSorte mesDeSorte;
	private List<PartidaLoteca> partidasLoteca;

	public RetornoDetalhamentoBolaoApostas() {
	}

	public RetornoDetalhamentoBolaoApostas(Boolean indicadorSurpresinha, List<Integer> dezenas, List<Integer> trevos) {
		this.indicadorSurpresinha = indicadorSurpresinha;
		this.dezenas = dezenas;
		this.trevos = trevos;
	}

	public RetornoDetalhamentoBolaoApostas(Boolean indicadorSurpresinha, List<Integer> dezenas, ParametroEquipe timeDoCoracao) {
		this.indicadorSurpresinha = indicadorSurpresinha;
		this.dezenas = dezenas;
		this.timeDoCoracao = timeDoCoracao;
	}
	
	public RetornoDetalhamentoBolaoApostas(Boolean indicadorSurpresinha, List<Integer> dezenas, Integer timeCoracaoMesSorte) {
		this.indicadorSurpresinha = indicadorSurpresinha;
		this.dezenas = dezenas;
		this.timeCoracaoMesSorte = timeCoracaoMesSorte;
	}

	public RetornoDetalhamentoBolaoApostas(Boolean indicadorSurpresinha, List<Integer> dezenas, ParametroMesDeSorte mesDeSorte) {
		this.indicadorSurpresinha = indicadorSurpresinha;
		this.dezenas = dezenas;
		this.mesDeSorte = mesDeSorte;
	}

	public RetornoDetalhamentoBolaoApostas(List<PartidaLoteca> partidaLotecas) {
		this.indicadorSurpresinha = false;
		this.partidasLoteca = partidaLotecas;
	}

	public RetornoDetalhamentoBolaoApostas(Boolean indicadorSurpresinha, List<?> dezenas) {
		this.indicadorSurpresinha = indicadorSurpresinha;
		this.dezenas = dezenas;
	}

	public Boolean getIndicadorSurpresinha() {
		return indicadorSurpresinha;
	}

	public void setIndicadorSurpresinha(Boolean indicadorSurpresinha) {
		this.indicadorSurpresinha = indicadorSurpresinha;
	}

	public List<?> getDezenas() {
		return dezenas;
	}

	public void setDezenas(List<?> dezenas) {
		this.dezenas = dezenas;
	}

	public List<Integer> getTrevos() {
		return trevos;
	}

	public void setTrevos(List<Integer> trevos) {
		this.trevos = trevos;
	}

	public ParametroMesDeSorte getMesDeSorte() {
		return mesDeSorte;
	}

	public void setMesDeSorte(ParametroMesDeSorte mesDeSorte) {
		this.mesDeSorte = mesDeSorte;
	}

	public ParametroEquipe getTimeDoCoracao() {
		return timeDoCoracao;
	}

	public void setTimeDoCoracao(ParametroEquipe timeDoCoracao) {
		this.timeDoCoracao = timeDoCoracao;
	}

	public List<PartidaLoteca> getPartidasLoteca() {
		return partidasLoteca;
	}

	public void setPartidasLoteca(List<PartidaLoteca> partidasLoteca) {
		this.partidasLoteca = partidasLoteca;
	}

	public Integer getTimeCoracaoMesSorte() {
		return timeCoracaoMesSorte;
	}

	public void setTimeCoracaoMesSorte(Integer timeCoracaoMesSorte) {
		this.timeCoracaoMesSorte = timeCoracaoMesSorte;
	}
}
