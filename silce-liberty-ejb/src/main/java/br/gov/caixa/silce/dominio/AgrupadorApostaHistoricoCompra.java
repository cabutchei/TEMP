package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.caixa.silce.dominio.entidade.Apostador;
import br.gov.caixa.silce.dominio.entidade.HistoricoAposta;
import br.gov.caixa.silce.dominio.entidade.HistoricoCompra;
import br.gov.caixa.silce.dominio.entidade.HistoricoReservaCotaBolao;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta.Situacao;
import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.CollectionUtil;
import br.gov.caixa.util.Decimal;

public class AgrupadorApostaHistoricoCompra implements Serializable {

	private static final long serialVersionUID = 1L;

	private Apostador apostador;
	private HistoricoCompra historicoCompra;
	private Map<Modalidade, List<HistoricoAposta<?>>> mapaApostas;
	private Integer qtdApostas;
	private Integer qtdApostasEmProcessamento;
	private Integer qtdApostasEfetivadas;
	private Integer qtdNaoEfetivadasDinheiroDevolvido;
	private Integer qtdNaoEfetivadasDinheiroEmDevolucao;
	private Integer qtdApostasCotas;
	private Integer qtdApostasCotasEmProcessamento;
	private Integer qtdApostasCotasEfetivadas;

	private Decimal valorApostasEmProcessamento;
	private Decimal valorApostasEfetivadas;
	private Decimal valorNaoEfetivadasDinheiroDevolvido;
	private Decimal valorNaoEfetivadasDinheiroEmDevolucao;
	private Decimal valorApostasCotasEmProcessamento;
	private Decimal valorApostasCotasEfetivadas;
	private Decimal valorApostasCotasCusteio;
	private Decimal valorApostasCotasTarifaCusteio;

	private boolean indicadorBolao;
	private List<HistoricoAposta<?>> apostas;
	private String pixCopiaECola;
	private String qrcodeArrByte;

	public AgrupadorApostaHistoricoCompra() {
		resetMapaApostas();
		this.apostas = new ArrayList<HistoricoAposta<?>>();
	}

	private void resetMapaApostas() {
		if (mapaApostas == null) {
			mapaApostas = new HashMap<Modalidade, List<HistoricoAposta<?>>>();
		}
		mapaApostas.clear();
		for (Modalidade modalidade : Modalidade.values()) {
			mapaApostas.put(modalidade, new ArrayList<HistoricoAposta<?>>());
		}
	}

	public boolean contemApostasMegasena() {
		return !CollectionUtil.isVazio(getListaMegasena());
	}

	public boolean contemApostasQuina() {
		return !CollectionUtil.isVazio(getListaQuina());
	}

	public boolean contemApostasLotofacil() {
		return !CollectionUtil.isVazio(getListaLotofacil());
	}

	public boolean contemApostasLotomania() {
		return !CollectionUtil.isVazio(getListaLotomania());
	}

	public boolean contemApostasDuplasena() {
		return !CollectionUtil.isVazio(getListaDuplasena());
	}

	public boolean contemApostasTimemania() {
		return !CollectionUtil.isVazio(getListaTimemania());
	}

	public boolean contemApostasLotogol() {
		return !CollectionUtil.isVazio(getListaLotogol());
	}

	public boolean contemApostasLoteca() {
		return !CollectionUtil.isVazio(getListaLoteca());
	}

	public HistoricoCompra getHistoricoCompra() {
		return historicoCompra;
	}

	public void setHistoricoCompra(HistoricoCompra historicoCompra) {
		this.historicoCompra = historicoCompra;
	}

	public Apostador getApostador() {
		return apostador;
	}

	public void setApostador(Apostador apostador) {
		this.apostador = apostador;
	}

	public List<HistoricoAposta<?>> getLista(Modalidade modalidade) {
		return mapaApostas.get(modalidade);
	}

	public List<HistoricoAposta<?>> getListaMegasena() {
		return mapaApostas.get(Modalidade.MEGA_SENA);
	}

	public List<HistoricoAposta<?>> getListaQuina() {
		return mapaApostas.get(Modalidade.QUINA);
	}

	public List<HistoricoAposta<?>> getListaLotofacil() {
		return mapaApostas.get(Modalidade.LOTOFACIL);
	}

