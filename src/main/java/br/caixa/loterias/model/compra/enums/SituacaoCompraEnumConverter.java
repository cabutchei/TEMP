package br.caixa.loterias.model.compra.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SituacaoCompraEnumConverter implements AttributeConverter<SituacaoCompraEnum, Long> {

    static Map<SituacaoCompraEnum, Long> CHAVES_ENUM = Arrays.stream(SituacaoCompraEnum.values())
        .collect(Collectors.toMap(
            Function.identity(), // Key: enum constant name (e.g., "SMALL")
            SituacaoCompraEnum::getValue // Value: custom value from the enum
        ));

    @Override
    public Long convertToDatabaseColumn(SituacaoCompraEnum attribute) {
        return CHAVES_ENUM.get(attribute);
    }

    @Override
    public SituacaoCompraEnum convertToEntityAttribute(Long dbData) {
        return CHAVES_ENUM.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey))
            .get(dbData);
    }
}
