package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.openjpa.persistence.jdbc.EagerFetchMode;
import org.apache.openjpa.persistence.jdbc.FetchMode;
import org.apache.openjpa.persistence.jdbc.Strategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.gov.caixa.dominio.AbstractEntidadeEnum;
import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.entidade.TipoDado.Tipo;
import br.gov.caixa.silce.dominio.jogos.TipoConcurso;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.ConversorUtil;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.EnumUtil;
import br.gov.caixa.util.Hora;
import br.gov.caixa.util.IntervaloData;
import br.gov.caixa.util.IntervaloHora;

/**
 * @author c101482
 */
@Entity
@Table(name = "LCETB007_PARAMETRO", schema = DatabaseConfig.SCHEMA)
public class Parametro extends AbstractEntidadeEnum<Long, Parametro.ParametroSistema> {

	private static final long serialVersionUID = 1L;

	private static final Gson GSON = new GsonBuilder().create();

	public enum ParametroSistema implements CaixaEnum<Long> {

		/** VALOR MAXIMO DE APOSTAS QUE PODEM SER REALIZADAS POR DIA PELO APOSTADOR */
		VALOR_MAXIMO_DE_APOSTAS_POR_DIA(1L),

		/** PERÍODO MAXIMO QUE O APOSTADOR PODE FICAR SEM ACESSAR SUA CONTA */
		PERIODO_PARA_EXCLUSAO_DE_CONTA_DE_APOSTADOR_INATIVO(2L),

		/**
		 * QUANTIDADE MÁXIMA DE TENTATIVAS DE VINCULACAO DA CARTEIRA ELETRÔNICA COM DADOS INVÁLIDOS POR SESSÃO PARA QUE
		 * O APOSTADOR SEJA BLOQUEADO
		 */
		QTD_DE_TENTATIVAS_DE_VINCULACAO_DA_CARTEIRA_ELETRONICA(3L),

		/**
		 * TEMPO QUE O APOSTADOR FICARÁ BLOQUEADO POR EXCEDER O PARÂMETRO DE CONTROLE DE VINCULAÇÃO DA CARTEIRA
		 * ELETRÔNICA
		 */
		TEMPO_BLOQUEIO_VINCULACAO_CARTEIRA_ELETRONICA(4L),

		/** QUANTIDADE MÁXIMA DE JOGOS NO CARRINHO DE APOSTAS */
		QTD_MAXIMA_DE_APOSTAS_NO_CARRINHO_DE_APOSTAS(5L),

		/** QUANTIDADE MÁXIMA DE VALIDAÇÕES DO CÓDIGO DE RESGATE DE PRÊMIO NA UNIDADE LOTÉRICA */
		QTD_MAXIMA_VALIDACOES_CODIGO_RESGATE_PREMIO_UNIDADE_LOTERICA(6L),

		/**
		 * TEMPO MÁXIMO, EM MINUTOS, QUE UMA APOSTA PREMIADA QUE CONTÉM TEIMOSINHAS VÁLIDAS PARA O PRÓXIMO CONCURSO PODE
		 * SER RESGATADA, ANTES DO FECHAMENTO DESSE CONCURSO
		 */
		TEMPO_MAXIMO_PARA_RESGATE_DE_PREMIOS_EM_MINUTOS(7L),

		HABILITA_LISTA_BRANCA(8L),

		INTERVALO_MANUTENCAO_SISPL(9L),
		VALOR_MINIMO_CARRINHO(10L),
		TEMPO_VALIDACAO_TOKEN_WEB(11L),
		TEMPO_VALIDACAO_TOKEN_APP(12L),
		PERCENTUAL_REPASSE_GOVERNO(13L),
		VALOR_PREMIO_LIMITE_CONSULTA_BILHETE(14L),
		BLOQUEIO_SELECAO_UL_POR_NOME(15L),
		BLOQUEIO_SELECAO_UL_POR_CODIGO(16L),
		BLOQUEIO_SELECAO_UL_POR_CEP(17L),

		/**
		 * Habilita que o apostador escolha sua lotérica de vinculação.
		 */
		HABILITA_SELECAO_LOTERICA_VINCULACAO(18L),

		/**
		 * Caso o parâmetro 18 esteja desabilitado, define a lotérica que será vinculada às compras e apostas.
		 */
		LOTERICA_FIXA(19L),
		
		PERCENTUAL_REPASSE_EDUCACAO(20L),
		PERCENTUAL_REPASSE_CULTURA(21L),
		PERCENTUAL_REPASSE_ESPORTE(22L),
		PERCENTUAL_REPASSE_SEGURANCA(23L),
		PERCENTUAL_REPASSE_SEGURIDADE(24L),
		PERCENTUAL_REPASSE_OUTROS(25L),

