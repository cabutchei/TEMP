package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

@Entity
@DiscriminatorValue("2")
public class ApostaMegasena extends AbstractApostaNumerica {

	private static final long serialVersionUID = 1L;
	
	public ApostaMegasena() {
		super(Modalidade.MEGA_SENA);
	}

	
}
