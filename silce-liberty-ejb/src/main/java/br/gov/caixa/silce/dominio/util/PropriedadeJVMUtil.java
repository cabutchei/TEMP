package br.gov.caixa.silce.dominio.util;

/**
 * @author c101482
 */
public class PropriedadeJVMUtil {

	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static PropriedadeJVMUtil instancia = new PropriedadeJVMUtil();

	public enum PropriedadeJVM {

		/**
		 * Remetente dos e-mails que serão enviados pelo sistema.
		 */
		EMAIL_REMETENTE("silce.email.remetente"),

		/**
		 * Indica se os XMLs que trafegam nas filas serão formatados (quebra de linha + identações).
		 */
		FORMAT_XML("silce.format.xml"),

		/**
		 * Indica se os XMLs que trafegam nas filas serão validados com seu XSD respectivo.
		 */
		VALIDATE_XML("silce.validate.xml"),

		/**
		 * Pasta que será salva os arquivos do SIDMO
		 */
		SIDMO_DIR("silce.sidmo.dir"),

		INITIAL_CONTEXT_PROVIDER("silce.java.naming.provider.url"), 
		
		INITIAL_CONTEXT_FACTORY("silce.java.naming.factory.initial"),

		/**
		 * Indica (para o JSF) se o projeto está no estágio de desenvolvimento.
		 */
		PROJECT_STAGE("silce.project.stage.development"),

		/**
		 * Indica se algumas configurações devem ser habilitadas para facilitar o desenvolvimento.
		 */
		DESENVOLVIMENTO("silce.desenvolvimento"),

		/**
		 * Propriedades para conectar ao Ldap do SICDU, no formato: endereco_ip:porta:ou_pessoas:ou_grupos:usuario:senha
		 * Por exemplo: 10.192.176.214:1389:ou=people,o=caixa:ou=groups,o=caixa:uid=USUARIO,ou=Users,o=caixa:SENHA
		 */
		SICDU_LDAP_PROPERTIES("silce.sicdu.ldap.properties", true),

		/**
		 * Allows for the expression language (EL) that WebSphere Application Server uses to coerce null and empty
		 * string integer values to a 0 value or for NOT allowing a coerce to a 0 value and retaining the null or empty
		 * string integer. The default is true and permits a null or empty string integer value to be coerced to a 0
		 * value.
		 * http://www-01.ibm.com/support/knowledgecenter/SSAW57_8.5.5/com.ibm.websphere.express.doc/ae/rweb_jsfengine
		 * .html
		 */
		COERCE_TO_ZERO("org.apache.el.parser.COERCE_TO_ZERO"),

		/**
		 * Usuário que será utilizado no proxy ao realizar chamadas HTTP ou HTTPs.
		 */
		PROXY_USER("silce.proxy.user"),

		/**
		 * Senha do usuário para acessar o proxy.
		 */
		PROXY_PASSWORD("silce.proxy.password", true),

		/**
		 * Domínio do usuário para acessar o proxy. É utilizado apenas para autenticação do tipo NTLM.
		 */
		PROXY_USER_DOMAIN("silce.proxy.user.domain"),

		/**
		 * Host do proxy. Exemplo: intranet.caixa
		 */
		PROXY_HOST("silce.proxy.host"),

		/**
		 * Porta do proxy. Exemplo: 80
		 */
		PROXY_PORT("silce.proxy.port"),

		/**
		 * Tipo de autenticação a ser utilizada com o proxy. Valores possíveis: Basic, Digest, NTLM, Negotiate, Kerberos.
		 */
		PROXY_AUTH_SCHEME("silce.proxy.auth.scheme"),

		FOLDER_BASE_PATH("silce.folder.base.path"),

		/**
		 * ACCESS TOKEN do Mercado Pago. É necessário em todas às chamadas HTTP para idenficar nossa conta.
		 */
		MERCADO_PAGO_ACCESS_TOKEN("silce.mercadopago.accesstoken", true),

		/**
		 * PUBLIC KEY do Mercado Pago. É necessário no Portal para a chamada HTTP pelo Javascript.
		 */
		MERCADO_PAGO_PUBLIC_KEY("silce.mercadopago.publickey"),

		/**
		 * recaptcha do google
		 */
		RECAPTCHA_DISABLED("silce.disable.repatcha"),

		/**
		 * sso
		 */
		SSO_HOST("silce.sso.host"),

		SSO_AUTH_HOSTS("silce.sso.authorizedhosts"),

		SSO_CLIENT_SECRET("silce.sso.client.secret", true),

