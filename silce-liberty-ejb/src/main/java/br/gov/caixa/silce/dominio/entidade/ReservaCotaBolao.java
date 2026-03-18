package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.silce.dominio.openjpa.HoraValueHandler;
import br.gov.caixa.silce.dominio.util.ApostaUtil;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;

/**
 * @author c142924
 *
 */
@Entity
@Table(name = "LCETB059_RESERVA_COTA_BOLAO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = ReservaCotaBolao.NQ_SELECT_BY_SITUACAO, 
			query = "Select reserva From ReservaCotaBolao reserva where reserva.situacao.id = ?1"),

		@NamedQuery(name = ReservaCotaBolao.NQ_DELETE_BY_ID,
			query = "Delete from ReservaCotaBolao reserva where reserva.id.id = ?1 and reserva.id.mes = ?2 and reserva.id.particao = ?3"),

		@NamedQuery(name = ReservaCotaBolao.NQ_SELECT_BY_COD_BOLAO_COD_COTA_CPF,
			query = "Select reserva From ReservaCotaBolao reserva "
				+ "join fetch reserva.aposta "
				+ "join fetch reserva.aposta.compra "
				+ "join fetch reserva.aposta.compra.meioPagamento "
				+ "join fetch reserva.aposta.compra.apostador "
				+ "join fetch reserva.aposta.compra.apostador.municipio "
				+ "where reserva.codBolao = ?1 and reserva.codCota = ?2 and reserva.aposta.compra.apostador.cpf = ?3 and reserva.dataHoraReserva = ?4"),

		@NamedQuery(name = ReservaCotaBolao.NQ_SELECT_BY_NSU_DATA_INCLUSAO_SITUACAO,
			query = "Select reserva From ReservaCotaBolao reserva where reserva.nsu = ?1 and reserva.dataUltimaSituacao = ?2 and reserva.situacao.id = ?3"),

		@NamedQuery(name = ReservaCotaBolao.NQ_SELECT_BY_ID_APOSTA, query = "Select reserva From ReservaCotaBolao reserva "
			+ "join fetch reserva.aposta "
			+ "join fetch reserva.aposta.compra "
			+ "join fetch reserva.aposta.compra.apostador "
			+ "where reserva.aposta.id = ?1")
})

