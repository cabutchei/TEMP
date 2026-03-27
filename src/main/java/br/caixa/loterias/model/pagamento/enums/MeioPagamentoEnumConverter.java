package br.caixa.loterias.model.pagamento.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class MeioPagamentoEnumConverter implements AttributeConverter<MeioPagamentoEnum, Long> {

    static Map<MeioPagamentoEnum, Long> CHAVES_ENUM = Arrays.stream(MeioPagamentoEnum.values())
        .collect(Collectors.toMap(
            Function.identity(),
            MeioPagamentoEnum::getValue
        ));

    @Override
    public Long convertToDatabaseColumn(MeioPagamentoEnum attribute) {
        return CHAVES_ENUM.get(attribute);
    }

    @Override
    public MeioPagamentoEnum convertToEntityAttribute(Long dbData) {
        return CHAVES_ENUM.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey))
            .get(dbData);
    }
}