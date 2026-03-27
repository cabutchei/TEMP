package br.caixa.loterias.model.matrizprognostico;

import br.caixa.loterias.model.legado.ModalidadeLegado;
import lombok.Getter;

@Getter
public enum TipoMatrizPrognosticoEnum {

    // Mega-Sena
    MEGASENA_MATRIZ_UNICA(1L, "Mega-Sena - Matriz Única"),

    // Lotofácil
    LOTOFACIL_MATRIZ_UNICA(2L, "Lotofácil - Matriz Única"),

    // Quina
    QUINA_MATRIZ_UNICA(3L, "Quina - Matriz Única"),

    // Lotomania
    LOTOMANIA_MATRIZ_UNICA(4L, "Lotomania - Matriz Única"),

    // Dupla Sena
    DUPLASENA_MATRIZ_UNICA(5L, "Dupla Sena - Matriz Única"),

    // Dia de Sorte
    DIASORTE_MATRIZ_NUMERICA(7L, "Dia de Sorte - Matriz Numérica"),
    DIASORTE_MATRIZ_MES(8L, "Dia de Sorte - Mês da Sorte"),

    // Super Sete - 7 matrizes, uma para cada coluna
    SUPERSETE_MATRIZ_COLUNA1(33L, "Super Sete - Coluna 1"),
    SUPERSETE_MATRIZ_COLUNA2(34L, "Super Sete - Coluna 2"),
    SUPERSETE_MATRIZ_COLUNA3(35L, "Super Sete - Coluna 3"),
    SUPERSETE_MATRIZ_COLUNA4(36L, "Super Sete - Coluna 4"),
    SUPERSETE_MATRIZ_COLUNA5(37L, "Super Sete - Coluna 5"),
    SUPERSETE_MATRIZ_COLUNA6(38L, "Super Sete - Coluna 6"),
    SUPERSETE_MATRIZ_COLUNA7(39L, "Super Sete - Coluna 7"),

    // Timemania
    TIMEMANIA_MATRIZ_NUMERICA(10L, "Timemania - Matriz Numérica"),
    TIMEMANIA_MATRIZ_TIME(11L, "Timemania - Time do Coração"),

    // Mais Milionária
    MAISMILIONARIA_MATRIZ_NUMERICA(12L, "Mais Milionária - Matriz Numérica"),

    // Loteca - Partidas individuais
    LOTECA_MATRIZ_PARTIDA1(14L, "Loteca - Partida 1"),
    LOTECA_MATRIZ_PARTIDA2(15L, "Loteca - Partida 2"),
    LOTECA_MATRIZ_PARTIDA3(16L, "Loteca - Partida 3"),
    LOTECA_MATRIZ_PARTIDA4(17L, "Loteca - Partida 4"),
    LOTECA_MATRIZ_PARTIDA5(18L, "Loteca - Partida 5"),
    LOTECA_MATRIZ_PARTIDA6(19L, "Loteca - Partida 6"),
    LOTECA_MATRIZ_PARTIDA7(20L, "Loteca - Partida 7"),
    LOTECA_MATRIZ_PARTIDA8(21L, "Loteca - Partida 8"),
    LOTECA_MATRIZ_PARTIDA9(22L, "Loteca - Partida 9"),
    LOTECA_MATRIZ_PARTIDA10(23L, "Loteca - Partida 10"),
    LOTECA_MATRIZ_PARTIDA11(24L, "Loteca - Partida 11"),
    LOTECA_MATRIZ_PARTIDA12(25L, "Loteca - Partida 12"),
    LOTECA_MATRIZ_PARTIDA13(26L, "Loteca - Partida 13"),
    LOTECA_MATRIZ_PARTIDA14(27L, "Loteca - Partida 14");

    private final Long codigo;
    private final String descricao;

    TipoMatrizPrognosticoEnum(Long codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    /**
     * Busca um tipo de matriz pelo código.
     */
    public static TipoMatrizPrognosticoEnum getByCodigo(Long codigo) {
        for (TipoMatrizPrognosticoEnum tipo : values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Codigo de tipo de matriz não encontrado: " + codigo);
    }

    /**
     * Obtém o tipo de matriz para a modalidade informada, se a modalidade for de matriz única.
     */
    public static TipoMatrizPrognosticoEnum getTipoMatrizUnicaByModalidade(ModalidadeLegado modalidade) {
        if (modalidade == null) {
            throw new IllegalArgumentException("Modalidade não informada");
        }

        return switch (modalidade) {
            case MEGA_SENA -> MEGASENA_MATRIZ_UNICA;
            case QUINA -> QUINA_MATRIZ_UNICA;
            case LOTOFACIL -> LOTOFACIL_MATRIZ_UNICA;
            case LOTOMANIA -> LOTOMANIA_MATRIZ_UNICA;
            case DUPLA_SENA -> DUPLASENA_MATRIZ_UNICA;
            default -> throw new IllegalStateException("Modalidade não suportada: " + modalidade);
        };
    }
}