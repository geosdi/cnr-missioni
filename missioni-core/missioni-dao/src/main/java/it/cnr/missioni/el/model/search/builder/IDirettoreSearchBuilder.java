package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.BoolQueryBuilder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public class DirettoreSearchBuilder implements ISearchBuilder {


	/**
	 * 
	 */
	private static final long serialVersionUID = 408602284907916569L;
	private BooleanModelSearch booleanModelSearch;
	private String id;


	private DirettoreSearchBuilder() {
		booleanModelSearch = new BooleanModelSearch();
	}

	public static DirettoreSearchBuilder getMassimaleSearchBuilder() {
		return new DirettoreSearchBuilder();
	}

	public DirettoreSearchBuilder withId(String id) {
		this.setId(id);
		if (id != null && !id.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_ID, id));
		return self();
	}
	
	
	public BoolQueryBuilder buildQuery() {
		return booleanModelSearch.buildQuery();
	}

	/**
	 * @return the booleanModelSearch
	 */
	public BooleanModelSearch getBooleanModelSearch() {
		return booleanModelSearch;
	}

	/**
	 * @param booleanModelSearch
	 */
	public void setBooleanModelSearch(BooleanModelSearch booleanModelSearch) {
		this.booleanModelSearch = booleanModelSearch;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	private DirettoreSearchBuilder self() {
		return this;
	}

	/**
	 * @param from
	 * @return
	 */
	@Override
	public ISearchBuilder withFrom(int from) {
		// TODO Auto-generated method stub
		return null;
	}


}
