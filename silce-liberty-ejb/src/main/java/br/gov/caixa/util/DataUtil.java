package br.gov.caixa.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import br.gov.caixa.util.exception.DataInvalidaException;
import br.gov.caixa.util.exception.HoraInvalidaException;

/**
 * Oferece funcionalidades para manipulação de objetos java.util.Date.
 * 
 * 
 */
public final class DataUtil {

	public static final String DD_MM_YYYY = "dd/MM/yyyy";
	public static final String YYYY_MM_DD = "yyyy/MM/dd";
	public static final String DDMMYYYY = "ddMMyyyy";
	public static final String DIAHORA_EXTENSO = "ddMMyyyyHHmmss";
	public static final String YYYYMMDD = "yyyy-MM-dd";
	public static final String DIA_HORA = "dd/MM/yyyy HH:mm:ss";
	public static final String HORA = "HH:mm:ss";
	public static final String DIA_HORA_EUA = "yyyy-MM-dd HH:mm:ss";
	public static final String DIA_HORA_EUA_2 = "yyyy/MM/dd HH:mm:ss";
	public static final String DIA_HORA_MILISEGUNDOS_EUA = "yyyy-MM-dd'T'HH:mm:ss.SSS";

	private static final String REGEX_DD_MM_YYYY = "(0?[1-9]|[12][0-9]|3[01])/((0?[1-9]|1[012])/([0-9]{4}))";
	private static final String REGEX_DD_MM_YYYY_HH_MM_SS = "(0?[1-9]|[12][0-9]|3[01])/((0?[1-9]|1[012])/([0-9]{4}))\\s+([0-1]\\d|2[0-3]):([0-5]\\d:[0-5]\\d)";

	// milisegundos em 1 dia (24 * 60 * 60 * 1000)
	public static final Long QTD_MILISEGUNDOS_DIA = 86400000L;

	private static final Map<String, String> PATTERN_REGEX = new HashMap<String, String>();

	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static DataUtil instancia = new DataUtil();

	private static final Map<Integer, String> NOMES_MESES = new HashMap<Integer, String>();
	private static final Map<Integer, String> NOMES_MESES_ABREVIADOS = new HashMap<Integer, String>();

	static {
		PATTERN_REGEX.put(DD_MM_YYYY, REGEX_DD_MM_YYYY);
		PATTERN_REGEX.put(DIA_HORA, REGEX_DD_MM_YYYY_HH_MM_SS);
	}

	static {
		NOMES_MESES.put(1, "Janeiro");
		NOMES_MESES.put(2, "Fevereiro");
		NOMES_MESES.put(3, "Março");
		NOMES_MESES.put(4, "Abril");
		NOMES_MESES.put(5, "Maio");
		NOMES_MESES.put(6, "Junho");
		NOMES_MESES.put(7, "Julho");
		NOMES_MESES.put(8, "Agosto");
		NOMES_MESES.put(9, "Setembro");
		NOMES_MESES.put(10, "Outubro");
		NOMES_MESES.put(11, "Novembro");
		NOMES_MESES.put(12, "Dezembro");

		NOMES_MESES_ABREVIADOS.put(1, "JAN");
		NOMES_MESES_ABREVIADOS.put(2, "FEV");
		NOMES_MESES_ABREVIADOS.put(3, "MAR");
		NOMES_MESES_ABREVIADOS.put(4, "ABR");
		NOMES_MESES_ABREVIADOS.put(5, "MAI");
		NOMES_MESES_ABREVIADOS.put(6, "JUN");
		NOMES_MESES_ABREVIADOS.put(7, "JUL");
		NOMES_MESES_ABREVIADOS.put(8, "AGO");
		NOMES_MESES_ABREVIADOS.put(9, "SET");
		NOMES_MESES_ABREVIADOS.put(10, "OUT");
		NOMES_MESES_ABREVIADOS.put(11, "NOV");
		NOMES_MESES_ABREVIADOS.put(12, "DEZ");
	}

	private DataUtil() {
	}

	public static String dataToString(Data value, String pattern) {
		return getSimpleDateFormat(pattern).format(value.getTime());
	}

	public static String dataToString(Timestamp value, String pattern) {
		return getSimpleDateFormat(pattern).format(value.getTime());
	}

	public static String dataToString(Data value) {
		return dataToString(value, DD_MM_YYYY);
	}

	public static String dataToStringSafe(Data value, String pattern) {
		if (value == null) {
			return null;
		}
		return dataToString(value, pattern);
	}

	public static String dataToStringSafe(Data value) {
		return dataToStringSafe(value, DD_MM_YYYY);
	}

	/**
	 * Converte uma data em String para java.util.Date.
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */
	public static Data stringToData(String value, String pattern) {
		SimpleDateFormat sd = getSimpleDateFormat(pattern);
		if (value != null && isDateFormatValid(value, pattern)) {
			try {
				return new Data(sd.parse(value));
			} catch (Exception e) {
				throw new DataInvalidaException(e);
			}
		} else {
			throw new DataInvalidaException("Data inválida: " + value);
		}
	}

	private static boolean isDateFormatValid(String value, String pattern) {
		boolean valid = true;
		String regex = PATTERN_REGEX.get(pattern);
		if (regex != null && !value.matches(regex)) {
			valid = false;
		}
		return valid;
	}

	public static Data stringToData(String value) {
		return stringToData(value, DD_MM_YYYY);
	}

	public static Data stringToTimestamp(String value) {
		return stringToData(value, DIA_HORA);
	}

