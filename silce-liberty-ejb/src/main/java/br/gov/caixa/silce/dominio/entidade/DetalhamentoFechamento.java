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
import javax.persistence.Table;

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.util.Decimal;

@Entity
@Table(name = "LCETB022_DETALHE_FECHAMENTO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id=?1"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_MP_OPERACAO,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id=?1 and df.operacao.id in (?2) and df.meioPagamento.id=?3"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_MP,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id=?1 and df.meioPagamento.id=?2"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_VALOR_NAO_ZERADO_BY_OPERACAO_COM_SUBCANAL,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id = ?1 and df.operacao.id in (?2) and df.subcanalDetalhamento is not null"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_VALOR_NAO_ZERADO_BY_OPERACAO_SEM_SUBCANAL,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id = ?1 and df.operacao.id in (?2) and df.concurso is not null and df.subcanalDetalhamento is null"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_OPERACAO_NAO_ZERADO_COM_CONCURSO_MODALIDADE,
			query = "Select df from DetalhamentoFechamento df join df.meioPagamento mp where df.fechamento.id=?1 and df.operacao.id=?2 and df.valorBruto <> ?3"
				+ " and df.modalidade is not null and df.concurso is not null and df.subcanalDetalhamento is null order by mp.id, df.id"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_COUNT_OPERACOES_BY_FECHAMENTO_MEIO_PAGAMENTO,
			query = "Select sum(df.quantidadeOperacoes) from DetalhamentoFechamento df where df.fechamento.id = ?1 and df.operacao.id in (?2) and df.meioPagamento.id = ?3"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_COUNT_OPERACOES_BY_FECHAMENTO_MEIO_PAGAMENTO_CONCURSO_NULL,
			query = "Select sum(df.quantidadeOperacoes) from DetalhamentoFechamento df where df.fechamento.id = ?1"
				+ " and df.operacao.id in (?2) and df.meioPagamento.id = ?3 and df.concurso is null"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_ZERADO,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id=?1 and df.operacao.id=?2"
				+ " and df.meioPagamento.id=?3 and df.modalidade is null and df.concurso is null and df.quantidadeOperacoes = 0"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_ZERADO_COM_SUBCANAL,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id=?1 and df.operacao.id=?2"
				+ " and df.meioPagamento.id=?3 and df.subcanalDetalhamento=?4 and df.modalidade is null and df.concurso is null and df.quantidadeOperacoes = 0"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_NULL_CONCURSO_NULL,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id=?1 and df.operacao.id=?2"
				+ " and df.meioPagamento.id=?3 and df.modalidade is null and df.concurso is null"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_NULL_CONCURSO_NULL_SUBCANAL_NULL,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id=?1 and df.operacao.id=?2"
				+ " and df.meioPagamento.id=?3 and df.modalidade is null and df.concurso is null and df.subcanalDetalhamento is null"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id=?1 and df.operacao.id=?2 and df.meioPagamento.id=?3 and df.modalidade = ?4 and df.concurso = ?5"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_SUBCANAL,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id=?1 and df.operacao.id=?2 and df.meioPagamento.id=?3 and df.modalidade = ?4 and df.concurso = ?5 and df.subcanalDetalhamento =?6"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_SUBCANAL_NULL,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id=?1 and  df.operacao.id=?2 and df.meioPagamento.id=?3 and df.modalidade = ?4 and df.concurso = ?5 and df.subcanalDetalhamento is null"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_NULL,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id=?1 and df.operacao.id=?2 and df.meioPagamento.id=?3 and df.modalidade = ?4 and df.concurso is null"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_LOTERICA_SUBCANAL,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id = ?1 and df.operacao.id = ?2 and df.meioPagamento.id = ?3 and df.modalidade = ?4 "
				+ "and df.concurso = ?5 and df.loterica.id = ?6 and df.subcanalDetalhamento =? 7"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_LOTERICA_SUBCANAL_NULL,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id = ?1 and df.operacao.id = ?2 and df.meioPagamento.id = ?3 and df.modalidade = ?4 "
				+ "and df.concurso = ?5 and df.loterica.id = ?6 and df.subcanalDetalhamento is null"),

		@NamedQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_LOTERICA_MEIO_PAGAMENTO_MODALIDADE_CONCURSO_SUBCANAL,
			query = "Select df from DetalhamentoFechamento df where df.fechamento.id = ?1 and df.loterica.id = ?2 and df.meioPagamento.id = ?3 "
				+ "and df.modalidade = ?4 and df.concurso = ?5 and df.subcanalDetalhamento =?6")
		
})
@NamedNativeQueries({
	@NamedNativeQuery(name = DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_PAGAMENTOAPOSTA_GROUPEDBY_MEIO_MODALIDADE_CONCURSO,
		query = "SELECT d.NU_JOGO, d.NU_CONCURSO, sum(d.QT_OPERACAO), sum(d.VR_BRUTO), sum(d.VR_COMISSAO), NU_MEIO_PAGAMENTO "
			+ "FROM lce.LCETB022_DETALHE_FECHAMENTO d "
			+ "WHERE d.NU_CONTROLE_FECHAMENTO = ?1 "
			+ "AND d.NU_TIPO_OPERACAO = ?2 "
			+ "AND d.NU_JOGO IS NOT NULL "
			+ "AND d.NU_CONCURSO IS NOT NULL "
			+ "AND D.IC_SUBCANAL_DETALHAMENTO IS NOT NULL "
			+ "GROUP BY d.NU_JOGO, d.NU_CONCURSO, d.NU_MEIO_PAGAMENTO"),

	@NamedNativeQuery(name = DetalhamentoFechamento.NQ_SELECT_ARRECADACAO,
		query = "SELECT COALESCE(df.IC_SUBCANAL_DETALHAMENTO,0) AS SUBCANAL, "
			+ " SUM(COALESCE (df.VR_BRUTO, 0)) AS VALOR_SUBCANAL"
			+ " FROM LCE.LCETB022_DETALHE_FECHAMENTO df"
			+ " INNER JOIN LCE.LCETB021_CONTROLE_FECHAMENTO ctrl ON ctrl.NU_CONTROLE_FECHAMENTO = df.NU_CONTROLE_FECHAMENTO"
				+ " WHERE ctrl.DT_CONTROLE_FECHAMENTO BETWEEN ?1 AND ?2"
				+ " AND df.NU_TIPO_OPERACAO = 5"
			+ " GROUP BY df.IC_SUBCANAL_DETALHAMENTO WITH UR"),

		@NamedNativeQuery(name = DetalhamentoFechamento.NQ_SELECT_VALOR_TOTAL_COTAS_FECHAMENTO,
			query = "SELECT DF.NU_CONCURSO, SUM(DISTINCT DF.VR_COTA_ADQUIRIDA) VALOR_COTA FROM LCE.LCETB022_DETALHE_FECHAMENTO DF "
			+ " INNER JOIN LCE.LCETB021_CONTROLE_FECHAMENTO CF ON CF.NU_CONTROLE_FECHAMENTO = DF.NU_CONTROLE_FECHAMENTO "
			+ " INNER JOIN LCE.LCETB028_ENVIO_FECHAMENTO EF ON EF.NU_CONTROLE_FECHAMENTO = DF.NU_CONTROLE_FECHAMENTO "
			+ " 	AND EF.NU_SISTEMA_FECHAMENTO = ?1 "
			+ " INNER JOIN LCE.LCETB029_DETALHE_ENVIO_FECHAMENTO DEF ON DEF.NU_ENVIO_FECHAMENTO = EF.NU_ENVIO_FECHAMENTO "
				+ " 	AND DEF.NU_CANAL_DISTRIBUICAO = DF.NU_CANAL_DISTRIBUICAO "
				+ " INNER JOIN LCE.LCETB062_DETALHE_RETORNO_FCHMO DRF ON DRF.NU_DETALHE_ENVIO_FECHAMENTO = DEF.NU_DETALHE_ENVIO_FECHAMENTO "
				+ " 	AND DRF.AA_MOVIMENTO_ENVIO_FECHAMENTO = DEF.AA_MOVIMENTO_ENVIO_FECHAMENTO AND DRF.MM_MOVIMENTO_ENVIO_FECHAMENTO = DEF.MM_MOVIMENTO_ENVIO_FECHAMENTO "
			+ " 	AND DRF.NO_TIPO_CONTA_LOTERICO = ?2 "
			+ " WHERE DF.NU_CONTROLE_FECHAMENTO = ?3 AND DF.NU_TIPO_OPERACAO = ?4 "
			+ " GROUP BY DF.NU_CONCURSO"),
	
	@NamedNativeQuery(name = DetalhamentoFechamento.NQ_SELECT_VALOR_TOTAL_TARIFAS_COTAS_FECHAMENTO,
			query = "SELECT DF.NU_CONCURSO, SUM(DISTINCT DF.VR_TARIFA_COTA_ADQUIRIDA) VALOR_TARIFA_COTA FROM LCE.LCETB022_DETALHE_FECHAMENTO DF "
			+ " INNER JOIN LCE.LCETB021_CONTROLE_FECHAMENTO CF ON CF.NU_CONTROLE_FECHAMENTO = DF.NU_CONTROLE_FECHAMENTO "
			+ " INNER JOIN LCE.LCETB028_ENVIO_FECHAMENTO EF ON EF.NU_CONTROLE_FECHAMENTO = DF.NU_CONTROLE_FECHAMENTO "
			+ " 	AND EF.NU_SISTEMA_FECHAMENTO = ?1 "
			+ " INNER JOIN LCE.LCETB029_DETALHE_ENVIO_FECHAMENTO DEF ON DEF.NU_ENVIO_FECHAMENTO = EF.NU_ENVIO_FECHAMENTO "
				+ " 	AND DEF.NU_CANAL_DISTRIBUICAO = DF.NU_CANAL_DISTRIBUICAO "
				+ " INNER JOIN LCE.LCETB062_DETALHE_RETORNO_FCHMO DRF ON DRF.NU_DETALHE_ENVIO_FECHAMENTO = DEF.NU_DETALHE_ENVIO_FECHAMENTO "
				+ " 	AND DRF.AA_MOVIMENTO_ENVIO_FECHAMENTO = DEF.AA_MOVIMENTO_ENVIO_FECHAMENTO AND DRF.MM_MOVIMENTO_ENVIO_FECHAMENTO = DEF.MM_MOVIMENTO_ENVIO_FECHAMENTO "
			+ " 	AND DRF.NO_TIPO_CONTA_LOTERICO = ?2 "
			+ " WHERE DF.NU_CONTROLE_FECHAMENTO = ?3 AND DF.NU_TIPO_OPERACAO = ?4 "
			+ " GROUP BY DF.NU_CONCURSO"),

	@NamedNativeQuery(name = DetalhamentoFechamento.NQ_SELECT_VALOR_TOTAL_CUSTEIOS_COTAS_FECHAMENTO,
			query = "SELECT DF.NU_CONCURSO, SUM(DISTINCT DF.VR_CUSTEIO_COTA) VALOR_CUSTEIO_COTA FROM LCE.LCETB022_DETALHE_FECHAMENTO DF "
			+ " INNER JOIN LCE.LCETB021_CONTROLE_FECHAMENTO CF ON CF.NU_CONTROLE_FECHAMENTO = DF.NU_CONTROLE_FECHAMENTO "
			+ " INNER JOIN LCE.LCETB028_ENVIO_FECHAMENTO EF ON EF.NU_CONTROLE_FECHAMENTO = DF.NU_CONTROLE_FECHAMENTO "
			+ " 	AND EF.NU_SISTEMA_FECHAMENTO = ?1 "
			+ " INNER JOIN LCE.LCETB029_DETALHE_ENVIO_FECHAMENTO DEF ON DEF.NU_ENVIO_FECHAMENTO = EF.NU_ENVIO_FECHAMENTO "
				+ " 	AND DEF.NU_CANAL_DISTRIBUICAO = DF.NU_CANAL_DISTRIBUICAO "
				+ " INNER JOIN LCE.LCETB062_DETALHE_RETORNO_FCHMO DRF ON DRF.NU_DETALHE_ENVIO_FECHAMENTO = DEF.NU_DETALHE_ENVIO_FECHAMENTO "
				+ " 	AND DRF.AA_MOVIMENTO_ENVIO_FECHAMENTO = DEF.AA_MOVIMENTO_ENVIO_FECHAMENTO AND DRF.MM_MOVIMENTO_ENVIO_FECHAMENTO = DEF.MM_MOVIMENTO_ENVIO_FECHAMENTO AND DRF.NO_TIPO_CONTA_LOTERICO = ?2 "
			+ " WHERE DF.NU_CONTROLE_FECHAMENTO = ?3 AND DF.NU_TIPO_OPERACAO = ?4 "
			+ " GROUP BY DF.NU_CONCURSO"),

	@NamedNativeQuery(name = DetalhamentoFechamento.NQ_SELECT_VALOR_TOTAL_TARIFAS_CUSTEIOS_COTAS_FECHAMENTO,
			query = "SELECT DF.NU_CONCURSO, SUM(DISTINCT DF.VR_TARIFA_CUSTEIO_COTA) VALOR_TARIFA_CUSTEIO_COTA FROM LCE.LCETB022_DETALHE_FECHAMENTO DF "
			+ " INNER JOIN LCE.LCETB021_CONTROLE_FECHAMENTO CF ON CF.NU_CONTROLE_FECHAMENTO = DF.NU_CONTROLE_FECHAMENTO "
			+ " INNER JOIN LCE.LCETB028_ENVIO_FECHAMENTO EF ON EF.NU_CONTROLE_FECHAMENTO = DF.NU_CONTROLE_FECHAMENTO "
			+ " 	AND EF.NU_SISTEMA_FECHAMENTO = ?1 "
			+ " INNER JOIN LCE.LCETB029_DETALHE_ENVIO_FECHAMENTO DEF ON DEF.NU_ENVIO_FECHAMENTO = EF.NU_ENVIO_FECHAMENTO "
				+ " 	AND DEF.NU_CANAL_DISTRIBUICAO = DF.NU_CANAL_DISTRIBUICAO "
				+ " INNER JOIN LCE.LCETB062_DETALHE_RETORNO_FCHMO DRF ON DRF.NU_DETALHE_ENVIO_FECHAMENTO = DEF.NU_DETALHE_ENVIO_FECHAMENTO "
				+ " 	AND DRF.AA_MOVIMENTO_ENVIO_FECHAMENTO = DEF.AA_MOVIMENTO_ENVIO_FECHAMENTO AND DRF.MM_MOVIMENTO_ENVIO_FECHAMENTO = DEF.MM_MOVIMENTO_ENVIO_FECHAMENTO AND DRF.NO_TIPO_CONTA_LOTERICO = ?2 "
			+ " WHERE DF.NU_CONTROLE_FECHAMENTO = ?3 AND DF.NU_TIPO_OPERACAO = ?4 "
			+ " GROUP BY DF.NU_CONCURSO"),		

	@NamedNativeQuery(name = DetalhamentoFechamento.NQ_SUM_VALOR_COMISSAO_SELECT_BY_DATA_OPERACAO_MEIO_PAGAMENTO,
			query = "SELECT SUM(t0.VR_BRUTO), SUM(t0.VR_COMISSAO), SUM(t0.VR_COMISSAO_AJUSTADA) FROM LCE.LCETB022_DETALHE_FECHAMENTO t0 "
				+ "INNER JOIN LCE.LCETB021_CONTROLE_FECHAMENTO t1 ON t0.NU_CONTROLE_FECHAMENTO = t1.NU_CONTROLE_FECHAMENTO "
				+ "INNER JOIN LCE.LCETB018_MEIO_PAGAMENTO t2 ON t0.NU_MEIO_PAGAMENTO = t2.NU_MEIO_PAGAMENTO "
				+ "INNER JOIN LCE.LCETB020_TIPO_OPERACAO t3 ON t0.NU_TIPO_OPERACAO = t3.NU_TIPO_OPERACAO "
				+ "WHERE (t1.DT_CONTROLE_FECHAMENTO = ?1 AND t0.NU_TIPO_OPERACAO = ?2 AND t0.NU_MEIO_PAGAMENTO = ?3 "
				+ "AND t0.AA_MOVIMENTO_FECHAMENTO_DIARIO = ?4 AND t0.MM_MOVIMENTO_FECHAMENTO_DIARIO = ?5) optimize for 1 row"),

	@NamedNativeQuery(name = DetalhamentoFechamento.NQ_SUM_VALOR_COMISSAO_SELECT_BY_MES_OPERACAO_MEIO_PAGAMENTO,
			query = "SELECT SUM(t0.VR_COMISSAO) FROM LCE.LCETB022_DETALHE_FECHAMENTO t0 "
				+ "INNER JOIN LCE.LCETB021_CONTROLE_FECHAMENTO t1 ON t0.NU_CONTROLE_FECHAMENTO = t1.NU_CONTROLE_FECHAMENTO "
				+ "INNER JOIN LCE.LCETB018_MEIO_PAGAMENTO t2 ON t0.NU_MEIO_PAGAMENTO = t2.NU_MEIO_PAGAMENTO "
				+ "INNER JOIN LCE.LCETB020_TIPO_OPERACAO t3 ON t0.NU_TIPO_OPERACAO = t3.NU_TIPO_OPERACAO "
				+ "WHERE (YEAR(t1.DT_CONTROLE_FECHAMENTO) = ?1 AND MONTH(t1.DT_CONTROLE_FECHAMENTO) = ?2 AND t0.NU_TIPO_OPERACAO = ?3 "
				+ "AND t0.NU_MEIO_PAGAMENTO = ?4 AND t0.AA_MOVIMENTO_FECHAMENTO_DIARIO = ?1 AND t0.MM_MOVIMENTO_FECHAMENTO_DIARIO = ?2) optimize for 1 row"),

		@NamedNativeQuery(name = DetalhamentoFechamento.NQ_UPDATE_BY_FECHAMENTO_LOTERICA_OPERACAO,
			query = "UPDATE LCE.LCETB022_DETALHE_FECHAMENTO DF "
				+ "SET DF.NU_TIPO_OPERACAO = ?1 "
				+ "WHERE DF.NU_CONTROLE_FECHAMENTO = ?2 AND DF.NU_CANAL_DISTRIBUICAO = ?3 AND DF.NU_TIPO_OPERACAO = ?4 "),

		@NamedNativeQuery(name = DetalhamentoFechamento.NQ_COUNT_BY_FECHAMENTO_LOTERICA_OPERACAO,
			query = "SELECT COUNT(*) FROM LCE.LCETB022_DETALHE_FECHAMENTO DF "
				+ "WHERE DF.NU_CONTROLE_FECHAMENTO = ?1 AND DF.NU_TIPO_OPERACAO = ?2")

})
public class DetalhamentoFechamento extends AbstractEntidade<DetalhamentoFechamentoPK> {

