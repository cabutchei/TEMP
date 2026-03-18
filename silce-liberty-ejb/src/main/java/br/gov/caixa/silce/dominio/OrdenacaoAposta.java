package br.gov.caixa.silce.dominio;

import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;

public enum OrdenacaoAposta implements CaixaEnum<Integer> {
	// Possiveis opcoes de ordenacao para apostas
	PADRAO(1, "Padrão", null),
	DATA_COMPRA_CRESCENTE(2, "Data Crescente", true), 
	DATA_COMPRA_DECRESCENTE(3, "Data Decrescente", false);

	private final Integer codigo;
	private final String descricao;
	private final Boolean ordemCrescente;

	private OrdenacaoAposta(Integer codigo, String descricao, Boolean ordemCrescente) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.ordemCrescente = ordemCrescente;
	}

	@Override
	public Integer getValue() {
		return getCodigo();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Boolean getOrdemCrescente() {
		return ordemCrescente;
	}

	public static OrdenacaoAposta getByCodigo(Integer codigo) {
		// Verificacao do null realizada em prol da Retrocompatibilidade com chamadas que não enviam o codigo da
		// ordenacao
		if (codigo == null) {
			return PADRAO;
		}
		return EnumUtil.recupereByValue(values(), codigo);
	}

	public Boolean isOrdenacaoCompra() {
		return this.equals(DATA_COMPRA_CRESCENTE) || this.equals(DATA_COMPRA_DECRESCENTE);
	}

}
