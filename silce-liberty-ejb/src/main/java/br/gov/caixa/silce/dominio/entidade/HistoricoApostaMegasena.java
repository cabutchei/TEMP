package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

@Entity
@DiscriminatorValue("2")
public class HistoricoApostaMegasena extends AbstractHistoricoApostaNumerica {

	private static final long serialVersionUID = 1L;
	
	public HistoricoApostaMegasena() {
		super(Modalidade.MEGA_SENA);
	}

	
}
