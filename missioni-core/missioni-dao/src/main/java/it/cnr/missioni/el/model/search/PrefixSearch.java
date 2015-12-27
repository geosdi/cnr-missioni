package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Salvia Vito
 */
public class PrefixSearch implements IBooleanSearch {

	private String field = null;
	private String value = null;
	//default
	private EnumBooleanType type = EnumBooleanType.MUST;

	public QueryBuilder getBooleanQuery() throws Exception{
		if(field == null || value == null)
			throw new Exception("Field or Value null");
		return QueryBuilders.prefixQuery(field, value.toLowerCase());
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public EnumBooleanType getType() {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(EnumBooleanType type) {
		this.type = type;
	}

}
