package br.gov.caixa.silce.dominio.jogos;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

/**
 * Tipos de Concursos e seus códigos. 
 *
 */
public enum ModoInclusaoApostaCarrinhoFavorito implements CaixaEnum<Integer> {

	NORMAL(1, "Normal"), ESPECIAL(2, "Especial"), AMBOS(0, "Ambos");

	private final Integer codigo;
	
	private final String descricao;

	private ModoInclusaoApostaCarrinhoFavorito(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public static ModoInclusaoApostaCarrinhoFavorito getByCodigo(Integer codigo) {
		return EnumUtil.recupereByValue(values(), codigo);
	}

	@Override
	public Integer getValue() {
		return getCodigo();
	}

	public String getDescricao() {
		return descricao;
	}
	
}
