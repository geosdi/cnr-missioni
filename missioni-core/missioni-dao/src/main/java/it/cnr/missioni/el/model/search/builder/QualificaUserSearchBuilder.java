package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public class QualificaUserSearchBuilder implements ISearchBuilder{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8819699628235873367L;
	/**
	 * 
	 */
	private BooleanModelSearch booleanModelSearch;
	private String value = null;
	private int size = 10;
	private int from = 0;
	private boolean all = false;
	
	private String fieldSort = SearchConstants.QUALIFICA_USER_FIELD_VALUE;
	private SortOrder sortOrder = SortOrder.ASC;
	
	private QualificaUserSearchBuilder() {
		booleanModelSearch = new BooleanModelSearch();
	}

	public static QualificaUserSearchBuilder getQualificaUserSearchBuilder() {
		return new QualificaUserSearchBuilder();
	}

	public QualificaUserSearchBuilder withValue(String value) {
		this.setValue(value);
		if (value != null && !value.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.QUALIFICA_USER_FIELD_VALUE, value));
		return self();
	}

	
	public QualificaUserSearchBuilder withAll(boolean all) {
		this.all = all;
		return self();
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public QualificaUserSearchBuilder withSize(int size) {
		this.size = size;
		return self();
	}

	public QualificaUserSearchBuilder withFrom(int from) {
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
	private QualificaUserSearchBuilder self() {
		return this;
	}

}
