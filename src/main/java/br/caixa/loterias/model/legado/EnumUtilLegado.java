package br.caixa.loterias.model.legado;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnumUtilLegado {

    public EnumUtilLegado() {
    }

    public static <T extends Enum<T>> T valueOf(Class<T> enumClass, String enumName) {
        try {
            return Enum.valueOf(enumClass, enumName);
        } catch(Exception exception) {
            return null;
        }
    }

    public static <E extends CaixaEnumLegado<T>, T extends Serializable> E recupereByValue(E[] enums, T value) {
        if (enums == null) {
            return null;
        }
        for (E caixaEnum : enums) {
            if (caixaEnum != null && Objects.equals(caixaEnum.getValue(), value)) {
                return caixaEnum;
            }
        }
        return null;
    }

    public static <T extends Serializable> List<T> recupereValuesByEnums(Iterable<? extends CaixaEnumLegado<T>> enums) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (CaixaEnumLegado<T> caixaEnum : enums) {
            arrayList.add(caixaEnum.getValue());
        }
        return arrayList;
    }

    // public static <E extends CaixaEnumLegado<T>, T extends Serializable> List<E> recupereEnumsByValues(E[] enums, Iterable<T> values) {
    //     ArrayList<E>
    // }

}
