package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.util.Date;

import br.gov.caixa.silce.dominio.exception.NsbException;

public class NsbBilhete implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nsb;
	private String nsbFormatado;
	private int nsu;
	private Date data;
	private int cds;
	private String nsbBarras;
	private String cvb2;

	private int cota;

	private String cpf = "";

	public static final int TAMANHO_NSB_SEM_DV = 22;
	public static final int TAMANHO_NSB_COM_DV = TAMANHO_NSB_SEM_DV + 1; // 23
	public static final int TAMANHO_NSB_COM_DV_CODIGO_BARRAS = TAMANHO_NSB_COM_DV + 29; // 52
	public static final int TAMANHO_NSB_COM_DV_CODIGO_BARRAS_CVB2 = TAMANHO_NSB_COM_DV_CODIGO_BARRAS + 19; // 71
	public static final int TAMANHO_NSB_GTECH = 16;

	/**
     * Um nsb válido deve conter somente caracteres hexadecimais, ou seja, [0-9A-F],
     * e deve conter no máximo 23 ou 16 caracteres. <br>
     * 
     * @param nsb
     *        Numero de seria do bilhete com o digito verificador
     * @throws NsbException
     */

	public NsbBilhete() {
        //necessario para um framework rest
    }

	/**
	 * @return String cds
	 */
	public int getCds() {
		return cds;
	}

	/**
	 * @return Date Data a partir de um CDS
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @return String NSB em formato hexadecimal, sem formata��o
	 */
	public String getNsb() {
		return nsb;
	}

	/**
	 * @return String NSB em formato num�rico para gera��o do c�digo de barras
	 */
	public String getNsbBarras() {
		return nsbBarras;
	}

	/**
	 * @return String NSB em formato hexadecimal, com formata��o
	 */
	public String getNsbFormatado() {
		return nsbFormatado;
	}

	/**
	 * @param int
	 *            valor do CDS
	 */
	public void setCds(int i) {
		cds = i;
	}

	/**
	 * @param date
	 *            Data de gera��o
	 */
	public void setData(Date date) {
		data = date;
	}

	/**
	 * @param String
	 *            O pr�prio NSB hexadecimal
	 */
	public void setNsb(String string) {
		nsb = string;
	}

	/**
	 * @param String
	 *            O pr�prio NSB em formato num�rico para o c�digo de barras
	 */
	public void setNsbBarras(String string) {
		nsbBarras = string;
	}

	/**
	 * @param String
	 *            O pr�prio NSB hexadecimal formatado
	 */
	public void setNsbFormatado(String string) {
		nsbFormatado = string;
	}

	/**
	 * @return int NSU gerador do NSB
	 */
	public int getNsu() {
		return nsu;
	}

	/**
	 * @param int
	 *            NSU gerador do NSB
	 */
	public void setNsu(int i) {
		nsu = i;
	}

	public void setCvb2(String cvb2) {
		this.cvb2 = cvb2;
	}

	public String getCvb2() {
		return cvb2;
	}

	public int getCota() {
		return cota;
	}

	public void setCota(int cota) {
		this.cota = cota;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
