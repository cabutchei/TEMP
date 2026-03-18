package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

@Entity
@DiscriminatorValue("3")
public class HistoricoApostaQuina extends AbstractHistoricoApostaNumerica {

	private static final long serialVersionUID = 1L;
	
	public HistoricoApostaQuina() {
		super(Modalidade.QUINA);
	}

}
