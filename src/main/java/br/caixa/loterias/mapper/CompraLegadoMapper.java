package br.caixa.loterias.mapper;

import br.caixa.loterias.model.apostador.Apostador;
import br.caixa.loterias.model.compra.Compra;
// import br.caixa.loterias.model.compra.enums.SituacaoCompraEnum;
import br.caixa.loterias.model.legado.ApostadorLegado;
import br.caixa.loterias.model.legado.CompraLegado;
// import br.caixa.loterias.model.legado.SubCanalLegado;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class CompraLegadoMapper {

    public CompraLegado toCompraLegado(Compra compra) {
        if (compra == null) return null;

        CompraLegado compraLegado = new CompraLegado();

        compraLegado.setIdLegado(compra.getIdCompraLegado());
        compraLegado.setIdNuvem(compra.getId());
        compraLegado.setSituacaoCompra(compra.getSituacao().getValue());

        if (compra.getPagamento() != null) {
            compraLegado.setNsuTransacaoDebito(compra.getPagamento().getNsu());
            compraLegado.setExternalIdPagamentoDebito(compra.getPagamento().getId().toString());
            compraLegado.setMeioPagamento(compra.getPagamento().getMeioPagamento().getCodigo());

            if (compra.getPagamento().getEstorno() != null) {
                compraLegado.setNsuTransacaoEstorno(null);
                compraLegado.setExternalIdPagamentoEstorno(compra.getPagamento().getEstorno().getId().toString());
            }

            // TODO: buscar timestamp de efetivação do pagamento de compra.pagamento
        }
        compraLegado.setDataInicioCompra(map(compra.getTimestampInicio()));
        compraLegado.setDataFinalizacaoCompra(map(compra.getTimestampFinalizacao()));
        compraLegado.setDataUltimaAlteracao(map(compra.getTimestampAlteracaoSituacao()));
        compraLegado.setValorTotal(compra.getValorTotal());
        compraLegado.setValorComissao(null);
        compraLegado.setNsu(compra.getNsuCompra());
        compraLegado.setCompraIntegrada(true);
        compraLegado.setLoterica(null);
        compraLegado.setApostador(toLegado(compra.getApostador()));

        return compraLegado;
    }

    private ApostadorLegado toLegado(Apostador apostador) {
        if (apostador == null) {
            return null;
        }

        ApostadorLegado apostadorLegado = new ApostadorLegado();
        apostadorLegado.setIdNuvem(apostador.getId());
        apostadorLegado.setCpf(apostador.getCpf());
        return apostadorLegado;
    }

    // private Long map(SituacaoCompraEnum situacao) {
    //     return situacao != null ? situacao.getValue() : null;
    // }

    // private SubCanalLegado map(Integer subcanal) {
    //     return subcanal != null ? SubCanalLegado.getByCodigo(subcanal) : null;
    // }

    private Date map(LocalDateTime value) {
        if (value == null) return null;

        return Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }
}
