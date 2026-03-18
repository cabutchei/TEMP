package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.silce.dominio.NSB;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Hora;

public class RetornoRegistraAposta extends SaidaBroker {

	private static final long serialVersionUID = 1L;
	private NSB nsb;
	private Long nsu;
	private Data data;
	private Data dataEfetivacao;
	private Hora horaEfetivacao;

	public NSB getNsb() {
		return nsb;
	}

	public void setNsb(NSB nsb) {
		this.nsb = nsb;
	}

	public Long getNsu() {
		return nsu;
	}

	public void setNsu(Long nsu) {
		this.nsu = nsu;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getDataEfetivacao() {
		return dataEfetivacao;
	}

	public void setDataEfetivacao(Data dataEfetivacao) {
		this.dataEfetivacao = dataEfetivacao;
	}

	public Hora getHoraEfetivacao() {
		return horaEfetivacao;
	}

	public void setHoraEfetivacao(Hora horaEfetivacao) {
		this.horaEfetivacao = horaEfetivacao;
	}

	@Override
	public String toString() {
		StringBuilder builder = createToStringBuilder();
		builder.append("RetornoRegistraAposta [nsb=").append(nsb).append(", nsu=").append(nsu).append(", data=").append(data)
				.append(", dataEfetivacao=").append(dataEfetivacao).append(", horaEfetivacao=").append(horaEfetivacao).append(']');
		return builder.toString();
	}

}
