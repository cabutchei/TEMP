package br.gov.caixa.servico.conversor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import br.gov.caixa.util.GenericsUtil;

/**
 * @author c127237
 * @param <S>
 *            Tipo de entrada
 * @param <R>
 *            Tipo de resposta
 */
public abstract class AbstractCaixaConversor<S, R> {

	private static final Logger LOG = LogManager.getLogger(AbstractCaixaConversor.class, new MessageFormatMessageFactory());

	public final String toString(S obj) {
		try {
			return convertToString(obj);
		} catch (Exception e) {
			String msg = "Ocorreu um erro na conversão (parse) de uma mensagem. Ocorreu ao tentar converter o objeto java para uma representação em string (XML, JSON, etc)."
				+ " O tipo de entrada é: {0}. O tipo de resposta é {1}. O tipo de objeto java é {2}. O toString desse objeto é: {3}";
			Class<?> tipoEntrada = GenericsUtil.getDeclaredTypeArgument(this.getClass(), 0, true);
			Class<?> tipoResposta = GenericsUtil.getDeclaredTypeArgument(this.getClass(), 0, true);
			LOG.error(msg, tipoEntrada, tipoResposta, obj.getClass(), obj, e);
			throw new ConversorException(e);
		}
	}

	public final R fromString(String s) {
		try {
			return convertFromString(s);
		} catch (Exception e) {
			String msg = "Ocorreu um erro na conversão (parse) de uma mensagem. Ocorreu ao tentar converter uma mensagem string (XML, JSON, etc) para um objeto java."
				+ " O tipo de entrada é: {0}. O tipo de resposta é {1}. A mensagem string é: {2}";
			Class<?> tipoEntrada = GenericsUtil.getDeclaredTypeArgument(this.getClass(), 0, true);
			Class<?> tipoResposta = GenericsUtil.getDeclaredTypeArgument(this.getClass(), 0, true);
			LOG.error(msg, tipoEntrada, tipoResposta, s, e);
			throw new ConversorException(e);
		}
	}

	protected abstract String convertToString(S obj);

	protected abstract R convertFromString(String s);

}
