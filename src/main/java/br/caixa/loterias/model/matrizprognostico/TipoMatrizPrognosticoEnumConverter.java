package br.caixa.loterias.model.matrizprognostico;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoMatrizPrognosticoEnumConverter implements AttributeConverter<TipoMatrizPrognosticoEnum, Long> {

    @Override
    public Long convertToDatabaseColumn(TipoMatrizPrognosticoEnum tipoMatriz) {
        if (tipoMatriz == null) {
            return null;
        }
        return tipoMatriz.getCodigo();
    }

    @Override
    public TipoMatrizPrognosticoEnum convertToEntityAttribute(Long codigo) {
        if (codigo == null) {
            return null;
        }
        return TipoMatrizPrognosticoEnum.getByCodigo(codigo);
    }
}