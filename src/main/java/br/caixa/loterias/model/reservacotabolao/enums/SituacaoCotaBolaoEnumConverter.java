package br.caixa.loterias.model.reservacotabolao.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.caixa.loterias.model.reservacotabolao.enums.SituacaoReservaCotaBolaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SituacaoCotaBolaoEnumConverter implements AttributeConverter<SituacaoReservaCotaBolaoEnum, Long>{

    static Map<SituacaoReservaCotaBolaoEnum, Long> CHAVES_ENUM = Arrays.stream(SituacaoReservaCotaBolaoEnum.values())
    .collect(Collectors.toMap(Function.identity(), SituacaoReservaCotaBolaoEnum::getValue));

    @Override
    public Long convertToDatabaseColumn(SituacaoReservaCotaBolaoEnum attribute) {
        return CHAVES_ENUM.get(attribute);
    }

    @Override
    public SituacaoReservaCotaBolaoEnum convertToEntityAttribute(Long dbData) {
        return CHAVES_ENUM.entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey)).get(dbData) ;
    }
    
}
