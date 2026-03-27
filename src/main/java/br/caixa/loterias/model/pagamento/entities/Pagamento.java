package br.caixa.loterias.model.pagamento.entities;

import java.time.LocalDateTime;

import br.caixa.loterias.model.compra.Compra;
import br.caixa.loterias.model.estorno.Estorno;
import br.caixa.loterias.model.pagamento.enums.SituacaoPagamentoEnum;
import br.caixa.loterias.model.pagamento.enums.SituacaoPagamentoEnumConverter;
import br.caixa.loterias.model.pagamento.enums.MeioPagamentoEnum;
import br.caixa.loterias.model.pagamento.enums.MeioPagamentoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "_PAGAMENTO", schema = "LCE")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NU_PAGAMENTO", nullable = false)
    private Long id;

    /**
    * Código do meio de pagamento utilizado.
    */
    @Column(name = "NU_MEIO_PAGAMENTO", nullable = false)
    @Convert(converter = MeioPagamentoEnumConverter.class)
    private MeioPagamentoEnum meioPagamento;

    /**
    * Código da situação do pagamento.
    */
    @Column(name = "NU_SITUACAO")
    @Convert(converter = SituacaoPagamentoEnumConverter.class)
    private SituacaoPagamentoEnum situacao;

    /**
    * Data e hora do processamento do pagamento.
    */
    @Column(name = "TS_PROCESSAMENTO")
    private LocalDateTime timestampProcessamento;

    /**
    * Data e hora da solicitação do pagamento.
    */
    @Column(name = "TS_SOLICITACAO")
    private LocalDateTime timestampSolicitacao;

    /**
    * Data e hora da expiração do pagamento.
    */
    @Column(name = "TS_EXPIRACAO_PAGAMENTO")
    private LocalDateTime timestampExpiracaoPagamento;

    /**
    * Código de retorno do processamento do pagamento.
    */
    @Column(name = "DE_COD_RETORNO", length = 255)
    private String codRetorno;

    /**
    * Estorno associado a este pagamento, se houver.
    */
    @ManyToOne
    @JoinColumn(name = "NU_ESTORNO", referencedColumnName = "NU_ESTORNO")
    private Estorno estorno;

    /**
    * Compra associada a esse pagamento
    */
    @OneToOne
    @JoinColumn(name = "NU_PAGAMENTO", referencedColumnName = "NU_PAGAMENTO")
    private Compra compra;

    /**
    * Número NSU do pagamento.
    */
    @Column(name = "NU_NSU")
    private String nsu;
}