		EXIBE_DETALHAMENTO_CONCURSOS_PREMIADOS(26L),
		EXIBE_VALOR_CONCURSOS_PREMIADOS(27L),
		EXIBE_DETALHAMENTO_FAIXAS_PREMIADAS(28L),
		EXIBE_VALOR_FAIXAS_PREMIADAS(29L),
		
		/**
		 * Parâmetros utilizados para aumentar o limite diário de apostas por apostador
		 */
		QTD_COMPRAS_EFETIVADAS(30L),
		QTD_COMPRAS_CANCELADAS(31L),
		QTD_COMPRAS_DEBITO_NAO_AUTORIZADO(32L),
		PERCENTUAL_LIMITE_VIGENTE(33L),
		PERCENTUAL_AUMENTO_LIMITE(34L),
		TEMPO_CALCULO_LIMITE(35L),
		ESCALA_CONVERSAO_PONTOS_REAIS(36L),
		HABILITA_USO_PONTOS(37L),
		PERCENTUAL_RENDA_JOGO_RESPONSAVEL(38L),
		HABILITA_SIMULADOR_JOGO_RESPONSAVEL(39L),
		HABILITA_AJUSTE_LIMITE_JOGO_RESPONSAVEL(40L), 

		HABILITA_NOVA_VERSAO_ARQ_MERCADO_PAGO(41L),
		LINK_DOWNLOAD(42L),
		HABILITA_MOSTRA_PAGINA(43L),
		VERSAO_APP_IOS(44L),
		HABILITA_REQ_APP(45L),
		BLOQUEIA_MODALIDADE_POR_CANAL(46L), 
		HABILITA_MENSAGEM(47L),
		INTERVALO_MANUTENCAO_SISPL_CONFERENCIA_RESGATE_PREMIO(48L),
		HABILITA_BLOQUEIO_CONFERENCIA_RESGATE_PREMIO(49L),
		OCULTA_MODALIDADE(50L),
		VALOR_ESTIMATIVA_ESPECIAL_MANUAL(51L),
		TEMPO_CARRINHO_FAVORITO_REPETIDO(52L),
		TEMPO_ETIQUETA_NOVO(53L),
		HABILITA_REQ_APP_ANDROID(54L),
		VERSAO_APP_ANDROID(55L),
		/**
		 * parâmetros de timout
		 */
		TIMEOUT_SISPL2_ENCERRA_CAPTACAO(56L),
		TIMEOUT_SISPL2_CANCELA_PAGAMENTO_PREMIO(57L), 
		TIMEOUT_SISPL2_CONFERE_PREMIO(58L),
		TIMEOUT_SISPL2_PAGA_PREMIO(59L),
		TIMEOUT_SISPL2_CONFIRMA_PAGAMENTO_PREMIO(60L),
		TIMEOUT_SISPL2_BUSCA_PARAMETROS_MODALIDADE_CONCURSO(61L),
		TIMEOUT_SISPL2_BUSCA_PARAMETROS_NAO_INICIALIZADOS(62L),
		TIMEOUT_SISPL2_BUSCA_PARAMETROS_ABERTOS(63L),
		TIMEOUT_GOOGLE_RECAPTCHA(64L),
		TIMEOUT_KEYCLOAK_GEN(65L),
		TIMEOUT_KEYCLOAK_INTROSPECT(66L),
		TIMEOUT_MERCADO_PAGO_CRIA_PAGAMENTO_COMPRA(67L),
		TIMEOUT_MERCADO_PAGO_CRIA_CUSTOMER_ID(68L),
		TIMEOUT_MERCADO_PAGO_CONSULTA_CUSTOMER(69L),
		TIMEOUT_MERCADO_PAGO_CONSULTA_CUSTOMER_ID(70L),
		TIMEOUT_MERCADO_PAGO_ADD_CARTAO(71L),
		TIMEOUT_MERCADO_PAGO_CONSULTA_CARTOES(72L),
		TIMEOUT_MERCADO_PAGO_EXCLUI_CARTOES(73L),
		TIMEOUT_MERCADO_PAGO_CRIA_PAGAMENTO_PREMIO(74L),
		TIMEOUT_MERCADO_PAGO_CONSULTA_PAGAMENTO_COMPRA_BY_EXTERNAL_REFERENCE(75L),
		TIMEOUT_MERCADO_PAGO_CONSULTA_PAGAMENTO_COMPRA_BY_EXTERNAL_REFERENCE_DETALHADO(76L),
		TIMEOUT_MERCADO_PAGO_CONSULTA_PAGAMENTO_PREMIO_BY_EXTERNAL_REFERENCE(77L),
		TIMEOUT_MERCADO_PAGO_CONSULTA_PAGAMENTO_PREMIO_BY_EXTERNAL_REFERENCE_DETALHADO(78L),
		TIMEOUT_MERCADO_PAGO_CONSULTA_PAGAMENTO_COMPRA_BY_ID(79L),
		TIMEOUT_MERCADO_PAGO_DEVOLUCAO_PAGAMENTO(80L),
		TIMEOUT_MERCADO_PAGO_PREPARA_CADASTRO_USUARIO(81L),
		TIMEOUT_MERCADO_PAGO_CRIA_PREFERENCIA_PAGAMENTO_COMPRA(82L),

