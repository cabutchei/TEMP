package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

@Entity
@DiscriminatorValue("18")
public class HistoricoApostaDuplasena extends AbstractHistoricoApostaNumerica {
	
	private static final long serialVersionUID = 1L;

	public HistoricoApostaDuplasena() {
		super(Modalidade.DUPLA_SENA);
	}

}
