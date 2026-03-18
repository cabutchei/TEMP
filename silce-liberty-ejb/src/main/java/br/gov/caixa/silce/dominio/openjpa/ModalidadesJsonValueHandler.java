package br.gov.caixa.silce.dominio.openjpa;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.openjpa.jdbc.identifier.DBIdentifier;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.AbstractValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.JavaTypes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.EnumUtil;
import br.gov.caixa.util.StringUtil;

public class ModalidadesJsonValueHandler extends AbstractValueHandler {

	private static final class TypeTokenExtension extends TypeToken<List<Integer>> {
	}

	private static final Type TYPE_LIST_INTEGER = new TypeTokenExtension().getType();

	public static final String STRATEGY_NAME = "br.gov.caixa.silce.dominio.openjpa.ModalidadesJsonValueHandler";

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
		
		List<Modalidade> modalidades = (List<Modalidade>) val;
		List<Integer> asValueList = EnumUtil.recupereValuesByEnums(modalidades);
		return gson.toJson(asValueList);
	}

	public Object toObjectValue(ValueMapping vm, Object val) {
		return toObjectValue((String) val);
	}

	public Object toObjectValue(ValueMapping vm, Object val, OpenJPAStateManager sm, JDBCStore store,
			JDBCFetchConfiguration fetch) throws SQLException {
		return toObjectValue((String) val);
	}

	private List<Modalidade> toObjectValue(String value) {
		if (StringUtil.isEmpty(value)) {
			return Collections.emptyList();
		}
		
		List<Integer> fromJson = gson.fromJson(value, TYPE_LIST_INTEGER);
		return EnumUtil.recupereEnumsByValues(Modalidade.values(), fromJson);
	}

}
