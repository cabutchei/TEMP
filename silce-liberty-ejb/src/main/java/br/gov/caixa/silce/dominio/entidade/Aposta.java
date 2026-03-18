package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.NSB;
import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.AbstractParametroJogo;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.jogos.IndicadorSurpresinha;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.Palpites;
import br.gov.caixa.silce.dominio.jogos.TipoConcurso;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler;
import br.gov.caixa.silce.dominio.util.ApostaUtil;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;

/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 * @author c127237 - Rinaldo Pitzer Junior
 */
@Entity
@Table(name = "LCETB011_APOSTA", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = Aposta.NQ_SELECT_BY_APOSTADOR_SITUACAOCOMPRA,
			query = "Select distinct aposta From Aposta aposta "
				+ "join fetch aposta.compra "
				+ "left join fetch aposta.comboAposta "
				+ "left join fetch aposta.reservaCotaBolao "
				+ "left join fetch aposta.reservaCotaBolao.loterica "
				+ "where aposta.compra.apostador.id = ?1 and aposta.compra.situacao.id = ?2 and (aposta.reservaCotaBolao is null or aposta.reservaCotaBolao.situacao.id <> ?3) "
				+ "ORDER BY aposta.dataInclusao DESC"),

		@NamedQuery(name = Aposta.NQ_SELECT_BY_SITUACAOCOMPRA_APOSTADOR_MODALIDADES,
			query = "Select aposta From Aposta aposta join fetch aposta.compra where aposta.compra.situacao.id = ?1"
				+ " and aposta.compra.apostador.id = ?2 and aposta.modalidade in (?3)"),
	@NamedQuery(
		name = Aposta.NQ_SELECT_BY_COMPRA,
		query = "Select aposta From Aposta aposta join fetch aposta.compra where aposta.compra.id = ?1 "
				+ "and (aposta.reservaCotaBolao is null or (aposta.reservaCotaBolao.situacao.id <> ?2 and aposta.reservaCotaBolao.situacao.id <> ?3)) "),
	@NamedQuery(
			name = Aposta.NQ_SELECT_BY_COMPRA_ORDERBY_ID_APOSTA, query = "Select aposta From Aposta aposta where aposta.compra.id = ?1 ORDER BY aposta.id ASC"),
	@NamedQuery(
			name = Aposta.NQ_SELECT_BY_COMPRA_APOSTADOR,
		query = "Select aposta From Aposta aposta join fetch aposta.compra where aposta.compra.id = ?1 and aposta.compra.apostador.id = ?2 "
				+ "and (aposta.reservaCotaBolao is null or aposta.reservaCotaBolao.situacao.id <> ?3)"),
	@NamedQuery(
		name = Aposta.NQ_SELECT_BY_APOSTADOR_MODALIDADES,
			query = "Select aposta From Aposta aposta join fetch aposta.compra where aposta.compra.situacao.id in (6, 7, 8, 9)"
				+ " and aposta.modalidade = ?1 and aposta.concursoAlvo = ?2 and aposta.compra.apostador.id = ?3"),

		@NamedQuery(name = Aposta.NQ_SELECT_ORIGINAIS_BY_COMPRA,
			query = "Select distinct aposta From Aposta aposta "
				+ "join fetch aposta.apostaComprada "
				+ "join fetch aposta.compra "
				+ "left join fetch aposta.reservaCotaBolao "
				+ "left join fetch aposta.reservaCotaBolao.loterica "
				+ "where aposta.compra.id = ?1 and aposta.compra.apostador.id = ?2 and aposta.apostaComprada.apostaTroca = false "
				+ "and (aposta.reservaCotaBolao is null or aposta.reservaCotaBolao.situacao.id <> ?3)"),

		@NamedQuery(name = Aposta.NQ_SELECT_ORIGINAIS_BY_COMPRA_COM_PREMIO,
			query = "Select distinct aposta From Aposta aposta"
				+ " left join fetch aposta.apostaComprada"
				+ " left join fetch aposta.apostaComprada.premio"
				+ " left join fetch aposta.apostaComprada.premio.meioPagamento"
				+ " left join fetch aposta.reservaCotaBolao"
				+ " left join fetch aposta.reservaCotaBolao.loterica"
				+ " join fetch aposta.compra"
				+ " join fetch aposta.compra.meioPagamento"
				+ " where aposta.compra.id = ?1 and aposta.compra.apostador.id = ?2"),

		@NamedQuery(name = Aposta.NQ_COUNT_BY_CONCURSOINICIAL_SITUACAO,
			query = "Select count(entidade) From Aposta entidade join fetch entidade.apostaComprada"
				+ " where entidade.modalidade = ?1 and entidade.apostaComprada.concursoInicial = ?2 and entidade.apostaComprada.situacao.id = ?3"
				+ " and entidade.indicadorBolao = false"),

		@NamedQuery(name = Aposta.NQ_COUNT_BY_CONCURSOINICIAL_SITUACAO_WITH_UR,
			query = "Select count(entidade) From Aposta entidade join fetch entidade.apostaComprada"
				+ " where entidade.modalidade = ?1 and entidade.apostaComprada.concursoInicial = ?2 and entidade.apostaComprada.situacao.id = ?3"
				+ " and entidade.indicadorBolao = false",
			hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }),

		@NamedQuery(name = Aposta.NQ_SELECT_BY_MODALIDADE,
			query = "Select aposta From Aposta aposta join fetch aposta.compra Where aposta.compra.id= ?1 and aposta.modalidade = ?2"),

		@NamedQuery(name = Aposta.NQ_COUNT_BY_SITUACAO,
			query = "Select count(entidade) From Aposta entidade join fetch entidade.apostaComprada"
				+ " join fetch entidade.compra Where entidade.compra.id= ?1 and entidade.apostaComprada.situacao.id = ?2"),

		@NamedQuery(name = Aposta.NQ_SELECT_BY_ID_AND_APOSTADOR,
			query = "Select distinct aposta From Aposta aposta "
				+ "join fetch aposta.compra "
				+ "left join fetch aposta.reservaCotaBolao "
				+ "left join fetch aposta.reservaCotaBolao.loterica "
				+ "where aposta.id = ?1 and aposta.compra.apostador.id = ?2"),

		@NamedQuery(name = Aposta.NQ_FIND_BY_APOSTADOR_MODALIDADE_IDS,
			query = "Select distinct entidade From Aposta entidade "
				+ "join fetch entidade.apostaComprada "
				+ "left join fetch entidade.comboAposta "
				+ "left join fetch entidade.reservaCotaBolao "
				+ "where (entidade.compra.apostador.id = ?1 and entidade.modalidade = ?2 and entidade.compra.dataInicioCompra >= ?3 and entidade.compra.dataInicioCompra <= ?4) "
				+ "and (entidade.apostaComprada.situacao.id in (?5) or entidade.id in (?6)) "
				+ "order by entidade.modalidade asc, entidade.apostaComprada.concursoInicial asc, entidade.apostaComprada.id asc"),

		@NamedQuery(name = Aposta.NQ_FIND_BY_APOSTADOR_IDS,
			query = "Select distinct entidade From Aposta entidade "
				+ "join fetch entidade.apostaComprada "
				+ "left join fetch entidade.comboAposta "
				+ "left join fetch entidade.reservaCotaBolao "
				+ "left join fetch entidade.reservaCotaBolao.loterica "
				+ "where (entidade.compra.apostador.id = ?1 and entidade.compra.dataInicioCompra >= ?2 and entidade.compra.dataInicioCompra <= ?3) "
				+ "and (entidade.apostaComprada.situacao.id in (?4) or entidade.id in (?5)) "
				+ "order by entidade.modalidade asc, entidade.apostaComprada.concursoInicial asc, entidade.apostaComprada.id asc"),

		@NamedQuery(name = Aposta.NQ_FIND_BY_APOSTADOR_MODALIDADE_IDS_ORDER_ESPECIAL,
			query = "Select distinct entidade From Aposta entidade "
				+ "join fetch entidade.apostaComprada "
				+ "left join fetch entidade.comboAposta "
				+ "left join fetch entidade.reservaCotaBolao "
				+ "left join fetch entidade.reservaCotaBolao.loterica "
				+ "where (entidade.compra.apostador.id = ?1 and entidade.modalidade = ?2 and entidade.compra.dataInicioCompra >= ?3 and entidade.compra.dataInicioCompra <= ?4) "
				+ "and (entidade.apostaComprada.situacao.id in (?5) or entidade.id in (?6)) "
				+ "order by entidade.tipoConcurso desc, entidade.modalidade asc, entidade.apostaComprada.concursoInicial asc, entidade.apostaComprada.id asc"),

		@NamedQuery(name = Aposta.NQ_FIND_BY_APOSTADOR_IDS_ORDER_ESPECIAL,
			query = "Select distinct entidade From Aposta entidade "
				+ "join fetch entidade.apostaComprada "
				+ "left join fetch entidade.comboAposta "
				+ "left join fetch entidade.reservaCotaBolao "
				+ "left join fetch entidade.reservaCotaBolao.loterica "
				+ "where (entidade.compra.apostador.id = ?1 and entidade.compra.dataInicioCompra >= ?2 and entidade.compra.dataInicioCompra <= ?3) "
				+ "and (entidade.apostaComprada.situacao.id in (?4) or entidade.id in (?5)) "
				+ "order by entidade.tipoConcurso desc, entidade.modalidade asc, entidade.apostaComprada.concursoInicial asc, entidade.apostaComprada.id asc"),

		@NamedQuery(name = Aposta.NQ_DELETE_BY_CARRINHO,
			query = "Delete from Aposta aposta where aposta.compra.id = ?1 and aposta.compra.apostador.id = ?2 and aposta.reservaCotaBolao is null"),

		@NamedQuery(name = Aposta.NQ_DELETE_ESPELHOS_BY_CARRINHO,
			query = "Delete from Aposta aposta where aposta.compra.id = ?1 and aposta.compra.apostador.id = ?2 and aposta.apostaOriginalEspelho is not null"),

		@NamedQuery(name = Aposta.NQ_SUM_BY_APOSTADOR_DATA_SITUACAO,
			query = "Select SUM(entidade.valor)+ 0.0 From Aposta entidade where entidade.compra.apostador.id = ?1"
				+ " and entidade.compra.dataInicioCompra = ?2 and entidade.apostaComprada.situacao.id in (?3)"),

		@NamedQuery(name = Aposta.NQ_SELECT_ESPELHO_BY_ORIGINAL,
			query = "Select entidade From Aposta entidade where entidade.apostaOriginalEspelho.id = ?1"),

		@NamedQuery(name = Aposta.NQ_COUNT_CARRINHO,
			query = "Select count(entidade) From Aposta entidade where entidade.compra.situacao.id = ?1 and entidade.compra.apostador.id = ?2"),

		@NamedQuery(name = Aposta.NQ_COUNT_COTAS_CARRINHO, query = "Select count(entidade) From Aposta entidade "
			+ "where entidade.compra.situacao.id = ?1 and entidade.compra.apostador.id = ?2 and entidade.indicadorBolao = true"),

		@NamedQuery(name = Aposta.NQ_COUNT_BY_COMBO_APOSTA,
			query = "SELECT count(aposta.comboAposta) FROM Aposta aposta JOIN FETCH aposta.comboAposta"
				+ " WHERE aposta.comboAposta.id = ?1"),

		@NamedQuery(name = Aposta.NQ_SELECT_ID_COMBOS_BY_COMPRA,
			query = "SELECT distinct(aposta.comboAposta.id) FROM Aposta aposta "
				+ " WHERE aposta.compra.id = ?1 and aposta.compra.apostador.id = ?2 and aposta.comboAposta is not null "),

		@NamedQuery(name = Aposta.NQ_FIND_APOSTAS_BY_APOSTADOR_COMBO,
			query = "SELECT aposta FROM Aposta aposta where aposta.compra.apostador.id = ?1 and aposta.comboAposta.id = ?2"),

		 @NamedQuery(name = Aposta.NQ_COUNT_RESERVA_COTAS_PENDENTES_BY_CONCURSOINICIAL_SITUACAO,
			 query = "Select count(entidade) From Aposta entidade "
				+ "join fetch entidade.reservaCotaBolao "
				+ " where entidade.modalidade = ?1 and entidade.concursoAlvo = ?2 and entidade.reservaCotaBolao.situacao.id = ?3"),
		
		 @NamedQuery(name = Aposta.NQ_COUNT_RESERVA_COTAS_PENDENTES_BY_CONCURSOINICIAL_SITUACAO_WITH_UR,
			query = "Select count(entidade) From Aposta entidade "
				+ "join fetch entidade.reservaCotaBolao "
				+ " where entidade.modalidade = ?1 and entidade.concursoAlvo = ?2 and entidade.reservaCotaBolao.situacao.id = ?3",
			hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }),

		@NamedQuery(name = Aposta.NQ_FIND_APOSTAS_BOLAO_BY_COMPRA,
			query = "SELECT aposta FROM Aposta aposta "
				+ "join fetch aposta.reservaCotaBolao "
				+ "where aposta.compra.id = ?1 and aposta.indicadorBolao = true"),

		@NamedQuery(name = Aposta.NQ_DELETE_BY_RESERVA_COTA_BOLAO,
			query = "Delete from Aposta aposta where aposta.reservaCotaBolao.id.id = ?1 and aposta.reservaCotaBolao.id.mes = ?2 and aposta.reservaCotaBolao.id.particao = ?3"),

		@NamedQuery(name = Aposta.NQ_SELECT_BY_CONCURSO_MODALIDADE_SITUACAO_RESERVA_COTA_BOLAO_WITH_UR,
			query = "Select distinct a From Aposta a "
				+ "left join fetch a.apostaComprada "
				+ "join fetch a.compra "
				+ "join fetch a.compra.situacao "
				+ "join fetch a.reservaCotaBolao "
				+ "left join fetch a.reservaCotaBolao.loterica "
				+ "where a.modalidade = ?1 and a.concursoAlvo = ?2 and a.reservaCotaBolao.situacao.id in (?3)",
			hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }),

		@NamedQuery(name = Aposta.NQ_SELECT_BY_COD_BOLAO_COD_COTA,
			query = "Select aposta From Aposta aposta "
				+ "join fetch aposta.reservaCotaBolao "
				+ "join fetch aposta.compra "
				+ "join fetch aposta.compra.apostador "
				+ "join fetch aposta.compra.apostador.municipio "
				+ "join fetch aposta.compra.meioPagamento "
				+ "where aposta.reservaCotaBolao.codBolao = ?1 and aposta.reservaCotaBolao.codCota = ?2"),

		@NamedQuery(name = Aposta.NQ_SELECT_BY_APOSTA, query = "Select aposta From Aposta aposta "
			+ "left join fetch aposta.reservaCotaBolao "
			+ "join fetch aposta.compra "
			+ "join fetch aposta.compra.apostador "
			+ "join fetch aposta.compra.apostador.municipio "
			+ "left join fetch aposta.compra.meioPagamento "
			+ "where aposta.id = ?1"),

		@NamedQuery(name = Aposta.NQ_SELECT_BY_ID_RESERVA_COTA_BOLAO,
			query = "Select aposta From Aposta aposta "
				+ "join fetch aposta.reservaCotaBolao "
				+ "join fetch aposta.compra "
				+ "join fetch aposta.compra.situacao "
				+ "join fetch aposta.compra.apostador "
				+ "where aposta.reservaCotaBolao.id.id = ?1 and aposta.reservaCotaBolao.id.mes = ?2 and aposta.reservaCotaBolao.id.particao = ?3")
})

