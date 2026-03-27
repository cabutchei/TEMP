package br.caixa.loterias.mapper.support;

import br.caixa.loterias.model.aposta.ApostaIndividual;
import br.caixa.loterias.model.compra.Compra;
import br.caixa.loterias.model.itemcompravel.ItemCompravel;
import br.caixa.loterias.model.legado.ApostaCompradaLegado;
import br.caixa.loterias.model.legado.ApostaLotomaniaLegado;
import br.caixa.loterias.model.matrizprognostico.MatrizPrognostico;
import jakarta.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, uses = {LegacyDateMapper.class, LegacyEnumMapper.class})
public abstract class LegacyApostaSupportMapper {

    @Inject
    LegacyDateMapper legacyDateMapper;

    @Inject
    LegacyEnumMapper legacyEnumMapper;

    @Named("resolveApostaIdLegado")
    public Long resolveApostaIdLegado(ItemCompravel itemCompravel) {
        if (itemCompravel == null) {
            return null;
        }

        if (itemCompravel.getApostaLegado() != null) {
            return itemCompravel.getApostaLegado();
        }

        return itemCompravel.getAposta() != null ? itemCompravel.getAposta().getIdLegado() : null;
    }

    @Named("toCompradaLegadoFromItem")
    public ApostaCompradaLegado toCompradaLegado(ItemCompravel itemCompravel) {
        if (itemCompravel == null || itemCompravel.getAposta() == null) {
            return null;
        }

        return toCompradaLegado(itemCompravel.getAposta(), itemCompravel.getCompra());
    }

    public ApostaCompradaLegado toCompradaLegado(ApostaIndividual aposta, Compra compra) {
        if (aposta == null) {
            return null;
        }

        ApostaCompradaLegado comprada = new ApostaCompradaLegado();
        comprada.setNsu(aposta.getNsuAposta());
        comprada.setConcursoInicial(
                aposta.getTeimosinhaConcursoInicial() != null
                        ? aposta.getTeimosinhaConcursoInicial()
                        : aposta.getConcursoAlvo()
        );
        comprada.setBilheteTroca(aposta.getTeimosinhaBilheteTroca());
        comprada.setTsInicioApostaComprada(legacyDateMapper.toDate(aposta.getTimestampInclusao()));
        comprada.setTsEnvioSispl(legacyDateMapper.toDate(aposta.getTimestampEnvioSispl()));
        comprada.setTsEfetivacaoSispl(legacyDateMapper.toDate(aposta.getTimestampEfetivacaoSispl()));
        comprada.setTsFinalizacaoProcessamento(legacyDateMapper.toDate(aposta.getTimestampFinalizacaoProcessamento()));
        comprada.setTsUltimaSituacao(legacyDateMapper.toDate(aposta.getTimestampAlteracaoSituacao()));
        comprada.setSituacao(legacyEnumMapper.toSituacaoApostaLegado(aposta.getSituacao()));
        comprada.setSubcanal(legacyEnumMapper.toSubcanal(compra));
        return comprada;
    }

    @Named("toApostaOriginalEspelhoFromItem")
    public ApostaLotomaniaLegado toApostaOriginalEspelho(ItemCompravel itemCompravel) {
        if (itemCompravel == null) {
            return null;
        }

        return toApostaOriginalEspelho(itemCompravel.getAposta());
    }

    @Named("toApostaOriginalEspelhoFromAposta")
    public ApostaLotomaniaLegado toApostaOriginalEspelho(ApostaIndividual aposta) {
        if (aposta == null || !Boolean.TRUE.equals(aposta.getEspelho())) {
            return null;
        }

        ApostaLotomaniaLegado espelho = new ApostaLotomaniaLegado();
        espelho.setEspelho(Boolean.TRUE);
        return espelho;
    }

    @Named("toPrognostico")
    public String toPrognostico(List<MatrizPrognostico> matrizes) {
        if (matrizes == null || matrizes.isEmpty()) {
            return null;
        }

        return matrizes.stream()
                .map(MatrizPrognostico::getValor)
                .collect(Collectors.joining(" | "));
    }
}
