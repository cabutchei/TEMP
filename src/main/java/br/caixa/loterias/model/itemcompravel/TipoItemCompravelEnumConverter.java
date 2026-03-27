package br.caixa.loterias.model.itemcompravel;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class TipoItemCompravelEnumConverter implements AttributeConverter<TipoItemCompravelEnum, Long> {

    static Map<TipoItemCompravelEnum, Long> CHAVES_ENUM = Arrays.stream(TipoItemCompravelEnum.values())
        .collect(Collectors.toMap(
            Function.identity(),
            TipoItemCompravelEnum::getValue
        ));

    @Override
    public Long convertToDatabaseColumn(TipoItemCompravelEnum attribute) {
        return CHAVES_ENUM.get(attribute);
    }

    @Override
    public TipoItemCompravelEnum convertToEntityAttribute(Long dbData) {
        return CHAVES_ENUM.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey))
            .get(dbData);
    }
}
