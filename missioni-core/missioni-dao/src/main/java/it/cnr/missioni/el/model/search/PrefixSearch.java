package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Salvia Vito
 */
public class PrefixSearch extends AbstractBooleanSearch<String> implements IBooleanSearch {

	public QueryBuilder getBooleanQuery() throws Exception {
		if (field == null || value == null)
			throw new Exception("Field or Value null");
		return QueryBuilders.prefixQuery(field, value.toLowerCase());
	}

}