	public List<HistoricoAposta<?>> getListaLotogol() {
		return mapaApostas.get(Modalidade.LOTOGOL);
	}

	public List<HistoricoAposta<?>> getListaLotomania() {
		return mapaApostas.get(Modalidade.LOTOMANIA);
	}

	public List<HistoricoAposta<?>> getListaDuplasena() {
		return mapaApostas.get(Modalidade.DUPLA_SENA);
	}

	public List<HistoricoAposta<?>> getListaLoteca() {
		return mapaApostas.get(Modalidade.LOTECA);
	}

	public List<HistoricoAposta<?>> getListaTimemania() {
		return mapaApostas.get(Modalidade.TIMEMANIA);
	}

	public List<HistoricoAposta<?>> getListaMaisMilionaria() {
		return mapaApostas.get(Modalidade.MAIS_MILIONARIA);
	}

	public void addApostas(List<HistoricoAposta<?>> apostas) {
		this.apostas.addAll(apostas);
		Collections.sort(this.apostas, new Comparator<HistoricoAposta<?>>() {
			public int compare(HistoricoAposta<?> o1, HistoricoAposta<?> o2) {
				Long id1 = null;
				Long id2 = null;

				if (o1.getApostaOriginal() == null && o1.getApostaComprada() == null) {
					return 0;
				} else if (o1.getApostaOriginal() == null && o1.getApostaComprada() != null) {
					id1 = o1.getApostaComprada().getId().getId();
				} else {
					id1 = o1.getApostaOriginal().getId().getId();
				}

				if (o2.getApostaOriginal() == null && o2.getApostaComprada() == null) {
					return 0;
				} else if (o2.getApostaOriginal() == null && o2.getApostaComprada() != null) {
					id2 = o2.getApostaComprada().getId().getId();
				} else {
					id2 = o2.getApostaOriginal().getId().getId();
				}

				return id2.compareTo(id1);
			}
		});
		resetQuantidades();
		for (HistoricoAposta<?> aposta : this.apostas) {
			mapaApostas.get(aposta.getModalidade()).add(aposta);
			aposta.getComboAposta();
			if (aposta.getIndicadorBolao() && aposta.getReservaCotaBolao() != null) {
				if (!this.indicadorBolao) {
					this.indicadorBolao = aposta.getIndicadorBolao();
				}
			}
		}
	}

	private void resetQuantidades() {
		qtdApostas = null;
		qtdApostasEmProcessamento = null;
		qtdApostasEfetivadas = null;
		qtdNaoEfetivadasDinheiroDevolvido = null;
		qtdNaoEfetivadasDinheiroEmDevolucao = null;
		qtdApostasCotas = null;
		qtdApostasCotasEmProcessamento = null;
		qtdApostasCotasEfetivadas = null;
		valorApostasEmProcessamento = null;
		valorApostasEfetivadas = null;
		valorNaoEfetivadasDinheiroDevolvido = null;
		valorNaoEfetivadasDinheiroEmDevolucao = null;
		valorApostasCotasEmProcessamento = null;
		valorApostasCotasEfetivadas = null;
		valorApostasCotasCusteio = null;
		valorApostasCotasTarifaCusteio = null;
		resetMapaApostas();
	}

	public Integer getQtdApostas() {
		if (qtdApostas == null) {
			qtdApostas = 0;

			for (Modalidade modalidade : Modalidade.values()) {
				qtdApostas += mapaApostas.get(modalidade).size();
			}
		}

		return qtdApostas;
	}

	public Decimal getValorTotal() {
		return historicoCompra.getValorTotal();
	}

	private Decimal getValorTotal(HistoricoReservaCotaBolao reservaCotaBolao) {
		return reservaCotaBolao.getValorCotaReservada().add(reservaCotaBolao.getValorTarifaServico());
	}

