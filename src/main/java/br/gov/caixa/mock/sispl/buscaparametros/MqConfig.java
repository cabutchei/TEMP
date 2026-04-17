package br.gov.caixa.mock.sispl.buscaparametros;

import java.nio.file.Path;
import java.time.ZoneId;

final class MqConfig {

	private final String host;
	private final int port;
	private final String channel;
	private final String queueManager;
	private final boolean mqEnabled;
	private final String user;
	private final String password;
	private final String requestQueue;
	private final String responseQueue;
	private final int pollWaitMillis;
	private final int reconnectDelayMillis;
	private final boolean runOnce;
	private final boolean preferReplyTo;
	private final int replyCcsid;
	private final int replyEncoding;
	private final Path responseFile;
	private final Path jsonFile;
	private final ZoneId zoneId;
	private final String headerUserService;
	private final String headerUser;
	private final String headerOperation;
	private final String origemRetorno;
	private final String msgRetorno;
	private final int codRetorno;
	private final boolean includeProximoConcurso;
	private final int apiCanal;
	private final String apiSubcanal;

	private MqConfig(String host, int port, String channel, String queueManager, boolean mqEnabled, String user, String password,
			String requestQueue, String responseQueue, int pollWaitMillis, int reconnectDelayMillis, boolean runOnce,
			boolean preferReplyTo, int replyCcsid, int replyEncoding, Path responseFile, Path jsonFile, ZoneId zoneId,
			String headerUserService, String headerUser, String headerOperation, String origemRetorno, String msgRetorno,
			int codRetorno, boolean includeProximoConcurso, int apiCanal, String apiSubcanal) {
		this.host = host;
		this.port = port;
		this.channel = channel;
		this.queueManager = queueManager;
		this.mqEnabled = mqEnabled;
		this.user = user;
		this.password = password;
		this.requestQueue = requestQueue;
		this.responseQueue = responseQueue;
		this.pollWaitMillis = pollWaitMillis;
		this.reconnectDelayMillis = reconnectDelayMillis;
		this.runOnce = runOnce;
		this.preferReplyTo = preferReplyTo;
		this.replyCcsid = replyCcsid;
		this.replyEncoding = replyEncoding;
		this.responseFile = responseFile;
		this.jsonFile = jsonFile;
		this.zoneId = zoneId;
		this.headerUserService = headerUserService;
		this.headerUser = headerUser;
		this.headerOperation = headerOperation;
		this.origemRetorno = origemRetorno;
		this.msgRetorno = msgRetorno;
		this.codRetorno = codRetorno;
		this.includeProximoConcurso = includeProximoConcurso;
		this.apiCanal = apiCanal;
		this.apiSubcanal = apiSubcanal;
	}

	static MqConfig fromEnvironment() {
		EnvConfig env = EnvConfig.load();
		return new MqConfig(
				value(env, "SISPL_MQ_HOST", "localhost"),
				env.getInt("SISPL_MQ_PORT", 1414),
				value(env, "SISPL_MQ_CHANNEL", "BRD3.SVRCONN.SILCE"),
				value(env, "SISPL_MQ_QUEUE_MANAGER", "BRD3"),
				env.getBoolean("SISPL_MQ_ENABLED", true),
				value(env, "SISPL_MQ_USER", "app"),
				value(env, "SISPL_MQ_PASSWORD", "SLCEBD01"),
				value(env, "SISPL_REQUEST_QUEUE", "LQ.REQ.SISPLC.BUSCA_PARAMETROS"),
				value(env, "SISPL_RESPONSE_QUEUE", "LQ.RSP.SISPL.BUSCA_PARAMETROS"),
				env.getInt("SISPL_POLL_WAIT_MS", 5000),
				env.getInt("SISPL_RECONNECT_DELAY_MS", 5000),
				env.getBoolean("SISPL_RUN_ONCE", false),
				env.getBoolean("SISPL_PREFER_REPLY_TO", true),
				env.getInt("SISPL_REPLY_CCSID", 37),
				env.getInt("SISPL_REPLY_ENCODING", 785),
				env.getPath("SISPL_RESPONSE_FILE"),
				env.getPath("SISPL_JSON_FILE"),
				ZoneId.of(value(env, "SISPL_TIME_ZONE", "America/Sao_Paulo")),
				value(env, "SISPL_HEADER_USUARIO_SERVICO", "SISPL"),
				value(env, "SISPL_HEADER_USUARIO", "MOCK"),
				value(env, "SISPL_HEADER_OPERACAO", "90301"),
				value(env, "SISPL_ORIGEM_RETORNO", "SISPL"),
				value(env, "SISPL_MSG_RETORNO", "OK"),
				env.getInt("SISPL_COD_RETORNO", 0),
				env.getBoolean("SISPL_INCLUDE_PROXIMO_CONCURSO", true),
				env.getInt("SISPL_API_CANAL", 9880),
				value(env, "SISPL_API_SUBCANAL", "SILCE"));
	}

	private static String value(EnvConfig env, String key, String defaultValue) {
		String value = env.get(key);
		return value == null || value.isBlank() ? defaultValue : value;
	}

	String host() {
		return host;
	}

	int port() {
		return port;
	}

	String channel() {
		return channel;
	}

	String queueManager() {
		return queueManager;
	}

	boolean mqEnabled() {
		return mqEnabled;
	}

	String user() {
		return user;
	}

	String password() {
		return password;
	}

	String requestQueue() {
		return requestQueue;
	}

	String responseQueue() {
		return responseQueue;
	}

	int pollWaitMillis() {
		return pollWaitMillis;
	}

	int reconnectDelayMillis() {
		return reconnectDelayMillis;
	}

	boolean runOnce() {
		return runOnce;
	}

	boolean preferReplyTo() {
		return preferReplyTo;
	}

	int replyCcsid() {
		return replyCcsid;
	}

	int replyEncoding() {
		return replyEncoding;
	}

	Path responseFile() {
		return responseFile;
	}

	Path jsonFile() {
		return jsonFile;
	}

	ZoneId zoneId() {
		return zoneId;
	}

	String headerUserService() {
		return headerUserService;
	}

	String headerUser() {
		return headerUser;
	}

	String headerOperation() {
		return headerOperation;
	}

	String origemRetorno() {
		return origemRetorno;
	}

	String msgRetorno() {
		return msgRetorno;
	}

	int codRetorno() {
		return codRetorno;
	}

	boolean includeProximoConcurso() {
		return includeProximoConcurso;
	}

	int apiCanal() {
		return apiCanal;
	}

	String apiSubcanal() {
		return apiSubcanal;
	}
}
