package br.gov.caixa.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Classe substituta de Calendar e Date.
 * Contém métodos mais intuitivos de comparação e conversão
 * 
 * @author c101482 - Vinicius Ferraz Campos Florentino
 *
 */
public class Data implements Serializable, Cloneable, Comparable<Data> {

	public static final Integer QTD_SEGUNDOS_NO_MINUTO = 60;
	public static final Integer QTD_MINUTOS_NA_HORA = 60;
	public static final Integer QTD_HORAS_NO_DIA = 24;
	public static final Integer QTD_DIAS_NA_SEMANA = 7;
	public static final Integer QTD_DIAS_NO_MES_COMERCIAL = 30;
	public static final Integer QTD_DIAS_NO_ANO_COMERCIAL = 360;
	public static final Integer QTD_MESES_NO_ANO = 12;

	private static final long serialVersionUID = 1L;
	
	private final Calendar c;
	
	public Data(String strDate, String pattern){
		this(DataUtil.stringToData(strDate, pattern));
	}
	
	public Data(String strDate) {
		this(strDate, DataUtil.DD_MM_YYYY);
	}

	public Data(Data data){
		this(data.getTime());
	}
	
	public Data(long date) {
		this(new GregorianCalendar());
		setTimeInMillis(date);
	}
	
	public Data(Date date) {
		this(date.getTime());
	}
	
	public Data(Calendar  calendar) {
		if(calendar == null) {
			throw new IllegalArgumentException("calendar não pode ser null");
		}
		c = calendar;
	}
	
	public Data(int year, int month, int dayOfMonth) {
		this(year, month, dayOfMonth, 0, 0, 0);
    }

	public Data(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
		this(year, month, dayOfMonth, hourOfDay, minute, 0);
    }

