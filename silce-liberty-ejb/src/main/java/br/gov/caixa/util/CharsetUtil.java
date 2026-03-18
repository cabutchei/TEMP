package br.gov.caixa.util;

import java.nio.charset.Charset;

public final class CharsetUtil {

	public static final Charset UTF8 = Charset.forName("UTF-8");
	// https://coderanch.com/t/277803/java-io/java/Generating-EBCDIC-file
	// "Cp037"

	// http://webcache.googleusercontent.com/search?q=cache%3Astackoverflow.com%2Fquestions%2F368603%2Fconvert-string-from-ascii-to-ebcdic-in-java&oq=cache%3Astackoverflow.com%2Fquestions%2F368603%2Fconvert-string-from-ascii-to-ebcdic-in-java&aqs=chrome..69i57j69i58.151j0j4&sourceid=chrome&ie=UTF-8
	// https://docs.oracle.com/javase/8/docs/technotes/guides/intl/encoding.doc.html
	// "Cp1047"
	public static final Charset EBCDIC = Charset.forName("Cp1047");

	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static CharsetUtil instancia = new CharsetUtil();

	private CharsetUtil() {
	}
}
