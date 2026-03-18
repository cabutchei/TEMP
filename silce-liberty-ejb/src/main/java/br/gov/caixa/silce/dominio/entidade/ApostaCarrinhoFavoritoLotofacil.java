package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("8")
public class ApostaCarrinhoFavoritoLotofacil extends AbstractApostaCarrinhoFavoritoNumerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
