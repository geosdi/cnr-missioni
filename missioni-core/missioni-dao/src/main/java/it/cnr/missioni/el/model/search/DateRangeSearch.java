package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * @author Salvia Vito
 */
public class DateRangeSearch extends AbstractBooleanSearch implements IBooleanSearch {
	
	private DateTime from;
	private DateTime to;
	
	public DateRangeSearch( ){
		super();
	}
	
	public DateRangeSearch(String field,DateTime from,DateTime to){
		super(field);
		this.from = from;
		this.to = to;
	}

	public QueryBuilder getBooleanQuery() throws Exception {
		if (field == null)
			throw new Exception("Field or Value null");
		RangeQueryBuilder queryDate = QueryBuilders.rangeQuery(field);

		// se null prende come riferimento 1/1/1970
		if (from == null && to != null)
			from = new DateTime(1970, 1, 1, 0, 0, DateTimeZone.UTC);
		queryDate.gte(from);
		// se null data odierna
		if (to == null && from != null)
			to = new DateTime(DateTimeZone.UTC);
		queryDate.lte(to);
		
        logger.trace("####################Called {} #internalBooleanSearch with parameters " +
                "field : {} - value : {}\n\n", getClass().getSimpleName(), field, from+"-"+to);
		
		return queryDate;
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

	@Override
    public String toString() {
        return getClass().getSimpleName() + " {"
                + " field = " + field
                + ", value = " + from+"-"+to + '}';
    }

}
