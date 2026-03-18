package br.gov.caixa.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 *
 */
public final class GenericsUtil {
	
	//Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static GenericsUtil instancia = new GenericsUtil();

	
	private GenericsUtil(){
	}

	public static Class<?> getFirstDeclaredTypeArgument(Class<?> clazz) {
		return getFirstDeclaredTypeArgument(clazz, false);
	}

	/**
	 * @param clazz
	 * @param includeSuperclass
	 *            se true, irá pesquisar o tipo generico nos pais, caso clazz nao seja parametrizável
	 * @return
	 */
	public static Class<?> getFirstDeclaredTypeArgument(Class<?> clazz, boolean includeSuperclass) {
		return getDeclaredTypeArgument(clazz, 0, includeSuperclass);
	}

	/**
	 * @param clazz
	 * @param typePosition
	 * @throws IllegalArgumentException Se não for possível definir o tipo
	 * @return
	 */
	public static Class<?> getDeclaredTypeArgument(Class<?> clazz,
		int typePosition, boolean includeSuperclass) {
		Type genericSuperclass = clazz.getGenericSuperclass();

		if (genericSuperclass instanceof ParameterizedType) {
			Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[typePosition];
			if (type instanceof Class<?>) {
				return (Class<?>) type;
			} else if (type instanceof ParameterizedType) {
				return (Class<?>) ((ParameterizedType) type).getRawType();
			}

		}
		if (includeSuperclass && clazz.getSuperclass() != null) {
			return getDeclaredTypeArgument(clazz.getSuperclass(), typePosition, includeSuperclass);
		}
		throw new IllegalArgumentException("Classe não é parametrizável");
	}

	public static Class<?> getDeclaredTypeArgument(Class<?> clazz,
		int typePosition) {
		return getDeclaredTypeArgument(clazz, typePosition, false);

	}

}
