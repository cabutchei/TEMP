package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

@Entity
@DiscriminatorValue("8")
public class HistoricoApostaLotofacil extends AbstractHistoricoApostaNumerica {
	
	private static final long serialVersionUID = 1L;

	public HistoricoApostaLotofacil() {
		super(Modalidade.LOTOFACIL);
	}

}
