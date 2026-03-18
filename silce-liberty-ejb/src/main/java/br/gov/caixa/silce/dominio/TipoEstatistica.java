package br.gov.caixa.silce.dominio;

import java.util.Arrays;
import java.util.List;

import br.gov.caixa.util.CaixaEnum;

public enum TipoEstatistica implements CaixaEnum<Integer> {

	// Estatística que exibe o número de usuários que possuem uma compra na situação FINALIZADA feita no período
	USUARIOS_COMPRARAM(1, "Usuários que compraram", false,
		"Quantidade de usuários que fizeram compras na situação finalizada."),
	// Estatística que exibe a subtração do total de usuários cadastrados na data final do total de usuários cadastrados
	// na data inicial
	USUARIOS_NOVOS(2, "Usuários que se cadastraram", false,
		"Quantidade de usuários que se cadastraram."),
	// Estatística que exibe a subtração do total de usuários que compraram do total de usuários na data Final
	USUARIOS_SEM_COMPRA(3, "Usuários que não compraram", false,
		"Quantidade de usuários que não possuem compras finalizadas."),
	// Estatística que exibe o número de compras na situação DEBITO_NAO_AUTORIZADO cuja alteração na situação foi feita
	// no período
	COMPRAS_NEGADAS(4, "Compras negadas", false,
		"Quantidade de compras em débito não autorizado."),
	// Estatística que exibe o número de carrinhos abandonados no dia da pesquisa, cuja última aposta foi feita no
	// período
	// Um carrinho esta abandonado se ele possui alguma aposta dentro dele e nenhuma outra aposta foi feita por no
	// minimo 5 dias seguidos
	CARRINHOS_ABANDONADOS(5, "Carrinhos abandonados", false,
		"Quantidade de carrinhos que não foram manipulados por 5 ou mais dias seguidos."),
	// Estatítica que exibe o número de usuários cujas compras feitas no período estão na situação DEBITO_NAO_AUTORIZADO
	USUARIOS_APENAS_COMPRAS_NEGADAS(6, "Usuários que possuem apenas compras negadas", false,
		"Quantidade de usuários que possuem apenas compras em débito não autorizado"),
	// Estatística de arrecadação
	ARRECADACAO(7, "Arrecadação Total", true, "Valor total de arrecadação de apostas por meio de consulta aos fechamentos.");

	private final Integer codigo;
	private final String descricao;
	private final Boolean decimal;
	private final String dica;

	private TipoEstatistica(Integer codigo, String descricao, boolean isDecimal, String dica) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.decimal = isDecimal;
		this.dica = dica;
	}

	public static List<TipoEstatistica> getTiposEstatistica() {
		return Arrays.asList(TipoEstatistica.values());
	}
	
	public static TipoEstatistica getByDescricao(String descricao) {

		for (TipoEstatistica tipo : TipoEstatistica.values()) {
			if (tipo.descricao.equals(descricao)) {
				return tipo;
			}
		}
		return null;
	}

	private static TipoEstatistica[] getEstatisticasSemLimitePeriodo() {
		return new TipoEstatistica[] { ARRECADACAO };
	}

	public static boolean isSemLimitePeriodo(TipoEstatistica tipo) {
		return Arrays.asList(TipoEstatistica.getEstatisticasSemLimitePeriodo()).contains(tipo);
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

	public Boolean getDecimal() {
		return decimal;
	}

	public String getDica() {
		return dica;
	}


}
