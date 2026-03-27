package br.caixa.loterias.model.aposta;

import br.caixa.loterias.model.combo.Combo;
import br.caixa.loterias.model.enums.situacaoapostaindividual.SituacaoApostaIndividualEnum;
import br.caixa.loterias.model.enums.situacaoapostaindividual.SituacaoApostaIndividualEnumConverter;
import br.caixa.loterias.model.matrizprognostico.MatrizPrognostico;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "LCE", name = "_APOSTA_INDIVIDUAL")
public class ApostaIndividual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NU_APOSTA", nullable = false)
    private Long id;

    @Column(name = "NU_APOSTA_LEGADO")
    private Long idLegado;

    /**
     * Indica se a aposta faz parte de um combo.
     */
    @Column(name = "IC_COMBO")
    private Boolean indicadorCombo;

    /**
     * Quantidade de concursos da teimosinha.
     */
    @Column(name = "NU_TEIMOSINHA_QTD")
    private Integer teimosinhaQtd;

    /**
     * Número do concurso inicial para apostas teimosinha.
     */
    @Column(name = "NU_TEIMOSINHA_CONCURSO_INICIAL")
    private Integer teimosinhaConcursoInicial;

    @Column(name = "IC_TEIMOSINHA_BILHETE_TROCA")
    private Boolean teimosinhaBilheteTroca;

    /**
     * Indica se esta aposta é espelho de outra.
     */
    @Column(name = "IC_ESPELHO")
    private Boolean espelho;

    /**
     * Indica se esta aposta gera uma aposta espelho.
     */
    @Column(name = "IC_GERA_ESPELHO")
    private Boolean geraEspelho;

    /**
     * Se for uma aposta espelho, este campo referencia o número da aposta original.
     */
    @Column(name = "NU_APOSTA_VINCULO_ESPELHO")
    private Long apostaVinculoEspelho;

    /**
     * Valor total da aposta em reais.
     */
    @Column(name = "VR_VALOR", precision = 38, scale = 2)
    private BigDecimal valor;

    /**
     * Indicador de surpresinha (escolha aleatória de números).
     */
    @Column(name = "IC_SURPRESINHA")
    private boolean surpresinha;

    /**
     * Número do concurso para o qual a aposta foi feita.
     */
    @Column(name = "NU_CONCURSO_ALVO")
    private Integer concursoAlvo;

    /**
     * Situação atual da aposta.
     */
    @Column(name = "NU_SITUACAO")
    @Convert(converter = SituacaoApostaIndividualEnumConverter.class)
    private SituacaoApostaIndividualEnum situacao;

    /**
     * Código do tipo de concurso.
     */
    @Column(name = "NU_TIPO_CONCURSO")
    private Long tipoConcurso;

    /**
     * Código da modalidade da aposta.
     */
    @Column(name = "NU_MODALIDADE")
    private Long modalidade;

    /**
     * Número de série do bilhete.
     */
    @Column(name = "NU_SERIE_BILHETE", length = 36)
    private String serieBilhete;

    /**
     * Número Sequencial Único da aposta no sistema.
     */
    @Column(name = "NU_NSU_APOSTA")
    private Long nsuAposta;

    /**
     * Data e hora de inclusão da aposta no sistema.
     */
    @Column(name = "TS_INCLUSAO_APOSTA")
    private LocalDateTime timestampInclusao;

    /**
     * Data e hora de envio da aposta para o SISPL.
     */
    @Column(name = "TS_ENVIO_SISPL")
    private LocalDateTime timestampEnvioSispl;

    /**
     * Data e hora de efetivação da aposta no SISPL.
     */
    @Column(name = "TS_EFETIVACAO_SISPL")
    private LocalDateTime timestampEfetivacaoSispl;

    /**
     * Data e hora de finalização do processamento da aposta.
     */
    @Column(name = "TS_FINALIZACAO_PROCESSAMENTO")
    private LocalDateTime timestampFinalizacaoProcessamento;

    /**
     * Data e hora da última alteração de situação da aposta.
     */
    @Column(name = "TS_ALTERACAO_SITUACAO")
    private LocalDateTime timestampAlteracaoSituacao;

    /**
     * Combo ao qual esta aposta individual pertence, se aplicável.
     */
    @ManyToOne
    @JoinColumn(name = "NU_COMBO", referencedColumnName = "NU_COMBO")
    private Combo combo;

    /**
     * Matrizes de prognóstico associadas a esta aposta individual.
     */
    @OneToMany(mappedBy = "apostaAssociada", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MatrizPrognostico> matrizesPrognostico;
}
