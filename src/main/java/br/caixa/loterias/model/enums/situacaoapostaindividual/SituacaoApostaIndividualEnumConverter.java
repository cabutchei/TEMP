package br.caixa.loterias.model.enums.situacaoapostaindividual;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Dummy implementation created to satisfy missing dependency references.
 */
@Converter(autoApply = true)
public class SituacaoApostaIndividualEnumConverter
        implements AttributeConverter<SituacaoApostaIndividualEnum, Long> {

    @Override
    public Long convertToDatabaseColumn(SituacaoApostaIndividualEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public SituacaoApostaIndividualEnum convertToEntityAttribute(Long dbData) {
        if (dbData == null) {
            return null;
        }

        for (SituacaoApostaIndividualEnum value : SituacaoApostaIndividualEnum.values()) {
            if (value.getValue().equals(dbData)) {
                return value;
            }
        }

        return null;
    }
}
