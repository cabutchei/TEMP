package br.gov.caixa.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 *
 */
public final class EnumUtil {

	//Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static EnumUtil instancia = new EnumUtil();
	
	private EnumUtil() {
	}
	
	/**
	 * @param enumClass
	 * @param enumName
	 * @return returna o Enum que tem aquele nome, null caso não exista
	 */
	public static <T extends Enum<T>> T valueOf(Class<T> enumClass, String enumName) {
		try {
			return Enum.valueOf(enumClass, enumName);
		}catch (Exception exception) {
			return null;
		}
	}
	
	/**
	 * @param enums
	 * @param value
	 * @return
	 */
	public static <E extends CaixaEnum<T>, T extends Serializable> E recupereByValue(E[] enums, T value) {
		for (E caixaEnum : enums) {
			if(caixaEnum.getValue().equals(value)) {
				return caixaEnum;
			}
		}
		return null;
	}
	
	/**
	 * @return uma lista com os VALUEs de todos os ENUMS passados
	 */
	public static <T extends Serializable> List<T> recupereValuesByEnums(Iterable<? extends CaixaEnum<T>> enums) {
		ArrayList<T> arrayList = new ArrayList<T>();
		for (CaixaEnum<T> caixaEnum : enums) {
			arrayList.add(caixaEnum.getValue());
		}
		return arrayList;
	}
	
	/**
	 * @return uma lista com os ENUMs de acordo com os VALUES passados
	 */
	public static <E extends CaixaEnum<T>, T extends Serializable> List<E> recupereEnumsByValues(E[] enums, Iterable<T> values) {
		ArrayList<E> arrayList = new ArrayList<E>();
		for (T value : values) {
			E recupereByValue = recupereByValue(enums, value);
			arrayList.add(recupereByValue);
		}
		return arrayList;
	}
	
}
