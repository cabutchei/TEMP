package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.NSB;
import br.gov.caixa.silce.dominio.Subcanal;
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
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;

/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 * @author c127237 - Rinaldo Pitzer Junior
 */
@Entity
@Table(name = "LCETB911_HSTRO_APOSTA", schema = DatabaseConfig.SCHEMA)
@DiscriminatorColumn(name = "NU_MODALIDADE_JOGO", discriminatorType = DiscriminatorType.INTEGER)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
		@NamedQuery(name = HistoricoAposta.NQ_SELECT_BY_HISTORICO_COMPRA_APOSTADOR,
			query = "Select aposta From HistoricoAposta aposta "
				+ "join fetch aposta.compra "
				+ "where aposta.compra.id.id = ?1 "
				+ "and aposta.id.mes = ?2 "
				+ "and aposta.id.ano = ?3 "
				+ "and aposta.compra.id.mes = ?2 "
				+ "and aposta.compra.id.ano = ?3 "
				+ "and aposta.compra.apostador.id = ?4 "
				+ "ORDER BY aposta.id ASC"),

		@NamedQuery(name = HistoricoAposta.NQ_SELECT_BY_ID_AND_APOSTADOR,
			query = "Select aposta From HistoricoAposta aposta "
				+ "join fetch aposta.compra "
				+ "Where aposta.id = ?1 "
				+ "and aposta.compra.apostador.id = ?2 "
				+ "ORDER BY aposta.id ASC"),

		@NamedQuery(name = HistoricoAposta.NQ_SELECT_ORIGINAIS_BY_HISTORICO_COMPRA_COM_PREMIO,
			query = "Select distinct aposta From HistoricoAposta aposta"
				+ " left join fetch aposta.apostaComprada"
				+ " left join fetch aposta.apostaComprada.situacao"
				+ " left join fetch aposta.reservaCotaBolao"
				+ " left join fetch aposta.reservaCotaBolao.loterica"
				+ " join fetch aposta.compra"
				+ " join fetch aposta.compra.meioPagamento"
				+ " where aposta.compra.id.id = ?1"
				+ " and aposta.compra.id.mes = ?2"
				+ " and aposta.compra.id.ano = ?3"
				+ " and aposta.id.mes = ?2 "
				+ " and aposta.id.ano = ?3 "
				+ " and aposta.compra.apostador.id = ?4")
})
public abstract class HistoricoAposta<P extends Palpites> extends AbstractEntidade<HistoricoApostaPK> {

	private static final long serialVersionUID = 1L;

	public static final String NQ_SELECT_BY_HISTORICO_COMPRA_APOSTADOR = "HistoricoAposta.NQ_SELECT_BY_HISTORICO_COMPRA_APOSTADOR";
	public static final String NQ_SELECT_BY_ID_AND_APOSTADOR = "HistoricoAposta.NQ_SELECT_BY_ID_AND_APOSTADOR";
	public static final String NQ_SELECT_ORIGINAIS_BY_HISTORICO_COMPRA_COM_PREMIO = "HistoricoAposta.NQ_SELECT_ORIGINAIS_BY_HISTORICO_COMPRA_COM_PREMIO";

	@EmbeddedId
	private HistoricoApostaPK id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumns({
			@JoinColumn(name = "NU_COMPRA", referencedColumnName = "NU_COMPRA"),
			@JoinColumn(name = "NU_MES", referencedColumnName = "NU_MES"),
			@JoinColumn(name = "NU_ANO", referencedColumnName = "NU_ANO")
	})
	private HistoricoCompra compra;

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

	@OneToOne(mappedBy = "aposta")
	private HistoricoApostaComprada apostaComprada;

	@Column(name = "NU_PARTICAO")
	private Long particao;

	@Column(name = "NU_CONCURSO_ALVO")
	private Integer concursoAlvo;

	@Column(name = "NU_MES")
	private Long mes;

	@Column(name = "NU_ANO")
	private Long ano;

	@Column(name = "TS_INCLUSAO_APOSTA_CARRINHO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataInclusao;

	@Column(name = "IC_SUBCANAL_APOSTA")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Subcanal subcanalCarrinhoAposta;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_VINCULO_APOSTA_ESPELHO", referencedColumnName = "NU_APOSTA")
	private HistoricoApostaLotomania apostaOriginalEspelho;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "NU_COMBO_APOSTA", referencedColumnName = "NU_COMBO_APOSTA")
	private ComboAposta comboAposta;

