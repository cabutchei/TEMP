package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue("11")
@NamedQueries({
		@NamedQuery(name = ApostaFavoritaDiaDeSorte.NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE_MES,
			query = "Select aposta From ApostaFavoritaDiaDeSorte aposta Where aposta.apostador.id=?1 and aposta.palpites = ?2"
				+ " and aposta.modalidade =?3 and aposta.mesDeSorte = ?4")
	})
public class ApostaFavoritaDiaDeSorte extends AbstractApostaFavoritaNumerica {

	public static final String NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE_MES = "ApostaFavoritaDiaDeSorte.NQ_RECUPERE_APOSTA_PROGNOSTICOS_MODALIDADE_MES";

	private static final long serialVersionUID = 1L;

	@Column(name = "MM_SORTE")
	private Integer mesDeSorte;

	public Integer getMesDeSorte() {
		return mesDeSorte;
	}

	public void setMesDeSorte(Integer mesDeSorte) {
		this.mesDeSorte = mesDeSorte;
	}

	@Override
	public ApostaFavoritaVO getVO() {
		ApostaFavoritaVO vo = super.getVO();
		vo.setMesDeSorte(mesDeSorte);
		return vo;
	}
}
