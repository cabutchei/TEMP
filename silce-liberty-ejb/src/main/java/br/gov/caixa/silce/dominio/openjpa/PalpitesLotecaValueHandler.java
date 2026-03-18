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

import br.gov.caixa.silce.dominio.jogos.Palpites;
import br.gov.caixa.silce.dominio.jogos.PalpitesLoteca;

/**
 * @author c101482
 *
 */
public class PalpitesLotecaValueHandler extends AbstractValueHandler {

	public static final String STRATEGY_NAME = "br.gov.caixa.silce.dominio.openjpa.PalpitesLotecaValueHandler";

	private static final long serialVersionUID = 1L;

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

	public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
		return toDataStoreValue((Palpites) val);
	}

	private String toDataStoreValue(Palpites value) {
		if(value == null) {
			return null;
		}
		return value.toString();
	}

	public Object toObjectValue(ValueMapping vm, Object val) {
		return toObjectValue((String) val);
	}

	public Object toObjectValue(ValueMapping vm, Object val, OpenJPAStateManager sm, JDBCStore store,
			JDBCFetchConfiguration fetch) throws SQLException {
		return toObjectValue((String) val);
	}

	private Palpites toObjectValue(String value) {
		if (value == null) {
			return null;
		}

		return new PalpitesLoteca(value);

	}

}
