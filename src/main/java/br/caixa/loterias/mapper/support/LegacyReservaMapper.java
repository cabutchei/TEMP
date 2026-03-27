package br.caixa.loterias.mapper.support;

import br.caixa.loterias.model.bolao.Bolao;
import br.caixa.loterias.model.legado.LotericaLegado;
import br.caixa.loterias.model.reservacotabolao.ReservaCotaBolao;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public abstract class LegacyReservaMapper {

    @Named("toCodBolao")
    public String toCodBolao(ReservaCotaBolao reserva) {
        if (reserva == null || reserva.getIdCotaMkp() == null) {
            return null;
        }

        return reserva.getIdBolaoMkp();
    }

    @Named("toLotericaLegado")
    public LotericaLegado toLotericaLegado(Bolao bolao) {
        if (bolao == null) {
            return null;
        }

        LotericaLegado loterica = new LotericaLegado();
        loterica.setId(bolao.getLotericaId());
        return loterica;
    }
}
