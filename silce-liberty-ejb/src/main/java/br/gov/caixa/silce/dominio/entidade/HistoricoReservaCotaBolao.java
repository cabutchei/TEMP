package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.silce.dominio.openjpa.HoraValueHandler;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;

/**
 * @author c142924
 *
 */
@Entity
@Table(name = "LCETB959_HSTRO_RSRVA_COTA_BLO", schema = DatabaseConfig.SCHEMA)
public class HistoricoReservaCotaBolao extends AbstractEntidade<HistoricoReservaCotaBolaoPK> {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private HistoricoReservaCotaBolaoPK id;

	@Column(name = "NU_PARTICAO")
	private Integer particao;

	@Column(name = "CO_BOLAO_RESERVA_COTA")
	private String codBolao;

	@Column(name = "CO_COTA_BOLAO")
	private String codCota;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_CANAL_DISTRIBUICAO", referencedColumnName = "NU_CD")
	private Loterica loterica;

	@Column(name = "AA_RESERVA_COTA_BOLAO")
	private Integer ano;

	@Column(name = "TS_DATA_HORA_RESERVA_COTA")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataHoraReserva;

	@Column(name = "DT_EXPRO_RESERVA_COTA_BOLAO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataExpiracaoReservaCotaBolao;

	@Column(name = "HH_EXPRO_RESERVA_COTA_BOLAO")
	@Strategy(HoraValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIME)
	private Hora horaExpiracaoReservaCotaBolao;

	@Column(name = "NU_COTA_RESERVADA")
	private Integer numeroCotaReservada;

	@Column(name = "QT_COTA_TOTAL_BOLAO")
	private Integer qtdCotaTotal;

	@Column(name = "VR_COTA_RESERVADA")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorCotaReservada;

	@Column(name = "VR_TARIFA_SERVICO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorTarifaServico;

	@Column(name = "DT_REGISTRO_BOLAO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataRegistroBolao;

	@Column(name = "HH_REGISTRO_BOLAO")
	@Strategy(HoraValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIME)
	private Hora horaRegistroBolao;

	@Column(name = "NU_TERMINAL_LOTERICO")
	private Integer numeroTerminalLoterico;
	
	@Column(name = "DE_COTA_BOLAO")
	private String dadosCotaBolao;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_SITUACAO_RESERVA_COTA_BOLAO")
	@EagerFetchMode(FetchMode.PARALLEL)
	private SituacaoReservaCotaBolao situacao;

	@Column(name = "TS_ALTERACAO_SITUACAO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataUltimaSituacao;

	@Column(name = "VR_CUSTEIO_COTA")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorCotaCusteio;

	@Column(name = "VR_TARIFA_CUSTEIO_COTA")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorTarifaCusteio;

	@Column(name = "NU_TRANSACAO_RESERVA_COTA")
	private Long nsu;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "reservaCotaBolao")
	private HistoricoAposta<?> aposta;

	public HistoricoReservaCotaBolao() {
	}

	public Data getDataHoraExpiracao() {
		Data dataHoraExpiracao = null;

		if (this.getDataExpiracaoReservaCotaBolao() != null) {
			dataHoraExpiracao = DataUtil.stringToData(
				DataUtil.dataToString(this.getDataExpiracaoReservaCotaBolao(), DataUtil.YYYY_MM_DD) + " "
					+ DataUtil.horaToString(this.getHoraExpiracaoReservaCotaBolao(), DataUtil.HORA),
				DataUtil.DIA_HORA_EUA_2);
		}
		return dataHoraExpiracao;
	}

	public Decimal getValorTotal() {
		Decimal total = Decimal.ZERO;
		if (valorCotaReservada != null && valorTarifaServico != null) {
			total = valorCotaReservada.add(valorTarifaServico);
		}
		return total;
	}

	public HistoricoReservaCotaBolaoPK getId() {
		return id;
	}

	public void setId(HistoricoReservaCotaBolaoPK id) {
		this.id = id;
	}

	public String getCodBolao() {
		return codBolao;
	}

	public void setCodBolao(String codBolao) {
		this.codBolao = codBolao;
	}

	public String getCodCota() {
		return codCota;
	}

	public void setCodCota(String codCota) {
		this.codCota = codCota;
	}

	public Loterica getLoterica() {
		return loterica;
	}

	public void setLoterica(Loterica loterica) {
		this.loterica = loterica;
	}

	public Data getDataHoraReserva() {
		return dataHoraReserva;
	}

	public void setDataHoraReserva(Data dataHoraReserva) {
		this.dataHoraReserva = dataHoraReserva;
	}

	public Data getDataExpiracaoReservaCotaBolao() {
		return dataExpiracaoReservaCotaBolao;
	}

	public void setDataExpiracaoReservaCotaBolao(Data dataExpiracaoReservaCotaBolao) {
		this.dataExpiracaoReservaCotaBolao = dataExpiracaoReservaCotaBolao;
	}

	public Hora getHoraExpiracaoReservaCotaBolao() {
		return horaExpiracaoReservaCotaBolao;
	}

	public void setHoraExpiracaoReservaCotaBolao(Hora horaExpiracaoReservaCotaBolao) {
		this.horaExpiracaoReservaCotaBolao = horaExpiracaoReservaCotaBolao;
	}

	public Integer getNumeroCotaReservada() {
		return numeroCotaReservada;
	}

	public void setNumeroCotaReservada(Integer numeroCotaReservada) {
		this.numeroCotaReservada = numeroCotaReservada;
	}

	public Integer getQtdCotaTotal() {
		return qtdCotaTotal;
	}

	public void setQtdCotaTotal(Integer qtdCotaTotal) {
		this.qtdCotaTotal = qtdCotaTotal;
	}

	public Decimal getValorCotaReservada() {
		return valorCotaReservada;
	}

	public void setValorCotaReservada(Decimal valorCotaReservada) {
		this.valorCotaReservada = valorCotaReservada;
	}

	public Decimal getValorTarifaServico() {
		return valorTarifaServico;
	}

	public void setValorTarifaServico(Decimal valorTarifaServico) {
		this.valorTarifaServico = valorTarifaServico;
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

	public String getDadosCotaBolao() {
		return dadosCotaBolao;
	}

	public void setDadosCotaBolao(String dadosCotaBolao) {
		this.dadosCotaBolao = dadosCotaBolao;
	}

	public SituacaoReservaCotaBolao getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoReservaCotaBolao situacao) {
		this.situacao = situacao;

		// Sempre que atualizar a situacao, atualizar a data da ultima alteracao
		setDataUltimaSituacao(DataUtil.getTimestampAtual());
	}

	public Data getDataUltimaSituacao() {
		return dataUltimaSituacao;
	}

	public void setDataUltimaSituacao(Data dataUltimaSituacao) {
		this.dataUltimaSituacao = dataUltimaSituacao;
	}

	public Decimal getValorCotaCusteio() {
		return valorCotaCusteio;
	}

	public void setValorCotaCusteio(Decimal valorCotaCusteio) {
		this.valorCotaCusteio = valorCotaCusteio;
	}

	public Decimal getValorTarifaCusteio() {
		return valorTarifaCusteio;
	}

	public void setValorTarifaCusteio(Decimal valorTarifaCusteio) {
		this.valorTarifaCusteio = valorTarifaCusteio;
	}

	public Long getNsu() {
		return nsu;
	}

	public void setNsu(Long nsu) {
		this.nsu = nsu;
	}

	public HistoricoAposta<?> getAposta() {
		return aposta;
	}

	public void setAposta(HistoricoAposta<?> aposta) {
		this.aposta = aposta;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getParticao() {
		return particao;
	}

	public void setParticao(Integer particao) {
		this.particao = particao;
	}
}
