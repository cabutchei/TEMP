package br.gov.caixa.silce.dominio.servico.marketplace;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import br.gov.caixa.dominio.SaidaHttp;
import br.gov.caixa.silce.dominio.entidade.Municipio;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolao;
import br.gov.caixa.silce.dominio.entidade.UnidadeFederacao;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.EnumUtil;
import br.gov.caixa.util.Hora;

public class SaidaHttpMarketplace implements SaidaHttp {

	private static final long serialVersionUID = 1L;

	private static final List<Integer> successHttpCodes = Arrays.asList(200, 201, 204);

	public enum SituacaoCota implements CaixaEnum<Long> {

		CRIADA(0L),
		DISPONIVEL(1L),
		RESERVADA(2L),
		BAIXADA_NAO_IMPRESSA(3L),
		BAIXADA_NO_ENCERRAMENTO(4L),
		FISICA_IMPRESSA(5L),
		FISICA_NAO_IMPRESSA(6L),
		BAIXADA_IMPRESSA(7L),
		VENDIDA(8L),
		ESTORNADA(9L),
		SEM_NSBC(10L),
		FISICA_REIMPRESSA(11L);

		private final Long value;

		private SituacaoCota(Long value) {
			this.value = value;
		}

		public Long getValue() {
			return value;
		}

		public static SituacaoCota getByValue(Long value) {
			return EnumUtil.recupereByValue(values(), value);
		}
	}

	private Integer statusCodeHttp;

	protected Integer index;
	protected String codigoBolao;
	protected String codigoCota;
	protected Integer qtdCotaDigital;
	protected Integer qtdCotaTotal;
	protected Integer qtdCotaDisponivel;
	protected Integer qtdCotaFisica;
	protected Integer qtdCotaBaixadasImpressas;
	protected Integer qtdCotaVendidas;
	protected Integer qtdCotaReservada;
	protected Integer qtdCotaBaixadas;
	protected Long loterica;
	protected String nomeFantasia;
	protected String nomeRazaoSocial;
	protected Long numeroUF;
	protected UnidadeFederacao uf;
	protected Long idMunicipio;
	protected Municipio municipio;
	protected Integer concurso;
	protected Integer tipoConcurso;
	protected Integer idModalidade;
	protected Modalidade modalidade;
	protected String dataSorteio;
	protected String horaSorteio;
	protected String diaSorteio;
	protected BigDecimal vrPremioEstimado;
	protected BigDecimal vrCotaSemTarifa;
	protected BigDecimal vrCotaComTarifa;
	protected BigDecimal valorCota;
	protected BigDecimal valorTarifaServico;
	protected BigDecimal vrUltimaCotaSemTarifa;
	protected BigDecimal vrUltimaCotaComTarifa;
	protected BigDecimal vrTarifaServicoUltimaCota;
	protected BigDecimal vrTarifaBolao;
	protected BigDecimal vrTotalBolaoComTarifa;
	protected BigDecimal vrTotalBolaoSemTarifa;
	protected List<RetornoDetalhamentoBolaoApostas> apostas;
	protected Integer qtdApostas;
	protected Integer qtdNumeros;
	protected Integer qtdSimplesLoteca;
	protected Integer qtdDuplosLoteca;
	protected Integer qtdTriplosLoteca;

	protected Integer numeroCota;
	protected String dataHoraReserva;
	protected Data dataRegistroBolao;
	protected Hora horaRegistroBolao;
	protected Integer numeroTerminalLoterico;
	protected String apostasBolao;

	protected SituacaoCota situacaoCota;

	private Integer tempoExpiracao;
	protected List<DadosCotaBolao> cotas;
	protected Integer totalRegistros;
	protected Integer paginaAtual;
	protected Integer ultimaPagina;
	protected BigDecimal valorMenorCota;

	protected String cpf;
	protected Long nsu;
	protected List<Long> nsus;

	protected ReservaCotaBolao reservaCotaBolao;
	protected String nsbc;
	protected Boolean contemResiduo = false;

