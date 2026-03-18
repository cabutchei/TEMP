package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

@Entity
@DiscriminatorValue("18")
public class ApostaDuplasena extends AbstractApostaNumerica {
	
	private static final long serialVersionUID = 1L;

	public ApostaDuplasena() {
		super(Modalidade.DUPLA_SENA);
	}

}
