package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Salvia Vito
 */
public class ExactSearch extends AbstractBooleanSearch<Object> implements IBooleanSearch {

	
	public ExactSearch( ){
		super();
	}
	
	public ExactSearch(String field,Object value){
		super(field,value);
	}
	
	public QueryBuilder getBooleanQuery() throws Exception {
		if (field == null || value == null)
			throw new Exception("Field or Value null");
		return QueryBuilders.matchQuery(field, value);
	}

}
