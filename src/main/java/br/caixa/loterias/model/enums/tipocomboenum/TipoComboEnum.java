package br.caixa.loterias.model.enums.tipocomboenum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TipoComboEnum {

    ESPECIAL(1L, "Especial", "Pacote contendo apostas para o concurso especial vigente."),
    SUPER_MILIONARIO(2L, "Super Milionário", "Pacote contendo apostas para concursos com prêmios acima de R$ 5 milhões, incluindo o concurso especial."),
    MILIONARIO(3L, "Milionário", "Pacote contendo apostas para concursos com prêmios acima de R$ 1,5 milhão, incluindo o concurso especial."),
    MUITO_DINHEIRO(4L, "Muito Dinheiro", "Pacote contendo apostas para concursos com prêmios acima de R$ 200 mil, incluindo o concurso especial."),
    SORTE_FACIL(5L, "Sorte Fácil", "Pacote contendo apostas das modalidades Dia de Sorte, Lotofácil e Quina."),
    CHANCE_TODO_DIA(6L, "Chance Todo Dia", "Testes de mensagem de Combo Chance Todo Dia!"),
    SORTE_ACUMULADA(7L, "Sorte Acumulada", "Pacote contendo apostas para todas as modalidades cujo prêmio está acumulado, incluindo o concurso especial."),
    SORTE_HOJE(8L, "Sorte de Hoje", "Testes de mensagem de Combo"),
    FEZINHA_DO_MES(9L, "Fezinha do Mês", "Pacote contendo a quantidade máxima de teimosinhas de todas as modalidades disponíveis, exceto as especiais."),
    VIP(10L, "Combo Vip", "Pacote contendo apostas múltiplas para as modalidades mais vendidas do Brasil!!!"),
    CAIXA_PRA_ELAS(11L, "Loterias Pra Elas", "Pacote contendo apostas para o concurso disponíveis das seguintes modalidades: +Milionária");

    @JsonValue
    private final Long codigo;

    private final String nome;

    private final String descricao;

    TipoComboEnum(Long codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    @JsonCreator
    public static TipoComboEnum getByCodigo(Long codigo) {
        for (TipoComboEnum tipo : TipoComboEnum.values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de TipoComboEnum não encontrado: " + codigo);
    }

    public static String getNomeByCodigo(Long codigo) {
        for (TipoComboEnum tipo : TipoComboEnum.values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo.getNome();
            }
        }
        throw new IllegalArgumentException("Código de TipoComboEnum não encontrado: " + codigo);
    }
}
