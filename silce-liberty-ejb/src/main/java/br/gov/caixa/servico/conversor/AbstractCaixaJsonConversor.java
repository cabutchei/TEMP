package br.gov.caixa.servico.conversor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.gov.caixa.dominio.EntradaServico;
import br.gov.caixa.dominio.SaidaServico;
import br.gov.caixa.util.StringUtil;

/**
 * @author c127237
 * @param <E> Tipo de entrada
 * @param <S> Tipo de saída
 */
public abstract class AbstractCaixaJsonConversor<E extends EntradaServico<S>, S extends SaidaServico> extends AbstractCaixaConversor<E, S> {

	private static final Logger LOG = LogManager.getLogger(AbstractCaixaJsonConversor.class, new MessageFormatMessageFactory());

	private final Gson gson;
	
	public AbstractCaixaJsonConversor() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		if (isFormatJson()) {
			gsonBuilder.setPrettyPrinting();
		}
		gson = gsonBuilder.create();
	}
	

	@Override
	public String convertToString(E obj) {

		try {
			JsonObject jsonObject = toJsonObject(obj);
			if (jsonObject != null) {
				return gson.toJson(jsonObject);
			}
		} catch (Exception e) {
			LOG.error("Ocorreu um erro ao tentar converter um JSON para String. Valor recebido: {0}", obj, e);
		}

		return null;
	}

	@Override
	public S convertFromString(String s) {

		try {
			if (!StringUtil.isEmpty(s)) {
				JsonElement fromJson = gson.fromJson(s, JsonElement.class);
				S convertido = fromJsonObject(fromJson);
				convertHeaderFromString(fromJson, convertido);
				return convertido;
			}
		} catch (Exception e) {
			LOG.error("Ocorreu um erro ao tentar converter uma String para JSON. Valor recebido: {0}", s, e);
		}

		return null;
	}

	protected void convertHeaderFromString(JsonElement fromJson, S convertido) {
		// O metodo deve ser vazio, visto que não é necessario que todos implmentem, é opcional
		// é possivel fazer sem no entando duplicaria-se codigo.
	}

	protected abstract JsonObject toJsonObject(E entrada);
	
	protected abstract S fromJsonObject(JsonElement jsonObject);
	
	protected abstract boolean isFormatJson();
	
	protected abstract boolean isValidKey(JsonObject jsonObject, String key);

	public Gson getGson() {
		return gson;
	}

}
