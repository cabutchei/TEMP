package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.silce.dominio.entidade.MunicipioPK;

public class RetornoConsultaLocalidadePorCEP extends SaidaBroker  {

	private static final long serialVersionUID = 1L;

	private String nomeBairro;

	private Long idBairro;

	private MunicipioPK municipioId;

	private Long ufId;



	/**
	 * @return the municipioId
	 */
	public MunicipioPK getMunicipioId() {
		return municipioId;
	}

	/**
	 * @param municipioId
	 *            the municipioId to set
	 */
	public void setMunicipioId(MunicipioPK municipioId) {
		this.municipioId = municipioId;
	}

	/**
	 * @return the ufId
	 */
	public Long getUfId() {
		return ufId;
	}

	/**
	 * @param ufId
	 *            the ufId to set
	 */
	public void setUfId(Long ufId) {
		this.ufId = ufId;
	}

	/**
	 * @return
	 */
	public String getNomeBairro() {
		return nomeBairro;
	}

	/**
	 * @param nomeBairro
	 */
	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
	}

	/**
	 * @return
	 */
	public Long getIdBairro() {
		return idBairro;
	}

	/**
	 * @param idBairro
	 */
	public void setIdBairro(Long idBairro) {
		this.idBairro = idBairro;
	}
	
}
