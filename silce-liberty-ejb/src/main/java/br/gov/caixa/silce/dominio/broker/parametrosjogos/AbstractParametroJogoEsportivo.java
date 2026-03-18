package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.util.List;

import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.StringUtil;

/**
 * Tipo que define os parâmetros comuns aos jogos numéricos.
 */
public abstract class AbstractParametroJogoEsportivo extends AbstractParametroJogo {

	private static final char CARACT_COMP_PART_SELECIONADAS = '0';

	private static final int QTD_MAX_DECIMAIS_PART_SELECIONADAS = 2;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private List<ParametroPartida> partidas;
	
	private List<String> legendas;

	private Decimal valorApostaMinima;

	public List<ParametroPartida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<ParametroPartida> partidas) {
		this.partidas = partidas;
	}

	public Decimal getValorApostaMinima() {
		return valorApostaMinima;
	}

	public void setValorApostaMinima(Decimal valorApostaMinima) {
		this.valorApostaMinima = valorApostaMinima;
	}
	
	public String getQtPartidas() {
		Integer qtPartidas = partidas.size();
		return StringUtil.completeAEsquerda(qtPartidas.toString(), QTD_MAX_DECIMAIS_PART_SELECIONADAS, CARACT_COMP_PART_SELECIONADAS);
	}

	public List<String> getLegendas() {
		return legendas;
	}

	public void setLegendas(List<String> legendas) {
		this.legendas = legendas;
	}

}
