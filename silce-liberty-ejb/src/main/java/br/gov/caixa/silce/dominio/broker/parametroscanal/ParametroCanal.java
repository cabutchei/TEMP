package br.gov.caixa.silce.dominio.broker.parametroscanal;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.util.CaixaEnum;
import br.gov.caixa.util.EnumUtil;
import br.gov.caixa.util.Hora;

public class ParametroCanal extends SaidaBroker {

	public enum Status implements CaixaEnum<Integer> {
		ABERTO(0), 
		FECHADO(1);

		private final Integer value;

		private Status(int value) {
			this.value = value;

		}

		public static Status getByValue(Integer value) {
			return EnumUtil.recupereByValue(values(), value);
		}

		public Integer getValue() {
			return value;
		}
		
		public Boolean isAberto() {
			return getValue().equals(Status.ABERTO.getValue());
		}
	}
	
	private static final long serialVersionUID = 1L;

	private Status status;

	private Hora horaAbertura;

	private Hora horaFechamento;

	public ParametroCanal() {
		this(null, null, null);
	}
	
	public ParametroCanal(Status status, Hora horaAbertura, Hora horaFechamento) {
		this.status = status;
		this.horaAbertura = horaAbertura;
		this.horaFechamento = horaFechamento;
	}

	public Status getStatus() {
		return status;
	}

	public Hora getHoraAbertura() {
		return horaAbertura;
	}

	public Hora getHoraFechamento() {
		return horaFechamento;
	}
	
	public boolean isAberto() {
		return status.isAberto();
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setHoraAbertura(Hora horaAbertura) {
		this.horaAbertura = horaAbertura;
	}

	public void setHoraFechamento(Hora horaFechamento) {
		this.horaFechamento = horaFechamento;
	}

}
