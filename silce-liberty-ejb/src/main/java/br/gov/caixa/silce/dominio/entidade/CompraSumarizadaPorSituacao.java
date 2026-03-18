package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.gov.caixa.silce.dominio.entidade.SituacaoCompra.Situacao;
import br.gov.caixa.util.Decimal;

public class CompraSumarizadaPorSituacao implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Quantidade de compras por situacao
	 */
	private Map<SituacaoCompra.Situacao, Long> sumarizacoes = new HashMap<SituacaoCompra.Situacao, Long>();

	/**
	 * Quantidade total de compras considerando todas as situacoes
	 */
	private Long quantidadeTotal = 0L;

	/**
	 * Média diaria do valor das compras
	 */
	private Decimal mediaDiaria;

	public void addSumarizacao(Integer situacao, Integer quantidade) {
		sumarizacoes.put(Situacao.getByValue(situacao.longValue()), quantidade.longValue());
		quantidadeTotal += quantidade;
	}

	public Long getQuantidade(Situacao situacao) {
		Long retorno = sumarizacoes.get(situacao);
		if (retorno == null) {
			return 0L;
		}
		return retorno;
	}

	public Long getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public Decimal getMediaDiaria() {
		return mediaDiaria;
	}

	public void setMediaDiaria(Decimal mediaDiaria) {
		this.mediaDiaria = mediaDiaria;
	}

}

