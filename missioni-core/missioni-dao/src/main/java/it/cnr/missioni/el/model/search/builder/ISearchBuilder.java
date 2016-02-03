package it.cnr.missioni.el.model.search.builder;

import java.io.Serializable;

import org.elasticsearch.index.query.BoolQueryBuilder;

/**
 * @author Salvia Vito
 */
public interface ISearchBuilder extends Serializable{

	
	BoolQueryBuilder buildQuery();
	
}
