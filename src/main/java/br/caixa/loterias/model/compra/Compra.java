package br.caixa.loterias.model.compra;

import br.caixa.loterias.model.apostador.Apostador;
import br.caixa.loterias.model.compra.enums.SituacaoCompraEnumConverter;
import br.caixa.loterias.model.itemcompravel.ItemCompravel;
import br.caixa.loterias.model.pagamento.entities.Pagamento;
import jakarta.persistence.*;
import br.caixa.loterias.model.compra.enums.SituacaoCompraEnum;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "_COMPRA", schema = "LCE")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NU_COMPRA", nullable = false)
    private Long id;

    /**
     * Apostador que realizou a compra.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "NU_APOSTADOR", referencedColumnName = "NU_APOSTADOR")
    private Apostador apostador;

    /**
     * Código da situação atual da compra.
     */
    @Column(name = "NU_SITUACAO", nullable = false)
    @Convert(converter = SituacaoCompraEnumConverter.class)
    private SituacaoCompraEnum situacao;

    /**
     * Data e hora da última alteração de situação da compra.
     */
    @Column(name = "TS_ALTERACAO_SITUACAO")
    private LocalDateTime timestampAlteracaoSituacao;

    /**
     * Data e hora da finalização da compra.
     */
    @Column(name = "TS_FINALIZACAO")
    private LocalDateTime timestampFinalizacao;

    /**
     * Subcanal da compra.
     */
    @Column(name = "NU_SUBCANAL")
    private Integer subcanal;

    /**
     * Data e hora do início da compra.
     */
    @Column(name = "TS_INICIO", nullable = false)
    private LocalDateTime timestampInicio;

    /**
     * Identificador da compra no sistema legado, se houver.
     */
    @Column(name = "NU_COMPRA_LEGADO")
    private Long idCompraLegado;

    /**
     * Número Sequencial Único da compra no sistema.
     */
    @Column(name = "NSU_COMPRA")
    private Long nsuCompra;

    /**
     * Pagamento associado a esta compra.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "NU_PAGAMENTO", referencedColumnName = "NU_PAGAMENTO")
    private Pagamento pagamento;

    /**
     * Identificador externo da compra utilizado para integração com o sistema de pagamento.
     * Este campo é preenchido quando a compra é criada e utilizado para rastrear a transação de pagamento.
     *
     * <p>
     * trocar prefixo para DE?
     */
    @Column(name = "NU_ID_EXTERNO", length = 100)
    private String idExterno;

    @OneToMany
    @JoinColumn(name = "NU_COMPRA", referencedColumnName = "NU_COMPRA")
    private List<ItemCompravel> itensCompraveis = new ArrayList<>();

    public BigDecimal getValorTotal() {
        return this.getItensCompraveis()
            .stream()
            .map(ItemCompravel::getValorItem)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
