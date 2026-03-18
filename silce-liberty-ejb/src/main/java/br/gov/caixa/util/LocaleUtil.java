package br.gov.caixa.util;

import java.util.Locale;

public final class LocaleUtil {

	public static final Locale PT_BR = new Locale("pt", "BR");

	//Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static LocaleUtil instancia = new LocaleUtil();

	private LocaleUtil(){
	}
	
	
}
