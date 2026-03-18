package br.gov.caixa.silce.dominio.servico.compra;

import java.math.BigDecimal;
import java.util.Date;

public class NuvemIntegracaoReserva implements IdNuvem {

    // transiente
    public NuvemIntegracaoReservaPK idLegado;

    public Long idNuvem;

    public String codBolao;

    public String codCota;

    public Date dataHoraReserva;

    public Date dataExpiracaoReservaCotaBolao;

    public Integer numeroCotaReservada;

    public Integer qtdCotaTotal;

    public BigDecimal valorCotaReservada;

    public BigDecimal valorTarifaServico;

    public Date dataRegistroBolao;

    public Integer numeroTerminalLoterico;

    public String dadosCotaBolao;

    public Long situacao;

    public Date dataUltimaSituacao;

    public BigDecimal valorCotaCusteio;

    public BigDecimal valorTarifaCusteio;

    public Long nsu;

    public NuvemIntegracaoLoterica loterica;

    public NuvemIntegracaoAposta aposta;

    public Long getIdNuvem() {
        return this.idNuvem;
    }
}
