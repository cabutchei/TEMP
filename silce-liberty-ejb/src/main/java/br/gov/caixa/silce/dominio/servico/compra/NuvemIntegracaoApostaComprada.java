package br.gov.caixa.silce.dominio.servico.compra;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representação de uma aposta comprada como enviada pelo silce-compra-processamento.
 */
public class NuvemIntegracaoApostaComprada extends DTONuvem {

    public Long nsu;

    public String nsuDevolucao;

    public Integer concursoInicial;

    public Long situacao;

    public Boolean bilheteTroca;

    public Date tsInicioApostaComprada;

    public Date tsEnvioSispl;

    public Date tsEfetivacaoSispl;

    public Date tsFinalizacaoProcessamento;

    public Date tsUltimaSituacao;

    public BigDecimal valorComissao;

    public NuvemIntegracaoAposta aposta;

    public Integer subcanal;

    public Boolean bloqueioDevolucao;

    public String nsb;

    public String erroResgate;

    public String erroAposta;

    public NuvemIntegracaoApostaComprada() {
    }
}
