package br.caixa.loterias.model.legado;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IndicadorSurpresinhaLegado implements CaixaEnumLegado<Integer> {

    NAO_SURPRESINHA(1),
    SURPRESINHA(2),
    SURPRESINHA_NUMERICA(3);

    private final Integer codigo;

    private IndicadorSurpresinhaLegado(Integer codigo) {
        this.codigo = codigo;
    }

    @JsonValue
    public Integer getCodigo() {
        return codigo;
    }

    public static IndicadorSurpresinhaLegado getByCodigo(Integer codigo) {
        return EnumUtilLegado.recupereByValue(values(), codigo);
    }

    public boolean isSurpresinha() {
        return !this.equals(NAO_SURPRESINHA);
    }

    @Override
    public Integer getValue() {
        return getCodigo();
    }
}