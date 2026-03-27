package br.caixa.loterias.model.legado;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ApostaCompradaLegado {

    private Long nsu;

    private String nsuDevolucao;

    private Integer concursoInicial;

    private NSBLegado nsb;

    private Boolean bilheteTroca;

    private Date tsInicioApostaComprada;

    private Date tsEnvioSispl;

    private Date tsEfetivacaoSispl;

    private Date tsFinalizacaoProcessamento;

    private SituacaoApostaLegado.SituacaoLegado situacao;

    private Date tsUltimaSituacao;

    private BigDecimal valorComissao;

    @ToString.Exclude
    private ApostaLegado aposta;

    private SubCanalLegado subcanal;

    private Boolean bloqueioDevolucao;

    private String erroAposta;

    private String erroResgate;
}
