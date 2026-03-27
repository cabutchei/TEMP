package br.caixa.loterias.model.aposta.enums;

import java.util.List;

public enum SituacaoApostaEnum {

    NAO_REGISTRADA(1L),
    EM_PROCESSAMENTO(2L),
    EM_PROCESSAMENTO_ENVIADA_SISPL(3L),
    EFETIVADA(4L),
    NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO(5L),
    NAO_EFETIVADA_DINHEIRO_DEVOLVIDO(6L),
    PAGAMENTO_EM_PROCESSAMENTO(7L),
    CANCELADA(8L),
    NAO_PREMIADA(9L),
    PRESCRITA(10L),
    PREMIO_PAGO(11L),
    NAO_EFETIVADA_AGUARDANDO_DEVOLUCAO_PIX(12L);

    private final Long value;

    private SituacaoApostaEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static SituacaoApostaEnum getByValue(Long value) {
        for (SituacaoApostaEnum situacao : SituacaoApostaEnum.values()) {
            if (situacao.getValue().equals(value)) {
                return situacao;
            }
        }
        throw new IllegalArgumentException("Nenhuma situação de aposta encontrada para o valor: " + value);
    }

    public static List<SituacaoApostaEnum> getSituacoesFinalizadas() {
        return List.of(
            EFETIVADA,
            NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO
        );
    }
}
