package it.cnr.missioni.el.model.search.builder;

import java.io.Serializable;

import org.elasticsearch.index.query.BoolQueryBuilder;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.EnumBooleanType;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public class RimborsoKmSearchBuilder implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -817549323873815355L;
	private BooleanModelSearch booleanModelSearch;
	private String value = null;
	private String id;
	private int size = 10;
	private int from = 0;

	private RimborsoKmSearchBuilder() {
		booleanModelSearch = new BooleanModelSearch();
	}

	public static RimborsoKmSearchBuilder getRimborsoKmSearchBuilder() {
		return new RimborsoKmSearchBuilder();
	}

	public RimborsoKmSearchBuilder withValue(String value) {
		this.setValue(value);
		if (value != null && !value.trim().equals(""))
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.RIMBORSO_KM_FIELD_VALUE, value));
		return self();
	}

	public RimborsoKmSearchBuilder withId(String id) {
		this.setId(id);
		if (id != null && !id.trim().equals(""))
			booleanModelSearch.getListaSearch()
					.add(new ExactSearch(SearchConstants.RIMBORSO_KM_FIELD_ID, id, EnumBooleanType.MUST_NOT));
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

	public RimborsoKmSearchBuilder withSize(int size) {
		this.size = size;
		return self();
	}

	public RimborsoKmSearchBuilder withFrom(int from) {
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
	 * 
	 * @return
	 */
	private RimborsoKmSearchBuilder self() {
		return this;
	}

}
