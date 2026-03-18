package br.gov.caixa.silce.dominio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.StringTokenizer;

public class StackTraceGerenciaBoloes implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum StackTraceGerenciaBoloesEnum {

		GERENCIA_CARRINHO_BEAN("GerenciaCarrinhoBean"),
		CONTROLA_EVOLUCAO_APOSTA_COTA_BOLAO_BEAN("ControlaEvolucaoApostaCotaBolaoBean"),
		CANCELAMENTO_RESERVA_COTAS_CONTROLLER("CancelamentoReservaCotasController"),
		CONTROLA_ENCERRAMENTO_REVENDA_BEAN("ControlaEncerramentoRevendaBean"),
		CONTROLA_CAPTACAO_MODALIDADE_BEAN("ControlaCaptacaoModalidadeBean"),
		EVOLUCAO_COMPRA_UTIL_BEAN("EvolucaoCompraUtilBean"),
		GERENCIA_APOSTAS_REALIZADAS_BEAN("GerenciaApostasRealizadasBean");

		private String classe;

		private StackTraceGerenciaBoloesEnum(String classe) {
			this.classe = classe;
		}

		public String getClasse() {
			return classe;
		}

		public void setClasse(String classe) {
			this.classe = classe;
		}
	}

	private StackTraceGerenciaBoloesEnum stackTraceGerenciaBoloesEnum;
	private int linha;

	public StackTraceGerenciaBoloes(StackTraceGerenciaBoloesEnum stackTraceGerenciaBoloesEnum, int linha) {
		this.stackTraceGerenciaBoloesEnum = stackTraceGerenciaBoloesEnum;
		this.linha = linha;
	}

	public static Integer getLineNumber() {
		try {
			Exception e = new Exception();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.close();

			StringBuffer buf = sw.getBuffer();
			BufferedReader br = new BufferedReader(new StringReader(buf.toString()));
			br.readLine(); // exeption type
			br.readLine(); // our code
			String line = br.readLine(); // calling line
			StringTokenizer st = new StringTokenizer(line, ":)");
			st.nextToken();
			String lineNumber = st.nextToken();

			return new Integer(lineNumber);

		} catch (NumberFormatException nfe) {
			return null;
		} catch (IOException ioe) {
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(getLineNumber());
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public StackTraceGerenciaBoloesEnum getStackTraceGerenciaBoloesEnum() {
		return stackTraceGerenciaBoloesEnum;
	}

	public void setStackTraceGerenciaBoloesEnum(StackTraceGerenciaBoloesEnum stackTraceGerenciaBoloesEnum) {
		this.stackTraceGerenciaBoloesEnum = stackTraceGerenciaBoloesEnum;
	}
}