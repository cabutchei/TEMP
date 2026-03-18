package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

/**
 * @author f656372
 */

public class RespostaAutoavaliacaoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPergunta;
	
	private Boolean indicadorResposta;

	private String descricaoResposta;

	public RespostaAutoavaliacaoVO() {
		this(null, null, null);
	}

	public RespostaAutoavaliacaoVO(Long idPergunta, Boolean indicadorResposta, String descricaoResposta) {
		this.idPergunta = idPergunta;
		this.indicadorResposta = indicadorResposta;
		this.descricaoResposta = descricaoResposta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPergunta == null) ? 0 : idPergunta.hashCode());
		result = prime * result + ((indicadorResposta == null) ? 0 : indicadorResposta.hashCode());
		result = prime * result + ((descricaoResposta == null) ? 0 : descricaoResposta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof RespostaAutoavaliacaoVO)) {
			return false;
		}
		RespostaAutoavaliacaoVO other = (RespostaAutoavaliacaoVO) obj;
		if (idPergunta == null) {
			if (other.idPergunta != null) {
				return false;
			}
		} else if (!idPergunta.equals(other.idPergunta)) {
			return false;
		}
		if (indicadorResposta != other.indicadorResposta) {
			return false;
		}
		if (descricaoResposta != other.descricaoResposta) {
			return false;
		}

		return true;
	}

	public Long getIdPergunta() {
		return idPergunta;
	}

	public void setIdPergunta(Long idPergunta) {
		this.idPergunta = idPergunta;
	}

	public Boolean getIndicadorResposta() {
		return indicadorResposta;
	}

	public void setIndicadorResposta(Boolean indicadorResposta) {
		this.indicadorResposta = indicadorResposta;
	}

	public String getDescricaoResposta() {
		return descricaoResposta;
	}

	public void setDescricaoResposta(String descricaoResposta) {
		this.descricaoResposta = descricaoResposta;
	}

}
