package br.gov.caixa.silce.dominio;

import java.io.Serializable;

import br.gov.caixa.util.Decimal;

public class AgrupadorMediaPremio implements Serializable {

	private static final long serialVersionUID = 1L;
	private Decimal somaPremio = Decimal.ZERO;
	private long qtdPremio = 0L;
	private String canalResgate;

	public AgrupadorMediaPremio(String canalResgate) {
		this.canalResgate = canalResgate;
	}

	public static AgrupadorMediaPremio of(String canalResgate) {
		return new AgrupadorMediaPremio(null);
	}

	public Decimal getSomaPremio() {
		return somaPremio;
	}

	public void setSomaPremio(Decimal somaPremio) {
		this.somaPremio = somaPremio;
	}

	public long getQtdPremio() {
		return qtdPremio;
	}

	public void setQtdPremio(long qtdPremio) {
		this.qtdPremio = qtdPremio;
	}

	public String getCanalResgate() {
		return canalResgate;
	}

	public void setCanalResgate(String canalResgate) {
		this.canalResgate = canalResgate;
	}

	public Decimal getMediaValor() {
		return qtdPremio > 0 ? somaPremio.divide(new Decimal(qtdPremio)) : Decimal.ZERO;
	}

}
