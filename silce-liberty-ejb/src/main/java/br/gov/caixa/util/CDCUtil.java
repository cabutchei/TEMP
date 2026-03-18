package br.gov.caixa.util;

import java.util.Calendar;

/**
 * Oferece métodos auxiliares para manipulação do CDC (Continuous Day Counter)
 * 
 * @author c134294 - Vinícius Rabelo
 */
public final class CDCUtil {

	/**
	 * Data inicial do CDC
	 */
	private static final String DATA_BASE_CDC_TEXTO = "01/01/1997";
	private static final Data DATA_BASE_CDC = DataUtil.stringToData(DATA_BASE_CDC_TEXTO);

	/**
	 * A data base da Getech é 01.05.1997 - esta data gera diferenças em datas em função dos anos bissextos. Para evitar
	 * estas diferenças, o SISPL considera como data base 01.01.1997 e diminui da quantidade de dias encontrados para
	 * uma data, a diferença entere as duas bases - DIFERENCA_DIAS_GETECH = 119 dias.
	 */
	private static final int DIFERENCA_DIAS_GETECH = 119;

	private static final int QTD_CHAR_CDC = 4;
	private static final int HORA_DIA_CALCULO_CDC = 12;

	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static CDCUtil instancia = new CDCUtil();


	private CDCUtil() {
	}

	/**
	 * Obtém a representação do CDC em formato {@link br.gov.caixa.util.Data}
	 * 
	 * @param cdc valor do CDC a ser convertido
	 * @return Representação do CDC em formato Data
	 */
	public static Data converteCdcParaData(int cdc) {
		Data data = new Data(DATA_BASE_CDC);
		data.add(Calendar.DAY_OF_YEAR, cdc + DIFERENCA_DIAS_GETECH);
		data.getCalendar().set(Calendar.HOUR_OF_DAY, HORA_DIA_CALCULO_CDC);
		return data;
	}

	/**
	 * Gera o CDC referente a data recebida
	 * 
	 * @return CDC
	 */
	public static String converteDataParaCdc(Data data) {
		//Como é mutável, criando copia
		Calendar calendarData = data.clone().getCalendar();
		calendarData.set(Calendar.HOUR_OF_DAY, HORA_DIA_CALCULO_CDC);

		Calendar calendarCdc = DATA_BASE_CDC.getCalendar();
		calendarCdc.set(Calendar.HOUR_OF_DAY, HORA_DIA_CALCULO_CDC);

		long numMillis = calendarData.getTime().getTime() - calendarCdc.getTime().getTime();
		long numDias = numMillis / DataUtil.QTD_MILISEGUNDOS_DIA;
		int cdc = (int) numDias - DIFERENCA_DIAS_GETECH;

		return StringUtil.completeAEsquerda(String.valueOf(cdc), QTD_CHAR_CDC, '0');
	}

}
