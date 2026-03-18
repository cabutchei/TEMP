package br.gov.caixa.silce.dominio.servico.marketplace;

import java.util.List;

import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroEquipe;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroMesDeSorte;

public class ApostaBolao extends AbstractRetornoErro {

	private Boolean indicadorSurpresinha;
	private List<?> dezenas;
	private List<Integer> trevos;
	private Integer mesTime;
	private ParametroMesDeSorte mesDeSorte;
	private ParametroEquipe timeDoCoracao;
	private List<PartidaLoteca> partidasLoteca;

	private static final long serialVersionUID = 1L;

	public ApostaBolao(Boolean indicadorSurpresinha, List<Integer> dezenas) {
		this.indicadorSurpresinha = indicadorSurpresinha;
		this.dezenas = dezenas;
	}

	public ApostaBolao(Boolean indicadorSurpresinha, List<Integer> dezenas, Integer mesTime) {
		this.indicadorSurpresinha = indicadorSurpresinha;
		this.dezenas = dezenas;
		this.mesTime = mesTime;
	}

	public ApostaBolao(Boolean indicadorSurpresinha, List<Integer> dezenas, ParametroMesDeSorte mesDeSorte) {
		this.indicadorSurpresinha = indicadorSurpresinha;
		this.dezenas = dezenas;
		this.mesDeSorte = mesDeSorte;
	}

	public ApostaBolao(Boolean indicadorSurpresinha, List<Integer> dezenas, List<Integer> trevos) {
		this.indicadorSurpresinha = indicadorSurpresinha;
		this.dezenas = dezenas;
		this.trevos = trevos;
	}

	public ApostaBolao(Boolean indicadorSurpresinha, List<Integer> dezenas, ParametroEquipe timeDoCoracao) {
		this.indicadorSurpresinha = indicadorSurpresinha;
		this.dezenas = dezenas;
		this.timeDoCoracao = timeDoCoracao;
	}

	public ApostaBolao(List<PartidaLoteca> partidasLoteca) {
		this.indicadorSurpresinha = false;
		this.partidasLoteca = partidasLoteca;
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

	public Integer getMesTime() {
		return mesTime;
	}

	public void setMesTime(Integer mesTime) {
		this.mesTime = mesTime;
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

	public void setTimeDoCoracao(ParametroEquipe timeCoracao) {
		this.timeDoCoracao = timeCoracao;
	}

	public List<PartidaLoteca> getPartidasLoteca() {
		return partidasLoteca;
	}

	public void setPartidasLoteca(List<PartidaLoteca> partidasLoteca) {
		this.partidasLoteca = partidasLoteca;
	}
}
