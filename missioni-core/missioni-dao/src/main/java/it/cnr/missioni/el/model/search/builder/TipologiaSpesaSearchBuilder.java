package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;

/**
 * @author Salvia Vito
 */
public class TipologiaSpesaSearchBuilder implements ISearchBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -817549323873815355L;
	private BooleanModelSearch booleanModelSearch;
	private boolean all;
	private int size = 10;
	private int from = 0;
	
	private String fieldSort = SearchConstants.TIPOLOGIA_SPESA_FIELD_VALUE;
	private SortOrder sortOrder = SortOrder.ASC;

	private TipologiaSpesaSearchBuilder() {
		booleanModelSearch = new BooleanModelSearch();
	}

	public static TipologiaSpesaSearchBuilder getTipologiaSpesaSearchBuilder() {
		return new TipologiaSpesaSearchBuilder();
	}


	public TipologiaSpesaSearchBuilder withAll(boolean all) {
		this.setAll(all);
		return self();
	}



	public TipologiaSpesaSearchBuilder withSize(int size) {
		this.size = size;
		return self();
	}

	public TipologiaSpesaSearchBuilder withFrom(int from) {
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
	private TipologiaSpesaSearchBuilder self() {
		return this;
	}

}
