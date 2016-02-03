package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public class NazioneSearchBuilder implements ISearchBuilder {



	/**
	 * 
	 */
	private static final long serialVersionUID = -807918615534715747L;
	private BooleanModelSearch booleanModelSearch;
	private String id;
	private int size = 10;
	private int from = 0;
	private boolean all = false;
	
	private String fieldSort = SearchConstants.NAZIONE_FIELD_VALUE;
	private SortOrder sortOrder = SortOrder.ASC;

	private NazioneSearchBuilder() {
		booleanModelSearch = new BooleanModelSearch();
	}

	public static NazioneSearchBuilder getNazioneSearchBuilder() {
		return new NazioneSearchBuilder();
	}

	public NazioneSearchBuilder withId(String id) {
		this.setId(id);
		if (id != null && !id.trim().equals(""))
			booleanModelSearch.getListaSearch()
					.add(new ExactSearch(SearchConstants.NAZIONE_FIELD_ID, id));
		return self();
	}
	
	public NazioneSearchBuilder withAll(boolean all) {
		this.all = all;
		return self();
	}


	public NazioneSearchBuilder withSize(int size) {
		this.size = size;
		return self();
	}

	public NazioneSearchBuilder withFrom(int from) {
		this.from = from;
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
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the from
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * @param from
	 */
	public void setFrom(int from) {
		this.from = from;
	}

	/**
	 * @return the all
	 */
	public boolean isAll() {
		return all;
	}

	/**
	 * @param all 
	 */
	public void setAll(boolean all) {
		this.all = all;
	}

	/**
	 * @return the fieldSort
	 */
	public String getFieldSort() {
		return fieldSort;
	}

	/**
	 * @param fieldSort 
	 */
	public void setFieldSort(String fieldSort) {
		this.fieldSort = fieldSort;
	}

	/**
	 * @return the sortOrder
	 */
	public SortOrder getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder 
	 */
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * 
	 * @return
	 */
	private NazioneSearchBuilder self() {
		return this;
	}

}
