package br.gov.caixa.silce.dominio.servico.marketplace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.gov.caixa.silce.dominio.jogos.PalpitesLoteca;

public class PartidaLoteca implements Serializable {

	private static final long serialVersionUID = 1L;
	private Equipe equipe1;
	private Equipe equipe2;
	private Boolean empate = Boolean.FALSE;

	public PartidaLoteca() {
	}

	public PartidaLoteca(boolean[] marcacoes) {
		equipe1 = new Equipe(marcacoes[0]);
		empate = marcacoes[1];
		equipe2 = new Equipe(marcacoes[2]);
	}

	public Equipe getEquipe1() {
		return equipe1;
	}

	public void setEquipe1(Equipe equipe1) {
		this.equipe1 = equipe1;
	}

	public Equipe getEquipe2() {
		return equipe2;
	}

	public void setEquipe2(Equipe equipe2) {
		this.equipe2 = equipe2;
	}

	public Boolean getEmpate() {
		return empate;
	}

	public void setEmpate(Boolean empate) {
		this.empate = empate;
	}

	public void populaPartida(Partida partida) {
		this.equipe1.setParametroEquipe(partida.getEquipe1());
		this.equipe2.setParametroEquipe(partida.getEquipe2());
	}

	@SuppressWarnings("deprecation")
	public static List<PartidaLoteca> fromPalpites(PalpitesLoteca palpites) {
		if (palpites != null) {
			List<PartidaLoteca> partidasLoteca = new ArrayList<PartidaLoteca>();
			partidasLoteca = new ArrayList<PartidaLoteca>();
			boolean[][] prognosticos = ((PalpitesLoteca) palpites).getPrognosticosConvertidos();
			for (int i = 0; i < prognosticos.length; i++) {
				PartidaLoteca partida = new PartidaLoteca(prognosticos[i]);
				partidasLoteca.add(partida);
			}
			return partidasLoteca;
		}
		return Collections.emptyList();
	}
}
