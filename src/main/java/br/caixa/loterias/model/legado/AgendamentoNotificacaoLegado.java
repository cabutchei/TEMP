package br.caixa.loterias.model.legado;

import lombok.Data;

import java.util.Date;

@Data
public class AgendamentoNotificacaoLegado {

    private Long id;

    private ModeloNotificacaoLegado modelo;

    private Date dataInicioVigencia;

    private Date dataFimVigencia;

    private Date dataInibicao;

    private Date dataCriacao;

    private Date dataAlteracao;
}
