package br.gov.caixa.util.exception;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.gov.caixa.util.StringUtil;

public final class CodigoErroUtil {

	public static final String JAVA_SEPARADOR_NESTED_CLASS = "$";
	public static final String DEFAULT_PACKAGE_PREFIX = "br.gov.caixa";
	public static final String DEFAULT_SEPARADOR = "-";
	
	public static final int DEFAULT_LIMITE_HASH_CODE = 2;
	public static final int DEFAULT_LIMITE_INICIAIS_CLASS = 3;
	public static final int DEFAULT_QTD_CLASSES_NO_ERRO = 2;

	//Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static CodigoErroUtil instancia = new CodigoErroUtil();

	private CodigoErroUtil() {
		// classe util
	}

	/**
	 * Gera um código erro para ser apresentado ao usuário. O algorimo é o seguinte: <br />
	 * Começando pelo último causedBy da exceção (Root Cause), procure na stacktrace, de cima para baixo, o pacote
	 * {@link #DEFAULT_PACKAGE_PREFIX}. <br />
	 * Caso não encontre, procure no causedBy anterior, e assim por diante. <br />
	 * Ao encontrar o pacote informado:, <br />
	 * 1. adiciona a linha da exceção, <br />
	 * 2. pega as iniciais da classe (letras maiúsculas), <br />
	 * 3. adiciona {@link #DEFAULT_LIMITE_HASH_CODE} dígitos do hashcode da classe, <br />
	 * 4. adiciona um {@link #DEFAULT_SEPARADOR}, <br />
	 * 5. repete 1 a 4 até atingir {@link #DEFAULT_QTD_CLASSES_NO_ERRO}, <br />
	 * 6. adiciona as iniciais do nome da classe da exceção. <br />
	 * <br />
	 * Caso não encontre nenhum pacote {@link #DEFAULT_PACKAGE_PREFIX}, será apresentado utilizando as primeiras linhas
	 * do último causedBy, independente do pacote.
	 */
	public static final String geraCodigoErro(Throwable t) {
		if (t == null) {
			return StringUtil.EMPTY;
		}

		List<Throwable> causesList = getCausesList(t);
		Object[] o = getStackTraceElement(causesList, DEFAULT_PACKAGE_PREFIX);

		if (o.length == 0) {
			return StringUtil.EMPTY;
		}

		@SuppressWarnings("unchecked")
		List<StackTraceElement> stackTraceElements = (List<StackTraceElement>) o[0];
		Throwable cause = (Throwable) o[1];

		StringBuilder codigoErro = new StringBuilder();

		for (int i = 0; i < stackTraceElements.size() && i < DEFAULT_QTD_CLASSES_NO_ERRO; i++) {
			StackTraceElement ste = stackTraceElements.get(i);
			int lineNumber = ste.getLineNumber();
			if (lineNumber > 0) {
				codigoErro.append(lineNumber);
			}

			final String className = ste.getClassName();
			String iniciaisClass = getIniciais(className, true);

			codigoErro.append(iniciaisClass).append(DEFAULT_SEPARADOR);
		}

		if (cause != null) {
			String exceptionName = cause.getClass().getSimpleName();
			// se não tiver nenhuma classe do pacote default na stacktrace, coloca também o hashCode da exceção
			boolean hashCodeException = codigoErro.length() == 0;
			String iniciaisException = getIniciais(exceptionName, hashCodeException);
			codigoErro.append(iniciaisException);
		}

		return codigoErro.toString();
	}

	public static final String getIniciais(String className, boolean appendHashCode) {
		String classNameSemInnerClass = className;
		if (className.contains(JAVA_SEPARADOR_NESTED_CLASS)) {
			// remove nome da inner class
			classNameSemInnerClass = className.substring(0, className.indexOf(JAVA_SEPARADOR_NESTED_CLASS));
		}

		final StringBuilder iniciais = new StringBuilder();
		final char[] charArray = classNameSemInnerClass.toCharArray();
		for (int i = 0; i < charArray.length && iniciais.length() < DEFAULT_LIMITE_INICIAIS_CLASS; i++) {
			final char c = charArray[i];
			if (Character.isUpperCase(c)) {
				iniciais.append(c);
			}
		}
		if (appendHashCode) {
			iniciais.append(getHashCode(classNameSemInnerClass));
		}
		return iniciais.toString();
	}

	private static final String getHashCode(final String classSimpleName) {
		int hashCode = classSimpleName.hashCode();
		int abs = Math.abs(hashCode);
		String hashCodeStr = Integer.toString(abs);
		if (hashCodeStr.length() <= DEFAULT_LIMITE_HASH_CODE) {
			return hashCodeStr;
		} else {
			return hashCodeStr.substring(0, DEFAULT_LIMITE_HASH_CODE);
		}
	}

	/**
	 * @return as linhas do Stack Trace do caused by mais abaixo, que seja do pacote informado. A primeira posição do
	 *         Array contém os {@link StackTraceElement}, e a segunda posição do array contém o {@link Throwable} que
	 *         representa o causedBy.
	 */
	private static final Object[] getStackTraceElement(List<Throwable> causesList, String packagePrefix) {
		List<StackTraceElement> stes = new ArrayList<StackTraceElement>();
		Throwable t = null;
		
		for (int i = causesList.size() - 1; i >= 0; i--) {
			Throwable throwable = causesList.get(i);
			StackTraceElement[] stackTrace = throwable.getStackTrace();
			for (int j = 0; j < stackTrace.length; j++) {
				if (StringUtil.isEmpty(packagePrefix) || stackTrace[j].getClassName().startsWith(packagePrefix)) {
					t = throwable;
					stes.add(stackTrace[j]);
				}
			}
			if (t != null) {
				break;
			}
		}

		if (t == null && !StringUtil.EMPTY.equals(packagePrefix)) {
			return getStackTraceElement(causesList, StringUtil.EMPTY);
		} else {
			return new Object[] {stes, t};
		}
	}

	private static final List<Throwable> getCausesList(Throwable t) {
		if (t.getCause() == null) {
			return Arrays.asList(t);
		}

		List<Throwable> throwables = new ArrayList<Throwable>();
		Throwable tAtual = t;
		Throwable tAnterior = null;

		boolean continua = true;
		while (continua) {
			throwables.add(tAtual);
			tAnterior = tAtual;
			tAtual = tAtual.getCause();
			if ((tAtual == null || throwables.contains(tAtual)) && tAnterior instanceof RemoteException) {
				tAtual = ((RemoteException) tAnterior).detail;
			}
			if (tAtual == null || throwables.contains(tAtual)) {
				continua = false;
			}
		}

		return throwables;
	}

}
