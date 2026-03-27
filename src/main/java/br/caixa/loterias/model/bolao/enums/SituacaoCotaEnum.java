package br.caixa.loterias.model.bolao.enums;

import java.util.List;

public enum SituacaoCotaEnum {

    INICIADA(1L),
    RESERVADA(2L),
    CANCELADA(3L),
    EM_PAGAMENTO(4L),
    CONFIRMADA(5L),
    EM_CANCELAMENTO(6L),
    CONTABILIZADA(7L);

    private final Long value;

    private SituacaoCotaEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static SituacaoCotaEnum getByValue(Long value) {
        for (SituacaoCotaEnum situacao : SituacaoCotaEnum.values()) {
            if (situacao.getValue().equals(value)) {
                return situacao;
            }
        }
        throw new IllegalArgumentException("Nenhuma situação de reserva encontrada para o valor: " + value);
    }

    public static List<SituacaoCotaEnum> getSituacoesFinalizadas() {
        return List.of(
            CONFIRMADA,
            CONTABILIZADA,
            CANCELADA
        );
    }
}
