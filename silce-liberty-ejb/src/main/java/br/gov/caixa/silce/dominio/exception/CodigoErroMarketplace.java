package br.gov.caixa.silce.dominio.exception;

import br.gov.caixa.dominio.exception.ICodigoErro;
import br.gov.caixa.dominio.exception.ICodigoMensagem;
import br.gov.caixa.silce.dominio.entidade.MeioPagamento.Meio;
import br.gov.caixa.silce.dominio.entidade.Operacao.OperacaoEnum;

/**
 * Classe que cria um código de erro com um código de mensagem. A mensagem deverá estar no bundle e a chave será: <br />
 * (sigla do meio de pagamento) + '_' + (id da operacao) + '_' + (código de erro passado como parâmetro); <br />
 * Ex.: MPG_3_2063 <br />
 * MPG = {@link Meio} <br />
 * 3 = {@link OperacaoEnum} <br />
 * 2063 = Código de erro retornado pelo {@link Meio}
 * 
 * @author c127237
 */
public final class CodigoErroMarketplace implements ICodigoErro {

	private static final int TO_STRING_BUFFER_CAPACITY = 50;
	private static final long serialVersionUID = 1L;

	private final ICodigoMensagem codigoMensagem;
	private final String codigoErro;

	/**
	 * Cria um Código de Erro de alguma operação executada por um Meio de Pagamento. Utilizar esse construtor quando não
	 * há {@link CodigoMensagem} definido, e será utilizado o código de erro retornado pelo Meio de Pagamento para
	 * montar a chave da mensagem que estará no bundle.
	 */
	CodigoErroMarketplace(String codigoErro) {
		this.codigoMensagem = new CodigoMensagemMarketplace(codigoErro);
		this.codigoErro = codigoErro;
	}

	@Override
	public ICodigoMensagem getCodigoMensagem() {
		return codigoMensagem;
	}

	public String getCodigoErro() {
		return codigoErro;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CodigoErroMeioPagamento [codigoMensagem=");
		builder.append(codigoMensagem);
		builder.append(", codigoErro=");
		builder.append(codigoErro);
		builder.append(']');
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoErro == null) ? 0 : codigoErro.hashCode());
		result = prime * result + ((codigoMensagem == null) ? 0 : codigoMensagem.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CodigoErroMarketplace)) {
			return false;
		}
		CodigoErroMarketplace other = (CodigoErroMarketplace) obj;
		if (codigoErro == null) {
			if (other.codigoErro != null) {
				return false;
			}
		} else if (!codigoErro.equals(other.codigoErro)) {
			return false;
		}
		if (codigoMensagem == null) {
			if (other.codigoMensagem != null) {
				return false;
			}
		} else if (!codigoMensagem.equals(other.codigoMensagem)) {
			return false;
		}
		return true;
	}

	private static final class CodigoMensagemMarketplace implements ICodigoMensagem {

		private static final long serialVersionUID = 1L;
		private final String codigoMensagem;

		public CodigoMensagemMarketplace(String codigoErro) {
			this.codigoMensagem = codigoErro;
		}

		@Override
		public String getCodigo() {
			return codigoMensagem;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((codigoMensagem == null) ? 0 : codigoMensagem.hashCode());
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
			if (!(obj instanceof CodigoMensagemMarketplace)) {
				return false;
			}
			CodigoMensagemMarketplace other = (CodigoMensagemMarketplace) obj;
			if (codigoMensagem == null) {
				if (other.codigoMensagem != null) {
					return false;
				}
			} else if (!codigoMensagem.equals(other.codigoMensagem)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(TO_STRING_BUFFER_CAPACITY);
			builder.append("CodigoMensagemMarketplace [codigoMensagem=");
			builder.append(codigoMensagem);
			builder.append(']');
			return builder.toString();
		}

	}
}
