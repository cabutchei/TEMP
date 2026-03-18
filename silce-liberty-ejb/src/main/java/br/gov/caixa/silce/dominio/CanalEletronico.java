package br.gov.caixa.silce.dominio;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

/**
 * @author c101482
 *
 */
public enum CanalEletronico implements CaixaEnum<Integer> {

	WEB(1, "WEB"),
	APP(2, "App");
	
	private final Integer codigo;
	private final String nome;

	private CanalEletronico(Integer codigo, String nome) {
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
	
	public static CanalEletronico getByNumero(Integer numero) {
		return EnumUtil.recupereByValue(values(), numero);
	}
	
}
