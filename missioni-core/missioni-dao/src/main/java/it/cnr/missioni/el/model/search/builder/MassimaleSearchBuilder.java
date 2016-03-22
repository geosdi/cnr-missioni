package it.cnr.missioni.el.model.search.builder;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.EnumBooleanType;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public class MassimaleSearchBuilder implements ISearchBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3676936353865160224L;
	private BooleanModelSearch booleanModelSearch;
	private String value = null;
	private String id;
	private String notId;
	private String livello;
	private String areaGeografica;
	private String tipo;
	private int size = 10;
	private int from = 0;

	private String fieldSort = SearchConstants.MASSIMALE_FIELD_AREA_GEOGRAFICA;
	private SortOrder sortOrder = SortOrder.ASC;

	private MassimaleSearchBuilder() {
		booleanModelSearch = new BooleanModelSearch();
	}

	public static MassimaleSearchBuilder getMassimaleSearchBuilder() {
		return new MassimaleSearchBuilder();
	}

	public MassimaleSearchBuilder withId(String id) {
		this.setId(id);
		if (id != null && !id.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_ID, id));
		return self();
	}
	
	public MassimaleSearchBuilder withNotId(String notId) {
		this.notId = notId;
		if (notId != null && !notId.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_ID, notId,EnumBooleanType.MUST_NOT));
		return self();
	}

	public MassimaleSearchBuilder withLivello(String livello) {
		this.setLivello(livello);
		if (livello != null && !livello.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_LIVELLO, livello));
		return self();
	}

	public MassimaleSearchBuilder withAreaGeografica(String areaGeografica) {
		this.setAreaGeografica(areaGeografica);
		if (areaGeografica != null && !areaGeografica.trim().equals(""))
			booleanModelSearch.getListaSearch()
					.add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_AREA_GEOGRAFICA, areaGeografica));
		return self();
	}
	
	public MassimaleSearchBuilder withTipo(String tipo) {
		this.tipo = tipo;
		if (tipo != null && !tipo.trim().equals(""))
			booleanModelSearch.getListaSearch()
					.add(new ExactSearch(SearchConstants.MASSIMALE_FIELD_TIPO, tipo));
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

	public MassimaleSearchBuilder withSize(int size) {
		this.size = size;
		return self();
	}

	public MassimaleSearchBuilder withFrom(int from) {
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
	 * @return the notId
	 */
	public String getNotId() {
		return notId;
	}

	/**
	 * @param notId 
	 */
	public void setNotId(String notId) {
		this.notId = notId;
	}

	/**
	 * @return the livello
	 */
	public String getLivello() {
		return livello;
	}

	/**
	 * @param livello
	 */
	public void setLivello(String livello) {
		this.livello = livello;
	}

	/**
	 * @return the areaGeografica
	 */
	public String getAreaGeografica() {
		return areaGeografica;
	}

	/**
	 * @param areaGeografica
	 */
	public void setAreaGeografica(String areaGeografica) {
		this.areaGeografica = areaGeografica;
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
	private MassimaleSearchBuilder self() {
		return this;
	}


}
