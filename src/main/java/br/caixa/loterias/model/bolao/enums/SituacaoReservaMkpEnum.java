package br.caixa.loterias.model.bolao.enums;

public enum SituacaoReservaMkpEnum {

    CRIADA(0L),
    DISPONIVEL(1L),
    RESERVADA(2L),
    BAIXADA_NAO_IMPRESSA(3L),
    BAIXADA_NO_ENCERRAMENTO(4L),
    FISICA_IMPRESSA(5L),
    FISICA_NAO_IMPRESSA(6L),
    BAIXADA_IMPRESSA(7L),
    VENDIDA(8L),
    ESTORNADA(9L),
    SEM_NSBC(10L),
    FISICA_REIMPRESSA(11L);

    private final Long value;

    private SituacaoReservaMkpEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static SituacaoReservaMkpEnum getByValue(Long value) {
        for (SituacaoReservaMkpEnum situacao : SituacaoReservaMkpEnum.values()) {
            if (situacao.getValue().equals(value)) {
                return situacao;
            }
        }
        throw new IllegalArgumentException("Nenhuma situação de reserva MKP encontrada para o valor: " + value);
    }
}
