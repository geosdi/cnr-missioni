package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.BoolQueryBuilder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public class RimborsoKmSearchBuilder implements ISearchBuilder {


	/**
	 * 
	 */
	private static final long serialVersionUID = -817549323873815355L;
	private BooleanModelSearch booleanModelSearch;
	private String id;

	private RimborsoKmSearchBuilder() {
		booleanModelSearch = new BooleanModelSearch();
	}

	public static RimborsoKmSearchBuilder getRimborsoKmSearchBuilder() {
		return new RimborsoKmSearchBuilder();
	}


	public RimborsoKmSearchBuilder withId(String id) {
		this.setId(id);
		if (id != null && !id.trim().equals(""))
			booleanModelSearch.getListaSearch()
					.add(new ExactSearch(SearchConstants.RIMBORSO_KM_FIELD_ID, id));
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
	private RimborsoKmSearchBuilder self() {
		return this;
	}

}
