package br.caixa.loterias.model.legado;

/**
 * Dummy implementation created to satisfy missing dependency references.
 */
public class OperacaoSisplLegado {

    public static final OperacaoSisplLegado REGISTRA_APOSTA_DIA_DE_SORTE =
            new OperacaoSisplLegado("REGISTRA_APOSTA_DIA_DE_SORTE");
    public static final OperacaoSisplLegado REGISTRA_APOSTA_DUPLA_SENA =
            new OperacaoSisplLegado("REGISTRA_APOSTA_DUPLA_SENA");
    public static final OperacaoSisplLegado REGISTRA_APOSTA_LOTECA =
            new OperacaoSisplLegado("REGISTRA_APOSTA_LOTECA");
    public static final OperacaoSisplLegado REGISTRA_APOSTA_LOTOFACIL =
            new OperacaoSisplLegado("REGISTRA_APOSTA_LOTOFACIL");
    public static final OperacaoSisplLegado REGISTRA_APOSTA_LOTOGOL =
            new OperacaoSisplLegado("REGISTRA_APOSTA_LOTOGOL");
    public static final OperacaoSisplLegado REGISTRA_APOSTA_LOTOMANIA =
            new OperacaoSisplLegado("REGISTRA_APOSTA_LOTOMANIA");
    public static final OperacaoSisplLegado REGISTRA_APOSTA_MAIS_MILIONARIA =
            new OperacaoSisplLegado("REGISTRA_APOSTA_MAIS_MILIONARIA");
    public static final OperacaoSisplLegado REGISTRA_APOSTA_MEGA_SENA =
            new OperacaoSisplLegado("REGISTRA_APOSTA_MEGA_SENA");
    public static final OperacaoSisplLegado REGISTRA_APOSTA_QUINA =
            new OperacaoSisplLegado("REGISTRA_APOSTA_QUINA");
    public static final OperacaoSisplLegado REGISTRA_APOSTA_SUPER_7 =
            new OperacaoSisplLegado("REGISTRA_APOSTA_SUPER_7");
    public static final OperacaoSisplLegado REGISTRA_APOSTA_TIMEMANIA =
            new OperacaoSisplLegado("REGISTRA_APOSTA_TIMEMANIA");

    private final String descricao;

    private OperacaoSisplLegado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
