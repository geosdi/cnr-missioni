package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Salvia Vito
 */
public class PrefixSearch extends AbstractBooleanSearch<String> implements IBooleanSearch {
	
	public PrefixSearch( ){
		super();
	}
	
	public PrefixSearch(String field,String value){
		super(field,value);
	}

	public QueryBuilder getBooleanQuery() throws Exception {
		if (field == null || value == null)
			throw new Exception("Field or Value null");
		
        logger.trace("####################Called {} #internalBooleanSearch with parameters " +
                "field : {} - value : {}\n\n", getClass().getSimpleName(), field, value);
		
		return QueryBuilders.prefixQuery(field, value.toLowerCase());
	}
	

}