	public Integer getQtdEmProcessamento() {
		if (qtdApostasEmProcessamento == null) {
			qtdApostasEmProcessamento = countQtdSituacao(Situacao.getSituacoesEmProcessamento());
		}
		return qtdApostasEmProcessamento;
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

	public Integer getQtdNaoEfetivadasDinheiroEmDevolucao() {
		if (qtdNaoEfetivadasDinheiroEmDevolucao == null) {
			qtdNaoEfetivadasDinheiroEmDevolucao = countQtdSituacao(Situacao.NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO);
		}
		return qtdNaoEfetivadasDinheiroEmDevolucao;
	}

	public Integer getQtdApostasCotas() {
		if (qtdApostasCotas == null) {
			qtdApostasCotas = 0;
			for (Modalidade modalidade : Modalidade.values()) {
				for (HistoricoAposta<?> aposta : mapaApostas.get(modalidade)) {
					if (aposta.getIndicadorBolao()) {
						qtdApostasCotas++;
					}
				}
			}
		}

		return qtdApostasCotas;
	}

	public Integer getQtdApostasCotasEmProcessamento() {
		if (qtdApostasCotasEmProcessamento == null) {
			qtdApostasCotasEmProcessamento = countQtdSituacao(SituacaoReservaCotaBolao.Situacao.getSituacoesEmProcessamento());
		}

		return qtdApostasCotasEmProcessamento;
	}

	public Integer getQtdApostasCotasEfetivadas() {
		if (qtdApostasCotasEfetivadas == null) {
			qtdApostasCotasEfetivadas = countQtdSituacao(SituacaoReservaCotaBolao.Situacao.CONFIRMADA);
		}

		return qtdApostasCotasEfetivadas;
	}

	public Decimal getValorEmProcessamento() {
		if (valorApostasEmProcessamento == null) {
			valorApostasEmProcessamento = someApostas(Situacao.getSituacoesEmProcessamento());
		}
		return valorApostasEmProcessamento;
	}

	public Decimal getValorEfetivadas() {
		if (valorApostasEfetivadas == null) {
			valorApostasEfetivadas = someApostas(Situacao.getSituacoesEfetivada());
		}
		return valorApostasEfetivadas;
	}

	public Decimal getValorEfetivadas(Modalidade modalidade) {
		return someApostas(mapaApostas.get(modalidade), Arrays.asList(Situacao.getSituacoesEfetivada()));
	}

	public Decimal getValorNaoEfetivadasDinheiroDevolvido() {
		if (valorNaoEfetivadasDinheiroDevolvido == null) {
			valorNaoEfetivadasDinheiroDevolvido = someApostas(Situacao.NAO_EFETIVADA_DINHEIRO_DEVOLVIDO);
		}
		return valorNaoEfetivadasDinheiroDevolvido;
	}

	public Decimal getValorNaoEfetivadasDinheiroEmDevolucao() {
		if (valorNaoEfetivadasDinheiroEmDevolucao == null) {
			valorNaoEfetivadasDinheiroEmDevolucao = someApostas(Situacao.NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO);
		}
		return valorNaoEfetivadasDinheiroEmDevolucao;
	}

	public Decimal getValorApostasCotasEmProcessamento() {
		if (valorApostasCotasEmProcessamento == null) {
			valorApostasCotasEmProcessamento = someApostas(SituacaoReservaCotaBolao.Situacao.getSituacoesEmProcessamento());
		}

		return valorApostasCotasEmProcessamento;
	}

	public Decimal getValorApostasCotasEfetivadas() {
		if (valorApostasCotasEfetivadas == null) {
			valorApostasCotasEfetivadas = someApostas(SituacaoReservaCotaBolao.Situacao.CONFIRMADA);
		}

		return valorApostasCotasEfetivadas;
	}

	public Decimal getValorApostasCotasCusteio() {
		if (valorApostasCotasCusteio == null) {
			valorApostasCotasCusteio = someCusteio(false);
		}
		return valorApostasCotasCusteio;
	}

	public Decimal getValorApostasCotasTarifaCusteio() {
		if (valorApostasCotasTarifaCusteio == null) {
			valorApostasCotasTarifaCusteio = someCusteio(true);
		}
		return valorApostasCotasTarifaCusteio;
	}

	private int countQtdSituacao(Situacao... situacoes) {
		int qtd = 0;
		List<Situacao> situacoesList = Arrays.asList(situacoes);

		for (Modalidade modalidade : Modalidade.values()) {
			List<HistoricoAposta<?>> apostasDaModalidade = mapaApostas.get(modalidade);
			for (HistoricoAposta<?> aposta : apostasDaModalidade) {
				if (aposta.getSituacao() != null && situacoesList.contains(aposta.getSituacao().getEnum())) {
					qtd++;
				}
			}
		}
		return qtd;
	}

	private Integer countQtdSituacao(SituacaoReservaCotaBolao.Situacao... situacoes) {
		int qtd = 0;
		List<SituacaoReservaCotaBolao.Situacao> situacoesList = Arrays.asList(situacoes);

		for (Modalidade modalidade : Modalidade.values()) {
			List<HistoricoAposta<?>> apostasDaModalidade = mapaApostas.get(modalidade);
			for (HistoricoAposta<?> aposta : apostasDaModalidade) {
				if (aposta.getIndicadorBolao()) {
					if (aposta.getReservaCotaBolao().getSituacao() != null && situacoesList.contains(aposta.getReservaCotaBolao().getSituacao().getEnum())) {
						qtd++;
					}
				}
			}
		}
		return qtd;
	}

	private Decimal someApostas(List<HistoricoAposta<?>> apostas, List<Situacao> situacoesList) {
		Decimal total = Decimal.ZERO;
		for (HistoricoAposta<?> aposta : apostas) {
			if (aposta.getSituacao() != null && situacoesList.contains(aposta.getSituacao().getEnum()) && !aposta.isApostaTroca()) {
				total = total.add(aposta.getValor());
			}
		}
		return total;
	}

	private Decimal someApostas(Situacao... situacoes) {
		Decimal total = Decimal.ZERO;
		List<Situacao> situacoesList = Arrays.asList(situacoes);
		for (Modalidade modalidade : Modalidade.values()) {
			total = total.add(someApostas(mapaApostas.get(modalidade), situacoesList));
		}
		return total;
	}

	private Decimal someApostas(SituacaoReservaCotaBolao.Situacao... situacoes) {
		Decimal total = Decimal.ZERO;
		List<SituacaoReservaCotaBolao.Situacao> situacoesList = Arrays.asList(situacoes);
		for (Modalidade modalidade : Modalidade.values()) {
			total = total.add(someApostasCotas(mapaApostas.get(modalidade), situacoesList));
		}
		return total;
	}

	// TODO Valor da cota sem ou com tarifa na soma?
	private Decimal someApostasCotas(List<HistoricoAposta<?>> apostas, List<SituacaoReservaCotaBolao.Situacao> situacoesList) {
		Decimal total = Decimal.ZERO;
		for (HistoricoAposta<?> aposta : apostas) {
			if (aposta.getIndicadorBolao()) {
				if (aposta.getReservaCotaBolao().getSituacao() != null && situacoesList.contains(aposta.getReservaCotaBolao().getSituacao().getEnum())) {
					total = total.add(getValorTotal(aposta.getReservaCotaBolao()));
				}
			}
		}
		return total;
	}

	private Decimal someCusteio(boolean isTarifa) {
		Decimal total = Decimal.ZERO;
		for (Modalidade modalidade : Modalidade.values()) {
			for (HistoricoAposta<?> aposta : mapaApostas.get(modalidade)) {
				if (aposta.getIndicadorBolao()) {
					if (isTarifa) {
						if (aposta.getReservaCotaBolao().getValorTarifaCusteio() == null) {
							aposta.getReservaCotaBolao().setValorTarifaCusteio(Decimal.ZERO);
						}
						total = total.add(aposta.getReservaCotaBolao().getValorTarifaCusteio());
					} else {
						if (aposta.getReservaCotaBolao().getValorCotaCusteio() == null) {
							aposta.getReservaCotaBolao().setValorCotaCusteio(Decimal.ZERO);
						}
						total = total.add(aposta.getReservaCotaBolao().getValorCotaCusteio());
					}
				}
			}
		}
		return total;
	}

	public List<HistoricoAposta<?>> getApostas() {
		return apostas;
	}

	public String getPixCopiaECola() {
		return pixCopiaECola;
	}

	public void setPixCopiaECola(String pixCopiaECola) {
		this.pixCopiaECola = pixCopiaECola;
	}

	public String getQrcodeArrByte() {
		return qrcodeArrByte;
	}

	public void setQrcodeArrByte(String qrcodeArrByte) {
		this.qrcodeArrByte = qrcodeArrByte;
	}

	public boolean isIndicadorBolao() {
		return indicadorBolao;
	}

	public void setIndicadorBolao(boolean indicadorBolao) {
		this.indicadorBolao = indicadorBolao;
	}
}
