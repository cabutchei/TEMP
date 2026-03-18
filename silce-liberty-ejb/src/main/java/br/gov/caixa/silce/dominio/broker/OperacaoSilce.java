package br.gov.caixa.silce.dominio.broker;



/**
 * Inicialmente valido para operações em que o SILCE provê o serviço
 * 
 * @author c101482
 */
public enum OperacaoSilce {

	VALIDA_CODIGO_RESGATE("1", "1");
	
	private final String operacao;
	private final String versao;
	private final Integer indice;

	private OperacaoSilce(String operacao, String versao) {
		this(operacao, versao, null);
	}

	private OperacaoSilce(String operacao, String versao, Integer indice) {
		this.operacao = operacao;
		this.versao = versao;
		this.indice = indice;
	}
	
	public static OperacaoSilce getByOperacaoIndice(String operacao, Integer indice) {
		for (OperacaoSilce operacaoSispl : values()) {
			if (operacao.equals(operacaoSispl.getOperacao()) && (indice == null || indice.equals(operacaoSispl.getIndice()))) {
				return operacaoSispl;
			}
		}
		return null;
	}

	public String getVersao() {
		return versao;
	}

	public Integer getIndice() {
		return indice;
	}

	public String getOperacao() {
		return operacao;
	}
}
