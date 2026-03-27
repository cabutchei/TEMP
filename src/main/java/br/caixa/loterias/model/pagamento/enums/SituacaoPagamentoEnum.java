package br.caixa.loterias.model.pagamento.enums;

import java.util.List;

public enum SituacaoPagamentoEnum {

    /**
     * Estado transitório para indicar que ainda não obtemos resposta do meio de pagamento.
     * No Legado era utilizado para estorno do PIX
     */
    EM_PROCESSAMENTO,

    /**
     * Aprovado pelo Meio de Pagamento
     */
    APROVADO,

    /**
     * Feito, porém não passou pelas validações da integração entre o Meio de Pagamento e o SILCE
     */
    INVALIDO,

    /**
     * Estornado pelo Meio de Pagamento
     */
    ESTORNADO,

    /**
     * Rejeitado pelo Meio de Pagamento
     */
    REJEITADO,

    /**
     * Não foi feito o pagamento
     */
    CANCELADO,

    /**
     * (PIX) Cobrança foi criada, mas ainda não foi paga.
     */
    ATIVO,

    /**
     * Não foi feito o pagamento
     */
    INEXISTENTE,

    /**
     * Ocorreu um erro de aplicação ao tentar processar o pagamento
     * Geralmente tratamos o erro, mas na etapa 2, vamos deixar que o legado trate
     */
    ERRO;

    public boolean isFinal() {
        return this != ATIVO && this != EM_PROCESSAMENTO;
    }

    public boolean isValidoParaPagamentoProcessado() {
        List<SituacaoPagamentoEnum> situacoesValidas = List.of(
            APROVADO,
            INVALIDO,
            REJEITADO,
            CANCELADO,
            ERRO
        );
        return situacoesValidas.contains(this);
    }

    public boolean requerIdTransacao() {
        List<SituacaoPagamentoEnum> situacoesQuePrecisamIdTransacao = List.of(
            APROVADO
        );
        return situacoesQuePrecisamIdTransacao.contains(this);
    }
}