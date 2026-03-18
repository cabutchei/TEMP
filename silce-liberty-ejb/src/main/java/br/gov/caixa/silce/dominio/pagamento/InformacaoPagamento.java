package br.gov.caixa.silce.dominio.pagamento;

import java.io.Serializable;
import java.util.HashMap;

import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;

public class InformacaoPagamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Meio meio;
	private HashMap<ParametroPagamento, String> parametrosPagamento = new HashMap<ParametroPagamento, String>();

	public InformacaoPagamento() {
		// faz nada
	}
	
	public InformacaoPagamento(Meio meioPagamento) {
		this.meio = meioPagamento;
	}
	
	public String getParam(ParametroPagamento key) {
		return getParametrosPagamento().get(key);
	}

	public Object putParam(ParametroPagamento key, String value) {
		return getParametrosPagamento().put(key, value);
	}

	public Meio getMeio() {
		return meio;
	}

	public void setMeio(Meio meio) {
		this.meio = meio;
	}
	
	public HashMap<ParametroPagamento, String> getParametrosPagamento() {
		return parametrosPagamento;
	}

	@Override
	public String toString() {
		return "InformacaoPagamento [meio=" + meio + ", parametrosPagamento=" + parametrosPagamento + "]";
	}

}
