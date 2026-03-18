package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

@Entity
@DiscriminatorValue("3")
public class ApostaQuina extends AbstractApostaNumerica {

	private static final long serialVersionUID = 1L;
	
	public ApostaQuina() {
		super(Modalidade.QUINA);
	}

}
