package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Salvia Vito
 */
public class MultiMatchSearch extends AbstractBooleanSearch<Object> implements IBooleanSearch {

	
	
	public MultiMatchSearch( ){
		super();
	}
	
	public MultiMatchSearch(String field,Object value){
		super(field,value);
	}
	
	public MultiMatchSearch(String field,Object value,EnumBooleanType type){
		super(field,value,type);
	}
	
	
	public QueryBuilder getBooleanQuery() throws Exception {
		if (value == null)
			throw new Exception("Field or Value null");
		
		
        logger.trace("####################Called {} #internalBooleanSearch with parameters " +
                "field : {} - value : {}\n\n", getClass().getSimpleName(), field, value);
		
		return QueryBuilders.multiMatchQuery(value, field.split(",")).type(Type.PHRASE_PREFIX);
	}
	


}
