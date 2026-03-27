package br.caixa.loterias.model.legado;

import lombok.Data;
import java.util.Date;

@Data
public class TipoComboLegado {

    private static final long serialVersionUID = 1L;

    public static final String NQ_SELECT_COMBOS_ORDER_BY_ORDEM =
            "TipoCombo.NQ_SELECT_COMBOS_ORDER_BY_ORDEM";

    public enum Tipo implements CaixaEnumLegado<Long> {
        ESPECIAL(1L),
        SUPER_MILIONARIO(2L),
        MILIONARIO(3L),
        MUITO_DINHEIRO(4L),
        SORTE_FACIL(5L),
        CHANCE_TODO_DIA(6L),
        SORTE_ACUMULADA(7L),
        SORTE_HOJE(8L),
        FEZINHA_DO_MES(9L),
        VIP(10L),
        CAIXA_PRA_ELAS(11L);

        private final Long value;

        private Tipo(Long value) {
            this.value = value;
        }

        @Override
        public Long getValue() {
            return value;
        }
    }

    private Long id;
    private String nome;
    private String descricao;
    private Integer ordem;
    private Date dataCriacao;
    private Boolean comboDisponivel = Boolean.TRUE;
    private Boolean novo;

    protected Tipo[] createValores() {
        return Tipo.values();
    }

    public Boolean getComboDisponivel() {
        return comboDisponivel;
    }

    public void setComboDisponivel(Boolean comboDisponivel) {
        this.comboDisponivel = comboDisponivel;
    }

    public boolean isDisponivel() {
        return this.getComboDisponivel() == null
                ? Boolean.TRUE
                : this.getComboDisponivel();
    }
}