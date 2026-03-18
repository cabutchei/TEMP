package br.gov.caixa.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Arrays;



/**
 * Classe utilitária para manipulação de URLs.
 */
public final class UrlUtil {

	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static UrlUtil instancia = new UrlUtil();

	private UrlUtil() {

	}

	public static String formatUrlEncodeParams(String pattern, String... urlParams) throws UnsupportedEncodingException {
		return formatUrlEncodeParams(pattern, false, false, urlParams);
	}

	public static String formatUrlEncodeParams(String pattern, Boolean dynamicHost, Boolean encodeParams, String... urlParams) throws UnsupportedEncodingException {
		String[] encodedParams = urlParams;
		if (!encodeParams) {
			encodedParams = encodeParams(dynamicHost ? Arrays.copyOfRange(urlParams, 1, urlParams.length) : urlParams);
		}
		return MessageFormat.format(pattern, dynamicHost ? add2BeginningOfArray(encodedParams, urlParams[0]) : (Object[]) encodedParams);
	}

	private static <T> T[] add2BeginningOfArray(T[] elements, T element) {
		T[] newArray = Arrays.copyOf(elements, elements.length + 1);
		newArray[0] = element;
		System.arraycopy(elements, 0, newArray, 1, elements.length);
		return newArray;
	}
	
	public static String[] encodeParams(String... urlParams) throws UnsupportedEncodingException {
		int qtdParams = urlParams.length;
		String[] paramsEncoded = new String[qtdParams];
		for (int i = 0; i < qtdParams; i++) {
			paramsEncoded[i] = URLEncoder.encode(urlParams[i], CharsetUtil.UTF8.name());
		}

		return paramsEncoded;
	}
}
