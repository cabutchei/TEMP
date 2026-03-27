package br.caixa.loterias.mapper;

import br.caixa.loterias.model.bolao.Bolao;
import br.caixa.loterias.model.compra.Compra;
import br.caixa.loterias.model.itemcompravel.ItemCompravel;
import br.caixa.loterias.model.reservacotabolao.ReservaCotaBolao;
import br.caixa.loterias.model.reservacotabolao.enums.SituacaoReservaCotaBolaoEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Essa classe é uma projeção que enriquece uma {@link ReservaCotaBolao} com o {@link ItemCompravel} associado.
 */
public final class ItemReservaCotaBolao {

    private final ItemCompravel item;

    public ItemReservaCotaBolao(ItemCompravel item) {
        this.item = Objects.requireNonNull(item, "item não pode ser null");

        if (!item.isReservaCota()) {
            throw new IllegalArgumentException("Item não é uma reserva");
        }
        if (item.getReserva() == null) {
            throw new IllegalArgumentException("Item não contém reserva");
        }
    }

    public ItemCompravel getItemCompravel() {
        return item;
    }

    public Long getItemCompravelId() {
        return item.getId();
    }

    public Compra getCompra() {
        return item.getCompra();
    }

    public Long getId() {
        return reserva().getId();
    }

    public SituacaoReservaCotaBolaoEnum getSituacao() {
        return reserva().getSituacao();
    }

    public BigDecimal getValorCota() {
        return reserva().getValorCota();
    }

    public BigDecimal getValorTarifaServico() {
        return reserva().getValorTarifaServico();
    }

    public BigDecimal getValorTotalCota() {
        return reserva().getValorTotalCota();
    }

    public LocalDateTime getTimestampAlteracaoSituacao() {
        return reserva().getTimestampAlteracaoSituacao();
    }

    public LocalDateTime getTimestampExpiracaoReserva() {
        return reserva().getTimestampExpiracaoReserva();
    }

    public LocalDateTime getTimestampReserva() {
        return reserva().getTimestampReserva();
    }

    public Bolao getBolao() {
        return reserva().getBolao();
    }

    public Long getIdReservaCarrinho() {
        return reserva().getIdReservaCarrinho();
    }

    public Long getNsuReserva() {
        return reserva().getNsuReserva();
    }

    public String getNsbCota() {
        return reserva().getNsbCota();
    }

    public Long getNuCotaInterno() {
        return reserva().getNuCotaInterno();
    }

    public LocalDateTime getTimestampFinalizacaoProcessamento() {
        return reserva().getTimestampFinalizacaoProcessamento();
    }

    public String getIdCotaMkp() {
        return reserva().getIdCotaMkp();
    }

    public LocalDateTime getTimestampCriacao() {
        return reserva().getTimestampCriacao();
    }

    public LocalDateTime getTimestampModificacao() {
        return reserva().getTimestampModificacao();
    }

    public BigDecimal getValorTarifaCusteio() {
        return reserva().getValorTarifaCusteio();
    }

    public BigDecimal getValorCotaCusteio() {
        return reserva().getValorCotaCusteio();
    }

    public String getIdBolaoMkp() {
        return reserva().getIdBolaoMkp();
    }

    private ReservaCotaBolao reserva() {
        return item.getReserva();
    }
}
