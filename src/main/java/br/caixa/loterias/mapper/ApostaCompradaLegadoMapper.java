package br.caixa.loterias.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import br.caixa.loterias.backends.marketplace.MkpBolaoDetalharCotaOutDto;
import br.caixa.loterias.model.legado.ApostaCompradaLegado;
import br.caixa.loterias.model.legado.SituacaoApostaLegado;
import br.caixa.loterias.model.legado.SubCanalLegado;
import br.caixa.loterias.model.reservacotabolao.ReservaCotaBolao;

public class ApostaCompradaLegadoMapper {

    public ApostaCompradaLegado fromReservaCota(ReservaCotaBolao reserva, MkpBolaoDetalharCotaOutDto reservaMKP) {

        ApostaCompradaLegado comprada = new ApostaCompradaLegado();

        comprada.setNsu(reserva.getNsuReserva());
        comprada.setNsuDevolucao(null);
        comprada.setConcursoInicial(reservaMKP.getConcurso());

        if (reserva.getSituacao() != null) {
            comprada.setSituacao(SituacaoApostaLegado.SituacaoLegado.EM_PROCESSAMENTO); // TODO: mapear situação
        }

        comprada.setBilheteTroca(false); // Reservas de cota não têm bilhete de troca

        comprada.setTsUltimaSituacao(converterTimestamp(reserva.getTimestampAlteracaoSituacao()));
        comprada.setTsEfetivacaoSispl(null); // Reserva não tem efetivação SISPL direta
        comprada.setTsEnvioSispl(null); // Reserva não tem envio SISPL direto
        comprada.setTsFinalizacaoProcessamento(converterTimestamp(reserva.getTimestampFinalizacaoProcessamento()));
        comprada.setTsInicioApostaComprada(converterTimestamp(reserva.getTimestampReserva()));

        comprada.setValorComissao(BigDecimal.valueOf(0));
        comprada.setSubcanal(SubCanalLegado.SILCE);
        comprada.setBloqueioDevolucao(false);
        comprada.setNsb(null);
        comprada.setErroResgate(null);
        comprada.setErroAposta(null);

        return comprada;
    }

    private Date converterTimestamp(LocalDateTime timestamp) {
        if (timestamp == null) {
            return null;
        }

        return Date.from(timestamp.atZone(ZoneId.systemDefault()).toInstant());
    }
}