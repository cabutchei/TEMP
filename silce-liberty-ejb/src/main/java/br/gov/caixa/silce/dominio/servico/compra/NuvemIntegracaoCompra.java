package br.gov.caixa.silce.dominio.servico.compra;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representação de um combo como enviado pelo silce-compra-processamento.
 */
public class NuvemIntegracaoCompra extends DTONuvem implements IdLegado, IdNuvem {

    public Long idNuvem;

    public Long idLegado;

    public Long situacaoCompra;

    public NuvemIntegracaoApostador apostador;

    public NuvemIntegracaoLoterica loterica;

    public String nsuTransacaoDebito;

    public String nsuTransacaoEstorno;

    public Long nsu;

    public Date dataInicioCompra;

    public Date dataEfetivacaoDebito;

    public Date dataFinalizacaoCompra;

    public BigDecimal valorTotal;

    public BigDecimal valorComissao;

    public Date dataUltimaAlteracao;

    public Long meioPagamento;

    public String codigoMensagemErro;

    public Integer subcanalPagamento;

    public Date dataExpiracaoPagamento;

    public Boolean compraIntegrada;

    public String externalIdPagamentoDebito;

    public String externalIdPagamentoEstorno;

    NuvemIntegracaoCompra() {


    }

    public Long getIdNuvem() {
        return this.idNuvem;
    }

    public Long getIdLegado() {
        return this.idLegado;
    }
}
