package br.caixa.loterias.model.compra.enums;

import java.util.List;

public enum SituacaoCompraEnum {

    CARRINHO(1L),
    DEBITO_INICIADO(2L),
    DEBITO_NAO_AUTORIZADO(3L),
    ESTORNADA(4L),
    CANCELADA(5L),
    DEBITO_REALIZADO(6L),
    EM_PROCESSAMENTO(7L),
    FINALIZADA(8L),
    EM_PROCESSAMENTO_REPRESADA(9L),
    AGUARDANDO_PAGAMENTO_PIX(10L),
    AGUARDANDO_DEVOLUCAO_PIX(11L),
    CANCELADA_PAGAMENTO_NAO_IDENTIFICADO_TEMPO_LIMITE(12L),
    FINALIZADA_TODAS_APOSTAS_EFETIVADAS(13L),
    FINALIZADA_CONTEM_APOSTAS_NAO_EFETIVADAS(14L);

    private final Long value;

    private SituacaoCompraEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static SituacaoCompraEnum getByValue(Long value) {
        for (SituacaoCompraEnum situacao : SituacaoCompraEnum.values()) {
            if (situacao.getValue().equals(value)) {
                return situacao;
            }
        }
        throw new IllegalArgumentException("Nenhuma situação de compra encontrada para o valor: " + value);
    }

    public static List<SituacaoCompraEnum> getSituacoesEmProcessamento() {
        return List.of(EM_PROCESSAMENTO, EM_PROCESSAMENTO_REPRESADA);
    }
}
