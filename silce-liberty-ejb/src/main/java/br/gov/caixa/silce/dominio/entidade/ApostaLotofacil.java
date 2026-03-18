package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

@Entity
@DiscriminatorValue("8")
public class ApostaLotofacil extends AbstractApostaNumerica {
	
	private static final long serialVersionUID = 1L;

	public ApostaLotofacil() {
		super(Modalidade.LOTOFACIL);
	}

}
