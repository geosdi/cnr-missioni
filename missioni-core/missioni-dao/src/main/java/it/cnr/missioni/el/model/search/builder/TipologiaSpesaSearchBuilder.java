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
	private String tipoTrattamento;
	private int size = 10;
	private int from = 0;
	private boolean italia = false;
	private boolean estera = false;
	
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

	public TipologiaSpesaSearchBuilder withEstera(boolean estera) {
		this.estera = estera;
		if(estera)
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_ESTERA, estera));
		return self();
	}
	
	public TipologiaSpesaSearchBuilder withItalia(boolean italia) {
		this.italia = italia;
		if(italia)
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_ITALIA, italia));
		return self();
	}

	public TipologiaSpesaSearchBuilder withTipoTrattamento(String tipoTrattamento) {
		this.tipoTrattamento = tipoTrattamento;
		if (tipoTrattamento != null && !tipoTrattamento.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.TIPOLOGIA_SPESA_FIELD_TIPO_TRATTAMENTO, tipoTrattamento));
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
	 * @return the tipoTrattamento
	 */
	public String getTipoTrattamento() {
		return tipoTrattamento;
	}

	/**
	 * @param tipoTrattamento 
	 */
	public void setTipoTrattamento(String tipoTrattamento) {
		this.tipoTrattamento = tipoTrattamento;
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
	 * @return the italia
	 */
	public boolean isItalia() {
		return italia;
	}

	/**
	 * @param italia 
	 */
	public void setItalia(boolean italia) {
		this.italia = italia;
	}

	/**
	 * @return the estera
	 */
	public boolean isEstera() {
		return estera;
	}

	/**
	 * @param estera 
	 */
	public void setEstera(boolean estera) {
		this.estera = estera;
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
