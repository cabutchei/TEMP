package br.gov.caixa.silce.dominio;

import java.util.Arrays;
import java.util.List;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

public enum Subcanal implements CaixaEnum<Integer> {

	SILCE(1, "Sistema Loterias Canal Eletrônico", "WEB"), 
	SIMLO_IOS(2, "Sistema Móvel de Loterias Plataforma IOS", "IOS"),
	SIMLO_ANDROID(3, "Sistema Móvel de Loterias Plataforma Android", "ANDROID"),
	CAIXA_TEM(4, "Caixa Tem", "CAIXA TEM");

	private final Integer codigo;
	private final String descricao;
	private final String nomeFormatado;

	private Subcanal(Integer codigo, String descricao, String nomeFormatado) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.nomeFormatado = nomeFormatado;
	}
	
	public static Subcanal[] getSubcanaisContemCombo() {
		return new Subcanal[] { SILCE, SIMLO_ANDROID, SIMLO_IOS };
	}

	public static List<Subcanal> getAllSubcanais() {
		return Arrays.asList(values());
	}

	public static Subcanal getByCodigo(Integer codigo) {
		return codigo != null ? EnumUtil.recupereByValue(values(), codigo) : SILCE;
	}

	public static Subcanal getByDescricao(String descricaoSubcanal) {
		for (Subcanal subcanal : Subcanal.values()) {
			if (subcanal.name().toLowerCase().contains(descricaoSubcanal.toLowerCase()) || subcanal.getDescricao().toLowerCase().contains(descricaoSubcanal.toLowerCase())) {
				return subcanal;
			}
		}
		return null;
	}

	@Override
	public Integer getValue() {
		return codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getNomeFormatado() {
		return nomeFormatado;
	}

}
