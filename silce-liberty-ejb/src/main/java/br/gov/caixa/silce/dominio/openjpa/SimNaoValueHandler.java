package br.gov.caixa.silce.dominio.openjpa;

import java.sql.SQLException;

import org.apache.openjpa.jdbc.identifier.DBIdentifier;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.AbstractValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.JavaTypes;

public class SimNaoValueHandler extends AbstractValueHandler {

	public static final String STRATEGY_NAME = "br.gov.caixa.silce.dominio.openjpa.SimNaoValueHandler";

	private static final long serialVersionUID = 1L;

	@Override
	public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
		DBIdentifier colName = DBIdentifier.newColumn(name, false);

		Column column = new Column();
		column.setIdentifier(colName);
		column.setJavaType(JavaTypes.CHAR_OBJ);
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

	public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
		return toDataStoreValue((Boolean) val);
	}

	private Character toDataStoreValue(Boolean value) {
		if (value == null) {
			return null;
		}

		return value ? 'S' : 'N';
	}

	public Object toObjectValue(ValueMapping vm, Object val) {
		return toObjectValue((Character) val);
	}

	public Object toObjectValue(ValueMapping vm, Object val, OpenJPAStateManager sm, JDBCStore store,
			JDBCFetchConfiguration fetch) throws SQLException {
		return toObjectValue((Character) val);
	}

	private Boolean toObjectValue(Character value) {
		if (value == null) {
			return null;
		}

		return value.equals('S');
	}

}