@NamedNativeQueries({
		@NamedNativeQuery(name = ReservaCotaBolao.NQ_SELECT_BY_SITUACAO_DATA_INCLUSAO,
			query = "SELECT R.NU_RESERVA_COTA_BOLAO, R.MM_RESERVA_COTA_BOLAO, R.NU_PARTICAO, R.CO_BOLAO_RESERVA_COTA, R.NU_TRANSACAO_RESERVA_COTA, R.NU_SITUACAO_RESERVA_COTA_BOLAO, AP.NU_CPF, R.CO_COTA_BOLAO, A.NU_APOSTA "
				+ "FROM LCE.LCETB059_RESERVA_COTA_BOLAO R "
				+ "INNER JOIN LCE.LCETB011_APOSTA A ON A.NU_RESERVA_COTA_BOLAO = R.NU_RESERVA_COTA_BOLAO "
				+ " 	AND A.MM_RESERVA_COTA_BOLAO = R.MM_RESERVA_COTA_BOLAO AND A.NU_PARTICAO_COTA_BOLAO = R.NU_PARTICAO "
				+ "INNER JOIN LCE.LCETB013_COMPRA C ON C.NU_COMPRA = A.NU_COMPRA "
				+ "INNER JOIN LCE.LCETB002_APOSTADOR AP ON AP.NU_APOSTADOR = C.NU_APOSTADOR "
				+ "WHERE R.NU_SITUACAO_RESERVA_COTA_BOLAO = ?1 "
				+ "AND A.TS_INCLUSAO_APOSTA_CARRINHO <= ?2 "
				+ "WITH UR"),

		@NamedNativeQuery(name = ReservaCotaBolao.NQ_SELECT_EM_CANCELAMENTO,
			query = "SELECT R.NU_RESERVA_COTA_BOLAO, R.MM_RESERVA_COTA_BOLAO, R.NU_PARTICAO, R.CO_BOLAO_RESERVA_COTA, R.NU_TRANSACAO_RESERVA_COTA, R.NU_SITUACAO_RESERVA_COTA_BOLAO, AP.NU_CPF, R.CO_COTA_BOLAO, A.NU_APOSTA "
				+ "FROM LCE.LCETB059_RESERVA_COTA_BOLAO R "
				+ "INNER JOIN LCE.LCETB011_APOSTA A ON A.NU_RESERVA_COTA_BOLAO = R.NU_RESERVA_COTA_BOLAO "
				+ " 	AND A.MM_RESERVA_COTA_BOLAO = R.MM_RESERVA_COTA_BOLAO AND A.NU_PARTICAO_COTA_BOLAO = R.NU_PARTICAO "
				+ "INNER JOIN LCE.LCETB013_COMPRA C ON C.NU_COMPRA = A.NU_COMPRA "
				+ "INNER JOIN LCE.LCETB002_APOSTADOR AP ON AP.NU_APOSTADOR = C.NU_APOSTADOR "
				+ "WHERE R.NU_SITUACAO_RESERVA_COTA_BOLAO = ?1 "
				+ "WITH UR"),

		@NamedNativeQuery(name = ReservaCotaBolao.NQ_SELECT_EXPIRADAS_BY_SITUACAO,
			query = "SELECT R.NU_RESERVA_COTA_BOLAO, R.MM_RESERVA_COTA_BOLAO, R.NU_PARTICAO, R.CO_BOLAO_RESERVA_COTA, R.NU_COTA_RESERVADA, R.NU_SITUACAO_RESERVA_COTA_BOLAO, AP.NU_CPF, C.NU_COMPRA, C.NU_SITUACAO_COMPRA "
				+ "FROM LCE.LCETB059_RESERVA_COTA_BOLAO R "
				+ "INNER JOIN LCE.LCETB011_APOSTA A ON A.NU_RESERVA_COTA_BOLAO = R.NU_RESERVA_COTA_BOLAO "
				+ " 	AND A.MM_RESERVA_COTA_BOLAO = R.MM_RESERVA_COTA_BOLAO AND A.NU_PARTICAO_COTA_BOLAO = R.NU_PARTICAO "
				+ "INNER JOIN LCE.LCETB013_COMPRA C ON C.NU_COMPRA = A.NU_COMPRA "
				+ "INNER JOIN LCE.LCETB002_APOSTADOR AP ON AP.NU_APOSTADOR = C.NU_APOSTADOR "
				+ "WHERE R.NU_SITUACAO_RESERVA_COTA_BOLAO = ?1 "
				+ "AND (TIMESTAMP(CONCAT(CHAR(R.DT_EXPRO_RESERVA_COTA_BOLAO), ' ') CONCAT(REPLACE(CHAR(R.HH_EXPRO_RESERVA_COTA_BOLAO), '.', ':')))) <= CURRENT_TIMESTAMP "
				+ "WITH UR"),

		@NamedNativeQuery(name = ReservaCotaBolao.NQ_UPDATE_SITUACAO_BY_ID,
			query = "UPDATE LCE.LCETB059_RESERVA_COTA_BOLAO SET NU_SITUACAO_RESERVA_COTA_BOLAO = ?1, TS_ALTERACAO_SITUACAO = CURRENT_TIMESTAMP WHERE NU_RESERVA_COTA_BOLAO = ?2 "
				+ " AND MM_RESERVA_COTA_BOLAO = ?3 AND NU_PARTICAO = ?4"),

		@NamedNativeQuery(name = ReservaCotaBolao.NQ_SELECT_EM_PAGAMENTO_BY_DATA_CORTE_ALTERACAO_SITUACAO_WITH_UR,
			query = "SELECT R.NU_RESERVA_COTA_BOLAO, R.MM_RESERVA_COTA_BOLAO, R.NU_PARTICAO, R.CO_BOLAO_RESERVA_COTA, R.CO_COTA_BOLAO, "
				+ "R.NU_COTA_RESERVADA, R.NU_TRANSACAO_RESERVA_COTA, R.NU_SITUACAO_RESERVA_COTA_BOLAO, R.VR_CUSTEIO_COTA, R.VR_TARIFA_CUSTEIO_COTA, "
				+ "A.NU_APOSTA, A.NU_MODALIDADE_JOGO, C.NU_COMPRA, C.NU_SITUACAO_COMPRA, C.NU_MEIO_PAGAMENTO, AP.NU_APOSTADOR, AP.NU_MUNICIPIO_IBGE, AP.NU_UF_IBGE_L99, AP.NU_CPF "
				+ "FROM LCE.LCETB059_RESERVA_COTA_BOLAO R "
				+ "INNER JOIN LCE.LCETB011_APOSTA A ON A.NU_RESERVA_COTA_BOLAO = R.NU_RESERVA_COTA_BOLAO "
				+ " 	AND A.MM_RESERVA_COTA_BOLAO = R.MM_RESERVA_COTA_BOLAO AND A.NU_PARTICAO_COTA_BOLAO = R.NU_PARTICAO "
				+ "INNER JOIN LCE.LCETB013_COMPRA C ON C.NU_COMPRA = A.NU_COMPRA "
				+ "INNER JOIN LCE.LCETB002_APOSTADOR AP ON AP.NU_APOSTADOR = C.NU_APOSTADOR "
				+ "WHERE R.NU_SITUACAO_RESERVA_COTA_BOLAO = ?1 "
				+ "AND R.TS_ALTERACAO_SITUACAO <= ?2 "
				+ "WITH UR"),

		@NamedNativeQuery(name = ReservaCotaBolao.NQ_SELECT_BY_DT_FINALIZACAO_PROCESSAMENTO_SITUACAO_RESERVA,
			query = "SELECT DISTINCT RCB.NU_CANAL_DISTRIBUICAO FROM LCE.LCETB059_RESERVA_COTA_BOLAO RCB "
				+ "INNER JOIN LCE.LCETB011_APOSTA A ON A.NU_RESERVA_COTA_BOLAO = RCB.NU_RESERVA_COTA_BOLAO "
				+ "		AND A.NU_MES <= ?1 "
				+ "INNER JOIN LCE.LCETB009_APOSTA_COMPRADA AC ON AC.NU_APOSTA = A.NU_APOSTA "
				+ "		AND AC.NU_MES <= ?1 AND AC.DT_FINALIZACAO_PROCESSAMENTO = ?2 "
				+ "WHERE RCB.MM_RESERVA_COTA_BOLAO <= ?1 "
				+ "AND RCB.NU_SITUACAO_RESERVA_COTA_BOLAO = ?3 "
				+ "ORDER BY RCB.NU_CANAL_DISTRIBUICAO ASC "),

		@NamedNativeQuery(name = ReservaCotaBolao.NQ_RETORNAR_COTAS_CONTABILIZADAS_PARA_CONFIRMADAS_BY_MES_DT_FINALIZACAO_PROCESSAMENTO,
			query = "UPDATE LCE.LCETB059_RESERVA_COTA_BOLAO SET NU_SITUACAO_RESERVA_COTA_BOLAO = ?4, TS_ALTERACAO_SITUACAO = CURRENT_TIMESTAMP "
				+ "WHERE NU_RESERVA_COTA_BOLAO IN ( "
				+ "		SELECT RCB.NU_RESERVA_COTA_BOLAO "
				+ "		FROM LCE.LCETB059_RESERVA_COTA_BOLAO RCB  "
				+ "		INNER JOIN LCE.LCETB011_APOSTA A ON A.NU_RESERVA_COTA_BOLAO = RCB.NU_RESERVA_COTA_BOLAO "
				+ "			AND A.NU_MES <= ?1 "
				+ "		INNER JOIN LCE.LCETB009_APOSTA_COMPRADA AC ON AC.NU_APOSTA = A.NU_APOSTA "
				+ "			AND AC.NU_MES <= ?1 AND AC.DT_FINALIZACAO_PROCESSAMENTO = ?2 "
				+ "		WHERE RCB.MM_RESERVA_COTA_BOLAO <= ?1 AND RCB.NU_SITUACAO_RESERVA_COTA_BOLAO = ?3"
				+ ")"),

		@NamedNativeQuery(name = ReservaCotaBolao.NQ_ATUALIZAR_COTAS_CONFIRMADAS_PARA_CONTABILIZADAS_BY_LOTERICA_MES_SITUACAO,
			query = "UPDATE LCE.LCETB059_RESERVA_COTA_BOLAO SET NU_SITUACAO_RESERVA_COTA_BOLAO = ?5, TS_ALTERACAO_SITUACAO = CURRENT_TIMESTAMP "
				+ "WHERE NU_RESERVA_COTA_BOLAO IN ( "
				+ "		SELECT RCB.NU_RESERVA_COTA_BOLAO "
				+ "		FROM LCE.LCETB059_RESERVA_COTA_BOLAO RCB  "
				+ "		INNER JOIN LCE.LCETB011_APOSTA A ON A.NU_RESERVA_COTA_BOLAO = RCB.NU_RESERVA_COTA_BOLAO "
				+ "			AND A.NU_MES <= ?1 "
				+ "		INNER JOIN LCE.LCETB009_APOSTA_COMPRADA AC ON AC.NU_APOSTA = A.NU_APOSTA "
				+ "			AND AC.NU_MES <= ?1 AND AC.DT_FINALIZACAO_PROCESSAMENTO = ?2 "
				+ "		WHERE RCB.MM_RESERVA_COTA_BOLAO <= ?1 AND RCB.NU_SITUACAO_RESERVA_COTA_BOLAO = ?3 AND NU_CANAL_DISTRIBUICAO = ?4"
				+ ")"),

		@NamedNativeQuery(name = ReservaCotaBolao.NQ_INSERT_RESERVA_COTA_BOLAO,
			query = "INSERT INTO LCE.LCETB059_RESERVA_COTA_BOLAO (MM_RESERVA_COTA_BOLAO, NU_PARTICAO, NU_SITUACAO_RESERVA_COTA_BOLAO, "
				+ "AA_RESERVA_COTA_BOLAO, CO_BOLAO_RESERVA_COTA, NU_TRANSACAO_RESERVA_COTA, TS_ALTERACAO_SITUACAO) "
				+ "VALUES(?1, ?2, ?3, ?4, ?5, ?6, CURRENT_TIMESTAMP)")

})
public class ReservaCotaBolao extends AbstractEntidade<ReservaCotaBolaoPK> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_SELECT_BY_SITUACAO = "ReservaCotaBolao.findBySituacao";
	public static final String NQ_SELECT_BY_SITUACAO_DATA_INCLUSAO = "ReservaCotaBolao.findBySituacaoDataInclusao";
	public static final String NQ_SELECT_EM_CANCELAMENTO = "ReservaCotaBolao.findEmCancelamento";
	public static final String NQ_SELECT_EXPIRADAS_BY_SITUACAO = "ReservaCotaBolao.findExpiradasBySituacao";
	public static final String NQ_DELETE_BY_ID = "ReservaCotaBolao.deleteById";
	public static final String NQ_UPDATE_SITUACAO_BY_ID = "ReservaCotaBolao.updateSituacaoById";
	public static final String NQ_SELECT_EM_PAGAMENTO_BY_DATA_CORTE_ALTERACAO_SITUACAO_WITH_UR = "ReservaCotaBolao.findEmPagamentoByDataCorteAlteracaoSituacaoWithUr";
	public static final String NQ_SELECT_BY_DT_FINALIZACAO_PROCESSAMENTO_SITUACAO_RESERVA = "ReservaCotaBolao.findByDtFinalizacaoProcessamentoSituacaoReserva";
	public static final String NQ_RETORNAR_COTAS_CONTABILIZADAS_PARA_CONFIRMADAS_BY_MES_DT_FINALIZACAO_PROCESSAMENTO = "ReservaCotaBolao.retornarCotasContabilizadasParaConfirmadasByDtFinalizacaoProcessamentoMes";
	public static final String NQ_ATUALIZAR_COTAS_CONFIRMADAS_PARA_CONTABILIZADAS_BY_LOTERICA_MES_SITUACAO = "ReservaCotaBolao.atualizarCotasConfirmadasParaContabilizadasByLotericaMesSituacao";
	public static final String NQ_SELECT_BY_COD_BOLAO_COD_COTA_CPF = "ReservaCotaBolao.findByCodBolaoCodCotaCpf";
	public static final String NQ_INSERT_RESERVA_COTA_BOLAO = "ReservaCotaBolao.insertReservaCotaBolao";
	public static final String NQ_SELECT_BY_NSU_DATA_INCLUSAO_SITUACAO = "ReservaCotaBolao.findByNsuDataInclusaoSituacao";
	public static final String NQ_SELECT_BY_ID_APOSTA = "ReservaCotaBolao.findByIdAposta";
	
	@EmbeddedId
	private ReservaCotaBolaoPK id;

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
	private Aposta<?> aposta;

	@Transient
	private String cpfApostador;

	@Transient
	private Compra compra;

	public ReservaCotaBolao() {
	}

	public ReservaCotaBolao(ReservaCotaBolaoPK pk) {
		this.id = pk;
	}

	public ReservaCotaBolao(ReservaCotaBolaoPK pk, String codBolao, Long nsu, String cpfApostador) {
		this.id = pk;
		this.codBolao = codBolao;
		this.nsu = nsu;
		this.cpfApostador = cpfApostador;
	}

	public ReservaCotaBolao(ReservaCotaBolaoPK pk, String codBolao, Long nsu, SituacaoReservaCotaBolao situacao, String cpfApostador, String codCota, Aposta<?> aposta) {
		this.id = pk;
		this.codBolao = codBolao;
		this.nsu = nsu;
		this.situacao = situacao;
		this.cpfApostador = cpfApostador;
		this.codCota = codCota;
		this.aposta = aposta;
	}

	public ReservaCotaBolao(ReservaCotaBolaoPK pk, String codBolao, Integer numCotaReservada, SituacaoReservaCotaBolao situacao, String cpfApostador,
		Compra compra) {
		this.id = pk;
		this.codBolao = codBolao;
		this.numeroCotaReservada = numCotaReservada;
		this.situacao = situacao;
		this.cpfApostador = cpfApostador;
		this.compra = compra;
	}

	public ReservaCotaBolao(ReservaCotaBolaoPK pk, String codBolao, String codCota, Integer numCotaReservada, Long nsu,
		SituacaoReservaCotaBolao situacao,
		Decimal valorCusteio, Decimal valorTarifaCusteio, Long idAposta, Integer codModalidade,
		Compra compra) {
		this.id = pk;
		this.codBolao = codBolao;
		this.codCota = codCota;
		this.numeroCotaReservada = numCotaReservada;
		this.nsu = nsu;
		this.situacao = situacao;
		this.valorCotaCusteio = valorCusteio;
		this.valorTarifaCusteio = valorTarifaCusteio;

		Aposta<?> aposta = ApostaUtil.createAposta(Modalidade.getByCodigo(codModalidade));
		this.aposta = aposta;
		this.getAposta().setId(idAposta);
		this.getAposta().setCompra(compra);
		this.cpfApostador = compra.getApostador().getCpf().getCpfSemMascara();
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

	public ReservaCotaBolaoPK getId() {
		return id;
	}

	public void setId(ReservaCotaBolaoPK id) {
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

	public Aposta<?> getAposta() {
		return aposta;
	}

	public void setAposta(Aposta<?> aposta) {
		this.aposta = aposta;
	}

	public String getCpfApostador() {
		return cpfApostador;
	}

	public void setCpfApostador(String cpfApostador) {
		this.cpfApostador = cpfApostador;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	@Override
	public String toString() {
		return "ReservaCotaBolao [id=" + id + "]";
	}
}
