package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * @author Salvia Vito
 */
public class DateRangeSearch implements IBooleanSearch {

	private String field = null;
	//default
	private EnumBooleanType type = EnumBooleanType.MUST;
	private DateTime from;
	private DateTime to;

	public QueryBuilder getBooleanQuery() throws Exception{
		if(field == null )
			throw new Exception("Field or Value null");
		RangeQueryBuilder queryDate = QueryBuilders.rangeQuery(field);

		// se nulla prendo come riferimento 1/1/1970
		if (from == null)
			from = new DateTime(1970, 1, 1, 0, 0, DateTimeZone.UTC);
		queryDate.gte(from);
		// se nulla data odierna
		if (to == null)
			to = new DateTime(DateTimeZone.UTC);
		queryDate.lte(to);
		return queryDate;
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

}
