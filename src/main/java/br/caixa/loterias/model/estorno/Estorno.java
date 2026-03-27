package br.caixa.loterias.model.estorno;

import br.caixa.loterias.model.estorno.enums.SituacaoEstornoEnum;
import br.caixa.loterias.model.estorno.enums.SituacaoEstornoEnumConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ESTORNO", schema = "LCE")
public class Estorno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NU_ESTORNO", nullable = false)
    private Long id;

    /**
     * Código da situação do estorno.
     */
    @Column(name = "NU_SITUACAO", nullable = false)
    @Convert(converter = SituacaoEstornoEnumConverter.class)
    private SituacaoEstornoEnum situacao;

    /**
     * Data e hora do processamento do estorno.
     */
    @Column(name = "TS_PROCESSAMENTO")
    private LocalDateTime timestampProcessamento;

    /**
     * Data e hora da solicitação do estorno.
     */
    @Column(name = "TS_SOLICITACAO")
    private LocalDateTime timestampSolicitacao;

    /**
     * Código de retorno do processamento do estorno.
     */
    @Column(name = "DE_COD_RETORNO", length = 255)
    private String codRetorno;
}