package br.gov.caixa.silce.dominio.servico.compra;

import java.io.Serializable;

/**
 * Representação de uma lotérica como enviada pelo silce-compra-processamento.
 */
public class NuvemIntegracaoLoterica implements Serializable {

    public Long id;

    public Long polo;

    public Long dv;

    NuvemIntegracaoLoterica() {

    }
}
