package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import br.gov.caixa.silce.dominio.entidade.Apostador;
import br.gov.caixa.silce.dominio.entidade.HistoricoAposta;
import br.gov.caixa.silce.dominio.entidade.HistoricoCompra;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta.Situacao;
import br.gov.caixa.util.Decimal;

/**
 * Feito similar ao AgrupadorApostaCompra para ficar mais facil criação das telas de histórico
 * 
 * @author c101482
 *
 */
public class AgrupadorHistoricoApostaCompra implements Serializable {

	private static final long serialVersionUID = 1L;

	private Apostador apostador;
	private HistoricoCompra compra;

	private Integer qtdApostas;
	private Integer qtdApostasEfetivadas;
	private Integer qtdNaoEfetivadasDinheiroDevolvido;

	private Decimal valorApostasEfetivadas;
	private Decimal valorNaoEfetivadasDinheiroDevolvido;

	private List<HistoricoAposta<?>> apostas;

	public AgrupadorHistoricoApostaCompra(HistoricoCompra compra, Apostador apostador, List<HistoricoAposta<?>> apostas) {
		this.compra = compra;
		this.apostador = apostador;
		this.apostas = apostas;
	}
	
	public HistoricoCompra getHistoricoCompra() {
		return compra;
	}

	public Apostador getApostador() {
		return apostador;
	}
	
	public Integer getQtdApostas() {
		// Esta assim pq copiamos do AgrupadorApostaCompra
		if (qtdApostas == null) {
			qtdApostas = apostas.size();
		}

		return qtdApostas;
	}

	public Decimal getValorTotal() {
		return compra.getValorTotal();
	}

	public Integer getQtdEfetivadas() {
		if (qtdApostasEfetivadas == null) {
			qtdApostasEfetivadas = countQtdSituacao(Situacao.getSituacoesEfetivada());
		}
		return qtdApostasEfetivadas;
	}

	public Integer getQtdNaoEfetivadasDinheiroDevolvido() {
		if (qtdNaoEfetivadasDinheiroDevolvido == null) {
			qtdNaoEfetivadasDinheiroDevolvido = countQtdSituacao(Situacao.NAO_EFETIVADA_DINHEIRO_DEVOLVIDO);
		}
		return qtdNaoEfetivadasDinheiroDevolvido;
	}

	public Decimal getValorEfetivadas() {
		if (valorApostasEfetivadas == null) {
			valorApostasEfetivadas = someApostas(Situacao.getSituacoesEfetivada());
		}
		return valorApostasEfetivadas;
	}

	public Decimal getValorNaoEfetivadasDinheiroDevolvido() {
		if (valorNaoEfetivadasDinheiroDevolvido == null) {
			valorNaoEfetivadasDinheiroDevolvido = someApostas(Situacao.NAO_EFETIVADA_DINHEIRO_DEVOLVIDO);
		}
		return valorNaoEfetivadasDinheiroDevolvido;
	}

	private int countQtdSituacao(Situacao... situacoes) {
		int qtd = 0;
		List<Situacao> situacoesList = Arrays.asList(situacoes);

		for (HistoricoAposta<?> aposta : apostas) {
			if (aposta.getSituacao() != null && situacoesList.contains(aposta.getSituacao().getEnum())) {
				qtd++;
			}
		}
		return qtd;
	}
	
	private Decimal someApostas(Situacao... situacoes) {
		Decimal total = Decimal.ZERO;
		List<Situacao> situacoesList = Arrays.asList(situacoes);

		for (HistoricoAposta<?> aposta : apostas) {
			if (aposta.getSituacao() != null && situacoesList.contains(aposta.getSituacao().getEnum())) {
				total = total.add(aposta.getValor());
			}
		}
		return total;
	}

	public List<HistoricoAposta<?>> getApostas() {
		return apostas;
	}

}
