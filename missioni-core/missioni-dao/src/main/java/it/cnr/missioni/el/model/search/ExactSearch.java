package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Salvia Vito
 */
public class ExactSearch extends IBooleanSearch.AbstractBooleanSearch<Object> {

	
	private Operator operator = Operator.AND;
	
	public ExactSearch( ){
		super();
	}
	
	public ExactSearch(String field,Object value,Operator operator){
		super(field,value);
		this.operator = operator;
	}
	
	public ExactSearch(String field,Object value){
		super(field,value);
	}
	
	public ExactSearch(String field,Object value,EnumBooleanType type,Operator operator){
		super(field,value,type);
		this.operator = operator;
	}
	
	public ExactSearch(String field,Object value,EnumBooleanType type ){
		super(field,value,type);
	}
	
	
	public QueryBuilder getBooleanQuery() throws Exception {
		if (field == null || value == null)
			throw new Exception("Field or Value null");
		
		
        logger.trace("####################Called {} #internalBooleanSearch with parameters " +
                "field : {} - value : {}\n\n", getClass().getSimpleName(), field, value);
		
		return QueryBuilders.matchQuery(field, value).operator(operator);
	}
	


}
