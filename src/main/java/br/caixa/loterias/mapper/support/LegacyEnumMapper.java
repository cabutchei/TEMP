package br.caixa.loterias.mapper.support;

import br.caixa.loterias.model.compra.Compra;
import br.caixa.loterias.model.enums.situacaoapostaindividual.SituacaoApostaIndividualEnum;
import br.caixa.loterias.model.legado.IndicadorSurpresinhaLegado;
import br.caixa.loterias.model.legado.ModalidadeLegado;
import br.caixa.loterias.model.legado.SituacaoApostaLegado;
import br.caixa.loterias.model.legado.SubCanalLegado;
import br.caixa.loterias.model.legado.TipoConcursoLegado;
import br.caixa.loterias.model.reservacotabolao.enums.SituacaoReservaCotaBolaoEnum;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public abstract class LegacyEnumMapper {

    @Named("toModalidade")
    public ModalidadeLegado toModalidade(Long modalidade) {
        return modalidade != null ? ModalidadeLegado.getByCodigo(modalidade.intValue()) : null;
    }

    @Named("toTipoConcurso")
    public TipoConcursoLegado toTipoConcurso(Long tipoConcurso) {
        if (tipoConcurso == null) {
            return null;
        }

        return Long.valueOf(2L).equals(tipoConcurso)
                ? TipoConcursoLegado.ESPECIAL
                : TipoConcursoLegado.NORMAL;
    }

    @Named("toIndicadorSurpresinha")
    public IndicadorSurpresinhaLegado toIndicadorSurpresinha(boolean surpresinha) {
        return surpresinha
                ? IndicadorSurpresinhaLegado.SURPRESINHA
                : IndicadorSurpresinhaLegado.NAO_SURPRESINHA;
    }

    @Named("toSubcanal")
    public SubCanalLegado toSubcanal(Compra compra) {
        if (compra == null || compra.getSubcanal() == null) {
            return null;
        }

        return SubCanalLegado.getByCodigo(compra.getSubcanal());
    }

    @Named("toSituacaoApostaLegado")
    public SituacaoApostaLegado.SituacaoLegado toSituacaoApostaLegado(
            SituacaoApostaIndividualEnum situacao
    ) {
        if (situacao == null) {
            return null;
        }

        return switch (situacao) {
            case NAO_REGISTRADA -> SituacaoApostaLegado.SituacaoLegado.PENDENTE;
            case EM_PROCESSAMENTO, EM_PROCESSAMENTO_ENVIADA_SISPL, PAGAMENTO_EM_PROCESSAMENTO ->
                    SituacaoApostaLegado.SituacaoLegado.PROCESSANDO;
            case NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO -> SituacaoApostaLegado.SituacaoLegado.CANCELADA;
        };
    }

    @Named("toSituacaoReserva")
    public Long toSituacaoReserva(SituacaoReservaCotaBolaoEnum situacao) {
        return situacao != null ? situacao.getValue() : null;
    }

    @Named("toInteger")
    public Integer toInteger(Long value) {
        return value != null ? value.intValue() : null;
    }
}
