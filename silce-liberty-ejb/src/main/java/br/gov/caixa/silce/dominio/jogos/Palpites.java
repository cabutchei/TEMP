package br.gov.caixa.silce.dominio.jogos;

import java.io.Serializable;
import java.util.List;

/**
 * @author c101482
 *
 */
public interface Palpites<T> extends Serializable {

	/**
	 * 
	 * @return String das dezenas para serem exibidas na tela, ex: 01 02 10 00
	 */
	public String getStringExibicao();

	public void addPrognostico(Integer eixo, T prognostico);

	public List<T> getPrognosticos();

	public void setPrognosticos(List<T> prognosticos);

	public int size();
}
