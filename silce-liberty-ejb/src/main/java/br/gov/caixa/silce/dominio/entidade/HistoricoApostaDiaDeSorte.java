package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.gov.caixa.silce.dominio.jogos.Modalidade;

@Entity
@DiscriminatorValue("11")
public class HistoricoApostaDiaDeSorte extends AbstractHistoricoApostaNumerica {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "MM_SORTE")
	private Integer mesDeSorte;

	public HistoricoApostaDiaDeSorte() {
		super(Modalidade.DIA_DE_SORTE);
	}

	public Integer getMesDeSorte() {
		return mesDeSorte;
	}

	public void setMesDeSorte(Integer mesDeSorte) {
		this.mesDeSorte = mesDeSorte;
	}
	
}
