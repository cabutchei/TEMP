package br.caixa.loterias.model.legado;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ModalidadeLegado implements CaixaEnumLegado<Integer> {

    // ordenadas em ordem alfabética
    DIA_DE_SORTE(11, "Dia de Sorte", "", OperacaoSisplLegado.REGISTRA_APOSTA_DIA_DE_SORTE, 17, null),
    DUPLA_SENA(18, "Dupla Sena", "Dupla de Páscoa", OperacaoSisplLegado.REGISTRA_APOSTA_DUPLA_SENA, 11, null),
    LOTECA(19, "Loteca", "", OperacaoSisplLegado.REGISTRA_APOSTA_LOTECA, 21, null),
    LOTOFACIL(8, "Lotofácil", "Lotofácil da Independência", OperacaoSisplLegado.REGISTRA_APOSTA_LOTOFACIL, 5, null),
    LOTOGOL(14, "Lotogol", "", OperacaoSisplLegado.REGISTRA_APOSTA_LOTOGOL, 23, null),
    LOTOMANIA(16, "Lotomania", "", OperacaoSisplLegado.REGISTRA_APOSTA_LOTOMANIA, 13, null),
    MAIS_MILIONARIA(9, "Mais Milionária", "", OperacaoSisplLegado.REGISTRA_APOSTA_MAIS_MILIONARIA, 9, "Milionária"),
    MEGA_SENA(2, "Mega-Sena", "Mega da Virada", OperacaoSisplLegado.REGISTRA_APOSTA_MEGA_SENA, 2, null),
    QUINA(3, "Quina", "Quina de São João", OperacaoSisplLegado.REGISTRA_APOSTA_QUINA, 7, null),
    SUPER_7(7, "Super Sete", "", OperacaoSisplLegado.REGISTRA_APOSTA_SUPER_7, 19, null),
    TIMEMANIA(20, "Timemania", "", OperacaoSisplLegado.REGISTRA_APOSTA_TIMEMANIA, 15, null),
    INSTANTANEA(1502, "Instantânea", "", null, 1, null);

    private final Integer codigo;
    private final String descricao;
    private final OperacaoSisplLegado movimentoSispl;
    private final String descricaoConcursoEspecial;
    private final Integer posicao;
    private final String descricaoGestor;

    private ModalidadeLegado(Integer codigo,
                             String descricao,
                             String descricaoConcursoEspecial,
                             OperacaoSisplLegado movimentoSispl,
                             Integer posicao,
                             String descricaoGestor) {

        this.codigo = codigo;
        this.descricao = descricao;
        this.descricaoConcursoEspecial = descricaoConcursoEspecial;
        this.movimentoSispl = movimentoSispl;
        this.posicao = posicao;

        if (descricaoGestor == null) {
            this.descricaoGestor = descricao;
        } else {
            this.descricaoGestor = descricaoGestor;
        }
    }

    public static List<ModalidadeLegado> getModalidades() {
        return Arrays.asList(ModalidadeLegado.values());
    }

    public static List<ModalidadeLegado> getModalidadesAtivas() {
        List<ModalidadeLegado> lista = new ArrayList<ModalidadeLegado>();
        for (ModalidadeLegado modalidade : ModalidadeLegado.values()) {
            if ((modalidade != LOTOGOL) && (modalidade != INSTANTANEA)) {
                lista.add(modalidade);
            }
        }
        return lista;
    }

    public static List<ModalidadeLegado> getModalidadesNaoEsportivas() {
        List<ModalidadeLegado> modalidades = new ArrayList<ModalidadeLegado>();
        for (ModalidadeLegado modalidade : ModalidadeLegado.values()) {
            if (!modalidade.isEsportivo()) {
                modalidades.add(modalidade);
            }
        }
        return modalidades;
    }

    public static Set<String> getModalidadesNormaisEspeciais() {
        Set<String> modalidades = new HashSet<String>();

        for (ModalidadeLegado modalidade : ModalidadeLegado.getModalidades()) {
            modalidades.add(modalidade.getCodigo() + "-" + TipoConcursoLegado.NORMAL.getCodigo());

            if (!modalidade.getDescricaoConcursoEspecial().equals("")) {
                modalidades.add(modalidade.getCodigo() + "-" + TipoConcursoLegado.ESPECIAL.getCodigo());
            }
        }

        return modalidades;
    }

    public static ModalidadeLegado getByDescricao(String descr) {
        String desc = StringUtilLegado.removeAcentosNotWordsUnderscoreToUpperTrim(descr);

        for (ModalidadeLegado modalidade : ModalidadeLegado.values()) {
            String normal = StringUtilLegado.removeAcentosNotWordsUnderscoreToUpperTrim(modalidade.descricao);
            String especial = StringUtilLegado.removeAcentosNotWordsUnderscoreToUpperTrim(modalidade.descricaoConcursoEspecial);

            if (normal.equals(desc) || especial.equals(desc)) {
                return modalidade;
            }
        }

        return null;
    }

    public static ModalidadeLegado getByCodigo(Integer codigo) {
        return EnumUtilLegado.recupereByValue(values(), codigo);
    }

    public static ModalidadeLegado getByEnumName(String enumName) {
        return EnumUtilLegado.valueOf(ModalidadeLegado.class, enumName);
    }

    public static Integer getQuantidadesEspeciais() {
        int qtdModalidadesEspeciais = 0;

        for (ModalidadeLegado modalidade : ModalidadeLegado.values()) {
            if (modalidade.temEspecial()) {
                qtdModalidadesEspeciais++;
            }
        }

        return qtdModalidadesEspeciais;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean temEspecial() {
        return !StringUtilLegado.isEmpty(descricaoConcursoEspecial);
    }

    public String getDescricao(TipoConcursoLegado tipoConcurso) {
        return getDescricao(tipoConcurso == null ? false : tipoConcurso.isEspecial());
    }

    public String getDescricao(Boolean especial) {
        if (especial != null && especial) {
            return getDescricaoConcursoEspecial();
        }
        return getDescricao();
    }

    public String getDescricaoGestor(TipoConcursoLegado tipoConcurso) {
        return getDescricaoGestor(tipoConcurso == null ? false : tipoConcurso.isEspecial());
    }

    public String getDescricaoGestor(Boolean especial) {
        if (especial != null && especial) {
            return getDescricaoConcursoEspecial();
        }
        return getDescricaoGestor();
    }

    public boolean isEsportivo() {
        return this.equals(LOTOGOL) || this.equals(LOTECA);
    }

    public boolean isNumerico() {
        return !isEsportivo() && !isSuperSete() && !isMaisMilionaria();
    }

    public boolean isMegaSena() {
        return this.equals(MEGA_SENA);
    }

    public boolean isQuina() {
        return this.equals(QUINA);
    }

    public boolean isLotofacil() {
        return this.equals(LOTOFACIL);
    }

    public boolean isLotogol() {
        return this.equals(LOTOGOL);
    }

    public boolean isLotomania() {
        return this.equals(LOTOMANIA);
    }

    public boolean isDuplaSena() {
        return this.equals(DUPLA_SENA);
    }

    public boolean isLoteca() {
        return this.equals(LOTECA);
    }

    public boolean isTimemania() {
        return this.equals(TIMEMANIA);
    }

    public boolean isDiaDeSorte() {
        return this.equals(DIA_DE_SORTE);
    }

    public boolean isSuperSete() {
        return this.equals(SUPER_7);
    }

    public boolean isMaisMilionaria() {
        return this.equals(MAIS_MILIONARIA);
    }

    public boolean isSISPL2() {
        return Arrays.asList(SUPER_7, MAIS_MILIONARIA).contains(this);
    }

    public static boolean isSISPL2(ModalidadeLegado modalidade) {
        return Arrays.asList(SUPER_7, MAIS_MILIONARIA).contains(modalidade);
    }

    @Override
    @JsonValue
    public Integer getValue() {
        return getCodigo();
    }

    public OperacaoSisplLegado getMovimentoSispl() {
        return movimentoSispl;
    }

    public String getDescricaoConcursoEspecial() {
        return descricaoConcursoEspecial;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public String getDescricaoGestor() {
        return descricaoGestor;
    }
}