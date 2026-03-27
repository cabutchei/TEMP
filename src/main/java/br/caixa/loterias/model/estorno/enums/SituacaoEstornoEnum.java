package br.caixa.loterias.model.estorno.enums;

import java.util.List;

public enum SituacaoEstornoEnum {

    EM_PROCESSAMENTO,
    ESTORNADO,
    ERRO;

    public boolean requerIdTransacao() {
        List<SituacaoEstornoEnum> situacoesQuePrecisamIdTransacao = List.of(
            ESTORNADO
        );
        return situacoesQuePrecisamIdTransacao.contains(this);
    }
}