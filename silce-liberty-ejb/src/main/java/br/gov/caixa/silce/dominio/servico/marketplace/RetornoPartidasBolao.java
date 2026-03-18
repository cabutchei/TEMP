package br.gov.caixa.silce.dominio.servico.marketplace;

import java.util.List;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

public class RetornoPartidasBolao extends AbstractRetornoErro {

	private static final long serialVersionUID = 1L;

	private List<Partida> partida;

	public RetornoPartidasBolao(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public RetornoPartidasBolao(Integer concurso, Integer modalidade, List<Partida> partida) {
		this.concurso = concurso;
		this.idModalidade = modalidade;
		this.setModalidade(Modalidade.getByCodigo(modalidade));
		this.partida = partida;
	}

	public List<Partida> getPartida() {
		return partida;
	}

	public void setPartida(List<Partida> partida) {
		this.partida = partida;
	}
}
