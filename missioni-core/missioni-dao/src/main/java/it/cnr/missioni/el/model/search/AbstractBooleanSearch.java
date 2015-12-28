package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public abstract class AbstractBooleanSearch<S> {
	
	
	protected String field;
	protected S value;
	protected DateTime from;
	protected DateTime to;
	//DEFAULT
	protected EnumBooleanType type = EnumBooleanType.MUST;
	
	public abstract  QueryBuilder getBooleanQuery() throws Exception;
	
	public  void addBooleanQuery(BoolQueryBuilder queryBuilder ) throws Exception {
		switch (type.name()) {
		case "SHOUD":
			queryBuilder.should(getBooleanQuery());
			break;
		case "MUST":
			queryBuilder.must(getBooleanQuery());
			break;
		case "MUST_NOT":
			queryBuilder.mustNot(getBooleanQuery());
			break;
		}

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
	public S getValue() {
		return value;
	}

	/**
	 * @param value 
	 */
	public void setValue(S value) {
		this.value = value;
	}

	/**
	 * @return the from
	 */
	public DateTime getFrom() {
		return from;
	}

	/**
	 * @param from 
	 */
	public void setFrom(DateTime from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public DateTime getTo() {
		return to;
	}

	/**
	 * @param to 
	 */
	public void setTo(DateTime to) {
		this.to = to;
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
