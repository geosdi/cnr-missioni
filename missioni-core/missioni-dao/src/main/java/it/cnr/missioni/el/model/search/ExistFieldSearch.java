package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Salvia Vito
 */
public class ExistFieldSearch extends AbstractBooleanSearch<Object> implements IBooleanSearch {

	
	public ExistFieldSearch( ){
		super();
	}
	
	public ExistFieldSearch(String field){
		super(field);
	}
	
	public ExistFieldSearch(String field,EnumBooleanType type ){
		super(field,type);
	}
	
	public QueryBuilder getBooleanQuery() throws Exception {
		if (field == null)
			throw new Exception("Field or Value null");
		
		
        logger.trace("####################Called {} #internalBooleanSearch with parameters " +
                "field : {} - value : {}\n\n", getClass().getSimpleName(), field, value);
		
		return QueryBuilders.existsQuery(field);
	}
	


}
