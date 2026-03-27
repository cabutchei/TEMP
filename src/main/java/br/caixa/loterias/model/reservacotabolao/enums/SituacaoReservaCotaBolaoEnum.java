package br.caixa.loterias.model.reservacotabolao.enums;

import lombok.Getter;

/**
 * Dummy implementation created to satisfy missing dependency references.
 */
@Getter
public enum SituacaoReservaCotaBolaoEnum {
    RESERVADA(1L),
    CANCELADA(2L);

    private final Long value;

    SituacaoReservaCotaBolaoEnum(Long value) {
        this.value = value;
    }
}
