package br.gov.caixa.silce.negocio.integracaocompra.mappers;


import br.gov.caixa.silce.negocio.integracaocompra.IntegracaoUtil;
import br.gov.caixa.silce.dominio.entidade.ApostaComprada;
import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoApostaComprada;
import br.gov.caixa.util.Decimal;

public class ApostaCompradaMapper {

    public ApostaComprada map(NuvemIntegracaoApostaComprada apostaCompradaNuvem) {
        ApostaComprada apostaComprada = new ApostaComprada();
        apostaComprada.setDataUltimaSituacao( IntegracaoUtil.extrairData(apostaCompradaNuvem.tsUltimaSituacao));
        apostaComprada.setConcursoInicial(apostaCompradaNuvem.concursoInicial);
        apostaComprada.setNsuTransacao(apostaCompradaNuvem.nsu);
        apostaComprada.setNsuTransacaoDevolucao(apostaCompradaNuvem.nsuDevolucao);
        apostaComprada.setNsb(null); // TODO puxar de comprada.nsb
        apostaComprada.setPremio(null); // TODO
        apostaComprada.setSubcanalPagamento( Subcanal.getByCodigo(apostaCompradaNuvem.subcanal));
        apostaComprada.setApostaTroca(apostaCompradaNuvem.bilheteTroca);
        apostaComprada.setValorComissao( new Decimal(apostaCompradaNuvem.valorComissao));
        apostaComprada.setIndicadorBloqueioDevolucao( apostaCompradaNuvem.bloqueioDevolucao);

        apostaComprada.setDataEfetivacaoSISPL(IntegracaoUtil.extrairData(apostaCompradaNuvem.tsEfetivacaoSispl));
        apostaComprada.setHoraEfetivacaoSISPL(IntegracaoUtil.extrairHora(apostaCompradaNuvem.tsEfetivacaoSispl));

        apostaComprada.setDataEnvioSISPL(IntegracaoUtil.extrairData(apostaCompradaNuvem.tsEnvioSispl));
        apostaComprada.setHoraEnvioSISPL(IntegracaoUtil.extrairHora(apostaCompradaNuvem.tsEnvioSispl));

        apostaComprada.setDataFinalizacaoProcessamento(IntegracaoUtil.extrairData(apostaCompradaNuvem.tsFinalizacaoProcessamento));
        apostaComprada.setHoraFinalizacaoProcessamento( IntegracaoUtil.extrairHora(apostaCompradaNuvem.tsFinalizacaoProcessamento));

        apostaComprada.setDataInicioApostaComprada(IntegracaoUtil.extrairData(apostaCompradaNuvem.tsInicioApostaComprada));
        apostaComprada.setHoraInicioApostaComprada(IntegracaoUtil.extrairHora(apostaCompradaNuvem.tsInicioApostaComprada));

        return apostaComprada;
    }
}
