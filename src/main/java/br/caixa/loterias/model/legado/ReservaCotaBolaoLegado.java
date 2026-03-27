package br.caixa.loterias.model.legado;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class ReservaCotaBolaoLegado {

    private String codBolao;

    private String codCota;

    private LotericaLegado loterica;

    private Date dataHoraReserva;

    private Date dataExpiracaoReservaCotaBolao;

    private Integer numeroCotaReservada;

    private Integer qtdCotaTotal;

    private BigDecimal valorCotaReservada;

    private BigDecimal valorTarifaServico;

    private Date dataRegistroBolao;

    private Integer numeroTerminalLoterico;

    private String dadosCotaBolao;

    private Long situacao;

    private Date dataUltimaSituacao;

    private BigDecimal valorCotaCusteio;

    private BigDecimal valorTarifaCusteio;

    private Long nsu;

    @ToString.Exclude
    private ApostaLegado aposta;

}
