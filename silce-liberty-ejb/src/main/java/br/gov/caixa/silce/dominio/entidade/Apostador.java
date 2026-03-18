package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.jogos.Sexo;
import br.gov.caixa.silce.dominio.openjpa.CEPValueHandler;
import br.gov.caixa.silce.dominio.openjpa.CPFValueHandler;
import br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.silce.dominio.openjpa.EmailValueHandler;
import br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler;
import br.gov.caixa.util.CEP;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Email;
import br.gov.caixa.util.StringUtil;
import br.gov.caixa.util.Telefone;

@Entity
@Table(name = "LCETB002_APOSTADOR", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name = Apostador.NQ_SELECT_BY_CPF, query = "Select a from Apostador a where a.cpf=?1"),
	@NamedQuery(name = Apostador.NQ_SELECT_BY_EMAIL, query = "Select a from Apostador a where a.email=?1"),
	@NamedQuery(name = Apostador.NQ_SELECT_BY_DATAULTIMOACESSOMAXIMA_SITUACAO, query = "Select a from Apostador a where a.dataUltimoAcesso <= ?1 and a.situacao.id = ?2"),
	@NamedQuery(name = Apostador.NQ_SELECT_BY_DATAULTIMOACESSO_SITUACAO, query = "Select a from Apostador a where a.dataUltimoAcesso = ?1 and a.situacao.id = ?2"),
	@NamedQuery(name = Apostador.NQ_SELECT_BY_DIFFERENTID_CPF, query = "Select a from Apostador a where a.id <>?1 and a.cpf=?2"),
	@NamedQuery(name = Apostador.NQ_SELECT_COUNT, query = "Select count(a.id) from Apostador a"),
	@NamedQuery(name = Apostador.NQ_SELECT_COUNT_APP, query = "Select count(a.id) from Apostador a where a.subcanal = ?1"),
	@NamedQuery(name = Apostador.NQ_SELECT_ID_BY_CPF, query = "Select a.id from Apostador a where a.cpf=?1",hints = { @QueryHint(name = "openjpa.FetchPlan.Isolation", value = "READ_UNCOMMITTED") }) })
