package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.silce.dominio.entidade.AbstractApostaFavorita;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.CollectionUtil;

public class AgrupadorApostaFavorita implements Serializable {

	private static final String MODALIDADE_ESPORTIVA_NAO_PODE_SER_FAVORITA = "Modalidade esportiva não pode ser favorita.";

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	private static final ResultadoPesquisaPaginada<AbstractApostaFavorita> RESULTADO_VAZIO = new ResultadoPesquisaPaginada<AbstractApostaFavorita>(0, Collections.EMPTY_LIST);

	private Map<Modalidade, ResultadoPesquisaPaginada<AbstractApostaFavorita>> mapa = new HashMap<Modalidade, ResultadoPesquisaPaginada<AbstractApostaFavorita>>();

	public boolean contemAposta(Modalidade modalidade) {
		ResultadoPesquisaPaginada<AbstractApostaFavorita> lista = getLista(modalidade);
		return contemAposta(lista);
	}

	public boolean contemApostas() {
		for (Modalidade modalidade : Modalidade.getModalidadesNaoEsportivas()) {
			if(contemAposta(modalidade)){
				return true;
			}
		}
		return false;
	}

	private boolean contemAposta(ResultadoPesquisaPaginada<AbstractApostaFavorita> resultadoPesquisaPaginada) {
		return resultadoPesquisaPaginada != null && !CollectionUtil.isVazio(resultadoPesquisaPaginada.getDados());
	}
	
	public ResultadoPesquisaPaginada<AbstractApostaFavorita> getApostasFavoritas(Modalidade modalidade) {
		ResultadoPesquisaPaginada<AbstractApostaFavorita> lista = getLista(modalidade);
		return lista;
	}

	public ResultadoPesquisaPaginada<AbstractApostaFavorita> getLista(Modalidade modalidade) {
		ResultadoPesquisaPaginada<AbstractApostaFavorita> resultadoPesquisaPaginada = mapa.get(modalidade);
		return resultadoPesquisaPaginada == null ? RESULTADO_VAZIO : resultadoPesquisaPaginada;
	}

	public void setLista(Modalidade modalidade, ResultadoPesquisaPaginada<AbstractApostaFavorita> listaApostaFavorita) {
		if (modalidade.isEsportivo()) {
			throw new IllegalArgumentException(MODALIDADE_ESPORTIVA_NAO_PODE_SER_FAVORITA);
		}
		mapa.put(modalidade, listaApostaFavorita);
	}

	public int getQtdModalidadesComAposta() {
		int i = 0;
		for (Modalidade modalidade : Modalidade.getModalidadesNaoEsportivas()) {
			if (contemAposta(modalidade)) {
				i++;
			}
		}
		return i;
	}

}
