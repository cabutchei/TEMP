package br.gov.caixa.dominio;


public enum MetodoHttp {

	GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE"), PATCH("PATCH");
	
	private final String httpName;

	private MetodoHttp(String httpName) {
		this.httpName = httpName;
	}
	
	public String getHttpName() {
		return httpName;
	}

}
