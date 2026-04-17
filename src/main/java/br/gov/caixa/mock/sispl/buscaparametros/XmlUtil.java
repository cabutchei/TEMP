package br.gov.caixa.mock.sispl.buscaparametros;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

final class XmlUtil {

	private XmlUtil() {
	}

	static String escape(String value) {
		if (value == null) {
			return "";
		}
		return value
				.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&apos;");
	}

	static Charset charsetForCcsid(int ccsid) {
		return switch (ccsid) {
			case 37 -> Charset.forName("Cp037");
			case 1208 -> StandardCharsets.UTF_8;
			default -> StandardCharsets.UTF_8;
		};
	}

	static String toHex(byte[] value) {
		StringBuilder hex = new StringBuilder(value.length * 2);
		for (byte b : value) {
			hex.append(String.format("%02X", b));
		}
		return hex.toString();
	}

	static byte[] fromHex(String hex, int expectedLength) {
		if (hex == null) {
			return null;
		}
		String normalized = hex.replaceAll("\\s+", "");
		if (normalized.length() != expectedLength * 2) {
			throw new IllegalArgumentException(
					"Expected " + (expectedLength * 2) + " hex characters but got " + normalized.length() + ".");
		}

		byte[] bytes = new byte[expectedLength];
		for (int i = 0; i < expectedLength; i++) {
			int offset = i * 2;
			bytes[i] = (byte) Integer.parseInt(normalized.substring(offset, offset + 2), 16);
		}
		return bytes;
	}
}
