package br.caixa.loterias.model.legado;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

/**
 * Dummy implementation created to satisfy missing dependency references.
 */
@Data
public class SituacaoApostaLegado {

    public enum SituacaoLegado implements CaixaEnumLegado<Long> {
        PENDENTE(1L),
        PROCESSANDO(2L),
        EFETIVADA(3L),
        CANCELADA(4L);

        private final Long value;

        SituacaoLegado(Long value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public Long getValue() {
            return value;
        }
    }

    private Long id;

    private String descricao;
}
