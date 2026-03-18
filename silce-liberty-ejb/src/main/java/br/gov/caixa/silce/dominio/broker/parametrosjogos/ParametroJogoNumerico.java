package br.gov.caixa.silce.dominio.broker.parametrosjogos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.caixa.util.Decimal;

/**
 * Tipo que define os parâmetros comuns aos jogos numéricos.
 */
public class ParametroJogoNumerico extends AbstractParametroJogo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean aceitaEspelho;

	private Integer prognosticoMaximo;

	private Integer quantidadeMinima;

	private Integer quantidadeMaxima;

	private Integer quantidadeSurpresinhas;

	private List<Integer> teimosinhas;
	
	private List<ParametroValorApostaJogoNumerico> valoresAposta;

	private Map<Integer, Decimal> mapaQuantidadePrognosticosValor = new HashMap<Integer, Decimal>();
	
	public Boolean getAceitaEspelho() {
		return aceitaEspelho;
	}

	public void setAceitaEspelho(Boolean aceitaEspelho) {
		this.aceitaEspelho = aceitaEspelho;
	}

	public Integer getPrognosticoMaximo() {
		return prognosticoMaximo;
	}

	public void setPrognosticoMaximo(Integer prognosticoMaximo) {
		this.prognosticoMaximo = prognosticoMaximo;
	}

	public Integer getQuantidadeMinima() {
		return quantidadeMinima;
	}

	public void setQuantidadeMinima(Integer quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}

	public Integer getQuantidadeMaxima() {
		return quantidadeMaxima;
	}

	public void setQuantidadeMaxima(Integer quantidadeMaxima) {
		this.quantidadeMaxima = quantidadeMaxima;
	}

	public Integer getQuantidadeSurpresinhas() {
		return quantidadeSurpresinhas;
	}

	public void setQuantidadeSurpresinhas(Integer quantidadeSurpresinhas) {
		this.quantidadeSurpresinhas = quantidadeSurpresinhas;
	}

	public List<Integer> getTeimosinhas() {
		return teimosinhas;
	}

	public void setTeimosinhas(List<Integer> teimosinhas) {
		this.teimosinhas = teimosinhas;
	}

	public List<ParametroValorApostaJogoNumerico> getValoresAposta() {
		return valoresAposta;
	}

	public void setValoresAposta(List<ParametroValorApostaJogoNumerico> valoresAposta) {
		this.valoresAposta = valoresAposta;
		mapaQuantidadePrognosticosValor = new HashMap<Integer, Decimal>();
		if(valoresAposta != null) {
			for (ParametroValorApostaJogoNumerico parametro : valoresAposta) {
				mapaQuantidadePrognosticosValor.put(parametro.getNumeroPrognosticos(), parametro.getValor());
			}
		}
	}
	
	public Decimal getValor(Integer quantidadePrognosticos) {
		return mapaQuantidadePrognosticosValor.get(quantidadePrognosticos);
	}

	public boolean getAceitaTeimosinha() {
		if (this.teimosinhas.size() > 1) {
			return true;
		}
		return !(this.teimosinhas.get(0) == 0);
	}

}
