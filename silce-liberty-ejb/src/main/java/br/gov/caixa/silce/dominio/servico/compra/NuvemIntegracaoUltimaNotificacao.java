package br.gov.caixa.silce.dominio.servico.compra;

import java.io.Serializable;
import java.util.Date;

/**
 * Representação da última notificação de um apostador como enviada pelo silce-compra-processamento.
 */
public class NuvemIntegracaoUltimaNotificacao implements Serializable {

    public Long id;

    public Long agendamentoNotificacao;

    public Long apostador;

    public Date dataVisualizacao;

    public Date dataExclusao;

    NuvemIntegracaoUltimaNotificacao() {
    }
}