		HABILITA_MOSTRAR_PAGINA_6_MESES_MAIS(83L),
		HABILITA_LINK_PAGINA_ALTERACAO_LOGIN_SSO(84L),
		HABILITA_CONSULTA_RESULTADO_CONCURSO_API_MANAGER(85L),
		TIMEOUT_API_MANAGER_CONSULTA_CONCURSO_PUBLICADO(86L),
		HABILITA_CONFERENCIA_AUTOMATICA_RESULTADO_APOSTAS(87L),
		TIMEOUT_API_MANAGER_CONSULTA_ULTIMO_RESULTADO(88L),
		HOST_MICRO_SERVICO_CONFERENCIA_APOSTAS(89L),
		HABILITA_CONSULTA_MINHAS_APOSTAS(90L),
		HABILITA_CACHE_PUBLIC_KEY_CLIENTE(91L),
		TEMPO_CACHE_MEIOS_PAGAMENTO(92L),
		RECUPERAR_MEIO_PAGAMENTO(93L),
		TIMESTAMP_ALTERACAO_ESCUDO(94L),
		TIMEOUT_RECARGA_PAY_APPLICATION_TOKEN(95L),
		TIMEOUT_RECARGA_PAY_APOSTADOR_TOKEN(96L),
		TIMEOUT_RECARGA_PAY_CRIA_PAGAMENTO_COMPRA(97L),
		TIMEOUT_RECARGA_PAY_CANCELA_PAGAMENTO_COMPRA(98L),
		TIMEOUT_RECARGA_PAY_CANCELA_PAGAMENTO_APOSTA(99L),
		TIMEOUT_RECARGA_PAY_CONSULTA_PAGAMENTO_COMPRA_BY_ID(100L),
		TIMEOUT_API_MANAGER_CONSULTA_HISTORICO_BILHETE(101L),
		TIMEOUT_RECARGA_PAY_CONSULTA_PAGAMENTO_COMPRA_BY_EXTERNAL_REFERENCE(102L),

		EXIBE_CANAL_DENUNCIAS(103L),

		TIMEOUT_RECARGA_PAY_CONSULTA_CARTOES(105L), 
		TIMEOUT_RECARGA_PAY_EXCLUI_CARTAO(106L),
		
		PIX_TEMPO_EXPIRACAO_SEGUNDOS_COBRANCA_DINAMICA(107L),
		PIX_TEMPO_EXPIRACAO_MINIMO_SEGUNDOS_COBRANCA_DINAMICA(108L),
		TIMEOUT_PIX_GERA_COBRANCA(109L),
		TIMEOUT_PIX_SOLICITA_DEVOLUCAO(110L),
		TIMEOUT_PIX_CONSULTA_COBRANCA(111L),
		TIMEOUT_PIX_CONSULTA_PAGAMENTO(112L),
		TIMEOUT_PIX_CONSULTA_DEVOLUCAO(113L),
		TIMEOUT_PIX_CONSULTA_QRCODE(114L),

		TEMPO_ADICIONAL_EM_MINUTOS_CREDITO_PREMIO(115L),

		HOST_MICRO_SERVICO_CARRINHO_PILOTO(116L),
		HOST_MICRO_SERVICO_CARRINHO_PRODUCAO(117L),

		TEMPOS_PERMITIDOS_SUSPENSAO_CONTA_APOSTADOR(118L),

		TEMPO_EM_DIAS_EXPURGO_APOSTAS_MICRO_SERVICO_CARRINHO(121L),

