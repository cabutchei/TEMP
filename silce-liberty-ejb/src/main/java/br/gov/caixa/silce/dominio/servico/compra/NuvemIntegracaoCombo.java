package br.gov.caixa.silce.dominio.servico.compra;

import br.gov.caixa.silce.dominio.entidade.TipoCombo;

/**
 * Representação de um combo como enviado pelo silce-compra-processamento.
 */
public class NuvemIntegracaoCombo extends DTONuvem implements IdLegado, IdNuvem {

    public Long idLegado;

    public Long idNuvem;

    public TipoCombo tipoCombo;

    NuvemIntegracaoCombo() {
    }

    public Long getIdNuvem() {
        return this.idNuvem;
    }

    public Long getIdLegado() {
        return this.idLegado;
    }
}
