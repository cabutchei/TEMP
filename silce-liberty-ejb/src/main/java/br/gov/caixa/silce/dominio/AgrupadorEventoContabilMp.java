package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import br.gov.caixa.silce.dominio.entidade.MeioPagamento;

public class AgrupadorEventoContabilMp extends AgrupadorEventoContabil implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long meioPagamento;
	private Integer operacao;
	private BigDecimal prestacaoApostasSIDEC;
	private BigDecimal prestacaoApostasNSGD;
	private BigDecimal arrecadacaoApostasSIDEC;
	private BigDecimal arrecadacaoApostasNSGD;
	private BigDecimal arrecadacaoApostasPIX;
	private BigDecimal saldo;

	public AgrupadorEventoContabilMp() {
		super();
	}

	public AgrupadorEventoContabilMp(String dataFechamento, Long meioPagamento, Integer operacao, BigDecimal prestacaoApostasSIDEC, BigDecimal prestacaoApostasNSGD,
		BigDecimal arrecadacaoApostasSIDEC, BigDecimal arrecadacaoApostasNSGD, BigDecimal arrecadacaoApostasPIX, BigDecimal saldo) {
		super(dataFechamento);
		this.meioPagamento = meioPagamento;
		this.operacao = operacao;
		this.prestacaoApostasSIDEC = prestacaoApostasSIDEC;
		this.prestacaoApostasNSGD = prestacaoApostasNSGD;
		this.arrecadacaoApostasSIDEC = arrecadacaoApostasSIDEC;
		this.arrecadacaoApostasNSGD = arrecadacaoApostasNSGD;
		this.arrecadacaoApostasPIX = arrecadacaoApostasPIX;
		this.saldo = saldo;
	}

	public Long getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(Long meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	public Integer getOperacao() {
		return operacao;
	}

	public void setOperacao(Integer operacao) {
		this.operacao = operacao;
	}

	public BigDecimal getPrestacaoApostasSIDEC() {
		return prestacaoApostasSIDEC;
	}

	public void setPrestacaoApostasSIDEC(BigDecimal prestacaoApostasSIDEC) {
		this.prestacaoApostasSIDEC = prestacaoApostasSIDEC;
	}

	public BigDecimal getPrestacaoApostasNSGD() {
		return prestacaoApostasNSGD;
	}

	public void setPrestacaoApostasNSGD(BigDecimal prestacaoApostasNSGD) {
		this.prestacaoApostasNSGD = prestacaoApostasNSGD;
	}

	public BigDecimal getArrecadacaoApostasPIX() {
		return arrecadacaoApostasPIX;
	}

	public void setArrecadacaoApostasPIX(BigDecimal arrecadacaoApostasPIX) {
		this.arrecadacaoApostasPIX = arrecadacaoApostasPIX;
	}

	public BigDecimal getArrecadacaoApostasSIDEC() {
		return arrecadacaoApostasSIDEC;
	}

	public void setArrecadacaoApostasSIDEC(BigDecimal arrecadacaoApostasSIDEC) {
		this.arrecadacaoApostasSIDEC = arrecadacaoApostasSIDEC;
	}

	public BigDecimal getArrecadacaoApostasNSGD() {
		return arrecadacaoApostasNSGD;
	}

	public void setArrecadacaoApostasNSGD(BigDecimal arrecadacaoApostasNSGD) {
		this.arrecadacaoApostasNSGD = arrecadacaoApostasNSGD;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getMeioPagamentoString() {
		return (meioPagamento == null) ? "" : MeioPagamento.Meio.getByValue(meioPagamento).name();
	}

	public String getPrestacaoApostasSIDECString() {
		return (prestacaoApostasSIDEC == null) ? "0,0" : prestacaoApostasSIDEC.toString().replace(".", ",");
	}

	public String getPrestacaoApostasNSGDString() {
		return (prestacaoApostasNSGD == null) ? "0,0" : prestacaoApostasNSGD.toString().replace(".", ",");
	}

	public String getArrecadacaoApostasSIDECString() {
		return (arrecadacaoApostasSIDEC == null) ? "0,0" : arrecadacaoApostasSIDEC.toString().replace(".", ",");
	}


	public String getArrecadacaoApostasNSGDString() {
		return (arrecadacaoApostasNSGD == null) ? "0,0" : arrecadacaoApostasNSGD.toString().replace(".", ",");
	}

	public String getArrecadacaoApostasPIXString() {

		return (arrecadacaoApostasPIX == null) ? "0,0" : arrecadacaoApostasPIX.toString().replace(".", ",");

	}

	public String getSaldoString() {

		return saldo.toString().replace(".", ",");

	}

	@Override
	public String toString() {
		StringBuilder dataBuilder = new StringBuilder();
		dataBuilder.append(getDataFechamentoFormatada() + ";");
		dataBuilder.append(getMeioPagamentoString() + ";");
		dataBuilder.append(getPrestacaoApostasSIDECString() + ";");
		dataBuilder.append(getPrestacaoApostasNSGDString() + ";");
		dataBuilder.append(getArrecadacaoApostasSIDECString() + ";");
		dataBuilder.append(getArrecadacaoApostasNSGDString() + ";");
		dataBuilder.append(getArrecadacaoApostasPIXString() + ";");
		dataBuilder.append(getSaldoString() + "\n");
		return dataBuilder.toString();
	}

	public static String getColunmNames() {
		StringBuilder dataBuilder = new StringBuilder();
		dataBuilder.append(getTituloCsvFile("MP"));
		dataBuilder.append("DATA;");
		dataBuilder.append("Meio Pagamento;");
		dataBuilder.append("SIDEC PRESTACAO DE CONTAS DO MEIO DE PAGAMENTO;");
		dataBuilder.append("NSGD PRESTACAO DE CONTAS DO MEIO DE PAGAMENTO;");
		dataBuilder.append("CONTABILIZACAO DA PRESTACAO DE CONTAS DO MEIO DE PAGAMENTO(SIDEC 25116-0);");
		dataBuilder.append("CONTABILIZACAO DA PRESTACAO DE CONTAS DO MEIO DE PAGAMENTO(NSGD 36716-8);");
		dataBuilder.append("ARRECADACAO PIX (37745-7);");
		dataBuilder.append("VENDAS MARKETPLACE (cotas +Tarifa de servico)\n");
		return dataBuilder.toString();
	}

	public static String getSubTotal(List<AgrupadorEventoContabilMp> eventos) {
		BigDecimal prestacaoApostasSIDEC = new BigDecimal(0.0);
		BigDecimal prestacaoApostasNSGD = new BigDecimal(0.0);
		BigDecimal arrecadacaoApostasSIDEC = new BigDecimal(0.0);
		BigDecimal arrecadacaoApostasNSGD = new BigDecimal(0.0);
		BigDecimal arrecadacaoApostasPIX = new BigDecimal(0.0);
		BigDecimal saldo = new BigDecimal(0.0);

		if (!eventos.isEmpty()) {
			for (AgrupadorEventoContabilMp evento : eventos) {
				prestacaoApostasSIDEC = prestacaoApostasSIDEC.add(plusValue(evento.getPrestacaoApostasSIDEC()));
				prestacaoApostasNSGD = prestacaoApostasNSGD.add(plusValue(evento.getPrestacaoApostasNSGD()));
				arrecadacaoApostasSIDEC = arrecadacaoApostasSIDEC.add(plusValue(evento.getArrecadacaoApostasSIDEC()));
				arrecadacaoApostasNSGD = arrecadacaoApostasNSGD.add(plusValue(evento.getArrecadacaoApostasNSGD()));
				arrecadacaoApostasPIX = arrecadacaoApostasPIX.add(plusValue(evento.getArrecadacaoApostasPIX()));
				saldo = saldo.add(plusValue(evento.getSaldo()));
			}
		}

		return "SUBTOTAL;;"
			+ prestacaoApostasSIDEC.toString().replace(".", ",") + ";"
			+ prestacaoApostasNSGD.toString().replace(".", ",") + ";"
			+ arrecadacaoApostasSIDEC.toString().replace(".", ",") + ";"
			+ arrecadacaoApostasNSGD.toString().replace(".", ",") + ";"
			+ arrecadacaoApostasPIX.toString().replace(".", ",") + ";"
			+ saldo.toString().replace(".", ",") + "\n";
	}

}