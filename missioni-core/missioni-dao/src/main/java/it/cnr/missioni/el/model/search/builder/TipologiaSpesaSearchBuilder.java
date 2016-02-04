package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.ExactSearch;

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
	private String id;
	private String tipo;
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
	
	public TipologiaSpesaSearchBuilder withId(String id) {
		this.setId(id);
		if (id != null && !id.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_ID, id));
		return self();
	}

	public TipologiaSpesaSearchBuilder withTipo(String tipo) {
		this.tipo = tipo;
		if (tipo != null && !tipo.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_TIPO, tipo));
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
	 * @param all
	 */
	public void setAll(boolean all) {
		this.all = all;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo 
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
