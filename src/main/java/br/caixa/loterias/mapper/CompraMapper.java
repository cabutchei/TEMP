package br.caixa.loterias.mapper;

import br.caixa.loterias.model.apostador.Apostador;
import br.caixa.loterias.model.compra.Compra;
import br.caixa.loterias.model.compra.enums.SituacaoCompraEnum;
import br.caixa.loterias.model.legado.ApostadorLegado;
import br.caixa.loterias.model.legado.CompraLegado;
import br.caixa.loterias.model.legado.SubCanalLegado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompraMapper {

    @Mapping(target = "idLegado", source = "idCompraLegado")
    @Mapping(target = "idNuvem", source = "id")
    @Mapping(target = "situacaoCompra", source = "situacao")
    @Mapping(target = "nsu", source = "nsuCompra")
    @Mapping(target = "dataInicioCompra", source = "timestampInicio")
    @Mapping(target = "dataFinalizacaoCompra", source = "timestampFinalizacao")
    @Mapping(target = "dataUltimaAlteracao", source = "timestampAlteracaoSituacao")
    @Mapping(target = "valorTotal", expression = "java(compra.getValorTotal())")
    @Mapping(target = "subcanalPagamento", source = "subcanal")
    @Mapping(target = "externalIdPagamentoDebito", source = "idExterno")
    CompraLegado toLegado(Compra compra);

    @Mapping(target = "idNuvem", source = "id")
    @Mapping(target = "cpf", source = "cpf")
    ApostadorLegado toLegado(Apostador apostador);

    default Long map(SituacaoCompraEnum situacao) {
        return situacao != null ? situacao.getValue() : null;
    }

    default SubCanalLegado map(Integer subcanal) {
        return subcanal != null ? SubCanalLegado.getByCodigo(subcanal) : null;
    }

    default Date map(LocalDateTime value) {
        if (value == null) {
            return null;
        }

        return Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }
}
