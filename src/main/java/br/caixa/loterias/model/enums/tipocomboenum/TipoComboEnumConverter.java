package br.caixa.loterias.model.enums.tipocomboenum;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Dummy implementation created to satisfy missing dependency references.
 */
@Converter(autoApply = true)
public class TipoComboEnumConverter implements AttributeConverter<TipoComboEnum, Long> {

    @Override
    public Long convertToDatabaseColumn(TipoComboEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoComboEnum convertToEntityAttribute(Long dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoComboEnum.getByCodigo(dbData);
    }
}
