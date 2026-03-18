package br.gov.caixa.silce.dominio.openjpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.openjpa.jdbc.identifier.DBIdentifier;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.AbstractValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.JavaTypes;

import br.gov.caixa.util.CaixaEnum;

/**
 * 
 * @author c101482
 *
 */
public class CaixaEnumValueHandler extends AbstractValueHandler {

	public static final String STRATEGY_NAME = "br.gov.caixa.silce.dominio.openjpa.CaixaEnumValueHandler";
	
	private static final long serialVersionUID = 1L;

	private Map<Serializable, Serializable> valesEnum = new HashMap<Serializable, Serializable>();
	
	@SuppressWarnings("unchecked")
	@Override
	public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
		
		DBIdentifier colName = DBIdentifier.newColumn(name, false);

		Class<Serializable> type = vm.getType();
		if(!type.isEnum()) {
			throw new IllegalArgumentException("Field não é enum: " + name);
		}
		
		if(!CaixaEnum.class.isAssignableFrom(type)) {
			throw new IllegalArgumentException("Field não é CaixaEnum: " + name);
		}
		
		for (Serializable enumConstant : type.getEnumConstants()) {
			CaixaEnum<Serializable> caixaEnum = (CaixaEnum<Serializable>) enumConstant;
			valesEnum.put(caixaEnum.getValue(), caixaEnum);
		}
		
		Column column = new Column();
		column.setIdentifier(colName);
		Class<?> clazz = getTipoDoGetValue(type);
		column.setJavaType(JavaTypes.getTypeCode(clazz));
		return new Column[] {column};
	}
	
	private Class<?> getTipoDoGetValue(Class<?> type) {
		Type[] genericInterfaces = type.getGenericInterfaces();
		for (Type genericSuperclass : genericInterfaces) {
			if (genericSuperclass instanceof ParameterizedType) {
				Type type2 = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
				if(type2 instanceof Class<?>) {
					return (Class<?>) type2;
				}else if(type2 instanceof ParameterizedType) {
					return (Class<?>) ((ParameterizedType) type2).getRawType();
				}
				
			}
		}
		throw new IllegalArgumentException("Classe nao parametrizável");
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
		return toDataStoreValue((Object) val);
	}

	private Object toDataStoreValue(Object value) {
		if (value == null) {
			return null;
		}

		return ((CaixaEnum<?>) value).getValue();
	}

	public Object toObjectValue(ValueMapping vm, Object val) {
		return valesEnum.get(val);
	}

	public Object toObjectValue(ValueMapping vm, Object val, OpenJPAStateManager sm, JDBCStore store,
			JDBCFetchConfiguration fetch) throws SQLException {
		return toObjectValue(vm, val);
	}

	
}
