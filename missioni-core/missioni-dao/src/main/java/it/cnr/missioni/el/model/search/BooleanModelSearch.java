package it.cnr.missioni.el.model.search;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Salvia Vito
 */
public  class BooleanModelSearch {

	private BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
	private List<IBooleanSearch> listaSearch = new ArrayList<IBooleanSearch>();

	public BoolQueryBuilder buildQuery() {

		listaSearch.forEach(booleanSearch -> {
			try {
				
				booleanSearch.addBooleanQuery(queryBuilder);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return queryBuilder;
	}


	/**
	 * @return the queryBuilder
	 */
	public BoolQueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	/**
	 * @param queryBuilder 
	 */
	public void setQueryBuilder(BoolQueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	/**
	 * @return the listaSearch
	 */
	public List<IBooleanSearch> getListaSearch() {
		return listaSearch;
	}

	/**
	 * @param listaSearch 
	 */
	public void setListaSearch(List<IBooleanSearch> listaSearch) {
		this.listaSearch = listaSearch;
	}

	

}
