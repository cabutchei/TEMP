package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("18")
public class ApostaCarrinhoFavoritoDuplasena extends AbstractApostaCarrinhoFavoritoNumerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
