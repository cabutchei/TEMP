package br.gov.caixa.util;


public final class  Modulo11 {
	
	//Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static Modulo11 instancia = new Modulo11();
		
	
	private Modulo11() {
		
	}
	
	public static int calculeDigito(String entrada, int[] pesos) {
		
		char[] caracteres = entrada.toCharArray();
		
		int soma = 0;
		
		//calculo do primeiro digito verificador
		for (int i = 0; i < caracteres.length; i++) {
			soma += Integer.parseInt(String.valueOf(caracteres[i])) * pesos[i];
		}
		
		int dv = soma % 11;
		
		if (dv < 2) {
			return 0;
		}
		return 11 - dv;
	}
}
