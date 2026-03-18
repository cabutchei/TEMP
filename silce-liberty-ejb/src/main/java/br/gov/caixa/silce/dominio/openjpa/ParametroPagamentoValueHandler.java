package br.gov.caixa.silce.dominio.openjpa;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.openjpa.jdbc.identifier.DBIdentifier;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.AbstractValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.JavaTypes;

import br.gov.caixa.silce.dominio.pagamento.ParametroPagamento;
import br.gov.caixa.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ParametroPagamentoValueHandler extends AbstractValueHandler {

	public static final String STRATEGY_NAME = "br.gov.caixa.silce.dominio.openjpa.ParametroPagamentoValueHandler";

	private static final long serialVersionUID = 1L;
	
	private static final Gson gson = new GsonBuilder().create();

	@Override
	public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
		DBIdentifier colName = DBIdentifier.newColumn(name, false);

		Column column = new Column();
		column.setIdentifier(colName);
		column.setJavaType(JavaTypes.STRING);
		return new Column[] {column};
	}

	public boolean isVersionable(ValueMapping vm) {
		return false;
	}

	public boolean objectValueRequiresLoad(ValueMapping vm) {
		return false;
	}

	public Object getResultArgument(ValueMapping vm) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
		if (val == null) {
			return null;
		}
		
		Map<ParametroPagamento, String> informacoesPagamento = (Map<ParametroPagamento, String>) val;

		JsonObject root = new JsonObject();
		Set<Entry<ParametroPagamento, String>> entrySet = informacoesPagamento.entrySet();
		for (Entry<ParametroPagamento, String> entry : entrySet) {
			root.addProperty(entry.getKey().getCodigo().toString(), entry.getValue());
		}
		
		return gson.toJson(root);
	}

	public Object toObjectValue(ValueMapping vm, Object val) {
		return toObjectValue((String) val);
	}

	public Object toObjectValue(ValueMapping vm, Object val, OpenJPAStateManager sm, JDBCStore store,
			JDBCFetchConfiguration fetch) throws SQLException {
		return toObjectValue((String) val);
	}

	private Map<ParametroPagamento, String> toObjectValue(String value) {
		if (StringUtil.isEmpty(value)) {
			return Collections.emptyMap();
		}
		
		Map<ParametroPagamento, String> informacoesPagamento = new HashMap<ParametroPagamento, String>();
		
		JsonObject fromJson = gson.fromJson(value, JsonObject.class);
		Set<Entry<String, JsonElement>> entrySet = fromJson.entrySet();
		for (Entry<String, JsonElement> entry : entrySet) {
			Long valueOf = Long.valueOf(entry.getKey());
			informacoesPagamento.put(ParametroPagamento.getByCodigo(valueOf), entry.getValue().getAsString());
		}
		
		return informacoesPagamento;
	}

}
