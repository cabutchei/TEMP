package br.caixa.loterias.model.legado;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SubCanalLegado implements CaixaEnumLegado<Integer> {

    SILCE(1, "Sistema Loterias Canal Eletrônico", "WEB"),
    SIMLO_IOS(2, "Sistema Móvel de Loterias Plataforma IOS", "IOS"),
    SIMLO_ANDROID(3, "Sistema Móvel de Loterias Plataforma Android", "ANDROID"),
    CAIXA_TEM(4, "Caixa Tem", "CAIXA TEM");

    private final Integer codigo;
    private final String descricao;
    private final String nomeFormatado;

    private SubCanalLegado(Integer codigo, String descricao, String nomeFormatado) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.nomeFormatado = nomeFormatado;
    }

    public static SubCanalLegado[] getSubcanaisContemCombo() {
        return new SubCanalLegado[]{ SILCE, SIMLO_ANDROID, SIMLO_IOS };
    }

    public static List<SubCanalLegado> getAllSubcanais() {
        return Arrays.asList(values());
    }

    public static List<SubCanalLegado> getAllSubCanaisAtivo() {
        List<SubCanalLegado> lista = new ArrayList<SubCanalLegado>();
        for (SubCanalLegado obj : SubCanalLegado.values()) {
            if (obj != SubCanalLegado.CAIXA_TEM) {
                lista.add(obj);
            }
        }
        return lista;
    }

    public static SubCanalLegado getByCodigo(Integer codigo) {
        return codigo != null
                ? EnumUtilLegado.recupereByValue(values(), codigo)
                : SILCE;
    }

    public static SubCanalLegado getByDescricao(String descricaoSubcanal) {
        for (SubCanalLegado subcanal : SubCanalLegado.values()) {
            if (subcanal.name().toLowerCase().contains(descricaoSubcanal.toLowerCase())
                    || subcanal.getDescricao().toLowerCase().contains(descricaoSubcanal.toLowerCase())) {
                return subcanal;
            }
        }
        return null;
    }

    public Integer getValue() {
        return codigo;
    }

    @JsonValue
    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNomeFormatado() {
        return nomeFormatado;
    }
}
