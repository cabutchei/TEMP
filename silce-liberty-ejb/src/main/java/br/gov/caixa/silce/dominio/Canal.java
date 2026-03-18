package br.gov.caixa.silce.dominio;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

/**
 * @author c101482
 *
 */
public enum Canal implements CaixaEnum<Integer> {

	//numero dos canais utilizados pelo SISPL
	SILCE(9880, "Canais Eletrônicos"),
	SISPL(9660, "Unidade Lotérica"),
	IBC(9602, "Internet Banking"),
	SIGEL(9770, "Agência");
	
	private final Integer codigo;
	private final String nome;

	private Canal(Integer codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public Integer getValue() {
		return getCodigo();
	}
	
	public String getNome() {
		return nome;
	}
	
	public static Canal getByNumero(Integer numero) {
		return EnumUtil.recupereByValue(values(), numero);
	}
	
	public static Canal[] getCanaisResgate(){
		return new Canal[] { SISPL, SIGEL };
	}

}
