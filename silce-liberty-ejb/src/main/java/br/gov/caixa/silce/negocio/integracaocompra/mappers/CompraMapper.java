package br.gov.caixa.silce.negocio.integracaocompra.mappers;

import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCompra;
import br.gov.caixa.silce.negocio.integracaocompra.IntegracaoUtil;
import br.gov.caixa.util.Decimal;

public class CompraMapper extends Mapper<NuvemIntegracaoCompra, Compra>{

    public Compra map(NuvemIntegracaoCompra compraNuvem) {
        Compra compra = new Compra();
        return map(compraNuvem, compra);
    }

    public Compra map(NuvemIntegracaoCompra compraNuvem, Compra compra) {
        compra.setNsuTransacaoDebito(compraNuvem.nsuTransacaoDebito);
        compra.setNsuTransacaoEstorno(compraNuvem.nsuTransacaoEstorno);
        compra.setNsu(compraNuvem.nsu);
        compra.setCodigoMensageErro(compraNuvem.codigoMensagemErro);

        // Data efetivação do débito (somente data)
        compra.setDataEfetivacaoDebito( IntegracaoUtil.extrairData(compraNuvem.dataEfetivacaoDebito));

        // Data última alteração (somente data)
        compra.setDataUltimaAlteracao( IntegracaoUtil.extrairData(compraNuvem.dataUltimaAlteracao));

        // Data/hora início da compra
        compra.setDataInicioCompra( IntegracaoUtil.extrairData(compraNuvem.dataInicioCompra));

        compra.setHoraInicioCompra( IntegracaoUtil.extrairHora(compraNuvem.dataInicioCompra));

        // Data/hora finalização da compra
        compra.setDataFinalizacaoCompra(IntegracaoUtil.extrairData(compraNuvem.dataFinalizacaoCompra));
        compra.setHoraFinalizacaoCompra(IntegracaoUtil.extrairHora(compraNuvem.dataFinalizacaoCompra));

        // Data/hora expiração do pagamento
        compra.setDataExpiracaoPagamento(IntegracaoUtil.extrairData(compraNuvem.dataExpiracaoPagamento));
        compra.setHoraExpiracaoPagamento(IntegracaoUtil.extrairHora(compraNuvem.dataExpiracaoPagamento));

        compra.setValorTotal(compraNuvem.valorTotal != null ? new Decimal(compraNuvem.valorTotal) : null);
        compra.setValorComissao(compraNuvem.valorComissao != null ? new Decimal(compraNuvem.valorComissao) : null);

        compra.setNuMeioPagamento(compraNuvem.meioPagamento);

        compra.setSubcanalPagamento(Subcanal.getByCodigo(compraNuvem.subcanalPagamento));

        compra.setParticao((long) IntegracaoUtil.getParticao(compraNuvem.nsu)); // TODO conferir se essa geração de partição é aceitável
        compra.setMes((long) IntegracaoUtil.getMesAtual());

        return compra;

    }
}