		TIMEOUT_MARKETPLACE_CONSULTAR_BOLOES(119L),
		TIMEOUT_MARKETPLACE_CONSULTAR_DETALHAMENTO_BOLAO(120L),
		TIMEOUT_MARKETPLACE_REALIZAR_RESERVA(122L),
		TIMEOUT_MARKETPLACE_CANCELAR_RESERVA(123L),
		TIMEOUT_MARKETPLACE_CONFIRMAR_RESERVA(124L),
		TIMEOUT_MARKETPLACE_CONSULTAR_COTAS_APOSTADOR(125L),
		TIMEOUT_MARKETPLACE_ENCERRAMENTO_REVENDA(126L),
		VALIDA_ENCERRAMENTO_REVENDA(127L),
		HABILITA_REVENDA_MARKETPLACE(128L),
		TIMEOUT_MARKETPLACE_ENCERRA_REVENDA(129L),
		TEMPO_MAXIMO_MILISSEGUNDOS_TRANSACAO_CONTABILIZACAO_RESERVAS_COTAS_FECHAMENTO_DIARIO(130L),
		QTD_MAXIMA_DE_COTAS_NO_CARRINHO_DE_APOSTAS(131L),
		GRUPO_AZURE_LIBERADO(132L),
		HABILITA_VENDA_COTAS_APP(133L),
		TIMEOUT_MARKETPLACE_PARTIDAS(134L),
		TEMPO_MAXIMO_MILISSEGUNDOS_TRANSACAO_DIRECIONA_FLUXO_FINANCEIRO_MKP(135L),
		TIMEOUT_CONSULTA_PESQUISA_CADASTRAL(136L),
		HABILITA_EXCLUSAO_COTAS_AUTOMATICAS(137L),
		HABILITA_VERIFICACAO_SIPES(138L),
		HOST_MICRO_SERVICO_CONFERENCIA_APOSTAS_WEB(139L), 
		LISTA_CPF_NAO_VALIDA_VALOR_MINIMO_APOSTA(140L),
		HOST_NOVO_MICRO_SERVICO_CONFERENCIA_APOSTAS_PRD(141L), 
		GRUPO_LIBERADO_NOVO_MICRO_SERVICO(142L),
		HOST_NOVO_MICRO_SERVICO_CONFERENCIA_APOSTAS_PLT(143L),

		VALOR_MINIMO_COMBO(149L);
		

		private final Long value;

		private ParametroSistema(Long value) {
			this.value = value;
		}

		public Long getValue() {
			return value;
		}

		public static ParametroSistema getByValue(Long value) {
			return EnumUtil.recupereByValue(values(), value);
		}

	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_TIPO_PARAMETRO")
	@EagerFetchMode(FetchMode.PARALLEL)
	private TipoParametro tipo;

	@Column(name = "NO_PARAMETRO")
	private String nome;

	@Column(name = "DE_PARAMETRO")
	private String descricao;

	@Column(name = "VR_PARAMETRO")
	private String valor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "NU_TIPO_DADO")
	@EagerFetchMode(FetchMode.PARALLEL)
	private TipoDado tipoDado;

	@Id
	@Column(name = "NU_PARAMETRO")
	private Long id;