		/**
		 * recaptcha do google
		 */
		CACHE_JBOSS_DISABLED("silce.disable.cache-jboss-was"),

		CACHE_EJB_DISABLED("silce.local.cache.disable"),

		/**
		 * informa se a chamada para o SSO irá passar pelo proxy do SILCE
		 */
		SSO_USE_SILCE_PROXY("silce.sso.use-silce-proxy"),

		SISPL2_USE_SILCE_PROXY("silce.sispl2.use-silce-proxy"),

		PATH_WAS_GESTOR_ALTA("silce.path.was.gestor.alta"),

		/**
		 * informa qual o endereco do sispl2 para requisição de servico rest
		 */
		SISPL2_HOST("silce.sispl2.host"), 
		
		HTTP_KEEP_ALIVE_PADRAO("silce.http.keep-alive-padrao-ms"),
		HTTP_TEMPO_FECHAR_CONEXAO_OCIOSA("silce.http.tempo-fechar-conexoes-ociosas-ms"),
		HTTP_MAX_CONEXOES_PADRAO_POR_HOST("silce.http.max-conexoes-padrao-por-dominio"),
		HTTP_MAX_CONEXOES_POR_HOST("silce.http.max-conexoes-por-dominio"),
		HTTP_MAX_CONEXOES_TOTAL("silce.http.max-conexoes-total"),
		HTTP_VERSOES_TLS_ACEITAS("silce.http.versoes-tls-aceitas"),
		API_KEY_INTERNET("silce.api-key.internet"),
		API_KEY_INTRANET("silce.api-key.intranet"),
		API_MANAGER_HOST_INTERNET("silce.apimanager.host.internet"),
		API_MANAGER_HOST_INTRANET("silce.apimanager.host.intranet"),
		API_MANAGER_USE_SILCE_PROXY("silce.apimanager.use-silce-proxy"),

		/**
		 * recarga pay
		 */
		RECARGA_PAY_CLIENT_ID_APPLICATION_TOKEN("silce.recargapay.client.id.application.token", true), 
		RECARGA_PAY_CLIENT_ID_APOSTADOR_TOKEN("silce.recargapay.client.id.apostador.token", true), 
		RECARGA_PAY_CLIENT_SECRET_APPLICATION_TOKEN("silce.recargapay.client.secret.application.token", true), 
		RECARGA_PAY_CLIENT_SECRET_APOSTADOR_TOKEN("silce.recargapay.client.secret.apostador.token", true),
		RECARGA_PAY_HOST("silce.recargapay.host"),
		RECARGA_PAY_FRONT_HOST("silce.recargapay.front.host"),
		RECARGA_PAY_AUDIENCE_HOST("silce.recargapay.audience.host"),
		RECARGA_PAY_AMBIENTE_TESTES("silce.recargapay.isQa"),

		/**
		 * PIX
		 */
		PIX_CHAVEPIX("silce.chavepix"),

		/**
		 * Host da descriptografia e host da esteira devops para chamada da API do SISPL
		 */
		API_CRIPTOGRAFIA_HOST("silce.api.criptografia.host"), 
		API_ESTEIRA_DEVOPS("silce.api.esteira.devops"),

		/**
		 * SILCE na Nuvem Azure
		 */
		NUVEM_APIKEY("silce.nuvem.apikey"),
		
		/**
		 * Ambiente que o jboss está rodando
		 */
		NOME_AMBIENTE("silce.nome.ambiente");

		private final String key;
		
		private final Boolean secret;

		private PropriedadeJVM(String key) {
			this(key, false);
		}
		
		private PropriedadeJVM(String key, Boolean secret) {
			this.key = key;
			this.secret = secret;
		}

		public String getKey() {
			return key;
		}

		public Boolean getSecret() {
			return secret;
		}

	}

	public static String getProperty(PropriedadeJVM propriedadeJVM) {
		return System.getProperty(propriedadeJVM.getKey());
	}

	public static void setProperty(PropriedadeJVM propriedadeJVM, String value) {
		System.setProperty(propriedadeJVM.getKey(), value);
	}

	public static Boolean isDesenvolvimento() {
		return Boolean.valueOf(getProperty(PropriedadeJVM.DESENVOLVIMENTO));
	}

	public static String getApiMkp() {
		return PropriedadeJVMUtil.getProperty(PropriedadeJVM.API_MANAGER_HOST_INTRANET);
	}

	public static String getApiPix() {
		return PropriedadeJVMUtil.getProperty(PropriedadeJVM.API_MANAGER_HOST_INTRANET);
	}

}