	public static final String NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_ZERADO = "DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_ZERADO";
	public static final String NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_ZERADO_COM_SUBCANAL = "DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_ZERADO_COM_SUBCANAL";
	public static final String NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_NULL_CONCURSO_NULL = "DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADENULL_CONCURSONULL";
	public static final String NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_NULL_CONCURSO_NULL_SUBCANAL_NULL = "DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADENULL_CONCURSONULL_SUBCANALNULL";
	public static final String NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_SUBCANAL_MODALIDADE_NULL_CONCURSO_NULL = "DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_SUBCANAL_MODALIDADENULL_CONCURSONULL";
	public static final String NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO = "DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO";
	public static final String NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_SUBCANAL = "DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_SUBCANAL";
	public static final String NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_SUBCANAL_NULL = "DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_SUBCANALNULL";
	public static final String NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_NULL = "DetalhamentoFechamento.NQ_SELECT_PAGAMENTOPREMIO_BY_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE";
	public static final String NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_LOTERICA_SUBCANAL = "DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_LOTERICA_SUBCANAL";
	public static final String NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_LOTERICA_SUBCANAL_NULL = "DetalhamentoFechamento.NQ_SELECT_BY_OPERACAO_FECHAMENTO_MEIOPAGAMENTO_MODALIDADE_CONCURSO_LOTERICA_SUBCANALNULL";
	public static final String NQ_SELECT_BY_FECHAMENTO_OPERACAO_NAO_ZERADO_COM_CONCURSO_MODALIDADE = "DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_OPERACAO_NAO_ZERADO_COM_CONCURSO_MODALIDADE";
	public static final String NQ_SUM_VALOR_COMISSAO_SELECT_BY_MES_OPERACAO_MEIO_PAGAMENTO = "DetalhamentoFechamento.NQ_SUM_VALOR_COMISSAO_SELECT_BY_MES_OPERACAO_MEIO_PAGAMENTO";
	public static final String NQ_SUM_VALOR_COMISSAO_SELECT_BY_DATA_OPERACAO_MEIO_PAGAMENTO = "DetalhamentoFechamento.NQ_SUM_VALOR_COMISSAO_SELECT_BY_DATA_OPERACAO_MEIO_PAGAMENTO";
	public static final String NQ_SELECT_BY_FECHAMENTO_VALOR_NAO_ZERADO_BY_OPERACAO_COM_SUBCANAL = "DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_VALOR_NAO_ZERADO_BY_OPERACAO_COM_SUBCANAL";
	public static final String NQ_SELECT_BY_FECHAMENTO_VALOR_NAO_ZERADO_BY_OPERACAO_SEM_SUBCANAL = "DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_VALOR_NAO_ZERADO_BY_OPERACAO_SEM_SUBCANAL";
	public static final String NQ_SELECT_BY_FECHAMENTO = "DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO";
	public static final String NQ_SELECT_BY_FECHAMENTO_MP_OPERACAO = "DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_MP_OPERACAO";
	public static final String NQ_COUNT_OPERACOES_BY_FECHAMENTO_MEIO_PAGAMENTO = "DetalhamentoFechamento.NQ_COUNT_OPERACOES_BY_FECHAMENTO_MEIO_PAGAMENTO";
	public static final String NQ_COUNT_OPERACOES_BY_FECHAMENTO_MEIO_PAGAMENTO_CONCURSO_NULL = "DetalhamentoFechamento.NQ_COUNT_OPERACOES_BY_FECHAMENTO_MEIO_PAGAMENTO_CONCURSO_NULL";
	public static final String NQ_SELECT_ARRECADACAO = "DetalhamentoFechamento.NQ_SELECT_ARRECADACAO";
	public static final String NQ_SELECT_BY_FECHAMENTO_MP = "DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_MP";
	public static final String NQ_SELECT_BY_FECHAMENTO_PAGAMENTOAPOSTA_GROUPEDBY_MEIO_MODALIDADE_CONCURSO = "DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_PAGAMENTOAPOSTA_GROUPEDBY_MEIO_MODALIDADE_CONCURSO";
	public static final String NQ_SELECT_BY_FECHAMENTO_LOTERICA_MEIO_PAGAMENTO_MODALIDADE_CONCURSO_SUBCANAL = "DetalhamentoFechamento.NQ_SELECT_BY_FECHAMENTO_LOTERICA_MEIO_PAGAMENTO_MODALIDADE_CONCURSO_SUBCANAL";
	public static final String NQ_SELECT_VALOR_TOTAL_COTAS_FECHAMENTO = "DetalhamentoFechamento.NQ_SELECT_VALOR_TOTAL_COTAS_FECHAMENTO";
	public static final String NQ_SELECT_VALOR_TOTAL_TARIFAS_COTAS_FECHAMENTO = "DetalhamentoFechamento.NQ_SELECT_VALOR_TOTAL_TARIFAS_COTAS_FECHAMENTO";
	public static final String NQ_SELECT_VALOR_TOTAL_CUSTEIOS_COTAS_FECHAMENTO = "DetalhamentoFechamento.NQ_SELECT_VALOR_TOTAL_CUSTEIOS_COTAS_FECHAMENTO";
	public static final String NQ_SELECT_VALOR_TOTAL_TARIFAS_CUSTEIOS_COTAS_FECHAMENTO = "DetalhamentoFechamento.NQ_SELECT_VALOR_TOTAL_TARIFAS_CUSTEIOS_COTAS_FECHAMENTO";
	public static final String NQ_UPDATE_BY_FECHAMENTO_LOTERICA_OPERACAO = "DetalhamentoFechamento.NQ_UPDATE_BY_FECHAMENTO_LOTERICA_OPERACAO";
	public static final String NQ_COUNT_BY_FECHAMENTO_LOTERICA_OPERACAO = "DetalhamentoFechamento.NQ_COUNT_BY_FECHAMENTO_LOTERICA_OPERACAO";

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DetalhamentoFechamentoPK id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_CONTROLE_FECHAMENTO", referencedColumnName = "NU_CONTROLE_FECHAMENTO")
	private Fechamento fechamento;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_MEIO_PAGAMENTO", referencedColumnName = "NU_MEIO_PAGAMENTO")
	private MeioPagamento meioPagamento;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_TIPO_OPERACAO", referencedColumnName = "NU_TIPO_OPERACAO")
	@EagerFetchMode(FetchMode.PARALLEL)
	private Operacao operacao;