	@Column(name = "IC_COTA_BOLAO")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean indicadorBolao;

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumns({
			@JoinColumn(name = "NU_RESERVA_COTA_BOLAO", referencedColumnName = "NU_RESERVA_COTA_BOLAO"),
			@JoinColumn(name = "MM_RESERVA_COTA_BOLAO", referencedColumnName = "MM_RESERVA_COTA_BOLAO"),
			@JoinColumn(name = "NU_PARTICAO_COTA_BOLAO", referencedColumnName = "NU_PARTICAO")
	})
	private HistoricoReservaCotaBolao reservaCotaBolao;

	@Transient
	private boolean premiada = false;

	@Transient
	private boolean sorteada = true;

	@Transient
	private boolean sorteadaPrimeiroConcurso = false;

	/**
	 * Este premio é atributo transient que será populado apenas na consulta ao prêmio da aposta.
	 */
	@Transient
	private Premio premioConsulta;

	/**
	 * vazio devido ao jpa
	 */
	protected HistoricoAposta() {
		// vazio devido ao jpa
	}

	public HistoricoAposta(Modalidade modalidade) {
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

	public HistoricoApostaPK getId() {
		return id;
	}

	public void setId(HistoricoApostaPK id) {
		this.id = id;
	}

	public HistoricoCompra getCompra() {
		return compra;
	}

	public void setCompra(HistoricoCompra compra) {
		this.compra = compra;
		if (compra == null) {
			setParticao(null);
		} else {
			setParticao(compra.getParticao());
			setMes(compra.getId().getMes());
		}
	}

	public HistoricoApostaComprada getApostaOriginal() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getApostaOriginal();
	}

	public void setApostaOriginal(HistoricoApostaComprada apostaOriginal) {
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

	public HistoricoPremio getPremio() {
		if (apostaComprada == null) {
			return null;
		}
		return apostaComprada.getPremio();
	}

	public void setPremio(HistoricoPremio premio) {
		initApostaOriginal();
		apostaComprada.setPremio(premio);
	}

	public SituacaoAposta getSituacao() {
		if (apostaComprada == null) {
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
		if (apostaComprada == null) {
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
			apostaComprada = new HistoricoApostaComprada();
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

	public HistoricoApostaComprada getApostaComprada() {
		return apostaComprada;
	}

	public void setApostaComprada(HistoricoApostaComprada apostaComprada) {
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
		vo.setIndicadorSurpresinha(indicadorSurpresinha);
		vo.setTipoConcurso(tipoConcurso);
		vo.setValor(valor);
		vo.setId(id.getId());
		vo.setConcursoAlvo(concursoAlvo);
		return vo;
	}

	public boolean isSorteada() {
		return sorteada;
	}

	public void setSorteada(boolean sorteada) {
		this.sorteada = sorteada;
	}

	public abstract boolean contemTeimosinha();

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

	/**
	 * @return the ano
	 */
	public Long getAno() {
		return ano;
	}

	/**
	 * @param ano
	 *            the ano to set
	 */
	public void setAno(Long ano) {
		this.ano = ano;
	}

	public ComboAposta getComboAposta() {
		return comboAposta;
	}

	public void setComboAposta(ComboAposta comboAposta) {
		this.comboAposta = comboAposta;
	}

	public Boolean getIndicadorBolao() {
		return indicadorBolao;
	}

	public void setIndicadorBolao(Boolean indicadorBolao) {
		this.indicadorBolao = indicadorBolao;
	}

	public HistoricoReservaCotaBolao getReservaCotaBolao() {
		return reservaCotaBolao;
	}

	public void setReservaCotaBolao(HistoricoReservaCotaBolao reservaCotaBolao) {
		this.reservaCotaBolao = reservaCotaBolao;
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

	public HistoricoApostaLotomania getApostaOriginalEspelho() {
		return apostaOriginalEspelho;
	}

	public void setApostaOriginalEspelho(HistoricoApostaLotomania apostaOriginalEspelho) {
		this.apostaOriginalEspelho = apostaOriginalEspelho;
	}
}