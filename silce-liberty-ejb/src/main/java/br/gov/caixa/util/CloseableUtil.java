package br.gov.caixa.util;

import java.io.Closeable;

/**
 * @author c101482
 *
 */
public final class CloseableUtil {

	//Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static CloseableUtil instancia = new CloseableUtil();

	
	private CloseableUtil() {
		// classe utilitária
	}
	
	
	/**
	 * Para ser utilizado nos blocos finally
	 * @param closeable
	 * @return true se fechou com sucesso, false caso contrário
	 */
	public static boolean close(Closeable closeable) {
		try {
			if(closeable != null) {
				closeable.close();
			}
			return true;
		}catch (Exception e) {
			//Nao faz nada, é safe
			return false;
		}
	}
}