@NamedNativeQueries({
		@NamedNativeQuery(name = Apostador.NQ_SELECT_COUNT_COMPRARAM, query = "SELECT CONSULTA.SUBCANAL,"
			+ " COUNT (CONSULTA.SUBCANAL) AS VALOR_SUBCANAL FROM ("
			+ "SELECT DISTINCT c.NU_APOSTADOR AS apostador, COALESCE (c.IC_SUBCANAL_PAGAMENTO,1) AS subcanal"
			+ "	FROM lce.LCETB013_COMPRA c"
			+ "	WHERE c.DT_FINALIZACAO_COMPRA BETWEEN ?1 AND ?2	AND	(c.NU_SITUACAO_COMPRA = 8 OR c.NU_SITUACAO_COMPRA = 14 OR c.NU_SITUACAO_COMPRA = 13)"
			+ ") AS CONSULTA GROUP BY SUBCANAL WITH UR"),
		@NamedNativeQuery(name = Apostador.NQ_SELECT_COUNT_CADASTRARAM, query = "SELECT CONSULTA.SUBCANAL," +
			" COUNT (CONSULTA.SUBCANAL) AS VALOR_SUBCANAL FROM (" +
			"SELECT COALESCE(ap.IC_SUBCANAL_CADASTRO,1) AS SUBCANAL, ap.NU_APOSTADOR AS APOSTADOR" +
			" FROM LCE.LCETB002_APOSTADOR ap " +
			" WHERE ap.TS_CADASTRO BETWEEN ?1 AND ?2 ) AS CONSULTA GROUP BY SUBCANAL WITH UR"),
		@NamedNativeQuery(name = Apostador.NQ_SELECT_COUNT_CADASTRARAM_TOTAL, query = "SELECT CONSULTA.SUBCANAL,"
			+ " COUNT (CONSULTA.SUBCANAL) AS VALOR_SUBCANAL FROM (" +
			"SELECT COALESCE(ap.IC_SUBCANAL_CADASTRO,1) AS SUBCANAL, ap.NU_APOSTADOR AS APOSTADOR" +
			" FROM LCE.LCETB002_APOSTADOR ap) AS CONSULTA GROUP BY SUBCANAL" +
			" UNION " +
			"SELECT 0 AS SUBCANAL,COUNT (DISTINCT ap.NU_APOSTADOR) AS VALOR_SUBCANAL FROM LCE.LCETB002_APOSTADOR ap WITH UR"),
		@NamedNativeQuery(name = Apostador.NQ_SELECT_COUNT_CADASTRADOS_PREVIAMENTE, query = "SELECT COUNT(*) FROM LCE.LCETB002_APOSTADOR "
			+ "WHERE LCE.LCETB002_APOSTADOR.TS_CADASTRO <= ?1 WITH UR", resultClass = Long.class),
		@NamedNativeQuery(name = Apostador.NQ_SELECT_COUNT_NAO_COMPRARAM, query = "SELECT CONSULTA.SUBCANAL,"
			+ " COUNT (CONSULTA.SUBCANAL) AS VALOR_SUBCANAL FROM (" +
			"SELECT DISTINCT ap.NU_APOSTADOR AS apostador,COALESCE (ap.IC_SUBCANAL_CADASTRO,1) AS subcanal" +
			" FROM LCE.LCETB002_APOSTADOR ap WHERE ap.NU_APOSTADOR NOT IN" +
			"(SELECT DISTINCT cp.NU_APOSTADOR FROM lce.LCETB013_COMPRA cp WHERE (cp.NU_SITUACAO_COMPRA = 8 OR cp.NU_SITUACAO_COMPRA = 14 OR cp.NU_SITUACAO_COMPRA = 13) AND"
			+ " cp.DT_FINALIZACAO_COMPRA BETWEEN ?1 AND ?2)) AS CONSULTA GROUP BY SUBCANAL WITH UR"),
		@NamedNativeQuery(name = Apostador.NQ_SELECT_COUNT_APENAS_COMPRAS_NEGADAS, query = "SELECT CONSULTA.SUBCANAL,"
			+ " COUNT (DISTINCT CONSULTA.APOSTADOR) AS VALOR_SUBCANAL FROM ("
			+ "SELECT DISTINCT COALESCE(C.IC_SUBCANAL_PAGAMENTO,1) AS SUBCANAL, C.NU_APOSTADOR AS APOSTADOR"
			+ " FROM LCE.LCETB013_COMPRA C WHERE C.NU_SITUACAO_COMPRA = 3 AND"
			+ " C.TS_ALTERACAO_SITUACAO BETWEEN ?1 AND ?2 AND NOT EXISTS (SELECT C2.NU_APOSTADOR FROM"
			+ " LCE.LCETB013_COMPRA C2 WHERE C2.NU_SITUACAO_COMPRA!=3 AND C2.NU_SITUACAO_COMPRA!=1"
			+ " AND C2.TS_ALTERACAO_SITUACAO BETWEEN ?1 AND ?2"
			+ " AND C2.NU_APOSTADOR= C.NU_APOSTADOR)) AS CONSULTA GROUP BY CONSULTA.SUBCANAL WITH UR"),
		@NamedNativeQuery(name = Apostador.NQ_SELECT_BY_CPF_NOME, query = "Select * from LCE.LCETB002_APOSTADOR a where a.NU_CPF= ?1 and UPPER(TRANSLATE(a.NO_APOSTADOR, 'aaaaeeiooouc','ãâáàéêíõôóúç'))  = ?2", resultClass = Apostador.class),

})
public class Apostador extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_CPF = "Apostador.findByCPF";
	public static final String NQ_SELECT_BY_CPF_NOME = "Apostador.NQ_SELECT_BY_NOME";
	public static final String NQ_SELECT_BY_EMAIL = "Apostador.findByEmail";
	public static final String NQ_SELECT_BY_DATAULTIMOACESSOMAXIMA_SITUACAO = "Apostador.findByDataMaximaUltimoAcesso";
	public static final String NQ_SELECT_BY_DATAULTIMOACESSO_SITUACAO = "Apostador.findByDataUltimoAcesso";
	public static final String NQ_SELECT_BY_DIFFERENTID_CPF = "Apostador.findByCpfWithDifferentId";
	public static final String NQ_SELECT_ID_BY_CPF = "Apostador.NQ_SELECT_ID_BY_CPF";
	private static final long serialVersionUID = 1L;
	public static final String NQ_SELECT_COUNT = "Apostador.NQ_SELECT_COUNT";
	public static final String NQ_SELECT_COUNT_COMPRARAM = "Apostador.NQ_SELECT_COUNT_COMPRARAM";
	public static final String NQ_SELECT_COUNT_CADASTRARAM = "Apostador.NQ_SELECT_COUNT_CADASTRARAM";
	public static final String NQ_SELECT_COUNT_CADASTRARAM_TOTAL = "Apostador.NQ_SELECT_COUNT_CADASTRARAM_TOTAL";
	public static final String NQ_SELECT_COUNT_CADASTRADOS_PREVIAMENTE = "Apostador.NQ_SELECT_COUNT_CADASTRADOS_PREVIAMENTE";
	public static final String NQ_SELECT_COUNT_APENAS_COMPRAS_NEGADAS = "Apostador.NQ_SELECT_COUNT_APENAS_COMPRAS_NEGADAS";
	public static final String NQ_SELECT_COUNT_APP = "Apostador.NQ_SELECT_COUNT_APP";
	public static final String NQ_SELECT_COUNT_NAO_COMPRARAM = "Apostador.NQ_SELECT_COUNT_NAO_COMPRARAM";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_APOSTADOR")
	private Long id;

	@Column(name = "NU_CPF")
	@Strategy(CPFValueHandler.STRATEGY_NAME)
	private CPF cpf;

	@Column(name = "DE_EMAIL")
	@Strategy(EmailValueHandler.STRATEGY_NAME)
	private Email email;

	@Column(name = "NO_APOSTADOR")
	private String nome;

	@Transient
	private String primeiroNome;

	@Column(name = "DT_NASCIMENTO")
	@Temporal(TemporalType.DATE)
	@Strategy(DataValueHandler.STRATEGY_NAME)
	private Data dataNascimento;

	@Column(name = "DT_ULTIMO_ACESSO")
	@Temporal(TemporalType.DATE)
	@Strategy(DataValueHandler.STRATEGY_NAME)
	private Data dataUltimoAcesso;

	@Column(name = "TS_ULTIMO_ERRO_LOGIN")
	@Temporal(TemporalType.TIMESTAMP)
	@Strategy(DataValueHandler.STRATEGY_NAME)
	private Data dataAtualizacaoContadorTentativasAcesso;

	@Column(name = "TS_BLOQUEIO_APOSTADOR")
	@Temporal(TemporalType.TIMESTAMP)
	@Strategy(DataValueHandler.STRATEGY_NAME)
	private Data dataFimBloqueioVinculacao;

	@Column(name = "QT_ERRO_LOGIN")
	private Integer contadorTentativasAcesso;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "ddd", column = @Column(name = "CO_DDD")),
		@AttributeOverride(name = "numero", column = @Column(name = "NU_CELULAR"))
	})
	private Telefone telefone;

	@Column(name = "NU_CEP")
	@Strategy(CEPValueHandler.STRATEGY_NAME)
	private CEP cep;

	@Transient
	private Boolean aceitaTermosUso;

	@Transient
	private Notificacao ultimaNotificacao = null;

	@Column(name = "IC_NOTICIA")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean aceitaReceberNoticias;

	@ManyToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name = "NU_DV_IBGE", referencedColumnName = "NU_DV_IBGE"),
		@JoinColumn(name = "NU_MUNICIPIO_IBGE", referencedColumnName = "NU_MUNICIPIO_IBGE"),
		@JoinColumn(name = "NU_UF_IBGE_L99", referencedColumnName = "NU_UF_IBGE_L99")
	})
	@EagerFetchMode(FetchMode.PARALLEL)
	private Municipio municipio;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_SITUACAO_APOSTADOR")
	@EagerFetchMode(FetchMode.PARALLEL)
	private SituacaoApostador situacao;

	@Column(name = "TS_CADASTRO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataCadastro;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_CD")
	@EagerFetchMode(FetchMode.NONE)
	private Loterica loterica;

	@OneToOne(optional = true)
	@JoinColumn(name = "NU_CONFIGURACAO_RAPIDAO")
	@EagerFetchMode(FetchMode.NONE)
	private Rapidao rapidao;

	@Column(name = "IC_SEXO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Sexo sexo;

	@Column(name = "NU_VERSAO_TERMO_ADESAO")
	@ManyToOne
	private TermoDeUso ultimoTermoDeUsoAceito;

	@Column(name = "TS_ACEITE_TERMO_ADESAO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataUltimoAceiteTermoDeUso;

	@Column(name = "DE_CUSTOMER_MERCADO_PAGO")
	private String mercadoPagoCustomerId;

	@Column(name = "TS_SELECAO_LOTERICA")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataSelecaoLoterica;

	@Column(name = "VR_MXMO_SLNDO_APSTA_DIA")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal limiteDiario;

	@Column(name = "VR_MXMO_APROVADO_APSTA_DIA")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal limiteDiarioAutorizado;

	@Column(name = "DT_ALT_VR_MXMO_SELECIONADO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataCalculoLimiteDiarioAutorizado;

	@Column(name = "IC_SUBCANAL_CADASTRO")
	@Strategy(CaixaEnumValueHandler.STRATEGY_NAME)
	private Subcanal subcanal;

	@Column(name = "IC_RESPOSTA")
	@Strategy(SimNaoValueHandler.STRATEGY_NAME)
	private Boolean autoAvaliacaoRespondida;

	@Column(name = "TS_AVALIACAO_RESPOSTA")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataRespostaAutoavaliacao;

	@Column(name = "TS_AVALIACAO_PROXIMA_RESPOSTA")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataProximaRespostaAutoavaliacao;
	
	@Column(name = "TS_ULTIMA_CONSULTA_BLOQUEIO")
	@Temporal(TemporalType.TIMESTAMP)
	@Strategy(DataValueHandler.STRATEGY_NAME)
	private Data dataUltimaConsultaSicow;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public Loterica getLoterica() {
		return loterica;
	}

	public void setLoterica(Loterica loterica) {
		this.loterica = loterica;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public CEP getCep() {
		return cep;
	}

	public void setCep(CEP cep) {
		this.cep = cep;
	}

	public Boolean getAceitaTermosUso() {
		return aceitaTermosUso;
	}

	public void setAceitaTermosUso(Boolean aceitaTermosUso) {
		this.aceitaTermosUso = aceitaTermosUso;
	}

	public Boolean getAceitaReceberNoticias() {
		return aceitaReceberNoticias;
	}

	public void setAceitaReceberNoticias(Boolean aceitaReceberNoticias) {
		this.aceitaReceberNoticias = aceitaReceberNoticias;
	}

	public SituacaoApostador getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoApostador situacao) {
		this.situacao = situacao;
	}

	public Data getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Data dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean isCarteiraVinculada() {
		return telefone != null && telefone.getDdd() != null && telefone.getNumero() != null;
	}

	public CPF getCpf() {
		return cpf;
	}

	public void setCpf(CPF cpf) {
		this.cpf = cpf;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
		this.primeiroNome = null;
	}

	public Data getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Data dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Data getDataAtualizacaoContadorTentativasAcesso() {
		return dataAtualizacaoContadorTentativasAcesso;
	}

	public int getContadorTentativasAcesso() {
		return contadorTentativasAcesso == null ? 0 : contadorTentativasAcesso;
	}

	/**
	 * Ao incrementar o contador de falha de tentativas de acesso, a data de atualizaÃ§Ã£o do contador Ã© atualizada
	 * tambÃ©m.
	 */
	public void incrementaContadorTentativasAcesso() {
		this.contadorTentativasAcesso = getContadorTentativasAcesso() + 1;
		this.dataAtualizacaoContadorTentativasAcesso = DataUtil.getTimestampAtual();
	}

	public void zeraContadorTentativasAcesso() {
		this.contadorTentativasAcesso = 0;
	}

	public Data getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Data dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public Data getDataFimBloqueioVinculacao() {
		return dataFimBloqueioVinculacao;
	}

	public void setDataFimBloqueioVinculacao(Data dataFimBloqueioVinculacao) {
		this.dataFimBloqueioVinculacao = dataFimBloqueioVinculacao;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public ApostadorVO getVO() {
		ApostadorVO apostadorVO = new ApostadorVO();
		apostadorVO.setAceitaReceberNoticias(getAceitaReceberNoticias());
		apostadorVO.setAceitaTermosUso(getAceitaTermosUso());
		apostadorVO.setCep(getCep());
		apostadorVO.setContadorTentativasAcesso(getContadorTentativasAcesso());
		apostadorVO.setCpf(getCpf());
		apostadorVO.setDataAtualizacaoContadorTentativasAcesso(getDataAtualizacaoContadorTentativasAcesso());
		apostadorVO.setDataCadastro(getDataCadastro());
		apostadorVO.setDataFimBloqueioVinculacao(getDataFimBloqueioVinculacao());
		apostadorVO.setDataNascimento(getDataNascimento());
		apostadorVO.setDataUltimoAcesso(getDataUltimoAcesso());
		apostadorVO.setEmail(getEmail());
		apostadorVO.setId(getId());
		apostadorVO.setSubcanal(getSubcanal());
		if (getLoterica() != null) {
			apostadorVO.setLotericaId(getLoterica().getId());
		}
		if (getMunicipio() != null) {
			apostadorVO.setMunicipioCodigoUf(getMunicipio().getId().getCodigoUF());
			apostadorVO.setMunicipioDigitoVerificador(getMunicipio().getId().getDigitoVerificador());
			apostadorVO.setMunicipioNumero(getMunicipio().getId().getNumero());
		}
		apostadorVO.setNome(getNome());
		if (getSituacao() != null) {
			apostadorVO.setSituacaoId(getSituacao().getEnum().getValue());
		}
		if (getTelefone() != null) {
			apostadorVO.setTelefoneDdd(getTelefone().getDdd());
			apostadorVO.setTelefoneNumero(getTelefone().getNumero());
		}
		return apostadorVO;
	}

	public String getSobrenome() {
		if (StringUtil.isEmpty(nome)) {
			return null;
		}
		return nome.substring(nome.indexOf(' ') + 1, nome.length());
	}

	public String getPrimeiroNome() {
		if (StringUtil.isEmpty(nome)) {
			return null;
		}
		int indexOf = nome.indexOf(' ');
		if (indexOf <= 0) {
			return nome;
		}
		return nome.substring(0, indexOf);
	}

	@Override
	public String toString() {
		StringBuilder builder = createToStringBuilder();
		builder.append("Apostador [id=").append(id).append(", cpf=").append(cpf).append(", email=").append(email)
				.append(", nome=").append(nome)
			.append(", primeiroNome=").append(primeiroNome)
				.append(", dataNascimento=")
				.append(dataNascimento).append(", dataUltimoAcesso=").append(dataUltimoAcesso)
				.append(", dataAtualizacaoContadorTentativasAcesso=").append(dataAtualizacaoContadorTentativasAcesso)
				.append(", dataFimBloqueioVinculacao=").append(dataFimBloqueioVinculacao)
				.append(", contadorTentativasAcesso=")
				.append(contadorTentativasAcesso).append(", telefone=").append(telefone).append(", cep=").append(cep)
				.append(", aceitaTermosUso=").append(aceitaTermosUso).append(", aceitaReceberNoticias=")
				.append(aceitaReceberNoticias)
				.append(", municipio=").append(municipio).append(", situacao=").append(situacao).append(", dataCadastro=")
				.append(dataCadastro).append(", loterica=").append(loterica).append(']');
		return builder.toString();
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Data getDataUltimoAceiteTermoDeUso() {
		return dataUltimoAceiteTermoDeUso;
	}

	public void setDataUltimoAceiteTermoDeUso(Data dataUltimoAceiteTermoDeUso) {
		this.dataUltimoAceiteTermoDeUso = dataUltimoAceiteTermoDeUso;
	}

	public TermoDeUso getUltimoTermoDeUsoAceito() {
		return ultimoTermoDeUsoAceito;
	}

	public void setUltimoTermoDeUsoAceito(TermoDeUso ultimoTermoDeUsoAceito) {
		this.ultimoTermoDeUsoAceito = ultimoTermoDeUsoAceito;
	}

	public String getMercadoPagoCustomerId() {
		return mercadoPagoCustomerId;
	}

	public void setMercadoPagoCustomerId(String mercadoPagoCustomerId) {
		this.mercadoPagoCustomerId = mercadoPagoCustomerId;
	}

	public Data getDataSelecaoLoterica() {
		return dataSelecaoLoterica;
	}

	public void setDataSelecaoLoterica(Data dataSelecaoLoterica) {
		this.dataSelecaoLoterica = dataSelecaoLoterica;
	}

	public Rapidao getRapidao() {
		return rapidao;
	}

	public void setRapidao(Rapidao rapidao) {
		this.rapidao = rapidao;
	}

	public Decimal getLimiteDiario() {
		return limiteDiario;
	}

	public void setLimiteDiario(Decimal limiteDiario) {
		this.limiteDiario = limiteDiario;
	}

	public Decimal getLimiteDiarioAutorizado() {
		return limiteDiarioAutorizado;
	}

	public void setLimiteDiarioAutorizado(Decimal limiteDiarioAutorizado) {
		this.limiteDiarioAutorizado = limiteDiarioAutorizado;
		setDataCalculoLimiteDiarioAutorizado(DataUtil.getDataAtual());
	}

	public Data getDataCalculoLimiteDiarioAutorizado() {
		return dataCalculoLimiteDiarioAutorizado;
	}

	public void setDataCalculoLimiteDiarioAutorizado(Data dataCalculoLimiteDiarioAutorizado) {
		this.dataCalculoLimiteDiarioAutorizado = dataCalculoLimiteDiarioAutorizado;
	}

	public Boolean getTemNovasNotificacoes() {
		return ultimaNotificacao != null;
	}

	public Notificacao getUltimaNotificacao() {
		return ultimaNotificacao;
	}

	public void setUltimaNotificacao(Notificacao ultimaNotificacao) {
		this.ultimaNotificacao = ultimaNotificacao;
	}

	public Subcanal getSubcanal() {
		return subcanal;
	}

	public void setSubcanal(Subcanal subcanal) {
		this.subcanal = subcanal;
	}

	public Boolean getAutoAvaliacaoRespondida() {
		return autoAvaliacaoRespondida;
	}

	public void setAutoAvaliacaoRespondida(Boolean autoAvaliacaoRespondida) {
		this.autoAvaliacaoRespondida = autoAvaliacaoRespondida;
	}

	public Data getDataRespostaAutoavaliacao() {
		return dataRespostaAutoavaliacao;
	}

	public void setDataRespostaAutoavaliacao(Data dataRespostaAutoavaliacao) {
		this.dataRespostaAutoavaliacao = dataRespostaAutoavaliacao;
	}

	public Data getDataProximaRespostaAutoavaliacao() {
		return dataProximaRespostaAutoavaliacao;
	}

	public void setDataProximaRespostaAutoavaliacao(Data dataProximaRespostaAutoavaliacao) {
		this.dataProximaRespostaAutoavaliacao = dataProximaRespostaAutoavaliacao;
	}

	public Data getDataUltimaConsultaSicow() {
		return dataUltimaConsultaSicow;
	}

	public void setDataUltimaConsultaSicow(Data dataUltimaConsultaSicow) {
		this.dataUltimaConsultaSicow = dataUltimaConsultaSicow;
	}

}
