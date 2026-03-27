package br.caixa.loterias.model.estorno.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Map;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class SituacaoEstornoEnumConverter implements AttributeConverter<SituacaoEstornoEnum, Long> {

    static Map<SituacaoEstornoEnum, Long> CHAVES_ENUM = Map.ofEntries(
        Map.entry(SituacaoEstornoEnum.EM_PROCESSAMENTO, 1L),
        Map.entry(SituacaoEstornoEnum.ERRO, 2L),
        Map.entry(SituacaoEstornoEnum.ESTORNADO, 3L)
    );

    @Override
    public Long convertToDatabaseColumn(SituacaoEstornoEnum attribute) {
        return CHAVES_ENUM.get(attribute);
    }

    @Override
    public SituacaoEstornoEnum convertToEntityAttribute(Long dbData) {
        return CHAVES_ENUM.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey))
            .get(dbData);
    }
}