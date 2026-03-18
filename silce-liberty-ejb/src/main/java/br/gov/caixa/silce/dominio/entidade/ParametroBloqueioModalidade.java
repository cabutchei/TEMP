package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.RepresentacaoConcurso;
import br.gov.caixa.silce.dominio.jogos.TipoConcurso;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Validate;

@Entity
@Table(name = "LCETB053_BLOQUEIO_MODALIDADE", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(
			name = ParametroBloqueioModalidade.NQ_SELECT_BY_MODALIDADE_SUBCANAL_TIPOCONCURSO,
			query = "select pbm From ParametroBloqueioModalidade pbm where pbm.modalidade = ?1 and pbm.tipoConcurso = ?2 and pbm.subcanal = ?3 and pbm.indicadorBloqueioBolao = ?4"
		),

		@NamedQuery(
			name = ParametroBloqueioModalidade.NQ_SELECT_BLOQUEIOS_BOLAO,
			query = "select pbm From ParametroBloqueioModalidade pbm where pbm.indicadorBloqueioBolao = true"
		)
})
public class ParametroBloqueioModalidade extends AbstractEntidade<Short> {

	private static final long serialVersionUID = 1L;
	public static final String NQ_SELECT_BY_MODALIDADE_SUBCANAL_TIPOCONCURSO = "ParametroBloqueioModalidade.NQ_SELECT_BY_MODALIDADE_SUBCANAL_TIPOCONCURSO";
	public static final String NQ_SELECT_BLOQUEIOS_BOLAO = "ParametroBloqueioModalidade.NQ_SELECT_BLOQUEIOS_BOLAO";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_BLOQUEIO_MODALIDADE")
	private Short id;

	@Column(name = "IC_SUBCANAL_BLOQUEIO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Subcanal subcanal;

	@Column(name = "NU_MODALIDADE_JOGO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Modalidade modalidade;

	@Column(name = "TS_BLOQUEIO_MODALIDADE")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataBloqueio;

	@Column(name = "IC_TIPO_CONCURSO_BLOQUEIO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private TipoConcurso tipoConcurso;
	
	@Column(name = "IC_BLOQUEIO_BOLAO")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean indicadorBloqueioBolao;

	public ParametroBloqueioModalidade() {

	}

	public ParametroBloqueioModalidade(Modalidade modalidade, TipoConcurso tipoConcurso, Subcanal subcanal, Boolean indicadorBloqueioBolao) {
		this.modalidade = modalidade;
		this.subcanal = subcanal;
		this.tipoConcurso = tipoConcurso;
		this.dataBloqueio = DataUtil.getTimestampAtual();
		this.indicadorBloqueioBolao = indicadorBloqueioBolao;
	}

	public static ParametroBloqueioModalidade of(RepresentacaoConcurso repr) throws NegocioException {
		Validate.notNull(repr.getSubcanal(), "Subcanal");
		Validate.notNull(repr.getModalidade(), "Modalidade");
		Validate.notNull(repr.getTipoConcurso(), "Tipo Concurso");
		Validate.notNull(repr.getIndicadorBloqueioBolao(), "Indicador bloqueio bolão");
		return new ParametroBloqueioModalidade(repr.getModalidade(), repr.getTipoConcurso(), repr.getSubcanal(), repr.getIndicadorBloqueioBolao());
	}

	@Override
	public Short getId() {
		return id;
	}

	@Override
	public void setId(Short id) {
		this.id = id;
	}

	public Subcanal getSubcanal() {
		return subcanal;
	}

	public void setSubcanal(Subcanal subcanal) {
		this.subcanal = subcanal;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Data getDataBloqueio() {
		return dataBloqueio;
	}

	public void setDataBloqueio(Data dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}

	public TipoConcurso getTipoConcurso() {
		return tipoConcurso;
	}

	public void setTipoConcurso(TipoConcurso tipoConcurso) {
		this.tipoConcurso = tipoConcurso;
	}
	
	public Boolean getIndicadorBloqueioBolao() {
		return indicadorBloqueioBolao;
	}

	public void setIndicadorBloqueioBolao(Boolean indicadorBloqueioBolao) {
		this.indicadorBloqueioBolao = indicadorBloqueioBolao;
	}

}