	@Column(name = "QT_OPERACAO")
	private Long quantidadeOperacoes;

	@Column(name = "VR_BRUTO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorBruto;

	@Column(name = "VR_COMISSAO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorComissao;

	@Column(name = "VR_COMISSAO_AJUSTADA")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorComissaoAjustada;

	@Column(name = "NU_JOGO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Modalidade modalidade;

	@Column(name = "NU_CONCURSO")
	private Integer concurso;

	@Column(name = "IC_SUBCANAL_DETALHAMENTO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Subcanal subcanalDetalhamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_CANAL_DISTRIBUICAO", referencedColumnName = "NU_CD")
	private Loterica loterica;

	@Column(name = "VR_COTA_ADQUIRIDA ")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorCota;

	@Column(name = "VR_TARIFA_COTA_ADQUIRIDA ")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorTarifaCota;

	@Column(name = "VR_CUSTEIO_COTA ")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorCusteioCota;

	@Column(name = "VR_TARIFA_CUSTEIO_COTA ")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal valorTarifaCusteioCota;

	public Decimal getValorBrutoCota() {
		return getValorCota().add(getValorTarifaCota());
	}

	public Decimal getValorLiquido() {
		return getValorBruto().subtract(getValorComissao());
	}

	public DetalhamentoFechamentoPK getId() {
		return id;
	}

	public void setId(DetalhamentoFechamentoPK id) {
		this.id = id;
	}

	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(MeioPagamento meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Long getQuantidadeOperacoes() {
		return quantidadeOperacoes;
	}

	public void setQuantidadeOperacoes(Long quantidadeOperacoes) {
		this.quantidadeOperacoes = quantidadeOperacoes;
	}

	public Decimal getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(Decimal valorBruto) {
		this.valorBruto = valorBruto;
	}

	public Decimal getValorComissao() {
		return valorComissao;
	}

	public void setValorComissao(Decimal valorComissao) {
		this.valorComissao = valorComissao;
	}

	public Decimal getValorComissaoAjustada() {
		return valorComissaoAjustada;
	}

	public void setValorComissaoAjustada(Decimal valorComissaoAjustada) {
		this.valorComissaoAjustada = valorComissaoAjustada;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Integer getConcurso() {
		return concurso;
	}

	public void setConcurso(Integer concurso) {
		this.concurso = concurso;
	}

	public Subcanal getSubcanalDetalhamento() {
		return subcanalDetalhamento;
	}

	public void setSubcanalDetalhamento(Subcanal subcanalDetalhamento) {
		this.subcanalDetalhamento = subcanalDetalhamento;
	}

	public Loterica getLoterica() {
		return loterica;
	}

	public void setLoterica(Loterica loterica) {
		this.loterica = loterica;
	}

	public Decimal getValorCota() {
		return valorCota;
	}

	public void setValorCota(Decimal valorCota) {
		this.valorCota = valorCota;
	}

	public Decimal getValorTarifaCota() {
		return valorTarifaCota;
	}

	public void setValorTarifaCota(Decimal valorTarifaCota) {
		this.valorTarifaCota = valorTarifaCota;
	}

	public Decimal getValorCusteioCota() {
		return valorCusteioCota;
	}

	public void setValorCusteioCota(Decimal valorCusteioCota) {
		this.valorCusteioCota = valorCusteioCota;
	}

	public Decimal getValorTarifaCusteioCota() {
		return valorTarifaCusteioCota;
	}

	public void setValorTarifaCusteioCota(Decimal valorTarifaCusteioCota) {
		this.valorTarifaCusteioCota = valorTarifaCusteioCota;
	}

}
