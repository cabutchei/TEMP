package br.gov.caixa.silce.negocio.integracaocompra.mappers;

import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolao;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoReserva;
import br.gov.caixa.silce.negocio.integracaocompra.IntegracaoUtil;
import br.gov.caixa.util.Decimal;

public class ReservaCotaBolaoMapper {

    public ReservaCotaBolao map(NuvemIntegracaoReserva reservaCotaBolaoNuvem) {
        ReservaCotaBolao reserva = new ReservaCotaBolao();
        reserva.setCodBolao(reservaCotaBolaoNuvem.codBolao);
        reserva.setQtdCotaTotal(reservaCotaBolaoNuvem.qtdCotaTotal);
        reserva.setDataRegistroBolao(IntegracaoUtil.extrairData(reservaCotaBolaoNuvem.dataRegistroBolao));
        reserva.setHoraRegistroBolao(IntegracaoUtil.extrairHora(reservaCotaBolaoNuvem.dataRegistroBolao));
        reserva.setNumeroTerminalLoterico(reservaCotaBolaoNuvem.numeroTerminalLoterico);


        reserva.setCodCota(reservaCotaBolaoNuvem.codCota);
        reserva.setDadosCotaBolao(reservaCotaBolaoNuvem.dadosCotaBolao);
        reserva.setNumeroCotaReservada(reservaCotaBolaoNuvem.numeroCotaReservada);

        reserva.setValorCotaReservada(new Decimal(reservaCotaBolaoNuvem.valorCotaReservada));
        reserva.setValorTarifaServico(new Decimal(reservaCotaBolaoNuvem.valorTarifaServico));
        reserva.setValorCotaCusteio(new Decimal(reservaCotaBolaoNuvem.valorCotaCusteio));
        reserva.setValorTarifaCusteio(new Decimal(reservaCotaBolaoNuvem.valorTarifaCusteio));

        reserva.setDataHoraReserva(IntegracaoUtil.extrairData(reservaCotaBolaoNuvem.dataHoraReserva));
        reserva.setDataExpiracaoReservaCotaBolao(IntegracaoUtil.extrairData(reservaCotaBolaoNuvem.dataExpiracaoReservaCotaBolao));
        reserva.setHoraExpiracaoReservaCotaBolao(IntegracaoUtil.extrairHora(reservaCotaBolaoNuvem.dataExpiracaoReservaCotaBolao));

        reserva.setNsu(reservaCotaBolaoNuvem.nsu);

        reserva.setDataUltimaSituacao(IntegracaoUtil.extrairData(reservaCotaBolaoNuvem.dataUltimaSituacao));

        return reserva;
    }
}