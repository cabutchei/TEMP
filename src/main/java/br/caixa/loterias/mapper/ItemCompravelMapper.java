package br.caixa.loterias.mapper;

import br.caixa.loterias.model.legado.ApostaLegado;
import br.caixa.loterias.model.legado.ReservaCotaBolaoLegado;
import br.caixa.loterias.model.itemcompravel.ItemCompravel;
import br.caixa.loterias.mapper.support.LegacyApostaSupportMapper;
import br.caixa.loterias.mapper.support.LegacyComboMapper;
import br.caixa.loterias.mapper.support.LegacyDateMapper;
import br.caixa.loterias.mapper.support.LegacyEnumMapper;
import br.caixa.loterias.mapper.support.LegacyReservaMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.CDI,
        uses = {
                CompraMapper.class,
                LegacyApostaSupportMapper.class,
                LegacyComboMapper.class,
                LegacyDateMapper.class,
                LegacyEnumMapper.class,
                LegacyReservaMapper.class
        },
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ItemCompravelMapper {

    public Object toLegado(ItemCompravel itemCompravel) {
        if (itemCompravel == null) {
            return null;
        }

        if (itemCompravel.isAposta()) {
            return toApostaLegado(itemCompravel);
        }

        if (itemCompravel.isReservaCota()) {
            return toReservaCotaBolaoLegado(itemCompravel);
        }

        return null;
    }

    @Mapping(target = "idLegado", source = ".", qualifiedByName = "resolveApostaIdLegado")
    @Mapping(target = "idNuvem", source = "aposta.id")
    @Mapping(target = "compra", source = "compra")
    @Mapping(target = "modalidade", source = "aposta.modalidade", qualifiedByName = "toModalidade")
    @Mapping(target = "tipoConcurso", source = "aposta.tipoConcurso", qualifiedByName = "toTipoConcurso")
    @Mapping(target = "indicadorSurpresinha", source = "aposta.surpresinha", qualifiedByName = "toIndicadorSurpresinha")
    @Mapping(target = "valor", source = "aposta.valor")
    @Mapping(target = "comprada", source = ".", qualifiedByName = "toCompradaLegadoFromItem")
    @Mapping(target = "concursoAlvo", source = "aposta.concursoAlvo")
    @Mapping(target = "dataInclusao", source = "aposta.timestampInclusao", qualifiedByName = "toDate")
    @Mapping(target = "subcanal", source = "compra", qualifiedByName = "toSubcanal")
    @Mapping(target = "apostaOriginalEspelho", source = "aposta", qualifiedByName = "toApostaOriginalEspelhoFromAposta")
    @Mapping(target = "comboAposta", source = "aposta.combo", qualifiedByName = "toComboApostaLegado")
    @Mapping(target = "nuCombo", source = "aposta.combo.id")
    @Mapping(target = "qtdTeimosinhas", source = "aposta.teimosinhaQtd")
    @Mapping(target = "prognostico", source = "aposta.matrizesPrognostico", qualifiedByName = "toPrognostico")
    public abstract ApostaLegado toApostaLegado(ItemCompravel itemCompravel);

    @Mapping(target = "codBolao", source = "reserva", qualifiedByName = "toCodBolao")
    @Mapping(target = "codCota", source = "reserva.idCotaMkp")
    @Mapping(target = "loterica", source = "reserva.bolao", qualifiedByName = "toLotericaLegado")
    @Mapping(target = "dataHoraReserva", source = "reserva.timestampReserva", qualifiedByName = "toDate")
    @Mapping(target = "dataExpiracaoReservaCotaBolao", source = "reserva.timestampExpiracaoReserva", qualifiedByName = "toDate")
    @Mapping(target = "numeroCotaReservada", source = "reserva.nuCotaInterno", qualifiedByName = "toInteger")
    @Mapping(target = "qtdCotaTotal", source = "reserva.bolao.qtdCotas")
    @Mapping(target = "valorCotaReservada", source = "reserva.valorCota")
    @Mapping(target = "valorTarifaServico", source = "reserva.valorTarifaServico")
    @Mapping(target = "dataRegistroBolao", source = "reserva.bolao.timestampRegistro", qualifiedByName = "toDate")
    @Mapping(target = "numeroTerminalLoterico", source = "reserva.bolao.terminalLoterico")
    @Mapping(target = "dadosCotaBolao", source = "reserva.idCotaMkp")
    @Mapping(target = "situacao", source = "reserva.situacao", qualifiedByName = "toSituacaoReserva")
    @Mapping(target = "dataUltimaSituacao", source = "reserva.timestampAlteracaoSituacao", qualifiedByName = "toDate")
    @Mapping(target = "valorCotaCusteio", source = "reserva.valorCotaCusteio")
    @Mapping(target = "valorTarifaCusteio", source = "reserva.valorTarifaCusteio")
    @Mapping(target = "nsu", source = "reserva.nsuReserva")
    @Mapping(target = "aposta", ignore = true)
    public abstract ReservaCotaBolaoLegado toReservaCotaBolaoLegado(ItemCompravel itemCompravel);

    @AfterMapping
    protected void linkComprada(@MappingTarget ApostaLegado target) {
        if (target != null && target.getComprada() != null) {
            target.getComprada().setAposta(target);
        }
    }
}