	public SaidaHttpMarketplace() {
	}

	@Override
	public Boolean isOperacaoExecutadaComSucesso() {
		return statusCodeHttp != null && successHttpCodes.contains(statusCodeHttp);
	}

	@Override
	public Integer getStatusCodeHttp() {
		return statusCodeHttp;
	}

	@Override
	public void setStatusCodeHttp(Integer statusCodeHttp) {
		this.statusCodeHttp = statusCodeHttp;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getCodigoBolao() {
		return codigoBolao;
	}

	public void setCodigoBolao(String codigoBolao) {
		this.codigoBolao = codigoBolao;
	}

	public String getCodigoCota() {
		return codigoCota;
	}

	public void setCodigoCota(String codigoCota) {
		this.codigoCota = codigoCota;
	}

	public Integer getQtdCotaDigital() {
		return qtdCotaDigital;
	}

	public void setQtdCotaDigital(Integer qtdCotaDigital) {
		this.qtdCotaDigital = qtdCotaDigital;
	}

	public Integer getQtdCotaTotal() {
		return qtdCotaTotal;
	}

	public void setQtdCotaTotal(Integer qtdCotaTotal) {
		this.qtdCotaTotal = qtdCotaTotal;
	}

	public Integer getQtdCotaDisponivel() {
		return qtdCotaDisponivel;
	}

	public void setQtdCotaDisponivel(Integer qtdCotaDisponivel) {
		this.qtdCotaDisponivel = qtdCotaDisponivel;
	}

	public Integer getQtdCotaFisica() {
		return qtdCotaFisica;
	}

	public void setQtdCotaFisica(Integer qtdCotaFisica) {
		this.qtdCotaFisica = qtdCotaFisica;
	}

	public Integer getQtdCotaBaixadasImpressas() {
		return qtdCotaBaixadasImpressas;
	}

	public void setQtdCotaBaixadasImpressas(Integer qtdCotaBaixadasImpressas) {
		this.qtdCotaBaixadasImpressas = qtdCotaBaixadasImpressas;
	}

	public Integer getQtdCotaVendidas() {
		return qtdCotaVendidas;
	}

	public void setQtdCotaVendidas(Integer qtdCotaVendidas) {
		this.qtdCotaVendidas = qtdCotaVendidas;
	}

	public Integer getQtdCotaReservada() {
		return qtdCotaReservada;
	}

	public void setQtdCotaReservada(Integer qtdCotaReservada) {
		this.qtdCotaReservada = qtdCotaReservada;
	}

	public Integer getQtdCotaBaixadas() {
		return qtdCotaBaixadas;
	}

	public void setQtdCotaBaixadas(Integer qtdCotaBaixadas) {
		this.qtdCotaBaixadas = qtdCotaBaixadas;
	}

	public Long getLoterica() {
		return loterica;
	}

	public void setLoterica(Long loterica) {
		this.loterica = loterica;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getNomeRazaoSocial() {
		return nomeRazaoSocial;
	}

	public void setNomeRazaoSocial(String nomeRazaoSocial) {
		this.nomeRazaoSocial = nomeRazaoSocial;
	}

	public Long getNumeroUF() {
		return numeroUF;
	}

	public void setNumeroUF(Long numeroUF) {
		this.numeroUF = numeroUF;
	}

	public UnidadeFederacao getUf() {
		return uf;
	}

	public void setUf(UnidadeFederacao uf) {
		this.uf = uf;
	}

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Integer getConcurso() {
		return concurso;
	}

	public void setConcurso(Integer concurso) {
		this.concurso = concurso;
	}

	public Integer getTipoConcurso() {
		return tipoConcurso;
	}

	public void setTipoConcurso(Integer tipoConcurso) {
		this.tipoConcurso = tipoConcurso;
	}

	public Integer getIdModalidade() {
		return idModalidade;
	}

	public void setIdModalidade(Integer idModalidade) {
		this.idModalidade = idModalidade;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public String getDataSorteio() {
		return dataSorteio;
	}

	public void setDataSorteio(String dataSorteio) {
		this.dataSorteio = dataSorteio;
	}

	public String getHoraSorteio() {
		return horaSorteio;
	}

	public void setHoraSorteio(String horaSorteio) {
		this.horaSorteio = horaSorteio;
	}

	public String getDiaSorteio() {
		return diaSorteio;
	}

	public void setDiaSorteio(String diaSorteio) {
		this.diaSorteio = diaSorteio;
	}

	public BigDecimal getVrPremioEstimado() {
		return vrPremioEstimado;
	}

	public void setVrPremioEstimado(BigDecimal vrPremioEstimado) {
		this.vrPremioEstimado = vrPremioEstimado;
	}

	public BigDecimal getVrCotaSemTarifa() {
		return vrCotaSemTarifa;
	}

	public void setVrCotaSemTarifa(BigDecimal vrCotaSemTarifa) {
		this.vrCotaSemTarifa = vrCotaSemTarifa;
	}

	public BigDecimal getVrCotaComTarifa() {
		return vrCotaComTarifa;
	}

	public void setVrCotaComTarifa(BigDecimal vrCotaComTarifa) {
		this.vrCotaComTarifa = vrCotaComTarifa;
	}

	public BigDecimal getValorCota() {
		return valorCota;
	}

	public void setValorCota(BigDecimal valorCota) {
		this.valorCota = valorCota;
	}

	public BigDecimal getValorTarifaServico() {
		return valorTarifaServico;
	}

	public void setValorTarifaServico(BigDecimal valorTarifaServico) {
		this.valorTarifaServico = valorTarifaServico;
	}

	public BigDecimal getVrUltimaCotaSemTarifa() {
		return vrUltimaCotaSemTarifa;
	}

	public void setVrUltimaCotaSemTarifa(BigDecimal vrUltimaCotaSemTarifa) {
		this.vrUltimaCotaSemTarifa = vrUltimaCotaSemTarifa;
	}

	public BigDecimal getVrUltimaCotaComTarifa() {
		return vrUltimaCotaComTarifa;
	}

	public void setVrUltimaCotaComTarifa(BigDecimal vrUltimaCotaComTarifa) {
		this.vrUltimaCotaComTarifa = vrUltimaCotaComTarifa;
	}

	public BigDecimal getVrTarifaServicoUltimaCota() {
		return vrTarifaServicoUltimaCota;
	}

	public void setVrTarifaServicoUltimaCota(BigDecimal vrTarifaServicoUltimaCota) {
		this.vrTarifaServicoUltimaCota = vrTarifaServicoUltimaCota;
	}

	public BigDecimal getVrTarifaBolao() {
		return vrTarifaBolao;
	}

	public void setVrTarifaBolao(BigDecimal vrTarifaBolao) {
		this.vrTarifaBolao = vrTarifaBolao;
	}

	public BigDecimal getVrTotalBolaoComTarifa() {
		return vrTotalBolaoComTarifa;
	}

	public void setVrTotalBolaoComTarifa(BigDecimal vrTotalBolaoComTarifa) {
		this.vrTotalBolaoComTarifa = vrTotalBolaoComTarifa;
	}

	public BigDecimal getVrTotalBolaoSemTarifa() {
		return vrTotalBolaoSemTarifa;
	}

	public void setVrTotalBolaoSemTarifa(BigDecimal vrTotalBolaoSemTarifa) {
		this.vrTotalBolaoSemTarifa = vrTotalBolaoSemTarifa;
	}

	public List<RetornoDetalhamentoBolaoApostas> getApostas() {
		return apostas;
	}

	public void setApostas(List<RetornoDetalhamentoBolaoApostas> apostas) {
		this.apostas = apostas;
	}

	public Integer getQtdApostas() {
		return qtdApostas;
	}

	public void setQtdApostas(Integer qtdApostas) {
		this.qtdApostas = qtdApostas;
	}

	public Integer getQtdNumeros() {
		return qtdNumeros;
	}

	public void setQtdNumeros(Integer qtdNumeros) {
		this.qtdNumeros = qtdNumeros;
	}

	public Integer getQtdSimplesLoteca() {
		return qtdSimplesLoteca;
	}

	public void setQtdSimplesLoteca(Integer qtdSimplesLoteca) {
		this.qtdSimplesLoteca = qtdSimplesLoteca;
	}

	public Integer getQtdDuplosLoteca() {
		return qtdDuplosLoteca;
	}

	public void setQtdDuplosLoteca(Integer qtdDuplosLoteca) {
		this.qtdDuplosLoteca = qtdDuplosLoteca;
	}

	public Integer getQtdTriplosLoteca() {
		return qtdTriplosLoteca;
	}

	public void setQtdTriplosLoteca(Integer qtdTriplosLoteca) {
		this.qtdTriplosLoteca = qtdTriplosLoteca;
	}

	public Integer getNumeroCota() {
		return numeroCota;
	}

	public void setNumeroCota(Integer numeroCota) {
		this.numeroCota = numeroCota;
	}

	public String getDataHoraReserva() {
		return dataHoraReserva;
	}

	public void setDataHoraReserva(String dataHoraReserva) {
		this.dataHoraReserva = dataHoraReserva;
	}

	public SituacaoCota getSituacaoCota() {
		return situacaoCota;
	}

	public void setSituacaoCota(SituacaoCota situacaoCota) {
		this.situacaoCota = situacaoCota;
	}

	public List<DadosCotaBolao> getCotas() {
		return cotas;
	}

	public void setCotas(List<DadosCotaBolao> cotas) {
		this.cotas = cotas;
	}

	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public Integer getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(Integer paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

	public Integer getUltimaPagina() {
		return ultimaPagina;
	}

	public void setUltimaPagina(Integer ultimaPagina) {
		this.ultimaPagina = ultimaPagina;
	}

	public BigDecimal getValorMenorCota() {
		return valorMenorCota;
	}

	public void setValorMenorCota(BigDecimal valorMenorCota) {
		this.valorMenorCota = valorMenorCota;
	}

	public Data getDataRegistroBolao() {
		return dataRegistroBolao;
	}

	public void setDataRegistroBolao(Data dataRegistroBolao) {
		this.dataRegistroBolao = dataRegistroBolao;
	}

	public Hora getHoraRegistroBolao() {
		return horaRegistroBolao;
	}

	public void setHoraRegistroBolao(Hora horaRegistroBolao) {
		this.horaRegistroBolao = horaRegistroBolao;
	}

	public Integer getNumeroTerminalLoterico() {
		return numeroTerminalLoterico;
	}

	public void setNumeroTerminalLoterico(Integer numeroTerminalLoterico) {
		this.numeroTerminalLoterico = numeroTerminalLoterico;
	}

	public String getApostasBolao() {
		return apostasBolao;
	}

	public void setApostasBolao(String apostasBolao) {
		this.apostasBolao = apostasBolao;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getNsu() {
		return nsu;
	}

	public void setNsu(Long nsu) {
		this.nsu = nsu;
	}

	public Integer getTempoExpiracao() {
		return tempoExpiracao;
	}

	public void setTempoExpiracao(Integer tempoExpiracao) {
		this.tempoExpiracao = tempoExpiracao;
	}

	public List<Long> getNsus() {
		return nsus;
	}

	public void setNsus(List<Long> nsus) {
		this.nsus = nsus;
	}

	public ReservaCotaBolao getReservaCotaBolao() {
		return reservaCotaBolao;
	}

	public void setReservaCotaBolao(ReservaCotaBolao reservaCotaBolao) {
		this.reservaCotaBolao = reservaCotaBolao;
	}

	public String getNsbc() {
		return nsbc;
	}

	public void setNsbc(String nsbc) {
		this.nsbc = nsbc;
	}

	public Boolean getContemResiduo() {
		return contemResiduo;
	}

	public void setContemResiduo(Boolean contemResiduo) {
		this.contemResiduo = contemResiduo;
	}

}