    /**
     * @param year
     * @param month 0 = janeiro, 1 = fev... igual ao Calendar 
     * @param dayOfMonth
     * @param hourOfDay
     * @param minute
     * @param second
     */
	public Data(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
		this(new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second));
    }

	public Calendar getCalendar() {
		return c;
    }

	public long getTimeInMillis() {
		return c.getTimeInMillis();
	}
	
	public Data setTimeInMillis(long time) {
		c.setTimeInMillis(time);
		return this;
	}

	public boolean before(Data when) {
		return c.before(when.c);
	}
	
	public boolean equalsOrAfter(Data when) {
		return !before(when);
	}
	
	public boolean equalsOrBefore(Data when) {
		return !after(when);
	}
	
	public Data zereHoraMinutoSegundoMilisegundo() {
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		return this;
	}
	
	public Data ultimoMinutoSegundo() {
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.HOUR_OF_DAY, 23);
		return this;
	}

	public boolean after(Data when) {
		return c.after(when.c);
	}
	
	public Data add(int field, int amount) {
		c.add(field, amount);
		return this;
	}
	
	public Data add(Hora hora) {
		c.add(Calendar.HOUR, hora.getHoraDoDia());
		c.add(Calendar.MINUTE, hora.getMinuto());
		c.add(Calendar.SECOND, hora.getSegundo());
		return this;
	}

	public Data subtract(int field, int amount) {
		c.add(field, -amount);
		return this;
	}

	public Data subtract(Hora hora) {
		c.add(Calendar.HOUR, -hora.getHoraDoDia());
		c.add(Calendar.MINUTE, -hora.getMinuto());
		c.add(Calendar.SECOND, -hora.getSegundo());
		return this;
	}

	public long getDiferenca(Data when, TimeUnit tipoUnidade) {
		boolean whenIsBefore = when.before(this);
		Data dataMenor = whenIsBefore ? when : this;
		Data dataMaior = whenIsBefore ? this : when;

		long diferencaEmMilis = dataMaior.getTimeInMillis() - dataMenor.getTimeInMillis();
		if (!tipoUnidade.equals(TimeUnit.DAYS)) {
			return tipoUnidade.convert(diferencaEmMilis, TimeUnit.MILLISECONDS);

		}
		int diferencaTimezone = dataMaior.c.get(Calendar.ZONE_OFFSET) - dataMenor.get(Calendar.ZONE_OFFSET);
		int diferencaHorarioVerao = dataMaior.c.get(Calendar.DST_OFFSET) - dataMenor.get(Calendar.DST_OFFSET);
		return tipoUnidade.convert(diferencaEmMilis + diferencaTimezone + diferencaHorarioVerao, TimeUnit.MILLISECONDS);

	}
	
	public long getDiferencaEmAnos(Data when) {
		boolean whenIsBefore = when.before(this);
		Data a = whenIsBefore ? when : this;
		Data b = whenIsBefore ? this : when;
		int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
		if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
			diff--;
		}
		return diff;
	}

	public boolean isEntreConsiderandoNulls(Data inicio, Data fim) {
		if (inicio != null && fim != null) {
			return this.equalsOrAfter(inicio) && this.equalsOrBefore(fim);
		}
		if (inicio == null && fim != null) {
			return this.equalsOrBefore(fim);
		}
		if (inicio != null && fim == null) {
			return this.equalsOrAfter(inicio);
		}
		return false;
	}

	@Override
	public String toString() {
		return DataUtil.dataToString(this, DataUtil.DIA_HORA);
	}
	
	public String toOnlyDataString() {
		return DataUtil.dataToString(this, DataUtil.DD_MM_YYYY);
	}
	
	public boolean isDiaSemana() {
		return !isFinalDeSemana();
	}
	
	public boolean isFinalDeSemana() {
		int daiOfWeek = c.get(Calendar.DAY_OF_WEEK);
		return daiOfWeek == Calendar.SUNDAY || daiOfWeek == Calendar.SATURDAY;
	}

	@Override
	public int hashCode() {
		return c.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Data)) {
			return false;
		}
		Data other = (Data) obj;
		//Para comparar desconsidera os milis
		int t1 = (int) (c.getTime().getTime() / 1000);
		int t2 = (int) (other.c.getTime().getTime() / 1000);
		return t1 == t2;
	}
	
	public Date getTime() {
		return c.getTime();
	}
	
	public Hora getHora() {
		return new Hora(this);
	}

	public Data setHora(Hora hora) {
		c.set(Calendar.HOUR_OF_DAY, hora.getHoraDoDia());
		c.set(Calendar.MINUTE, hora.getMinuto());
		c.set(Calendar.SECOND, hora.getSegundo());
		return this;
	}

	public int get(int field) {
		return c.get(field);
	}

	@Override
	public Data clone() {
		return new Data(c.getTime());
	}

	@Override
	public int compareTo(Data o) {
		return c.compareTo(o.c);
	}

	public Data imutavel() {
		// fazendo assim por dica do sonar
		return DataImutavel.newInstance(this.getCalendar());
	}

	public boolean isPassado() {
		return this.before(DataUtil.getTimestampAtual());
	}

	public int getDay() {
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public int getMonth() {
		return c.get(Calendar.MONTH) + 1;
	}

	public int getYear() {
		return c.get(Calendar.YEAR);
	}

	static final class DataImutavel extends Data {

		private static final long serialVersionUID = 1L;

		private DataImutavel(Calendar calendar) {
			super(calendar);
		}

		private static final DataImutavel newInstance(Calendar calendar) {
			return new DataImutavel(calendar);
		}

		@Override
		public Calendar getCalendar() {
			Calendar instance = Calendar.getInstance();
			long timeInMillis = super.getCalendar().getTimeInMillis();
			instance.setTimeInMillis(timeInMillis);
			return instance;
		}

		@Override
		public Data setTimeInMillis(long time) {
			return new Data(time).imutavel();
		}

		@Override
		public Data zereHoraMinutoSegundoMilisegundo() {
			return new Data(getTimeInMillis()).zereHoraMinutoSegundoMilisegundo().imutavel();
		}

		@Override
		public Data add(int field, int amount) {
			return new Data(getTimeInMillis()).add(field, amount).imutavel();
		}

		@Override
		public Data subtract(int field, int amount) {
			return new Data(getTimeInMillis()).subtract(field, amount).imutavel();
		}

	}
}
