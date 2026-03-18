package br.gov.caixa.silce.dominio.servico.compra;

public class NuvemIntegracaoReservaPK {

    public Long id;

    public Integer mes;

    public Integer particao;

    public NuvemIntegracaoReservaPK() {}

    public NuvemIntegracaoReservaPK(Long id, Integer mes, Integer particao) {
        this.id = id;
        this.mes = mes;
        this.particao = particao;
    }
}
