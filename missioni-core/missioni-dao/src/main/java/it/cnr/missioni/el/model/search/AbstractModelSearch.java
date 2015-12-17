package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public abstract class AbstractModelSearch {

	private BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
	

	/**
	 * 
	 * Costruisce la query per una ricerca fra 2 date
	 * 
	 * @param field
	 * @param from
	 * @param to
	 */
	public void buildRangeDateQuery(String field, DateTime from, DateTime to) {
		RangeQueryBuilder queryDate = QueryBuilders.rangeQuery(field);
		
		//se nulla prendo come riferimento 1/1/1970
		if(from == null)
			from = new DateTime(1970,1,2,0,0);
			queryDate.gte(from);
			//se nulla data odierna
			if (to == null)
				to = new DateTime();
			queryDate.lte(to);
			queryBuilder.must(queryDate);
	}

	/**
	 * 
	 * Costruisce la query ricercando per un valore esatto
	 * 
	 * @param field
	 * @param value
	 */
	public void buildExactValue(String field, String value) {
		queryBuilder.must(QueryBuilders.matchQuery(field, value));
	}

	/**
	 * 
	 * Costruisce la query in modo da ricercare il valore per prefix
	 * 
	 * @param field
	 * @param value
	 */
	public void buildStringQueryPrefix(String field, String value) {
		this.getQueryBuilder().must(QueryBuilders.prefixQuery(field, value.toLowerCase()));
	}

	/**
	 * @return the queryBuilder
	 */
	public BoolQueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	/**
	 * @param queryBuilder
	 */
	public void setQueryBuilder(BoolQueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}
	
	public abstract void buildQuery();

}