@NamedNativeQueries({
		@NamedNativeQuery(name = Aposta.NQ_UPDATE_MES_PARTICAO,
			query = "UPDATE LCE.LCETB011_APOSTA aposta SET aposta.NU_MES = ("
				+ "select compra.NU_MES FROM LCE.LCETB013_COMPRA compra where compra.NU_COMPRA = aposta.NU_COMPRA),"
				+ " aposta.NU_PARTICAO = (select compra.NU_PARTICAO FROM LCE.LCETB013_COMPRA compra where compra.NU_COMPRA = aposta.NU_COMPRA)"),

		@NamedNativeQuery(name = Aposta.NQ_COUNT_RESERVA_COTAS_COMPRAS_PIX_PENDENTES_BY_CONCURSOINICIAL_SITUACAO,
			query = "SELECT COUNT(t0.NU_APOSTA) FROM LCE.LCETB011_APOSTA t0 "
				+ "INNER JOIN LCE.LCETB009_APOSTA_COMPRADA t1 ON t0.NU_APOSTA = t1.NU_APOSTA "
				+ "INNER JOIN LCE.LCETB013_COMPRA t2 ON t0.NU_COMPRA = t2.NU_COMPRA "
				+ "WHERE t0.NU_MODALIDADE_JOGO = ?1 "
				+ "AND t1.NU_CONCURSO_INICIAL = ?2 "
				+ "AND t2.NU_SITUACAO_COMPRA = 10 "
				+ "AND t0.IC_COTA_BOLAO = ?3 "
				+ "FETCH FIRST 1 ROWS ONLY "),

		@NamedNativeQuery(name = Aposta.NQ_COUNT_RESERVA_COTAS_COMPRAS_PIX_PENDENTES_BY_CONCURSOINICIAL_SITUACAO_WITH_UR,
			query = "SELECT COUNT(t0.NU_APOSTA) FROM LCE.LCETB011_APOSTA t0 "
				+ "INNER JOIN LCE.LCETB009_APOSTA_COMPRADA t1 ON t0.NU_APOSTA = t1.NU_APOSTA "
				+ "INNER JOIN LCE.LCETB013_COMPRA t2 ON t0.NU_COMPRA = t2.NU_COMPRA "
				+ "WHERE t0.NU_MODALIDADE_JOGO = ?1 "
				+ "AND t1.NU_CONCURSO_INICIAL = ?2 "
				+ "AND t2.NU_SITUACAO_COMPRA = 10 "
				+ "AND t0.IC_COTA_BOLAO = ?3 "
				+ "FETCH FIRST 1 ROWS ONLY WITH UR")

})
@DiscriminatorColumn(name = "NU_MODALIDADE_JOGO", discriminatorType = DiscriminatorType.INTEGER)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Aposta<P extends Palpites<?>> extends AbstractEntidade<Long> {

	public static final String NQ_COUNT_BY_COMBO_APOSTA = "Aposta.countByComboAposta";
	public static final String NQ_UPDATE_MES_PARTICAO = "Aposta.NQ_UPDATE_MES_PARTICAO";
	public static final String NQ_SELECT_BY_APOSTADOR_SITUACAOCOMPRA = "Aposta.consulteByApostadorSituacaoCompra";
	public static final String NQ_SELECT_BY_SITUACAOCOMPRA_APOSTADOR_MODALIDADES = "Aposta.consulteByCompraApostadorModalidade";
	public static final String NQ_SELECT_BY_COMPRA = "Aposta.consulteByCompra";
	public static final String NQ_SELECT_BY_COMPRA_ORDERBY_ID_APOSTA = "Aposta.consulteByCompraOrderByIdAposta";
	public static final String NQ_SELECT_BY_COMPRA_APOSTADOR = "Aposta.consulteByCompraApostador";
	public static final String NQ_SELECT_ORIGINAIS_BY_COMPRA = "Aposta.consulteOriginaisByCompra";
	public static final String NQ_SELECT_ORIGINAIS_BY_COMPRA_COM_PREMIO = "Aposta.consulteOriginaisByCompraComPremio";
	public static final String NQ_SELECT_BY_ID_AND_APOSTADOR = "Aposta.consulteByIdAndApostador";
	public static final String NQ_COUNT_BY_CONCURSOINICIAL_SITUACAO = "Aposta.countApostasByConcursoSituacao";
	public static final String NQ_COUNT_BY_CONCURSOINICIAL_SITUACAO_WITH_UR = "Aposta.countApostasByConcursoSituacaoWithUr";
	public static final String NQ_COUNT_RESERVA_COTAS_PENDENTES_BY_CONCURSOINICIAL_SITUACAO_WITH_UR = "Aposta.countReservaCotasPendentesByConcursoSituacaoWithUr";
	public static final String NQ_COUNT_RESERVA_COTAS_PENDENTES_BY_CONCURSOINICIAL_SITUACAO = "Aposta.countReservaCotasPendentesByConcursoSituacao";
	public static final String NQ_SELECT_BY_MODALIDADE = "Aposta.consulteByCompraModalidadeSituacao";
	public static final String NQ_COUNT_BY_SITUACAO = "Aposta.countBySituacaoApostaComprada";
	public static final String NQ_DELETE_BY_CARRINHO = "Aposta.deleteByCarrinho";
	public static final String NQ_DELETE_ESPELHOS_BY_CARRINHO = "Aposta.NQ_DELETE_ESPELHOS_BY_CARRINHO";
	public static final String NQ_SUM_BY_APOSTADOR_DATA_SITUACAO = "Aposta.NQ_SUM_EFETIVADAS_HOJE_BY_APOSTADOR";
	public static final String NQ_FIND_BY_APOSTADOR_MODALIDADE_IDS = "Aposta.NQ_FIND_BY_APOSTADOR_MODALIDADE_IDS";
	public static final String NQ_FIND_BY_APOSTADOR_IDS = "Aposta.NQ_FIND_BY_APOSTADOR_IDS";
	public static final String NQ_FIND_BY_APOSTADOR_IDS_ORDER_ESPECIAL = "Aposta.NQ_FIND_BY_APOSTADOR_IDS_ORDER_ESPECIAL";
	public static final String NQ_FIND_BY_APOSTADOR_MODALIDADE_IDS_ORDER_ESPECIAL = "Aposta.NQ_FIND_BY_APOSTADOR_MODALIDADE_IDS_ORDER_ESPECIAL";
	public static final String NQ_SELECT_ESPELHO_BY_ORIGINAL = "Aposta.NQ_SELECT_ESPELHO_BY_ORIGINAL";
	public static final String NQ_COUNT_CARRINHO = "Aposta.NQ_COUNT_CARRINHO";
	public static final String NQ_COUNT_COTAS_CARRINHO = "Aposta.NQ_COUNT_COTAS_CARRINHO";
	public static final String NQ_FIND_APOSTAS_BY_APOSTADOR_COMBO = "Aposta.NQ_FIND_APSOTAS_BY_APOSTADOR_COMBO";
	public static final String NQ_SELECT_ID_COMBOS_BY_COMPRA = "Aposta.NQ_SELECT_ID_COMBOS_BY_COMPRA";
	public static final String NQ_SELECT_BY_APOSTADOR_MODALIDADES = "Aposta.NQ_SELECT_BY_APOSTADOR_MODALIDADES";
	public static final String NQ_COUNT_BY_CONCURSOINICIAL_SITUACAO_COMPRA = "Aposta.countApostasByConcursoSituacaoCompra";
	public static final String NQ_COUNT_BY_CONCURSOINICIAL_SITUACAO_COMPRA_WITH_UR = "Aposta.countApostasByConcursoSituacaoCompraWithUr";
	public static final String NQ_FIND_APOSTAS_BOLAO_BY_COMPRA = "Aposta.NQ_FIND_APOSTAS_BOLAO_BY_COMPRA";
	public static final String NQ_DELETE_BY_RESERVA_COTA_BOLAO = "Aposta.deleteByReservaCotaBolao";
	public static final String NQ_SELECT_BY_CONCURSO_MODALIDADE_SITUACAO_RESERVA_COTA_BOLAO_WITH_UR = "Aposta.selectByConcursoModalidadeSituacaoReservaCotaBolaoWithUr";
	public static final String NQ_COUNT_RESERVA_COTAS_COMPRAS_PIX_PENDENTES_BY_CONCURSOINICIAL_SITUACAO_WITH_UR = "Aposta.NQ_COUNT_RESERVA_COTAS_COMPRAS_PIX_PENDENTES_BY_CONCURSOINICIAL_SITUACAO_COMPRA_WITH_UR";
	public static final String NQ_COUNT_RESERVA_COTAS_COMPRAS_PIX_PENDENTES_BY_CONCURSOINICIAL_SITUACAO = "Aposta.NQ_COUNT_RESERVA_COTAS_COMPRAS_PIX_PENDENTES_BY_CONCURSOINICIAL_SITUACAO_COMPRA";
	public static final String NQ_SELECT_BY_COD_BOLAO_COD_COTA = "Aposta.findByCodBolaoCodCota";
	public static final String NQ_SELECT_BY_APOSTA = "Aposta.findByAposta";
	public static final String NQ_SELECT_BY_ID_RESERVA_COTA_BOLAO = "Aposta.findByIdReservaCotaBolao";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_APOSTA")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "NU_COMPRA", referencedColumnName = "NU_COMPRA")
	private Compra compra;

	@Column(name = "NU_MODALIDADE_JOGO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Modalidade modalidade;

	@Column(name = "IC_TIPO_CONCURSO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private TipoConcurso tipoConcurso;

	@Column(name = "IC_SURPRESINHA")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private IndicadorSurpresinha indicadorSurpresinha = IndicadorSurpresinha.NAO_SURPRESINHA;

	@Column(name = "VR_APOSTA")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valor;

	@OneToOne(mappedBy = "aposta", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private ApostaComprada apostaComprada;

	@Column(name = "NU_PARTICAO")
	private Long particao;

	@Column(name = "NU_CONCURSO_ALVO")
	private Integer concursoAlvo;

	@Column(name = "NU_MES")
	private Long mes;

	@Column(name = "TS_INCLUSAO_APOSTA_CARRINHO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataInclusao;

	@Column(name = "IC_SUBCANAL_APOSTA")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Subcanal subcanalCarrinhoAposta;

	@Transient
	private boolean premiada = false;

	@Transient
	private boolean sorteada = true;

	@Transient
	private boolean sorteadaPrimeiroConcurso = false;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_VINCULO_APOSTA_ESPELHO", referencedColumnName = "NU_APOSTA")
	private ApostaLotomania apostaOriginalEspelho;

	// Criado para atendimento de demanda no silce na nuvem
	@Column(name = "NU_VINCULO_APOSTA_ESPELHO", insertable = false, updatable = false)
	private Long apostaOriginalLotomania;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "NU_COMBO_APOSTA", referencedColumnName = "NU_COMBO_APOSTA")
	private ComboAposta comboAposta;

	@Column(name = "NU_COMBO_APOSTA", insertable = false, updatable = false)
	private Long nuCombo;

	@Column(name = "IC_COTA_BOLAO")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean indicadorBolao;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, optional = true)
	@JoinColumns({
			@JoinColumn(name = "NU_RESERVA_COTA_BOLAO", referencedColumnName = "NU_RESERVA_COTA_BOLAO"),
			@JoinColumn(name = "MM_RESERVA_COTA_BOLAO", referencedColumnName = "MM_RESERVA_COTA_BOLAO"),
			@JoinColumn(name = "NU_PARTICAO_COTA_BOLAO", referencedColumnName = "NU_PARTICAO")
	})
	private ReservaCotaBolao reservaCotaBolao;

	public ComboAposta getComboAposta() {
		return comboAposta;
	}

	public void setComboAposta(ComboAposta comboAposta) {
		this.comboAposta = comboAposta;
	}

	/**
	 * Este premio é atributo transient que será populado apenas na consulta ao prêmio da aposta.
	 */
	@Transient
	private Premio premioConsulta;

	/**
	 * vazio devido ao jpa
	 */
	protected Aposta() {
		// vazio devido ao jpa
	}

	public Aposta(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public String getDescModalidade() {
		if (getModalidade() == null) {
			return null;
		}
		return getModalidade().getDescricao(getTipoConcurso());
	}

	public TipoConcurso getTipoConcurso() {
		return tipoConcurso;
	}

	public void setTipoConcurso(TipoConcurso tipoConcurso) {
		this.tipoConcurso = tipoConcurso;
	}

	public abstract P getPalpites();

	public Decimal getValor() {
		return valor;
	}

	public void setValor(Decimal valor) {
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
		if (compra == null) {
			setParticao(null);
		} else {
			setParticao(compra.getParticao());
			setMes(compra.getMes());
		}
	}

	public ApostaComprada getApostaOriginal() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getApostaOriginal();
	}

	public void setApostaOriginal(ApostaComprada apostaOriginal) {
		initApostaOriginal();
		this.apostaComprada.setApostaOriginal(apostaOriginal);
	}

	public Long getNsuTransacao() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getNsuTransacao();
	}

	public void setNsuTransacao(Long nsuTransacao) {
		initApostaOriginal();
		apostaComprada.setNsuTransacao(nsuTransacao);
	}

	public Integer getConcursoInicial() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getConcursoInicial();
	}

	public void setConcursoInicial(Integer concursoInicial) {
		initApostaOriginal();
		apostaComprada.setConcursoInicial(concursoInicial);
	}

	public Boolean getApostaTroca() {
		if (apostaComprada == null) {
			return Boolean.FALSE;
		}
		return apostaComprada.getApostaTroca();
	}

	public Boolean isApostaTroca() {
		return getApostaTroca();
	}

	public void setApostaTroca(Boolean apostaTroca) {
		initApostaOriginal();
		apostaComprada.setApostaTroca(apostaTroca);
	}

	public Premio getPremio() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getPremio();
	}

	public void setPremio(Premio premio) {
		initApostaOriginal();
		apostaComprada.setPremio(premio);
	}

	public SituacaoAposta getSituacao() {
		if (getApostaComprada() == null) {
			return null;
		}
		return apostaComprada.getSituacao();
	}

	public void setSituacao(SituacaoAposta situacao) {
		initApostaOriginal();
		apostaComprada.setSituacao(situacao);
	}

	public Data getDataUltimaSituacao() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getDataUltimaSituacao();
	}

	public void setDataUltimaSituacao(Data dataUltimaSituacao) {
		initApostaOriginal();
		apostaComprada.setDataUltimaSituacao(dataUltimaSituacao);
	}

	public NSB getNsb() {
		if (getApostaComprada() == null) {
			return null;
		}
		return apostaComprada.getNsb();
	}

	public void setNsb(NSB nsb) {
		initApostaOriginal();
		apostaComprada.setNsb(nsb);
	}

	private void initApostaOriginal() {
		if (apostaComprada == null) {
			apostaComprada = new ApostaComprada();
			apostaComprada.setAposta(this);
		}
	}

	public Data getDataEnvioSISPL() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getDataEnvioSISPL();
	}

	public void setDataEnvioSISPL(Data dataEnvioSISPL) {
		initApostaOriginal();
		apostaComprada.setDataEnvioSISPL(dataEnvioSISPL);
	}

	public Hora getHoraEnvioSISPL() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getHoraEnvioSISPL();
	}

	public void setHoraEnvioSISPL(Hora horaEnvioSISPL) {
		initApostaOriginal();
		apostaComprada.setHoraEnvioSISPL(horaEnvioSISPL);
	}

	public Data getDataFinalizacaoProcessamento() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getDataFinalizacaoProcessamento();
	}

	public void setDataFinalizacaoProcessamento(Data dataFinalizacaoProcessamento) {
		initApostaOriginal();
		apostaComprada.setDataFinalizacaoProcessamento(dataFinalizacaoProcessamento);
	}

	public Hora getHoraFinalizacaoProcessamento() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getHoraFinalizacaoProcessamento();
	}

	public void setHoraFinalizacaoProcessamento(Hora horaFinalizacaoProcessamento) {
		initApostaOriginal();
		apostaComprada.setHoraFinalizacaoProcessamento(horaFinalizacaoProcessamento);
	}

	public boolean isEsportivo() {
		return getModalidade().isEsportivo();
	}

	public boolean isNumerico() {
		return getModalidade().isNumerico();
	}

	public boolean isEspecial() {
		return tipoConcurso.isEspecial();
	}

	public IndicadorSurpresinha getIndicadorSurpresinha() {
		return indicadorSurpresinha;
	}

	public void setIndicadorSurpresinha(IndicadorSurpresinha indicadorSurpresinha) {
		this.indicadorSurpresinha = indicadorSurpresinha;
	}

	public boolean isSurpresinha() {
		return indicadorSurpresinha.isSurpresinha();
	}

	public ApostaComprada getApostaComprada() {
		return apostaComprada;
	}

	public void setApostaComprada(ApostaComprada apostaComprada) {
		this.apostaComprada = apostaComprada;
	}

	public Long getParticao() {
		return particao;
	}

	public void setParticao(Long particao) {
		this.particao = particao;
	}

	public boolean isPremiada() {
		return premiada;
	}

	public void setPremiada(boolean premiada) {
		this.premiada = premiada;
	}

	public Premio getPremioConsulta() {
		return premioConsulta;
	}

	public void setPremioConsulta(Premio premioConsulta) {
		this.premioConsulta = premioConsulta;
	}

	public AbstractApostaVO<?> getVO() {
		AbstractApostaVO<?> vo = ApostaUtil.createApostaVO(getModalidade());
		vo.setIndicadorBolao(indicadorBolao);
		vo.setIndicadorSurpresinha(indicadorSurpresinha);
		vo.setTipoConcurso(tipoConcurso);
		vo.setValor(valor);
		vo.setId(id);
		if (indicadorBolao) {
			vo.setIdReservaCotaBolao(reservaCotaBolao.getId().getId());
			vo.setMesReservaCotaBolao(reservaCotaBolao.getId().getMes());
			vo.setNuParticaoReservaCotaBolao(reservaCotaBolao.getId().getParticao());
			vo.setCodBolao(reservaCotaBolao.getCodBolao());
			vo.setQtdCotasBolao(reservaCotaBolao.getQtdCotaTotal());
		}
		vo.setConcursoAlvo(concursoAlvo);
		vo.setSubcanalCarrinhoAposta(subcanalCarrinhoAposta);
		return vo;
	}

	/**
	 * @param situacao
	 * @param nsbTroca
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Aposta<?>> T getTeimosinha(SituacaoAposta situacao, Premio premio, Long nsuTransacao, Data dataInicioApostaComprada, Hora horaInicioApostaComprada) {
		if (isEsportivo()) {
			throw new IllegalStateException("Não é possível criar teimosinha de aposta esportiva");
		}
		Aposta<?> novaAposta = ApostaUtil.createAposta(getModalidade());
		ApostaComprada novaApostaComprada = new ApostaComprada();

		novaAposta.setApostaComprada(novaApostaComprada);
		novaAposta.setTipoConcurso(getTipoConcurso());
		novaAposta.setValor(getValor());
		novaAposta.setCompra(getCompra());
		novaAposta.setApostaOriginal(getApostaComprada());
		novaAposta.setConcursoInicial(premio.getConcursoInicialTroca());
		novaAposta.setApostaTroca(true);
		novaAposta.setPremio(null);
		novaAposta.setSituacao(situacao);
		novaAposta.setNsb(premio.getNsbTroca());
		novaAposta.setDataEnvioSISPL(null);
		novaAposta.setHoraEnvioSISPL(null);

		novaAposta.setNsuTransacao(nsuTransacao);
		novaAposta.setDataInicioApostaComprada(dataInicioApostaComprada);
		novaAposta.setHoraInicioApostaComprada(horaInicioApostaComprada);

		novaAposta.setDataEfetivacaoSISPL(premio.getDataPagamento());
		novaAposta.setHoraEfetivacaoSISPL(premio.getHoraPagamento());

		Data timestampAtual = DataUtil.getTimestampAtual();
		novaAposta.setDataFinalizacaoProcessamento(timestampAtual);
		novaAposta.setHoraFinalizacaoProcessamento(new Hora(timestampAtual));

		novaAposta.setIndicadorSurpresinha(getIndicadorSurpresinha());

		novaAposta.setParticao(getParticao());
		novaAposta.setMes(getMes());
		novaAposta.setValorComissao(Decimal.ZERO);
		novaAposta.setConcursoAlvo(getConcursoAlvo());

		/*
		 * Vinculo ao combo da aposta original
		 */
		novaAposta.setComboAposta(getComboAposta());
		novaAposta.setNuCombo(getNuCombo());

		novaAposta.setIndicadorBolao(Boolean.FALSE);

		// PRECISA SER A ULTIMA LINHA
		novaApostaComprada.setAposta(novaAposta);
		novaApostaComprada.setIndicadorBloqueioDevolucao(Boolean.FALSE);

		return (T) novaAposta;
	}

	public boolean isSorteada() {
		return sorteada;
	}

	public void setSorteada(boolean sorteada) {
		this.sorteada = sorteada;
	}

	/**
	 * @return true se a aposta estiver válida
	 * @throws NegocioException
	 *             se a aposta estiver inválida
	 */
	public abstract boolean valida(AbstractParametroJogo parametroJogo) throws NegocioException;

	public Data getDataEfetivacaoSISPL() {
		return apostaComprada.getDataEfetivacaoSISPL();
	}

	public Hora getHoraEfetivacaoSISPL() {
		return apostaComprada.getHoraEfetivacaoSISPL();
	}

	public void setDataEfetivacaoSISPL(Data dataEfetivacaoSISPL) {
		apostaComprada.setDataEfetivacaoSISPL(dataEfetivacaoSISPL);
	}

	public void setHoraEfetivacaoSISPL(Hora horaEfetivacaoSISPL) {
		apostaComprada.setHoraEfetivacaoSISPL(horaEfetivacaoSISPL);
	}

	/**
	 * @return se o valor dessa aposta pode ser devolvido, de acordo com sua situacao atual.
	 */
	public boolean podeSerDevolvida() {
		return SituacaoAposta.Situacao.NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO.getValue().equals(getSituacao().getId());
	}

	public String getNsuTransacaoDevolucao() {
		return apostaComprada.getNsuTransacaoDevolucao();
	}

	public void setNsuTransacaoDevolucao(String nsuTransacaoDevolucao) {
		initApostaOriginal();
		apostaComprada.setNsuTransacaoDevolucao(nsuTransacaoDevolucao);
	}

	public Data getDataInicioApostaComprada() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getDataInicioApostaComprada();
	}

	public void setDataInicioApostaComprada(Data dataInicioApostaComprada) {
		initApostaOriginal();
		apostaComprada.setDataInicioApostaComprada(dataInicioApostaComprada);
	}

	public Hora getHoraInicioApostaComprada() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getHoraInicioApostaComprada();
	}

	public void setHoraInicioApostaComprada(Hora horaInicioApostaComprada) {
		initApostaOriginal();
		apostaComprada.setHoraInicioApostaComprada(horaInicioApostaComprada);
	}

	public Decimal getValorComissao() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getValorComissao();
	}

	public void setValorComissao(Decimal valorComissao) {
		initApostaOriginal();
		apostaComprada.setValorComissao(valorComissao);
	}

	public Integer getConcursoAlvo() {
		return concursoAlvo;
	}

	public void setConcursoAlvo(Integer concursoAlvo) {
		this.concursoAlvo = concursoAlvo;
	}

	public String getExternalIdDevolucao() {
		return apostaComprada.getExternalIdDevolucao();
	}

	public String getTxIdDevolucaoPix() {
		return apostaComprada.getTxIdDevolucaoPix();
	}

	public String getEstornoPix() {
		return apostaComprada.getDevolucaoApostaPix();
	}

	public boolean isSorteadaPrimeiroConcurso() {
		return sorteadaPrimeiroConcurso;
	}

	public void setSorteadaPrimeiroConcurso(boolean sorteadaPrimeiroConcurso) {
		this.sorteadaPrimeiroConcurso = sorteadaPrimeiroConcurso;
	}

	public Long getMes() {
		return mes;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	public ApostaLotomania getApostaOriginalEspelho() {
		return apostaOriginalEspelho;
	}

	public void setApostaOriginalEspelho(ApostaLotomania apostaOriginalEspelho) {
		this.apostaOriginalEspelho = apostaOriginalEspelho;
	}

	public Data getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Data dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Subcanal getSubcanalCarrinhoAposta() {
		return subcanalCarrinhoAposta;
	}

	public void setSubcanalCarrinhoAposta(Subcanal subcanalCarrinhoAposta) {
		this.subcanalCarrinhoAposta = subcanalCarrinhoAposta;
	}

	public Long getNuCombo() {
		return nuCombo;
	}

	public void setNuCombo(Long nuCombo) {
		this.nuCombo = nuCombo;
	}

	public Boolean getIndicadorBolao() {
		return indicadorBolao;
	}

	public void setIndicadorBolao(Boolean indicadorBolao) {
		this.indicadorBolao = indicadorBolao;
	}

	public ReservaCotaBolao getReservaCotaBolao() {
		return reservaCotaBolao;
	}

	public void setReservaCotaBolao(ReservaCotaBolao reservaCotaBolao) {
		this.reservaCotaBolao = reservaCotaBolao;
	}

	@Override
	public String toString() {
		return "Aposta [id=" + id + "]";
	}

	public Long getApostaOriginalLotomania() {
		return apostaOriginalLotomania;
	}

	public void setApostaOriginalLotomania(Long apostaOriginalLotomania) {
		this.apostaOriginalLotomania = apostaOriginalLotomania;
	}
}
