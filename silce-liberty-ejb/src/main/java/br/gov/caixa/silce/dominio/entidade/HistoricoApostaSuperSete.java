package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

@Entity
@DiscriminatorValue("7")
public class HistoricoApostaSuperSete extends AbstractHistoricoApostaNumerica {

	private static final long serialVersionUID = 1L;
	
	public HistoricoApostaSuperSete() {
		super(Modalidade.SUPER_7);
	}

	
}
