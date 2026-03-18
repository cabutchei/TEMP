package br.gov.caixa.util;

import java.io.Serializable;


/**
 * @author c101482
 *
 */
public class Telefone implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final int QNT_DIGITOS_DDD = 3;

	private Integer ddd;  
	
	private Integer numero;
	
	/**
	 * Construtor necessário 
	 */
	protected Telefone() {
		//Necessário
	}
	
	public Telefone(Integer ddd, Integer numero) {
		valide(ddd, numero);
		this.ddd = ddd;
		this.numero = numero;
	}

	public Telefone(String ddd, String numero) {
		Integer dddInt = Integer.parseInt(ddd);
		Integer numeroInt = Integer.parseInt(numero);

		valide(dddInt, numeroInt);

		this.ddd = dddInt;
		this.numero = numeroInt;
	}

	private void valide(Integer ddd, Integer numero) {
		if(ddd == null || numero == null) {
			throw new NullPointerException("ddd e numero são obrigatórios");
		}
		String numeroStr = String.valueOf(numero);
		if(numeroStr.length() != 8 && numeroStr.length() != 9) {
			throw new IllegalArgumentException("Número deve ter 8 ou 9 digitos");
		}
	}

	public Integer getDdd() {
		return ddd;
	}

	public Integer getNumero() {
		return numero;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (ddd != null) {
			builder.append(StringUtil.completeAEsquerda(ddd.toString(), QNT_DIGITOS_DDD, '0'));
		}
		if (numero != null) {
			builder.append(numero);
		}
		return builder.toString();
	}


	/**
	 * É o padrão do SIDMO
	 * DDD com 2 numeros, celular com 9
	 * @return
	 */
	public String getNumeroCompletoSemMascara() {
		return StringUtil.completeAEsquerda(String.valueOf(getDdd()), 2, '0')+StringUtil.completeAEsquerda(String.valueOf(getNumero()), 9, '0');
	}
	
	/**
	 * (DDD) 1234-5678
	 * ou
	 * (DDD) 12342-5678
	 * @return
	 */
	public String getNumeroMascarado() {
		if(getDdd() == null || getNumero() == null) {
			return "";
		}
		StringBuilder digitosNumero = new StringBuilder(String.valueOf(getNumero()));
		digitosNumero.insert(digitosNumero.length() == 8 ? 4 : 5, '-');
		return "(" + StringUtil.completeAEsquerda(String.valueOf(getDdd()), 3, '0') + ") " + digitosNumero;
	}
  
  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ddd == null) ? 0 : ddd.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		if (!(obj instanceof Telefone)) {
			return false;
		}
		Telefone other = (Telefone) obj;
		if (ddd == null) {
			if (other.ddd != null) {
				return false;
			}
		} else if (!ddd.equals(other.ddd)) {
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}
		return true;
	}
	
}
