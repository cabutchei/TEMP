package br.caixa.loterias.model.reservacotabolao;

import br.caixa.loterias.model.bolao.Bolao;
import br.caixa.loterias.model.reservacotabolao.enums.SituacaoCotaBolaoEnumConverter;
import br.caixa.loterias.model.reservacotabolao.enums.SituacaoReservaCotaBolaoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * Representa uma cota de {@link Bolao}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_RESERVA_COTA_BOLAO", schema = "LCE")
public class ReservaCotaBolao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NU_RESERVA_COTA_BOLAO", nullable = false)
    private Long id;

    /**
     * Código da situação da cota.
     */
    @Column(name = "NU_SITUACAO")
    @Convert(converter = SituacaoCotaBolaoEnumConverter.class)
    private SituacaoReservaCotaBolaoEnum situacao;

    /**
     * Valor da cota em reais.
     */
    @Column(name = "VR_COTA", precision = 38, scale = 2)
    private BigDecimal valorCota;

    /**
     * Valor da tarifa de serviço associada à cota.
     */
    @Column(name = "VR_TARIFA_SERVICO", precision = 38, scale = 2)
    private BigDecimal valorTarifaServico;

    /**
    * Valor total da cota (valor da cota + tarifa de serviço).
    */
    @Column(name = "VR_TOTAL_COTA", precision = 38, scale = 2)
    private BigDecimal valorTotalCota;

    /**
    * Data e hora da última alteração de situação da cota.
    */
    @Column(name = "TS_ALTERACAO_SITUACAO")
    private LocalDateTime timestampAlteracaoSituacao;

    /**
    * Data e hora de expiração da reserva da cota.
    */
    @Column(name = "TS_EXPIRACAO_RESERVA")
    private LocalDateTime timestampExpiracaoReserva;

    /**
    * Data e hora da reserva da cota.
    */
    @Column(name = "TS_RESERVA")
    private LocalDateTime timestampReserva;

    /**
    * Bolão ao qual esta cota pertence.
    */
    @ManyToOne(optional = false)
    @JoinColumn(name = "NU_BOLAO", referencedColumnName = "NU_BOLAO")
    private Bolao bolao;

    /**
    * Identificador da cota no modulo slice-carrinho, se houver.
    */
    @Column(name = "NU_ID_RESERVA_CARRINHO")
    private Long idReservaCarrinho;

    /**
    * NSU da reserva.
    */
    @Column(name = "NU_NSU_RESERVA")
    private Long nsuReserva;

    /**
    * NSB da reserva.
    */
    @Column(name = "DE_NSB_COTA", length = 100)
    private String nsbCota;

    /**
    * NU da cota interno.
    */
    @Column(name = "NU_COTA_INTERNO")
    private Long nuCotaInterno;

    /**
    * Data e hora da finalização do processamento.
    */
    @Column(name = "TS_FINALIZACAO_PROCESSAMENTO")
    private LocalDateTime timestampFinalizacaoProcessamento;

    /**
    * Identificador (hash) da cota no marketplace.
    */
    @Column(name = "DE_ID_COTA_MKP", length = 255)
    private String idCotaMkp;

    /**
    * Data e hora de criação do registro.
    */
    @Column(name = "TS_CRIACAO")
    private LocalDateTime timestampCriacao;

    /**
    * Data e hora de modificação do registro.
    */
    @Column(name = "TS_MODIFICACAO")
    private LocalDateTime timestampModificacao;

    /**
    * Valor tarifa custeio.
    */
    @Column(name = "VR_TARIFA_CUSTEIO", precision = 38, scale = 2)
    private BigDecimal valorTarifaCusteio;

    /**
    * Valor cota custeio.
    */
    @Column(name = "VR_COTA_CUSTEIO", precision = 38, scale = 2)
    private BigDecimal valorCotaCusteio;

    /**
    * Gera o hash do bolão sem o número da cota.
    */
    public String getIdBolaoMkp() {
        String strComCota = new String(Base64.getDecoder().decode(idCotaMkp));
        String strSemCota = strComCota.substring(strComCota.indexOf(":") + 1);

        return Base64.getEncoder().encodeToString(strSemCota.getBytes());
    }

}
