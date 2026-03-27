package br.caixa.loterias.model.legado;

import lombok.Data;

import java.util.Date;

@Data
public class NotificacaoLegado {
    
    private Long id;

    private AgendamentoNotificacaoLegado agendamentoNotificacao;

    private ApostadorLegado apostador;

    private Date dataVisualizacao;

    private Date dataExclusao;
    
}
