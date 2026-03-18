package br.gov.caixa.silce.dominio.jogos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroJogoNumerico;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroMaisMilionaria;
import br.gov.caixa.silce.dominio.entidade.TipoCombo;
import br.gov.caixa.util.Decimal;

public class Combo implements Serializable {

	private static final long serialVersionUID = 1L;
	private final TipoCombo tipoCombo;
	private List<ModalidadeCombo> modalidadesCombo;
	private Decimal valor;

	public Combo(TipoCombo tipoCombo, List<ModalidadeCombo> modalidadesCombo) {
		super();
		this.tipoCombo = tipoCombo;
		Collections.sort(modalidadesCombo);
		this.modalidadesCombo = modalidadesCombo;
		this.valor = Combo.calculaValorCombo(modalidadesCombo);
	}

	public static Decimal calculaValorCombo(List<ModalidadeCombo> modalidades) {
		Decimal custoCombo = Decimal.ZERO;
		for (ModalidadeCombo modalidadeCombo : modalidades) {
			Decimal quantidadeTeimosinhas = new Decimal(modalidadeCombo.getQuantidadeTeimosinhas().toString());
			Decimal quantidadeApostas = new Decimal(modalidadeCombo.getQuantidadeApostas().toString());
			Decimal valorAposta = null;
			if (modalidadeCombo.getModalidade().equals(Modalidade.MAIS_MILIONARIA)) {
				List<Integer> qtPrognosticos = new ArrayList<Integer>();
				qtPrognosticos.add(modalidadeCombo.getQuantidadeDezenas());
				qtPrognosticos.add(modalidadeCombo.getQuantidadeDezenasTrevos());
				valorAposta = ((ParametroMaisMilionaria) modalidadeCombo.getParametro()).getValorMaisMilionaria(qtPrognosticos);
			} else {
				valorAposta = ((ParametroJogoNumerico) modalidadeCombo.getParametro()).getValor(modalidadeCombo.getQuantidadeDezenas());	
			}
			

			if (valorAposta == null) {
				throw new IllegalArgumentException("Valor não existe para quantidade de dezenas.");
			}
			custoCombo = custoCombo.add(quantidadeTeimosinhas.isMaiorQue(Decimal.ZERO) ? valorAposta.multiply(quantidadeTeimosinhas).multiply(quantidadeApostas)
				: valorAposta.multiply(quantidadeApostas));
		}
		return custoCombo;
	}

	public int getQuantidadeApostas() {
		int apostas = 0;
		for (ModalidadeCombo modalidadeCombo : modalidadesCombo) {
			apostas += modalidadeCombo.getQuantidadeApostas();
		}
		return apostas;
	}

	public TipoCombo getTipoCombo() {
		return tipoCombo;
	}

	public List<ModalidadeCombo> getModalidades() {
		return Collections.unmodifiableList(modalidadesCombo);
	}

	public Decimal getValor() {
		return valor;
	}

	public List<ModalidadeCombo> getModalidadesCombo() {
		return modalidadesCombo;
	}

	public void setModalidadesCombo(List<ModalidadeCombo> modalidadesCombo) {
		this.modalidadesCombo = modalidadesCombo;
	}

	public void setValor(Decimal valor) {
		this.valor = valor;
	}

}
