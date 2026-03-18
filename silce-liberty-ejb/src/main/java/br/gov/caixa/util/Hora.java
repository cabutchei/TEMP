package br.gov.caixa.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * Classe que representa Hora de um dia;
 * 
 * hora 0 - 23
 * minuto 0 - 59
 * segundo 0 - 59
 * 
 * @author c101482 - Vinicius Ferraz Campos Florentino
 *
 */
public final class Hora implements Serializable, Comparable<Hora> {

	private static final long serialVersionUID = 1L;

	private static final int ULTIMO_SEGUNDO_VALIDO = 59;

	private static final int ULTIMO_MINUTO_VALIDO = 59;

	private static final int ULTIMA_HORA_VALIDA = 23;

	private int horaDoDia;
	
	private int minuto;
	
	private int segundo;
	
	public Hora() {
		this(DataUtil.getTimestampAtual());
	}
	
	public Hora(Data timestampAtual) {
		this(timestampAtual.get(Calendar.HOUR_OF_DAY), timestampAtual
			.get(Calendar.MINUTE), timestampAtual.get(Calendar.SECOND));
	}
	
	public Hora(int hora, int minuto, int segundo) {
		initValores(hora, minuto, segundo);
	}

	
	public Hora(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		initValores(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
	}

	public Hora(long timestamp) {
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(timestamp);
		initValores(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
	}

	public Hora(Hora hora) {
		this(hora.getHoraDoDia(), hora.getMinuto(), hora.getSegundo());
	}
	
	/**
	 * @param horaDoDia HH:mm:ss
	 */
	public Hora(String horaStr) {
		Data stringToData = DataUtil.stringToData(horaStr, "HH:mm:ss");
		initValores(stringToData.get(Calendar.HOUR_OF_DAY), stringToData.get(Calendar.MINUTE), stringToData.get(Calendar.SECOND));
	}
	
	/**
	 * Verifica se esta hora está entre as duas horas informadas. Os limites são exclusivos (não inclusivos).
	 */
	public boolean isEntre(Hora horaInicio, Hora horaFim) {
		boolean depoisDeHoraInicio = compareTo(horaInicio) > 0;
		boolean antesDeHoraFim = compareTo(horaFim) < 0;

		return depoisDeHoraInicio && antesDeHoraFim;
	}

	public int getHoraDoDia() {
		return horaDoDia;
	}

	public int getMinuto() {
		return minuto;
	}

	public int getSegundo() {
		return segundo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + horaDoDia;
		result = prime * result + minuto;
		result = prime * result + segundo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Hora)) {
			return false;
		}
		Hora other = (Hora) obj;
		if (horaDoDia != other.horaDoDia) {
			return false;
		}
		if (minuto != other.minuto) {
			return false;
		}
		if (segundo != other.segundo) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Hora o) {
		if(horaDoDia != o.horaDoDia) {
			return horaDoDia - o.horaDoDia;
		}
		
		if(minuto != o.minuto) {
			return minuto - o.minuto;
		}
		
		if(segundo != o.segundo) {
			return segundo - o.segundo;
		}
		
		return 0;
	}

	public boolean equalsOrAfter(Hora when) {
		return !before(when);
	}

	public boolean equalsOrBefore(Hora when) {
		return !after(when);
	}

	public boolean before(Hora when) {
		return compareTo(when) < 0;
	}

	public boolean after(Hora when) {
		return compareTo(when) > 0;
	}

	public Date getTime() {
		return getCalendar().getTime();
	}
	
	public Calendar getCalendar() {
		//O porque de 70 0 1: (retirado da classe Calendar)
		
		/*
		 *  The epoch is the number of days or milliseconds from some defined
	     * starting point. The epoch for java.util.Date is used here; that is,
	     * milliseconds from January 1, 1970 (Gregorian), midnight UTC.  Other
	     * epochs which are used are January 1, year 1 (Gregorian), which is day 1
	     * of the Gregorian calendar, and December 30, year 0 (Gregorian), which is
	     * day 1 of the Julian calendar.
		 */
		
		return new GregorianCalendar(70, 0, 1, horaDoDia, minuto, segundo);
	}

	@Override
	public String toString() {
		return StringUtil.completeAEsquerda(Integer.toString(horaDoDia), 2, '0')+":"
				+StringUtil.completeAEsquerda(Integer.toString(minuto), 2, '0')+":"
				+StringUtil.completeAEsquerda(Integer.toString(segundo), 2, '0');
	}
	
	public boolean isBetween(IntervaloHora intervalo) {
		if (intervalo == null) {
			return false;
		}

		Hora inicio = intervalo.getInicio();
		Hora fim = intervalo.getFim();
		if (inicio == null && fim == null) {
			return false;
		}
		if(inicio != null && fim == null) {
			return this.equalsOrAfter(inicio);
		}
		if(inicio == null && fim != null) {
			return this.equalsOrBefore(fim);
		}
		
		if (inicio.equalsOrBefore(fim)) {
			return this.equalsOrAfter(inicio) && this.equalsOrBefore(fim);
		}
		return !(this.after(fim) && this.before(inicio));
	}


	private void valideSegundo(int segundo) {
		if(segundo < 0 || segundo > ULTIMO_SEGUNDO_VALIDO) {
			throw new IllegalArgumentException("Segundo inválido "+segundo);
		}
	}

	private void valideMinuto(int minuto) {
		if(minuto < 0 || minuto > ULTIMO_MINUTO_VALIDO) {
			throw new IllegalArgumentException("Minuto inválido "+minuto);
		}
	}

	private void valideHora(int hora) {
		if(hora < 0 || hora > ULTIMA_HORA_VALIDA) {
			throw new IllegalArgumentException("Hora inválida "+hora);
		}
	}

	private void initValores(int hora, int minuto, int segundo) {
		valideHora(hora);
		valideMinuto(minuto);
		valideSegundo(segundo);
		this.horaDoDia = hora;
		this.minuto = minuto;
		this.segundo = segundo;
	}
	
	
}
