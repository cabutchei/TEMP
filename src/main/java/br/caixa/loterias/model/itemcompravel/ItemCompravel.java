package br.caixa.loterias.model.itemcompravel;

import br.caixa.loterias.model.aposta.ApostaIndividual;
import br.caixa.loterias.model.compra.Compra;
import br.caixa.loterias.model.enums.situacaoapostainidividual.SituacaoApostaIndividualEnum;
import br.caixa.loterias.model.reservacotabolao.ReservaCotaBolao;
import br.caixa.loterias.model.reservacotabolao.enums.SituacaoReservaCotaBolaoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "_ITEM_COMPRAVEL", schema = "LCE")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class ItemCompravel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NU_ITEM_COMPRAVEL", nullable = false)
    private Long id;

    /**
     * Compra à qual este item está associado.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "NU_COMPRA", referencedColumnName = "NU_COMPRA")
    private Compra compra;

    /**
     * Código do tipo do item comprável. Indica se trata-se de uma
     * {@link ApostaIndividual} ou uma {@link ReservaCotaBolao}.
     */
    @Column(name = "NU_TIPO", nullable = false)
    @Convert(converter = TipoItemCompravelEnumConverter.class)
    private TipoItemCompravelEnum tipo;

    /**
     * Aposta individual associada a este item, se aplicável.
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "NU_APOSTA", referencedColumnName = "NU_APOSTA", nullable = true)
    private ApostaIndividual aposta;

    /**
     * Reserva de Cota de bolão associada a este item, se aplicável.
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "NU_RESERVA_COTA_BOLAO", referencedColumnName = "NU_RESERVA_COTA_BOLAO", nullable = true)
    private ReservaCotaBolao reserva;

    /**
     * Número da aposta no legado.
     */
    @Column(name = "NU_APOSTA_LEGADO")
    private Long apostaLegado; // o nome desse atributo é misleading: sugere que um objeto ApostaLegado seria retornado

    public boolean isAposta() {
        return this.tipo == TipoItemCompravelEnum.APOSTA;
    }

    public boolean isReservaCota() {
        return this.tipo == TipoItemCompravelEnum.RESERVA_COTA;
    }

    public BigDecimal getValorItem() {
        if (this.aposta != null) {
            return this.aposta.getValor();
        }

        return this.reserva.getValorTotalCota();
    }

    public Long getSituacao() {
        if (this.aposta != null) {
            return this.aposta.getSituacao().getValue();
        }

        return this.reserva.getSituacao().getValue();
    }

    public boolean isItemNaoEfetivado() {
        if (this.aposta != null) {
            return this.aposta.getSituacao().equals(SituacaoApostaIndividualEnum.NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO);
        }

        return this.reserva.getSituacao().equals(SituacaoReservaCotaBolaoEnum.CANCELADA);
    }
}
