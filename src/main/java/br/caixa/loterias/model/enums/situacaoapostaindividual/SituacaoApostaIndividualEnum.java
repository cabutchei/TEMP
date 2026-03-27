package br.caixa.loterias.model.enums.situacaoapostaindividual;

import lombok.Getter;

/**
 * Dummy implementation created to satisfy missing dependency references.
 */
@Getter
public enum SituacaoApostaIndividualEnum {
    NAO_REGISTRADA(1L),
    EM_PROCESSAMENTO(2L),
    EM_PROCESSAMENTO_ENVIADA_SISPL(3L),
    PAGAMENTO_EM_PROCESSAMENTO(4L),
    NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO(5L);

    private final Long value;

    SituacaoApostaIndividualEnum(Long value) {
        this.value = value;
    }
}
