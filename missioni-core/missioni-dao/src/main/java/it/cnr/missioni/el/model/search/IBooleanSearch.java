package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author Salvia Vito
 */
public interface IBooleanSearch {
	
	public QueryBuilder getBooleanQuery() throws Exception;
		
	public EnumBooleanType getType();

}
