package br.caixa.loterias.mapper;

import br.caixa.loterias.model.aposta.ApostaIndividual;
import br.caixa.loterias.model.compra.Compra;
import br.caixa.loterias.model.legado.ApostaLegado;
import br.caixa.loterias.mapper.support.LegacyApostaSupportMapper;
import br.caixa.loterias.mapper.support.LegacyComboMapper;
import br.caixa.loterias.mapper.support.LegacyDateMapper;
import br.caixa.loterias.mapper.support.LegacyEnumMapper;
import jakarta.inject.Inject;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.JAKARTA_CDI,
        uses = {
                CompraMapper.class,
                LegacyApostaSupportMapper.class,
                LegacyComboMapper.class,
                LegacyDateMapper.class,
                LegacyEnumMapper.class
        },
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ApostaMapper {

    @Inject
    LegacyApostaSupportMapper legacyApostaSupportMapper;

    @Mapping(target = "idLegado", source = "aposta.idLegado")
    @Mapping(target = "idNuvem", source = "aposta.id")
    @Mapping(target = "compra", source = "compra")
    @Mapping(target = "modalidade", source = "aposta.modalidade", qualifiedByName = "toModalidade")
    @Mapping(target = "tipoConcurso", source = "aposta.tipoConcurso", qualifiedByName = "toTipoConcurso")
    @Mapping(target = "indicadorSurpresinha", source = "aposta.surpresinha", qualifiedByName = "toIndicadorSurpresinha")
    @Mapping(target = "valor", source = "aposta.valor")
    @Mapping(target = "comprada", expression = "java(toCompradaLegado(aposta, compra))")
    @Mapping(target = "concursoAlvo", source = "aposta.concursoAlvo")
    @Mapping(target = "dataInclusao", source = "aposta.timestampInclusao", qualifiedByName = "toDate")
    @Mapping(target = "subcanal", source = "compra", qualifiedByName = "toSubcanal")
    @Mapping(target = "apostaOriginalEspelho", source = "aposta", qualifiedByName = "toApostaOriginalEspelhoFromAposta")
    @Mapping(target = "comboAposta", source = "aposta.combo", qualifiedByName = "toComboApostaLegado")
    @Mapping(target = "nuCombo", source = "aposta.combo.id")
    @Mapping(target = "qtdTeimosinhas", source = "aposta.teimosinhaQtd")
    @Mapping(target = "prognostico", source = "aposta.matrizesPrognostico", qualifiedByName = "toPrognostico")
    public abstract ApostaLegado toLegado(ApostaIndividual aposta, Compra compra);

    public ApostaLegado toLegado(ApostaIndividual aposta) {
        return toLegado(aposta, null);
    }

    protected br.caixa.loterias.model.legado.ApostaCompradaLegado toCompradaLegado(
            ApostaIndividual aposta,
            Compra compra
    ) {
        return legacyApostaSupportMapper.toCompradaLegado(aposta, compra);
    }

    @AfterMapping
    protected void linkComprada(@MappingTarget ApostaLegado target) {
        if (target != null && target.getComprada() != null) {
            target.getComprada().setAposta(target);
        }
    }
}
