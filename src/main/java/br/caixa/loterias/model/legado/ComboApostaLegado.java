package br.caixa.loterias.model.legado;

import lombok.Data;

@Data
public class ComboApostaLegado {

    public static final String NQ_SELECT_BY_ID = "ComboAposta.NQ_SELECT_BY_ID";
    public static final String NQ_DELETE_BY_APOSTA_COMPRA = "ComboAposta.NQ_DELETE_BY_APOSTA_COMPRA";
    public static final String NQ_DELETE_BY_LIST_ID = "ComboAposta.NQ_DELETE_BY_LIST_ID";

    private static final long serialVersionUID = 1L;

    private Long id;
    private TipoComboLegado tipoCombo;
    private Long mes;
    private Long particao;
    private String dataInclusao;

    public ComboApostaLegado(TipoComboLegado tipoCombo) {
        this.tipoCombo = tipoCombo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((mes == null) ? 0 : mes.hashCode());
        result = prime * result + ((particao == null) ? 0 : particao.hashCode());
        result = prime * result + ((tipoCombo == null) ? 0 : tipoCombo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ComboApostaLegado other = (ComboApostaLegado) obj;

        if (dataInclusao == null) {
            if (other.dataInclusao != null) {
                return false;
            }
        } else if (!dataInclusao.equals(other.dataInclusao)) {
            return false;
        }

        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }

        if (mes == null) {
            if (other.mes != null) {
                return false;
            }
        } else if (!mes.equals(other.mes)) {
            return false;
        }

        if (particao == null) {
            if (other.particao != null) {
                return false;
            }
        } else if (!particao.equals(other.particao)) {
            return false;
        }

        if (tipoCombo != other.tipoCombo) {
            return false;
        }

        return true;
    }

    public String getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(String dataInclusao) {
        this.dataInclusao = dataInclusao;
    }
}