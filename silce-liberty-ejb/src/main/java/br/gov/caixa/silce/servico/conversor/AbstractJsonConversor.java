package br.gov.caixa.silce.servico.conversor;

import com.google.gson.JsonObject;

import br.gov.caixa.dominio.EntradaServico;
import br.gov.caixa.dominio.SaidaServico;
import br.gov.caixa.servico.conversor.AbstractCaixaJsonConversor;
import br.gov.caixa.silce.dominio.util.PropriedadeJVMUtil;
import br.gov.caixa.silce.dominio.util.PropriedadeJVMUtil.PropriedadeJVM;

public abstract class AbstractJsonConversor<E extends EntradaServico<S>, S extends SaidaServico> extends AbstractCaixaJsonConversor<E, S> {

	@Override
	protected final boolean isFormatJson() {
		return Boolean.valueOf(PropriedadeJVMUtil.getProperty(PropriedadeJVM.FORMAT_XML));
	}
	
	@Override
	protected final boolean isValidKey(JsonObject jsonObject, String key) {
		return jsonObject.has(key) && !jsonObject.get(key).isJsonNull();
	}

}
