package br.caixa.loterias.model.legado;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoConcursoLegado implements CaixaEnumLegado<Character> {

    NORMAL(1, "Normal"),
    ESPECIAL(2, "Especial");

    private final Integer codigo;
    private final String descricao;

    private TipoConcursoLegado(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @JsonValue
    public Integer getCodigo() {
        return codigo;
    }

    public static TipoConcursoLegado getByDescricao(String descr) {
        if (descr == null) {
            return null;
        }

        String desc = StringUtilLegado.removeAcentosAndToUpper(descr)
                .trim()
                .replaceAll("[- ]", "");

        for (TipoConcursoLegado tipoConcurso : TipoConcursoLegado.values()) {
            String normalizada = StringUtilLegado
                    .removeAcentosAndToUpper(tipoConcurso.descricao)
                    .trim()
                    .replaceAll("[- ]", "");

            if (normalizada.equals(desc)) {
                return tipoConcurso;
            }
        }

        return null;
    }

    public static TipoConcursoLegado getByCodigo(Character codigo) {
        return EnumUtilLegado.recupereByValue(values(), codigo);
    }

    public boolean isNormal() {
        return getCodigo().equals(NORMAL.getCodigo());
    }

    public boolean isEspecial() {
        return getCodigo().equals(ESPECIAL.getCodigo());
    }

    @Override
    public Character getValue() {
        return getCodigo().toString().toCharArray()[0];
    }

    public String getDescricao() {
        return descricao;
    }
}