package br.caixa.loterias.model.legado;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class CompraLegado {

    private Long idLegado;

    private Long idNuvem;

    private Long situacaoCompra; // tipar como enum? SituacaoCompraLegado

    private ApostadorLegado apostador;

    private LotericaLegado loterica;

    private String nsuTransacaoDebito;

    private String nsuTransacaoEstorno;

    private Long nsu;

    private Date dataInicioCompra;

    private Date dataEfetivacaoDebito;

    private Date dataFinalizacaoCompra;

    private BigDecimal valorTotal;

    private BigDecimal valorComissao;

    private Date dataUltimaAlteracao;

    private Long meioPagamento;

    private String codigoMensagemErro;

    private SubCanalLegado subcanalPagamento;

    private Date dataExpiracaoPagamento;

    private Boolean compraIntegrada;

    private String externalIdPagamentoDebito;

    private String externalIdPagamentoEstorno;
}