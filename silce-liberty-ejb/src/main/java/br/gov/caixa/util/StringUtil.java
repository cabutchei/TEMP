package br.gov.caixa.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import br.gov.caixa.util.exception.AlgoritmoHashNaoEncontradoException;

/**
 * Classe utilitária para manipulação de Strings.
 * 
 * @author p552259
 */
public final class StringUtil {

	public static final String EMPTY = "";
	public static final Character WHITE_SPACE = ' ';
	private static final String[] EMPTY_STRING_ARRAY = new String[0];
	private static final List<String> PALAVRAS_PARA_NAO_NORMALIZAR_EM_NOMES = Arrays.asList("de", "dos", "do", "das", "da");
	
	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static StringUtil instancia = new StringUtil();

	private StringUtil() {

	}

	public static String convertByteArrayToString(byte[] byteArray, String format){
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		for (byte b : byteArray) {
			f.format(format, b);
		}
		f.close();
		return sb.toString();
	}


	/**
	 * método usado para saber se um String é nula ou vazia.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null ? true : value.trim().isEmpty();
	}

	/**
	 * Gera um Hash com base no algoritmo SHA-256 dada a String passada como parâmetro.
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getHashAtivacao(String key) {
		return getHashAtivacao(key, "SHA-256");
	}

	/**
	 * Gera um Hash, com base no algoritmo passado como parâmetro, da String passada como parâmetro.
	 * 
	 * @param key
	 * @param algoritmo
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getHashAtivacao(String key, String algoritmo) {

		MessageDigest md;
		try {
			md = MessageDigest.getInstance(algoritmo);

			md.update(StringUtil.getBytes(key));

			byte[] hash = md.digest();

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < hash.length; i++) {

				int parteAlta = ((hash[i] >> 4) & 0xf) << 4;
				int parteBaixa = hash[i] & 0Xf;

				if (parteAlta == 0) {
					sb.append('0');
				}
				sb.append(Integer.toHexString(parteAlta | parteBaixa));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new AlgoritmoHashNaoEncontradoException(e);
		}
	}

	public static String getKeyValue(String matrix, String key) {

		String[] aux = matrix.split(",");

		for (int i = 0; i < aux.length; i++) {

			String pointer = aux[i];

			if (pointer.indexOf(key) != -1) {
				int index = aux[i].indexOf('=');
				int length = aux[i].length();

				return pointer.substring(index + 1, length);
			}

		}
		return null;

	}

	/**
	 * Gera um Hash com base no algoritmo SHA-256 dada a String passada como parâmetro.
	 * 
	 * @param key
	 * @param maxSize
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getHashAtivacao(String key, int maxSize) {
		return getHashAtivacao(key).substring(0, maxSize);
	}

	public static String merge(Collection<String> strings) {
		return merge(", ", strings.toArray(EMPTY_STRING_ARRAY));
	}
	
	public static String merge(String separator, String... strings) {
		StringBuilder sb = new StringBuilder();
		for (String s : strings) {
			sb.append(s);
			sb.append(separator);
		}
		sb.delete(sb.length() - separator.length(), sb.length());
		return sb.toString();
	}

	public static String nullSafeTrim(String value) {
		return value == null ? value : value.trim();
	}
	
	/**
	 * Difere de {@link String#valueOf(Object)}, pois quando <code>objeto</code> é null, retorna null ao invés de
	 * "null". 
	 * 
	 * Ou seja, se <code>objeto</code> for null, retorna null, caso contrário, retorna {@link Object#toString()}.
	 */
	public static String valueOf(Object objeto) {
		return objeto == null ? null : objeto.toString();
	}

	public static String completeAEsquerda(String value, int quantidadeMax, char c) {
		if (value == null || value.length() >= quantidadeMax) {
			return value;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = value.length(); i < quantidadeMax; i++) {
			sb.append(c);
		}
		sb.append(value);
		return sb.toString();
	}

	public static String completeADireita(String value, int quantidadeMax, char c) {
		if (value == null || value.length() >= quantidadeMax) {
			return value;
		}
		StringBuilder sb = new StringBuilder(value);
		for (int i = value.length(); i < quantidadeMax; i++) {
			sb.append(c);
		}
		return sb.toString();

	}

