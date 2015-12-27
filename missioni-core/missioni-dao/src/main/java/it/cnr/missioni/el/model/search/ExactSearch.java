package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Salvia Vito
 */
public class ExactSearch implements IBooleanSearch{

	private String field = null;
	private Object value = null;
	//default
	private EnumBooleanType type = EnumBooleanType.MUST;
	

	
	public QueryBuilder getBooleanQuery() throws Exception{
		if(field == null || value == null)
			throw new Exception("Field or Value null");
		return QueryBuilders.matchQuery(field, value);
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
	public Object getValue() {
		return value;
	}

	/**
	 * @param value 
	 */
	public void setValue(Object value) {
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