	public static Data stringToDataSafe(String value, String pattern) {
		try {
			return stringToData(value, pattern);
		} catch (Exception e) {
			return null;
		}
	}

	public static Data stringToDataSafe(String value) {
		return stringToDataSafe(value, DD_MM_YYYY);
	}

	private static SimpleDateFormat getSimpleDateFormat(String pattern) {
		SimpleDateFormat sd = new SimpleDateFormat(pattern, LocaleUtil.PT_BR);
		sd.setLenient(false);
		return sd;
	}

	public static boolean isDataValida(String data) {
		return isDataValida(data, DD_MM_YYYY);
	}

	public static boolean isDataValida(String data, String pattern) {
		try {
			stringToData(data, pattern);
			return true;
		} catch (DataInvalidaException e) {
			return false;
		}
	}

	public static Data getDataAtual() {
		Data data = new Data(System.currentTimeMillis());
		data.zereHoraMinutoSegundoMilisegundo();
		return data;
	}

	public static Data getTimestampAtual() {
		return new Data(System.currentTimeMillis());
	}

	/**
	 * @return Retorna o ultimo timestamp do dia com hora 23, minuto 59 e segundo 59 e milisegundo 999
	 */
	public static Data getUltimoTimestampDoDia(Data data) {
		Data dataFinal = new Data(data);
		dataFinal.zereHoraMinutoSegundoMilisegundo();
		dataFinal.add(Calendar.HOUR_OF_DAY, 23);
		dataFinal.add(Calendar.MINUTE, 59);
		dataFinal.add(Calendar.SECOND, 59);
		dataFinal.add(Calendar.MILLISECOND, 999);
		return dataFinal;
	}

	/**
	 * @see #getUltimoTimestampDoDia(Data)
	 */
	public static Data getUltimoTimestampDeHoje() {
		return getUltimoTimestampDoDia(getTimestampAtual());
	}

	/**
	 * @return uma Data com time zero
	 */
	public static Data getDataZero() {
		return new Data(0L);
	}

	public static Data fromIso8601(String dataIso8601) {
		if (dataIso8601 == null) {
			return null;
		}
		Calendar parseDateTime = DatatypeConverter.parseDateTime(dataIso8601);
		return new Data(parseDateTime);
	}

	public static String toIso8601(Data data) {
		if (data == null) {
			return null;
		}
		return DatatypeConverter.printDateTime(data.getCalendar());
	}

	public static String horaToString(Hora value) {
		return horaToString(value, HORA);
	}

	public static String horaToString(Hora value, String pattern) {
		return getSimpleDateFormat(pattern).format(value.getTime());
	}

	public static Hora stringToHora(String value, String pattern) {
		SimpleDateFormat sd = getSimpleDateFormat(pattern);
		if (value != null && isDateFormatValid(value, pattern)) {
			try {
				return new Hora(sd.parse(value));
			} catch (Exception e) {
				throw new HoraInvalidaException(e);
			}
		} else {
			throw new HoraInvalidaException("Hora inválida: " + value);
		}
	}

	public static Hora stringToHora(String value) {
		return stringToHora(value, HORA);
	}

	public static String getNomeMes(int numeroMes) {
		return NOMES_MESES.get(numeroMes);
	}

	public static String getAbreviacaoMes(int numeroMes) {
		return NOMES_MESES_ABREVIADOS.get(numeroMes);
	}

	public static Integer getNumeroMesPorAbreviacao(String abreviacaoMes) {
		if (StringUtil.isEmpty(abreviacaoMes)) {
			return null;
		}

		Set<Entry<Integer, String>> entrySet = NOMES_MESES_ABREVIADOS.entrySet();
		for (Entry<Integer, String> entry : entrySet) {
			if (entry.getValue().toLowerCase(LocaleUtil.PT_BR).equals(abreviacaoMes.toLowerCase(LocaleUtil.PT_BR))) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static Integer getNumeroMesPorNome(String nomeMes) {
		if (StringUtil.isEmpty(nomeMes)) {
			return null;
		}

		Set<Entry<Integer, String>> entrySet = NOMES_MESES.entrySet();
		for (Entry<Integer, String> entry : entrySet) {
			if (entry.getValue().toLowerCase(LocaleUtil.PT_BR).equals(nomeMes.toLowerCase(LocaleUtil.PT_BR))) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static List<Integer> gereListaMeses(final int mesDaConsulta, final int qtdMesesParaTras) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, mesDaConsulta - qtdMesesParaTras - 1);
		List<Integer> meses = new ArrayList<Integer>(qtdMesesParaTras + 1);
		do {
			meses.add(calendar.get(Calendar.MONTH) + 1);
			calendar.add(Calendar.MONTH, 1);
		} while (meses.size() <= qtdMesesParaTras);
		return meses;
	}

	public static List<Integer> getNumeroMeses() {
		List<Integer> numeroMeses = new ArrayList<Integer>();
		numeroMeses.addAll(NOMES_MESES.keySet());
		return numeroMeses;
	}

	public static long getDateDiffMilissegundo(Data timestamp) {
		return timestamp.getTime().getTime() - QTD_MILISEGUNDOS_DIA;
	}

	public static String getDiaSemanaExtenso(String data) {
		DateTimeFormatter parser = DateTimeFormatter.ofPattern(DD_MM_YYYY);
		TemporalAccessor accessor = parser.parse(data);
		DayOfWeek dow = DayOfWeek.from(accessor);
		return dow.getDisplayName(TextStyle.FULL, new java.util.Locale("pt", "BR"));
	}
}
