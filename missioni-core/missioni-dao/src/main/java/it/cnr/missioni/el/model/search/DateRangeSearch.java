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

	public QueryBuilder getBooleanQuery() throws Exception {
		if (field == null)
			throw new Exception("Field or Value null");
		RangeQueryBuilder queryDate = QueryBuilders.rangeQuery(field);

		// se null prende come riferimento 1/1/1970
		if (from == null)
			from = new DateTime(1970, 1, 1, 0, 0, DateTimeZone.UTC);
		queryDate.gte(from);
		// se null data odierna
		if (to == null)
			to = new DateTime(DateTimeZone.UTC);
		queryDate.lte(to);
		return queryDate;
	}

}
