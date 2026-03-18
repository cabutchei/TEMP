package br.gov.caixa.silce.dominio.servico.marketplace;

import java.math.BigDecimal;
import java.util.List;

import br.gov.caixa.silce.dominio.entidade.Loterica;

public class RetornoDetalhamentoBolao extends AbstractRetornoErro {

	private static final long serialVersionUID = 1L;

	private Loterica lotericaFormatada;

	public RetornoDetalhamentoBolao(Integer qtdCotaTotal, Integer qtdCotaFisica, Integer qtdCotaDigital, Integer qtdCotaDisponivel,
		Integer qtdCotaBaixadasImpressas, Integer qtdCotaVendidas, Integer qtdCotaReservada, Integer qtdCotaBaixadas, Long loterica, String nomeFantasia, String nomeRazaoSocial,
		Long numeroUF, Long idMunicipio, Integer concurso, Integer tipoConcurso, Integer idModalidade, String dataSorteio, String horaSorteio, BigDecimal vrPremioEstimado,
		BigDecimal vrCotaSemTarifa, BigDecimal vrCotaComTarifa, BigDecimal vrTarifaServicoCota, BigDecimal vrUltimaCotaSemTarifa, BigDecimal vrUltimaCotaComTarifa,
		BigDecimal vrTarifaServicoUltimaCota,
		Integer qtdApostas, Integer qtdNumeros, BigDecimal vrTarifaBolao, BigDecimal vrTotalBolaoComTarifa, BigDecimal vrTotalBolaoSemTarifa,
		List<RetornoDetalhamentoBolaoApostas> apostas) {
		this.qtdCotaTotal = qtdCotaTotal;
		this.qtdCotaFisica = qtdCotaFisica;
		this.qtdCotaDigital = qtdCotaDigital;
		this.qtdCotaDisponivel = qtdCotaDisponivel;
		this.qtdCotaBaixadasImpressas = qtdCotaBaixadasImpressas;
		this.qtdCotaVendidas = qtdCotaVendidas;
		this.qtdCotaReservada = qtdCotaReservada;
		this.qtdCotaBaixadas = qtdCotaBaixadas;
		this.loterica = loterica;
		this.nomeFantasia = nomeFantasia;
		this.nomeRazaoSocial = nomeRazaoSocial;
		this.numeroUF = numeroUF;
		this.idMunicipio = idMunicipio;
		this.concurso = concurso;
		this.tipoConcurso = tipoConcurso;
		this.idModalidade = idModalidade;
		this.dataSorteio = dataSorteio;
		this.horaSorteio = horaSorteio;
		this.vrPremioEstimado = vrPremioEstimado;
		this.vrCotaSemTarifa = vrCotaSemTarifa;
		this.vrCotaComTarifa = vrCotaComTarifa;
		this.valorTarifaServico = vrTarifaServicoCota;
		this.vrUltimaCotaSemTarifa = vrUltimaCotaSemTarifa;
		this.vrUltimaCotaComTarifa = vrUltimaCotaComTarifa;
		this.vrTarifaServicoUltimaCota = vrTarifaServicoUltimaCota;
		this.qtdApostas = qtdApostas;
		this.qtdNumeros = qtdNumeros;
		this.vrTarifaBolao = vrTarifaBolao;
		this.vrTotalBolaoComTarifa = vrTotalBolaoComTarifa;
		this.vrTotalBolaoSemTarifa = vrTotalBolaoSemTarifa;
		this.apostas = apostas;
	}

	public RetornoDetalhamentoBolao() {
		this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null);
	}

	public RetornoDetalhamentoBolao(RetornoErro retornoErro) {
		super(retornoErro);
	}

	public Loterica getLotericaFormatada() {
		return lotericaFormatada;
	}

	public void setLotericaFormatada(Loterica lotericaFormatada) {
		this.lotericaFormatada = lotericaFormatada;
	}
}
