package br.caixa.loterias.model.legado;

import java.io.Serializable;


public interface CaixaEnumLegado<X> extends Serializable {
    
    /**
     * Valor que identifica unicament o Enum, ex: PK da entidade
     * @return
     */
    public X getValue();
}
