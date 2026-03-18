package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.util.List;

public class AgrupadorEstatistica implements Serializable {

	private static final long serialVersionUID = 1L;

	private TipoEstatistica tipoEstatistica;

	private List<SubcanalValor> valores;

	public AgrupadorEstatistica(TipoEstatistica tipoEstatistica, List<SubcanalValor> valores) {
		this.tipoEstatistica = tipoEstatistica;
		this.valores = valores;
	}

	public static AgrupadorEstatistica of(TipoEstatistica tipoEstatistica, List<SubcanalValor> valores) {
		return new AgrupadorEstatistica(tipoEstatistica, valores);
	}

	public TipoEstatistica getTipoEstatistica() {
		return tipoEstatistica;
	}

	public void setTipoEstatistica(TipoEstatistica tipoEstatistica) {
		this.tipoEstatistica = tipoEstatistica;
	}

	public List<SubcanalValor> getValores() {
		return valores;
	}

	public void setValores(List<SubcanalValor> valores) {
		this.valores = valores;
	}
}