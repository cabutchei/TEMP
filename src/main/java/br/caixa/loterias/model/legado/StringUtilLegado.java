package br.caixa.loterias.model.legado;

import java.text.Normalizer;

/**
 * Dummy implementation created to satisfy missing dependency references.
 */
public final class StringUtilLegado {

    private StringUtilLegado() {
    }

    public static String removeAcentosNotWordsUnderscoreToUpperTrim(String value) {
        if (value == null) {
            return "";
        }

        return removeAcentosAndToUpper(value)
                .replaceAll("[^\\w\\s]", "")
                .trim();
    }

    public static String removeAcentosAndToUpper(String value) {
        if (value == null) {
            return "";
        }

        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return normalized.toUpperCase();
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
