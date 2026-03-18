package br.gov.caixa.dominio.exception;

public enum Recurso {

	MQ("MQ", 1),
	DB("DB", 2),
	FILE("FILE", 3),
	LDAP("LDAP", 4),
	MAIL("MAIL", 5),
	HTTP("HTTP", 6),
	FTP("FTP", 7),
	SIRAN("SIRAN", 8),
	TABELA("TABELA", 9),
	MQ_FACTORY("QUEUE_FACTORY", 10),
	DS("DATA_SOURCE", 11),
	EM("ENTITY_MANAGER", 12),
	
	IC("INITIAL_CONTEXT", 13),
	
	JVM("JVM", 14),
	
	EH_CACHE("EH_CACHE", 15),

	LOGIN_TOKEN("EH_CACHE", 16),

	HTTP_INFRA("HTTP_INFRA", 17),

	//Não definido é para excecoes que não são RuntimeException
	//Ex: IOException, JAXBException.. Os EJBs capturam e lançam um AmbienteException
	NAO_DEFINIDO("NAO_DEFINIDO", 99);
	
	private final String codigo;

	private final Integer codigoRecurso;

	private Recurso(String codigo, Integer codigoRecurso) {
		this.codigo = codigo;
		this.codigoRecurso = codigoRecurso;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public Integer getCodigoRecurso() {
		return codigoRecurso;
	}

}
