package br.caixa.loterias.model.legado;

import com.fasterxml.jackson.annotation.JsonValue;

public class SituacaoApostadorLegado {

    private static final long serialVersionUID = 1L;

    public enum Situacao implements CaixaEnumLegado<Long> {
        ATIVO(1L),
        BLOQUEADO(2L),
        BLOQUEADO_TOTAL(3L),
        BLOQUEADO_PARCIAL(4L);

        private final Long value;

        private Situacao(Long value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public Long getValue() {
            return value;
        }

        public boolean isBloqueado() {
            return BLOQUEADO.equals(this);
        }

        public boolean isAtivo() {
            return ATIVO.equals(this);
        }

        /**
         * @return todas as situações em que o apostador tenha sido bloqueado pelo sicow
         */
        public static Situacao[] getSituacoesBloqueioSicow() {
            return new Situacao[]{BLOQUEADO_TOTAL, BLOQUEADO_PARCIAL};
        }
    }

    private Long id;
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    protected Situacao[] createValores() {
        return Situacao.values();
    }

    @Override
    public String toString() {
        return "SituacaoApostador [id=" + id + ", descricao=" + descricao + "]";
    }
}