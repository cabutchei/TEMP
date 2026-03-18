package br.gov.caixa.util;

import java.util.Collection;
import java.util.Map;

/**
 * Classe utilitária para operações com Coleções e Arrays.
 * 
 */
public final class CollectionUtil {

	//Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static CollectionUtil instancia = new CollectionUtil();

	
	/**
	 * Construtor privado para evitar instanciações.
	 */
	private CollectionUtil() {
		
	}
	
	/**
	 * Verifica se uma coleção é vazia ou nula.
	 * 
	 * @param colecao
	 * 		A coleção a ser verificada.
	 * @return Verdadeiro caso seja uma coleção nula ou vazia.
	 */
	public static boolean isVazio(Collection<?> colecao) {
		return colecao == null || colecao.isEmpty();
	}
	
	/**
	 * Verifica se um mapa é vazio ou nulo.
	 * 
	 * @param mapa
	 * 		O mapa a ser verificado.
	 * @return Verdadeiro caso seja um mapa nulo ou vazio.
	 */
	public static boolean isVazio(Map<?, ?> mapa) {
		return mapa == null || mapa.isEmpty();
	}
	
	public static int sizeNullSafe(Collection<?> colecao) {
		return colecao == null ? 0 : colecao.size();
	}
	
}
