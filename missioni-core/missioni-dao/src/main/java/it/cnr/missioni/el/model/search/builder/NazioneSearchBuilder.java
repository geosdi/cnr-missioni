package it.cnr.missioni.el.model.search.builder;

import java.io.Serializable;

import org.elasticsearch.index.query.BoolQueryBuilder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.EnumBooleanType;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public class NazioneSearchBuilder implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -807918615534715747L;
	private BooleanModelSearch booleanModelSearch;
	private String value = null;
	private String id;
	private int size = 10;
	private int from = 0;
	private boolean all = false;

	private NazioneSearchBuilder() {
		booleanModelSearch = new BooleanModelSearch();
	}

	public static NazioneSearchBuilder getNazioneSearchBuilder() {
		return new NazioneSearchBuilder();
	}

	public NazioneSearchBuilder withValue(String value) {
		this.setValue(value);
		if (value != null && !value.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.QUALIFICA_USER_FIELD_VALUE, value));
		return self();
	}

	public NazioneSearchBuilder withId(String id) {
		this.setId(id);
		if (id != null && !id.trim().equals(""))
			booleanModelSearch.getListaSearch()
					.add(new ExactSearch(SearchConstants.QUALIFICA_USER_FIELD_ID, id, EnumBooleanType.MUST_NOT));
		return self();
	}
	
	public NazioneSearchBuilder withAll(boolean all) {
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
	 * 
	 * @return
	 */
	private NazioneSearchBuilder self() {
		return this;
	}

}
