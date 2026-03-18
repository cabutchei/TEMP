package br.gov.caixa.silce.dominio.jogos;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;
import br.gov.caixa.util.StringUtil;

/**
 * Tipos de Concursos e seus códigos. 
 *
 */
public enum TipoConcurso implements CaixaEnum<Character> {

	NORMAL('1', "Normal"), ESPECIAL('2', "Especial");

	private final Character codigo;
	
	private final String descricao;

	private TipoConcurso(Character codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public Character getCodigo() {
		return codigo;
	}
	
	public static TipoConcurso getByDescricao(String descr) {
		String desc = StringUtil.removeAcentosAndToUpper(descr).trim().replaceAll("-| ", "");
		for (TipoConcurso tipoConcurso : TipoConcurso.values()) {
			if (StringUtil.removeAcentosAndToUpper(tipoConcurso.descricao).trim().replaceAll("-| ", "").equals(desc)) {
				return tipoConcurso;
			}
		}
		return null;
	}

	public static TipoConcurso getByCodigo(Character codigo) {
		return EnumUtil.recupereByValue(values(), codigo);
	}

	public boolean isNormal() {
		return getCodigo().equals(NORMAL.getCodigo());
	}
	
	public boolean isEspecial() {
		return getCodigo().equals(ESPECIAL.getCodigo());
	}

	@Override
	public Character getValue() {
		return getCodigo();
	}

	public String getDescricao() {
		return descricao;
	}
	
}
