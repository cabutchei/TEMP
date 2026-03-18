package br.gov.caixa.silce.dominio.filtro;

import java.util.Arrays;
import java.util.List;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

public enum TipoFiltro implements CaixaEnum<Integer> {

//	AGENCIA(1, "Agência"), LOTERICO(2, "Lotérico"), MEIO_PAGAMENTO(3, "Meio de Pagamento");
	LOTERICO(1, "Lotérico"), MEIO_PAGAMENTO(2, "Meio de Pagamento");

	private final Integer codigo;
	private final String descricao;

	private TipoFiltro(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public static List<TipoFiltro> getTipos() {
		return Arrays.asList(TipoFiltro.values());
	}

	public static TipoFiltro getByCodigo(Integer codigo) {
		return EnumUtil.recupereByValue(values(), codigo);
	}

	public static TipoFiltro getByEnumName(String enumName) {
		return EnumUtil.valueOf(TipoFiltro.class, enumName);
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public Integer getValue() {
		return this.codigo;
	}

}
