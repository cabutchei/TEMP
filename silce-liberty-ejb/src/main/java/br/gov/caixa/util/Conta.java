package br.gov.caixa.util;

import java.io.Serializable;

public class Conta implements Serializable {

	private static final int[] PESOS_AG = new int[] { 2, 3, 4, 5 };
	public static final int TAMANHO_DV = 1;
	public static final int TAMANHO_NUMERO = 12;

	public static final int TAMANHO_OPERACAO = 4;

	public static final int TAMANHO_AGENCIA = 4;

	public static final long serialVersionUID = 1L;

	private static final int[] PESOS_CALCULO_GERAL = new int[] { 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	private Integer agencia;
	private Integer operacao;
	private Long numero;
	private Integer dv;

	public enum TipoSistemaFinanceiro implements CaixaEnum<Long> {
		SIDEC(3L), SID05(1292L);

		private final Long value;

		private TipoSistemaFinanceiro(Long value) {
			this.value = value;
		}

		public Long getValue() {
			return value;
		}
	}

	public Conta() {
		super();
	}

	public Conta(Integer agencia, Integer operacao, Long numero, Integer dv) {
		// valideValores(agencia, operacao, numero, dv);
		this.agencia = agencia;
		this.operacao = operacao;
		this.numero = numero;
		this.dv = dv;
	}

	private static String completeAEsquerda(Integer i, int quantidadeMax) {
		return StringUtil.completeAEsquerda(i == null ? "" : i.toString(), quantidadeMax, '0');
	}

	private static String completeAEsquerda(Long i, int quantidadeMax) {
		return StringUtil.completeAEsquerda(i == null ? "" : i.toString(), quantidadeMax, '0');
	}

	private void valideValores(Integer agencia2, Integer operacao2, Integer numero2, Integer dv2) {
		if (!isValido(agencia2, operacao2, numero2, dv2)) {
			throw new IllegalArgumentException("DV inválido ");
		}
	}

	public static boolean isValido(Integer agencia2, Integer operacao2, Integer numero2, Integer dv2) {
		Validate.notNull(agencia2, "Agência");
		Validate.notNull(operacao2, "Operação");
		Validate.notNull(numero2, "Número");
		Validate.notNull(dv2, "DV");
		String ag = completeAEsquerda(agencia2, TAMANHO_AGENCIA);
		String op = completeAEsquerda(operacao2, TAMANHO_OPERACAO);
		String num = completeAEsquerda(numero2, TAMANHO_NUMERO);
		int digioCalculado = Modulo11.calculeDigito(ag + op + num, PESOS_CALCULO_GERAL);
		return digioCalculado == dv2;
	}


	public Integer getAgencia() {
		return agencia;
	}

	public Integer getOperacao() {
		return operacao;
	}

	public Long getNumero() {
		return numero;
	}

	public Integer getDv() {
		return dv;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agencia == null) ? 0 : agencia.hashCode());
		result = prime * result + ((dv == null) ? 0 : dv.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((operacao == null) ? 0 : operacao.hashCode());
		return result;
	}

	

	@Override
	public String toString() {
		return completeAEsquerda(agencia, TAMANHO_AGENCIA) + "/" + completeAEsquerda(operacao, TAMANHO_OPERACAO) + "." + completeAEsquerda(numero, TAMANHO_NUMERO) + "-" + dv;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Conta)) {
			return false;
		}
		Conta other = (Conta) obj;
		if (agencia == null) {
			if (other.agencia != null) {
				return false;
			}
		} else if (!agencia.equals(other.agencia)) {
			return false;
		}
		if (dv == null) {
			if (other.dv != null) {
				return false;
			}
		} else if (!dv.equals(other.dv)) {
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}
		if (operacao == null) {
			if (other.operacao != null) {
				return false;
			}
		} else if (!operacao.equals(other.operacao)) {
			return false;
		}
		return true;
	}

	public Integer getDVAgencia() {
		StringBuilder sb = new StringBuilder(getAgencia().toString()).reverse();
		return Modulo11.calculeDigito(sb.toString(), PESOS_AG);
	}

}
