package br.gov.caixa.silce.dominio.broker;

public enum OperacaoSispl {

	CONSULTA_PREMIO("1500", "1"),
	AUTORIZA_PAGAMENTO_PREMIO("200", "1", 1),
	CANCELA_PAGAMENTO_PREMIO("200", "1", 0),
	CONFIRMA_PAGAMENTO_PREMIO("200", "1", 2),
	GERA_NSBI("950103", "1"),

	BUSCA_PARAMETROS_CANAL("950101", "1"),
	BUSCA_PARAMETROS_JOGOS("90301", "1"),
	
	ENCERRA_CAPTACAO("950102", "1"),

	REGISTRA_APOSTA_MEGA_SENA("702", "1"),
	REGISTRA_APOSTA_QUINA("703", "1"),
	REGISTRA_APOSTA_LOTOFACIL("708", "1"),
	REGISTRA_APOSTA_LOTOGOL("814", "1"),
	REGISTRA_APOSTA_LOTOMANIA("716", "1"),
	REGISTRA_APOSTA_DUPLA_SENA("718", "1"),
	REGISTRA_APOSTA_LOTECA("819", "1"),
	REGISTRA_APOSTA_TIMEMANIA("720", "1"),
	REGISTRA_APOSTA_DIA_DE_SORTE("711", "1"),
	REGISTRA_APOSTA_SUPER_7("707", "1"),
	REGISTRA_APOSTA_MAIS_MILIONARIA("709", "1");
	
	private final String operacao;
	private final String versao;
	private final Integer indice;

	private OperacaoSispl(String operacao, String versao) {
		this(operacao, versao, null);
	}

	private OperacaoSispl(String operacao, String versao, Integer indice) {
		this.operacao = operacao;
		this.versao = versao;
		this.indice = indice;
	}
	
	public static OperacaoSispl getByOperacaoIndice(String operacao, Integer indice) {
		for (OperacaoSispl operacaoSispl : values()) {
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

	@Override
	public String toString() {
		return "OperacaoSispl [operaco=" + getOperacao() + ", versao=" + getVersao() + ", indice=" + getIndice() + "]";
	}
}