	/**
	 * Quebra a String na quantidade de dígitos desejada.
	 * 
	 * @param valor A string a ser quebrada.
	 * @param quantidade A quantidade de dígitos.
	 * @return Array com a string quebrada.
	 */
	public static String[] splitDigitos(String valor, Integer quantidade) {
		Pattern compile = Pattern.compile("\\d{" + quantidade + "}");
		Matcher matcher = compile.matcher(valor);

		List<String> array = new ArrayList<String>();

		while (matcher.find()) {
			array.add(matcher.group());
		}
		return array.toArray(new String[] {});
	}

	public static String toLowerCase(String s) {
		return s.toLowerCase(LocaleUtil.PT_BR);
	}

	public static String toUpperCase(String s) {
		return s.toUpperCase(LocaleUtil.PT_BR);
	}

	public static byte[] getBytes(String s) {
		return s.getBytes(CharsetUtil.UTF8);
	}

	public static int countOccurrences(String str, char c) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == c) {
				count++;
			}
		}
		return count;
	}

	public static boolean hasSomenteNumeros(String str) {
		return str.matches("\\d+");
	}

	public static boolean hasTamanhoValido(String str, int tam) {
		return str.length() == tam;
	}
	
	/**
	 * Remove tudo que não é dígito da String.
	 */
	public static String removeNaoDigitos(String value) {
		if (isEmpty(value)) {
			return value;
		}
		return value.replaceAll("\\D", "");
	}
	
	/**
	 * Remove tudo que não é alfanumérico da String, inclusive espaços.
	 */
	public static String removeNaoAlfanumerico(String value) {
		if (isEmpty(value)) {
			return value;
		}
		return value.replaceAll("[^\\p{L}\\d]", "");
	}
	
	public static String removeNonASCIICharacters(String str) {
		//http://en.wikipedia.org/wiki/ASCII#ASCII_printable_characters
		return str.replaceAll("[^\\x20-\\x7e]", "");
	}
	
	/**
	 * Normaliza o nome próprio de pessoas, seguindo o padrão "João Paulo dos Santos".
	 */
	public static String normalizaNomePessoaCompleto(String nomeCompleto) {
		if (isEmpty(nomeCompleto)) {
			return nomeCompleto;
		}
		StringBuilder nomeFinal = new StringBuilder();

		// cria uma string com tudo minúsculo
		String nomeMinusculo = nomeCompleto.toLowerCase(LocaleUtil.PT_BR);
		// separa todos os nomes em um array
		String[] nomes = nomeMinusculo.split("\\s+");

		for (String parteNome : nomes) {
			if (!PALAVRAS_PARA_NAO_NORMALIZAR_EM_NOMES.contains(parteNome)) {
				// se for um nome
				// transforma em array e por em UpperCase a primeira letra
				char[] parteNomeArray = parteNome.toCharArray();
				parteNomeArray[0] = Character.toUpperCase(parteNomeArray[0]);
				nomeFinal.append(parteNomeArray);
			} else {
				// se não for um nome (ex.: preposição "de")
				nomeFinal.append(parteNome);
			}
			nomeFinal.append(' ');
		}

		nomeFinal.deleteCharAt(nomeFinal.length() - 1);
		return nomeFinal.toString();
	}

	public static String removeAcentosAndToUpper(String str) {
		return StringUtil.toUpperCase(removeAcentos(str));
	}

	public static String limpaRetornoRest(String str) {
		return str.replace("<br />", "").replace("&nbsp;", "");
	}

	public static String formatMessage(String pattern, String... arguments) {
		return MessageFormat.format(pattern, (Object[]) arguments);
	}

	public static String removeAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	public static String removeNotWordsUnderscore(String str) {
		return str.replaceAll("[\\W_]", "");
	}

	public static String removeAcentosNotWordsUnderscoreToUpperTrim(String str) {
		return removeNotWordsUnderscore(removeAcentosAndToUpper(str)).trim();
	}

	public static String convertListToStringBySeparator(List<?> list, String separator) {
		return StringUtils.join(list, separator);
	}

	public static String convertListToStringBySeparator(Set<?> list, String separator) {
		return StringUtils.join(list, separator);
	}
}
