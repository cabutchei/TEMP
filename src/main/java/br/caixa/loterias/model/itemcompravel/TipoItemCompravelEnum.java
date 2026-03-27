package br.caixa.loterias.model.itemcompravel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoItemCompravelEnum {
    APOSTA(1L),
    RESERVA_COTA(2L);

    private final Long value;
}
