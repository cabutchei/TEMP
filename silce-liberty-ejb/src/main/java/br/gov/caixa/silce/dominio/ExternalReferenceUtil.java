package br.gov.caixa.silce.dominio;

import br.gov.caixa.silce.dominio.entidade.Operacao.OperacaoEnum;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.StringUtil;


/**
 * @author c101482
 */
public final class ExternalReferenceUtil {

	private static final int TAMANHO_NSU = 11;
	
	private static final int TAMANHO_DATA = 8;

	private static final int TAMANHO_OPERACAO = 3;
	
	private static final int TAMANHO_TXID = 32;

	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static ExternalReferenceUtil instancia = new ExternalReferenceUtil();
	
	private ExternalReferenceUtil() {
		// não deve ser instanciado
	}

	public static String getExternalId(OperacaoEnum operacao, Data data, Long nsu) {
		String idOperacao = operacao.getValue().toString();

		String idOperacaoCompleto = StringUtil.completeAEsquerda(idOperacao, TAMANHO_OPERACAO, '0');
		String dataSemBarras = DataUtil.dataToString(data, DataUtil.DDMMYYYY);
		String nsuCompleto = StringUtil.completeAEsquerda(nsu.toString(), TAMANHO_NSU, '0');

		return idOperacaoCompleto + dataSemBarras + nsuCompleto;
	}
	
	public static String getExternalIdEstornoPix(OperacaoEnum operacao, Data data, Long nsu) {
		String idOperacao = operacao.getValue().toString();

		String idOperacaoCompleto = StringUtil.completeAEsquerda(idOperacao, TAMANHO_OPERACAO, '0');
		String dataSemBarras = DataUtil.dataToString(data, DataUtil.DIAHORA_EXTENSO);
		String nsuCompleto = StringUtil.completeAEsquerda(nsu.toString(), TAMANHO_NSU, '0');

		return idOperacaoCompleto + dataSemBarras + nsuCompleto;
	}

	public static Long getNSU(String externalReference) {
		return Long.valueOf(externalReference.substring(TAMANHO_OPERACAO + TAMANHO_DATA));
	}
	
	public static int getTamanhoNSU() {
		return TAMANHO_NSU;
	}

	public static int getTamanhoTXID() {
		return TAMANHO_TXID;
	}

}
