package br.caixa.loterias.model.legado;

public class ApostaLotomaniaLegado {

    private static final long serialVersionUID = 1L;

    private Boolean espelho = Boolean.FALSE;

    public ApostaLotomaniaLegado() {
        ModalidadeLegado.LOTOMANIA.getCodigo();
    }

    public Boolean getEspelho() {
        return isEspelho();
    }

    public Boolean isEspelho() {
        return espelho;
    }

    public void setEspelho(Boolean espelho) {
        this.espelho = espelho;
    }
}
