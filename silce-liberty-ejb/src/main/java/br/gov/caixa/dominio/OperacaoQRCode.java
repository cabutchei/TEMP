package br.gov.caixa.dominio;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Lista de operações utilizada na String de QRCode utilizados na caixa
 * 
 * @author c101482
 *
 */
public enum OperacaoQRCode {

	PAGAMENTO_MREIO("PGPREMIO");
	
	private static final String CAIXA_STR = "CAIXA";
	private static final Gson gson = new GsonBuilder().create();

	private final String operacao;

	private OperacaoQRCode(String operacao) {
		this.operacao = operacao;
	}

	public String getOperacao() {
		return operacao;
	}
	
	public static String toQRCode(OperacaoQRCode operacao, Map<String, Object> fields) {
		fields.put("operacao", operacao.getOperacao());
		return CAIXA_STR + gson.toJson(fields);
	}


}
