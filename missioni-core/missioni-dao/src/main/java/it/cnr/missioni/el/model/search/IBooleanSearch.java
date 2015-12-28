package it.cnr.missioni.el.model.search;

import org.elasticsearch.index.query.BoolQueryBuilder;

/**
 * @author Salvia Vito
 */
public interface IBooleanSearch {
	
	void addBooleanQuery(BoolQueryBuilder queryBuilder ) throws Exception;

}
