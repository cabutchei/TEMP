package br.gov.caixa.silce.dominio.config;


public final class DatabaseConfig {

	public static final String SCHEMA = "LCE";
	public static final String PERSISTENCE_UNIT = "silcePersistenceUnit";
	
	public static final String REGION_PREFIX = "";
	
	//Colocado somente para dar 100% no emma
	private static DatabaseConfig instancia = new DatabaseConfig();
		
	
	/**
	 * Construtor privado
	 */
	private DatabaseConfig() {
		//Construtor privado
	}
	
	public static DatabaseConfig getInstancia() {
		return instancia;
	}
}