	@Column(name = "TS_ALTERACAO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.TIMESTAMP)
	private Data dataAlteracao;

	@Transient
	private Serializable valorTipado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoParametro getTipo() {
		return tipo;
	}

	public void setTipo(TipoParametro tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
		this.valorTipado = null;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValorTipado() {
		if (valorTipado == null && valor != null) {
			switch (tipoDado.getEnum()) {
				case BOOLEANO:
					valorTipado = Boolean.valueOf("true".equalsIgnoreCase(valor));
					break;
				case DATA:
					valorTipado = DataUtil.stringToData(valor);
					break;
				case INTEIRO:
					valorTipado = ConversorUtil.parseInt(valor);
					break;
				case DECIMAL:
				case MONETARIO:
					valorTipado = ConversorUtil.parseDecimal(valor);
					break;
				case STRING:
					valorTipado = valor.trim();
					break;
				case TIMESTAMP:
					valorTipado = DataUtil.stringToData(valor, DataUtil.DIA_HORA);
					break;
				case HORA:
					valorTipado = new Hora(valor);
					break;
				case INTERVALO_HORA:
					JsonObject fromJsonHora = GSON.fromJson(valor, JsonObject.class);
					JsonElement horaInicio = fromJsonHora.get("i");
					JsonElement horaFim = fromJsonHora.get("f");
					Hora i = horaInicio != null ? new Hora(horaInicio.getAsString()) : null;
					Hora f = horaFim != null ? new Hora(horaFim.getAsString()) : null;
					valorTipado = new IntervaloHora(i, f);
					break;
				case INTERVALO_DATA:
					JsonObject fromJson = GSON.fromJson(valor, JsonObject.class);
					JsonElement inicio = fromJson.get("i");
					JsonElement fim = fromJson.get("f");

					Data dataInicio = inicio != null ? DataUtil.stringToDataSafe(inicio.getAsString()) : null;
					Data dataFim = fim != null ? DataUtil.stringToDataSafe(fim.getAsString()) : null;

					valorTipado = new IntervaloData(dataInicio, dataFim);
					break;
				case MODALIDADE_BLOQUEADA_CANAL:
					JsonObject fromJsonBloqueio = GSON.fromJson(valor, JsonObject.class);
					HashMap<Integer, HashMap<Integer, List<Integer>>> mapaJsonBloqueio = new HashMap<Integer, HashMap<Integer, List<Integer>>>();
					for (Subcanal subcanal : Subcanal.values()) {
						JsonObject jsonTiposConcurso = (JsonObject) fromJsonBloqueio.get(subcanal.getCodigo().toString());
						HashMap<Integer, List<Integer>> mapaBloqueadas = new HashMap<Integer, List<Integer>>();
						for (TipoConcurso tipo : TipoConcurso.values()) {
							Integer codigoTipoConcurso = Integer.parseInt(String.valueOf(tipo.getCodigo()));
							mapaBloqueadas.put(codigoTipoConcurso, new ArrayList<Integer>());
							if (jsonTiposConcurso != null) {
								JsonArray asJsonArray = jsonTiposConcurso.getAsJsonArray(tipo.getCodigo().toString());
								for (JsonElement modalidade : asJsonArray) {
									mapaBloqueadas.get(codigoTipoConcurso).add(modalidade.getAsInt());
								}
							}
						}
						mapaJsonBloqueio.put(subcanal.getCodigo(), mapaBloqueadas);
					}
					valorTipado = mapaJsonBloqueio;
					break;
				case NAO_SE_APLICA:
					valorTipado = "N/A";
					break;
			}
		}
		return (T) valorTipado;
	}

	@SuppressWarnings("unchecked")
	public <T> T getArray() {

		String[] arrayDeNomes = valor.split("; ");
		List<String> valores = new ArrayList<String>();

		System.out.println("Tamanho do array: " + arrayDeNomes.length);

		for (String nome : arrayDeNomes) {
			valores.add(nome);
		}

		if (!valores.isEmpty()) {
			return (T) valores;
		}

		return null;

	}

	public void setValorIntervaloHora(IntervaloHora intervaloHora) {

		JsonObject root = new JsonObject();
		Hora inicio = intervaloHora.getInicio();
		if (inicio != null) {
			root.addProperty("i", inicio.toString());
		}
		Hora fim = intervaloHora.getFim();
		if (fim != null) {
			root.addProperty("f", fim.toString());
		}
		this.valor = GSON.toJson(root);
		this.valorTipado = null;
	}

	public void setValorIntervaloData(IntervaloData intervaloData) {

		JsonObject root = new JsonObject();
		Data inicio = intervaloData.getInicio();
		if (inicio != null) {
			root.addProperty("i", inicio.toOnlyDataString());
		}
		Data fim = intervaloData.getFim();
		if (fim != null) {
			root.addProperty("f", fim.toOnlyDataString());
		}
		this.valor = GSON.toJson(root);
		this.valorTipado = null;
	}

	public TipoDado getTipoDado() {
		return tipoDado;
	}

	public void setTipoDado(TipoDado tipoDado) {
		this.tipoDado = tipoDado;
		this.valorTipado = null;
	}

	public boolean isTipoDadoMonetario() {
		return getTipoDado().isMonetario();
	}

	public boolean isTipoDadoDecimal() {
		return getTipoDado().isDecimal();
	}

	public boolean isTipoDadoBooleano() {
		return getTipoDado().isMesmoEnum(Tipo.BOOLEANO);
	}

	/**
	 * Se nao tiver um tratamento especial na tela
	 * 
	 * @return
	 */
	public boolean isTipoDadoEspecifico() {
		return isTipoDadoMonetario() || isTipoDadoBooleano() || isTipoIntervaloHora() || isTipoIntervaloData() || isTipoDadoDecimal();
	}

	public boolean isTipoIntervaloHora() {
		return getTipoDado().isMesmoEnum(Tipo.INTERVALO_HORA);
	}

	public boolean isTipoIntervaloData() {
		return getTipoDado().isMesmoEnum(Tipo.INTERVALO_DATA);
	}

	@Override
	protected ParametroSistema[] createValores() {
		return ParametroSistema.values();
	}

	public Data getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Data dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
}
