package br.caixa.loterias.model.pagamento.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Map;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class SituacaoPagamentoEnumConverter implements AttributeConverter<SituacaoPagamentoEnum, Long> {

    static Map<SituacaoPagamentoEnum, Long> CHAVES_ENUM = Map.ofEntries(
        Map.entry(SituacaoPagamentoEnum.EM_PROCESSAMENTO, 1L),
        Map.entry(SituacaoPagamentoEnum.APROVADO, 2L),
        Map.entry(SituacaoPagamentoEnum.INVALIDO, 3L),
        Map.entry(SituacaoPagamentoEnum.ESTORNADO, 4L),
        Map.entry(SituacaoPagamentoEnum.REJEITADO, 5L),
        Map.entry(SituacaoPagamentoEnum.CANCELADO, 6L),
        Map.entry(SituacaoPagamentoEnum.ATIVO, 7L),
        Map.entry(SituacaoPagamentoEnum.INEXISTENTE, 8L),
        Map.entry(SituacaoPagamentoEnum.ERRO, 9L)
    );

    @Override
    public Long convertToDatabaseColumn(SituacaoPagamentoEnum attribute) {
        return CHAVES_ENUM.get(attribute);
    }

    @Override
    public SituacaoPagamentoEnum convertToEntityAttribute(Long dbData) {
        return CHAVES_ENUM.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey))
            .get(dbData);
    }
}