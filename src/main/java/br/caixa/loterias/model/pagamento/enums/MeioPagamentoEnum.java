package br.caixa.loterias.model.pagamento.enums;

import lombok.Getter;

@Getter
public enum MeioPagamentoEnum {

    MERCADO_PAGO(1L, "MPG"),
    CARTEIRA_ELETRONICA(2L, "DMO"),
    PONTOS_SILCE(3L, "PLE"),
    MERCADO_PAGO_BASICO(4L, "MPB"),
    RECARGAPAY(5L, "RPY"),
    PIX(6L, "PIX");

    private final Long codigo;
    private final String sigla;

    private MeioPagamentoEnum(Long codigo, String sigla) {
        this.codigo = codigo;
        this.sigla = sigla;
    }

    public static MeioPagamentoEnum getByValue(Long value) {
        if (value == null) {
            return null;
        }
        for (MeioPagamentoEnum meio : values()) {
            if (meio.codigo.equals(value)) {
                return meio;
            }
        }
        return null;
    }

    public Long getValue() {
        return this.codigo;
    }
}